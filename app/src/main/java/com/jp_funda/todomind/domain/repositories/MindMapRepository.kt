package com.jp_funda.todomind.domain.repositories

import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import java.util.*

interface MindMapRepository {
    suspend fun insertMindMap(mindMap: MindMap)

    suspend fun getMindMap(id: UUID): MindMap?

    suspend fun getAllMindMaps(): List<MindMap>

    suspend fun getMostRecentlyUpdatedMindMap(): MindMap?

    suspend fun updateMindMap(mindMap: MindMap)

    suspend fun deleteMindMap(mindMap: MindMap)
}