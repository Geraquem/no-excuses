package com.mmfsin.noexcuses.presentation.calendar

import com.mmfsin.noexcuses.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
) : BaseViewModel<CalendarEvent>() {
}