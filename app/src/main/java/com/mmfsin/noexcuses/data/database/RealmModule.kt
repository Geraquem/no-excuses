package com.mmfsin.noexcuses.data.database

import io.realm.RealmConfiguration

object RealmModule {
    fun realmConfiguration(): RealmConfiguration {
        return RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
    }
}