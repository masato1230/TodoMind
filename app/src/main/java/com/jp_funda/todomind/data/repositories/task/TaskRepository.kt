package com.jp_funda.todomind.data.repositories.task

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.notification.TaskReminder
import io.reactivex.rxjava3.core.Single
import io.realm.Realm
import io.realm.kotlin.where
import java.util.*
import javax.inject.Inject

@ExperimentalMaterialApi
class TaskRepository @Inject constructor(
    private val context: Context
) {

    // CREATE
    fun createTask(task: Task): Single<Task> {
        // set reminder
        task.dueDate?.let { TaskReminder.setTaskReminder(task, context) }

        return Single.create { emitter ->
            Realm.getDefaultInstance().use {
                it.executeTransactionAsync { realm ->
                    val maxReversedOrder =
                        realm.copyFromRealm(realm.where<Task>().findAll())
                            .maxWithOrNull(Comparator.comparingInt { lastTask ->
                                lastTask.reversedOrder ?: 0
                            })?.reversedOrder
                            ?: 0
                    task.reversedOrder = maxReversedOrder + 1
                    val now = Date()
                    task.createdDate = now
                    task.updatedDate = now
                    realm.insertOrUpdate(task)
                    emitter.onSuccess(task)
                }
            }
        }
    }

    fun restoreTask(task: Task): Single<Task> {
        // set reminder
        task.dueDate?.let { TaskReminder.setTaskReminder(task, context) }

        return Single.create { emitter ->
            Realm.getDefaultInstance().use {
                it.executeTransactionAsync { realm ->
                    task.updatedDate = Date()
                    realm.insertOrUpdate(task)
                    emitter.onSuccess(task)
                }
            }
        }
    }

    // READ
    fun getAllTasks(): Single<List<Task>> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().use {
                it.executeTransactionAsync { realm ->
                    val result1 = realm.where<Task>().findAll()
                    val result2 = Realm.getDefaultInstance().copyFromRealm(result1)
                    emitter.onSuccess(result2)
                }
            }
        }
    }

    fun getTask(id: UUID): Single<Task> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().use {
                it.executeTransactionAsync { realm ->
                    val result = realm.where<Task>().equalTo("id", id).findFirst()
                    if (result != null) {
                        val resultCopy = Realm.getDefaultInstance().copyFromRealm(result)
                        emitter.onSuccess(resultCopy)
                    } else {
                        emitter.onError(Exception("No data"))
                    }
                }
            }
        }
    }

    fun getTasksInAMindMap(mindMap: MindMap): Single<List<Task>> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().use {
                it.executeTransactionAsync { realm ->
                    val result1 = realm.where<Task>().equalTo("mindMap.id", mindMap.id).findAll()
                    val result2 = Realm.getDefaultInstance().copyFromRealm(result1)
                    emitter.onSuccess(result2)
                }
            }
        }
    }

    fun getTasksFilteredByStatus(status: TaskStatus): Single<List<Task>> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().use {
                it.executeTransactionAsync { realm ->
                    val result = realm.where<Task>().equalTo("status", status.state).findAll()
                    emitter.onSuccess(result)
                }
            }
        }
    }

    // UPDATE
    fun updateTask(updatedTask: Task): Single<Task> {
        // set reminder
        updatedTask.dueDate?.let { TaskReminder.setTaskReminder(updatedTask, context) }

        return Single.create { emitter ->
            Realm.getDefaultInstance().use {
                it.executeTransactionAsync { realm ->
                    updatedTask.updatedDate = Date()
                    realm.copyToRealmOrUpdate(updatedTask)
                    emitter.onSuccess(updatedTask)
                }
            }
        }
    }

    // DELETE
    fun deleteTask(task: Task): Single<Task> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().use {
                it.executeTransactionAsync { realm ->
                    val realmTask = realm.where<Task>().equalTo("id", task.id).findFirst()
                    realmTask?.let { deletingTask ->
                        deletingTask.deleteFromRealm()
                        emitter.onSuccess(task)
                    } ?: run {
                        emitter.onError(Throwable("Error at deleteTask"))
                    }
                }
            }
        }
    }
}