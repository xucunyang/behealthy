package com.yang.me.healthy.data.dao

import androidx.room.*
import com.yang.me.healthy.data.bean.EventDetail

@Dao
interface EventDetailDao {

    @Query("SELECT * FROM EventDetail")
    fun getAllEventDetail(): List<EventDetail>

    @Query("SELECT * FROM EventDetail WHERE eventId=:id")
    fun getEventDetailById(id: Int): EventDetail

    @Query("SELECT * FROM EventDetail WHERE timeStamp >=:start AND timeStamp <=:end")
    fun getEventDetailByTimeInterval(start: Long, end: Long): List<EventDetail>

//    fun getTodayTypeEventTotal() {
//        getEventDetailByTimeInterval()
//    }

    @Insert
    fun insert(event: EventDetail)

    @Delete
    fun delete(event: EventDetail)

    @Update
    fun update(event: EventDetail)

}