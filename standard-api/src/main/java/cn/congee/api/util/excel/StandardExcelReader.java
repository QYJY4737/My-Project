package cn.congee.api.util.excel;

import cn.congee.api.util.StandardFileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午3:16
 **/
public class StandardExcelReader {

    public static StandardExcel openExcel(String filePath) throws IOException {
        StandardFileUtil.isFileExistThrowException(filePath);
        return new StandardExcel(new File(filePath).getCanonicalPath());
    }

    public static StandardExcel openExcel(File file) throws IOException {
        return new StandardExcel(file.getCanonicalPath());
    }

    public static StandardExcel openExcel(InputStream ins, StandardExcelFileType fileType) throws IOException {
        return new StandardExcel(ins, fileType);
    }

    public static void main(String[] args) throws Exception {
        StandardExcel smartExcel = openExcel(new FileInputStream(new File("F:/privilege.xlsx")), StandardExcelFileType.XLSX);
        System.out.println(smartExcel.getSheetList());
    }

}
