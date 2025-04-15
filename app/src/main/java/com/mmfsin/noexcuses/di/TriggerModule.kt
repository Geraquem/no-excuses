package com.mmfsin.noexcuses.di

import com.mmfsin.noexcuses.presentation.maximums.trigger.TriggerManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TriggerModule {

    @Provides
    @Singleton
    fun provideTriggerModule(): TriggerManager {
        return TriggerManager(false)
    }
}