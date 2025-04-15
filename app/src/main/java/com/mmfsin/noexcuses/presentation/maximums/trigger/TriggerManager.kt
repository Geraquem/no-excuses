package com.mmfsin.noexcuses.presentation.maximums.trigger

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TriggerManager @Inject constructor(triggerValue: Boolean) {
    private val _trigger = MutableLiveData<Boolean>().apply {
        value = triggerValue
    }

    val trigger: LiveData<Boolean> = _trigger

    fun updateTrigger() {
        val value = getTriggerValue()
        value?.let { _trigger.value = !it }
    }

    private fun getTriggerValue() = _trigger.value
}