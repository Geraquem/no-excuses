package com.mmfsin.noexcuses.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DataDTO(
    @PrimaryKey
    var id: String? = null,
    var reps: Int? = null,
    var weight: Double? = null
) : RealmObject()