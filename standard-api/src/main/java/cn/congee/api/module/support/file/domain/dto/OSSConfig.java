package cn.congee.api.module.support.file.domain.dto;

import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午10:58
 **/
@Data
public class OSSConfig {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;

    @Override
    public String toString() {
        return "OSSConfig{" +
                "endpoint='" + endpoint + '\'' +
                ", accessKeyId='" + accessKeyId + '\'' +
                ", accessKeySecret='" + accessKeySecret + '\'' +
                ", bucketName='" + bucketName + '\'' +
                '}';
    }

}
