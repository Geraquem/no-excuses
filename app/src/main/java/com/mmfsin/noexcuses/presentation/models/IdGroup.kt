package com.mmfsin.noexcuses.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IdGroup(
    val routineId: String,
    val dayId: String,
    var exerciseId: String? = null,
    var muscularGroup: String? = null
) : Parcelable
