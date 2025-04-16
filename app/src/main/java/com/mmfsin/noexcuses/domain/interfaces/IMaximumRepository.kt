package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.MData
import com.mmfsin.noexcuses.domain.models.MaximumData
import com.mmfsin.noexcuses.domain.models.TempMaximumData

interface IMaximumRepository {
    fun registerMaximumData(data: TempMaximumData)
    fun deleteMaximumData(exerciseId: String)

    fun getAllMaximumData(): List<MaximumData>
    fun getMaximumDataByExerciseId(exerciseId: String): MaximumData?
    fun getMDataById(mDataId: String): MData?
    fun editMData(mDataId: String, data: TempMaximumData)
    fun deleteMDataById(mDataId: String)
}