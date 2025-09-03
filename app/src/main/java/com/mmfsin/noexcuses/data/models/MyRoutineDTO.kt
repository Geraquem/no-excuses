package com.mmfsin.noexcuses.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class MyRoutineDTO : RealmObject {
    @PrimaryKey
    var id: String = ""
    var title: String = ""
    var description: String? = null
    var days: Int = 0
    var doingIt: Boolean = false
    var createdByUser: Boolean = true
}
