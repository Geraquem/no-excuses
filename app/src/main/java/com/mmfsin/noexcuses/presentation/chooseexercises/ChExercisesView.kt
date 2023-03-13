package com.mmfsin.noexcuses.presentation.chooseexercises

import com.mmfsin.noexcuses.domain.models.RealmExercise

interface ChExercisesView {
    fun getExercises(realmExercises: List<RealmExercise>)
    fun savedExerciseInDay(result: Boolean)
    fun sww()
}