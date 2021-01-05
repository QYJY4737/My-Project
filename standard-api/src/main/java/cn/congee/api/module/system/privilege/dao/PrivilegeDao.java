package cn.congee.api.module.system.privilege.dao;

import cn.congee.api.module.system.privilege.domain.entity.PrivilegeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午6:13
 **/
@Mapper
@Component
public interface PrivilegeDao extends BaseMapper<PrivilegeEntity> {

    /**
     * 根据权限key删除
     * @param keyList
     */
    void delByKeyList(@Param(value = "keyList") List<String> keyList);
    /**
     * 根据权限parentkey删除
     * @param keyList
     */
    void delByParentKeyList(@Param(value = "keyList") List<String> keyList);

    /**
     * 批量保存
     * @param privilegeList
     */
    void batchInsert(List<PrivilegeEntity> privilegeList);

    /**
     * 批量更新
     * @param privilegeList
     */
    void batchUpdate(@Param(value = "updateList") List<PrivilegeEntity> privilegeList);

    /**
     * 根据父节点key查询
     * @param parentKey
     * @return
     */
    List<PrivilegeEntity> selectByParentKey(@Param(value = "parentKey") String parentKey);

    /**
     * 根据父节点key查询
     * @param keyList
     * @return
     */
    List<PrivilegeEntity> selectByKeyList(@Param(value = "keyList") List<String> keyList);

    /**
     * 根据权限key查询
     * @param key
     * @return
     */
    PrivilegeEntity selectByKey(@Param(value = "key") String key);

    /**
     * 根据类型查询
     * @param type
     * @return
     */
    List<PrivilegeEntity> selectByExcludeType(@Param(value = "type") Integer type);

    /**
     * 根据类型查询
     * @param type
     * @return
     */
    List<PrivilegeEntity> selectByType(@Param(value = "type") Integer type);

    /**
     * 查询所有权限
     * @return
     */
    List<PrivilegeEntity> selectAll();

}
