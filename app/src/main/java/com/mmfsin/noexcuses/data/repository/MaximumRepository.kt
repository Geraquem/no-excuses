package com.mmfsin.noexcuses.data.repository

import android.content.Context
import com.mmfsin.noexcuses.data.mappers.toExercise
import com.mmfsin.noexcuses.data.mappers.toMData
import com.mmfsin.noexcuses.data.mappers.toMaximumData
import com.mmfsin.noexcuses.data.mappers.toMaximumDataDTO
import com.mmfsin.noexcuses.data.models.ExerciseDTO
import com.mmfsin.noexcuses.data.models.MaximumDataDTO
import com.mmfsin.noexcuses.domain.interfaces.IMaximumRepository
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.MData
import com.mmfsin.noexcuses.domain.models.MaximumData
import com.mmfsin.noexcuses.domain.models.TempMaximumData
import com.mmfsin.noexcuses.utils.EXERCISE_ID
import com.mmfsin.noexcuses.utils.ID
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.kotlin.where
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MaximumRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val realmDatabase: IRealmDatabase,
) : IMaximumRepository {

    override fun registerMaximumData(data: TempMaximumData) {
        realmDatabase.addObject { data.toMaximumDataDTO() }
    }

    override fun getAllMaximumData(): List<MaximumData> {
        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy")

        val maximums = realmDatabase.getObjectsFromRealm { where<MaximumDataDTO>().findAll() }
        val result = mutableListOf<MaximumData>()
        maximums.groupBy { it.exerciseId }.map { mData ->
            val (exerciseId, data) = mData
            val exercise = getExerciseById(exerciseId)
            val list = mutableListOf<MData>()
            val sortedData = data.sortedByDescending { LocalDate.parse(it.date, formatter) }
            sortedData.forEach { list.add(it.toMData()) }
            exercise?.let { e -> result.add(MaximumData(e, list)) }
        }
        return result
    }

    private fun getExerciseById(id: String): Exercise? {
        val exercises = realmDatabase.getObjectFromRealm(ExerciseDTO::class.java, ID, id)
        return exercises?.toExercise()
    }

    override fun getMaximumDataByExerciseId(exerciseId: String): MaximumData? {
        val maximumDTOList = realmDatabase.getObjectsFromRealm {
            where<MaximumDataDTO>().equalTo(EXERCISE_ID, exerciseId).findAll()
        }

        val exercise = getExerciseById(exerciseId)
        return if (exercise != null) maximumDTOList.toMaximumData(exercise) else null
    }

    override fun getMDataById(mDataId: String): MData? {
        val data = realmDatabase.getObjectFromRealm(MaximumDataDTO::class.java, ID, mDataId)
        return data?.toMData()
    }

    override fun deleteMDataById(mDataId: String) {
        realmDatabase.deleteObject(MaximumDataDTO::class.java, ID, mDataId)
    }
}