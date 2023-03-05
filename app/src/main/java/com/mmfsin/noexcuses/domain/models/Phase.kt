package com.mmfsin.noexcuses.domain.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Phase(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var description: String? = null
) : RealmObject()
