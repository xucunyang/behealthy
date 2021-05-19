//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yang.me.lib;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.yang.me.lib.adapter.OnItemClickListener;
import com.yang.me.lib.bean.SingleSelectBean;
import com.yang.me.lib.databinding.DialogBaseSelectBinding;
import com.yang.me.lib.util.TestUtil;

import java.util.List;

public class BaseSelectDialog extends DialogFragment implements OnClickListener {
    private int currentSelectIndex;
    private DialogBaseSelectBinding binding;
    private View mRootView;
    private BaseWrapAdapter<SingleSelectVH, SingleSelectBean> adapter;

    public BaseSelectDialog(Context context){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_base_select, container, false);
        mRootView = binding.getRoot();
        return mRootView;
    }

    private void initView() {
        this.initDialog();
        final List<SingleSelectBean> listBean = TestUtil.getSingleSelectListBean();
        this.adapter = new BaseWrapAdapter(listBean, new BaseWrapAdapter.VhProvider<SingleSelectVH>() {
            public SingleSelectVH createVH(@NonNull ViewGroup parent, int viewType) {
                SingleSelectVH singleSelectVH = new SingleSelectVH(parent);
                singleSelectVH.setItemClickListener(new OnItemClickListener<SingleSelectBean>() {
                    public void onItemClick(View view, int position, SingleSelectBean bean) {
                        BaseSelectDialog.this.currentSelectIndex = position;

                        for (int i = 0; i < listBean.size(); ++i) {
                            SingleSelectBean b = (SingleSelectBean) listBean.get(i);
                            b.setDefaultSelect(i == BaseSelectDialog.this.currentSelectIndex);
                        }

                        BaseSelectDialog.this.adapter.updateData(listBean);
                        Toast.makeText(BaseSelectDialog.this.getContext(), " " + position, Toast.LENGTH_LONG).show();
                    }
                });
                return singleSelectVH;
            }
        });
        this.binding.rv.setAdapter(this.adapter);
        this.binding.rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        this.binding.closeList.setOnClickListener(this);
    }

    private void initDialog() {
        Window window = this.getActivity().getWindow();
        if (window != null) {
            WindowManager windowManager = window.getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            Point outSize = new Point();
            display.getSize(outSize);
            window.setGravity(80);
            window.setSoftInputMode(16);
            LayoutParams params = window.getAttributes();
            params.width = LayoutParams.MATCH_PARENT;
            params.height = LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }

    }

    public void onClick(View v) {
        if (v == this.binding.closeList) {
            this.dismiss();
        }

    }
}
