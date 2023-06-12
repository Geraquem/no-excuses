package com.mmfsin.noexcuses.data.repository

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.noexcuses.data.database.RealmDatabase
import com.mmfsin.noexcuses.domain.interfaces.IFirebase
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.MuscularGroup

class FirebaseRepository(private val listener: IFirebase) {

    private val muscularGroupsRoot = Firebase.database.reference.child("muscular_groups")
    private val exercisesRoot = Firebase.database.reference.child("exercises")

    private val realm by lazy { RealmDatabase() }

    fun getMuscularGroupsFromFirebase() {
        muscularGroupsRoot.get().addOnSuccessListener {
            for (muscularName in it.children) {
                muscularName.getValue(MuscularGroup::class.java)?.let { mg -> saveMGroup(mg) }
            }
            listener.retrievedMGroupsFromFirebase(true)
        }.addOnFailureListener {
            listener.retrievedExercisesFromFirebase(false)
        }
    }

    fun getExercisesFromFirebase() {
        exercisesRoot.get().addOnSuccessListener {
            for (muscularName in it.children) {
                for (exercise in muscularName.children) {
                    exercise.getValue(Exercise::class.java)?.let { ex -> saveExercise(ex) }
                }
            }
            listener.retrievedExercisesFromFirebase(true)
        }.addOnFailureListener {
            listener.retrievedExercisesFromFirebase(false)
        }
    }

    private fun saveMGroup(mGroup: MuscularGroup): Boolean =
        realm.addObject { mGroup }

    private fun saveExercise(exercise: Exercise): Boolean =
        realm.addObject { exercise }
}