package com.mmfsin.noexcuses.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MaximumDataDTO(
    @PrimaryKey
    var id: String = "",
    var exerciseId: String = "",
    var weight: String = "",
    var date: String = "",
) : RealmObject()
