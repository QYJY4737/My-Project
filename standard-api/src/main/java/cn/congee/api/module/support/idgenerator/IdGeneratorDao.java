package cn.congee.api.module.support.idgenerator;

import cn.congee.api.module.support.idgenerator.domain.IdGeneratorEntity;
import cn.congee.api.module.support.idgenerator.domain.IdGeneratorLastNumberDTO;
import cn.congee.api.module.support.idgenerator.domain.IdGeneratorRecordDTO;
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

    void updateLastNumber(@Param("generatorId") Long generatorId, @Param("lastNumber") Long lastNumber);

    int replaceIdGeneratorRecord(@Param("generatorId") Long generatorId,//
                                 @Param("year") int year,//
                                 @Param("month") int month,//
                                 @Param("day") int day,//
                                 @Param("lastNumber") Long lastNumber);

    IdGeneratorRecordDTO selectHistoryLastNumber(@Param("generatorId") Long generatorId,
                                                 @Param("year") int year,
                                                 @Param("month") int month,
                                                 @Param("day") int day);

}
