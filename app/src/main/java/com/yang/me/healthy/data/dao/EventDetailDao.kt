package com.yang.me.healthy.data.dao

import androidx.room.*
import com.yang.me.healthy.data.bean.EventDetail
import com.yang.me.lib.util.TimeUtil

@Dao
interface EventDetailDao {

    @Query("SELECT * FROM EventDetail")
    fun getAllEventDetail(): List<EventDetail>

    @Query("SELECT * FROM EventDetail WHERE eventId=:id")
    fun getEventDetailById(id: Int): EventDetail

    @Query("SELECT * FROM EventDetail WHERE timeStamp >=:start AND timeStamp <=:end")
    fun getEventDetailByTimeInterval(start: Long, end: Long): List<EventDetail>

    @Query("SELECT * FROM EventDetail WHERE timeStamp >=:start AND timeStamp <=:end AND eventId=:eventId")
    fun getTodayDetailTotalByType(
        eventId: Long,
        start: Long = TimeUtil.getStartOfDay(System.currentTimeMillis()),
        end: Long = TimeUtil.getEndOfDay(System.currentTimeMillis())
    ): List<EventDetail>

    @Insert
    fun insert(event: EventDetail)

    @Delete
    fun delete(event: EventDetail)

    @Update
    fun update(event: EventDetail)

}