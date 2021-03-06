package com.yang.me.healthy.data.dao

import androidx.room.*
import com.yang.me.healthy.data.bean.TypedEvent

@Dao
interface TypedEventDao {

    @Query("SELECT * FROM TypedEvent")
    fun getAllTypedEvent(): List<TypedEvent>

    @Query("SELECT * FROM TypedEvent WHERE eventName=:eventName")
    fun getAllTypedEventByName(eventName: String): List<TypedEvent>

    @Query("SELECT * FROM TypedEvent WHERE id=:id")
    fun getTypedEventById(id: Int): TypedEvent

    @Query("DELETE FROM TypedEvent WHERE id=:id ")
    fun deleteById(id: Long)

    @Insert
    fun insert(typedEvent: TypedEvent)

    @Delete
    fun delete(typedEvent: TypedEvent)

    @Update
    fun update(typedEvent: TypedEvent)

}