package com.mmfsin.noexcuses.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DefaultRoutineDTO(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var days: Long = 0,
    var doingIt: Boolean = false,
    var createdByUser: Boolean = false
) : RealmObject()

open class DefaultDayDTO(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var routineId: String = "",
    var exercises: Long = 0
) : RealmObject()


open class DefaultExerciseDTO(
    @PrimaryKey
    var id: String = "",
    var dayId: String = "",
    var exerciseId: String = "",
    var desc: String = "",
    var reps: String = "",
    var superSerie: Boolean = false
) : RealmObject()

