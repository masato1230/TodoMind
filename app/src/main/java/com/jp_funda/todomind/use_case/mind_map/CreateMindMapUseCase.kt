package com.jp_funda.todomind.use_case.mind_map

import com.jp_funda.repositories.mind_map.entity.MindMap
import com.jp_funda.repositories.mind_map.MindMapRepository
import java.util.*
import javax.inject.Inject

class CreateMindMapUseCase @Inject constructor(
    private val repository: MindMapRepository,
) {
    suspend operator fun invoke(mindMap: MindMap) {
        val now = Date()
        mindMap.createdDate = now
        mindMap.updatedDate = now
        mindMap.x = mindMap.x ?: 100f
        mindMap.y = mindMap.y ?: 100f

        repository.insertMindMap(mindMap)
    }
}