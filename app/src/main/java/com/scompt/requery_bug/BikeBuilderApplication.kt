package com.scompt.requery_bug

import android.app.Activity
import android.app.Application
import io.requery.Entity
import io.requery.Generated
import io.requery.Key
import io.requery.Persistable
import io.requery.android.sqlite.DatabaseSource
import io.requery.meta.EntityModelBuilder
import io.requery.reactivex.KotlinReactiveEntityStore
import io.requery.sql.KotlinEntityDataStore
import io.requery.sql.TableCreationMode
import java.util.ArrayList


@Entity
data class ExampleEntity (
        @get:Key @get:Generated
        val id: Long,

        val name: String?
) : Persistable

class BugApplication() : Application() {
    override fun onCreate() {
        super.onCreate()

        val source = DatabaseSource(this, Models.DEFAULT, "example", 1)
        if (BuildConfig.DEBUG) {
            // use this in development mode to drop and recreate the tables on every upgrade
            source.setTableCreationMode(TableCreationMode.DROP_CREATE)
            source.setLoggingEnabled(true)
        }
        val configuration = source.configuration
        val entityStore = KotlinReactiveEntityStore(KotlinEntityDataStore<Persistable>(configuration))

        entityStore.insert(ExampleEntity(5, null), Long::class).subscribe()
    }
}

class MyActivity() : Activity() {
}