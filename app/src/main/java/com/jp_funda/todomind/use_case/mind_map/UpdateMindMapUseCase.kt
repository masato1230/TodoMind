package com.jp_funda.todomind.use_case.mind_map

import com.jp_funda.repositories.mind_map.entity.MindMap
import com.jp_funda.repositories.mind_map.MindMapRepository
import java.util.*
import javax.inject.Inject

class UpdateMindMapUseCase @Inject constructor(private val repository: MindMapRepository) {
    suspend operator fun invoke(mindMap: MindMap) {
        mindMap.updatedDate = Date()
        repository.updateMindMap(mindMap)
    }
}