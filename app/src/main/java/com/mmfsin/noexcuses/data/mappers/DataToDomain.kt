package com.mmfsin.noexcuses.data.mappers

import com.mmfsin.noexcuses.data.models.ChExerciseDTO
import com.mmfsin.noexcuses.data.models.DataDTO
import com.mmfsin.noexcuses.data.models.DayDTO
import com.mmfsin.noexcuses.data.models.DefaultDayDTO
import com.mmfsin.noexcuses.data.models.DefaultExerciseDTO
import com.mmfsin.noexcuses.data.models.DefaultRoutineDTO
import com.mmfsin.noexcuses.data.models.ExerciseDTO
import com.mmfsin.noexcuses.data.models.MuscularGroupDTO
import com.mmfsin.noexcuses.data.models.MyRoutineDTO
import com.mmfsin.noexcuses.data.models.NoteDTO
import com.mmfsin.noexcuses.data.models.StretchingDTO
import com.mmfsin.noexcuses.domain.models.ChExercise
import com.mmfsin.noexcuses.domain.models.CompactExercise
import com.mmfsin.noexcuses.domain.models.Data
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.DefaultExercise
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.domain.models.Note
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.domain.models.Stretching
import com.mmfsin.noexcuses.utils.formatTime
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/** MuscularGroup */
fun MuscularGroupDTO.toMuscularGroup() = MuscularGroup(
    id = id,
    name = name,
    manImageURL = manImageURL,
    womanImageURL = womanImageURL,
)

fun List<MuscularGroupDTO>.toMuscularGroupList() = this.map { it.toMuscularGroup() }

/** Exercise */
fun ExerciseDTO.toExercise() = Exercise(
    id = id,
    category = category,
    imageURL = imageURL,
    gifURL = gifURL,
    name = name,
    description = description,
    involvedMuscles = muscles,
    isFav = isFav,
    muscleWikiURL = muscleWikiURL,
    createdByUser = createdByUser
)

fun List<ExerciseDTO>.toExerciseList() = this.map { it.toExercise() }

/** My Routine */
fun MyRoutineDTO.toRoutine() = Routine(
    id = id,
    name = title,
    description = description,
    days,
    doingIt = doingIt,
    createdByUser = true
)

fun List<MyRoutineDTO>.toMyRoutineList() = this.map { it.toRoutine() }

/** Default Routine */
fun DefaultRoutineDTO.toRoutine() = Routine(
    id = id,
    name = name,
    description = description,
    days = days.toInt(),
    doingIt = doingIt,
    createdByUser = false
)

fun List<DefaultRoutineDTO>.toDefaultRoutineList() = this.map { it.toRoutine() }

/** Day */
fun DefaultDayDTO.toDay() = Day(
    id = id,
    routineId = routineId,
    title = name,
    exercises = exercises.toInt()
)

fun DayDTO.toDay() = Day(
    id = id,
    routineId = routineId,
    title = title,
    exercises = exercises
)

fun List<DayDTO>.toDayListFromDayDTO() = this.map { it.toDay() }
fun List<DefaultDayDTO>.toDayListFromDefaultDayDTO() = this.map { it.toDay() }

/** Default Exercise */
fun DefaultExerciseDTO.toDefaultExercise(exercise: Exercise) = DefaultExercise(
    id = id,
    exercise = exercise,
    dayId = dayId,
    desc = desc,
    reps = reps,
    series = reps.split(",").size.toString(),
    superSerie = superSerie
)

/** ChExercise */
fun ChExerciseDTO.toChExercise() = ChExercise(
    id = id,
    routineId = routineId,
    dayId = dayId,
    exerciseId = exerciseId,
    data = data?.toDataList(),
    time = time,
    notes = notes,
    position = position,
    superSerie = superSerie
)

fun ChExerciseDTO.toCompactExercise(e: Exercise) = CompactExercise(
    chExerciseId = id,
    name = e.name,
    category = e.category,
    imageURL = e.imageURL,
    gifURL = e.gifURL,
    series = data?.size,
    time = time.formatTime(),
    hasNotes = !notes.isNullOrEmpty(),
    position = position,
    superSerie = superSerie
)

/** Data */
fun DataDTO.toData() = Data(
    id = id,
    exerciseDayId = exerciseDayId,
    reps = reps,
    weight = weight
)

fun List<DataDTO>.toDataList() = this.map { it.toData() }

/** Notes */
fun NoteDTO.toNote() = Note(
    id = id,
    title = title,
    description = description,
    date = formatDate(date),
    pinned = pinned
)

private fun formatDate(date: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(Date(date))
}

fun List<NoteDTO>.toNoteList() = this.map { it.toNote() }

/** Stretching */

fun StretchingDTO.toStretching() = Stretching(
    image1URL = imageURL,
    image2URL = image2URL,
    description = description,
    order = order
)

fun List<StretchingDTO>.toStretching() = this.map { it.toStretching() }

fun List<String>.toCalendarDayList(): List<CalendarDay> {
    val result = mutableListOf<CalendarDay>()
    this.forEach { date ->
        val splitDate = date.split("/")
        if (splitDate.size == 3) {
            result.add(
                CalendarDay.from(
                    splitDate[2].toInt(),
                    splitDate[1].toInt(),
                    splitDate[0].toInt()
                )
            )
        }
    }
    return result
}