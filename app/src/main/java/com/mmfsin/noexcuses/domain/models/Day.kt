package com.mmfsin.noexcuses.domain.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Day(
    @PrimaryKey
    var id: String = "",
    var phaseId : String = "",
    var name: String = ""
) : RealmObject()
