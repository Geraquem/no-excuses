package com.mmfsin.noexcuses.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    var id: String? = null,
    var pos: String? = null,
    var exerciseDayId: String? = null,
    var reps: Int? = null,
    var weight: Double? = null
) : Parcelable