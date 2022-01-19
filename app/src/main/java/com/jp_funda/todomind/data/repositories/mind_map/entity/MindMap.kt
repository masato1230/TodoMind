package com.jp_funda.todomind.data.repositories.mind_map.entity

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