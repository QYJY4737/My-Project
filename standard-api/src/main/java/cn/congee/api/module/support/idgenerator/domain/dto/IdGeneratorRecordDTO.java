package cn.congee.api.module.support.idgenerator.domain.dto;

import lombok.Data;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:39
 **/
@Data
public class IdGeneratorRecordDTO {

    private Long generatorId;

    private Integer year;

    private Integer month;

    private Integer day;

    private Long lastNumber;

}
