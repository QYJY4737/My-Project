package cn.congee.api.module.business.peony.domain.entity;

import cn.congee.api.common.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * [ 牡丹花 ]
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午9:57
 **/
@Data
@TableName("t_peony")
public class PeonyEntity extends BaseEntity {

    /**
     * 品种
     */
    private String kind;

    /**
     * 名字
     */
    private String name;

    /**
     * 颜色
     */
    private String color;

    /**
     * 图片链接
     */
    private String imageUrl;

}
