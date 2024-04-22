package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.data.mappers.toDay
import com.mmfsin.noexcuses.data.mappers.toDayListFromDayDTO
import com.mmfsin.noexcuses.data.mappers.toMyRoutine
import com.mmfsin.noexcuses.data.mappers.toMyRoutineList
import com.mmfsin.noexcuses.data.models.DayDTO
import com.mmfsin.noexcuses.data.models.DefaultRoutineDTO
import com.mmfsin.noexcuses.data.models.MyRoutineDTO
import com.mmfsin.noexcuses.domain.interfaces.IMyRoutinesRepository
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.utils.ID
import com.mmfsin.noexcuses.utils.ROUTINE_ID
import io.realm.kotlin.where
import java.util.UUID
import javax.inject.Inject

class MyRoutinesRepository @Inject constructor(
    private val realmDatabase: IRealmDatabase
) : IMyRoutinesRepository {

    override fun getRoutines(): List<Routine> {
        val groups = realmDatabase.getObjectsFromRealm { where<MyRoutineDTO>().findAll() }
        return if (groups.isNotEmpty()) groups.toMyRoutineList()
        else emptyList()
    }

    override fun getRoutineById(id: String): Routine? {
        val routine = getRoutineDTO(id)
        return routine?.toMyRoutine() ?: run { null }
    }

    override fun updateRoutinePushPin(id: String) {
        val dfRoutines = realmDatabase.getObjectsFromRealm { where<DefaultRoutineDTO>().findAll() }
        dfRoutines.forEach { routine ->
            routine.doingIt = false
            realmDatabase.addObject { routine }
        }

        val myRoutines = realmDatabase.getObjectsFromRealm { where<MyRoutineDTO>().findAll() }
        myRoutines.forEach { routine ->
            if (routine.id == id) routine.doingIt = !routine.doingIt
            else routine.doingIt = false
            realmDatabase.addObject { routine }
        }

    }

    override fun addRoutine(title: String, description: String?) {
        val id = UUID.randomUUID().toString()
        val routine = MyRoutineDTO(id, title, description, 0)
        realmDatabase.addObject { routine }
    }

    override fun editRoutine(id: String, title: String, description: String?) {
        val routine = getRoutineDTO(id)
        routine?.let {
            it.title = title
            it.description = description
            realmDatabase.addObject { it }
        }
    }

    override fun deleteRoutine(id: String) {
        /** DELETE DAYS RELATED WITH ROUTINE */
        val days = realmDatabase.getObjectsFromRealm {
            where<DayDTO>().equalTo(ROUTINE_ID, id).findAll()
        }
        for (day in days) {
            realmDatabase.deleteObject(DayDTO::class.java, ID, day.id)
        }

        /** DELTE ROUTINE */
        realmDatabase.deleteObject(MyRoutineDTO::class.java, ID, id)
    }

    private fun getRoutineDTO(id: String): MyRoutineDTO? =
        realmDatabase.getObjectFromRealm(MyRoutineDTO::class.java, ID, id)

    override fun getRoutineDays(routineId: String): List<Day> {
        val days = realmDatabase.getObjectsFromRealm {
            where<DayDTO>().equalTo(ROUTINE_ID, routineId).findAll()
        }
        return days.toDayListFromDayDTO()
    }

    override fun addDay(routineId: String, title: String) {
        val routine = getRoutineDTO(routineId)
        routine?.let {
            it.days++
            realmDatabase.addObject { it }
        }

        val id = UUID.randomUUID().toString()
        val day = DayDTO(id, routineId, title, 0)
        realmDatabase.addObject { day }
    }

    override fun getDayById(dayId: String): Day? {
        val day = getDayDTO(dayId)
        return day?.toDay() ?: run { null }
    }

    override fun editDay(id: String, title: String) {
        val day = getDayDTO(id)
        day?.let {
            it.title = title
            realmDatabase.addObject { it }
        }
    }

    override fun deleteDay(id: String) {
        /** DELETE EXERCISES RELATED WITH ROUTINE */
        /** TODO */

        /** DELETE DAY */
        val day = getDayDTO(id)
        day?.let { d ->
            /** DELETE DAYS COUNT IN ROUTINE */
            val routine = getRoutineDTO(d.routineId)
            routine?.let { r ->
                r.days--
                realmDatabase.addObject { r }
            }
            realmDatabase.deleteObject(MyRoutineDTO::class.java, ID, id)
        }
    }

    private fun getDayDTO(id: String): DayDTO? =
        realmDatabase.getObjectFromRealm(DayDTO::class.java, ID, id)
}