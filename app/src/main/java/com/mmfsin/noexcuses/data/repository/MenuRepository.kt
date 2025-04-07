package com.mmfsin.noexcuses.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mmfsin.noexcuses.data.mappers.toDayListFromDayDTO
import com.mmfsin.noexcuses.data.mappers.toDayListFromDefaultDayDTO
import com.mmfsin.noexcuses.data.mappers.toRoutine
import com.mmfsin.noexcuses.data.models.DayDTO
import com.mmfsin.noexcuses.data.models.DefaultDayDTO
import com.mmfsin.noexcuses.data.models.DefaultRoutineDTO
import com.mmfsin.noexcuses.data.models.MuscularGroupDTO
import com.mmfsin.noexcuses.data.models.MyRoutineDTO
import com.mmfsin.noexcuses.data.models.NoteDTO
import com.mmfsin.noexcuses.domain.interfaces.IMenuRepository
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.utils.BODY_IMAGE_WOMAN_SELECTED
import com.mmfsin.noexcuses.utils.DAYS
import com.mmfsin.noexcuses.utils.DEFAULT_ROUTINES
import com.mmfsin.noexcuses.utils.EXERCISES
import com.mmfsin.noexcuses.utils.ID
import com.mmfsin.noexcuses.utils.MY_SHARED_PREFS
import com.mmfsin.noexcuses.utils.M_GROUPS
import com.mmfsin.noexcuses.utils.ROUTINE_DOING_IT
import com.mmfsin.noexcuses.utils.ROUTINE_ID
import com.mmfsin.noexcuses.utils.SAVED_VERSION
import com.mmfsin.noexcuses.utils.SERVER_DEFAULT_ROUTINES
import com.mmfsin.noexcuses.utils.SERVER_EXERCISES
import com.mmfsin.noexcuses.utils.SERVER_STRETCHING
import com.mmfsin.noexcuses.utils.VERSION
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class MenuRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val realmDatabase: IRealmDatabase,
) : IMenuRepository {

    private val reference = Firebase.database.reference

    override suspend fun checkVersion() {
        getDataFromFirebase(getSavedVersion())
    }

    private suspend fun getDataFromFirebase(savedVersion: Long) {
        val latch = CountDownLatch(1)
        reference.get().addOnSuccessListener {
            val version = it.child(VERSION).value as Long
            if (version == savedVersion) {
                latch.countDown()
            } else {
                saveVersion(newVersion = version)
                deleteSystemData()

                val fbMGroups = it.child(M_GROUPS)
                for (child in fbMGroups.children) {
                    child.getValue(MuscularGroupDTO::class.java)
                        ?.let { mGroup -> saveMGroups(mGroup) }
                }

                latch.countDown()
            }
        }.addOnFailureListener {
            latch.countDown()
        }

        withContext(Dispatchers.IO) {
            latch.await()
        }
    }

    private fun saveVersion(newVersion: Long) {
        val editor = getSharedPreferences().edit()
        editor.putLong(SAVED_VERSION, newVersion)
        editor.apply()
    }

    private fun getSavedVersion(): Long = getSharedPreferences().getLong(SAVED_VERSION, -1)

    private fun deleteSystemData() {
        val sharedPrefs = context.getSharedPreferences(MY_SHARED_PREFS, MODE_PRIVATE)
        sharedPrefs.edit().apply {
            putBoolean(SERVER_EXERCISES, true)
            putBoolean(SERVER_DEFAULT_ROUTINES, true)
            putBoolean(SERVER_STRETCHING, true)
            apply()
        }
    }

    private fun saveMGroups(mGroup: MuscularGroupDTO) = realmDatabase.addObject { mGroup }

    private fun getSharedPreferences() = context.getSharedPreferences(MY_SHARED_PREFS, MODE_PRIVATE)

    override fun getMyActualRoutine(): Routine? {
        val myRoutines = realmDatabase.getObjectsFromRealm {
            where<MyRoutineDTO>().equalTo(ROUTINE_DOING_IT, true).findAll()
        }
        if (myRoutines.isNotEmpty()) return myRoutines.first().toRoutine()

        val dfRoutines = realmDatabase.getObjectsFromRealm {
            where<DefaultRoutineDTO>().equalTo(ROUTINE_DOING_IT, true).findAll()
        }
        if (dfRoutines.isNotEmpty()) return dfRoutines.first().toRoutine()
        return null
    }

    override fun getMyActualRoutineDays(routineId: String): List<Day> {
        val routine = getMyActualRoutine()
        routine?.let {
            if (routine.createdByUser) {
                val days = realmDatabase.getObjectsFromRealm {
                    where<DayDTO>().equalTo(ROUTINE_ID, routineId).findAll()
                }
                return days.toDayListFromDayDTO()
            } else {
                val days = realmDatabase.getObjectsFromRealm {
                    where<DefaultDayDTO>().equalTo(ROUTINE_ID, routineId).findAll()
                }
                return days.toDayListFromDefaultDayDTO()
            }
        }
        return emptyList()
    }

    override fun unpinRoutineFromMenu(routineId: String) {
        val dfRoutine =
            realmDatabase.getObjectFromRealm(DefaultRoutineDTO::class.java, ID, routineId)
        dfRoutine?.let { routine ->
            routine.doingIt = false
            realmDatabase.addObject { routine }
        }
        val mRoutine = realmDatabase.getObjectFromRealm(MyRoutineDTO::class.java, ID, routineId)
        mRoutine?.let { routine ->
            routine.doingIt = false
            realmDatabase.addObject { routine }
        }
    }

    override fun unpinNoteFromMenu(noteId: String) {
        val note = realmDatabase.getObjectFromRealm(NoteDTO::class.java, ID, noteId)
        note?.let { n ->
            n.pinned = false
            realmDatabase.addObject { note }
        }
    }

    override fun checkBodyImage(): Boolean {
        val sharedPrefs = context.getSharedPreferences(MY_SHARED_PREFS, MODE_PRIVATE)
        return sharedPrefs.getBoolean(BODY_IMAGE_WOMAN_SELECTED, false)
    }

    override fun editBodyImage(womanImageSelected: Boolean) {
        val sharedPrefs = context.getSharedPreferences(MY_SHARED_PREFS, MODE_PRIVATE)
        sharedPrefs.edit().apply {
            putBoolean(BODY_IMAGE_WOMAN_SELECTED, womanImageSelected)
            apply()
        }
    }

    /*****************************************************************************************************************************************/
    override suspend fun insertDataInFirestore() {
        val db = Firebase.firestore
        val batch = db.batch()

        val routineId = "routine_7"
        val dayId = "r7d3"

        val listToInsert = getExercisesToInsert(dayId)
//        val listToInsert = getDaysToInsert(routineId)

        val latch = CountDownLatch(1)

        val usersCollection = db.collection(DEFAULT_ROUTINES).document(routineId)
            .collection(DAYS)
            .document(dayId)
            .collection(EXERCISES)

        for (user in listToInsert) {
            val newDocRef = user["id"]?.let { usersCollection.document(it) }
            if (newDocRef != null) {
                batch.set(newDocRef, user)
            }
        }

        batch.commit()
            .addOnSuccessListener {
                latch.countDown()
            }
            .addOnFailureListener {
                latch.countDown()
            }
        withContext(Dispatchers.IO) { latch.await() }
    }

    private fun getDaysToInsert(routineId: String): List<HashMap<String, String>> {
        return listOf(
            hashMapOf(
                "id" to "r7d1",
                "routineId" to routineId,
                "name" to "Piernas y Core",
                "exercises" to "2000000000000000000",
            ),
            hashMapOf(
                "id" to "r7d2",
                "routineId" to routineId,
                "name" to "Pecho, Hombros y Tríceps",
                "exercises" to "2000000000000000000",
            ),
            hashMapOf(
                "id" to "r7d3",
                "routineId" to routineId,
                "name" to "Espalda, Bíceps y Core",
                "exercises" to "2000000000000000000",
            ),
//            hashMapOf(
//                "id" to "r8d4",
//                "routineId" to routineId,
//                "name" to "Pierna y abdomen",
//                "exercises" to "2000000000000000000",
//            ),
//            hashMapOf(
//                "id" to "r8d5",
//                "routineId" to routineId,
//                "name" to "TORSO (Pecho, Espalda, Hombro y Brazos)",
//                "exercises" to "2000000000000000000",
//            )
        )
    }

    private fun getExercisesToInsert(day: String): List<HashMap<String, String>> {
        return listOf(
            hashMapOf(
                "dayId" to day,
                "id" to "${day}e1",
                "exerciseId" to "espalda2",
                "reps" to "12,12,15,15",
                "desc" to "1.30",
            ),
            hashMapOf(
                "dayId" to day,
                "id" to "${day}e2",
                "exerciseId" to "espalda5",
                "reps" to "12,12,15,15",
                "desc" to "1.30",
            ),
            hashMapOf(
                "dayId" to day,
                "id" to "${day}e3",
                "exerciseId" to "espalda11",
                "reps" to "12,12,15,15",
                "desc" to "1.30",
            ),
            hashMapOf(
                "dayId" to day,
                "id" to "${day}e4",
                "exerciseId" to "espalda17",
                "reps" to "12,12,15,15",
                "desc" to "1.30",
            ),
            hashMapOf(
                "dayId" to day,
                "id" to "${day}e5",
                "exerciseId" to "biceps12",
                "reps" to "12,12,15,15",
                "desc" to "1.30",
            ),
            hashMapOf(
                "dayId" to day,
                "id" to "${day}e6",
                "exerciseId" to "biceps2",
                "reps" to "12,12,15,15",
                "desc" to "1.30",
            ),
            hashMapOf(
                "dayId" to day,
                "id" to "${day}e7",
                "exerciseId" to "core15",
                "reps" to "10,10,10,10",
                "desc" to "1",
            ),
            hashMapOf(
                "dayId" to day,
                "id" to "${day}e8",
                "exerciseId" to "cardio5",
                "reps" to "20'",
                "desc" to "-",
            )
        )
    }
    /*****************************************************************************************************************************************/
}