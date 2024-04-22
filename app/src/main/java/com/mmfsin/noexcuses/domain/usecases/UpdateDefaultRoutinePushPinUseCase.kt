package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IDefaultRoutinesRepository
import javax.inject.Inject

class UpdateDefaultRoutinePushPinUseCase @Inject constructor(
    private val repository: IDefaultRoutinesRepository
) : BaseUseCase<UpdateDefaultRoutinePushPinUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) = repository.updateRoutinePushPin(params.id)

    data class Params(
        val id: String
    )
}