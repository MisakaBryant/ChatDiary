package com.mobileSE.chatdiary.util.ImgRecognition;

import com.baidu.aip.imageclassify.AipImageClassify;

/**
 * 百度AI图像识别AipImageClassify单例类
 */
public class AipClient {

        private static AipImageClassify aipImageClassify;

        private AipClient() {}

        public static AipImageClassify getAipImageClassify() {
            if (aipImageClassify == null) {
                aipImageClassify = new AipImageClassify(baiduAip.APP_ID, baiduAip.API_KEY, baiduAip.SECRET_KEY);
            }
            return aipImageClassify;
        }
}
