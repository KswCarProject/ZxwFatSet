package com.szchoiceway.fatset.util;

import android.util.Log;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class KSW_UpdateFactory_XmlDocument_Demo {
    private static final String TAG = "XmlDocument_Demo";
    private Document document;
    private String fileName;

    public void init() {
        try {
            this.document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createXml(String str) {
        Element createElement = this.document.createElement("employees");
        this.document.appendChild(createElement);
        Element createElement2 = this.document.createElement("employee");
        Element createElement3 = this.document.createElement("name");
        createElement3.appendChild(this.document.createTextNode("丁宏亮"));
        createElement2.appendChild(createElement3);
        Element createElement4 = this.document.createElement("sex");
        createElement4.appendChild(this.document.createTextNode("m"));
        createElement2.appendChild(createElement4);
        Element createElement5 = this.document.createElement("age");
        createElement5.appendChild(this.document.createTextNode("30"));
        createElement2.appendChild(createElement5);
        createElement.appendChild(createElement2);
        try {
            Transformer newTransformer = TransformerFactory.newInstance().newTransformer();
            DOMSource dOMSource = new DOMSource(this.document);
            newTransformer.setOutputProperty("encoding", "gb2312");
            newTransformer.setOutputProperty("indent", "yes");
            newTransformer.transform(dOMSource, new StreamResult(new PrintWriter(new FileOutputStream(str))));
            System.out.println("生成XML文件成功!");
        } catch (TransformerConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e2) {
            System.out.println(e2.getMessage());
        } catch (FileNotFoundException e3) {
            System.out.println(e3.getMessage());
        } catch (TransformerException e4) {
            System.out.println(e4.getMessage());
        }
    }

    public Map<String, String> parserXml(String str) {
        try {
            HashMap hashMap = new HashMap();
            DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Log.i(TAG, "--->>>XML: 222");
            FileInputStream fileInputStream = new FileInputStream(str);
            Log.i(TAG, "--->>>XML: 333");
            Document parse = newDocumentBuilder.parse(fileInputStream);
            Log.i(TAG, "--->>>XML: 444");
            NodeList childNodes = parse.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                NodeList childNodes2 = childNodes.item(i).getChildNodes();
                for (int i2 = 0; i2 < childNodes2.getLength(); i2++) {
                    NodeList childNodes3 = childNodes2.item(i2).getChildNodes();
                    for (int i3 = 0; i3 < childNodes3.getLength(); i3++) {
                        System.out.println("--->>>XML: " + childNodes3.item(i3).getNodeName() + ":" + childNodes3.item(i3).getTextContent());
                        if (!"#comment".equals(childNodes3.item(i3).getNodeName())) {
                            hashMap.put(childNodes3.item(i3).getNodeName(), childNodes3.item(i3).getTextContent());
                        }
                    }
                }
            }
            System.out.println("--->>>XML: 解析完毕");
            return hashMap;
        } catch (FileNotFoundException e) {
            Log.e(TAG, "--->>>XML: " + e.getMessage());
            return null;
        } catch (ParserConfigurationException e2) {
            Log.e(TAG, "--->>>XML: " + e2.getMessage());
            return null;
        } catch (SAXException e3) {
            Log.e(TAG, "--->>>XML: " + e3.getMessage());
            return null;
        } catch (IOException e4) {
            Log.e(TAG, "--->>>XML: " + e4.getMessage());
            return null;
        }
    }
}
