package com.mmfsin.noexcuses.data.repository

import android.content.Context
import com.mmfsin.noexcuses.data.mappers.toStretching
import com.mmfsin.noexcuses.data.models.StretchingDTO
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import com.mmfsin.noexcuses.domain.interfaces.IStretchingRepository
import com.mmfsin.noexcuses.domain.models.Stretching
import com.mmfsin.noexcuses.utils.CATEGORY
import dagger.hilt.android.qualifiers.ApplicationContext
import io.realm.kotlin.where
import javax.inject.Inject

class StretchingRepository @Inject constructor(
    @ApplicationContext val context: Context,
    private val realmDatabase: IRealmDatabase
) : IStretchingRepository {

    override fun getStretching(category: String): List<Stretching> {
        return realmDatabase.getObjectsFromRealm {
            where<StretchingDTO>().equalTo(CATEGORY, category).findAll()
        }.toStretching()
    }
}