package com.java.springboot.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FontUnderline;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteSampleXSSFExcel {

    public static void main(String args[]){
        System.out.println("Hello from WriteSampleXSSFExcel");
        generateSampleXSSFExcel("./excel/XLSXExample.xlsx");
    }

    private static final Map<String,XSSFCellStyle> styleMap = new HashMap<>();
    private static final Map<String,XSSFFont> fontMap = new HashMap<>();

    public static XSSFWorkbook generateSampleXSSFExcel(XSSFWorkbook wb){

        XSSFSheet sheet = XSSFFactoryUtil.createSheet(wb, "Cell Types", 0);

        XSSFCellStyle primaryHeaderStyle = getPrimaryHeaderStyle(wb);
        styleMap.put("primary_header", primaryHeaderStyle);

        XSSFCellStyle secondaryHeaderStyle = getSecondaryStyle(wb);
        styleMap.put("secondary_header", secondaryHeaderStyle);

        XSSFCellStyle secondaryHeaderRightAlignStyle = getSecondaryStyleRightAlign(wb);
        styleMap.put("secondary_header_right_align", secondaryHeaderRightAlignStyle);

        XSSFCellStyle defaultTextStyle = getDefaultTextStyle(wb);
        styleMap.put("default_text", defaultTextStyle);

        XSSFCellStyle defaultTextStyleBold = getDefaultTextStyleBold(wb);
        styleMap.put("default_text_bold", defaultTextStyleBold);

        XSSFCellStyle defaultTahomaTextStyle = getDefaultTahomaTextStyle(wb);
        styleMap.put("default_tahoma_text", defaultTahomaTextStyle);

        XSSFCellStyle defaultTahomaTextStyleCurrency = getDefaultTahomaTextStyleCurrency(wb);
        styleMap.put("default_tahoma_text_currency", defaultTahomaTextStyleCurrency);

        XSSFCellStyle defaultTahomaTextStyleInteger = getDefaultTahomaTextStyleInteger(wb);
        styleMap.put("default_tahoma_text_integer", defaultTahomaTextStyleInteger);

        XSSFCellStyle defaultTahomaTextStyleDouble = getDefaultTahomaTextStyleDouble(wb);
        styleMap.put("default_tahoma_text_double", defaultTahomaTextStyleDouble);

        XSSFCellStyle defaultTahomaTextStyleBorder_left_right = getDefaultTahomaTextStyleBorderLeftRight(wb);
        styleMap.put("default_tahoma_text_border_left_right", defaultTahomaTextStyleBorder_left_right);

        XSSFCellStyle defaultTahomaTextStyleBorder_top_left = getDefaultTahomaTextStyleBorderCustom(wb,true,true,true,false);
        styleMap.put("default_tahoma_text_border_top_left", defaultTahomaTextStyleBorder_top_left);

        XSSFCellStyle hyperlinkStyle = getHyperLinkStyle(wb);
        styleMap.put("hyperlink", hyperlinkStyle);

        XSSFCellStyle labelTextStyle = getLabelCellStyle(wb);
        styleMap.put("label_text", labelTextStyle);

        int row = 2;
        XSSFCell cell1 = XSSFFactoryUtil.buildCell(sheet, 0, row)
                .value("Primary Header Style")
                .style(primaryHeaderStyle)
                .build();
        row += 2;

        XSSFCell cell2 = XSSFFactoryUtil.buildCell(sheet, 0, row)
                .value("Primary Header Style Merged")
                .style(primaryHeaderStyle)
                .build();
        sheet.addMergedRegion(new CellRangeAddress(row,row,0,2));
        row += 2;

        XSSFCell cell3 = XSSFFactoryUtil.buildCell(sheet, 0, row).value("Secondary Header Style").style(secondaryHeaderStyle).build();
        row += 2;

        XSSFCell cell31 = XSSFFactoryUtil.buildCell(sheet, 0, row).value("Right Align").style(secondaryHeaderRightAlignStyle).build();
        row += 2;

        XSSFCell cell4 = XSSFFactoryUtil.buildCell(sheet, 0, row).value("Default Text Style").style(defaultTextStyle).build();
        row += 2;

        XSSFCell cell41 = XSSFFactoryUtil.buildCell(sheet, 0, row).value("Default Text Style Bold").style(defaultTextStyleBold).build();
        row += 2;

        XSSFCell cell42 = XSSFFactoryUtil.buildCell(sheet, 2, row).value("Default Text Style Border").style(defaultTahomaTextStyleBorder_left_right).build();
        row += 2;

        XSSFCell cell5 = XSSFFactoryUtil.buildCell(sheet, 0, row).value("Label Text Style").style(labelTextStyle).build();
        row += 2;

        XSSFCell cell6 = XSSFFactoryUtil.buildCell(sheet, 1, row).value(23+"").style(defaultTahomaTextStyle).build();
        row += 2;

        XSSFCell cell7 = XSSFFactoryUtil.buildCell(sheet, 1, row).value(23).style(defaultTahomaTextStyle).build();
        row += 2;

        XSSFCell cell71 = XSSFFactoryUtil.buildCell(sheet, 1, row).value(84).style(defaultTahomaTextStyleCurrency).build();
        row += 2;

        XSSFCell cell72 = XSSFFactoryUtil.buildCell(sheet, 1, row).value(1284.25416).style(defaultTahomaTextStyleDouble).build();
        row += 2;

        XSSFCell cell73 = XSSFFactoryUtil.buildCell(sheet, 1, row).value(1284).style(defaultTahomaTextStyleInteger).build();
        row += 2;

        XSSFCell cell74 = XSSFFactoryUtil.buildCell(sheet, 0, row).value("Secret Link")
                .style(hyperlinkStyle).url("https://www.google.com/search?q=apache+poi")
                .build();
        row += 2;

        XSSFCell cell77 = XSSFFactoryUtil.buildCell(sheet, 0, row).value("Secret Link2")
                .url("https://www.google.com/search?q=apache+poi+again")
                .style(hyperlinkStyle).build();
        row += 2;

        XSSFCell cell78 = XSSFFactoryUtil.buildCell(sheet, 1, row).value("merged region1")
                .style(defaultTahomaTextStyleBorder_top_left).build();
        XSSFCell cell79 = XSSFFactoryUtil.buildCell(sheet, 3, row).value("value2")
                .style(defaultTahomaTextStyleBorder_top_left).build();
        row+=2;

        // XSSFFactoryUtil.mergeAdapter(sheet, 1, row, 3, row); row+=4;
        XSSFCell cell81 = XSSFFactoryUtil.buildCell(sheet, 1, row).value("merged region88")
                .style(defaultTahomaTextStyleBorder_top_left).build();
        // XSSFCell cell82 = XSSFFactoryUtil.buildCell(sheet, 2, row).value("merged region3")
        //                                     // .style(defaultTahomaTextStyleBorder_top_left)
        //                                     .build();
        // XSSFCell cell83 = XSSFFactoryUtil.buildCell(sheet, 3, row).value("merged region3")
        //                                     // .style(defaultTahomaTextStyleBorder_top_left)
        //                                     .build();
        XSSFFactoryUtil.mergeCellWithStyle(sheet, 1, row, 3, row);
        row+=2;


        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(2);
        return wb;
    }

    public static void generateSampleXSSFExcel(String fileName) {
        try {
            XSSFWorkbook wb = generateSampleXSSFExcel(new XSSFWorkbook());
            FileOutputStream fos = new FileOutputStream(fileName);
            wb.write(fos);
            fos.close();
        }catch(Exception e){
            e.printStackTrace();;
        }
    }

    private static XSSFCellStyle getLabelCellStyle(XSSFWorkbook xssfWorkbook){
        XSSFCellStyle style = null;
        XSSFFont font = XSSFFactoryUtil.createFont(xssfWorkbook, XSSFFactoryUtil.FONT_ARIAL, 12, XSSFFactoryUtil.WEIGHT_BOLD, XSSFFactoryUtil.COLOR_BROWN);
        style = XSSFFactoryUtil.buildCellStyle(xssfWorkbook).font(font).build();
        return style;
    }

    private static XSSFCellStyle getPrimaryHeaderStyle(XSSFWorkbook xssfWorkbook){
        XSSFCellStyle style = null;
        XSSFFont font = XSSFFactoryUtil.createFont(xssfWorkbook, XSSFFactoryUtil.FONT_TAHOMA, 10, XSSFFactoryUtil.WEIGHT_BOLD, XSSFFactoryUtil.COLOR_BLACK);
        style = XSSFFactoryUtil.buildCellStyle(xssfWorkbook).font(font).bgColor(XSSFFactoryUtil.COLOR_BLUE_GREY).hAlignment(HorizontalAlignment.CENTER).build();
        return style;
    }

    private static XSSFCellStyle getSecondaryStyle(XSSFWorkbook xssfWorkbook){
        XSSFCellStyle style = null;
        XSSFFont font = XSSFFactoryUtil.createFont(xssfWorkbook, XSSFFactoryUtil.FONT_TAHOMA, 10, XSSFFactoryUtil.WEIGHT_NOBOLD, XSSFFactoryUtil.COLOR_BLACK);
        style = XSSFFactoryUtil.buildCellStyle(xssfWorkbook).font(font).build();
        return style;
    }

    private static XSSFCellStyle getSecondaryStyleRightAlign(XSSFWorkbook xssfWorkbook){
        XSSFCellStyle style = null;
        XSSFFont font = XSSFFactoryUtil.createFont(xssfWorkbook, XSSFFactoryUtil.FONT_TAHOMA, 10, XSSFFactoryUtil.WEIGHT_NOBOLD, XSSFFactoryUtil.COLOR_BLACK);
        style = XSSFFactoryUtil.buildCellStyle(xssfWorkbook).font(font).hAlignment(HorizontalAlignment.RIGHT).build();
        return style;
    }

    private static XSSFCellStyle getDefaultTahomaTextStyle(XSSFWorkbook xssfWorkbook){
        XSSFCellStyle style = null;
        XSSFFont font = XSSFFactoryUtil.createFont(xssfWorkbook, XSSFFactoryUtil.FONT_ARIAL, 10, XSSFFactoryUtil.WEIGHT_NOBOLD, XSSFFactoryUtil.COLOR_BLACK);
        style = XSSFFactoryUtil.buildCellStyle(xssfWorkbook).font(font).build();
        return style;
    }

    private static XSSFCellStyle getDefaultTahomaTextStyleCurrency(XSSFWorkbook xssfWorkbook){
        XSSFCellStyle style = null;
        XSSFFont font = XSSFFactoryUtil.createFont(xssfWorkbook, XSSFFactoryUtil.FONT_ARIAL, 10, XSSFFactoryUtil.WEIGHT_NOBOLD, XSSFFactoryUtil.COLOR_BLACK);
        style = XSSFFactoryUtil.buildCellStyle(xssfWorkbook).font(font).format("[$$-409]#,##0.00").build();
        return style;
    }

    private static XSSFCellStyle getDefaultTahomaTextStyleDouble(XSSFWorkbook xssfWorkbook){
        XSSFCellStyle style = null;
        XSSFFont font = XSSFFactoryUtil.createFont(xssfWorkbook, XSSFFactoryUtil.FONT_ARIAL, 10, XSSFFactoryUtil.WEIGHT_NOBOLD, XSSFFactoryUtil.COLOR_BLACK);
        style = XSSFFactoryUtil.buildCellStyle(xssfWorkbook).font(font).format("#,##0.00").build();
        return style;
    }

    private static XSSFCellStyle getDefaultTahomaTextStyleInteger(XSSFWorkbook xssfWorkbook){
        XSSFCellStyle style = null;
        XSSFFont font = XSSFFactoryUtil.createFont(xssfWorkbook, XSSFFactoryUtil.FONT_ARIAL, 10, XSSFFactoryUtil.WEIGHT_NOBOLD, XSSFFactoryUtil.COLOR_BLACK);
        style =  XSSFFactoryUtil.buildCellStyle(xssfWorkbook).font(font).format("#,##").build();
        return style;
    }

    private static XSSFCellStyle getDefaultTahomaTextStyleBorderLeftRight(XSSFWorkbook xssfWorkbook){
        XSSFCellStyle style = null;
        XSSFFont font = XSSFFactoryUtil.createFont(xssfWorkbook, XSSFFactoryUtil.FONT_ARIAL, 10, XSSFFactoryUtil.WEIGHT_NOBOLD, XSSFFactoryUtil.COLOR_BLACK);
        style = XSSFFactoryUtil.buildCellStyle(xssfWorkbook).font(font).border(XSSFFactoryUtil.COLOR_BLACK, BorderStyle.THIN, true, true, false, false).build();
        return style;
    }

    private static XSSFCellStyle getDefaultTahomaTextStyleBorderCustom(XSSFWorkbook xssfWorkbook, boolean left, boolean right, boolean top, boolean bottom){
        XSSFCellStyle style = null;
        XSSFFont font = XSSFFactoryUtil.createFont(xssfWorkbook, XSSFFactoryUtil.FONT_ARIAL, 10, XSSFFactoryUtil.WEIGHT_NOBOLD, XSSFFactoryUtil.COLOR_BLACK);
        style = XSSFFactoryUtil.buildCellStyle(xssfWorkbook).font(font)
                .bgColor(XSSFFactoryUtil.COLOR_BLUE_GREY)
                .border(XSSFFactoryUtil.COLOR_BLACK, BorderStyle.THICK, left, right, top, bottom).build();
        return style;
    }

    private static XSSFCellStyle getDefaultTextStyle(XSSFWorkbook xssfWorkbook){
        XSSFCellStyle style = null;
        XSSFFont font = XSSFFactoryUtil.createFont(xssfWorkbook, XSSFFactoryUtil.FONT_ARIAL, 8, XSSFFactoryUtil.WEIGHT_NOBOLD, XSSFFactoryUtil.COLOR_BLACK);
        style = XSSFFactoryUtil.buildCellStyle(xssfWorkbook).font(font).build();
        return style;
    }

    private static XSSFCellStyle getDefaultTextStyleBold(XSSFWorkbook xssfWorkbook){
        XSSFCellStyle style = null;
        XSSFFont font = XSSFFactoryUtil.createFont(xssfWorkbook, XSSFFactoryUtil.FONT_ARIAL, 8, XSSFFactoryUtil.WEIGHT_BOLD, XSSFFactoryUtil.COLOR_BLACK);
        style =  XSSFFactoryUtil.buildCellStyle(xssfWorkbook).font(font).build();
        return style;
    }

    private static XSSFCellStyle getHyperLinkStyle(XSSFWorkbook xssfWorkbook){
        XSSFCellStyle style = null;
        XSSFFont font = XSSFFactoryUtil.createFont(xssfWorkbook, XSSFFactoryUtil.FONT_TAHOMA, 10, XSSFFactoryUtil.WEIGHT_BOLD, XSSFFactoryUtil.COLOR_BLUE);
        font.setUnderline(FontUnderline.SINGLE);
        style = XSSFFactoryUtil.buildCellStyle(xssfWorkbook).font(font).build();
        return style;
    }

}