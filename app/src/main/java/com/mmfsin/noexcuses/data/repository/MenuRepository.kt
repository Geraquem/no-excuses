package com.mmfsin.noexcuses.data.repository

import com.mmfsin.noexcuses.domain.interfaces.IMenuRepository
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import javax.inject.Inject

class MenuRepository @Inject constructor(
    private val realmDatabase: IRealmDatabase
) : IMenuRepository {

    override fun checkVersion() {
    }
}