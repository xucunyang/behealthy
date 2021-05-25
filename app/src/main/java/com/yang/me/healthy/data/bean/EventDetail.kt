package com.yang.me.healthy.data.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yang.me.lib.bean.BaseBean

@Entity
data class EventDetail(
    var eventId: Long,
    var tempProgress: Int,
    var timeStamp: Long
) : BaseBean() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}