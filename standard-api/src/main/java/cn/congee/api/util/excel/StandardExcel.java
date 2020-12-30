package cn.congee.api.util.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by wgb
 * Date: 2020/12/29
 * Time: 下午3:15
 **/
public class StandardExcel {

    List<StandardSheet> sheetList = new ArrayList<StandardSheet>();;

    public StandardExcel(String fileName) {
        org.apache.poi.ss.usermodel.Workbook workbook = null;
        try {
            workbook = fileName.endsWith(".xls") ? new HSSFWorkbook(new FileInputStream(fileName)) : new XSSFWorkbook(new FileInputStream(fileName));
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int index = 0; index < numberOfSheets; index++) {
                addSheet(new StandardSheet(workbook.getSheetAt(index)));
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                }
                workbook = null;
            }
        }
    }

    public StandardExcel(InputStream ins, StandardExcelFileType fileType) {
        org.apache.poi.ss.usermodel.Workbook workbook = null;
        try {
            workbook = fileType == StandardExcelFileType.XLS ? new HSSFWorkbook(ins) : new XSSFWorkbook(ins);
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int index = 0; index < numberOfSheets; index++) {
                addSheet(new StandardSheet(workbook.getSheetAt(index)));
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                }
                workbook = null;
            }
        }
    }

    final protected void addSheet(StandardSheet sheet) {
        this.sheetList.add(sheet);
    }

    final protected void addSheetList(Collection<StandardSheet> sheets) {
        this.sheetList.addAll(sheets);
    }

    final public List<StandardSheet> getSheetList() {
        return sheetList;
    }

    final public StandardSheet getSheet(String sheetName) {
        for (StandardSheet sheet : sheetList) {
            if (sheet.getName().equals(sheetName)) {
                return sheet;
            }
        }
        return null;
    }

}
