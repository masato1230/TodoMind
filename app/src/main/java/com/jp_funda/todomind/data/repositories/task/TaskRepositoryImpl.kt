package com.jp_funda.todomind.data.repositories.task

import android.util.Log
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.domain.repositories.TaskRepository
import com.jp_funda.todomind.notification.TaskReminder
import io.reactivex.rxjava3.core.Single
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import java.util.*
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor() : TaskRepository {
    // CREATE
    override suspend fun insertTasks(tasks: List<Task>) {
        Realm.getDefaultInstance().use { realm ->
            for (task in tasks) {
                realm.insertOrUpdate(task)
            }
        }
    }

    // READ
    override suspend fun getAllTasks(): List<Task> {
        Realm.getDefaultInstance().use { realm ->
            val result1 = realm.where<Task>().findAll()
            return Realm.getDefaultInstance().copyFromRealm(result1)
        }
    }

    override suspend fun getTask(id: UUID): Task? {
        Realm.getDefaultInstance().use { realm ->
            val result = realm.where<Task>().equalTo("id", id).findFirst()
            return result?.let { realm.copyFromRealm(it) }
        }
    }

    override suspend fun getNextRemindTask(lastRemindedTask: Task?): Task? {
        Realm.getDefaultInstance().use { realm ->
            val date = Date()
            date.minutes -= 1
            val result = realm.where<Task>()
                .greaterThan("dueDate", date)
                .greaterThan("updatedDate", lastRemindedTask?.updatedDate ?: Date(0))
                .findFirst()
            return result?.let { realm.copyFromRealm(it) }
        }
    }

    override suspend fun getTasksInAMindMap(mindMap: MindMap): List<Task> {
        Realm.getDefaultInstance().use { realm ->
            val result1 = realm.where<Task>().equalTo("mindMap.id", mindMap.id).findAll()
            return realm.copyFromRealm(result1)
        }
    }

    override suspend fun getTasksFilteredByStatus(status: TaskStatus): List<Task> {
        Realm.getDefaultInstance().use { realm ->
            val result = realm.where<Task>().equalTo("status", status.state).findAll()
            return realm.copyFromRealm(result)
        }
    }

    // UPDATE
    override suspend fun updateTask(updatedTask: Task) {
        Realm.getDefaultInstance().use { realm ->
            updatedTask.updatedDate = Date()
            realm.copyToRealmOrUpdate(updatedTask)
        }
    }
}

// TODO old

// DELETE
fun deleteTask(task: Task): Single<Task> {
    return Single.create { emitter ->
        Realm.getDefaultInstance().use {
            it.executeTransactionAsync { realm ->
                val realmTask = realm.where<Task>().equalTo("id", task.id).findFirst()
                realmTask?.let { deletingTask ->
                    deletingTask.deleteFromRealm()
                    emitter.onSuccess(task)
                    // set reminder
                    setNextReminder(realm)
                } ?: run {
                    emitter.onError(Throwable("Error at deleteTask"))
                }
            }
        }
    }
}

private fun setNextReminder(realm: Realm) {
    // set Reminder
    val date = Date()
    date.minutes -= 1
    val result = realm.where<Task>()
        .greaterThan("dueDate", date)
        .sort("dueDate", Sort.ASCENDING)
        .findFirst()
    result?.let { nextRemindTask ->
        Log.d("Next", nextRemindTask.toString())
        TaskReminder.setTaskReminder(nextRemindTask, context)
    }
}
}