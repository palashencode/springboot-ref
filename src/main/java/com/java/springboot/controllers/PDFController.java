package com.java.springboot.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.java.springboot.entities.User;
import com.java.springboot.service.CountryService;
import com.java.springboot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/api/v1/pdf/")
@Slf4j
public class PDFController {

    private final static String pdfPath = "src/main/resources/pdf/";
    private final static String imgPath = "src/main/resources/static/img/";
    private final static String fontPath = "src/main/resources/fonts/";
    Set<String> fontSet = Set.of("NotoSansCJKtc-Regular.ttf","NotoSansBengali-Regular.ttf");
    String defaultFontName = "NotoSansCJKtc-Regular.ttf";

    @Autowired
    CountryService countryService;

    @Autowired
    UserService userService;

    /**
     *  Creating a PDF is like painting a canvas with x,y starting from left lower co-ordinates.
     *  PDFBox is not as savvy as iText, but iText needs a paid license for commercial use.
     * @throws IOException
     * @throws URISyntaxException
     */

    @GetMapping("file/users")
    public void  saveUsersToPDFFolder() throws IOException, URISyntaxException {
        List<User> users = userService.getUsers();
        PDDocument document = generatePDFDocumentForUsers(new PDDocument(), "coffee.jpg", users);
        document.save(pdfPath+"Users.pdf");
        document.close();
    }

    @GetMapping("download/users")
    public void  downloadUsersToPDFFolder(HttpServletResponse response) throws IOException, URISyntaxException {
        List<User> users = userService.getUsers();
        PDDocument document = generatePDFDocumentForUsers(new PDDocument(), "coffee.jpg", users);
        response.addHeader("Content-Type", "application/force-download");
        response.addHeader("Content-Disposition", "attachment; filename=\"Users.pdf\"");
        document.save(response.getOutputStream());
        document.close();
    }

    @Operation(summary = "Used to generate a password protected file, passwords : ownerpass userpass ")
    @GetMapping("download/password/users")
    public void  downloadPasswordProtectedPDF(HttpServletResponse response) throws IOException, URISyntaxException {
        List<User> users = userService.getUsers();
        PDDocument document = generatePDFDocumentForUsers(new PDDocument(), "coffee.jpg", users);

        AccessPermission accessPermission = new AccessPermission();
        accessPermission.setCanPrint(false);
        accessPermission.setCanModify(false);

        StandardProtectionPolicy standardProtectionPolicy
                = new StandardProtectionPolicy("ownerpass", "userpass", accessPermission);
        document.protect(standardProtectionPolicy);

        response.addHeader("Content-Type", "application/force-download");
        response.addHeader("Content-Disposition", "attachment; filename=\"Users.pdf\"");
        document.save(response.getOutputStream());
        document.close();
    }

    private PDDocument generatePDFDocumentForUsers(PDDocument document, String imgFileName, List<User> users) throws IOException {
        PDPage page = new PDPage();
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        float margin = 10;
        PDRectangle visible = page.getCropBox();
        float w = visible.getWidth();
        float h = visible.getHeight();

        float lowerLeftX = visible.getLowerLeftX();
        float lowerLeftY = visible.getLowerLeftY();
        float upperRightX = visible.getUpperRightX();
        float upperRightY = visible.getUpperRightY();

        Path path = Paths.get(imgPath+imgFileName);
        PDImageXObject coffeeImg
                = PDImageXObject.createFromFile(path.toAbsolutePath().toString(), document);

        int imgH = coffeeImg.getHeight();
        int imgW = coffeeImg.getWidth();

        int imageStartX = (int)(w-imgW)/2;
        int imageStartY = (int)(h-margin-imgH);
        contentStream.drawImage(coffeeImg, imageStartX, imageStartY);

        log.info("width {}, height {}", w, h);
        log.info("lowerleftX {}, lowerLeftY {}, upperRightX {}, upperRightY {}", lowerLeftX, lowerLeftY, upperRightX, upperRightY);

        float startX = margin;
        float startY = imageStartY-(2*margin);

        contentStream.setFont(PDType0Font.load(document,  new FileInputStream(new File(fontPath+defaultFontName)), true), 12);
        contentStream.beginText();
        contentStream.setLeading(14.5f);
        contentStream.newLineAtOffset(startX,startY);
        contentStream.showText("Displaying the user table here : ");
        for(User u : users){
            contentStream.newLine();
            String line = "Name :"+u.getName()
                    +", Username :"+u.getUserName()
                    +", City :"+u.getCity()
                    +", Desc :"+u.getDescription()
                    +", DOJ :"+u.getDateOfJoining()
                    + ", DOB :"+u.getExactDob();
            showText(document, contentStream, line, 12);
        }
        contentStream.endText();
        contentStream.close();

        return document;
    }

    private void showText(PDDocument doc, PDPageContentStream contentStream, String line, int fontSize) throws IOException {
        Set<String> triedFonts = new TreeSet<>();
        List<String> fontList = fontSet.stream().toList();
        int len = fontList.size();
        boolean reTry = true;
        int i = 0;
        while(reTry && (i < len)) {
            try {
                contentStream.showText(line);
                reTry = false;
            } catch (IllegalStateException e) {
                String font = fontList.get(i);
//                triedFonts.add(font);
                contentStream.setFont(PDType0Font.load(doc, new FileInputStream(new File(fontPath + font)), true), fontSize);
                log.info("trying font :"+font+", i="+i);
                i++;
//                reTry = triedFonts.size() >= fontList.size();
            }
        }
    }

    private void loadFonts(PDDocument doc, PDPageContentStream contentStream, int fontSize) throws IOException {
        // Setting one of the basic 14 available fonts
//        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.COURIER), fontSize);
//        InputStream notoSansRegularBoldResource = PDFController.class.getResourceAsStream("NotoSansCJKtc-Bold.ttf");
//        InputStream notoSansRegularResource = PDFController.class.getResourceAsStream("NotoSansCJKtc-Regular.ttf");

        contentStream.setFont(PDType0Font.load(doc,  new File(fontPath+"NotoSansCJKtc-Regular.ttf")), fontSize);
    }

}
