package com.jp_funda.repositories.mind_map.entity

import androidx.core.graphics.toColor
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

open class MindMap(
    @PrimaryKey var id: UUID = UUID.randomUUID(),
    var title: String? = null,
    var description: String? = null,
    var createdDate: Date = Date(),
    var updatedDate: Date = Date(),
    var isCompleted: Boolean = false,
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

    override fun toString(): String {
        return "MindMap(" +
                "id = ${this.id}, " +
                "title = ${this.title}, " +
                "description = ${this.description}, " +
                "createdDate = ${this.createdDate}, " +
                "updatedDate = ${this.updatedDate}, " +
                "isCompleted = ${this.isCompleted}, " +
                "x = ${this.x}, " +
                "y = ${this.y}, " +
                "color = ${this.color}, " +
                ")"
    }
}