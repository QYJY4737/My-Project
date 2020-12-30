package cn.congee.api.config;

import cn.congee.api.module.support.file.domain.dto.OSSConfig;
import cn.congee.api.module.system.systemconfig.SystemConfigDao;
import cn.congee.api.module.system.systemconfig.SystemConfigService;
import cn.congee.api.module.system.systemconfig.constant.SystemConfigEnum;
import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wgb
 * Date: 2020/12/30
 * Time: 14:46
 **/
@Configuration
public class StandardQiNiuFileConfig {

    @Autowired
    private SystemConfigDao systemConfigDao;
    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * 华东机房,配置自己空间所在的区域
     */
    @Bean
    public com.qiniu.storage.Configuration qiniuConfig() {
        return new com.qiniu.storage.Configuration(Zone.zone0());
    }

    /**
     * 构建一个七牛上传工具实例
     */
    @Bean
    public UploadManager uploadManager() {
        return new UploadManager(qiniuConfig());
    }

    /**
     * 认证信息实例
     * @return
     */
    @Bean
    public Auth auth() {
        OSSConfig ossConfig = systemConfigService.selectByKey2Obj(SystemConfigEnum.Key.QI_NIU_OSS.name(), OSSConfig.class);
        return Auth.create(ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
    }

    /**
     * 构建七牛空间管理实例
     */
    @Bean
    public BucketManager bucketManager() {
        return new BucketManager(auth(), qiniuConfig());
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }

}
