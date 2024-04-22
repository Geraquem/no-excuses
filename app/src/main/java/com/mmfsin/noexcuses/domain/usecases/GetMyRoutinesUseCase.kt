package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCaseNoParams
import com.mmfsin.noexcuses.domain.interfaces.IMyRoutinesRepository
import com.mmfsin.noexcuses.domain.models.Routine
import javax.inject.Inject

class GetMyRoutinesUseCase @Inject constructor(private val repository: IMyRoutinesRepository) :
    BaseUseCaseNoParams<List<Routine>>() {

    override suspend fun execute(): List<Routine> = repository.getRoutines()
}