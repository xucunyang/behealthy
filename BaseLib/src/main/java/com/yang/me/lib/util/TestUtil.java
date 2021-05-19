package com.yang.me.lib.util;

import com.yang.me.lib.bean.SingleSelectBean;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

//    public static List<NewsListBean> getTestNewsListBean() {
//        List<NewsListBean> data = new ArrayList<>();
//        for (int i = 0; i < 16; i++) {
//            NewsListBean newsListBean = new NewsListBean();
//            ArrayList<String> imgs = new ArrayList<>();
//            imgs.add(TestConst.urls[i % TestConst.urls.length]);
//            newsListBean.thumbnailsJson = imgs;
//            newsListBean.title = "糗事播报第--" + i;
//            newsListBean.title = "糗事播报 | 经典段子 第--" + i;
//            data.add(newsListBean);
//        }
//        return data;
//    }
//
//
//    public static NavChildBean getNavChildBean() {
//
//        NavChildBean navChildBean = new NavChildBean();
//        ArrayList<NewsListBean> topbanners = new ArrayList<>();
//        for (int i = 0; i < 8; i++) {
//            NewsListBean newsListBean = new NewsListBean();
//            ArrayList<String> thumbnailsJson = new ArrayList();
//            thumbnailsJson.add(TestConst.urls[i % TestConst.urls.length]);
//            newsListBean.thumbnailsJson = thumbnailsJson;
//            topbanners.add(newsListBean);
//        }
//        navChildBean.topBanners = topbanners;
//        return navChildBean;
//    }

    public static List<SingleSelectBean> getSingleSelectListBean() {
        List<SingleSelectBean> data = new ArrayList();

        for (int i = 0; i < 6; ++i) {
            SingleSelectBean bean = new SingleSelectBean();
            bean.setItemName("倍速播放：" + (i + 1) + ".0 X");
            bean.setDefaultSelect(i == 1);
            data.add(bean);
        }

        return data;
    }
}
