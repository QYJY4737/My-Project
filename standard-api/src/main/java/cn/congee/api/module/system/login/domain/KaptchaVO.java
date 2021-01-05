package cn.congee.api.module.system.login.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:35
 **/
@Data
public class KaptchaVO {

    @ApiModelProperty("验证码UUID")
    private String uuid;

    @ApiModelProperty("base64 验证码")
    private String code;

}
