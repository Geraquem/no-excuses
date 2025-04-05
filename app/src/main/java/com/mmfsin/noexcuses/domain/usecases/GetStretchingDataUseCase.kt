package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCaseNoParams
import com.mmfsin.noexcuses.domain.interfaces.IStretchingRepository
import com.mmfsin.noexcuses.domain.models.Stretch
import javax.inject.Inject

class GetStretchingDataUseCase @Inject constructor(
    private val repository: IStretchingRepository
) : BaseUseCaseNoParams<List<Stretch>>() {

    override suspend fun execute(): List<Stretch> = repository.getStretchingData()
}