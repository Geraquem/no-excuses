package com.mmfsin.noexcuses.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.noexcuses.data.models.DayDTO
import com.mmfsin.noexcuses.data.models.DefaultDayDTO
import com.mmfsin.noexcuses.data.models.DefaultRoutineDTO
import com.mmfsin.noexcuses.data.models.ExerciseDTO
import com.mmfsin.noexcuses.data.models.MuscularGroupDTO
import com.mmfsin.noexcuses.domain.interfaces.IMenuRepository
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.utils.DAYS
import com.mmfsin.noexcuses.utils.DEFAULT_ROUTINES
import com.mmfsin.noexcuses.utils.EXERCISES
import com.mmfsin.noexcuses.utils.FIRST_TIME
import com.mmfsin.noexcuses.utils.MY_SHARED_PREFS
import com.mmfsin.noexcuses.utils.M_GROUPS
import com.mmfsin.noexcuses.utils.ROUTINES
import com.mmfsin.noexcuses.utils.SAVED_VERSION
import com.mmfsin.noexcuses.utils.VERSION
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
//            if (version == savedVersion) {
//                latch.countDown()
//            } else {
            saveVersion(newVersion = version)

            val fbMGroups = it.child(M_GROUPS)
            for (child in fbMGroups.children) {
                child.getValue(MuscularGroupDTO::class.java)
                    ?.let { mGroup -> saveMGroups(mGroup) }
            }

            val fbExercises = it.child(EXERCISES)
            for (child in fbExercises.children) {
                for (exercise in child.children) {
                    exercise.getValue(ExerciseDTO::class.java)
                        ?.let { e -> saveExercise(e) }
                }
            }

            val defaultRoutines = it.child(DEFAULT_ROUTINES)

            val routines = defaultRoutines.child(ROUTINES)
            for (routine in routines.children) {
                routine.getValue(DefaultRoutineDTO::class.java)?.let { r -> saveDefaultRoutine(r) }
            }

            val days = defaultRoutines.child(DAYS)
            for (day in days.children) {
                day.getValue(DefaultDayDTO::class.java)?.let { d -> saveDefaultDay(d) }
            }


//            for (child in routines.children) {
//                try {
//                    child.getValue(RoutineDTO::class.java)
//                        ?.let { r -> saveRoutinePredInRealm(r) }
//                    for (exercise in child.child(DAY_ONE).children) {
//                        exercise.getValue(ExercisePredDTO::class.java)?.let { e ->
//                            saveExercisePredInRealm(e)
//                        }
//                    }
//                } catch (e: Exception) {
//                    Log.e("Error parsing RoutinePreDTO", e.message.toString())
//                }
////                }
//            }
//
            latch.countDown()

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

    private fun saveMGroups(mGroup: MuscularGroupDTO) = realmDatabase.addObject { mGroup }
    private fun saveExercise(exercise: ExerciseDTO) = realmDatabase.addObject { exercise }

    private fun saveDefaultRoutine(routine: DefaultRoutineDTO) = realmDatabase.addObject { routine }
    private fun saveDefaultDay(day: DefaultDayDTO) = realmDatabase.addObject { day }

//    private fun saveExercisePredInRealm(exercise: ExercisePredDTO) =
//        realmDatabase.addObject { exercise }

    override fun isFirstTime(): Boolean {
        val firstTime = getSharedPreferences().getBoolean(FIRST_TIME, true)
        if (firstTime) {
            val editor = getSharedPreferences().edit()
            editor.putBoolean(FIRST_TIME, false)
            editor.apply()
        }
        return firstTime
    }

    private fun getSharedPreferences() =
        context.getSharedPreferences(MY_SHARED_PREFS, MODE_PRIVATE)
}