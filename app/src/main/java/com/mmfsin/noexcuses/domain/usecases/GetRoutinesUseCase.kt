package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCaseNoParams
import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcuses.domain.models.Routine
import javax.inject.Inject

class GetRoutinesUseCase @Inject constructor(private val repository: IRoutinesRepository) :
    BaseUseCaseNoParams<List<Routine>>() {

    override suspend fun execute(): List<Routine> = repository.getRoutines()
}