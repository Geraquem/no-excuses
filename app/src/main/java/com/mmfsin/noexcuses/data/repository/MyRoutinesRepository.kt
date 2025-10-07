package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.data.mappers.createDayDTO
import com.mmfsin.noexcuses.data.mappers.createNewMyRoutineDTO
import com.mmfsin.noexcuses.data.mappers.toDay
import com.mmfsin.noexcuses.data.mappers.toDayListFromDayDTO
import com.mmfsin.noexcuses.data.mappers.toMyRoutineList
import com.mmfsin.noexcuses.data.mappers.toRoutine
import com.mmfsin.noexcuses.data.models.ChExerciseDTO
import com.mmfsin.noexcuses.data.models.DataDTO
import com.mmfsin.noexcuses.data.models.DayDTO
import com.mmfsin.noexcuses.data.models.DefaultRoutineDTO
import com.mmfsin.noexcuses.data.models.MyRoutineDTO
import com.mmfsin.noexcuses.domain.interfaces.IMyRoutinesRepository
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.utils.DATA_ID
import com.mmfsin.noexcuses.utils.DAY_ID
import com.mmfsin.noexcuses.utils.ID
import com.mmfsin.noexcuses.utils.ROUTINE_ID
import io.realm.kotlin.ext.query
import java.util.UUID
import javax.inject.Inject

class MyRoutinesRepository @Inject constructor(
    private val realmDatabase: IRealmDatabase
) : IMyRoutinesRepository {

    override fun getRoutines(): List<Routine> {
        val groups = realmDatabase.getObjectsFromRealm { query<MyRoutineDTO>().find() }
        return if (groups.isNotEmpty()) groups.toMyRoutineList()
        else emptyList()
    }

    override fun getRoutineById(id: String): Routine? {
        val routine = realmDatabase.getObjectFromRealm(MyRoutineDTO::class, ID, id)
        return routine?.toRoutine() ?: run { null }
    }

    override suspend fun updateRoutinePushPin(id: String) {
        realmDatabase.write {
            // Resetear todas las DefaultRoutineDTO
            val dfRoutines = query<DefaultRoutineDTO>().find()
            dfRoutines.forEach { routine ->
                routine.doingIt = false
            }

            // Actualizar MyRoutineDTO
            val myRoutines = query<MyRoutineDTO>().find()
            myRoutines.forEach { routine ->
                routine.doingIt = (routine.id == id)
            }
        }
    }

    override suspend fun addRoutine(title: String, description: String?) {
        realmDatabase.addObject { createNewMyRoutineDTO(title, description) }
    }

    override suspend fun addDfRoutineToMine(routine: MyRoutineDTO) {
        realmDatabase.addObject { routine }
    }

    override suspend fun editRoutine(id: String, title: String, description: String?) {
        realmDatabase.write {
            val routine = query<MyRoutineDTO>("$ID == $0", id).first().find()
            routine?.let { r ->
                r.title = title
                r.description = description
            }
        }
    }

    override suspend fun deleteRoutine(id: String) {
        realmDatabase.write {
            val routine = query<MyRoutineDTO>("$ID == $0", id).first().find()
            routine?.let { r ->
                val days = query<DayDTO>("$ROUTINE_ID == $0", id).find()
                days.forEach { d ->
                    // Eliminar todos los ejercicios asociados a este día
                    val exercises = query<ChExerciseDTO>("$DAY_ID == $0", d.id).find()
                    exercises.forEach { e ->
                        val dataId = e.exerciseId + e.dayId
                        val data = query<DataDTO>("$DATA_ID == $0", dataId).find()
                        delete(data)
                        delete(e)
                    }
                    // Borrar todos los días de la rutina
                    delete(d)
                }
                //Borrar la rutina
                delete(r)
            }
        }
    }

    override fun getRoutineDays(routineId: String): List<Day> {
        val days = realmDatabase.getObjectsFromRealm {
            query<DayDTO>("$ROUTINE_ID == $0", routineId).find()
        }
        return days.toDayListFromDayDTO()
    }

    override suspend fun addDay(routineId: String, title: String) {
        realmDatabase.write {
            val routine = query<MyRoutineDTO>("$ID == $0", routineId).first().find()
            routine?.let { r ->
                r.days += 1
                copyToRealm(
                    createDayDTO(UUID.randomUUID().toString(), routineId, title, 0)
                )
            }
        }
    }

    override fun addDayToNewDfRoutineMine(day: Day, newDayId: String, newRoutineId: String) {
        val dayDTO = createDayDTO(newDayId, newRoutineId, day.title, day.exercises)
        realmDatabase.addObject { dayDTO }
    }

    override fun getDayById(dayId: String): Day? {
        val day = realmDatabase.getObjectFromRealm(DayDTO::class, ID, dayId)
        return day?.toDay() ?: run { null }
    }

    override suspend fun editDay(id: String, title: String) {
        realmDatabase.write {
            val day = query<DayDTO>("$ID == $0", id).first().find()
            day?.let { d ->
                d.title = title
            }
        }
    }

    override suspend fun deleteDay(id: String) {
        realmDatabase.write {
            // Eliminar todos los ejercicios asociados a este día
            val exercises = query<ChExerciseDTO>("$DAY_ID == $0", id).find()
            exercises.forEach { e ->
                val dataId = e.exerciseId + e.dayId
                val data = query<DataDTO>("$DATA_ID == $0", dataId).find()
                delete(data)
                delete(e)
            }

            // Buscar el día
            val day = query<DayDTO>("$ID == $0", id).first().find()
            day?.let { d ->

                // Actualizar la rutina
                val routine = query<MyRoutineDTO>("$ID == $0", d.routineId).first().find()
                routine?.let { r ->
                    r.days -= 1
                }

                // Eliminar el día
                delete(d)
            }
        }
    }
}