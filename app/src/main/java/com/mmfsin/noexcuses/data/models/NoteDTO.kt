package com.mmfsin.noexcuses.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class NoteDTO(
    @PrimaryKey
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var date: Long = 0
) : RealmObject()
