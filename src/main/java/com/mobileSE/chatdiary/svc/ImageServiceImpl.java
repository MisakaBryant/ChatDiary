package com.mobileSE.chatdiary.svc;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mobileSE.chatdiary.common.exception.BizError;
import com.mobileSE.chatdiary.common.exception.BizException;
import com.mobileSE.chatdiary.dao.DiaryDao;
import com.mobileSE.chatdiary.dao.DiaryImageDao;
import com.mobileSE.chatdiary.dao.UserDao;
import com.mobileSE.chatdiary.dao.UserImageDao;
import com.mobileSE.chatdiary.pojo.entity.DiaryImageEntity;
import com.mobileSE.chatdiary.pojo.entity.UserImageEntity;
import com.mobileSE.chatdiary.svc.service.BaiduAipService;
import com.mobileSE.chatdiary.svc.service.ImageService;
import com.mobileSE.chatdiary.util.ImgBed.GiteeImgBed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {
    private final DiaryImageDao diaryImageDao;
    private final UserImageDao userImageDao;
    private final UserDao userDao;
    private final DiaryDao diaryDao;

    private final BaiduAipService baiduAipService;

    @Override
    public String uploadImageByName(MultipartFile image, String fileName) {
        String originalFileName = image.getOriginalFilename();
        //上传图片不存在时
        if (originalFileName == null) {
            throw new BizException(BizError.IMG_ERROR);
        }
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
        String requestUrl = String.format(GiteeImgBed.CREATE_REPOS_URL, GiteeImgBed.OWNER, GiteeImgBed.REPO_NAME, targetDir);
        String resultJson = HttpUtil.post(requestUrl, paramMap);
        log.info(resultJson);
        JSONObject jsonObject = JSONUtil.parseObj(resultJson);
        //表示操作失败
        if (jsonObject.getObj("commit") == null) {
            throw new BizException(BizError.REQUEST_ERROR);
        }
        JSONObject content = JSONUtil.parseObj(jsonObject.getObj("content"));
        return content.getStr("download_url");
    }

    /**
     * 上传日记的图片, 修改， 上传图片单独作为日记
     *
     * @param image
     * @param timestamp
     * @param diaryId
     */
    @Override
    public void uploadDiaryImageByDate(MultipartFile image, Date timestamp, Long diaryId) {
        String originalFileName = image.getOriginalFilename();
        if (originalFileName == null) {
            throw new BizException(BizError.IMG_ERROR);
        }
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = timestamp.getTime() + suffix;
        String url = uploadImageByName(image, fileName);
        String description = baiduAipService.getImgDescription(image);
        DiaryImageEntity save = diaryImageDao.save(DiaryImageEntity.builder().url(url).description(description).timestamp(timestamp).diaryId(diaryId).build());
        log.info("Uploading image diary:" + save);

    }

    @Override
    public void uploadUserImageByUserId(MultipartFile image, Long userId) {
        String originalFileName = image.getOriginalFilename();
        if (originalFileName == null) {
            throw new BizException(BizError.IMG_ERROR);
        }

        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
        String dataTime = LocalDate.now().toString();
        String fileName = dataTime + suffix;
        String url = uploadImageByName(image, fileName);
        List<UserImageEntity> byUserId = userImageDao.findByUserId(userId);
        if (byUserId.isEmpty()) {
            UserImageEntity save = userImageDao.save(UserImageEntity.builder().userId(userId).timestamp(new Date()).url(url).build());
            userDao.save(userDao.findById(userId).get().setAvatarUrlId(save.getId()));
        } else {
            UserImageEntity userImageEntity = byUserId.get(0);
            userImageDao.save(userImageEntity.setUrl(url));
        }
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
    public List<DiaryImageEntity> getImageByDiaryId(Long id) {
        return diaryImageDao.findByDiaryId(id);
    }
}
