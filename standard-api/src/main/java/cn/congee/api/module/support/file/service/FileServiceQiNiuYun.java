package cn.congee.api.module.support.file.service;

import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.module.support.file.constant.FileResponseCodeConst;
import cn.congee.api.module.support.file.constant.FileServiceNameConst;
import cn.congee.api.module.support.file.domain.dto.OSSConfig;
import cn.congee.api.module.support.file.domain.vo.UploadVO;
import cn.congee.api.module.system.systemconfig.SystemConfigService;
import cn.congee.api.module.system.systemconfig.constant.SystemConfigEnum;
import cn.congee.api.util.StandardFileUtil;
import cn.congee.api.util.StandardStringUtil;
import cn.congee.api.util.StandardUploadUtil;
import com.alibaba.fastjson.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * [ 七牛云 ]
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:16
 **/
@Slf4j
@Service(FileServiceNameConst.QI_NIU_OSS)
public class FileServiceQiNiuYun implements IFileService {

    //1小时，可以自定义链接过期时间
    private static final Long expireInSeconds = 3600L;

    @Autowired
    private StandardUploadUtil standardUploadUtil;

    @Autowired
    private SystemConfigService systemConfigService;

    UploadManager ossClient = null;

    String accessConfig = null;

    String token = null;

    @Override
    public ResponseDTO<UploadVO> fileUpload(MultipartFile multipartFile, String path) {
        OSSConfig ossConfig = systemConfigService.selectByKey2Obj(SystemConfigEnum.Key.QI_NIU_OSS.name(), OSSConfig.class);
        try {
            InputStream inputStream = new ByteArrayInputStream(multipartFile.getBytes());
            if (! ossConfig.toString().equals(accessConfig)) {
                //accessKeyId 发生变动自动重新创建新的UploadManager
                ossClient = new UploadManager(new Configuration());
                token = Auth.create(ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret()).
                        uploadToken(ossConfig.getBucketName());
                accessConfig = ossConfig.toString();
            }
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String ossPath = path + "/" + uuid;
            String fileName = multipartFile.getOriginalFilename();
            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
            String mime = this.getContentType(fileExt);
            Response res = ossClient.put(inputStream, ossPath, token, null, mime);
            if (! res.isOK()) {
                log.error("QINIU fileUpload ERROR : {}", res.toString());
                return ResponseDTO.wrap(FileResponseCodeConst.UPLOAD_ERROR);
            }
            UploadVO localUploadVO = new UploadVO();
            String url = this.getFileUrl(ossPath).getData();
            if(StandardStringUtil.isNotBlank(url)){
                url = url.split("&")[0];
            }
            localUploadVO.setUrl("http://" + url);
            localUploadVO.setFileName(fileName);
            localUploadVO.setFilePath(ossPath);
            localUploadVO.setFileSize(multipartFile.getSize());
            return ResponseDTO.succData(localUploadVO);
        } catch (Exception e) {
            log.error("QINIU fileUpload ERROR : {}", e);
        }
        return ResponseDTO.wrap(FileResponseCodeConst.UPLOAD_ERROR);
    }

    @Override
    public ResponseDTO<String> getFileUrl(String path) {
        OSSConfig ossConfig = systemConfigService.selectByKey2Obj(SystemConfigEnum.Key.QI_NIU_OSS.name(), OSSConfig.class);
        try {
            if (! ossConfig.toString().equals(accessConfig)) {
                //accessKeyId 发生变动自动重新创建新的UploadManager
                ossClient = new UploadManager(new Configuration());
                token = Auth.create(ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret()).
                        uploadToken(ossConfig.getBucketName());
                accessConfig = ossConfig.toString();
            }
            String encodedFileName = URLEncoder.encode(path, "utf-8");
            String domainOfBucket = ossConfig.getEndpoint();
            String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
            String accessKey = ossConfig.getAccessKeyId();
            String secretKey = ossConfig.getAccessKeySecret();
            Auth auth = Auth.create(accessKey, secretKey);
            //1小时，可以自定义链接过期时间
            long expireInSeconds = 3600;
            String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
            return ResponseDTO.succData(finalUrl);
        } catch (Exception e) {
            log.error("QINIU getFileUrl ERROR : {}", e);
        }
        return ResponseDTO.wrap(FileResponseCodeConst.URL_ERROR);
    }

