package com.mmfsin.noexcuses.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    var id: String?,
    var reps: Int?,
    var weight: Double?
) : Parcelable