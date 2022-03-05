package com.jp_funda.todomind.data.repositories.task.entity

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
    var createdDate: Date? = Date(),
    var updatedDate: Date? = Date(),

    // To-Do list fields
    var dueDate: Date? = null,
    // display task in this order at TO-Do list, this field should be always filled at create
    var reversedOrder: Int? = null,

    // Mind Map fields
    var x: Float? = null,
    var y: Float? = null,
    var parentTask: Task? = null,
    var color: Int? = null, // Color Argb int

    styleEnum: NodeStyle? = NodeStyle.HEADLINE_1,
    statusEnum: TaskStatus? = TaskStatus.InProgress,
) : RealmObject() {
    private var status: String = statusEnum?.name ?: TaskStatus.InProgress.name
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

    private var style: String = styleEnum?.name ?: NodeStyle.HEADLINE_1.name
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
}