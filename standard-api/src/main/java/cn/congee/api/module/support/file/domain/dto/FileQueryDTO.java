package cn.congee.api.module.support.file.domain.dto;

import cn.congee.api.common.anno.ApiModelPropertyEnum;
import cn.congee.api.common.domain.PageParamDTO;
import cn.congee.api.common.validator.en.CheckEnum;
import cn.congee.api.module.support.file.constant.FileModuleTypeEnum;
import cn.congee.api.module.support.file.constant.FileServiceTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 文件信息查询dto
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:03
 **/
@Data
public class FileQueryDTO extends PageParamDTO {

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "业务类型")
    @ApiModelPropertyEnum(FileModuleTypeEnum.class)
    @CheckEnum(enumClazz = FileModuleTypeEnum.class, message = "文件业务类型错误")
    private Integer moduleType;

    @ApiModelProperty(value = "文件位置")
    @ApiModelPropertyEnum(FileServiceTypeEnum.class)
    @CheckEnum(enumClazz = FileServiceTypeEnum.class, message = "文件位置类型错误")
    private Integer fileLocationType;

}
