package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.data.mappers.createNewRoutineFromDefault
import com.mmfsin.noexcuses.domain.interfaces.IDefaultRoutinesRepository
import com.mmfsin.noexcuses.domain.interfaces.IMyRoutinesRepository
import java.util.UUID
import javax.inject.Inject

class AddDfRoutineToMineUseCase @Inject constructor(
    private val dfRepository: IDefaultRoutinesRepository,
    private val mRepository: IMyRoutinesRepository
) : BaseUseCase<AddDfRoutineToMineUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) {
        val dfRoutine = dfRepository.getDefaultRoutineById(params.dfRoutineId)
        dfRoutine?.let { r ->
            val newRoutineId = UUID.randomUUID().toString()

            val days = dfRepository.getDefaultDays(params.dfRoutineId)
            days.forEach { day -> mRepository.addDayToNewDfRoutineMine(day, newRoutineId) }

            val newRoutine = r.createNewRoutineFromDefault(newRoutineId, days.size)
            mRepository.addDfRoutineToMine(newRoutine)

        } ?: run { throw NullPointerException() }
    }

    data class Params(
        val dfRoutineId: String,
    )
}