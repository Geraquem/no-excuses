package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCaseNoParams
import com.mmfsin.noexcuses.domain.interfaces.IDefaultRoutinesRepository
import com.mmfsin.noexcuses.domain.models.DefaultRoutine
import javax.inject.Inject

class GetDefaultRoutinesUseCase @Inject constructor(private val repository: IDefaultRoutinesRepository) :
    BaseUseCaseNoParams<List<DefaultRoutine>>() {

    override suspend fun execute(): List<DefaultRoutine> = repository.getDefaultRoutines()
}