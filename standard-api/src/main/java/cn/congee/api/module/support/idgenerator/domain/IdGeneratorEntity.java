package cn.congee.api.module.support.idgenerator.domain;

import cn.congee.api.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:37
 **/
@Data
@TableName(value = "t_id_generator")
public class IdGeneratorEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 5582354131134766548L;

    /**
     * 英文key
     */
    private String keyName;

    /**
     * 规则格式
     */
    private String ruleFormat;

    /**
     * 类型
     */
    private String ruleType;

    /**
     * 初始值
     */
    private Long initNumber;

    /**
     * 上次产生的id
     */
    private Long lastNumber;

    /**
     * 备注
     */
    private String remark;

}
