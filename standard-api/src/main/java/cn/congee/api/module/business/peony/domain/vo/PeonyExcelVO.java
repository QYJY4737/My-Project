package cn.congee.api.module.business.peony.domain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 *  [ 牡丹花 ]
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午9:58
 **/
@Data
public class PeonyExcelVO {

    @Excel(name = "ID")
    private Long id;

    @Excel(name = "品种")
    private String kind;

    @Excel(name = "名字")
    private String name;

    @Excel(name = "颜色")
    private String color;

    @Excel(name = "图片链接")
    private String imageUrl;

    @Excel(name = "创建时间", format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Excel(name = "更新时间", format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
