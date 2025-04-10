package com.mmfsin.noexcuses.data.mappers

import android.util.Log
import com.mmfsin.noexcuses.data.models.CalendarInfoDTO
import com.mmfsin.noexcuses.data.models.ChExerciseDTO
import com.mmfsin.noexcuses.data.models.DataDTO
import com.mmfsin.noexcuses.data.models.MaximumDataDTO
import com.mmfsin.noexcuses.data.models.MyRoutineDTO
import com.mmfsin.noexcuses.domain.models.CalendarInfo
import com.mmfsin.noexcuses.domain.models.ChExercise
import com.mmfsin.noexcuses.domain.models.Data
import com.mmfsin.noexcuses.domain.models.DefaultExercise
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.domain.models.TempMaximumData
import io.realm.RealmList
import java.util.UUID

fun ChExercise.toChExerciseDTO(actualPos: Int?) =
    ChExerciseDTO(
        id,
        routineId,
        dayId,
        exerciseId,
        data = setExerciseData(this.data),
        time,
        notes,
        position = actualPos ?: this.position,
        superSerie = this.superSerie
    )

fun setExerciseData(data: List<Data>?): RealmList<DataDTO>? {
    return data?.let {
        val list = RealmList<DataDTO>()
        for (d in data) {
            list.add(d.toDataDTO())
        }
        list
    } ?: run { null }
}

fun Data.toDataDTO() = DataDTO(id, exerciseDayId, reps, weight)

fun Routine.createNewRoutineFromDefault(newId: String, days: Int) = MyRoutineDTO(
    id = newId,
    title = this.name,
    description = this.description,
    days = days
)

fun DefaultExercise.toChExerciseDTO(
    newExerciseId: String,
    newRoutineId: String,
    newDayId: String,
    position: Int
) = ChExerciseDTO(
    id = newExerciseId,
    routineId = newRoutineId,
    dayId = newDayId,
    exerciseId = this.exercise.id,
    data = parseDataSeries(newDayId, this.reps),
    time = this.desc.parseTime(),
    notes = null,
    position = position,
    superSerie = this.superSerie
)

fun String.parseTime(): Double? {
    return try {
        this.toDouble()
    } catch (e: Exception) {
        null
    }
}

fun parseDataSeries(
    newDayId: String,
    reps: String
): RealmList<DataDTO> {
    val result = RealmList<DataDTO>()
    try {
        reps.split(",").forEach { s ->
            val data = DataDTO(
                id = UUID.randomUUID().toString(),
                exerciseDayId = newDayId,
                reps = s.toInt()
            )
            result.add(data)
        }

    } catch (e: Exception) {
        Log.e("*/*/*/*/*/*", "Error parsing exercise series")
    }
    return result
}

fun CalendarInfo.toCalendarInfoDTO() = CalendarInfoDTO(
    id = UUID.randomUUID().toString(),
    date = "$day/$month/$year",
    dayId = dayId,
    routineId = routineId
)

fun TempMaximumData.toMaximumDataDTO() = MaximumDataDTO(
    id = UUID.randomUUID().toString(),
    exerciseId = exerciseId,
    weight = weight,
    date = date
)