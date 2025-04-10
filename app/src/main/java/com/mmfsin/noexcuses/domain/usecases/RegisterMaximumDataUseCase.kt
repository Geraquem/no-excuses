package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IMaximumRepository
import com.mmfsin.noexcuses.domain.models.TempMaximumData
import javax.inject.Inject

class RegisterMaximumDataUseCase @Inject constructor(
    private val repository: IMaximumRepository
) : BaseUseCase<RegisterMaximumDataUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) {
        repository.registerMaximumData(params.data)
    }

    data class Params(
        val data: TempMaximumData,
    )
}