package com.jp_funda.todomind.data.repositories

import com.jp_funda.todomind.data.database.dataentities.Task
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single
import io.realm.Realm
import io.realm.kotlin.where

class TaskRepository {

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
}