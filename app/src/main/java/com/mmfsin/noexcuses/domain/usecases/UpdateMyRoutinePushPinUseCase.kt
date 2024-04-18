package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IDefaultRoutinesRepository
import com.mmfsin.noexcuses.domain.interfaces.IMyRoutinesRepository
import com.mmfsin.noexcuses.domain.models.DefaultRoutine
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