package com.yang.myapplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static final String PROD_URL = "https://m-sit.mcd.cn/mini-mfe/#/qr/foreignCardPayment/index?" +
            "TranType=0001&languageType=en&TranDate=20230504&fromApp=1&TranReserved={\"PageLanguage\"" +
            ":\"en_US\"}&RiskData=AAAwAQAAAAAAAAoAAAAxMzQzODY1NjI1rIUghy8qm9noLPb4J5gEc3mH4Yh0ZEKWDo" +
            "osoHOuJepJ3pw25ffqXpyW15UDY3ZBAzGkCWzTT7C5hS7lYm32bOQD1s4wr3NmpdxjjZXxM0qIfh7ybDdD5gTDP" +
            "8ta3jlwwoEzlzku+WM7OAY4mCaTm6/vj1wu1nhXka7OHbjZLzp+/VLRIObg4RhtvXKUMbH7fdSD2aXzo9E7hg8Gk" +
            "gB5OejLwKn4LW6UadmxiNZWM+v01yuFZs4t8ViXQjvcknX9NcVmjqvNOur0Rbc9mSuuXldMzxtWFAjBuC/8i7F9D" +
            "sxNi3r4V3mIn4h4wBhsB8n7dzZ51cAicOe2/zRLP35FotuIOElT7vnNnuT6PwoPfPI8OkYyF7uhNQ==&CurryNo=" +
            "CNY&RemoteAddr=221.181.215.246&PayTimeOut=14&TimeStamp=20230504134154&BusiType=0001&Orde" +
            "rAmt=5850&Version=20140728&MerPageUrl=https://m-sit.mcd.cn/mini-mfe/#/qr/foreignCardPaymentRe" +
            "sult/index&MerResv={\"storeId\":\"1450999\"}&CardTranData=AACgAgAAAAAAAAoAAAAxMzQzODY1NjI" +
            "1hyKX6uwh9WutsNdKCo/6uD92XQ96eW6hmuIz+mq34BmPxFRluA5QXJ1/n74P1HCeeBx8RzltNkviurujB684bnnx" +
            "r7fgQvdMUJqS8MFtR77AhZJLh0rOiDczl2SaxNmhFcZseDPXIFh7GiEJo4mTHXLRxdvC62mLSh7v7D58xPhwZuS1aX0cm6+qlwxMXL92o2AuIATxbOvUhno8rrroXiT81ajG+DyHu1tUdGXjgH6SVMBgHWgLFSb7/0N4FUR5N71/szpqcj7+KiiR37W7ALVZ4ayeklR7UsT/kwsmDGLnsHsuBCjZqaxdN5SbSqcyBVJd/wCf1p1wf9DRwd0VhhUUdng4+tlNtUg6B" +
            "rd3wkvw76P1O1tLVFcMgs+1uh85G0EZEZRDFtYqKGR9ApRjSCFoGx2WdXp6Exrjejz6zb+caJD62Jm/17pgChGVBCcsLlzZ2TUedlQUrtfTULK+dK7p112ggZyvIAZDtqJ/kEwq/6ePK7nea9auw9F4o07AsqHCcuAVafILFkL0s3CcW+e" +
            "K8+lq+K4O1jDlzhVQ+kyzc8zJ+jn/fKh8KAqyi3dazaO8JIs/iUqFx0xs/URPMPW7hSeK2abX7oor3J6KIfb0/bksjrzzHPRi0hwWBdv8rlqSQrRm/UOwzhFI8rx1rQLKMr5EklmlFzXAyIsVZKE1KT25HKtVM2NpZ2LhxOdCB1koLFJmhE9" +
            "XpPfpQ+zcrqktoQD1K9tYsnFrpg2niLSmAWS/ZwJW/6eqvbMDhQOCRRHbqxkEZ6hQfKAAeC3MVY0teCo7Afam+1ELkQvLJPNSYji/7r7DtIQz0kJXlI7x2mQWiLCIHUNO5SqaA0Wq1oYturdT8yEW&Signature=AABeAAAAAAAAAAoAAA" +
            "AxNDE1OTkzMjIzMEQCIEgLFz/jqoA4Nbhyw2IRbOvH995NjOEce9fKBVYlbV0nAiAdvIBIg0ancjKW59DNGL4mMrbI4o7px37tyK0X80tWhA==&MerOrderNo=12574229220044234752&MerId=581412303310007&MerBgUrl=" +
            "https://payment.mcd.com.cn/middle/v1/callback/UMS/pay/04a0a42588c34a568a0bf1b189f0d0d6/03/" +
            "1450999/02&payId=11574228986358542336&TranTime=134154" +
            "&CommodityMsg=1#1#Food Service^5850^1^01^01&AccessType=0&envH=r1";

    public static final String TEST_URL = "https://m.mcdchina.net/mini-mfe/#/qr/foreignCardPayment/" +
            "index?TranType=0001&languageType=en&TranDate=20230413&TranReserved={\"PageLanguage\":\"en_US\"" +
            "}&RiskData=AAAwAQAAAAAAAAoAAAAxMjk3MjM0NTYzNn3OE1KfUYuxcBsskFJ6o4+AYl2s91lsbx3sNHl+QxUpE1ChyWW5" +
            "U7Cn2rNUW4xT9HrLx3JGb0b5YNZd2JyTU7Fk3F4znEiYKC8eKtFAVZlB1Ex0ynIJlpy3+iDczOU0gaHCTTGTFpAxntLlFFVmyeEwiv/591KzXXY22No8ndyIzy4EVkgmrRSD+n1Kgf20MaQg0qJZInaLrhQsDghIsf0DHOFkNY/rue/sDX62kGSgkV51S8iZ" +
            "EZbkloPeZdopZDUu0syb7tJ8ACER5hiWhaugFKS26bzYgVKkmO0LURYPGoHseWe0l28zFglZFn26TFLqIMBLMqCfQ6Gat1phku60/XYPq82+YxIk4WLNTjvkcGzd19SS6g==&CurryNo=CNY&RemoteAddr=222.190.117.130&PayTimeOut=4&TimeSta" +
            "mp=20230413152544&BusiType=0001&OrderAmt=3950&Version=20140728&MerPageUrl=https://m.mcdchina.net/mini-mfe/#/qr/foreignCardPaymentResult/index&MerResv={\"storeId\":\"1990999\"}&CardTranData=AACgAgAAAAAAAAoAAAAxMjk3MjM0NTYzOyWnn+4pish4W+fg6RNzNXyNHxoaw9DX3+htx/DMb4BkYuIxfTuJBzE7qu2ofikfxDDW" +
            "xnt90N8WzdDu02JjW7jiLi+KRlXMl1zgjalsKXAFMJRSqis3qt57a/5Z1EDyZdCNrzFRn8K1LX/AAvZq8AViK8fvs1aM+KEKR/T0YQ95UE8KMq7HywVq3wxm99O1dZJO1K6x3Y8x2okvvxPDpicYRODPwvhIxVMakYsLh1ewHLZXbe5ZtZoD8PhSc8sb4wE" +
            "NS8wbrnbxoRd8fvaKQ1aWQ9cw8bI7dU8w1uNaORbJbQNnAVxqk0P2JYJYXl12lp65XnG/wkzoAzCgr1pCtJOtXWRRZHJqhACLr8ZtRx1ac8dq0ChPhevUh0LvX9hYm2gUF3RvVQnLWaLILJ928qoagYqPrx3ZPoZR+ghPfu/Qm1xvP5V5o4cv0k9" +
            "TIEoUkeHCDGptw46hsaGYt5iIvKiRtHqjMWgDI4AMArabZnkAF4j54k/DbVKyTAJbsLtzv4BjNJA8MWYWgp06l2uiwHDtB/OTk3NyXVxqs0oxZOJMT0fVSj0cpFgPio3K3UgEFVWqxegqgjDLejxsDjiB209kDGtCdH5iZ+HDM/KcTZ6H8DJRT5x+23" +
            "O4vO7E8R6I6OHCZ9Iz5la7zP8FcF0OQg2rJfedeuFXOl8g7b0g+3Vauhtx1fgt8O9nVVyYFKNYtjK27ZmZen7FQJHXmVSPI47" +
            "QtjA03MFaK8HvHky3fa3h5rbluThF3Cf3bDJGjZTQ/+4GcG+W6ApBKGL13etTT+Ivyl5ez5svK6m4ilq+EPKMD4Y+BJ4yPuzV" +
            "IvANfqT6tGKpj/FLow9LCScsJJMnFZEmRZQTFf+S&Signature=AABfAAAAAAAAAAoAAAAxMDU1ODQyOTUyMEUCIQCVJxcVck" +
            "SR5+HTIVzc+8XWCvyMQnxv1N450FBcY4HBKQIgPonFsKDxmTSRM3aNRfBERC6lEEGWs1ntwOfu5JGD42E=&MerOrderNo=125" +
            "66645203853373440&MerId=000092303153993&MerBgUrl=" +
            "https://pay.mcd.cn/middle/v1/callback/UMS/pay/f49c385b30e340699c981942561fff5a/03/1990999/02&payId=11566645123431788544&TranTime=152544" +
            "&CommodityMsg=1#1#Food Service^3950^1^01^01&AccessType=0";
    /** 判断是否是url
     *
     * @param urls
     * @return
     */
    public static boolean isHttpUrl(String urls) {
        if (urls == null) return false;
        boolean isurl = false;
        //设置正则表达式 次正则表达式无法匹配带有"-"的网址，导致匹配api-sit.mcd.com失败，而匹配超时的为线上url：api.mcd.com产生正则回溯cpu占用卡死
        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";
        //对比
        Pattern pat = Pattern.compile(regex.trim());
        Matcher mat = pat.matcher(urls.trim());
        //判断是否匹配
        isurl = mat.matches();
        if (isurl) {
            isurl = true;
        }
        return isurl;
    }


}
