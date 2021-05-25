package com.yang.me.healthy.ui.fragment

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
import com.yang.me.lib.util.Util
import java.util.*


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
    private lateinit var eventTypedDialog: EventTypeDialog
    private lateinit var baseWrapAdapter: BaseWrapAdapter<ItemEventVH, TypedEvent>

    override fun getFragmentLayoutId() =
        R.layout.fragment_home

    override fun init() {
        context?.apply {
            eventTypedDialog = EventTypeDialog(this)
            eventTypedDialog.setOnDismissListener { dialog ->
                initAddRv()
                updateCircleProgress()
            }
        }

        setStatusBarColor(R.color.black)
        Util.setNavigationBarColor(activity, R.color.black)

        typedEventDao = AppDataBase.get().getTypedEventDao()
        eventDetailDao = AppDataBase.get().getEventDetailDao()

        mViewBinding.includeActionBar.titleTv.text = "首页"
        mViewBinding.includeActionBar.menuEnd.setOnClickListener(this)
        mViewBinding.includeActionBar.toolbar.navigationIcon = null
        mViewBinding.date.text = Util.getSimpleDateFormat(System.currentTimeMillis(), "yyyy-MM-dd")
        mViewBinding.week.text = Util.getWeek(Date(System.currentTimeMillis()))

        updateCircleProgress()
        initAddRv()

        launchWrapped(this, {
            typedEventDao.getAllTypedEvent().isEmpty()
        }, {
            if (it) {
                eventTypedDialog.show()
            }
        })
    }

    private fun updateCircleProgress() {
        launchWrapped(this,
            {
                val allTypedEvent = typedEventDao.getAllTypedEvent()
                for (typedEvent in allTypedEvent) {
                    typedEvent.totalProgress =
                        getTotalCount(eventDetailDao.getTodayDetailTotalByType(typedEvent.id))
                }
                allTypedEvent
            }, {
                if (it.isNotEmpty()) {
                    if (it.isNotEmpty()) {
                        val outEvent = it[0]
                        mViewBinding.colorfulProgress.outDestDegree =
                            360f * (outEvent.totalProgress.toFloat() / outEvent.targetProgress)

                        if (it.size > 1) {
                            val midEvent = it[1]
                            mViewBinding.colorfulProgress.midDestDegree =
                                360f * (midEvent.totalProgress.toFloat() / midEvent.targetProgress)
                        }

                        if (it.size > 2) {
                            val innerEvent = it[2]
                            mViewBinding.colorfulProgress.innerDestDegree =
                                360f * (innerEvent.totalProgress.toFloat() / innerEvent.targetProgress)
                        }
                    }
                    mViewBinding.colorfulProgress.startAnimateProgress()
                }
            })
    }

    private fun getTotalCount(list: List<EventDetail>): Int {
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
                if (it.isNotEmpty()) {
                    baseWrapAdapter = BaseWrapAdapter<ItemEventVH, TypedEvent>(it,
                        BaseWrapAdapter.VhProvider<ItemEventVH> { parent, _ ->
                            ItemEventVH(parent)
                        })
                    baseWrapAdapter.setClickListener { view, position, bean ->
                        launchWrapped(HomeFragment@ this,
                            {
                                eventDetailDao.insert(
                                    EventDetail(
                                        bean.id,
                                        1,
                                        System.currentTimeMillis()
                                    )
                                )
                                typedEventDao.getAllTypedEvent()
                            }, {
                                if (it.isNotEmpty()) {
                                    if (it.isNotEmpty()) {
                                        val outEvent = it[0]
                                        var outTempProgress: Float = 0f
                                        var midTempProgress: Float = 0f
                                        var innerTempProgress: Float = 0f
                                        for (index in it.indices) {
                                            if (it[index].id == bean.id) {
                                                if (index == 0) {
                                                    outTempProgress =
                                                        360f * 1 / it[index].targetProgress
                                                } else if (index == 1) {
                                                    midTempProgress =
                                                        360f * 1 / it[index].targetProgress
                                                } else if (index == 2) {
                                                    innerTempProgress =
                                                        360f * 1 / it[index].targetProgress
                                                }
                                            }
                                        }
                                        mViewBinding.colorfulProgress.increaseWithAnim(
                                            outTempProgress,
                                            midTempProgress,
                                            innerTempProgress
                                        )
                                    }
                                }
                            }
                        )
                    }
                    mViewBinding.rv.adapter = baseWrapAdapter
                    mViewBinding.rv.layoutManager = GridLayoutManager(requireContext(), it.size)
                }
            }
        )
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        if (v == mViewBinding.includeActionBar.menuEnd) {
            eventTypedDialog.show()
        }
    }

}

