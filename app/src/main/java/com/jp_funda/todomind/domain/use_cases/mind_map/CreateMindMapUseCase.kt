package com.jp_funda.todomind.domain.use_cases.mind_map

import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.domain.repositories.MindMapRepository
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