package cn.congee.api.module.system.systemconfig;

import cn.congee.api.common.constant.JudgeEnum;
import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.common.reload.annotation.StandardReload;
import cn.congee.api.common.constant.ResponseCodeConst;
import cn.congee.api.constant.StandardReloadTagConst;
import cn.congee.api.module.support.standardreload.StandardReloadService;
import cn.congee.api.module.system.systemconfig.constant.SystemConfigDataType;
import cn.congee.api.module.system.systemconfig.constant.SystemConfigEnum;
import cn.congee.api.module.system.systemconfig.constant.SystemConfigResponseCodeConst;
import cn.congee.api.module.system.systemconfig.domain.dto.*;
import cn.congee.api.module.system.systemconfig.domain.entity.SystemConfigEntity;
import cn.congee.api.util.StandardBeanUtil;
import cn.congee.api.util.StandardPageUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * 系统配置业务类
 *
 * Created by wgb
 * Date: 2020/12/28
 * Time: 下午3:52
 **/
@Slf4j
@Service
public class SystemConfigService {

    /**
     * 系统配置缓存
     */
    private ConcurrentHashMap<String, SystemConfigEntity> systemConfigMap = new ConcurrentHashMap<>();

    @Autowired
    private SystemConfigDao systemConfigDao;

    @Autowired
    private StandardReloadService standardReloadService;

    /**
     * 初始化系统设置缓存
     */
    @PostConstruct
    private void initSystemConfigCache() {
        List<SystemConfigEntity> entityList = systemConfigDao.selectAll();
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }

        systemConfigMap.clear();
        entityList.forEach(entity -> this.systemConfigMap.put(entity.getConfigKey().toLowerCase(), entity));
        log.info("系统设置缓存初始化完毕:{}", systemConfigMap.size());

