package com.jp_funda.todomind.domain.use_cases.mind_map

import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import com.jp_funda.todomind.domain.repositories.MindMapRepository
import java.util.*
import javax.inject.Inject

class GetMindMapUseCase @Inject constructor(private val repository: MindMapRepository) {
    suspend operator fun invoke(id: UUID): MindMap? {
        return repository.getMindMap(id)
    }
}