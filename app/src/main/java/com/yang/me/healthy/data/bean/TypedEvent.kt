package com.yang.me.healthy.data.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yang.me.lib.bean.BaseBean


/**
 * <pre>
 *
 * Description: 事件类型
 *
 * Author: xucunyang
 * Time: 2021/5/18 11:07
 *
 * </pre>
 */
@Entity
data class TypedEvent(
    /**
     * 事件名称
     */
    var eventName: String,
    /**
     * 是否积极的事件
     */
    var isPositive: Boolean = true,
    /**
     * 创建时间
     */
    var createTimeStamp: Long = System.currentTimeMillis(),
    /**
     * 目标
     */
    var targetProgress: Int,
    /**
     * 单位
     */
    var unit: String
) : BaseBean() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}