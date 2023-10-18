package com.mmfsin.noexcuses.data.repository

import android.content.Context
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.noexcuses.data.models.ExerciseDTO
import com.mmfsin.noexcuses.data.models.MuscularGroupDTO
import com.mmfsin.noexcuses.data.models.RoutineDTO
import com.mmfsin.noexcuses.domain.interfaces.IMenuRepository
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.utils.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class MenuRepository @Inject constructor(
    @ApplicationContext val context: Context, private val realmDatabase: IRealmDatabase
) : IMenuRepository {

    private val reference = Firebase.database.reference

    override suspend fun checkVersion() {
        getDataFromFirebase(getSavedVersion())
    }

    private suspend fun getDataFromFirebase(savedVersion: Long) {
        val latch = CountDownLatch(1)
        reference.get().addOnSuccessListener {
            val version = it.child(VERSION).value as Long
            if (version == savedVersion) {
                latch.countDown()
            } else {
                saveVersion(newVersion = version)

                val routines = it.child(ROUTINES)
                for (child in routines.children) {
                    child.getValue(RoutineDTO::class.java)?.let { r ->
                        saveRoutineInRealm(r)
                    }
                }

                val fbMGroups = it.child(M_GROUPS)
                for (child in fbMGroups.children) {
                    child.getValue(MuscularGroupDTO::class.java)
                        ?.let { mGroup -> saveMGroupsInRealm(mGroup) }
                }

                val fbExercises = it.child(EXERCISES)
                for (child in fbExercises.children) {
                    for (exercise in child.children) {
                        exercise.getValue(ExerciseDTO::class.java)
                            ?.let { e -> saveExerciseInRealm(e) }
                    }
                }

                latch.countDown()
            }

        }.addOnFailureListener {
            latch.countDown()
        }

        withContext(Dispatchers.IO) {
            latch.await()
        }
    }

    private fun saveVersion(newVersion: Long) {
        val editor = getSharedPreferences().edit()
        editor.putLong(SAVED_VERSION, newVersion)
        editor.apply()
    }

    private fun getSavedVersion(): Long = getSharedPreferences().getLong(SAVED_VERSION, -1)

    private fun getSharedPreferences() =
        context.getSharedPreferences(MY_SHARED_PREFS, Context.MODE_PRIVATE)

    private fun saveRoutineInRealm(routine: RoutineDTO) = realmDatabase.addObject { routine }
    private fun saveMGroupsInRealm(mGroup: MuscularGroupDTO) = realmDatabase.addObject { mGroup }
    private fun saveExerciseInRealm(exercise: ExerciseDTO) = realmDatabase.addObject { exercise }

    override fun isFirstTime(): Boolean {
        val firstTime = getSharedPreferences().getBoolean(FIRST_TIME, true)
        if (firstTime) setNotFirstTime()
        return firstTime
    }

    private fun setNotFirstTime() {
        val editor = getSharedPreferences().edit()
        editor.putBoolean(FIRST_TIME, false)
        editor.apply()
    }
}