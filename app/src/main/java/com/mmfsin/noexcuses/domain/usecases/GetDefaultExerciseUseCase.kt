package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IDefaultRoutinesRepository
import com.mmfsin.noexcuses.domain.models.DefaultExercise
import javax.inject.Inject

class GetDefaultExerciseUseCase @Inject constructor(
    private val repository: IDefaultRoutinesRepository
) : BaseUseCase<GetDefaultExerciseUseCase.Params, DefaultExercise?>() {

    override suspend fun execute(params: Params): DefaultExercise? =
        repository.getDefaultExerciseById(params.id)

    data class Params(
        val id: String
    )
}