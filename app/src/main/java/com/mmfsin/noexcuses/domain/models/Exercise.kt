package com.mmfsin.noexcuses.domain.models

open class Exercise(
    var id: String,
    var category: String,
    var imageURL: String,
    var gifURL: String,
    var name: String,
    var description: String,
    var involvedMuscles: String,
    var isFav: Boolean,
    var muscleWikiURL: String?
)
