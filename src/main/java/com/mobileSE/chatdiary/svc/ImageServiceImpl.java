package com.mobileSE.chatdiary.svc;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mobileSE.chatdiary.common.GiteeImgBed;
import com.mobileSE.chatdiary.common.exception.BizError;
import com.mobileSE.chatdiary.common.exception.BizException;
import com.mobileSE.chatdiary.dao.ImageDao;
import com.mobileSE.chatdiary.pojo.entity.DiaryEntity;
import com.mobileSE.chatdiary.pojo.entity.ImageEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {
    private final ImageDao imageDao;
    private final ApiService apiService;
    private final DiaryService diaryService;

    @Override
    public void uploadImage(MultipartFile image, Date timestamp) {
        String originalFileName = image.getOriginalFilename();
        //上传图片不存在时
        if (originalFileName == null) {
            throw new BizException(BizError.IMG_ERROR);
        }
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = timestamp.getTime() + suffix;
        byte[] bytes;
        try {
            bytes = image.getBytes();
        } catch (Exception e) {
            throw new BizException(BizError.IMG_ERROR);
        }
        String paramImgFile = Base64.encode(bytes);
        //设置转存到Gitee仓库参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", GiteeImgBed.ACCESS_TOKEN);
        paramMap.put("message", GiteeImgBed.ADD_MESSAGE);
        paramMap.put("content", paramImgFile);
        //转存文件路径
        String targetDir = GiteeImgBed.PATH + fileName;
        //设置请求路径
        String requestUrl = String.format(GiteeImgBed.CREATE_REPOS_URL, GiteeImgBed.OWNER,
                GiteeImgBed.REPO_NAME, targetDir);
        String resultJson = HttpUtil.post(requestUrl, paramMap);
        log.info(resultJson);
        JSONObject jsonObject = JSONUtil.parseObj(resultJson);
        //表示操作失败
        if (jsonObject.getObj("commit") == null) {
            throw new BizException(BizError.REQUEST_ERROR);
        }
        JSONObject content = JSONUtil.parseObj(jsonObject.getObj("content"));
        String url = content.getStr("download_url");
        log.info(url);
        String description = apiService.getImgDescription(image);
        DiaryEntity diary = diaryService.getDiaryByAuthorIdAndTimestamp(StpUtil.getLoginIdAsLong(), timestamp);
        Long diaryId = diary.getId();
        imageDao.save(ImageEntity.builder().url(url).description(description).timestamp(timestamp).diaryId(diaryId).build());

    }

//    /**
//     * 删除图片
//     * @param imgPath
//     * @return
//     * @throws Exception
//     */
//    @DeleteMapping("/delImg")
//    @ResponseBody
//    public Result delImg(@RequestParam(value = "imgPath") String imgPath) throws Exception {
//        //路径不存在不存在时
//        if(imgPath == null || imgPath.trim().equals("")){
//            return Result.fail(Result.SERVE_ERROR,"删除失败");
//        }
//        String path = imgPath.split("master/")[1];
//        //上传图片不存在时
//        if(path == null || path.trim().equals("")){
//            return Result.fail(Result.IMG_EXIST_ERROR,Result.IMG_EXIST_ERROR_MSG);
//        }
//        //设置请求路径
//        String requestUrl = String.format(GiteeImgBed.GET_IMG_URL, GiteeImgBed.OWNER,
//                GiteeImgBed.REPO_NAME, path);
//        logger.info("请求Gitee仓库路径:{}",requestUrl);
//
//        //获取图片所有信息
//        String resultJson = HttpUtil.get(requestUrl);
//
//        JSONObject jsonObject = JSONUtil.parseObj(resultJson);
//        if (jsonObject == null) {
//            logger.error("Gitee服务器未响应,message:{}",jsonObject);
//            return Result.fail(Result.SERVE_ERROR,Result.SERVE_ERROR_MSG);
//        }
//        //获取sha,用于删除图片
//        String sha = jsonObject.getStr("sha");
//        //设置删除请求参数
//        Map<String,Object> paramMap = new HashMap<>();
//        paramMap.put("access_token", GiteeImgBed.ACCESS_TOKEN);
//        paramMap.put("sha", sha);
//        paramMap.put("message", GiteeImgBed.DEl_MESSAGE);
//        //设置删除路径
//        requestUrl = String.format(GiteeImgBed.DEL_IMG_URL, GiteeImgBed.OWNER,
//                GiteeImgBed.REPO_NAME, path);
//        logger.info("请求Gitee仓库路径:{}",requestUrl);
//        //删除文件请求路径
//        resultJson = HttpRequest.delete(requestUrl).form(paramMap).execute().body();
//        HttpRequest.put(requestUrl).form(paramMap).execute().body();
//        jsonObject = JSONUtil.parseObj(resultJson);
//        //请求之后的操作
//        if(jsonObject.getObj("commit") == null){
//            logger.error("Gitee服务器未响应,message:{}",jsonObject);
//            return Result.fail(Result.SERVE_ERROR,Result.SERVE_ERROR_MSG);
//        }
//        return Result.success("删除成功");
//    }

    @Override
    public List<ImageEntity> getImageByDiaryId(Long id) {
        return imageDao.findByDiaryId(id);
    }
}
