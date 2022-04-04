package com.jp_funda.todomind.data.repositories.task

import android.content.Context
import android.util.Log
import androidx.compose.material.ExperimentalMaterialApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import com.jp_funda.todomind.notification.TaskReminder
import io.reactivex.rxjava3.core.Single
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import java.util.*
import javax.inject.Inject

@ExperimentalPagerApi
@ExperimentalMaterialApi
class TaskRepository @Inject constructor(
    private val context: Context
) {

    // CREATE
    fun createTask(task: Task): Single<Task> {
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

                    // set Reminder
                    setNextReminder(realm)
                }
            }
        }
    }

    fun createAll(tasks: List<Task>): Single<Boolean> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().use {
                it.executeTransactionAsync { realm ->
                    for (task in tasks) {
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

                        // set Reminder
                    }
                    emitter.onSuccess(true)
                    setNextReminder(realm)
                }
            }
        }
    }

    fun restoreTask(task: Task): Single<Task> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().use {
                it.executeTransactionAsync { realm ->
                    task.updatedDate = Date()
                    realm.insertOrUpdate(task)
                    emitter.onSuccess(task)

                    // set Reminder
                    setNextReminder(realm)
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
                        val resultCopy = realm.copyFromRealm(result)
                        emitter.onSuccess(resultCopy)
                    } else {
                        emitter.onError(Exception("No data"))
                    }
                }
            }
        }
    }

    fun getNextRemindTask(lastRemindedTask: Task?): Single<Task> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().use {
                it.executeTransactionAsync { realm ->
                    val date = Date()
                    date.minutes -= 1
                    val result = realm.where<Task>()
                        .greaterThan("dueDate", date)
                        .greaterThan("updatedDate", lastRemindedTask?.updatedDate ?: Date(0))
                        .findFirst()
                    if (result == null) emitter.onError(Throwable("No task to remind")) else {
                        val copy = realm.copyFromRealm(result)
                        emitter.onSuccess(copy)
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
        return Single.create { emitter ->
            Realm.getDefaultInstance().use {
                it.executeTransactionAsync { realm ->
                    updatedTask.updatedDate = Date()
                    realm.copyToRealmOrUpdate(updatedTask)
                    emitter.onSuccess(updatedTask)

                    // set Reminder
                    setNextReminder(realm)
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