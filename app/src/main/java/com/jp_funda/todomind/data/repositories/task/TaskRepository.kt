package com.jp_funda.todomind.data.repositories.task

import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.data.repositories.task.entity.Task
import io.reactivex.rxjava3.core.Single
import io.realm.Realm
import io.realm.kotlin.where
import javax.inject.Inject

class TaskRepository @Inject constructor() {

    fun createTask(task: Task): Single<Task> {
        return Single.create<Task> { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync({ realm ->
                realm.insert(task)
            }, {
                emitter.onSuccess(task)
            }, { error ->
                emitter.onError(error)
            })
        }
    }

    fun getAllTasks(): Single<List<Task>> {
        var result = emptyList<Task>()
        return Single.create { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync({ realm ->
                result = realm.where<Task>().findAll()
            }, {
                emitter.onSuccess(result)
            }, { error ->
                emitter.onError(error)
            })
        }
    }

    fun getTasksInAMindMap(mindMap: MindMap): Single<List<Task>> {
        var result = emptyList<Task>()
        return Single.create { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync({ realm ->
                result = realm.where<Task>().findAll()
            }, {
                emitter.onSuccess(result)
            }, {
                emitter.onError(it)
            })
        }
    }

    fun updateTask(updatedTask: Task): Single<Task> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync({ realm ->
                realm.copyToRealmOrUpdate(updatedTask)
            }, {
                emitter.onSuccess(updatedTask)
            }, {
                emitter.onError(it)
            })
        }
    }

    fun deleteTask(task: Task): Single<Task> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync({ realm ->
                task.deleteFromRealm()
            }, {
                emitter.onSuccess(task)
            }, {
                emitter.onError(it)
            })
        }
    }
}