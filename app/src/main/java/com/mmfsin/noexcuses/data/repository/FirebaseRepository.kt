package com.mmfsin.noexcuses.data.repository

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.noexcuses.data.database.RealmDatabase
import com.mmfsin.noexcuses.domain.interfaces.IExercises
import com.mmfsin.noexcuses.domain.models.CompleteExercise
import com.mmfsin.noexcuses.domain.models.RealmExercise

class FirebaseRepository(private val listener: IExercises) {

    private val realm by lazy { RealmDatabase() }

    fun getExercisesFromFirebase() {
        Firebase.database.reference.child("ejercicios").get().addOnSuccessListener {
            for (muscularName in it.children) {
                for (exercise in muscularName.children) {
                    exercise.getValue(RealmExercise::class.java)?.let { ex -> save(ex) }
                }
            }
            listener.retrievedExercisesFromFirebase(true)
        }.addOnFailureListener {
            listener.retrievedExercisesFromFirebase(false)
        }
    }

    fun getExerciseFromFirebase(exercise: RealmExercise) {
        Firebase.database.reference.child("ejercicios")
            .child(exercise.category).child(exercise.id)
            .get().addOnSuccessListener {
                it.getValue(CompleteExercise::class.java)?.let { exercise ->
                    listener.retrievedSingleExercise(exercise)
                }
            }.addOnFailureListener {
                listener.retrievedSingleExercise(null)
            }
    }

    private fun save(realmExercise: RealmExercise): Boolean = realm.addObject { realmExercise }

}