package com.mmfsin.noexcuses.data.repository

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.noexcuses.data.database.RealmDatabase
import com.mmfsin.noexcuses.domain.interfaces.IExercises
import com.mmfsin.noexcuses.domain.models.RealmExercise

class FirebaseRepository(val listener: IExercises) {

    private val realm by lazy { RealmDatabase() }

    fun getExercisesFromFirebase() {
        Firebase.database.reference.child("ejercicios").get().addOnSuccessListener {
            for (muscularName in it.children) {
                for (exercise in muscularName.children) {
                    exercise.getValue(RealmExercise::class.java)?.let { ex -> save(ex) }
                }
            }
            listener.retrievedFromFirebase(true)
        }.addOnFailureListener {
            listener.retrievedFromFirebase(false)
        }
    }

    private fun save(realmExercise: RealmExercise): Boolean = realm.addObject { realmExercise }

}