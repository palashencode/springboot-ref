package com.java.springboot.util;

import javax.net.ssl.SSLEngineResult.HandshakeStatus;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.formula.DataValidationEvaluator.ValidationEnum;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFBorderFormatting;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;

public class XSSFFactoryUtil {

    public static final short COLOR_BLACK = IndexedColors.BLACK.getIndex();
    public static final short COLOR_BLUE = IndexedColors.BLUE.getIndex();
    public static final short COLOR_BLUE_GREY = IndexedColors.BLUE_GREY.getIndex();
    public static final short COLOR_BROWN = IndexedColors.BROWN.getIndex();
    public static final short COLOR_GREY_25 = IndexedColors.GREY_25_PERCENT.getIndex();
    public static final String FONT_ARIAL = "Arial";
    public static final String FONT_TAHOMA = "Tahoma";
    public static final Boolean WEIGHT_BOLD = true;
    public static final Boolean WEIGHT_NOBOLD = false;

    public static void mergeAdapter(XSSFSheet sheet, int col1, int row1, int col2, int row2){
        sheet.addMergedRegion(new CellRangeAddress(row1,row2,col1,col2));
    }

    public static void applyMergedCellStyle(XSSFSheet sheet, int col1, int row1, int col2, int row2){

        XSSFRow row = null;
        row = sheet.getRow(row1);
        if(row == null) return;
        XSSFCell cell = row.getCell(col1);
        if(cell == null) return;

        XSSFCellStyle cellStyle = cell.getCellStyle();

        for (int i = row1; i <= row2; i++) {
            for (int j = col1; j <= col2; j++) {
                sheet.getRow(i).getCell(j).setCellStyle(cellStyle);
            }
        }

        // sheet.addMergedRegion(new CellRangeAddress(row1,row2,col1,col2));
    }

    public static void mergeCellWithStyle(XSSFSheet sheet, int col1, int row1, int col2, int row2){

        try {
            XSSFRow row = null;
            row = sheet.getRow(row1);
            if(row == null) throw new AssertionError("cannot merge null rows");
            XSSFCell cell = row.getCell(col1);
            if(cell == null) throw new AssertionError("cannot merge null cells");

            XSSFCellStyle cellStyle = cell.getCellStyle();

            CellRangeAddress mergedCell = new CellRangeAddress(row1,row2,col1,col2);
            sheet.addMergedRegion(mergedCell);

            RegionUtil.setBorderBottom(cellStyle.getBorderBottom(),mergedCell, sheet);
            RegionUtil.setBottomBorderColor(cellStyle.getBottomBorderColor(), mergedCell, sheet);

            RegionUtil.setBorderRight(cellStyle.getBorderRight(),mergedCell, sheet);
            RegionUtil.setRightBorderColor(cellStyle.getRightBorderColor(), mergedCell, sheet);

            RegionUtil.setBorderTop(cellStyle.getBorderTop(),mergedCell, sheet);
            RegionUtil.setTopBorderColor(cellStyle.getTopBorderColor(), mergedCell, sheet);

            RegionUtil.setBorderLeft(cellStyle.getBorderLeft(),mergedCell, sheet);
            RegionUtil.setLeftBorderColor(cellStyle.getLeftBorderColor(), mergedCell, sheet);

        } catch (AssertionError e) {
            //Do Nothing, optionally log an error message
            System.out.println("ERROR");
        }
    }

    public static XSSFSheet createSheet(XSSFWorkbook xssfWorkbook, String name, int order){
        if( xssfWorkbook == null ) return null;
        XSSFSheet sheet = xssfWorkbook.createSheet(name);
        xssfWorkbook.setSheetOrder(name, order);
        return sheet;
    }

    public static XSSFontBuilder createFontBuilder(XSSFWorkbook xssfWorkbook,String fontName){
        return new XSSFontBuilder(xssfWorkbook,fontName);
    }

    public static class XSSFontBuilder{
        private XSSFWorkbook xssfWorkbook;
        private String fontName;
        private int heightInPoints;
        private boolean isBold;
        private short color;

        XSSFontBuilder(XSSFWorkbook xssfWorkbook,String fontName){
            this.xssfWorkbook = xssfWorkbook;
            this.fontName = fontName;
        }

        public XSSFontBuilder heightInPoints(int heightInPoints){
            this.heightInPoints = heightInPoints;
            return this;
        }

