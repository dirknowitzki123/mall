package com.kingyon.chengxin.product.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtil {

    /**
     * 导出Exceld
     *
     * @param sheetName sheet名称
     * @param title     标题
     * @param values    内容
     * @return
     */
    public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values) {

        HSSFWorkbook workbook = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow row1 = sheet.createRow(0);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        HSSFCell cell = row1.createCell(0);

        // 1.生成字体对象
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 12);
        font.setFontName("新宋体");

        // 2.生成样式对象
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font); // 调用字体样式对象
        style.setWrapText(true);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//设置居中样式
        style.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);

        // 3.单元格应用样式
        cell.setCellStyle(style);

        //设置单元格内容
        cell.setCellValue(sheetName);
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, title.length));
        //在sheet里创建第二行
        HSSFRow row2 = sheet.createRow(1);
        row2.setHeightInPoints(25);
        //声明列对象
        HSSFCell cellTitle = null;
        //创建单元格并设置单元格内容及样式
        for (int i = 0; i < title.length; i++) {
            cellTitle = row2.createCell(i);
            cellTitle.setCellStyle(style);
            cellTitle.setCellValue(title[i]);

        }

        for (int i = 0; i < values.length; i++) {
            row2 = sheet.createRow(i + 2);
            for (int j = 0; j < values[i].length; j++) {
                //将内容按顺序赋给对应的列对象
                sheet.autoSizeColumn((short) j);
                row2.setHeightInPoints(25);
                HSSFCell cell1 = row2.createCell(j);
                cell1.setCellValue(values[i][j]);
                cell1.setCellStyle(style);
            }
        }

        return workbook;

    }

    ;

}
