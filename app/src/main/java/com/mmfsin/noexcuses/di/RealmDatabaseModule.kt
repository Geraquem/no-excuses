package com.mmfsin.noexcuses.di

import com.mmfsin.noexcuses.data.database.RealmDatabase
import com.mmfsin.noexcuses.data.models.CalendarInfoDTO
import com.mmfsin.noexcuses.data.models.ChExerciseDTO
import com.mmfsin.noexcuses.data.models.DataDTO
import com.mmfsin.noexcuses.data.models.DayDTO
import com.mmfsin.noexcuses.data.models.DefaultDayDTO
import com.mmfsin.noexcuses.data.models.DefaultExerciseDTO
import com.mmfsin.noexcuses.data.models.DefaultRoutineDTO
import com.mmfsin.noexcuses.data.models.ExerciseDTO
import com.mmfsin.noexcuses.data.models.MaximumDataDTO
import com.mmfsin.noexcuses.data.models.MuscularGroupDTO
import com.mmfsin.noexcuses.data.models.MyRoutineDTO
import com.mmfsin.noexcuses.data.models.NoteDTO
import com.mmfsin.noexcuses.data.models.StretchingDTO
import com.mmfsin.noexcuses.domain.interfaces.IRealmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.components.ViewModelComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

@Module
@InstallIn(ViewModelComponent::class, ServiceComponent::class)
object RealmDatabaseModule {

    @Provides
    fun provideRealmDatabase(): IRealmDatabase {
        val config = RealmConfiguration.Builder(
            schema = setOf(
                CalendarInfoDTO::class,
                ChExerciseDTO::class,
                DataDTO::class,
                DayDTO::class,
                DefaultRoutineDTO::class,
                DefaultDayDTO::class,
                DefaultExerciseDTO::class,
                ExerciseDTO::class,
                MaximumDataDTO::class,
                MuscularGroupDTO::class,
                MyRoutineDTO::class,
                NoteDTO::class,
                StretchingDTO::class
            )
        ).schemaVersion(3).build()

        val realm = Realm.open(config)

        migrateMyRoutine(realm)

        return RealmDatabase(realm)
    }

    private fun migrateMyRoutine(realm: Realm) {
        realm.writeBlocking {
            val myRoutines = query(MyRoutineDTO::class).find()
            myRoutines.forEach { oldRoutine ->
                if (oldRoutine.pinnedDate == null) {
                    oldRoutine.pinnedDate = null
                }
            }

            val dfRoutines = query(DefaultRoutineDTO::class).find()
            dfRoutines.forEach { oldRoutine ->
                if (oldRoutine.pinnedDate == null) {
                    oldRoutine.pinnedDate = null
                }
            }
        }
    }
}