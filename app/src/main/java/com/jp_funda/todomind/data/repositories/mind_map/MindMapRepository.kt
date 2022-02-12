package com.jp_funda.todomind.data.repositories.mind_map

import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import io.reactivex.rxjava3.core.Single
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import java.util.*
import javax.inject.Inject

class MindMapRepository @Inject constructor() {

    // CREATE
    fun createMindMap(mindMap: MindMap): Single<MindMap> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync({ realm ->
                val now = Date()
                mindMap.createdDate = now
                mindMap.updatedDate = now
                realm.insert(mindMap)
            }, {
                emitter.onSuccess(mindMap)
            }, {
                emitter.onError(it)
            })
        }
    }

    // READ
    fun getAllMindMaps(): Single<List<MindMap>> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync { realm ->
                val result1 = realm.where<MindMap>()
                    .findAll()
                    .sort("createdDate", Sort.DESCENDING)
                val result2 = Realm.getDefaultInstance().copyFromRealm(result1)
                emitter.onSuccess(result2)
            }
        }
    }

    fun getMostRecentlyUpdatedMindMap(): Single<MindMap> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync { realm ->
                val result = realm.where<MindMap>()
                    .findAll()
                    .sort("createdDate", Sort.DESCENDING)
                    .first()
                result?.let {
                    emitter.onSuccess(Realm.getDefaultInstance().copyFromRealm(result))
                }
            }
        }
    }

    // UPDATE
    fun updateMindMap(updatedMindMap: MindMap): Single<MindMap> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync({ realm ->
                updatedMindMap.updatedDate = Date()
                realm.copyToRealmOrUpdate(updatedMindMap)
            }, {
                emitter.onSuccess(updatedMindMap)
            }, {
                emitter.onError(it)
            })
        }
    }

    // DELETE
    fun deleteMindMap(mindMap: MindMap): Single<MindMap> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync { realm ->
                val realmMindMap = realm.where<MindMap>().equalTo("id", mindMap.id).findFirst()
                realmMindMap?.let {
                    it.deleteFromRealm()
                    emitter.onSuccess(mindMap)
                } ?: run {
                    emitter.onError(Throwable("Error at delete mindMap"))
                }
            }
        }
    }
}