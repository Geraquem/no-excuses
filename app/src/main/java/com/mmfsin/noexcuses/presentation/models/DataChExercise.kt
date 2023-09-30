package com.mmfsin.noexcuses.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataChExercise(
    var weight: Double? = null,
    var series: Int? = null,
    var reps: Int? = null,
    var notes: String? = null
) : Parcelable
