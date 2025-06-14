package com.mmfsin.noexcuses.data.repository

import android.content.Context
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.noexcuses.data.mappers.toChExercise
import com.mmfsin.noexcuses.data.mappers.toChExerciseDTO
import com.mmfsin.noexcuses.data.mappers.toCompactExercise
import com.mmfsin.noexcuses.data.mappers.toExercise
import com.mmfsin.noexcuses.data.mappers.toExerciseList
import com.mmfsin.noexcuses.data.mappers.toMuscularGroupList
import com.mmfsin.noexcuses.data.models.ChExerciseDTO
import com.mmfsin.noexcuses.data.models.DataDTO
import com.mmfsin.noexcuses.data.models.DayDTO
import com.mmfsin.noexcuses.data.models.ExerciseDTO
import com.mmfsin.noexcuses.data.models.MuscularGroupDTO
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.mappers.toExerciseDTO
import com.mmfsin.noexcuses.domain.models.ChExercise
import com.mmfsin.noexcuses.domain.models.CompactExercise
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.presentation.models.CreatedExercise
import com.mmfsin.noexcuses.utils.CATEGORY
import com.mmfsin.noexcuses.utils.DATA_ID
import com.mmfsin.noexcuses.utils.DAY_ID
import com.mmfsin.noexcuses.utils.EXERCISES
import com.mmfsin.noexcuses.utils.FAV_ID
import com.mmfsin.noexcuses.utils.ID
import com.mmfsin.noexcuses.utils.MY_SHARED_PREFS
import com.mmfsin.noexcuses.utils.ROUTINE_ID
import com.mmfsin.noexcuses.utils.SERVER_EXERCISES
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class ExercisesRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val realmDatabase: IRealmDatabase
) : IExercisesRepository {

    override fun getMuscularGroups(): List<MuscularGroup> {
        val groups = realmDatabase.getObjectsFromRealm { where<MuscularGroupDTO>().findAll() }
        return if (groups.isNotEmpty()) groups.sortedBy { it.order }.toMuscularGroupList()
        else emptyList()
    }

    suspend fun getExercises(): List<Exercise> {
        val latch = CountDownLatch(1)
        val sharedPrefs = context.getSharedPreferences(MY_SHARED_PREFS, Context.MODE_PRIVATE)

        if (sharedPrefs.getBoolean(SERVER_EXERCISES, true)) {
            realmDatabase.deleteAllObjects(ExerciseDTO::class.java)
            val exercises = mutableListOf<ExerciseDTO>()
            Firebase.database.reference.child(EXERCISES).get().addOnSuccessListener {
                for (mgroup in it.children) {
                    for (child in mgroup.children) {
                        child.getValue(ExerciseDTO::class.java)?.let { exerciseDTO ->
                            saveExerciseInRealm(exerciseDTO)
                            exercises.add(exerciseDTO)
                        }
                    }
                }
                sharedPrefs.edit().apply {
                    putBoolean(SERVER_EXERCISES, false)
                    apply()
                }
                latch.countDown()

            }.addOnFailureListener {
                latch.countDown()
            }

            withContext(Dispatchers.IO) { latch.await() }
            return exercises.sortedBy { it.order }.toExerciseList()

        } else {
            val exercises = realmDatabase.getObjectsFromRealm { where<ExerciseDTO>().findAll() }
            return exercises.sortedBy { it.order }.toExerciseList()
        }
    }

    override suspend fun getExercisesByMuscularGroup(mGroup: String): List<Exercise> {
        val exercises = getExercises()
        return exercises.filter { it.category == mGroup }
    }

    private fun saveExerciseInRealm(exercise: ExerciseDTO) = realmDatabase.addObject { exercise }

    override fun getExerciseById(id: String): Exercise? {
        val exercises = realmDatabase.getObjectFromRealm(ExerciseDTO::class.java, ID, id)
        return exercises?.toExercise()
    }

    override fun getDayExercises(dayId: String): List<CompactExercise> {
        val exercises = realmDatabase.getObjectsFromRealm {
            where<ChExerciseDTO>().equalTo(DAY_ID, dayId).findAll()
        }

        val resultList = mutableListOf<CompactExercise>()
        for (exercise in exercises) {
            exercise.exerciseId?.let { id ->
                val ex = getExerciseById(id)
                ex?.let { e -> resultList.add(exercise.toCompactExercise(e)) }
            }
        }
        return resultList.sortedBy { it.position }
    }

    override fun addChExercise(chExercise: ChExercise) {
        val day = getDayDTO(chExercise.dayId)
        var exercisePos = 0
        day?.let {
            it.exercises++
            exercisePos = it.exercises
            realmDatabase.addObject { it }
        }
        realmDatabase.addObject { chExercise.toChExerciseDTO(exercisePos) }
    }

    override fun addDefaultExerciseAsMine(chExercise: ChExerciseDTO) {
        realmDatabase.addObject { chExercise }
    }

    override fun editChExercise(chExercise: ChExercise) {
        realmDatabase.addObject { chExercise.toChExerciseDTO(null) }
    }

    override fun moveChExercise(exercises: List<String>) {
        exercises.forEachIndexed { i, id ->
            val exercise = realmDatabase.getObjectFromRealm(ChExerciseDTO::class.java, ID, id)
            exercise?.let { e ->
                e.position = i
                realmDatabase.addObject { e }
            }
        }
    }

    private fun getDayDTO(id: String): DayDTO? =
        realmDatabase.getObjectFromRealm(DayDTO::class.java, ID, id)

    override fun getChExerciseById(chExerciseId: String): ChExercise? =
        getChExerciseDTO(chExerciseId)?.toChExercise()

    private fun getChExerciseDTO(chExerciseId: String): ChExerciseDTO? =
        realmDatabase.getObjectFromRealm(ChExerciseDTO::class.java, ID, chExerciseId)

    override fun getFavExercises(): List<Exercise> {
        val favs = realmDatabase.getObjectsFromRealm {
            where<ExerciseDTO>().equalTo(FAV_ID, true).findAll()
        }
        return favs.toExerciseList().sortedBy { it.category }
    }

    override fun checkExerciseFav(exerciseId: String): Boolean {
        val exercise = getExerciseById(exerciseId)
        return exercise?.isFav ?: run { false }
    }

    override fun updateExerciseFav(exerciseId: String) {
        val exercise = realmDatabase.getObjectFromRealm(ExerciseDTO::class.java, ID, exerciseId)
        exercise?.let { e ->
            e.isFav = !e.isFav
            realmDatabase.addObject { e }
        }
    }

    override fun deleteChExercise(chExerciseId: String) {
        val chExercise = getChExerciseDTO(chExerciseId)
        chExercise?.let { e ->
            /** delete all series related with chExercise */
            val dataId = e.exerciseId + e.dayId
            deleteDataExercise(dataId)

            /** delete chExercise */
            val day = getDayDTO(e.dayId)
            day?.let {
                it.exercises--
                realmDatabase.addObject { it }
            }
            realmDatabase.deleteObject(ChExerciseDTO::class.java, ID, e.id)
        }
    }

    private fun deleteDataExercise(dataId: String) {
        val data = realmDatabase.getObjectsFromRealm {
            where<DataDTO>().equalTo(DATA_ID, dataId).findAll()
        }
        data.forEach { d ->
            d.id?.let { id -> realmDatabase.deleteObject(DataDTO::class.java, ID, id) }
        }
    }

    override fun deleteExercisesFromDeletedDay(dayId: String) {
        val exercises = realmDatabase.getObjectsFromRealm {
            where<ChExerciseDTO>().equalTo(DAY_ID, dayId).findAll()
        }
        for (e in exercises) {
            deleteChExercise(e.id)
        }
    }

    override fun deleteExercisesFromDeletedRoutine(routineId: String) {
        val exercises = realmDatabase.getObjectsFromRealm {
            where<ChExerciseDTO>().equalTo(ROUTINE_ID, routineId).findAll()
        }
        for (e in exercises) {
            deleteChExercise(e.id)
        }
    }

    override fun createCustomExercise(createdExercise: CreatedExercise) {
        val exercises = realmDatabase.getObjectsFromRealm {
            where<ExerciseDTO>().equalTo(CATEGORY, createdExercise.category).findAll()
        }.sortedBy { it.order }
        val order = exercises.last().order + 10

        val exerciseToAdd = createdExercise.toExerciseDTO(order = order)
        saveExerciseInRealm(exerciseToAdd)
    }

    override fun editCustomExercise(createdExercise: CreatedExercise, id: String) {
        val exercise = realmDatabase.getObjectFromRealm(ExerciseDTO::class.java, ID, id)
        exercise?.order?.let { o ->
            val exerciseToAdd = createdExercise.toExerciseDTO(id, o)
            saveExerciseInRealm(exerciseToAdd)
        }
    }

    override fun deleteCustomExercise(createdExerciseId: String) {
        realmDatabase.deleteObject(ExerciseDTO::class.java, ID, createdExerciseId)
    }
}