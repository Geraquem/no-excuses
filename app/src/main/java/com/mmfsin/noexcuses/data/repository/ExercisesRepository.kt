package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.data.mappers.*
import com.mmfsin.noexcuses.data.models.*
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.models.ChExercise
import com.mmfsin.noexcuses.domain.models.CompactExercise
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.utils.*
import io.realm.kotlin.where
import javax.inject.Inject

class ExercisesRepository @Inject constructor(
    private val realmDatabase: IRealmDatabase
) : IExercisesRepository {

    override fun getMuscularGroups(): List<MuscularGroup> {
        val groups = realmDatabase.getObjectsFromRealm { where<MuscularGroupDTO>().findAll() }
        return if (groups.isNotEmpty()) groups.sortedBy { it.order }.toMuscularGroupList()
        else emptyList()
    }

    override fun getExercisesByMuscularGroup(mGroup: String): List<Exercise> {
        val exercises = realmDatabase.getObjectsFromRealm {
            where<ExerciseDTO>().equalTo(CATEGORY, mGroup).findAll()
        }
        return if (exercises.isNotEmpty()) exercises.sortedBy { it.order }.toExerciseList()
        else emptyList()
    }

    override fun getExerciseById(id: String): Exercise? {
        val exercises = realmDatabase.getObjectsFromRealm {
            where<ExerciseDTO>().equalTo(ID, id).findAll()
        }
        return if (exercises.isNotEmpty()) exercises.first().toExercise() else null
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
        return resultList
    }

    override fun addChExercise(chExercise: ChExercise) {
        val day = getDayDTO(chExercise.dayId)
        day?.let {
            it.exercises++
            realmDatabase.addObject { it }
        }
        realmDatabase.addObject { chExercise.toChExerciseDTO() }
    }

    override fun editChExercise(chExercise: ChExercise) {
        realmDatabase.addObject { chExercise.toChExerciseDTO() }
    }

    private fun getDayDTO(id: String): DayDTO? {
        val days = realmDatabase.getObjectsFromRealm { where<DayDTO>().equalTo(ID, id).findAll() }
        return if (days.isNotEmpty()) days.first() else null
    }

    override fun getChExercise(chExerciseId: String): ChExercise? {
        val exercises = realmDatabase.getObjectsFromRealm {
            where<ChExerciseDTO>().equalTo(ID, chExerciseId).findAll()
        }
        return if (exercises.isNotEmpty()) exercises.first().toChExercise() else null
    }

    private fun getChExerciseDTO(chExerciseId: String): ChExerciseDTO? {
        val exercises = realmDatabase.getObjectsFromRealm {
            where<ChExerciseDTO>().equalTo(ID, chExerciseId).findAll()
        }
        return if (exercises.isNotEmpty()) exercises.first() else null
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
        for (d in data) {
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
}