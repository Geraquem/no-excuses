package com.mmfsin.noexcuses.data.repository

import android.content.Context
import com.mmfsin.noexcuses.data.mappers.toDayList
import com.mmfsin.noexcuses.data.mappers.toDayListaaa
import com.mmfsin.noexcuses.data.mappers.toDefaultRoutine
import com.mmfsin.noexcuses.data.mappers.toDefaultRoutineList
import com.mmfsin.noexcuses.data.models.DefaultDayDTO
import com.mmfsin.noexcuses.data.models.DefaultRoutineDTO
import com.mmfsin.noexcuses.domain.interfaces.IDefaultRoutinesRepository
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.DefaultRoutine
import com.mmfsin.noexcuses.utils.ID
import com.mmfsin.noexcuses.utils.ROUTINE_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.kotlin.where
import javax.inject.Inject

class DefaultRoutinesRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val realmDatabase: IRealmDatabase
) : IDefaultRoutinesRepository {

    override fun getDefaultRoutines(): List<DefaultRoutine> {
        val exercises = realmDatabase.getObjectsFromRealm {
            where<DefaultRoutineDTO>().findAll()
        }
        return exercises.toDefaultRoutineList()
    }

    override fun getDefaultRoutineById(id: String): DefaultRoutine? {
        val routine = realmDatabase.getObjectFromRealm(DefaultRoutineDTO::class.java, ID, id)
        return routine?.toDefaultRoutine()
    }

    override fun getDefaultDays(routineId: String): List<Day> {
        val days = realmDatabase.getObjectsFromRealm {
            where<DefaultDayDTO>().equalTo(ROUTINE_ID, routineId).findAll()
        }
        return days.toDayList()
    }
}