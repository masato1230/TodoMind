package com.jp_funda.todomind.data.database.dataentities

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class MindMap(
    @PrimaryKey var id: UUID = UUID.randomUUID(),
    var title: String,
    var description: String?,
    var createdDate: Date,
    var updatedDate: Date,
    var isCompleted: Boolean,
) : RealmObject() {
}