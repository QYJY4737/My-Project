package cn.congee.api.module.system.datascope.service;

import cn.congee.api.common.constant.ResponseCodeConst;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.module.system.datascope.DataScopeRoleDao;
import cn.congee.api.module.system.datascope.constant.DataScopeTypeEnum;
import cn.congee.api.module.system.datascope.constant.DataScopeViewTypeEnum;
import cn.congee.api.module.system.datascope.domain.dto.*;
import cn.congee.api.module.system.datascope.domain.entity.DataScopeRoleEntity;
import cn.congee.api.util.StandardBeanUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午2:13
 **/
@Slf4j
@Service
public class DataScopeService {

    @Autowired
    private DataScopeRoleDao dataScopeRoleDao;

    /**
     * 获取所有可以进行数据范围配置的信息
     *
     * @return
     */
    public ResponseDTO<List<DataScopeAndViewTypeVO>> dataScopeList() {
        log.info("获取所有可以进行数据范围配置的信息接口入参为空");
        List<DataScopeDTO> dataScopeList = this.getDataScopeType();
        List<DataScopeAndViewTypeVO> dataScopeAndTypeList = StandardBeanUtil.copyList(dataScopeList, DataScopeAndViewTypeVO.class);
        List<DataScopeViewTypeVO> typeList = this.getViewType();
        dataScopeAndTypeList.forEach(e -> {
            e.setViewTypeList(typeList);
        });
        log.info("获取所有可以进行数据范围配置的信息接口出参为dataScopeAndTypeList=[{}]", JSON.toJSONString(dataScopeAndTypeList));
        return ResponseDTO.succData(dataScopeAndTypeList);
    }

    /**
     * 获取当前系统存在的数据可见范围
     *
     * @return
     */
    public List<DataScopeViewTypeVO> getViewType() {
        List<DataScopeViewTypeVO> viewTypeList = Lists.newArrayList();
        DataScopeViewTypeEnum[] enums = DataScopeViewTypeEnum.class.getEnumConstants();
        DataScopeViewTypeVO dataScopeViewTypeDTO;
        for (DataScopeViewTypeEnum viewTypeEnum : enums) {
            dataScopeViewTypeDTO = DataScopeViewTypeVO.builder().viewType(viewTypeEnum.getValue()).viewTypeLevel(viewTypeEnum.getLevel()).viewTypeName(viewTypeEnum.getDesc()).build();
            viewTypeList.add(dataScopeViewTypeDTO);
        }
        Comparator<DataScopeViewTypeVO> comparator = (h1, h2) -> h1.getViewTypeLevel().compareTo(h2.getViewTypeLevel());
        viewTypeList.sort(comparator);
        return viewTypeList;
    }

    /**
     * 获取数据返回类型列表
     *
     * @return
     */
    public List<DataScopeDTO> getDataScopeType() {
        List<DataScopeDTO> dataScopeTypeList = Lists.newArrayList();
        DataScopeTypeEnum[] enums = DataScopeTypeEnum.class.getEnumConstants();
        DataScopeDTO dataScopeDTO;
        for (DataScopeTypeEnum typeEnum : enums) {
            dataScopeDTO =
                    DataScopeDTO.builder().dataScopeType(typeEnum.getValue()).dataScopeTypeDesc(typeEnum.getDesc()).dataScopeTypeName(typeEnum.getName()).dataScopeTypeSort(typeEnum.getSort()).build();
            dataScopeTypeList.add(dataScopeDTO);
        }
        Comparator<DataScopeDTO> comparator = (h1, h2) -> h1.getDataScopeTypeSort().compareTo(h2.getDataScopeTypeSort());
        dataScopeTypeList.sort(comparator);
        return dataScopeTypeList;
    }

    /**
     * 获取某个角色的数据范围设置信息
     *
     * @param roleId
     * @return
     */
    public ResponseDTO<List<DataScopeSelectVO>> dataScopeListByRole(Long roleId) {
        log.info("获取某个角色的数据范围设置信息接口入参为roleId=[{}]", roleId);
        List<DataScopeRoleEntity> dataScopeRoleEntityList = dataScopeRoleDao.listByRoleId(roleId);
        if (CollectionUtils.isEmpty(dataScopeRoleEntityList)) {
            return ResponseDTO.succData(Lists.newArrayList());
        }
        List<DataScopeSelectVO> dataScopeSelects = StandardBeanUtil.copyList(dataScopeRoleEntityList, DataScopeSelectVO.class);
        log.info("获取某个角色的数据范围设置信息接口出参为dataScopeSelects=[{}]", JSON.toJSONString(dataScopeSelects));
        return ResponseDTO.succData(dataScopeSelects);
    }

    /**
     * 批量设置某个角色的数据范围设置信息
     *
     * @param batchSetRoleDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> dataScopeBatchSet(DataScopeBatchSetRoleDTO batchSetRoleDTO) {
        log.info("批量设置某个角色的数据范围设置信息接口入参为batchSetRoleDTO=[{}]", JSON.toJSONString(batchSetRoleDTO));
        List<DataScopeBatchSetDTO> batchSetList = batchSetRoleDTO.getBatchSetList();
        if (CollectionUtils.isEmpty(batchSetList)) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "缺少配置信息");
        }
        List<DataScopeRoleEntity> dataScopeRoleEntityList = StandardBeanUtil.copyList(batchSetList, DataScopeRoleEntity.class);
        dataScopeRoleEntityList.forEach(e -> e.setRoleId(batchSetRoleDTO.getRoleId()));
        dataScopeRoleDao.deleteByRoleId(batchSetRoleDTO.getRoleId());
        dataScopeRoleDao.batchInsert(dataScopeRoleEntityList);
        return ResponseDTO.succ();
    }

}
