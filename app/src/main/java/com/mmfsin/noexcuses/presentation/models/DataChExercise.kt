package com.mmfsin.noexcuses.presentation.models

import android.os.Parcelable
import com.mmfsin.noexcuses.domain.models.Data
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataChExercise(
    var dataList: List<Data>? = null,
    var time: Double? = null,
    var superSerie: Boolean = false,
    var notes: String? = null
) : Parcelable