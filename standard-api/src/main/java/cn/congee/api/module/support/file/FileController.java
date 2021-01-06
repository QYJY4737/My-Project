package cn.congee.api.module.support.file;

import cn.congee.api.common.anno.NoNeedLogin;
import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.constant.SwaggerTagConst;
import cn.congee.api.module.support.file.constant.FileServiceTypeEnum;
import cn.congee.api.module.support.file.domain.dto.FileAddDTO;
import cn.congee.api.module.support.file.domain.dto.FileQueryDTO;
import cn.congee.api.module.support.file.domain.vo.FileVO;
import cn.congee.api.module.support.file.domain.vo.UploadVO;
import cn.congee.api.module.support.file.service.FileService;
import cn.congee.api.module.system.login.domain.RequestTokenBO;
import cn.congee.api.util.StandardRequestTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 文件服务
 *
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午11:20
 **/
@RestController
@RequestMapping(value = "/api/file")
@Api(tags = {SwaggerTagConst.Admin.MANAGER_FILE})
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "文件本地上传", notes = "文件本地上传")
    @PostMapping("/localUpload/{moduleType}")
    public ResponseDTO<UploadVO> localUpload(@RequestParam(value = "file") MultipartFile file, @PathVariable Integer moduleType) throws Exception {
        return fileService.fileUpload(file, FileServiceTypeEnum.LOCAL, moduleType);
    }

    @ApiOperation(value = "获取本地文件URL", notes = "获取文件URL")
    @PostMapping("/get")
    public ResponseDTO<String> localGetFile(@RequestParam(value = "path") String path) {
        return fileService.getFileUrl(path, FileServiceTypeEnum.LOCAL);
    }

    @ApiOperation(value = "文件阿里云上传", notes = "文件阿里云上传")
    @PostMapping("/aliYunUpload/{moduleType}")
    public ResponseDTO<UploadVO> aliYunUpload(@RequestParam(value = "file") MultipartFile file, @PathVariable(value = "moduleType") Integer moduleType) throws Exception {
        return fileService.fileUpload(file, FileServiceTypeEnum.ALI_OSS, moduleType);
    }

    @ApiOperation(value = "获取阿里云文件URL", notes = "获取阿里云文件URL")
    @PostMapping("/aliYunGet")
    public ResponseDTO<String> aliYunGet(@RequestParam(value = "path") String path) {
        return fileService.getFileUrl(path, FileServiceTypeEnum.ALI_OSS);
    }

    @ApiOperation(value = "文件七牛云上传", notes = "文件七牛云上传")
    @PostMapping("/qiNiuUpload/{moduleType}")
    public ResponseDTO<UploadVO> qiNiuUpload(@RequestParam(value = "file") MultipartFile file, @PathVariable(value = "moduleType") Integer moduleType) throws Exception {
        return fileService.fileUpload(file, FileServiceTypeEnum.QI_NIU_OSS, moduleType);
    }

    @ApiOperation(value = "获取七牛云文件URL", notes = "获取七牛云URL")
    @PostMapping("/qiNiuGet")
    public ResponseDTO<String> qiNiuGet(@RequestParam(value = "path") String path) {
        return fileService.getFileUrl(path, FileServiceTypeEnum.QI_NIU_OSS);
    }

    @ApiOperation(value = "系统文件查询", notes = "系统文件查询")
    @PostMapping("/query")
    public ResponseDTO<PageResultDTO<FileVO>> queryListByPage(@RequestBody FileQueryDTO queryDTO) {
        return fileService.queryListByPage(queryDTO);
    }

    @ApiOperation(value = "系统文件下载通用接口（流下载）", notes = "系统文件下载通用接口（流下载）")
    @GetMapping("/downLoad")
    @NoNeedLogin
    public ResponseEntity<byte[]> downLoadById(@RequestParam(value = "id") Long id, HttpServletRequest request) {
        return fileService.downLoadById(id, request);
    }

    @ApiOperation(value = "系统文件保存通用接口", notes = "系统文件保存通用接口")
    @PostMapping("/save")
    public ResponseDTO<String> saveFile(@Valid @RequestBody FileAddDTO addDTO) {
        RequestTokenBO requestToken = StandardRequestTokenUtil.getRequestUser();
        return fileService.saveFile(addDTO,requestToken);
    }

}
