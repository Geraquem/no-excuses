package com.mmfsin.noexcuses.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
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
import com.mmfsin.noexcuses.domain.interfaces.IDefaultRoutinesRepository
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.DefaultExercise
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.utils.DAYS
import com.mmfsin.noexcuses.utils.DAY_ID
import com.mmfsin.noexcuses.utils.DEFAULT_DAYS
import com.mmfsin.noexcuses.utils.DEFAULT_ROUTINES
import com.mmfsin.noexcuses.utils.ID
import com.mmfsin.noexcuses.utils.MY_SHARED_PREFS
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
        val defaultRoutines = mutableListOf<DefaultRoutineDTO>()
        val sharedPrefs = context.getSharedPreferences(MY_SHARED_PREFS, Context.MODE_PRIVATE)

        if (sharedPrefs.getBoolean(SERVER_DEFAULT_ROUTINES, true)) {
            Firebase.firestore.collection(DEFAULT_ROUTINES).get()
                .addOnSuccessListener { documents ->
                    for (doc in documents) {
                        try {
                            doc.toObject(DefaultRoutineDTO::class.java).let {
                                defaultRoutines.add(it)
                                realmDatabase.addObject { it }
                            }
                        } catch (e: Exception) {
                            Log.e("error", "error parsing routine")
                        }
                    }
                    sharedPrefs.edit().apply {
                        putBoolean(SERVER_DEFAULT_ROUTINES, false)
                        apply()
                    }
                    latch.countDown()

                }.addOnFailureListener {
                    latch.countDown()
                }
            withContext(Dispatchers.IO) { latch.await() }
            Toast.makeText(context, "from firebase", Toast.LENGTH_SHORT).show()
            return defaultRoutines.toDefaultRoutineList()
        } else {
            val routines = realmDatabase.getObjectsFromRealm {
                where<DefaultRoutineDTO>().findAll()
            }

            Toast.makeText(context, "from realm", Toast.LENGTH_SHORT).show()
            return routines.toDefaultRoutineList()
        }
    }

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

    override suspend fun getDefaultDays(routineId: String): List<Day> {
        val days = realmDatabase.getObjectsFromRealm {
            where<DefaultDayDTO>().equalTo(ROUTINE_ID, routineId).findAll()
        }
        if (days.isEmpty()) {
            val latch = CountDownLatch(1)
            val dDays = mutableListOf<DefaultDayDTO>()
            Firebase.firestore.collection(DEFAULT_DAYS).document(routineId).collection(DAYS).get()
                .addOnSuccessListener { documents ->
                    for (doc in documents) {
                        try {
                            doc.toObject(DefaultDayDTO::class.java).let {
                                dDays.add(it)
                                realmDatabase.addObject { it }
                            }
                        } catch (e: Exception) {
                            Log.e("error", "error parsing day")
                        }
                    }
                    latch.countDown()

                }.addOnFailureListener {
                    latch.countDown()
                }
            withContext(Dispatchers.IO) { latch.await() }
            Toast.makeText(context, "from firebase", Toast.LENGTH_SHORT).show()

            return dDays.toDayListFromDefaultDayDTO()
        } else {
            Toast.makeText(context, "from realm", Toast.LENGTH_SHORT).show()
            return days.toDayListFromDefaultDayDTO()
        }
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
        val dfExercise =
            realmDatabase.getObjectFromRealm(DefaultExerciseDTO::class.java, ID, id)
        dfExercise?.let { dfE ->
            val exercise = getExerciseFromDefaultExercise(dfE.exerciseId)
            exercise?.let { e -> result = dfE.toDefaultExercise(e) }
        }
        return result
    }

    private fun getExerciseFromDefaultExercise(exerciseId: String): Exercise? {
        val exercise =
            realmDatabase.getObjectFromRealm(ExerciseDTO::class.java, ID, exerciseId)
        return exercise?.toExercise()
    }
}