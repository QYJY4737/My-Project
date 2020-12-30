package cn.congee.api.common.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Page返回对象
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:44
 **/
@Data
public class PageResultDTO<T> {

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private Long pageNum;

    /**
     * 每页的数量
     */
    @ApiModelProperty(value = "每页的数量")
    private Long pageSize;

    /**
     * 总记录数
     */
    @ApiModelProperty(value = "总记录数")
    private Long total;

    /**
     * 总页数
     */
    @ApiModelProperty(value = "总页数")
    private Long pages;

    /**
     * 结果集
     */
    @ApiModelProperty(value = "结果集")
    private List<T> list;

}
