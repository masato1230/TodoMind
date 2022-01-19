package com.jp_funda.todomind.data.repositories.mind_map

import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import io.reactivex.rxjava3.core.Single
import io.realm.Realm
import io.realm.kotlin.where

class MindMapRepository {

    fun createMindMap(mindMap: MindMap): Single<MindMap> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync({ realm ->
                realm.insert(mindMap)
            }, {
                emitter.onSuccess(mindMap)
            }, {
                emitter.onError(it)
            })
        }
    }

    fun getAllMindMaps(): Single<List<MindMap>> {
        var result = emptyList<MindMap>()
        return Single.create { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync({ realm ->
                result = realm.where<MindMap>().findAll()
            }, {
                emitter.onSuccess(result)
            }, {
                emitter.onError(it)
            })
        }
    }

    fun updateMindMap(updatedMindMap: MindMap): Single<MindMap> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync({ realm ->
                realm.copyToRealmOrUpdate(updatedMindMap)
            }, {
                emitter.onSuccess(updatedMindMap)
            }, {
                emitter.onError(it)
            })
        }
    }
    
    fun deleteMindMap(mindMap: MindMap): Single<MindMap> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync({ realm ->
                mindMap.deleteFromRealm()
            }, {
                emitter.onSuccess(mindMap)
            }, {
                emitter.onError(it)
            })
        }
    }
}