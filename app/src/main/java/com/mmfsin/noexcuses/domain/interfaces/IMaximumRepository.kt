package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.MaximumData
import com.mmfsin.noexcuses.domain.models.TempMaximumData

interface IMaximumRepository {
    fun registerMaximumData(data: TempMaximumData)

    fun getAllMaximumData(): List<MaximumData>
    fun getMaximumDataByExerciseId(exerciseId: String): MaximumData?
}