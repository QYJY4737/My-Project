package cn.congee.api.module.system.position.domain.entity;

import cn.congee.api.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 岗位
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:38
 **/
@Data
@TableName(value = "t_position")
public class PositionEntity extends BaseEntity {

    /**
     * 岗位名称
     */
    private String positionName;

    /**
     * 岗位描述
     */
    private String remark;

}
