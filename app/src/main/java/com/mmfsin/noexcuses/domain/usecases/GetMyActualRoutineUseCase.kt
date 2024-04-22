package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCaseNoParams
import com.mmfsin.noexcuses.domain.interfaces.IMenuRepository
import com.mmfsin.noexcuses.domain.models.Routine
import javax.inject.Inject

class GetMyActualRoutineUseCase @Inject constructor(
    private val repository: IMenuRepository
) : BaseUseCaseNoParams<Routine?>() {

    override suspend fun execute(): Routine? = repository.getMyActualRoutine()

    data class Params(
        val routineId: String
    )
}