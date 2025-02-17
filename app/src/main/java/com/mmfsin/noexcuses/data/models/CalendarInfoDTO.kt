package com.mmfsin.noexcuses.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CalendarInfoDTO(
    @PrimaryKey
    var id: String = "",
    var date: String = ""
) : RealmObject()
