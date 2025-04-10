package com.mmfsin.noexcuses.domain.models

data class MaximumData(
    val exercise: Exercise,
    val data: List<MData>
)

data class MData(
    val id: String,
    val weight: String,
    val date: String
)

data class TempMaximumData(
    val exerciseId: String,
    val weight: String,
    val date: String
)

