package com.yang.me.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.jsbcmasterapp.R;
import com.android.jsbcmasterapp.Res;
import com.android.jsbcmasterapp.utils.ColorFilterImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *
 * Description: 流式TextView排序布局，横排后换行
 *
 * Author: xucunyang
 * Time: 2021/5/10 11:47
 *
 * </pre>
 */
public class FlowTextViewLayout extends RelativeLayout {

    private static final String TAG = "AreaMultipleTextView";

    private final Context context;

    private final int textColor;

    private final int wordMargin;

    private final int lineMargin;

    //private Drawable textBackground;

    private int layout_width;

    private final Map<Integer, List<TextView>> lineMap;

    private OnMultipleTVItemClickListener listener;

    @SuppressWarnings("ResourceType")
    public FlowTextViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyView);

        textColor = ColorFilterImageView.isFilter ? context.getResources().getColor(R.color.grey) : Res.getColor("select_area_gray");// array.getColor(Color.BLACK, 0XFF00FF00); // 提供默认值，放置未指定
        float textSize = array.getDimension(R.styleable.MyView_textSize, 24);
        textSize = px2sp(context, textSize);
        wordMargin = array.getDimensionPixelSize(R.styleable.MyView_textWordMargin, 0);
        lineMargin = array.getDimensionPixelSize(R.styleable.MyView_textLineMargin, 0);
        //textBackground = getResources().getDrawable(R.drawable.search_label_shape);// array.getDrawable(R.styleable
        // .MyView_textBackground);
        int textPaddingLeft = array.getDimensionPixelSize(R.styleable.MyView_textPaddingLeft, 0);
        int textPaddingRight = array.getDimensionPixelSize(R.styleable.MyView_textPaddingRight, 0);
        int textPaddingTop = array.getDimensionPixelSize(R.styleable.MyView_textPaddingTop, 0);
        int textPaddingBottom = array.getDimensionPixelSize(R.styleable.MyView_textPaddingBottom, 0);
        array.recycle();

        lineMap = new HashMap<Integer, List<TextView>>();
        // 下边是获取系统属性
        int[] attrsArray = new int[]{android.R.attr.id, // 0
                android.R.attr.background, // 1
                android.R.attr.layout_width, // 2
                android.R.attr.layout_height, // 3
                android.R.attr.layout_marginLeft, // 4
                // 这个地方必须放到android.R.attr.layout_width的下边
                // 不知道为什么
                android.R.attr.layout_marginRight // 5
        };
        TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);

        try {
            layout_width = ta.getDimensionPixelSize(2, ViewGroup.LayoutParams.MATCH_PARENT);

        } catch (Exception e) {
            DisplayMetrics dm = getResources().getDisplayMetrics();
            layout_width = dm.widthPixels;
        }
        int marginRight = ta.getDimensionPixelSize(4, 0);
        int marginLeft = ta.getDimensionPixelSize(5, 0);
        layout_width = layout_width - marginRight - marginLeft;

        ta.recycle();
    }

    public OnMultipleTVItemClickListener getOnMultipleTVItemClickListener() {
        return listener;
    }

    public void setOnMultipleTVItemClickListener(OnMultipleTVItemClickListener listener) {
        this.listener = listener;
    }

    public OnSetTextValueListener setTextValueListener;

    public interface OnSetTextValueListener {

        void onSetTextValue(int index, TextView tv);

    }

    public void setTextViews(int size, OnSetTextValueListener setTextValueListener) {
        if (size <= 0)
            return;

        removeAllViews();

        //清掉上次设置的textview list
        for (List<TextView> v : lineMap.values()) {
            for (int i = 0; i < v.size(); i++) {
                TextView tv = v.get(i);
                tv.setVisibility(View.GONE);
            }
        }
        lineMap.clear();
        // 每一行拉伸
        int line = 0;
        List<TextView> lineList = new ArrayList<TextView>();
        lineMap.put(0, lineList);

        int x = 0;
        int y = 0;
        TextView tv = null;
        for (int i = 0; i < size; i++) {
            tv = (TextView) View.inflate(context, Res.getLayoutID("select_area_textview"), null);
            tv.setSingleLine(true);
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(textColor);
            TextPaint tp = tv.getPaint();
            tp.setFakeBoldText(false);
            tv.setTag(i);// 标记position
            if (null != setTextValueListener) {
                setTextValueListener.onSetTextValue(i, tv);
            }
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onMultipleTVItemClick(v, (Integer) v.getTag());
                    }
                }
            });

            int w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            int h = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
            tv.measure(w, h);
            int tvh = tv.getMeasuredHeight();
            int tvw = getMeasuredWidth(tv);

            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams
                    .WRAP_CONTENT);
            if (x + tvw > layout_width) {
                x = 0;
                y = y + tvh + lineMargin;

                // 拉伸处理
                line++;
                lineMap.put(line, new ArrayList<TextView>());
            }
            lp.leftMargin = x;
            lp.topMargin = y;
            x = x + tvw + wordMargin;
            tv.setLayoutParams(lp);

            // 拉伸处理
            lineMap.get(line).add(tv);

        }
        // 每一行拉伸
        for (int i = 0; i <= line; i++) {

            // 该行最后一个位置
            int len = lineMap.get(i).size();
            TextView tView = lineMap.get(i).get(len - 1);

            LayoutParams lp = (LayoutParams) tView.getLayoutParams();
            int right = lp.leftMargin + getMeasuredWidth(tView);
            int emptyWidth = layout_width - right;
            int padding = emptyWidth / (len * 2);

            int leftOffset = 0;
            for (int j = 0; j < lineMap.get(i).size(); j++) {
                TextView tView2 = lineMap.get(i).get(j);
                addView(tView2);
            }
        }
    }

    /**
     * 更新选中状态
     *
     * @param index            index
     * @param defaultTextColor 默认文字颜色
     * @param selectTextColor  选中文字颜色
     * @param defaultBg        默认bg
     * @param selectBg         选中的bg
     */
    public void changeItemStyle(int index, int defaultTextColor, int selectTextColor,
                                int defaultBg, int selectBg) {
        int size = getChildCount();
        try {
            for (int i = 0; i < size; i++) {
                TextView item = (TextView) getChildAt(i);
                item.setTextColor(i == index ? selectTextColor : defaultTextColor);
                item.setBackgroundResource(i == index ? selectBg : defaultBg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "changeItemStyle: " + e.getMessage());
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

    }

    public int getMeasuredWidth(View v) {
        return v.getMeasuredWidth();
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public interface OnMultipleTVItemClickListener {
        public void onMultipleTVItemClick(View view, int position);
    }
}