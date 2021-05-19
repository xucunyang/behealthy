//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yang.me.lib;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NotFoundFragment extends Fragment {
    private boolean isFromActivity;
    private RelativeLayout top;
    private ImageView iv_back;
    private View divider;
    private TextView tv_content;
    private boolean flag;

    public NotFoundFragment(boolean flag) {
        this.flag = flag;
    }

    public NotFoundFragment() {
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_not_found, (ViewGroup) null);
        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.initView(view);
    }

    private void initView(View view) {
        this.tv_content = (TextView) view.findViewById(R.id.tv_content);
        if (this.flag) {
            this.tv_content.setText("暂无内容");
        }

        this.top = (RelativeLayout) view.findViewById(R.id.top);
        this.iv_back = (ImageView) view.findViewById(R.id.iv_back);
        if (null != this.getArguments()) {
            String title = this.getArguments().getString("title");
            this.isFromActivity = this.getArguments().getBoolean("isFromActivity");
        }

        if (this.isFromActivity) {
            this.top.setVisibility(View.VISIBLE);
        } else {
            this.top.setVisibility(View.GONE);
        }

        this.iv_back.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                NotFoundFragment.this.checkPageCanFinish();
            }
        });
        if (null != this.getArguments()) {
            boolean isCancelFitsSystemWindows = this.getArguments().getBoolean("isCancelFitsSystemWindows");
            if (isCancelFitsSystemWindows) {
                LayoutParams lp = (LayoutParams) this.top.getLayoutParams();
                lp.setMargins(0, 0, 0, 0);
            }
        }

    }

    public void checkPageCanFinish() {
        if (null == this.getParentFragment()) {
            this.getActivity().finish();
        }
    }
}
