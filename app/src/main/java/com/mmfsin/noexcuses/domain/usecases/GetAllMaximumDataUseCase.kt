package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCaseNoParams
import com.mmfsin.noexcuses.domain.interfaces.IMaximumRepository
import com.mmfsin.noexcuses.domain.models.MaximumData
import javax.inject.Inject

class GetAllMaximumDataUseCase @Inject constructor(
    private val repository: IMaximumRepository
) : BaseUseCaseNoParams<List<MaximumData>>() {

    override suspend fun execute(): List<MaximumData> = repository.getAllMaximumData()

}