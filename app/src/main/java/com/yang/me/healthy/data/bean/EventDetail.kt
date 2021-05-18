package com.yang.me.healthy.data.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EventDetail (
    @PrimaryKey(autoGenerate = true)
    var eventId: Int,
    var tempProgress: Int,
    var timeStamp: Long
)