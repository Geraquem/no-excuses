package com.mmfsin.noexcuses.domain.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class MuscularGroup(
    @PrimaryKey
    var name: String = "",
) : RealmObject()
