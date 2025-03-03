package com.mmfsin.noexcuses.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.noexcuses.data.mappers.toDayListFromDayDTO
import com.mmfsin.noexcuses.data.mappers.toDayListFromDefaultDayDTO
import com.mmfsin.noexcuses.data.mappers.toRoutine
import com.mmfsin.noexcuses.data.models.DayDTO
import com.mmfsin.noexcuses.data.models.DefaultDayDTO
import com.mmfsin.noexcuses.data.models.DefaultRoutineDTO
import com.mmfsin.noexcuses.data.models.MuscularGroupDTO
import com.mmfsin.noexcuses.data.models.MyRoutineDTO
import com.mmfsin.noexcuses.domain.interfaces.IMenuRepository
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.Routine
import com.mmfsin.noexcuses.utils.BODY_IMAGE_WOMAN_SELECTED
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
    private val realmDatabase: IRealmDatabase
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

    override fun checkBodyImage(): Boolean {
        val sharedPrefs = context.getSharedPreferences(MY_SHARED_PREFS, Context.MODE_PRIVATE)
        return sharedPrefs.getBoolean(BODY_IMAGE_WOMAN_SELECTED, false)
    }

    override fun editBodyImage(womanImageSelected: Boolean) {
        val sharedPrefs = context.getSharedPreferences(MY_SHARED_PREFS, MODE_PRIVATE)
        sharedPrefs.edit().apply {
            putBoolean(BODY_IMAGE_WOMAN_SELECTED, womanImageSelected)
            apply()
        }
    }
}