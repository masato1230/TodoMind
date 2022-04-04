package com.jp_funda.todomind.data.repositories.task.entity

import androidx.core.graphics.toColor
import com.jp_funda.todomind.data.NodeStyle
import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

enum class TaskStatus(val state: String) {
    InProgress("InProgress"),
    Open("Open"),
    Complete("Complete"),
}

open class Task(
    // General fields
    @PrimaryKey var id: UUID = UUID.randomUUID(),
    var mindMap: MindMap? = null,
    var title: String? = null,
    var description: String? = null,
    var createdDate: Date = Date(),
    var updatedDate: Date = Date(),

    // To-Do list fields
    var dueDate: Date? = null,
    // display task in this order at TO-Do list, this field should be always filled at create
    var reversedOrder: Int? = null,

    // Mind Map fields
    var x: Float? = null,
    var y: Float? = null,
    var parentTask: Task? = null,
    var color: Int? = null, // Color Argb int

    styleEnum: NodeStyle = NodeStyle.HEADLINE_2,
    statusEnum: TaskStatus = TaskStatus.InProgress,
) : RealmObject() {
    private var status: String = statusEnum.name
    var statusEnum: TaskStatus
        get() {
            // default the state to "Open" if the state is unreadable
            return try {
                TaskStatus.valueOf(status)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
                TaskStatus.InProgress
            }
        }
        set(value) {
            // users set state using a FrogState, but it is saved as a string internally
            status = value.state
        }

    private var style: String = styleEnum.name
    var styleEnum: NodeStyle
        get() {
            return NodeStyle.valueOf(style)
        }
        set(value) {
            style = value.name
        }

    // Utils

    fun copy(): Task {
        return Task(
            id = id,
            mindMap = mindMap,
            title = title,
            description = description,
            createdDate = createdDate,
            updatedDate = updatedDate,
            dueDate = dueDate,
            reversedOrder = reversedOrder,
            x = x,
            y = y,
            parentTask = parentTask,
            color = color,
            styleEnum = styleEnum,
            statusEnum = statusEnum,
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
        return "Task(" +
                "id = ${this.id}, " +
                "mindMap_id = ${this.mindMap?.id} ,\n" +
                "title = ${this.title},\n " +
                "description = ${this.description},\n " +
                "createdDate = ${this.createdDate},\n " +
                "updatedDate = ${this.updatedDate},\n " +
                "dueDate = ${this.dueDate},\n " +
                "reversedOrder = ${this.reversedOrder},\n " +
                "x = ${this.x},\n " +
                "y = ${this.y},\n " +
                "parentTask_id = ${this.parentTask?.id},\n " +
                "color = ${this.color},\n " +
                "styleEnum = ${this.styleEnum},\n " +
                "statusEnum = ${this.statusEnum},\n " +
                ")"
    }
}