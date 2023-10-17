package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCaseNoParams
import com.mmfsin.noexcuses.domain.interfaces.IMenuRepository
import javax.inject.Inject

class FirstTimeUseCase @Inject constructor(private val repository: IMenuRepository) :
    BaseUseCaseNoParams<Boolean>() {

    override suspend fun execute(): Boolean = repository.isFirstTime()
}