package com.jp_funda.todomind.data.repositories

import com.jp_funda.todomind.data.database.dataentities.Task
import io.reactivex.rxjava3.core.Single
import io.realm.Realm
import io.realm.kotlin.where

class TaskRepository {

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