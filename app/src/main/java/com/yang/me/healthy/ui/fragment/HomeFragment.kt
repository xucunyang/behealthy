package com.yang.me.healthy.ui.fragment

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.GridLayoutManager
import com.yang.me.healthy.R
import com.yang.me.healthy.data.AppDataBase
import com.yang.me.healthy.data.bean.EventDetail
import com.yang.me.healthy.data.bean.TypedEvent
import com.yang.me.healthy.data.dao.EventDetailDao
import com.yang.me.healthy.data.dao.TypedEventDao
import com.yang.me.healthy.databinding.FragmentHomeBinding
import com.yang.me.healthy.ui.dialog.ConfirmDialog
import com.yang.me.healthy.ui.dialog.EventTypeDialog
import com.yang.me.lib.BaseBindFragment
import com.yang.me.lib.CustomItemDecoration
import com.yang.me.lib.VhItemSizeHelper
import com.yang.me.lib.adapter.BaseWrapAdapter
import com.yang.me.lib.extension.launchWrapped
import com.yang.me.lib.util.Util
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
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
    var onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (operateRvInEditMode) {
                finishEditMode()
                remove()
            }
        }
    }

    override fun getFragmentLayoutId() =
        R.layout.fragment_home

    override fun init() {
        // blur top view
        setImmersiveBlurView(
            mViewBinding.includeActionBar.toolbar,
            mViewBinding.includeActionBar.blurLayout,
            false
        )
        // black navigation bar
        Util.setNavigationBarColor(activity, R.color.black)

        mViewBinding.includeActionBar.blurLayout.setBlurRadius(15)

        context?.apply {
            eventTypedDialog = EventTypeDialog(this)
            eventTypedDialog.setOnDismissListener { dialog ->
                initAddRv()
                updateCircleProgress()
            }
        }

        typedEventDao = AppDataBase.get().getTypedEventDao()
        eventDetailDao = AppDataBase.get().getEventDetailDao()

        mViewBinding.includeActionBar.titleTv.text = "首页"
        mViewBinding.includeActionBar.menuEnd.setOnClickListener(this)
        mViewBinding.includeActionBar.toolbar.navigationIcon = null
        mViewBinding.date.text = Util.getSimpleDateFormat(System.currentTimeMillis(), "yyyy-MM-dd")
        mViewBinding.week.text = Util.getWeek(Date(System.currentTimeMillis()))

        mViewBinding.includeActionBar.menuText.setOnClickListener {
            onBackPressedCallback.remove()
            finishEditMode()
        }

        // Horizontal
        OverScrollDecoratorHelper.setUpStaticOverScroll(
            mViewBinding.rv,
            OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL
        );
        // Vertical
        OverScrollDecoratorHelper.setUpOverScroll(mViewBinding.scrollView)

        updateCircleProgress()
        initAddRv()

        // load
        launchWrapped(this, {
            typedEventDao.getAllTypedEvent().isEmpty()
        }, {
            if (it) {
                eventTypedDialog.show()
            }
        })
    }

    private fun updateCircleProgress() {
        launchWrapped(this, {
            getAllTypedEventData()
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
                    } else {
                        mViewBinding.colorfulProgress.midDestDegree = 0f
                        mViewBinding.colorfulProgress.innerDestDegree = 0f
                    }

                    if (it.size > 2) {
                        val innerEvent = it[2]
                        mViewBinding.colorfulProgress.innerDestDegree =
                            360f * (innerEvent.totalProgress.toFloat() / innerEvent.targetProgress)
                    } else {
                        mViewBinding.colorfulProgress.innerDestDegree = 0f
                    }
                } else {
                    mViewBinding.colorfulProgress.outDestDegree = 0f
                    mViewBinding.colorfulProgress.midDestDegree = 0f
                    mViewBinding.colorfulProgress.innerDestDegree = 0f
                }
                mViewBinding.colorfulProgress.startAnimateProgress()
            }
        })
    }

    /**
     * 获取所有的项目数据并求和
     */
    private fun getAllTypedEventData(): List<TypedEvent> {
        val allTypedEvent = typedEventDao.getAllTypedEvent()
        for (typedEvent in allTypedEvent) {
            typedEvent.totalProgress =
                getTotalCount(eventDetailDao.getTodayDetailTotalByType(typedEvent.id))
        }
        return allTypedEvent
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
                typedEventData.clear()
                typedEventData.addAll(getAllTypedEventData())
                typedEventData
            },
            uiBlock = { it ->
                if (it.isNotEmpty()) {
                    mViewBinding.rv.adapter = getOperateRvAdapter()
                    if (mViewBinding.rv.itemDecorationCount == 0) {
                        val vhItemSizeHelper = VhItemSizeHelper(context, it.size, 10, 10)
                        mViewBinding.rv.addItemDecoration(
                            CustomItemDecoration(
                                context,
                                vhItemSizeHelper
                            )
                        )
                    }
                    mViewBinding.rv.layoutManager = GridLayoutManager(requireContext(), it.size)
                    mViewBinding.rv.visibility = View.VISIBLE
                } else {
                    mViewBinding.rv.visibility = View.GONE
                }
            }
        )
    }

    val typedEventData = mutableListOf<TypedEvent>()

    var operateRvInEditMode = false

    override fun onClick(v: View?) {
        super.onClick(v)
        if (v == mViewBinding.includeActionBar.menuEnd) {
//            eventTypedDialog.show()
            activity?.let { ThirdActivity.start(it, "xcy~~~") }
        }
    }

    private fun getOperateRvAdapter(): BaseWrapAdapter<ItemEventVH, TypedEvent> {
        baseWrapAdapter = BaseWrapAdapter<ItemEventVH, TypedEvent>(typedEventData,
            BaseWrapAdapter.VhProvider<ItemEventVH> { parent, _ ->
                val itemEventVH = ItemEventVH(parent)
                itemEventVH.setOnDeleteListener { v, _, b ->
                    context?.apply {
                        val dialog = ConfirmDialog(this)
                        dialog.onDone = {
                            typedEventDao.deleteById(b.id)
                            eventDetailDao.deleteDetailByEventId(b.id)
                            initAddRv()
                            updateCircleProgress()
                        }
                        dialog.show()
                    }

                }
                itemEventVH
            })
        baseWrapAdapter.setClickListener { view, position, bean ->
            launchWrapped(HomeFragment@ this,
                {
                    eventDetailDao.insert(EventDetail(bean.id, 1))
                    typedEventDao.getAllTypedEvent()
                }, { list ->
                    if (list.isNotEmpty()) {
                        var outTempProgress = 0f
                        var midTempProgress = 0f
                        var innerTempProgress = 0f
                        for (index in list.indices) {
                            if (list[index].id == bean.id) {
                                if (index == 0) {
                                    outTempProgress = 360f / list[index].targetProgress
                                } else if (index == 1) {
                                    midTempProgress = 360f / list[index].targetProgress
                                } else if (index == 2) {
                                    innerTempProgress =
                                        360f / list[index].targetProgress
                                }
                            }
                        }
                        mViewBinding.colorfulProgress.increaseWithAnim(
                            outTempProgress,
                            midTempProgress,
                            innerTempProgress
                        )

                        initAddRv()
                    }
                }
            )
        }
        baseWrapAdapter.setOnLongClickListener { _, _, _ ->
            operateRvInEditMode = true
            baseWrapAdapter.data.forEach {
                it.showDelete = true
            }
            baseWrapAdapter.notifyDataSetChanged()

            addBackCallback()

            setToolbarEndMenu(false)
        }
        return baseWrapAdapter
    }

    private fun addBackCallback() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        );
    }

    private fun setToolbarEndMenu(showAddMenu: Boolean) {
        mViewBinding.includeActionBar.menuEnd.visibility =
            if (showAddMenu) View.VISIBLE else View.GONE
        mViewBinding.includeActionBar.menuText.visibility =
            if (showAddMenu) View.GONE else View.VISIBLE
    }

    private fun finishEditMode() {
        if (operateRvInEditMode) {
            for (datum in baseWrapAdapter.data) {
                datum.showDelete = false
            }
            baseWrapAdapter.notifyDataSetChanged()

            setToolbarEndMenu(true)
        }
        operateRvInEditMode = false
    }

}

