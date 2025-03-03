package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IMenuRepository
import javax.inject.Inject

class SwitchBodyImageUseCase @Inject constructor(private val repository: IMenuRepository) :
    BaseUseCase<SwitchBodyImageUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) =
        repository.editBodyImage(params.womanImageSelected)

    data class Params(
        val womanImageSelected: Boolean
    )
}