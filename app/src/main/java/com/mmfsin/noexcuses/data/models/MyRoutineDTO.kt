package com.mmfsin.noexcuses.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MyRoutineDTO(
    @PrimaryKey
    var id: String = "",
    var title: String = "",
    var description: String? = null,
    var days: Int = 0,
    var doingIt: Boolean = false,
    var createdByUser: Boolean = true
) : RealmObject()
