package com.java.springboot.controllers;


import com.java.springboot.entities.User;
import com.java.springboot.service.UserService;
import com.java.springboot.util.WriteSampleXSSFExcel;
import com.java.springboot.util.XSSFFactoryUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/excel/")
@Slf4j
public class ExcelController {

    private static String excelFolderPath = "src/main/resources/excel/";

    @Autowired
    UserService userService;

    @GetMapping("file/sample")
    public void  saveSampleExcelSheet(){
        WriteSampleXSSFExcel.generateSampleXSSFExcel(excelFolderPath+"Sample.xlsx");
    }

    @GetMapping("download/sample")
    public void  downloadSampleExcelSheet(HttpServletResponse response) throws IOException {

        // set response headers
        response.setContentType("application/octet-stream");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + "Sample.xlsx" + "\"");

        XSSFWorkbook wb = WriteSampleXSSFExcel.generateSampleXSSFExcel(new XSSFWorkbook());
        wb.write(response.getOutputStream());
        wb.close();
    }

    @Operation(summary = "It will read user data from the generated excel sheet and display")
    @GetMapping("read/users")
    public ResponseEntity<List<Map<String,String>>> readUsersFromExcelFolder() throws IOException {
        String filePath = excelFolderPath + "Users.xlsx";
        FileInputStream fis = new FileInputStream(filePath);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        Sheet sheet = wb.getSheetAt(0);
        int rowN = 2;
        int colN = 2;
        Iterator<Row> rows = sheet.iterator();
        for (int i = 0; i < rowN; i++) {
            rows.next();
        }

        List<Map<String,String>> response = new ArrayList<>();
        // to reach the data row
        while(rows.hasNext()){
            Map<String,String> values = new LinkedHashMap<>();
            int colLocal = colN;
            Row row = rows.next();
            Iterator<Cell> cells = row.iterator();
            values.put("Name", row.getCell(colLocal++).getStringCellValue());
            values.put("UserName", row.getCell(colLocal++).getStringCellValue());
            values.put("City", row.getCell(colLocal++).getStringCellValue());
            values.put("DateOfJoining", row.getCell(colLocal++).getStringCellValue());
            values.put("ExactDOJ", row.getCell(colLocal++).getStringCellValue());
            values.put("Description", row.getCell(colLocal++).getStringCellValue());
            values.put("Status", row.getCell(colLocal++).getNumericCellValue()>0 ? "active" : "inactive");
            response.add(values);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("download/users")
    public void  downloadUsersToExcelFolder(HttpServletResponse response){
        List<User> users = userService.getUsers();
        String[] header = new String[]{"NAME","USERNAME","CITY","DESCRIPTION","DATEOFJOINING","EXACTDOB","STATUS"};

        // set response headers
        response.setContentType("application/octet-stream");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + "Users.xlsx" + "\"");

        XSSFWorkbook wb = createUserExcelSheet(new XSSFWorkbook(), header, users, "Users");
        try {
            wb.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("file/users")
    public void  saveUsersToExcelFolder(){
        List<User> users = userService.getUsers();
        String[] header = new String[]{"NAME","USERNAME","CITY","DESCRIPTION","DATEOFJOINING","EXACTDOB","STATUS"};

        XSSFWorkbook wb = createUserExcelSheet(new XSSFWorkbook(), header, users, "Users");
        try {
            FileOutputStream fos = new FileOutputStream(excelFolderPath+"Users.xlsx");
            wb.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private XSSFWorkbook createUserExcelSheet(XSSFWorkbook wb, String[] header, List<User> users, String sheetName){
        XSSFSheet sheet = XSSFFactoryUtil.createSheet(wb, sheetName, 0);
        XSSFCellStyle primaryHeaderStyle = getPrimaryHeaderStyle(wb);

        int row = 2;
        int startCol = 2;
        for (int i = 0; i < header.length; i++) {
            XSSFFactoryUtil.buildCell(sheet, i+startCol, row)
                    .value(header[i])
                    .style(primaryHeaderStyle)
                    .build();
        }

        row++;
        for (User u : users){
            int col = startCol;
            XSSFFactoryUtil.buildCell(sheet, col++, row).value(u.getName()).build();
            XSSFFactoryUtil.buildCell(sheet, col++, row).value(u.getUserName()).build();
            XSSFFactoryUtil.buildCell(sheet, col++, row).value(u.getCity()).build();
            XSSFFactoryUtil.buildCell(sheet, col++, row).value(u.getDescription()).build();
            XSSFFactoryUtil.buildCell(sheet, col++, row).value(u.getDateOfJoining().toString()).build();
            XSSFFactoryUtil.buildCell(sheet, col++, row).value(u.getExactDob().toString()).build();
            XSSFFactoryUtil.buildCell(sheet, col++, row).value(u.getStatus()).build();
            row++;
        }

        int finalCol = startCol + header.length;
        for (int i = startCol; i < finalCol; i++) {
            sheet.autoSizeColumn(i);
        }
        return wb;
    }

    private static XSSFCellStyle getPrimaryHeaderStyle(XSSFWorkbook xssfWorkbook){
        XSSFCellStyle style = null;
        XSSFFont font = XSSFFactoryUtil.createFont(xssfWorkbook, XSSFFactoryUtil.FONT_TAHOMA, 10, XSSFFactoryUtil.WEIGHT_BOLD, XSSFFactoryUtil.COLOR_GREY_25);
        style = XSSFFactoryUtil.buildCellStyle(xssfWorkbook).font(font).bgColor(XSSFFactoryUtil.COLOR_BLUE_GREY).hAlignment(HorizontalAlignment.CENTER).build();
        return style;
    }

}
