package cn.congee.api.config;

import cn.congee.api.module.support.file.domain.dto.OSSConfig;
import cn.congee.api.module.system.systemconfig.SystemConfigService;
import cn.congee.api.module.system.systemconfig.constant.SystemConfigEnum;
import com.alibaba.fastjson.JSON;
import com.qiniu.common.Zone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wgb
 * Date: 2020/12/30
 * Time: 14:45
 **/
@Slf4j
@Component
public class StandardQiNiuConfig {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private Zone zone;
    private String endpoint;
    private long expireInSeconds;

    @Autowired
    private SystemConfigService systemConfigService;

    private static StandardQiNiuConfig instance = new StandardQiNiuConfig();

    private StandardQiNiuConfig(){
        try {
            OSSConfig ossConfig = systemConfigService.selectByKey2Obj(SystemConfigEnum.Key.QI_NIU_OSS.name(), OSSConfig.class);
            log.info("初始版七牛云配置文件为ossConfig=[{}]", JSON.toJSONString(ossConfig));
            accessKey = ossConfig.getAccessKeyId();
            secretKey = ossConfig.getAccessKeySecret();
            bucket = ossConfig.getBucketName();
            endpoint = ossConfig.getEndpoint();

            //1小时，可以自定义链接过期时间 -1
            expireInSeconds = 3600L;
            /**
             * # [{'zone0':'华东'}, {'zone1':'华北'},{'zone2':'华南'},{'zoneNa0':'北美'},{'zoneAs0':''}]
             * # 链接过期时间，单位是秒，3600代表1小时，-1代表永不过期
             *
             */
            String zoneName = "zone0";
            if(zoneName.equals("zone0")){
                zone = Zone.zone0();
            }else if(zoneName.equals("zone1")){
                zone = Zone.zone1();
            }else if(zoneName.equals("zone2")){
                zone = Zone.zone2();
            }else if(zoneName.equals("zoneNa0")){
                zone = Zone.zoneNa0();
            }else if(zoneName.equals("zoneAs0")){
                zone = Zone.zoneAs0();
            }else{
                throw new Exception("Zone对象配置错误！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static StandardQiNiuConfig getInstance(){
        return instance;
    }

}
