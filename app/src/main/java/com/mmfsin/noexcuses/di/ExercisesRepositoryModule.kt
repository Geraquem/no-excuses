package com.mmfsin.noexcuses.di

import com.mmfsin.noexcuses.data.repository.ExercisesRepository
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface ExercisesRepositoryModule {
    @Binds
    fun bind(repository: ExercisesRepository): IExercisesRepository
}