package com.jp_funda.todomind.domain.use_cases.mind_map

import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.domain.repositories.MindMapRepository
import javax.inject.Inject

class GetMostRecentlyUpdatedMindMapUseCase @Inject constructor(
    private val repository: MindMapRepository,
) {
    suspend operator fun invoke(): MindMap? {
        return repository.getMostRecentlyUpdatedMindMap()
    }
}