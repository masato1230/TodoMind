package com.jp_funda.todomind.data.repositories.mind_map.entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

open class MindMap(
    @PrimaryKey var id: UUID = UUID.randomUUID(),
    var title: String? = null,
    var description: String? = null,
    var createdDate: Date? = null,
    var updatedDate: Date? = null,
    var isCompleted: Boolean? = null,
) : RealmObject() {
    // Utils
    fun copy(): MindMap {
        return MindMap(
            id = id,
            title = title,
            description = description,
            createdDate = createdDate,
            updatedDate = updatedDate,
            isCompleted = isCompleted,
        )
    }

    companion object {
        val dateFormat = SimpleDateFormat("EEE MM/dd hh:mm aaa", Locale.US)
    }
}