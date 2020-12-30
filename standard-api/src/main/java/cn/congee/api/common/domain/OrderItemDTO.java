package cn.congee.api.common.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:20
 **/
@Data
@Slf4j
public class OrderItemDTO {

    private String column;
    private boolean asc = true;

}
