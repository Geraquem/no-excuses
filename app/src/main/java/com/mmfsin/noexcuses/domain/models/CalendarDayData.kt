package com.mmfsin.noexcuses.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CalendarDayData(
    val databaseId: String,
    var dayName: String?,
    var dayId: String,
    var routineName: String?,
    var routineDescription: String?,
    var routineId: String
) : Parcelable