package com.mmfsin.noexcuses.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CalendarInfoDTO(
    @PrimaryKey
    var id: String = "",
    var day: Int = 0,
    var month: Int = 0,
    var year: Int = 0,
) : RealmObject()
