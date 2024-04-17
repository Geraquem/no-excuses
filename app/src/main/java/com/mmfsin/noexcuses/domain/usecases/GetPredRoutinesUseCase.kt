package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCaseNoParams
import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcuses.domain.models.DefaultRoutine
import javax.inject.Inject

class GetPredRoutinesUseCase @Inject constructor(private val repository: IRoutinesRepository) :
    BaseUseCaseNoParams<List<DefaultRoutine>>() {

    override suspend fun execute(): List<DefaultRoutine> = repository.getPredeterminatedRoutines()
}