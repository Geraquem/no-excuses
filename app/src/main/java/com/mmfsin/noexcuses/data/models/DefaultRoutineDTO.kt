package com.mmfsin.noexcuses.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DefaultRoutineDTO(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var days: Long = 0
) : RealmObject()

open class DefaultDayDTO(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var routineId: String = ""
) : RealmObject()

