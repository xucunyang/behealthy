package com.yang.me.healthy.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.yang.me.healthy.R
import com.yang.me.healthy.data.AppDataBase
import com.yang.me.healthy.data.bean.EventDetail
import com.yang.me.healthy.data.bean.TypedEvent
import com.yang.me.healthy.data.dao.EventDetailDao
import com.yang.me.healthy.data.dao.TypedEventDao
import com.yang.me.healthy.databinding.FragmentHomeBinding
import com.yang.me.healthy.ui.dialog.EventTypeDialog
import com.yang.me.lib.BaseBindFragment
import com.yang.me.lib.adapter.BaseWrapAdapter
import com.yang.me.lib.extension.launchWrapped
import com.yang.me.lib.extension.toast
import com.yang.me.lib.util.Util
import java.util.*
import kotlin.collections.HashMap


/**
 * <pre>
 *
 * Description: 主页
 *
 * Author: xucunyang
 * Time: 2021/5/25 17:18
 *
 * </pre>
 */
class HomeFragment : BaseBindFragment<FragmentHomeBinding>() {
    private lateinit var typedEventDao: TypedEventDao
    private lateinit var eventDetailDao: EventDetailDao

    override fun getFragmentLayoutId() =
        R.layout.fragment_home

    override fun init() {
        setStatusBarColor(R.color.black)
        Util.setNavigationBarColor(activity, R.color.black)

        typedEventDao = AppDataBase.get().getTypedEventDao()
        eventDetailDao = AppDataBase.get().getEventDetailDao()

        mViewBinding.includeActionBar.titleTv.text = "首页"
        mViewBinding.includeActionBar.menuEnd.setOnClickListener(this)
        mViewBinding.includeActionBar.toolbar.navigationIcon = null
        mViewBinding.date.text = Util.getSimpleDateFormat(System.currentTimeMillis(), "yyyy-MM-dd")
        mViewBinding.week.text = Util.getWeek(Date(System.currentTimeMillis()))

        initCircleProgress()
        initAddRv()
    }

    private fun initCircleProgress() {
        launchWrapped(this,
            {
                var dataMap: Map<TypedEvent, Float> = mutableMapOf()
                val allTypedEvent = typedEventDao.getAllTypedEvent()
                for (typedEvent in allTypedEvent) {
                    val typedDetailList =
                        eventDetailDao.getTodayDetailTotalByType(typedEvent.id)
                    dataMap[typedEvent] =getTotalCount(typedDetailList)
                }
            }, {

            })
    }

    fun getTotalCount(list: List<EventDetail>): Int {
        var sum: Int = 0
        for (eventDetail in list) {
            sum += eventDetail.tempProgress
        }
        return sum
    }

    private fun initAddRv() {
        launchWrapped(
            lifecycleOwner = this,
            asyncBlock = {
                typedEventDao.getAllTypedEvent()
            },
            uiBlock = {
                toast(requireContext(), it.size.toString())
                if (it.isNotEmpty()) {
                    val baseWrapAdapter = BaseWrapAdapter<ItemEventVH, TypedEvent>(it,
                        BaseWrapAdapter.VhProvider<ItemEventVH> { parent, _ ->
                            ItemEventVH(
                                parent
                            )
                        })
                    baseWrapAdapter.setClickListener { view, position, bean ->
                        eventDetailDao.insert(EventDetail(bean.id, 1, System.currentTimeMillis()))
                    }
                    mViewBinding.rv.adapter = baseWrapAdapter
                    mViewBinding.rv.layoutManager = GridLayoutManager(requireContext(), it.size)
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchWrapped(this, {
            typedEventDao.getAllTypedEvent().isEmpty()
        }, {
            if (it) {
                context?.apply {
                    EventTypeDialog(this).show()
                }
            }
        })
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        if (v == mViewBinding.includeActionBar.menuEnd) {
            context?.apply {
                EventTypeDialog(this).show()
            }
        }
    }

}

