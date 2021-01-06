package cn.congee.api.module.support.idgenerator;

import cn.congee.api.module.support.idgenerator.domain.entity.IdGeneratorEntity;
import cn.congee.api.module.support.idgenerator.domain.dto.IdGeneratorLastNumberDTO;
import cn.congee.api.module.support.idgenerator.domain.dto.IdGeneratorRecordDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:40
 **/
@Mapper
public interface IdGeneratorDao {

    IdGeneratorLastNumberDTO selectLastNumber(Long id);

    List<IdGeneratorEntity> selectAll();

    void updateLastNumber(@Param(value = "generatorId") Long generatorId, @Param(value = "lastNumber") Long lastNumber);

    int replaceIdGeneratorRecord(@Param(value = "generatorId") Long generatorId,
                                 @Param(value = "year") int year,
                                 @Param(value = "month") int month,
                                 @Param(value = "day") int day,
                                 @Param(value = "lastNumber") Long lastNumber);

    IdGeneratorRecordDTO selectHistoryLastNumber(@Param(value = "generatorId") Long generatorId,
                                                 @Param(value = "year") int year,
                                                 @Param(value = "month") int month,
                                                 @Param(value = "day") int day);

}
