package com.yang.me.healthy.data

import android.util.Log
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yang.me.healthy.App
import com.yang.me.healthy.data.bean.EventDetail
import com.yang.me.healthy.data.bean.TypedEvent
import com.yang.me.healthy.data.dao.EventDetailDao
import com.yang.me.healthy.data.dao.TypedEventDao

@Database(
    entities = [
        EventDetail::class,
        TypedEvent::class
    ], version = 1, exportSchema = false
)
abstract class AppDataBase() : RoomDatabase() {

    companion object {
        private const val DB_NAME = "be_healthy.db"

        private const val TAG = "AbsAppDataBase";

        @Volatile
        private var instance: AppDataBase? = null

        fun get(): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: getDB().also {
                    instance = it
                }
            }
        }

        private fun getDB() =
            Room.databaseBuilder(App.getContext(), AppDataBase::class.java, DB_NAME)
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.i(TAG, "on create");
                    }
                })
                .allowMainThreadQueries()
                .build()

    }

    abstract fun getEventDetailDao(): EventDetailDao

    abstract fun getTypedEventDao(): TypedEventDao

}