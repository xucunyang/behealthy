package com.yang.me.healthy.data.bean

import androidx.room.Entity
import androidx.room.PrimaryKey


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
    @PrimaryKey(autoGenerate = true)
    var id: Int,
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
    var createTimeStamp: Long,
    /**
     * 目标
     */
    var targetProgress: Int,
    /**
     * 单位
     */
    var unit: String
)