    @Override
    public ResponseEntity<byte[]> fileDownload(String key, String fileName, HttpServletRequest request) {
        File file = this.getFile(key, fileName);
        if (file == null) {
            throw new RuntimeException("文件不存在");
        }
        return this.downloadMethod(file, request);
    }

    /**
     * 获取下载路径
     */
    public String getDownloadUrl(String key) {
        OSSConfig ossConfig = systemConfigService.selectByKey2Obj(SystemConfigEnum.Key.QI_NIU_OSS.name(), OSSConfig.class);
        String domainOfBucket = ossConfig.getEndpoint();
        String finalUrl = "";
        try {
            String encodedFileName = URLEncoder.encode(key, "utf-8").replace("+", "%20");
            String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
            Auth auth = Auth.create(ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
            finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        } catch (Exception e) {
            log.error("QINIU download ERROR : {}", e);
        }
        return finalUrl;
    }

    /**
     * 获取文件
     */
    public File getFile(String key, String fileName) {
        String finalUrl = getDownloadUrl(key);
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().url(finalUrl).build();
        okhttp3.Response resp = null;
        File file = new File(fileName);
        try {
            resp = client.newCall(req).execute();
            if (resp.isSuccessful()) {
                ResponseBody body = resp.body();
                InputStream objectContent = body.byteStream();
                // 输入流转换为字节流
                byte[] buffer = FileCopyUtils.copyToByteArray(objectContent);
                // 字节流写入文件
                FileCopyUtils.copy(buffer, file);
                // 关闭输入流
                objectContent.close();
            }

        } catch (IOException e) {
            log.error("文件获取失败：" + e);
            return null;
        } finally {
        }
        return file;
    }

    /**
     * 以文件形式上传
     *
     * @param file
     * @return
     */
    public ResponseDTO<UploadVO> uploadByFile(MultipartFile file){
        log.info("以文件形式上传接口入参为file=[{}]", file);
        Map<String, Object> map = standardUploadUtil.uploadByFile(file);
        Set<String> keys = map.keySet();
        String key = null;
        if(keys != null){
            Iterator<String> it = keys.iterator();
            while (it.hasNext()){
                key = it.next();
            }
        }
        String value = map.get(key).toString();
        log.info("以文件形式上传接口出参为url=[{}]", JSON.toJSONString(value));
        UploadVO localUploadVO = new UploadVO();
        localUploadVO.setUrl(value);
        localUploadVO.setFileName(file.getOriginalFilename());
        localUploadVO.setFilePath(key);
        localUploadVO.setFileSize(file.getSize());
        return new ResponseDTO<>(localUploadVO);
    }

    /**
     * 以流形式上传
     *
     * @param stream
     * @return
     */
    public ResponseDTO<UploadVO> uploadByStream(InputStream stream){
        log.info("以流形式上传接口入参为stream=[{}]", JSON.toJSONString(stream));
        Map<String, Object> map = standardUploadUtil.uploadByStream(stream);
        Set<String> keys = map.keySet();
        String key = null;
        if(keys != null){
            Iterator<String> it = keys.iterator();
            while (it.hasNext()){
                key = it.next();
            }
        }
        String value = map.get(key).toString();
        log.info("以流形式上传接口出参为url=[{}]", JSON.toJSONString(value));
        UploadVO localUploadVO = new UploadVO();
        localUploadVO.setUrl(value);
        try{
            File file = StandardFileUtil.asFile(stream);
            MultipartFile multipartFile = StandardFileUtil.fileToMultipartFile(file);
            localUploadVO.setFileName(multipartFile.getOriginalFilename());
            localUploadVO.setFilePath(key);
            localUploadVO.setFileSize(multipartFile.getSize());
        }catch (IOException e){
            e.printStackTrace();
        }
        return new ResponseDTO<>(localUploadVO);
    }

    /**
     * 根据key删除七牛云相关文件
     *
     * @param key
     * @return
     */
    public ResponseDTO<Response> deleteByKey(String key){
        log.info("根据key删除七牛云相关文件接口入参为key=[{}]", key);
        Response response = standardUploadUtil.deleteByKey(key);
        log.info("根据key删除七牛云相关文件接口出参为response=[{}]", JSON.toJSONString(response));
        return new ResponseDTO<>(response);
    }

}
