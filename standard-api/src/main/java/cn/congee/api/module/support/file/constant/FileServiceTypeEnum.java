package cn.congee.api.module.support.file.constant;

import cn.congee.api.common.domain.BaseEnum;

/**
 * 文件服务枚举类
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午10:56
 **/
public enum FileServiceTypeEnum implements BaseEnum {

    /**
     * 本地文件服务
     */
    LOCAL(1, FileServiceNameConst.LOCAL, "本地文件服务"),

    /**
     * 阿里OSS文件服务
     */
    ALI_OSS(2, FileServiceNameConst.ALI_OSS, "阿里OSS文件服务"),

    /**
     * 七牛文件服务
     */
    QI_NIU_OSS(3, FileServiceNameConst.QI_NIU_OSS, "七牛文件服务");

    private Integer locationType;

    private String serviceName;

    private String desc;

    FileServiceTypeEnum(Integer locationType, String serviceName, String desc) {
        this.locationType = locationType;
        this.serviceName = serviceName;
        this.desc = desc;
    }

    public String getServiceName() {
        return serviceName;
    }

    @Override
    public Integer getValue() {
        return this.locationType;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

}
