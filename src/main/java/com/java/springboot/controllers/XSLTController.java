package com.java.springboot.controllers;


import com.java.springboot.entities.xmllist.Users;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/xslt/")
@Slf4j
public class XSLTController {

    private final static String xsltPath = "src/main/resources/xslt/";

    @GetMapping("transform/data")
    public String transformInputToXSLT() throws TransformerException {
        Source xmlSource = new StreamSource(xsltPath+"data.xml");
        Source xsltSource = new StreamSource(xsltPath+"stylesheet.xslt");

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(xsltSource);

        StreamResult result = new StreamResult(xsltPath+"output.html");
        transformer.transform(xmlSource, result);
        return "XSLT transformation completed successfully.";
    }

    @GetMapping("transform/users")
    public String transformUsersInputToXSLT() throws TransformerException {
        Source xmlSource = new StreamSource(xsltPath+"Users.xml");
        Source xsltSource = new StreamSource(xsltPath+"user-stylesheet.xslt");

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(xsltSource);

        StreamResult result = new StreamResult(xsltPath+"Users.html");
        transformer.transform(xmlSource, result);
        return "XSLT transformation completed successfully.";
    }

    @GetMapping("transform/download/users")
    public String transformDownloadUsersInputToXSLT(HttpServletResponse response) throws TransformerException, IOException {

        // set response headers
        response.setContentType("text/html");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + "response.html" + "\"");

        Source xmlSource = new StreamSource(xsltPath+"Users.xml");
        Source xsltSource = new StreamSource(xsltPath+"user-stylesheet.xslt");

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(xsltSource);

        StreamResult result = new StreamResult(response.getOutputStream());
        transformer.transform(xmlSource, result);
        return "XSLT transformation completed successfully.";
    }

    @GetMapping("transform/download/data")
    public void transformInputToXSLT(HttpServletResponse response) throws TransformerException, IOException {

        // set response headers
        response.setContentType("text/html");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + "response.html" + "\"");

        Source xmlSource = new StreamSource(xsltPath+"data.xml");

//      This approach offers an advantage when we need to perform multiple transformations
//      using the same stylesheet, as it avoids the overhead of recompiling the stylesheet.
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Source xsltSource = new StreamSource(xsltPath+"stylesheet.xslt");

        Templates templates = transformerFactory.newTemplates(xsltSource);
        Transformer transformer = templates.newTransformer();

        StreamResult result = new StreamResult(response.getOutputStream());
        transformer.transform(xmlSource, result);
    }
}
