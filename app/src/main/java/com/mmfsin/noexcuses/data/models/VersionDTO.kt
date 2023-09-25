package com.mmfsin.noexcuses.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class VersionDTO(
    @PrimaryKey
    var id: String = "",
    var version: Long = 0,
) : RealmObject()
