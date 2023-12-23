package com.java.springboot.controllers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/xpath/")
@Slf4j
public class XpathController {

    private static final String XSLTPATH = "src/main/resources/xpath/";

    @GetMapping("transform/tutorials")
    public Map<String, Object> transformInputToXSLT() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        Map<String, Object> response = new LinkedHashMap<>();
        try(FileInputStream fileIS = new FileInputStream(XSLTPATH+"Tutorials.xml")) {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(fileIS);
            XPath xPath = XPathFactory.newInstance().newXPath();

            String tutorialId02 = "/Tutorials/Tutorial[@tutId='02']/title";
            String title02 = (String)xPath.compile(tutorialId02).evaluate(xmlDocument, XPathConstants.STRING);
            response.put(tutorialId02,title02);

            String countExpression = "count(/Tutorials/Tutorial)";
            Double doubleCount = (Double)(xPath.compile(countExpression).evaluate(xmlDocument, XPathConstants.NUMBER));
            int count = doubleCount.intValue();
            response.put("TutorialCount:",count);

            for (int i = 0; i < count; i++) {
                String expression = "/Tutorials/Tutorial[" + (i+1) + "]/title";
                String expressionDate = "/Tutorials/Tutorial["+(i+1)+"]/date";
                String title = (String)xPath.compile(expression).evaluate(xmlDocument, XPathConstants.STRING);
                String date = (String)xPath.compile(expressionDate).evaluate(xmlDocument, XPathConstants.STRING);
                response.put(expression,title);
                response.put(expressionDate,date);
            }
        }
        return response;
    }

}
