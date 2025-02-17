package com.mmfsin.noexcuses.di

import com.mmfsin.noexcuses.data.repository.CalendarRepository
import com.mmfsin.noexcuses.domain.interfaces.ICalendarRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface CalendarRepositoryModule {
    @Binds
    fun bind(repository: CalendarRepository): ICalendarRepository
}