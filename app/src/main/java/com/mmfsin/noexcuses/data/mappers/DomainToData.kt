package com.mmfsin.noexcuses.data.mappers

import android.util.Log
import com.mmfsin.noexcuses.data.models.CalendarInfoDTO
import com.mmfsin.noexcuses.data.models.ChExerciseDTO
import com.mmfsin.noexcuses.data.models.DataDTO
import com.mmfsin.noexcuses.data.models.DayDTO
import com.mmfsin.noexcuses.data.models.MaximumDataDTO
import com.mmfsin.noexcuses.data.models.MyRoutineDTO
import com.mmfsin.noexcuses.data.models.NoteDTO
import com.mmfsin.noexcuses.domain.models.CalendarInfo
import com.mmfsin.noexcuses.domain.models.ChExercise
import com.mmfsin.noexcuses.domain.models.Data
import com.mmfsin.noexcuses.domain.models.DefaultExercise
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.domain.models.TempMaximumData
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import java.util.UUID

fun toChExerciseDTO(actualPos: Int?, chE: ChExercise) = ChExerciseDTO().apply {
    id = chE.id
    routineId = chE.routineId
    dayId = chE.dayId
    exerciseId = chE.exerciseId
    data = setExerciseData(chE.data)
    time = chE.time
    notes = chE.notes
    position = actualPos ?: chE.position
    superSerie = chE.superSerie
}

fun setExerciseData(data: List<Data>?): RealmList<DataDTO>? {
    return data?.let {
        val list = realmListOf<DataDTO>()
        for (d in data) {
            list.add(dataToDataDTO(d))
        }
        list
    } ?: run { null }
}

fun dataToDataDTO(data: Data) = DataDTO().apply {
    id = data.id
    exerciseDayId = data.exerciseDayId
    reps = data.reps
    weight = data.weight
}

fun createNewRoutineFromDefault(newId: String, d: Int, r: Routine) = MyRoutineDTO().apply {
    id = newId
    title = r.name
    description = r.description
    days = d
}

fun defaultExerciseToChExerciseDTO(
    defaultExercise: DefaultExercise,
    newExerciseId: String,
    newRoutineId: String,
    newDayId: String,
    pos: Int
) = ChExerciseDTO().apply {
    id = newExerciseId
    routineId = newRoutineId
    dayId = newDayId
    exerciseId = defaultExercise.exercise.id
    data = parseDataSeries(newDayId, defaultExercise.reps)
    time = defaultExercise.desc.parseTime()
    notes = null
    position = pos
    superSerie = defaultExercise.superSerie
}

fun String.parseTime(): Double? {
    return try {
        this.toDouble()
    } catch (e: Exception) {
        null
    }
}

fun parseDataSeries(
    newDayId: String, repetitions: String
): RealmList<DataDTO> {
    val result = realmListOf<DataDTO>()
    try {
        repetitions.split(",").forEach { s ->
            val data = DataDTO().apply {
                id = UUID.randomUUID().toString()
                exerciseDayId = newDayId
                reps = s.toInt()
            }
            result.add(data)
        }

    } catch (e: Exception) {
        Log.e("*/*/*/*/*/*", "Error parsing exercise series")
    }
    return result
}

fun toCalendarInfoDTO(cI: CalendarInfo) = CalendarInfoDTO().apply {
    id = UUID.randomUUID().toString()
    date = "${cI.day}/${cI.month}/${cI.year}"
    dayId = cI.dayId
    routineId = cI.routineId
}

fun toMaximumDataDTO(tMD: TempMaximumData) = MaximumDataDTO().apply {
    id = UUID.randomUUID().toString()
    exerciseId = tMD.exerciseId
    weight = tMD.weight
    date = tMD.date
}

fun createNewMyRoutineDTO(t: String, d: String?) = MyRoutineDTO().apply {
    id = UUID.randomUUID().toString()
    title = t
    description = d
    days = 0
}

fun createDayDTO(dId: String, rId: String, t: String, numExercises: Int) = DayDTO().apply {
    id = dId
    routineId = rId
    title = t
    exercises = numExercises
}

fun createNewNote(t: String, d: String, f: Long) = NoteDTO().apply {
    id = UUID.randomUUID().toString()
    title = t
    description = d
    date = f
    pinned = false
}