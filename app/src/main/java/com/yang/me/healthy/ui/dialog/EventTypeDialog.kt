package com.yang.me.healthy.ui.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import com.yang.me.healthy.R
import com.yang.me.healthy.data.AppDataBase
import com.yang.me.healthy.data.bean.TypedEvent
import com.yang.me.healthy.databinding.DialogEventTypeBinding
import com.yang.me.lib.BaseDialog
import com.yang.me.lib.extension.launchWrapped
import com.yang.me.lib.extension.toast

class EventTypeDialog(
    context: Context,
    val editMode: Boolean = false,
    val typedEvent: TypedEvent? = null
) :
    BaseDialog<DialogEventTypeBinding>(context), View.OnClickListener {

    override fun getLayoutId() = R.layout.dialog_event_type

    override fun initView() {
        super.initView()
        mViewBinding.close.setOnClickListener(this)
        mViewBinding.done.setOnClickListener(this)

        if (editMode) {
            typedEvent?.apply {
                mViewBinding.editTypeName.setText(typedEvent.eventName)
                mViewBinding.editUnit.setText(typedEvent.unit)
                mViewBinding.editTarget.setText(typedEvent.targetProgress.toString())
            }
            mViewBinding.done.setText("修改")
        } else {
            mViewBinding.done.setText("添加")
        }

    }

    override fun onStart() {
        super.onStart()
        window?.setBackgroundDrawable(ColorDrawable(Color.BLACK))

        val dm = DisplayMetrics()
        window?.windowManager?.defaultDisplay?.getMetrics(dm)
        val attributes = window?.attributes
        attributes?.width = ViewGroup.LayoutParams.MATCH_PARENT;
        attributes?.height = (dm.heightPixels * 0.9f).toInt()
        window?.attributes = attributes
    }

    override fun onClick(v: View?) {
        if (v == mViewBinding.close) {
            dismiss()
        } else if (v == mViewBinding.done) {
            onDone()
        }
    }

    private fun onDone() {
        val typedName = mViewBinding.editTypeName.text.toString()
        val unit = mViewBinding.editUnit.text.toString()
        val target = mViewBinding.editTarget.text.toString()

        if (typedName.isEmpty()) {
            toast(requireContext(), "项目名不能为空")
            return
        }

        if (unit.isEmpty()) {
            toast(requireContext(), "单位不能为空")
            return
        }

        if (target.isEmpty()) {
            toast(requireContext(), "目标不能为空")
            return
        }

        launchWrapped(
            asyncBlock = {
                val typedEventDao = AppDataBase.get().getTypedEventDao()
                if (editMode) {
                    typedEvent?.apply {
                        this.eventName = typedName
                        this.targetProgress = target.toInt()
                        this.unit = unit
                        typedEventDao.update(typedEvent)
                    }
                    true
                } else {
                    val isExist = typedEventDao.getAllTypedEventByName(typedName).isNotEmpty()
                    if (!isExist) {
                        typedEventDao.insert(
                            TypedEvent(
                                typedName,
                                true,
                                System.currentTimeMillis(),
                                target.toInt(),
                                unit
                            )
                        )
                    }
                    isExist
                }
            },
            uiBlock = {
                if (editMode) {
                    toast(requireContext(), "已修改")
                    dismiss()
                } else {
                    if (it) {
                        toast(requireContext(), "项目名已存在，重新新添加新项目")
                    } else {
                        toast(requireContext(), "已添加")
                        dismiss()
                    }
                }
            })
    }

    private fun requireContext() = context

}