        public XSSFontBuilder bold(boolean isBold){
            this.isBold = isBold;
            return this;
        }

        public XSSFontBuilder color(short color){
            this.color = color;
            return this;
        }

        public XSSFFont build(){
            XSSFFont font = xssfWorkbook.createFont();
            font.setColor(color);
            font.setBold(isBold);
            font.setFontName(fontName);
            if(heightInPoints != 0){
                font.setFontHeightInPoints((short)heightInPoints);
            }
            return font;
        }
    }

    public static XSSFFont createFont(XSSFWorkbook xssfWorkbook, String fontName, int heightInPoints, boolean isBold, short color){
        XSSFFont font = xssfWorkbook.createFont();
        font.setColor(color);
        font.setBold(isBold);
        font.setFontName(fontName);
        font.setFontHeightInPoints((short)heightInPoints);
        return font;
    }

    public static XSSFCellBuilder buildCell(XSSFSheet xssfSheet, int colNo, int rowNo){
        return new XSSFCellBuilder(xssfSheet,  colNo,  rowNo);
    }

    public static class XSSFCellBuilder{
        private XSSFSheet xssfSheet;
        private int colNo;
        private int rowNo;
        private XSSFCellStyle xssfCellStyle;

        private boolean isValueSet = false;
        private Integer intValue = null;
        private Double doubleValue = null;
        private String stringValue = null;

        private String url = null;

        private XSSFCellBuilder(XSSFSheet xssfSheet, int colNo, int rowNo){
            this.xssfSheet = xssfSheet;
            this.colNo = colNo;
            this.rowNo = rowNo;
        }

        public XSSFCellBuilder value(Integer val){
            isValueSet = true;
            this.intValue = val;
            return this;
        }

        public XSSFCellBuilder url(String url){
            this.url = url;
            return this;
        }

        public XSSFCellBuilder value(String val){
            isValueSet = true;
            this.stringValue = val;
            return this;
        }
        public XSSFCellBuilder value(Double val){
            isValueSet = true;
            this.doubleValue = val;
            return this;
        }

        public XSSFCellBuilder style(XSSFCellStyle xssfCellStyle){
            this.xssfCellStyle = xssfCellStyle;
            return this;
        }

        public XSSFCell build(){
            if(this.xssfSheet == null) return null;


            XSSFRow row = this.xssfSheet.getRow(this.rowNo);
            if(row == null){
                row = this.xssfSheet.createRow(this.rowNo);
            }
            XSSFCell cell = row.getCell(this.colNo);
            if(cell == null){
                cell = row.createCell(this.colNo);
            }

            if(this.xssfCellStyle != null){
                cell.setCellStyle(this.xssfCellStyle);
            }

            if(this.isValueSet){
                if(this.intValue != null){
                    cell.setCellValue(intValue);
                }else if(this.stringValue != null){
                    cell.setCellValue(this.stringValue);
                }else if(this.doubleValue != null){
                    cell.setCellValue(this.doubleValue);
                }
            }

            if(this.url != null){
                CreationHelper creationHelper = this.xssfSheet.getWorkbook().getCreationHelper();
                Hyperlink link = creationHelper.createHyperlink(HyperlinkType.URL);
                link.setAddress(url);
                cell.setHyperlink(link);
            }

            return cell;
        }

    }

    public static XSSFCell addLinkToCell(XSSFWorkbook xssfWorkbook, XSSFCell cell, String url){
        CreationHelper creationHelper = xssfWorkbook.getCreationHelper();
        Hyperlink link = creationHelper.createHyperlink(HyperlinkType.URL);
        link.setAddress(url);
        cell.setHyperlink(link);
        return cell;
    }

    public static class XSSFCellStyleBuilder{
        private XSSFWorkbook xssfWorkbook;
        private XSSFFont xssfFont;
        private String customDataFormat;

        private HorizontalAlignment hAlignment = HorizontalAlignment.GENERAL;
        private VerticalAlignment vAlignment = VerticalAlignment.BOTTOM;

        private Short bgColorIndex;

        private boolean left;
        private boolean right;
        private boolean top;
        private boolean bottom;
        private BorderStyle borderStyle;
        private Short cellBorderColorIndex;

        private boolean isBorderSet;

        private XSSFCellStyleBuilder(XSSFWorkbook xssfWorkbook){
            this.xssfWorkbook = xssfWorkbook;
        }

