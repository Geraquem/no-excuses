package com.mmfsin.noexcuses.presentation.dayexercises

import com.mmfsin.noexcuses.domain.models.RealmExercise

interface DayExercisesView {
    fun getDayExercises(exercises: List<RealmExercise>)
    fun dayExerciseDeleted()
    fun sww()
}