package com.mmfsin.noexcuses.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DayDTO(
    @PrimaryKey
    var id: String = "",
    var routineId: String = "",
    var title: String = "",
    var exercises: Int = 0
) : RealmObject()