        standardReloadService.register(this);
    }

    @StandardReload(StandardReloadTagConst.SYSTEM_CONFIG)
    public boolean reload(String args) {
        this.initSystemConfigCache();
        log.warn("<<SmartReload>> <<{}>> , args {} reload success ", StandardReloadTagConst.SYSTEM_CONFIG, args);
        return true;
    }

    /**
     * 分页获取系统配置
     *
     * @param queryDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<SystemConfigVO>> getSystemConfigPage(SystemConfigQueryDTO queryDTO) {
        log.info("分页获取系统配置接口入参为queryDTO=[{}]", JSON.toJSONString(queryDTO));
        Page page = StandardPageUtil.convert2QueryPage(queryDTO);
        if(queryDTO.getKey() != null){
            queryDTO.setKey(queryDTO.getKey().toLowerCase());
        }
        List<SystemConfigEntity> entityList = systemConfigDao.selectSystemSettingList(page, queryDTO);
        PageResultDTO<SystemConfigVO> pageResultDTO = StandardPageUtil.convert2PageResult(page, entityList, SystemConfigVO.class);
        log.info("分页获取系统配置接口出参为pageResultDTO=[{}]", JSON.toJSONString(pageResultDTO));
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 根据参数key获得一条数据（数据库）
     *
     * @param configKey
     * @return
     */
    public ResponseDTO<SystemConfigVO> selectByKey(String configKey) {
        log.info("根据参数key获得一条数据（数据库）接口入参为configKey=[{}]", configKey);
        if(configKey != null){
            configKey = configKey.toLowerCase();
        }
        SystemConfigEntity entity = systemConfigDao.getByKey(configKey);
        if (entity == null) {
            return ResponseDTO.wrap(SystemConfigResponseCodeConst.NOT_EXIST);
        }
        SystemConfigVO configDTO = StandardBeanUtil.copy(entity, SystemConfigVO.class);
        log.info("根据参数key获得一条数据（数据库）接口出参为configDTO=[{}]", JSON.toJSONString(configDTO));
        return ResponseDTO.succData(configDTO);
    }

    /**
     * 根据参数key获得一条数据 并转换为 对象
     *
     * @param configKey
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T selectByKey2Obj(String configKey, Class<T> clazz) {
        if(configKey != null){
            configKey = configKey.toLowerCase();
        }
        SystemConfigEntity entity = systemConfigDao.getByKey(configKey);
        if (entity == null) {
            return null;
        }
        SystemConfigDTO configDTO = StandardBeanUtil.copy(entity, SystemConfigDTO.class);
        String configValue = configDTO.getConfigValue();
        if (StringUtils.isEmpty(configValue)) {
            return null;
        }
        T obj = JSON.parseObject(configValue, clazz);
        return obj;
    }

    public SystemConfigDTO getCacheByKey(SystemConfigEnum.Key key) {
        SystemConfigEntity entity = this.systemConfigMap.get(key.name().toLowerCase());
        if (entity == null) {
            return null;
        }
        return StandardBeanUtil.copy(entity, SystemConfigDTO.class);
    }

    /**
     * 添加系统配置
     *
     * @param configAddDTO
     * @return
     */
    public ResponseDTO<String> addSystemConfig(SystemConfigAddDTO configAddDTO) {
        log.info("添加系统配置接口入参为configAddDTO=[{}]", JSON.toJSONString(configAddDTO));
        configAddDTO.setConfigKey(configAddDTO.getConfigKey().toLowerCase());
        SystemConfigEntity entity = systemConfigDao.getByKey(configAddDTO.getConfigKey());
        if (entity != null) {
            return ResponseDTO.wrap(SystemConfigResponseCodeConst.ALREADY_EXIST);
        }
        ResponseDTO valueValid = this.configValueValid(configAddDTO.getConfigKey(),configAddDTO.getConfigValue());
        if(!valueValid.isSuccess()){
            return valueValid;
        }
        configAddDTO.setConfigKey(configAddDTO.getConfigKey().toLowerCase());
        SystemConfigEntity addEntity = StandardBeanUtil.copy(configAddDTO, SystemConfigEntity.class);
        addEntity.setIsUsing(JudgeEnum.YES.getValue());
        systemConfigDao.insert(addEntity);
        //刷新缓存
        this.initSystemConfigCache();
        return ResponseDTO.succ();
    }

    /**
     * 更新系统配置
     *
     * @param updateDTO
     * @return
     */
    public ResponseDTO<String> updateSystemConfig(SystemConfigUpdateDTO updateDTO) {
        log.info("更新系统配置接口入参为updateDTO=[{}]", JSON.toJSONString(updateDTO));
        updateDTO.setConfigKey(updateDTO.getConfigKey().toLowerCase());
        SystemConfigEntity entity = systemConfigDao.selectById(updateDTO.getId());
        //系统配置不存在
        if (entity == null) {
            return ResponseDTO.wrap(SystemConfigResponseCodeConst.NOT_EXIST);
        }
        SystemConfigEntity alreadyEntity = systemConfigDao.getByKeyExcludeId(updateDTO.getConfigKey().toLowerCase(), updateDTO.getId());
        if (alreadyEntity != null) {
            return ResponseDTO.wrap(SystemConfigResponseCodeConst.ALREADY_EXIST);
        }
        ResponseDTO valueValid = this.configValueValid(updateDTO.getConfigKey(),updateDTO.getConfigValue());
        if(!valueValid.isSuccess()){
            return valueValid;
        }
        entity = StandardBeanUtil.copy(updateDTO, SystemConfigEntity.class);
        updateDTO.setConfigKey(updateDTO.getConfigKey().toLowerCase());
        systemConfigDao.updateById(entity);

        //刷新缓存
        this.initSystemConfigCache();
        return ResponseDTO.succ();
    }


    private ResponseDTO<String> configValueValid(String configKey , String configValue){
        SystemConfigEnum.Key configKeyEnum = SystemConfigEnum.Key.selectByKey(configKey);
        if(configKeyEnum == null){
            return ResponseDTO.succ();
        }
        SystemConfigDataType dataType = configKeyEnum.getDataType();
        if(dataType == null){
            return ResponseDTO.succ();
        }
        if(dataType.name().equals(SystemConfigDataType.TEXT.name())){
            return ResponseDTO.succ();
        }
        if(dataType.name().equals(SystemConfigDataType.JSON.name())){
            try {
                JSONObject jsonStr = JSONObject.parseObject(configValue);
                return ResponseDTO.succ();
            } catch (Exception e) {
                return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM,"数据格式不是JSON,请修改后提交。");
            }
        }
        if(StringUtils.isNotEmpty(dataType.getValid())){
            Boolean valid = Pattern.matches(dataType.getValid(), configValue);
            if(valid){
                return ResponseDTO.succ();
            }
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM,"数据格式不是"+dataType.name().toLowerCase()+",请修改后提交。");
        }

        return ResponseDTO.succ();
    }

    /**
     * 根据分组名称 获取获取系统设置
     *
     * @param group
     * @return
     */
    public ResponseDTO<List<SystemConfigVO>> getListByGroup(String group) {
        log.info("根据分组名称 获取获取系统设置接口入参为group=[{}]", group);
        List<SystemConfigEntity> entityList = systemConfigDao.getListByGroup(group);
        if (CollectionUtils.isEmpty(entityList)) {
            return ResponseDTO.succData(Lists.newArrayList());
        }
        List<SystemConfigVO> systemConfigList = StandardBeanUtil.copyList(entityList, SystemConfigVO.class);
        log.info("根据分组名称 获取获取系统设置接口出参为systemConfigList=[{}]", JSON.toJSONString(systemConfigList));
        return ResponseDTO.succData(systemConfigList);
    }

    /**
     * 根据分组名称 获取获取系统设置
     *
     * @param group
     * @return
     */
    public List<SystemConfigDTO> getListByGroup(SystemConfigEnum.Group group) {
        List<SystemConfigEntity> entityList = systemConfigDao.getListByGroup(group.name());
        if (CollectionUtils.isEmpty(entityList)) {
            return Lists.newArrayList();
        }
        List<SystemConfigDTO> systemConfigList = StandardBeanUtil.copyList(entityList, SystemConfigDTO.class);
        return systemConfigList;
    }

}
