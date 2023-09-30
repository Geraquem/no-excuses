package com.mmfsin.noexcuses.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ChExerciseDTO(
    @PrimaryKey
    var id: String = "",
    var routineId: String = "",
    var dayId: String = "",
    var exerciseId: String? = null,
    var weight: Double? = null,
    var series: Int? = null,
    var reps: Int? = null,
    var notes: String? = null,
) : RealmObject()
