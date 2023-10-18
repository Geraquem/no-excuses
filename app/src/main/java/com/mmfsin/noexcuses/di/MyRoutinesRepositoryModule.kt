package com.mmfsin.noexcuses.di

import com.mmfsin.noexcuses.data.repository.MyRoutinesRepository
import com.mmfsin.noexcuses.domain.interfaces.IMyRoutinesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface MyRoutinesRepositoryModule {
    @Binds
    fun bind(repository: MyRoutinesRepository): IMyRoutinesRepository
}