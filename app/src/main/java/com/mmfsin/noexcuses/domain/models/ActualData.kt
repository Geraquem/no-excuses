package com.mmfsin.noexcuses.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActualData(
    var routineId: String,
    var dayId: String,
    var createdByUser: Boolean
) : Parcelable