package com.mmfsin.noexcuses.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RoutineDTO(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var description: String? = null,
    var days: String = "",
) : RealmObject()
