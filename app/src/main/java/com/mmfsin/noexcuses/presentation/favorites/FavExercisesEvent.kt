package com.mmfsin.noexcuses.presentation.favorites

import com.mmfsin.noexcuses.domain.models.Exercise

sealed class FavExercisesEvent {
    class GetFavExercises(val exercises: List<Exercise>) : FavExercisesEvent()
    object SWW : FavExercisesEvent()
}