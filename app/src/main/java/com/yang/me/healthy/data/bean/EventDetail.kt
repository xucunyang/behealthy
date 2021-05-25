package com.yang.me.healthy.data.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yang.me.lib.bean.BaseBean

@Entity
data class EventDetail(
    /**
     * 事件类型Id
     */
    var eventId: Long,
    /**
     * 增加的进度
     */
    var tempProgress: Int,
    /**
     * 时间
     */
    var timeStamp: Long = System.currentTimeMillis()
) : BaseBean() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}