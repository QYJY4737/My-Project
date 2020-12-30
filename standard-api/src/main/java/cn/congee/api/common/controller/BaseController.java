package cn.congee.api.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 上午10:13
 **/
@Slf4j
public class BaseController {

    /**
     * 下载 Excel 消息头
     * @param fileName
     * @param workbook
     * @param response
     */
    public void downloadExcel(String fileName, Workbook workbook, HttpServletResponse response) {
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
        try {
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            log.error("", e);
        }
    }

}
