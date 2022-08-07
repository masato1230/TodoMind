package com.jp_funda.repositories.mind_map

import com.jp_funda.repositories.mind_map.entity.MindMap
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import java.util.*

class MindMapRepositoryImpl : MindMapRepository {
    // CREATE
    override suspend fun insertMindMap(mindMap: MindMap) {
        Realm.getDefaultInstance().use {
            it.executeTransaction { realm ->
                realm.insertOrUpdate(mindMap)
            }
        }
    }

    // READ
    override suspend fun getMindMap(id: UUID): MindMap? {
        Realm.getDefaultInstance().use {
            var mindMap: MindMap? = null
            it.executeTransaction { realm ->
                val result = realm.where<MindMap>().equalTo("id", id).findFirst()
                mindMap = result?.let { resultMindMap -> realm.copyFromRealm(resultMindMap) }
            }
            return mindMap
        }
    }

    override suspend fun getAllMindMaps(): List<MindMap> {
        Realm.getDefaultInstance().use {
            var mindMaps = emptyList<MindMap>()
            it.executeTransaction { realm ->
                val result = realm.where<MindMap>()
                    .findAll()
                    .sort("createdDate", Sort.DESCENDING)
                mindMaps = Realm.getDefaultInstance().copyFromRealm(result)
            }
            return mindMaps
        }
    }

    override suspend fun getMostRecentlyUpdatedMindMap(): MindMap? {
        Realm.getDefaultInstance().use {
            var mindMap: MindMap? = null
            it.executeTransaction { realm ->
                val result = realm.where<MindMap>()
                    .equalTo("isCompleted", false)
                    .findAll()
                    .sort("updatedDate", Sort.DESCENDING)
                    .firstOrNull()
                mindMap = result?.let { found -> realm.copyFromRealm(found) }
            }
            return mindMap
        }
    }

    // UPDATE
    override suspend fun updateMindMap(mindMap: MindMap) {
        Realm.getDefaultInstance().use {
            it.executeTransaction { realm ->
                realm.copyToRealmOrUpdate(mindMap)
            }
        }
    }

    // Delete
    override suspend fun deleteMindMap(mindMap: MindMap) {
        Realm.getDefaultInstance().use {
            it.executeTransaction { realm ->
                val realmMindMap = realm.where<MindMap>().equalTo("id", mindMap.id).findFirst()
                realmMindMap?.deleteFromRealm()
            }
        }
    }
}