package com.jp_funda.todomind.data.repositories.mind_map

import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.data.repositories.task.entity.Task
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
            Realm.getDefaultInstance().use {
                it.executeTransactionAsync({ realm ->
                    val now = Date()
                    mindMap.createdDate = now
                    mindMap.updatedDate = now
                    mindMap.x = mindMap.x ?: 100f
                    mindMap.y = mindMap.y ?: 100f
                    realm.insertOrUpdate(mindMap)
                }, {
                    emitter.onSuccess(mindMap)
                }, { e ->
                    emitter.onError(e)
                })
            }
        }
    }

    // READ
    fun getAllMindMaps(): Single<List<MindMap>> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().use {
                it.executeTransactionAsync { realm ->
                    val result1 = realm.where<MindMap>()
                        .findAll()
                        .sort("createdDate", Sort.DESCENDING)
                    val result2 = Realm.getDefaultInstance().copyFromRealm(result1)
                    emitter.onSuccess(result2)
                }
            }
        }
    }

    fun getMostRecentlyUpdatedMindMap(): Single<MindMap> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().use {
                it.executeTransactionAsync { realm ->
                    try {
                        val result = realm.where<MindMap>()
                            .equalTo("isCompleted", false)
                            .findAll()
                            .sort("updatedDate", Sort.DESCENDING)
                            .first()
                        result?.let {
                            emitter.onSuccess(Realm.getDefaultInstance().copyFromRealm(result))
                        } ?: run {
                            emitter.onError(Throwable("No data"))
                        }
                    } catch (e: Exception) {
                        emitter.onError(e)
                    }
                }
            }
        }
    }

    // UPDATE
    fun updateMindMap(updatedMindMap: MindMap): Single<MindMap> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().use {
                it.executeTransactionAsync({ realm ->
                    updatedMindMap.updatedDate = Date()
                    realm.copyToRealmOrUpdate(updatedMindMap)
                }, {
                    emitter.onSuccess(updatedMindMap)
                }, { e ->
                    emitter.onError(e)
                })
            }
        }
    }

    // DELETE
    fun deleteMindMap(mindMap: MindMap): Single<MindMap> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().use {
                it.executeTransactionAsync { realm ->
                    val realmMindMap = realm.where<MindMap>().equalTo("id", mindMap.id).findFirst()
                    realmMindMap?.let { deletingMindMap ->
                        deleteTasksInAMindMap(realm, deletingMindMap)
                        deletingMindMap.deleteFromRealm()
                        emitter.onSuccess(mindMap)
                    } ?: run {
                        emitter.onError(Throwable("Error at delete mindMap"))
                    }
                }
            }
        }
    }

    private fun deleteTasksInAMindMap(realm: Realm, mindMap: MindMap) {
        realm.where<Task>().equalTo("mindMap.id", mindMap.id).findAll().deleteAllFromRealm()
    }
}