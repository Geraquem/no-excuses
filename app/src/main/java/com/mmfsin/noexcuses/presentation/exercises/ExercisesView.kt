package com.mmfsin.noexcuses.presentation.exercises

import com.mmfsin.noexcuses.domain.models.RealmExercise

interface ExercisesView {
    fun getExecises(realmExercises: List<RealmExercise>)
    fun sww()
}