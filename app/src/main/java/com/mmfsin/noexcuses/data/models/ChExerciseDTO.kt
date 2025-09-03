package com.mmfsin.noexcuses.data.models

import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class ChExerciseDTO: RealmObject {
    @PrimaryKey
    var id: String = ""
    var routineId: String = ""
    var dayId: String = ""
    var exerciseId: String? = null
    var data: RealmList<DataDTO>?
    var time: Double? = null
    var notes: String? = null
    var position: Int = 0
    var superSerie: Boolean = false
}
