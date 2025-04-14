package com.mmfsin.noexcuses.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MaximumDataDTO(
    @PrimaryKey
    var id: String = "",
    var exerciseId: String = "",
    var weight: Double = 0.0,
    var date: String = "",
) : RealmObject()
