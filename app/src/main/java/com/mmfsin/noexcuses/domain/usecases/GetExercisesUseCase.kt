package com.mmfsin.noexcuses.domain.usecases

import android.content.Context
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.base.BaseUseCase
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.utils.ADD_EXERCISE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetExercisesUseCase @Inject constructor(
    @ApplicationContext val context: Context,
    private val repository: IExercisesRepository
) :
    BaseUseCase<GetExercisesUseCase.Params, List<Exercise>>() {

    override suspend fun execute(params: Params): List<Exercise> {
        val list = repository.getExercisesByMuscularGroup(params.mGroup) as MutableList<Exercise>
        list.add(
            Exercise(
                id = ADD_EXERCISE,
                category = params.mGroup,
                imageURL = "",
                gifURL = "",
                name = context.getString(R.string.exercise_add_new_one),
                description = "",
                involvedMuscles = "",
                isFav = false,
                muscleWikiURL = "",
                createdByUser = false
            )
        )
        return list
    }

    data class Params(
        val mGroup: String
    )
}