package com.yang.me.healthy.ui.dialog

import android.content.Context
import android.view.View
import com.yang.me.healthy.R
import com.yang.me.healthy.databinding.DialogConfirmBinding
import com.yang.me.lib.BaseDialog

class ConfirmDialog(context: Context) : BaseDialog<DialogConfirmBinding>(context),
    View.OnClickListener {

    var onDone: (() -> Unit)? = null

    override fun getLayoutId() = R.layout.dialog_confirm

    override fun initView() {
        super.initView()
        mViewBinding.cancel.setOnClickListener(this)
        mViewBinding.done.setOnClickListener(this)
    }

    fun setTitle(title: String) {
        mViewBinding.title.text = title
    }

    override fun onClick(v: View?) {
        if (v == mViewBinding.cancel) {
            dismiss()
        } else if (v == mViewBinding.done) {
            onDone()
        }
    }

    private fun onDone() {
        onDone?.invoke()
        dismiss()
    }

    private fun requireContext() = context

}