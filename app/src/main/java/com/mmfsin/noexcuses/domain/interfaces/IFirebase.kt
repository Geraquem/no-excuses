package com.mmfsin.noexcuses.domain.interfaces

interface IFirebase {
    fun retrievedMGroupsFromFirebase(result: Boolean)
    fun retrievedExercisesFromFirebase(result: Boolean)
}