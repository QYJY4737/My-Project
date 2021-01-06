package cn.congee.api.module.support.standardreload.dao;

import cn.congee.api.module.support.standardreload.domain.entity.ReloadResultEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * t_reload_result 数据表dao
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午5:56
 **/
@Mapper
@Component
public interface ReloadResultDao extends BaseMapper<ReloadResultEntity> {

    List<ReloadResultEntity> query(@Param(value = "tag") String tag);

}
