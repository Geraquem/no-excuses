package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IMyRoutinesRepository
import javax.inject.Inject

class UpdateMyRoutinePushPinUseCase @Inject constructor(
    private val repository: IMyRoutinesRepository
) : BaseUseCase<UpdateMyRoutinePushPinUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) =
        repository.updateRoutinePushPin(params.id)

    data class Params(
        val id: String
    )
}