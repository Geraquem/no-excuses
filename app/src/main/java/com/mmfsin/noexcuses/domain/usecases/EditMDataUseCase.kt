package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IMaximumRepository
import com.mmfsin.noexcuses.domain.models.TempMaximumData
import javax.inject.Inject

class EditMDataUseCase @Inject constructor(
    private val repository: IMaximumRepository
) : BaseUseCase<EditMDataUseCase.Params, Unit>() {

    override suspend fun execute(params: Params) =
        repository.editMData(params.mDataId, params.tmpData)

    data class Params(
        val mDataId: String,
        val tmpData: TempMaximumData
    )
}