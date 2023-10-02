package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCaseNoParams
import com.mmfsin.noexcuses.domain.models.Data
import javax.inject.Inject

class SetUpAddDataRVUseCase @Inject constructor() :
    BaseUseCaseNoParams<List<Data>>() {

    override suspend fun execute(): List<Data> {
        /** 4 vacíos porque el primero es el Header y otros 3 de inicio */
        val list = mutableListOf<Data>().apply {
            add(Data())
            add(Data())
            add(Data())
            add(Data())
        }
        return list
    }
}