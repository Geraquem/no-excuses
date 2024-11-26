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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/** MuscularGroup */
fun MuscularGroupDTO.toMuscularGroup() = MuscularGroup(
    id = id,
    name = name,
    imageURL = imageURL
)

fun List<MuscularGroupDTO>.toMuscularGroupList() = this.map { it.toMuscularGroup() }

/** Exercise */
fun ExerciseDTO.toExercise() = Exercise(
    id = id,
    category = category,
    imageURL = imageURL,
    name = name,
    description = description,
    involvedMuscles = muscles,
    isFav = isFav,
    muscleWikiURL = muscleWikiURL
)

fun List<ExerciseDTO>.toExerciseList() = this.map { it.toExercise() }

/** My Routine */
fun MyRoutineDTO.toMyRoutine() = Routine(
    id = id,
    title = title,
    description = description,
    days,
    doingIt = doingIt,
    createdByUser = true
)

fun List<MyRoutineDTO>.toMyRoutineList() = this.map { it.toMyRoutine() }

/** Default Routine */
fun DefaultRoutineDTO.toDefaultRoutine() = Routine(
    id = id,
    title = name,
    description = description,
    days = days.toInt(),
    doingIt = doingIt,
    createdByUser = false
)

fun List<DefaultRoutineDTO>.toDefaultRoutineList() = this.map { it.toDefaultRoutine() }

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
    series = reps.split(",").size.toString()
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
    position = position
)

fun ChExerciseDTO.toCompactExercise(e: Exercise) = CompactExercise(
    chExerciseId = id,
    name = e.name,
    category = e.category,
    imageURL = e.imageURL,
    series = data?.size,
    time = time.formatTime(),
    hasNotes = !notes.isNullOrEmpty(),
    position = position
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