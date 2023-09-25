package com.mmfsin.noexcuses.di

import com.mmfsin.noexcuses.data.repository.MenuRepository
import com.mmfsin.noexcuses.domain.interfaces.IMenuRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface MenuRepositoryModule {
    @Binds
    fun bind(repository: MenuRepository): IMenuRepository
}