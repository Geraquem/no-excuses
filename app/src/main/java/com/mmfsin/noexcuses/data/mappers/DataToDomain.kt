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
import com.mmfsin.noexcuses.domain.models.ChExercise
import com.mmfsin.noexcuses.domain.models.CompactExercise
import com.mmfsin.noexcuses.domain.models.Data
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.DefaultExercise
import com.mmfsin.noexcuses.domain.models.DefaultRoutine
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.domain.models.MyRoutine
import com.mmfsin.noexcuses.domain.models.Note
import com.mmfsin.noexcuses.utils.formatTime

/** MuscularGroup */
fun MuscularGroupDTO.toMuscularGroup() = MuscularGroup(name, imageURL)

fun List<MuscularGroupDTO>.toMuscularGroupList() = this.map { it.toMuscularGroup() }

/** Exercise */
fun ExerciseDTO.toExercise() = Exercise(id, category, imageURL, name, dataURL)

fun List<ExerciseDTO>.toExerciseList() = this.map { it.toExercise() }

/** My Routine */
fun MyRoutineDTO.toMyRoutine() = MyRoutine(id, title, description, days)

fun List<MyRoutineDTO>.toMyRoutineList() = this.map { it.toMyRoutine() }

/** Default Routine */
fun DefaultRoutineDTO.toDefaultRoutine() = DefaultRoutine(
    id = id,
    title = name,
    description = description,
    days = days
)

fun List<DefaultRoutineDTO>.toDefaultRoutineList() = this.map { it.toDefaultRoutine() }

private fun String.daysCount(): Int = this.split(",").size

/** Day */
fun DefaultDayDTO.toDay() = Day(
    id = id,
    routineId = routineId,
    title = name,
    exercises = exercises.toInt()
)

fun DayDTO.toDay() = Day(id, routineId, title, exercises)

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
fun ChExerciseDTO.toChExercise() =
    ChExercise(id, routineId, dayId, exerciseId, data?.toDataList(), time, notes)

fun List<ChExerciseDTO>.toChExerciseList() = this.map { it.toChExercise() }

fun ChExerciseDTO.toCompactExercise(e: Exercise) = CompactExercise(
    id, e.name, e.category, e.imageURL, data?.size, time.formatTime(), hasNotes(this.notes)
)

private fun hasNotes(notes: String?): Boolean {
    return !notes.isNullOrEmpty()
}

/** Data */
fun DataDTO.toData() = Data(id, exerciseDayId, reps, weight)
fun List<DataDTO>.toDataList() = this.map { it.toData() }

/** Notes */
fun NoteDTO.toNote() = Note(id, title, description, formatDate(date))

private fun formatDate(date: String): String {
    return "jeje"
}

fun List<NoteDTO>.toNoteList() = this.map { it.toNote() }