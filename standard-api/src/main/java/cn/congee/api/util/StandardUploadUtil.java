package cn.congee.api.util;

import cn.congee.api.module.support.file.domain.dto.OSSConfig;
import cn.congee.api.module.system.systemconfig.SystemConfigService;
import cn.congee.api.module.system.systemconfig.constant.SystemConfigEnum;
import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wgb
 * Date: 2020/12/30
 * Time: 14:49
 **/
@Slf4j
@Component
public class StandardUploadUtil {

    @Autowired
    private Auth auth;
    // 定义七牛云上传的相关策略
    private StringMap putPolicy;
    @Autowired
    private UploadManager uploadManager;
    @Autowired
    private BucketManager bucketManager;
    @Autowired
    private SystemConfigService systemConfigService;


    /**
     * 以文件形式上传
     *
     * @param file
     * @return
     */
    public Map<String, Object> uploadByFile(MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        try {
            Response response = this.uploadManager.put(StandardFileUtil.multipartFileToFile(file), null, getUploadToken());
            int retry = 0;
            while (response.needRetry() && retry < 3) {
                response = this.uploadManager.put(StandardFileUtil.multipartFileToFile(file), null, getUploadToken());
                retry++;
            }
            //解析结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            System.out.println("putRet.key: " + putRet.key);
            System.out.println("putRet.hash: " + putRet.hash);
            OSSConfig ossConfig = this.getConfigValue();
            String url = "http://" + ossConfig.getEndpoint() + "/" + putRet.hash;
            map.put(putRet.key, url);
            System.out.println(map.get(putRet.key));
        }catch (QiniuException qe){
            log.error("以文件形式上传报错: " + qe.getMessage());
            qe.printStackTrace();
        }finally {
            return map;
        }
    }

    /**
     * 以流形式上传
     *
     * @param stream
     * @return
     */
    public Map<String, Object> uploadByStream(InputStream stream) {
        Map<String, Object> map = new HashMap<>();
        try {
            Response response = this.uploadManager.put(stream, null, getUploadToken(), null, null);
            int retry = 0;
            while (response.needRetry() && retry < 3) {
                response = this.uploadManager.put(stream, null, getUploadToken(), null, null);
                retry++;
            }
            //解析结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            System.out.println("putRet.key: " + putRet.key);
            System.out.println("putRet.hash: " + putRet.hash);
            OSSConfig ossConfig = this.getConfigValue();
            String url = "http://" + ossConfig.getEndpoint() + "/" + putRet.hash;
            map.put(putRet.key, url);
        }catch (QiniuException qe){
            log.error("以流形式上传报错: " + qe.getMessage());
            qe.printStackTrace();
        }finally {
            return map;
        }
    }

    /**
     * 根据key删除七牛云相关文件
     *
     * @param key
     * @return
     */
    public Response deleteByKey(String key) {
        Response response = null;
        try {
            OSSConfig ossConfig = this.getConfigValue();
            response = bucketManager.delete(ossConfig.getBucketName(), key);
            log.info("根据key删除七牛云相关文件返回结果为response=[{}]", JSON.toJSONString(response));
            int retry = 0;
            while (response.needRetry() && retry++ < 3) {
                response = bucketManager.delete(ossConfig.getBucketName(), key);
            }
        }catch (QiniuException qe){
            log.error("根据key删除七牛云相关文件报错: " + qe.getMessage());
            qe.printStackTrace();
        }finally {
            return response;
        }
    }

    /**
     * 获取上传凭证
     *
     * @return
     */
    private String getUploadToken() {
        OSSConfig ossConfig = this.getConfigValue();
        return this.auth.uploadToken(ossConfig.getBucketName(), null, 3600, putPolicy);
    }

    /**
     * 获取七牛云配置
     *
     * @return
     */
    private OSSConfig getConfigValue(){
        return systemConfigService.selectByKey2Obj(SystemConfigEnum.Key.QI_NIU_OSS.name(), OSSConfig.class);
    }

}
