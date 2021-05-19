package com.yang.me.lib.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;

import com.yang.me.lib.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {
    private static final String TAG = "Util";

    public static LifecycleOwner getLifecycleOwner(Context context) {
        while (!(context instanceof LifecycleOwner)) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        return (LifecycleOwner) context;
    }

    public static FragmentActivity getFragmentActivity(Context context) {
        while (!(context instanceof FragmentActivity)) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        return (FragmentActivity) context;
    }

    private final static String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";

    //获取状态栏高度
    public static int getStatusBarHeight(Resources res) {
        int statusBarHeight = 0;
        int resourceId = res.getIdentifier(STATUS_BAR_HEIGHT_RES_NAME, "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        Log.w(TAG, "getStatusBarHeight: " + statusBarHeight);
        return statusBarHeight;
    }

    public static int getTopBarHeight(Context context) {
        return getActionBarSize(context) + getStatusBarHeight(context.getResources());
    }

    public static int getActionBarSize(Context context) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, typedValue, true))
            return TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());
        return 0;
    }

    public static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        if (activity != null) {
            View decor = activity.getWindow().getDecorView();
            if (dark) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
    }

    //添加顶部状态栏
    public static View addStateBar(Activity activity) {
        Window window = activity.getWindow();
        //activity的顶级布局
        ViewGroup rootView = (ViewGroup) window.getDecorView();
        //创建新的View,并添加到rootView顶部)
        View statusBarView;
        if (null != rootView.findViewById(R.id.status_bar_custom)) {
            statusBarView = rootView.findViewById(R.id.status_bar_custom);
        } else {
            statusBarView = new View(activity);
            statusBarView.setBackgroundColor(activity.getResources().getColor(android.R.color.transparent));
            rootView.addView(statusBarView);
        }
        statusBarView.setId(R.id.status_bar_custom);
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        getStatusBarHeight(activity.getResources()));
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);
        statusBarView.setVisibility(View.VISIBLE);
        return statusBarView;
    }

    /**
     * 设置状态栏颜色
     *
     * @param ColorId
     */
    public void setStateBarColor(Activity activity, int ColorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            //透明化状态栏
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            Resources res = activity.getResources();
            //获取状态栏目的高度
            int statusBarHeight = getStatusBarHeight(res);

            View stateBarView = addStateBar(activity);
            stateBarView.setBackgroundColor(ColorId);
        }
    }

    /**
     * 重设 view 的宽高
     */
    public static void setViewLayoutParams(View view, int width, int height) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(width, height);
        } else {
            if (lp.height != height || lp.width != width) {
                lp.width = width;
                lp.height = height;
            }
        }
        view.setLayoutParams(lp);
    }

    public static String getArticleDate(long timeMillis) {
        return getSimpleDateFormat(timeMillis, "yyyy/MM/dd HH:mm");
    }

    public static String getSimpleDateFormat(long timeMillis, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date(timeMillis));
    }

    public static boolean isToday(long timeStamp) {
        Date inputJudgeDate = new Date(timeStamp);
        Date nowDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(nowDate);
        String subDate = format.substring(0, 10);
        String beginTime = subDate + " 00:00:00";
        String endTime = subDate + " 23:59:59";
        Date paseBeginTime = null;
        Date paseEndTime = null;

        try {
            paseBeginTime = dateFormat.parse(beginTime);
            paseEndTime = dateFormat.parse(endTime);
        } catch (ParseException var12) {
            Log.e("TimeUtil", "isToday: e.getMessage()");
        }

        return (inputJudgeDate.after(paseBeginTime) || inputJudgeDate.equals(paseBeginTime)) && inputJudgeDate.before(paseEndTime);
    }

    public static String getWeek(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String week = sdf.format(date);
        return week;
    }

    public static String covertTo(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat targetSdf = new SimpleDateFormat("HH:mm");
        String targetStr = "";
        //转换为日期类型
        try {
            Date date = sdf.parse(time);
            targetStr = targetSdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return targetStr;
    }

    public static String generateTime(long duration) {
        int totalSeconds = (int) (duration / 1000L);
        int seconds = totalSeconds % 60;
        int minutes = totalSeconds / 60 % 60;
        int hours = totalSeconds / 3600;
        return hours > 0 ?
                String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds)
                : String.format(Locale.US, "%02d:%02d", minutes, seconds);
    }
}