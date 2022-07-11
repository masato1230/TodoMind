package com.jp_funda.todomind.domain.repositories

import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap

interface MindMapRepository {
    suspend fun insertMindMap(mindMap: MindMap)

    suspend fun getAllMindMaps(): List<MindMap>

    suspend fun getMostRecentlyUpdatedMindMap(): MindMap?
}