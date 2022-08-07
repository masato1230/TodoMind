package com.jp_funda.todomind.domain.use_cases.mind_map

import com.jp_funda.todomind.database.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.domain.repositories.MindMapRepository
import javax.inject.Inject

// TODO add sort functionality
class GetAllMindMapsUseCase @Inject constructor(private val repository: MindMapRepository) {
    suspend operator fun invoke(): List<MindMap> {
        return repository.getAllMindMaps()
    }
}