package com.jp_funda.todomind.data.repositories.task

import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.data.repositories.task.entity.Task
import com.jp_funda.todomind.data.repositories.task.entity.TaskStatus
import io.reactivex.rxjava3.core.Single
import io.realm.Realm
import io.realm.kotlin.where
import javax.inject.Inject

class TaskRepository @Inject constructor() {

    // CREATE
    fun createTask(task: Task): Single<Task> {
        return Single.create<Task> { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync { realm ->
                realm.insert(task)
                emitter.onSuccess(task)
            }
        }
    }

    // READ
    fun getAllTasks(): Single<List<Task>> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync { realm ->
                val result1 = realm.where<Task>().findAll()
                val result2 = Realm.getDefaultInstance().copyFromRealm(result1)
                emitter.onSuccess(result2)
            }
        }
    }

    fun getTasksInAMindMap(mindMap: MindMap): Single<List<Task>> {
        var result = emptyList<Task>()
        return Single.create { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync({ realm ->
                result = realm.where<Task>().equalTo("mindmap.id", mindMap.id).findAll()
            }, {
                emitter.onSuccess(result)
            }, {
                emitter.onError(it)
            })
        }
    }

    fun getTasksFilteredByStatus(status: TaskStatus): Single<List<Task>> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync { realm ->
                val result = realm.where<Task>().equalTo("status", status.state).findAll()
                emitter.onSuccess(result)
            }
        }
    }

    // UPDATE
    fun updateTask(updatedTask: Task): Single<Task> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync { realm ->
                realm.copyToRealmOrUpdate(updatedTask)
                emitter.onSuccess(updatedTask)
            }
        }
    }

    // DELETE
    fun deleteTask(task: Task): Single<Task> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync { realm ->
                task.deleteFromRealm()
                emitter.onSuccess(task)
            }
        }
    }
}