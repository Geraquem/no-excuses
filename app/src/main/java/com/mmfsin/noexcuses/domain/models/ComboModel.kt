package com.mmfsin.noexcuses.domain.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ComboModel(
    @PrimaryKey
    var id: String = "",
    var dayId: String = "",
    var exerciseId: String = "",
//    var order: Long,
) : RealmObject()
