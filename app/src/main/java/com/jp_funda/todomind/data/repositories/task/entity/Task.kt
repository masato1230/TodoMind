package com.jp_funda.todomind.data.repositories.task.entity

import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

enum class TaskStatus(val state: String) {
    Open("Open"),
    InProgress("In Progress"),
    Complete("Complete"),
}

open class Task(
    // General fields
    @PrimaryKey var id: UUID = UUID.randomUUID(),
    var mindMap: MindMap? = null,
    var title: String? = null,
    var description: String? = null,
    var createdDate: Date? = null,
    var updatedDate: Date? = null,

    // To-Do list fields
    var dueDate: Date? = null,

    // Mind Map fields
    var hierarchy: Int? = null,
    var parentTask: Task? = null,
    var positionNumber: Int? = null,

    ) : RealmObject() {
    private var status: String = TaskStatus.Open.state
    var statusEnum: TaskStatus
        get() {
            // default the state to "Open" if the state is unreadable
            return try {
                TaskStatus.valueOf(status)
            } catch (e: IllegalArgumentException) {
                TaskStatus.Open
            }
        }
        set(value) {
            // users set state using a FrogState, but it is saved as a string internally
            status = value.state
        }
    }