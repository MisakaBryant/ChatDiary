package com.mobileSE.chatdiary.svc;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.mobileSE.chatdiary.common.exception.BizError;
import com.mobileSE.chatdiary.common.exception.BizException;
import com.mobileSE.chatdiary.svc.service.BaiduAipService;
import com.mobileSE.chatdiary.util.ImgRecognition.AipClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class BaiduAipServiceImpl implements BaiduAipService {
    @Override
    public String getImgDescription(MultipartFile image) {
        AipImageClassify client = AipClient.getAipImageClassify();
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("baike_num", "0");  // 返回百科信息的结果数，0表示不返回
        try {
            byte[] bytes = image.getBytes();
            JSONObject res = client.advancedGeneral(bytes, options);
            log.info(res.toString(2));

            int result_num = res.getInt("result_num");
            JSONArray results = res.getJSONArray("result");
            StringBuilder description = new StringBuilder("keywords: ");
            for (int i = 0; i < result_num; i++) {
                JSONObject result = results.getJSONObject(i);
                double score = result.getDouble("score");
                if (score < 0.4) {  // 识别度低于0.4的不要，这个值后面可以调
                    continue;
                }
                String keyword = result.getString("keyword");
                description.append(keyword).append(" ");
            }

            log.info(description.toString());
            return description.toString();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BizException(BizError.AIP_ERROR);
        }
    }

}
