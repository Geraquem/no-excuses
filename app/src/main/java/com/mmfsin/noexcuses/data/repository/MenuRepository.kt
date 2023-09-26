package com.mmfsin.noexcuses.data.repository

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.noexcuses.data.models.ExerciseDTO
import com.mmfsin.noexcuses.data.models.MuscularGroupDTO
import com.mmfsin.noexcuses.data.models.VersionDTO
import com.mmfsin.noexcuses.domain.interfaces.IMenuRepository
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.utils.EXERCISES
import com.mmfsin.noexcuses.utils.M_GROUPS
import com.mmfsin.noexcuses.utils.VERSION
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class MenuRepository @Inject constructor(
    private val realmDatabase: IRealmDatabase
) : IMenuRepository {

    private val reference = Firebase.database.reference

    override suspend fun checkVersion() {
        val savedVersion = realmDatabase.getObjectsFromRealm { where<VersionDTO>().findAll() }
        val actualVersion = if (savedVersion.isEmpty()) -1 else savedVersion.first().version
        getDataFromFirebase(actualVersion)
    }

    private suspend fun getDataFromFirebase(savedVersion: Long) {
        val latch = CountDownLatch(1)
        reference.get().addOnSuccessListener {
            val version = it.child(VERSION).value as Long
            realmDatabase.addObject { VersionDTO(VERSION, version) }
            if (version == savedVersion) {
                latch.countDown()
            } else {
                val fbMGroups = it.child(M_GROUPS)
                for (child in fbMGroups.children) {
                    child.getValue(MuscularGroupDTO::class.java)?.let { deck ->
                        saveMGroupsInRealm(deck)
                    }
                }

                val fbExercises = it.child(EXERCISES)
                for (child in fbExercises.children) {
                    for (exercise in child.children) {
                        exercise.getValue(ExerciseDTO::class.java)?.let { e ->
                            saveExerciseInRealm(e)
                        }
                    }
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

    private fun saveMGroupsInRealm(mGroup: MuscularGroupDTO) = realmDatabase.addObject { mGroup }
    private fun saveExerciseInRealm(exercise: ExerciseDTO) = realmDatabase.addObject { exercise }
}