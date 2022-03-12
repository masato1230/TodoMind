package com.jp_funda.todomind.data.repositories.mind_map.entity

import androidx.core.graphics.toColor
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
    // for mind map create
    var x: Float? = null,
    var y: Float? = null,
    var color: Int? = null, // Argb Color
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
            x = x,
            y = y,
            color = color,
        )
    }

    companion object {
        val dateFormat = SimpleDateFormat("EEE MM/dd hh:mm aaa", Locale.US)
    }

    val colorHex: String?
        get() {
            if (color != null) {
                return String.format("#%06X", 0xFFFFFF and color!!.toColor().toArgb())
            }
            return null
        }
}