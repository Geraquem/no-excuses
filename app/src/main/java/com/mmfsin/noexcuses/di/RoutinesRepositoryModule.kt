package com.mmfsin.noexcuses.di

import com.mmfsin.noexcuses.data.repository.RoutinesRepository
import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RoutinesRepositoryModule {
    @Binds
    fun bind(repository: RoutinesRepository): IRoutinesRepository
}