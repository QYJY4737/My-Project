package cn.congee.api.module.business.peony.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.congee.api.common.controller.BaseController;
import cn.congee.api.common.domain.PageResultDTO;
import cn.congee.api.common.domain.ResponseDTO;
import cn.congee.api.common.domain.ValidateList;
import cn.congee.api.module.business.peony.domain.dto.PeonyAddDTO;
import cn.congee.api.module.business.peony.domain.dto.PeonyQueryDTO;
import cn.congee.api.module.business.peony.domain.dto.PeonyUpdateDTO;
import cn.congee.api.module.business.peony.domain.vo.PeonyExcelVO;
import cn.congee.api.module.business.peony.domain.vo.PeonyVO;
import cn.congee.api.module.business.peony.service.PeonyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午10:13
 **/
@RestController
@Api(tags = {"牡丹花"})
@RequestMapping(value = "/peony")
public class PeonyController extends BaseController {

    @Autowired
    private PeonyService peonyService;

    @ApiOperation(value = "分页查询牡丹花",notes = "分页查询牡丹花")
    @PostMapping("/page/query")
    public ResponseDTO<PageResultDTO<PeonyVO>> queryByPage(@RequestBody PeonyQueryDTO queryDTO) {
        return peonyService.queryByPage(queryDTO);
    }

    @ApiOperation(value = "添加牡丹花",notes = "添加牡丹花")
    @PostMapping("/add")
    public ResponseDTO<String> add(@RequestBody @Validated PeonyAddDTO addTO){
        return peonyService.add(addTO);
    }

    @ApiOperation(value="修改牡丹花",notes = "修改牡丹花")
    @PostMapping("/update")
    public ResponseDTO<String> update(@RequestBody @Validated PeonyUpdateDTO updateDTO){
        return peonyService.update(updateDTO);
    }

    @ApiOperation(value="批量删除牡丹花",notes = "批量删除牡丹花")
    @PostMapping("/deleteByIds")
    public ResponseDTO<String> delete(@RequestBody @Validated ValidateList<Long> idList) {
        return peonyService.deleteByIds(idList);
    }

    @ApiOperation(value = "批量查询导出对象", notes = "批量查询导出对象")
    @PostMapping("/export/batch")
    public void batchExport(@RequestBody @Validated ValidateList<Long> idList, HttpServletResponse response) {
        //查询数据
        List<PeonyExcelVO> peonyList = peonyService.queryBatchExportData(idList);
        //导出操作
        ExportParams ex = new ExportParams("牡丹花", "Sheet1");
        Workbook workbook = ExcelExportUtil.exportExcel(ex, PeonyExcelVO.class, peonyList);
        downloadExcel("牡丹花", workbook, response);
    }

    @ApiOperation(value = "查询全部导出对象", notes = "查询全部导出对象")
    @PostMapping("/export/all")
    public void exportAll(@RequestBody @Validated PeonyQueryDTO queryDTO, HttpServletResponse response) {
        //查询数据
        List<PeonyExcelVO> peonyList = peonyService.queryAllExportData(queryDTO);
        //导出操作
        ExportParams ex = new ExportParams("牡丹花", "Sheet1");
        Workbook workbook = ExcelExportUtil.exportExcel(ex, PeonyExcelVO.class, peonyList);
        downloadExcel("牡丹花", workbook, response);
    }

}
