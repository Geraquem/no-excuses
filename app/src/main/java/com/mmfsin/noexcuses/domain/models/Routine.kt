package com.mmfsin.noexcuses.domain.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Routine(
    @PrimaryKey
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var days: Int = 0,
) : RealmObject()
