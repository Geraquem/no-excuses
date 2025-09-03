package com.mmfsin.noexcuses.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class DataDTO : RealmObject {
    @PrimaryKey
    var id: String? = null
    var exerciseDayId: String? = null
    var reps: Int? = null
    var weight: Double? = null
}