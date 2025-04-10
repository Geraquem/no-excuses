package com.mmfsin.noexcuses.di

import com.mmfsin.noexcuses.data.repository.MaximumRepository
import com.mmfsin.noexcuses.domain.interfaces.IMaximumRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface MaximumRepositoryModule {
    @Binds
    fun bind(repository: MaximumRepository): IMaximumRepository
}