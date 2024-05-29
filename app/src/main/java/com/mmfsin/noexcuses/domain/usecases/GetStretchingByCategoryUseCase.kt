package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IStretchingRepository
import com.mmfsin.noexcuses.domain.models.Stretching
import javax.inject.Inject

class GetStretchingByCategoryUseCase @Inject constructor(
    private val repository: IStretchingRepository
) : BaseUseCase<GetStretchingByCategoryUseCase.Params, List<Stretching>>() {

    override suspend fun execute(params: Params): List<Stretching> =
        repository.getStretching(params.category)

    data class Params(
        val category: String
    )
}