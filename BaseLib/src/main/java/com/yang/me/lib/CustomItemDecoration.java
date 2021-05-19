package com.yang.me.lib;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yang.me.lib.util.ScreenUtils;

/**
 * <pre>
 *
 * Description: 自定义的item间距修饰，配合{@link VhItemSizeHelper}设置上下左有间距
 *
 * Author: xucunyang
 * Time: 2021/4/26 17:27
 *
 * </pre>
 */
public class CustomItemDecoration extends RecyclerView.ItemDecoration {
    private VhItemSizeHelper mItemSizeHelper;

    private Context context;

    public CustomItemDecoration(Context context, @NonNull VhItemSizeHelper mVhItemSizeHelper) {
        this.context = context;
        this.mItemSizeHelper = mVhItemSizeHelper;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int childPosition = parent.getChildLayoutPosition(view);
        int spanCount = mItemSizeHelper.getSpanCount();
        // 设置top间距
        if (childPosition / spanCount == 0) {
            //第一行
            outRect.top = mItemSizeHelper.getTopItemTopSpace();
        } else {
            outRect.top = mItemSizeHelper.getItemVerSpace();
        }

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager
                && ((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.HORIZONTAL) {

            // 单个item实际占用的宽度
            float itemWidth = mItemSizeHelper.getItemWidth();
            // 单个item剩余的空间
            float itemExtraSpace = mItemSizeHelper.getItemHorSpace();
            // 边距
            float sideSpace = mItemSizeHelper.getStartEndSpace();
            if (childPosition % spanCount == 0) {
                // 首列
                outRect.left = (int) sideSpace;
                outRect.right = (int) (itemExtraSpace * 1f / 2);
            } else if (childPosition % spanCount == spanCount - 1) {
                // 末列
                outRect.left = (int) (itemExtraSpace * 1f / 2);
                outRect.right = (int) sideSpace;
            } else {
                // 除了首尾其他的item边距
                outRect.right = (int) (itemExtraSpace * 1f / 2);
                outRect.left = (int) (itemExtraSpace * 1f / 2);
            }
        } else if (layoutManager instanceof GridLayoutManager) {
            // 单个item实际占用的宽度
            float itemWidth = mItemSizeHelper.getItemWidth();
            // 单个item剩余的空间
            float itemExtraSpace = ScreenUtils.getScreenWidth(context) * 1f / spanCount - itemWidth;
            // 边距
            float sideSpace = mItemSizeHelper.getStartEndSpace();
            if (childPosition % spanCount == 0) {
                // 首列
                outRect.left = (int) sideSpace;
                outRect.right = (int) (itemExtraSpace - sideSpace);
            } else if (childPosition % spanCount == spanCount - 1) {
                // 末列
                outRect.left = (int) (itemExtraSpace - sideSpace);
                outRect.right = (int) sideSpace;
            } else {
                // 除了首尾其他的item边距
                outRect.right = (int) (itemExtraSpace * 1f / 2);
                outRect.left = (int) (itemExtraSpace * 1f / 2);
            }
        }

    }

}
