package com.mmfsin.noexcuses.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MuscularGroupsDTO(
    @PrimaryKey
    var name: String = "",
    var imageURL: String = "",
    var order: Long = 0,
) : RealmObject()
