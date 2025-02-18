package com.mmfsin.noexcuses.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CalendarInfo(
    var day: Int,
    var month: Int,
    var year: Int,
    var dayId: String,
    var routineId: String
) : Parcelable