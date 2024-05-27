package com.mmfsin.noexcuses.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class FavExerciseDTO(
    @PrimaryKey
    var id: String = "",
    var exerciseId: String? = null,
) : RealmObject()
