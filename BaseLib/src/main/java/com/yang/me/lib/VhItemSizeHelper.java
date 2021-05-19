package com.yang.me.lib;

import android.content.Context;
import android.view.ViewGroup;

import com.yang.me.lib.util.ScreenUtils;
import com.yang.me.lib.util.SizeUtils;
import com.yang.me.lib.util.Util;

public class VhItemSizeHelper {
    /**
     * recyclerview中水平方向首个和末尾的item靠边的间距，单位dp
     */
    private int startEndSpace;
    /**
     * recyclerview中竖直方向首个和末尾的item靠边的间距，单位dp
     */
    private int topBottomSpace;
    /**
     * item水平间距，单位dp
     */
    private int itemHorSpace;
    /**
     * 针对网格布局 GridLayoutManager的首行顶部间距
     */
    private int topItemTopSpace;
    /**
     * item竖直间距，单位dp
     */
    private int itemVerSpace;
    /**
     * 网格布局中占满一recyclerview宽度的item数量，单位dp
     */
    private int spanCount = 1;

    private Context context;

    public VhItemSizeHelper(Context context, int spanCount, int startEndSpace, int itemHorSpace) {
        this.spanCount = spanCount;
        this.context = context;
        this.startEndSpace = (int) dp2px(startEndSpace);
        this.itemHorSpace = (int) dp2px(itemHorSpace);
    }

    public float getItemWidth(Context context) {
        float totalSpanWidth = ScreenUtils.getScreenWidth(context) - getTotalHorExtraSpace();
        return totalSpanWidth / spanCount;
    }

    public int getTotalHorExtraSpace() {
        return (spanCount - 1) * itemHorSpace + 2 * startEndSpace;
    }

    public float dp2px(float dp) {
        return SizeUtils.dp2px(context, dp);
    }

    public void setItemWidth(ViewGroup viewGroup, int width, int height) {
        Util.setViewLayoutParams(viewGroup, width, height);
    }

    public void setItemViewWidth(ViewGroup itemView) {
        setItemWidth(itemView, (int) getItemWidth(context), ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public int getStartEndSpace() {
        return (int) startEndSpace;
    }

    public void setStartEndSpace(int startEndSpace) {
        this.startEndSpace = (int) dp2px(startEndSpace);
    }

    public int getTopBottomSpace() {
        return topBottomSpace;
    }

    public void setTopBottomSpace(int topBottomSpace) {
        this.topBottomSpace = (int) dp2px(topBottomSpace);
    }

    public int getItemHorSpace() {
        return itemHorSpace;
    }

    public void setItemHorSpace(int itemHorSpace) {
        this.itemHorSpace = (int) dp2px(itemHorSpace);
    }

    public int getItemVerSpace() {
        return itemVerSpace;
    }

    public void setItemVerSpace(int itemVerSpace) {
        this.itemVerSpace = (int) dp2px(itemVerSpace);
    }

    public int getSpanCount() {
        return spanCount;
    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
    }

    public int getTopItemTopSpace() {
        return topItemTopSpace;
    }

    public void setTopItemTopSpace(int topItemTopSpace) {
        this.topItemTopSpace = (int) dp2px(topItemTopSpace);
    }
}
