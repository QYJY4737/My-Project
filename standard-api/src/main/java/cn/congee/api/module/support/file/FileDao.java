package cn.congee.api.module.support.file;

import cn.congee.api.module.support.file.domain.dto.FileDTO;
import cn.congee.api.module.support.file.domain.dto.FileQueryDTO;
import cn.congee.api.module.support.file.domain.entity.FileEntity;
import cn.congee.api.module.support.file.domain.vo.FileVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:09
 **/
@Mapper
@Component
public interface FileDao extends BaseMapper<FileEntity> {

    /**
     * 批量添加上传文件
     *
     * @param fileDTOList
     * @return
     */
    Integer insertFileBatch(List<FileDTO> fileDTOList);


    /**
     * 批量添加上传文件
     *
     * @param fileDTOList
     * @return
     */
    Integer insertFileEntityBatch(List<FileEntity> fileDTOList);

    /**
     * 批量删除
     *
     * @param moduleId
     * @return
     */
    Integer deleteFilesByModuleId(@Param(value = "moduleId") String moduleId);

    /**
     * 批量删除
     *
     * @param moduleId
     * @param moduleType
     * @return
     */
    Integer deleteFilesByModuleIdAndModuleType(@Param(value = "moduleId") String moduleId, @Param(value = "moduleType") String moduleType);

    /**
     * @param moduleId
     * @return
     */
    List<FileVO> listFilesByModuleId(@Param(value = "moduleId") String moduleId);

    /**
     *
     * @param fileIds
     * @return
     */
    List<FileVO> listFilesByFileIds(@Param(value = "fileIds") List<Long> fileIds);

    /**
     *
     * @param moduleId
     * @param moduleType
     * @return
     */
    List<FileVO> listFilesByModuleIdAndModuleType(@Param(value = "moduleId") String moduleId, @Param(value = "moduleType") String moduleType);

    /**
     *
     * @param moduleId
     * @param moduleTypes
     * @return
     */
    List<FileVO> listFilesByModuleIdAndModuleTypes(@Param(value = "moduleId") String moduleId, @Param(value = "moduleTypes") List<String> moduleTypes);

    /**
     *
     * @param moduleIds
     * @param moduleType
     * @return
     */
    List<FileVO> listFilesByModuleIdsAndModuleType(@Param(value = "moduleIds") List<String> moduleIds, @Param(value = "moduleType") String moduleType);

    /**
     *
     * @param page
     * @param queryDTO
     * @return
     */
    List<FileVO> queryListByPage(Page page, @Param(value = "queryDTO") FileQueryDTO queryDTO);

}
