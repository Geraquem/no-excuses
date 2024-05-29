package com.mmfsin.noexcuses.di

import com.mmfsin.noexcuses.data.repository.StretchingRepository
import com.mmfsin.noexcuses.domain.interfaces.IStretchingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface StretchingRepositoryModule {
    @Binds
    fun bind(repository: StretchingRepository): IStretchingRepository
}