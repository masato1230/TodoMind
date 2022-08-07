package com.jp_funda.todomind.domain.use_cases.mind_map

import com.jp_funda.todomind.database.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.domain.repositories.MindMapRepository
import java.util.*
import javax.inject.Inject

class UpdateMindMapUseCase @Inject constructor(private val repository: MindMapRepository) {
    suspend operator fun invoke(mindMap: MindMap) {
        mindMap.updatedDate = Date()
        repository.updateMindMap(mindMap)
    }
}