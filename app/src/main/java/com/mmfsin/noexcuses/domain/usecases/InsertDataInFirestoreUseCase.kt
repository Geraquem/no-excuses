package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCaseNoParams
import com.mmfsin.noexcuses.domain.interfaces.IMenuRepository
import javax.inject.Inject

class InsertDataInFirestoreUseCase @Inject constructor(private val repository: IMenuRepository) :
    BaseUseCaseNoParams<Unit>() {

    override suspend fun execute() = repository.insertDataInFirestore()
}