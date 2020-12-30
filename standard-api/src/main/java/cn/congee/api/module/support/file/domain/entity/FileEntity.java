package cn.congee.api.module.support.file.domain.entity;

import cn.congee.api.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午10:54
 **/
@Data
@TableName(value = "t_file")
public class FileEntity extends BaseEntity {

    /**
     * 相关业务id
     */
    private String moduleId;

    /**
     * 相关业务类型
     */
    private String moduleType;

    /**
     * 文件位置类型
     */
    private Integer fileLocationType;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 文件类型，程序中枚举控制，文件类型：如身份证正面，三证合一等等
     */
    private String fileType;

    /**
     * 文件key，用于文件下载
     */
    private String filePath;

    /**
     * 创建人，即上传人
     */
    private Long createrUser;

}
