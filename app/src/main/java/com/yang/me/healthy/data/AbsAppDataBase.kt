package com.yang.me.healthy.data

import android.content.Context
import android.util.Log
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.yang.me.healthy.App
import com.yang.me.healthy.data.bean.EventDetail
import com.yang.me.healthy.data.bean.TypedEvent

@Database(
    entities = [
        EventDetail::class,
        TypedEvent::class
    ], version = 1, exportSchema = false
)
abstract class AbsAppDataBase(context: Context) : RoomDatabase() {
    companion object {
        const val DB_NAME = "be_healthy_db"

        private val TAG = "AbsAppDataBase";

        var dataBase: AbsAppDataBase

        init {
            dataBase = Room.databaseBuilder(App.getContext(), AbsAppDataBase::class.java, DB_NAME)
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.i(TAG, "on create");
                    }
                }).build()
        }
    }


    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }

    override fun clearAllTables() {
        TODO("Not yet implemented")
    }

}