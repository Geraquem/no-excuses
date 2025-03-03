package com.mmfsin.noexcuses.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MuscularGroupDTO(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var manImageURL: String = "",
    var womanImageURL: String = "",
    var isManSelected: Boolean = true,
    var order: Long = 0,
) : RealmObject()
