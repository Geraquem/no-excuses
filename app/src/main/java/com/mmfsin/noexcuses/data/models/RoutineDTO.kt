package com.mmfsin.noexcuses.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RoutineDTO(
    @PrimaryKey
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var days: Int = 0,
) : RealmObject()
