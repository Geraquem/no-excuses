package com.mmfsin.noexcuses.data.repository

import android.content.Context
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mmfsin.noexcuses.data.mappers.toStretching
import com.mmfsin.noexcuses.data.models.StretchingDTO
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.interfaces.IStretchingRepository
import com.mmfsin.noexcuses.domain.models.Stretching
import com.mmfsin.noexcuses.utils.CATEGORY
import com.mmfsin.noexcuses.utils.MY_SHARED_PREFS
import com.mmfsin.noexcuses.utils.SERVER_STRETCHING
import com.mmfsin.noexcuses.utils.STRETCHING
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class StretchingRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val realmDatabase: IRealmDatabase
) : IStretchingRepository {

    override suspend fun getStretching(category: String): List<Stretching> {
        val latch = CountDownLatch(1)
        val sharedPrefs = context.getSharedPreferences(MY_SHARED_PREFS, Context.MODE_PRIVATE)

//        if (sharedPrefs.getBoolean(SERVER_STRETCHING, true)) {
        if (true) {
            realmDatabase.deleteAllObjects(StretchingDTO::class.java)
            val stretches = mutableListOf<StretchingDTO>()
            Firebase.database.reference.child(STRETCHING).get().addOnSuccessListener {
                for (mgroup in it.children) {
                    for (child in mgroup.children) {
                        child.getValue(StretchingDTO::class.java)?.let { stretchingDTO ->
                            saveStretchingInRealm(stretchingDTO)
                            stretches.add(stretchingDTO)
                        }
                    }
                }
                sharedPrefs.edit().apply {
                    putBoolean(SERVER_STRETCHING, false)
                    apply()
                }
                latch.countDown()

            }.addOnFailureListener {
                latch.countDown()
            }

            withContext(Dispatchers.IO) { latch.await() }
            val selection = stretches.filter { it.category == category }
            return selection.toStretching().sortedBy { it.order }

        } else {
            return realmDatabase.getObjectsFromRealm {
                where<StretchingDTO>().equalTo(CATEGORY, category).findAll()
            }.toStretching().sortedBy { it.order }
        }
    }

    private fun saveStretchingInRealm(stretching: StretchingDTO) =
        realmDatabase.addObject { stretching }

}