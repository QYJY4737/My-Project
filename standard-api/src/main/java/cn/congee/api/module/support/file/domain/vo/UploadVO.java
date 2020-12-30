package cn.congee.api.module.support.file.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:06
 **/
@Data
public class UploadVO {

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "url")
    private String url;

    @ApiModelProperty(value = "filePath")
    private String filePath;

    @ApiModelProperty(value = "文件大小")
    private Long fileSize;

}
