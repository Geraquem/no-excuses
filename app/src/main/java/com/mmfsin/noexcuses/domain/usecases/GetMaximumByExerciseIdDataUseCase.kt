package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IMaximumRepository
import com.mmfsin.noexcuses.domain.models.MaximumData
import javax.inject.Inject

class GetMaximumByExerciseIdDataUseCase @Inject constructor(
    private val repository: IMaximumRepository
) : BaseUseCase<GetMaximumByExerciseIdDataUseCase.Params, MaximumData?>() {

    override suspend fun execute(params: Params): MaximumData? =
        repository.getMaximumDataByExerciseId(params.exerciseId)

    data class Params(
        val exerciseId: String
    )
}