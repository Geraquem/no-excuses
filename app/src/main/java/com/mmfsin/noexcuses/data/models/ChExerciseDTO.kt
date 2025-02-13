package com.mmfsin.noexcuses.data.models

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ChExerciseDTO(
    @PrimaryKey
    var id: String = "",
    var routineId: String = "",
    var dayId: String = "",
    var exerciseId: String? = null,
    var data: RealmList<DataDTO>? = null,
    var time: Double? = null,
    var notes: String? = null,
    var position: Int = 0,
    var superSerie: Boolean = false
) : RealmObject()
