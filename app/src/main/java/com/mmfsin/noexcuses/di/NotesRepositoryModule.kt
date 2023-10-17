package com.mmfsin.noexcuses.di

import com.mmfsin.noexcuses.data.repository.NotesRepository
import com.mmfsin.noexcuses.domain.interfaces.INotesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface NotesRepositoryModule {
    @Binds
    fun bind(repository: NotesRepository): INotesRepository
}