        public XSSFCellStyleBuilder font(XSSFFont xssfFont){
            this.xssfFont = xssfFont;
            return this;
        }

        public XSSFCellStyleBuilder format(String format){
            this.customDataFormat = format;
            return this;
        }

        public XSSFCellStyleBuilder hAlignment(HorizontalAlignment hAlignment){
            this.hAlignment = hAlignment;
            return this;
        }

        public XSSFCellStyleBuilder bgColor(Short colorIndex){
            this.bgColorIndex = colorIndex;
            return this;
        }

        public XSSFCellStyleBuilder vAlignment(VerticalAlignment vAlignment){
            this.vAlignment = vAlignment;
            return this;
        }

        public XSSFCellStyleBuilder border(short colorIndex, BorderStyle borderStyle, boolean left, boolean right, boolean top, boolean bottom){
            this.isBorderSet = true;

            this.cellBorderColorIndex = colorIndex;
            this.borderStyle = borderStyle;
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
            return this;
        }

        public XSSFCellStyle build(){
            if(xssfWorkbook == null ) return null;
            XSSFCellStyle xssfCellStyle = xssfWorkbook.createCellStyle();

            if(xssfFont != null){
                xssfCellStyle.setFont(xssfFont);
            }

            xssfCellStyle.setAlignment(hAlignment);
            xssfCellStyle.setVerticalAlignment(vAlignment);

            if(bgColorIndex != null){
                setBackGroundColor(xssfCellStyle,bgColorIndex);
            }

            if(customDataFormat != null){
                xssfCellStyle.setDataFormat(xssfWorkbook.createDataFormat().getFormat(customDataFormat));
            }

            if(isBorderSet){
                XSSFFactoryUtil.setBorderStyle(xssfCellStyle, cellBorderColorIndex, borderStyle, left, right, top, bottom);
            }

            return xssfCellStyle;
        }

    }

    public static void setBorderStyle(XSSFCellStyle xssfCellStyle,short colorIndex, BorderStyle borderStyle, boolean left, boolean right, boolean top, boolean bottom){
        if(left){
            xssfCellStyle.setLeftBorderColor(colorIndex);
            xssfCellStyle.setBorderLeft(borderStyle);
        }
        if(right){
            xssfCellStyle.setRightBorderColor(colorIndex);
            xssfCellStyle.setBorderRight(borderStyle);
        }
        if(top){
            xssfCellStyle.setTopBorderColor(colorIndex);
            xssfCellStyle.setBorderTop(borderStyle);
        }
        if(bottom){
            xssfCellStyle.setBottomBorderColor(colorIndex);
            xssfCellStyle.setBorderBottom(borderStyle);
        }
    }

    public static XSSFCellStyleBuilder buildCellStyle(XSSFWorkbook xssfWorkbook){
        return new XSSFCellStyleBuilder(xssfWorkbook);
    }


    public static XSSFCellStyle setCellStyleHorizontalAlignment(XSSFCellStyle style, HorizontalAlignment hAlignment){
        style.setAlignment(hAlignment);
        return style;
    }

    public static XSSFCellStyle setCellStyleColor(XSSFCellStyle style, short colorIndex){
        setBackGroundColor(style,colorIndex);
        return style;
    }


    private static void setBackGroundColor(XSSFCellStyle style, int colorIndex){
        style.setFillForegroundColor((short)colorIndex);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }

    public static void newBorderStyle(XSSFWorkbook workbook, XSSFCellStyle oldCellStyle,short colorIndex, BorderStyle borderStyle, boolean left, boolean right, boolean top, boolean bottom){
        XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
        xssfCellStyle.cloneStyleFrom(oldCellStyle);

        if(left){
            xssfCellStyle.setLeftBorderColor(colorIndex);
            xssfCellStyle.setBorderLeft(borderStyle);
        }
        if(right){
            xssfCellStyle.setRightBorderColor(colorIndex);
            xssfCellStyle.setBorderRight(borderStyle);
        }
        if(top){
            xssfCellStyle.setTopBorderColor(colorIndex);
            xssfCellStyle.setBorderTop(borderStyle);
        }
        if(bottom){
            xssfCellStyle.setBottomBorderColor(colorIndex);
            xssfCellStyle.setBorderBottom(borderStyle);
        }
    }

}