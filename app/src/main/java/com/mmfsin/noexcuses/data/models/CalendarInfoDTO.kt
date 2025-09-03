package com.mmfsin.noexcuses.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class CalendarInfoDTO : RealmObject {
    @PrimaryKey
    var id: String = ""
    var date: String = ""
    var dayId: String = ""
    var routineId: String = ""
}
