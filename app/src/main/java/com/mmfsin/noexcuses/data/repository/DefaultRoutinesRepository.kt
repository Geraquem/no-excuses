package com.mmfsin.noexcuses.data.repository

import android.content.Context
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.noexcuses.data.mappers.toDay
import com.mmfsin.noexcuses.data.mappers.toDayListFromDefaultDayDTO
import com.mmfsin.noexcuses.data.mappers.toDefaultExercise
import com.mmfsin.noexcuses.data.mappers.toDefaultRoutine
import com.mmfsin.noexcuses.data.mappers.toDefaultRoutineList
import com.mmfsin.noexcuses.data.mappers.toExercise
import com.mmfsin.noexcuses.data.models.DefaultDayDTO
import com.mmfsin.noexcuses.data.models.DefaultExerciseDTO
import com.mmfsin.noexcuses.data.models.DefaultRoutineDTO
import com.mmfsin.noexcuses.data.models.ExerciseDTO
import com.mmfsin.noexcuses.data.models.MyRoutineDTO
import com.mmfsin.noexcuses.data.models.StretchingDTO
import com.mmfsin.noexcuses.domain.interfaces.IDefaultRoutinesRepository
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.DefaultExercise
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.utils.DAYS
import com.mmfsin.noexcuses.utils.DAY_ID
import com.mmfsin.noexcuses.utils.DEFAULT_ROUTINES
import com.mmfsin.noexcuses.utils.EXERCISES
import com.mmfsin.noexcuses.utils.ID
import com.mmfsin.noexcuses.utils.MY_SHARED_PREFS
import com.mmfsin.noexcuses.utils.ROUTINES
import com.mmfsin.noexcuses.utils.ROUTINE_ID
import com.mmfsin.noexcuses.utils.SERVER_DEFAULT_ROUTINES
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class DefaultRoutinesRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val realmDatabase: IRealmDatabase
) : IDefaultRoutinesRepository {

    override suspend fun getDefaultRoutines(): List<Routine> {
        val latch = CountDownLatch(1)
        val sharedPrefs = context.getSharedPreferences(MY_SHARED_PREFS, Context.MODE_PRIVATE)

        if (sharedPrefs.getBoolean(SERVER_DEFAULT_ROUTINES, true)) {
            realmDatabase.deleteAllObjects(StretchingDTO::class.java)
            val defaultRoutines = mutableListOf<DefaultRoutineDTO>()
            Firebase.database.reference.child(DEFAULT_ROUTINES).get().addOnSuccessListener {
                val routines = it.child(ROUTINES)
                for (routine in routines.children) {
                    routine.getValue(DefaultRoutineDTO::class.java)?.let { r ->
                        saveDefaultRoutine(r)
                        defaultRoutines.add(r)
                    }
                }

                val days = it.child(DAYS)
                for (day in days.children) {
                    day.getValue(DefaultDayDTO::class.java)?.let { d -> saveDefaultDay(d) }
                }

                val exercises = it.child(EXERCISES)
                for (exercise in exercises.children) {
                    exercise.getValue(DefaultExerciseDTO::class.java)
                        ?.let { e -> saveDefaultExercise(e) }
                }

                sharedPrefs.edit().apply {
//                    putBoolean(SERVER_DEFAULT_ROUTINES, false)
                    apply()
                }
                latch.countDown()

            }.addOnFailureListener {
                latch.countDown()
            }

            withContext(Dispatchers.IO) { latch.await() }
            return defaultRoutines.toDefaultRoutineList()

        } else {
            val routines = realmDatabase.getObjectsFromRealm {
                where<DefaultRoutineDTO>().findAll()
            }
            return routines.toDefaultRoutineList()
        }
    }

    private fun saveDefaultRoutine(routine: DefaultRoutineDTO) = realmDatabase.addObject { routine }
    private fun saveDefaultDay(day: DefaultDayDTO) = realmDatabase.addObject { day }
    private fun saveDefaultExercise(exercise: DefaultExerciseDTO) =
        realmDatabase.addObject { exercise }

    override fun getDefaultRoutineById(id: String): Routine? {
        val routine = realmDatabase.getObjectFromRealm(DefaultRoutineDTO::class.java, ID, id)
        return routine?.toDefaultRoutine()
    }

    override fun updateRoutinePushPin(id: String) {
        val myRoutines = realmDatabase.getObjectsFromRealm { where<MyRoutineDTO>().findAll() }
        myRoutines.forEach { routine ->
            routine.doingIt = false
            realmDatabase.addObject { routine }
        }

        val dfRoutines = realmDatabase.getObjectsFromRealm { where<DefaultRoutineDTO>().findAll() }
        dfRoutines.forEach { routine ->
            if (routine.id == id) routine.doingIt = !routine.doingIt
            else routine.doingIt = false
            realmDatabase.addObject { routine }
        }
    }

    override fun getDefaultDays(routineId: String): List<Day> {
        val days = realmDatabase.getObjectsFromRealm {
            where<DefaultDayDTO>().equalTo(ROUTINE_ID, routineId).findAll()
        }
        return days.toDayListFromDefaultDayDTO()
    }

    override fun getDefaultDayById(id: String): Day? {
        val routine = realmDatabase.getObjectFromRealm(DefaultDayDTO::class.java, ID, id)
        return routine?.toDay()
    }

    override fun getDefaultExercises(dayId: String): List<DefaultExercise> {
        val result = mutableListOf<DefaultExercise>()
        val dfExercises = realmDatabase.getObjectsFromRealm {
            where<DefaultExerciseDTO>().equalTo(DAY_ID, dayId).findAll()
        }
        dfExercises.forEach { dfExercise ->
            val exercise = getExerciseFromDefaultExercise(dfExercise.exerciseId)
            exercise?.let { e -> result.add(dfExercise.toDefaultExercise(e)) }
        }
        return result
    }

    override fun getDefaultExerciseById(id: String): DefaultExercise? {
        var result: DefaultExercise? = null
        val dfExercise = realmDatabase.getObjectFromRealm(DefaultExerciseDTO::class.java, ID, id)
        dfExercise?.let { dfE ->
            val exercise = getExerciseFromDefaultExercise(dfE.exerciseId)
            exercise?.let { e -> result = dfE.toDefaultExercise(e) }
        }
        return result
    }

    private fun getExerciseFromDefaultExercise(exerciseId: String): Exercise? {
        val exercise = realmDatabase.getObjectFromRealm(ExerciseDTO::class.java, ID, exerciseId)
        return exercise?.toExercise()
    }
}