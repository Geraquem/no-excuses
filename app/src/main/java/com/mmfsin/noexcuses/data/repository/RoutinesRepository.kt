package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.data.mappers.toDayList
import com.mmfsin.noexcuses.data.mappers.toRoutine
import com.mmfsin.noexcuses.data.mappers.toRoutineList
import com.mmfsin.noexcuses.data.models.DayDTO
import com.mmfsin.noexcuses.data.models.RoutineDTO
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.utils.ID
import com.mmfsin.noexcuses.utils.ROUTINE_ID
import io.realm.kotlin.where
import java.util.*
import javax.inject.Inject

class RoutinesRepository @Inject constructor(
    private val realmDatabase: IRealmDatabase
) : IRoutinesRepository {

    override fun getRoutines(): List<Routine> {
        val groups = realmDatabase.getObjectsFromRealm { where<RoutineDTO>().findAll() }
        return if (groups.isNotEmpty()) groups.toRoutineList()
        else emptyList()
    }

    override fun getRoutineById(id: String): Routine? {
        val routines =
            realmDatabase.getObjectsFromRealm { where<RoutineDTO>().equalTo(ID, id).findAll() }
        return if (routines.isNotEmpty()) routines.first().toRoutine()
        else null
    }

    override fun addRoutine(title: String, description: String?) {
        val id = UUID.randomUUID().toString()
        val routine = RoutineDTO(id, title, description, 0)
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
        /** DELETE DAYS AND EXERCISES IN THAT ROUTINE */
        val routine = getRoutineDTO(id)
        routine?.let { realmDatabase.deleteObject({ it }, id) }
    }

    private fun getRoutineDTO(id: String): RoutineDTO? {
        val routines =
            realmDatabase.getObjectsFromRealm { where<RoutineDTO>().equalTo(ID, id).findAll() }
        return if (routines.isNotEmpty()) routines.first() else null
    }

    override fun getDays(routineId: String): List<Day> {
        val days = realmDatabase.getObjectsFromRealm {
            where<DayDTO>().equalTo(ROUTINE_ID, routineId).findAll()
        }
        return if (days.isNotEmpty()) days.toDayList()
        else emptyList()
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
}