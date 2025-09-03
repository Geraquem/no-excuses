package com.mmfsin.noexcuses.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class DayDTO : RealmObject {
    @PrimaryKey
    var id: String = ""
    var routineId: String = ""
    var title: String = ""
    var exercises: Int = 0
}
