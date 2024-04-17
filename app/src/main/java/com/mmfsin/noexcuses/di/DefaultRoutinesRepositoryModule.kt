package com.mmfsin.noexcuses.di

import com.mmfsin.noexcuses.data.repository.DefaultRoutinesRepository
import com.mmfsin.noexcuses.domain.interfaces.IDefaultRoutinesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DefaultRoutinesRepositoryModule {
    @Binds
    fun bind(repository: DefaultRoutinesRepository): IDefaultRoutinesRepository
}