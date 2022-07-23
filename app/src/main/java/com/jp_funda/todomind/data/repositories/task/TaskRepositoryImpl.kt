package com.jp_funda.todomind.data.repositories.task

import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.domain.repositories.TaskRepository
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import java.util.*
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor() : TaskRepository {
    // CREATE
    override suspend fun insertTasks(tasks: List<Task>) {
        Realm.getDefaultInstance().use {
            it.executeTransaction { realm ->
                for (task in tasks) {
                    realm.insertOrUpdate(task)
                }
            }
        }
    }

    // READ
    override suspend fun getAllTasks(): List<Task> {
        Realm.getDefaultInstance().use {
            var result = emptyList<Task>()
            it.executeTransaction { realm ->
                val result1 = realm.where<Task>().findAll()
                result = Realm.getDefaultInstance().copyFromRealm(result1)
            }
            return result
        }
    }

    override suspend fun getTask(id: UUID): Task? {
        Realm.getDefaultInstance().use {
            var resultTask: Task? = null
            it.executeTransaction { realm ->
                val result = realm.where<Task>().equalTo("id", id).findFirst()
                resultTask = result?.let { task -> realm.copyFromRealm(task) }
            }
            return resultTask
        }
    }

    /**
     * @param lastRemindedTask is needed only when set next reminder in a same minutes.
     */
    override suspend fun getNextRemindTask(lastRemindedTask: Task?): Task? {
        Realm.getDefaultInstance().use {
            var nextRemindTask: Task? = null
            it.executeTransaction { realm ->
                val date = Date()
                date.minutes -= 1
                val result = realm.where<Task>()
                    .greaterThan("dueDate", date)
                    .sort("dueDate", Sort.ASCENDING, "updatedDate", Sort.ASCENDING)
                    .greaterThan("updatedDate", lastRemindedTask?.updatedDate ?: Date(0))
                    .findFirst()
                result?.let { task ->
                    nextRemindTask = realm.copyFromRealm(task)
                }
            }
            return nextRemindTask
        }
    }

    override suspend fun getTasksInAMindMap(mindMap: MindMap): List<Task> {
        Realm.getDefaultInstance().use {
            var tasks = emptyList<Task>()
            it.executeTransaction { realm ->
                val result1 = realm.where<Task>().equalTo("mindMap.id", mindMap.id).findAll()
                tasks = realm.copyFromRealm(result1)
            }
            return tasks
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
        Realm.getDefaultInstance().use {
            it.executeTransaction { realm ->
                realm.copyToRealmOrUpdate(updatedTask)
            }
        }
    }

    // DELETE
    override suspend fun deleteTask(task: Task) {
        Realm.getDefaultInstance().use {
            it.executeTransaction { realm ->
                val deletingTask = realm.where<Task>().equalTo("id", task.id).findFirst()
                deletingTask?.deleteFromRealm()
            }
        }
    }

    override suspend fun deleteTasksInAMindMap(mindMap: MindMap) {
        Realm.getDefaultInstance().use {
            it.executeTransaction { realm ->
                realm.where<Task>().equalTo("mindMap.id", mindMap.id).findAll().deleteAllFromRealm()
            }
        }
    }
}