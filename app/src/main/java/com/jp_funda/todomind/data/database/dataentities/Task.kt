package com.jp_funda.todomind.data.database.dataentities

import io.realm.RealmObject
import io.realm.annotations.Index
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
    // TODO add mind map realm object
    var title: String,
    var description: String,
    var createdDate: Date,
    var updatedDate: Date,

    // To-Do list fields
    var dueDate: Date,

    // Mind Map fields
    var hierarchy: Int,
    var parentTask: Task? = null,
    var positionNumber: Int,

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