package cn.congee.api.module.support.standardreload.dao;

import cn.congee.api.module.support.standardreload.domain.entity.ReloadItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * t_reload_item 数据表dao
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午5:54
 **/
@Mapper
@Component
public interface ReloadItemDao extends BaseMapper<ReloadItemEntity> {
}
