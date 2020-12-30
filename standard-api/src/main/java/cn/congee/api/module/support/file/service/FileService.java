package cn.congee.api.module.support.file.service;

import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.module.support.file.FileDao;
import cn.congee.api.module.support.file.constant.FileModuleTypeEnum;
import cn.congee.api.module.support.file.constant.FileResponseCodeConst;
import cn.congee.api.module.support.file.constant.FileServiceTypeEnum;
import cn.congee.api.module.support.file.domain.dto.FileAddDTO;
import cn.congee.api.module.support.file.domain.dto.FileDTO;
import cn.congee.api.module.support.file.domain.dto.FileQueryDTO;
import cn.congee.api.module.support.file.domain.entity.FileEntity;
import cn.congee.api.module.support.file.domain.vo.FileVO;
import cn.congee.api.module.support.file.domain.vo.UploadVO;
import cn.congee.api.module.system.login.domain.RequestTokenBO;
import cn.congee.api.util.StandardBaseEnumUtil;
import cn.congee.api.util.StandardBeanUtil;
import cn.congee.api.util.StandardPageUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.qiniu.http.Response;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:07
 **/
@Service
public class FileService {

    @Autowired
    private FileDao fileDao;

    @Autowired
    private java.util.Map<String, IFileService> fileServiceMap;

    /**
     * 获取文件服务实现
     *
     * @param typeEnum
     * @return
     */
    private IFileService getFileService(FileServiceTypeEnum typeEnum) {
        /**
         * 获取文件服务
         */
        String serviceName = typeEnum.getServiceName();
        IFileService fileService = fileServiceMap.get(serviceName);
        if (null == fileService) {
            throw new RuntimeException("未找到文件服务实现类：" + serviceName);
        }
        return fileService;
    }

    /**
     * 文件上传服务
     *
     * @param file
     * @param typeEnum   文件服务类型枚举类
     * @param moduleType 文件夹类型
     * @return
     */
    public ResponseDTO<UploadVO> fileUpload(MultipartFile file, FileServiceTypeEnum typeEnum, Integer moduleType) {
        FileModuleTypeEnum moduleTypeEnum = StandardBaseEnumUtil.getEnumByValue(moduleType, FileModuleTypeEnum.class);
        if (null == moduleTypeEnum) {
            return ResponseDTO.wrap(FileResponseCodeConst.FILE_MODULE_ERROR);
        }
        // 获取文件服务
        IFileService fileService = this.getFileService(typeEnum);
        ResponseDTO<UploadVO> response = fileService.fileUpload(file, moduleTypeEnum.getPath());
        return response;
    }

    /**
     * 以文件形式上传
     *
     * @param file
     * @param typeEnum
     * @return
     */
    public ResponseDTO<UploadVO> uploadByFile(MultipartFile file, FileServiceTypeEnum typeEnum) {
        // 获取文件服务
        IFileService fileService = this.getFileService(typeEnum);
        ResponseDTO<UploadVO> response = fileService.uploadByFile(file);
        return response;
    }

    /**
     * 以流形式上传
     *
     * @param stream
     * @return
     */
    public ResponseDTO<UploadVO> uploadByStream(InputStream stream, FileServiceTypeEnum typeEnum) {
        // 获取文件服务
        IFileService fileService = this.getFileService(typeEnum);
        ResponseDTO<UploadVO> response = fileService.uploadByStream(stream);
        return response;
    }

    /**
     * 根据key删除七牛云相关文件
     *
     * @param key
     * @return
     */
    public ResponseDTO<Response> deleteByKey(String key, FileServiceTypeEnum typeEnum) {
        // 获取文件服务
        IFileService fileService = this.getFileService(typeEnum);
        ResponseDTO<Response> response = fileService.deleteByKey(key);
        return response;
    }

    /**
     * 根据文件绝对路径 获取文件URL
     *
     * @param path
     * @return
     */
    public ResponseDTO<String> getFileUrl(String path, FileServiceTypeEnum typeEnum) {
        IFileService fileService = this.getFileService(typeEnum);
        return fileService.getFileUrl(path);
    }

    /**
     * 批量插入
     *
     * @param fileDTOList
     */
    public void insertFileBatch(List<FileDTO> fileDTOList) {
        fileDao.insertFileBatch(fileDTOList);
    }

    /**
     * 根据module 删除文件信息
     *
     * @param moduleId
     * @return
     */
    public void deleteFilesByModuleId(String moduleId) {
        fileDao.deleteFilesByModuleId(moduleId);
    }

    /**
     * 根据module 获取文件信息
     *
     * @param moduleId
     * @return
     */
    public List<FileVO> listFilesByModuleId(String moduleId) {
        return fileDao.listFilesByModuleId(moduleId);
    }

    /**
     * @param filesStr 逗号分隔文件id字符串
     * @return
     */
    public List<FileVO> getFileDTOList(String filesStr) {
        if (StringUtils.isEmpty(filesStr)) {
            return Lists.newArrayList();
        }
        String[] fileIds = filesStr.split(",");
        List<Long> fileIdList = Arrays.asList(fileIds).stream().map(e -> Long.valueOf(e)).collect(Collectors.toList());
        List<FileVO> files = fileDao.listFilesByFileIds(fileIdList);
        return files;
    }

    /**
     * 分页查询文件列表
     *
     * @param queryDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<FileVO>> queryListByPage(FileQueryDTO queryDTO) {
        Page page = StandardPageUtil.convert2QueryPage(queryDTO);
        List<FileVO> fileList = fileDao.queryListByPage(page, queryDTO);
        if (CollectionUtils.isNotEmpty(fileList)) {
            fileList.forEach(e -> {
                // 根据文件服务类 获取对应文件服务 查询 url
                FileServiceTypeEnum serviceTypeEnum = StandardBaseEnumUtil.getEnumByValue(e.getFileLocationType(), FileServiceTypeEnum.class);
                IFileService fileService = this.getFileService(serviceTypeEnum);
                e.setFileUrl(fileService.getFileUrl(e.getFilePath()).getData());
            });
        }
        PageResultDTO<FileVO> pageResultDTO = StandardPageUtil.convert2PageResult(page, fileList);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 根据id 下载文件
     *
     * @param id
     * @param request
     * @return
     */
    public ResponseEntity<byte[]> downLoadById(Long id, HttpServletRequest request) {
        FileEntity entity = fileDao.selectById(id);
        if (null == entity) {
            throw new RuntimeException("文件信息不存在");
        }

        // 根据文件服务类 获取对应文件服务 查询 url
        FileServiceTypeEnum serviceTypeEnum = StandardBaseEnumUtil.getEnumByValue(entity.getFileLocationType(), FileServiceTypeEnum.class);
        IFileService fileService = this.getFileService(serviceTypeEnum);
        ResponseEntity<byte[]> stream = fileService.fileDownload(entity.getFilePath(), entity.getFileName(), request);
        return stream;
    }

    /**
     * 系统文件保存通用接口
     * @param addDTO
     * @return
     */
    public ResponseDTO<String> saveFile(FileAddDTO addDTO, RequestTokenBO requestToken) {
        FileEntity entity = StandardBeanUtil.copy(addDTO,FileEntity.class);
        entity.setCreaterUser(requestToken.getRequestUserId());
        entity.setCreateTime(new Date());
        fileDao.insert(entity);
        return ResponseDTO.succ();
    }

}
