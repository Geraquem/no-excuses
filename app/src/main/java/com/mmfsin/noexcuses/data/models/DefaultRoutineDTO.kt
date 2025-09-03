package com.mmfsin.noexcuses.data.models

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

open class DefaultRoutineDTO : RealmObject {
    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var description: String = ""
    var days: Long = 0
    var doingIt: Boolean = false
    var createdByUser: Boolean = false
}

open class DefaultDayDTO : RealmObject {
    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var routineId: String = ""
    var exercises: Long = 0
}


open class DefaultExerciseDTO : RealmObject {
    @PrimaryKey
    var id: String = ""
    var dayId: String = ""
    var exerciseId: String = ""
    var desc: String = ""
    var reps: String = ""
    var superSerie: Boolean = false
}

