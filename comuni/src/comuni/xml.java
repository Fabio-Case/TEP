/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comuni;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Fabio
 */
public class xml {
    public void validate(String XMLdocument, String XSDschema) throws SAXException, IOException {
        // creazione di uno schema XSD a partire dal file
        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        File schemaFile = new File(XSDschema);
        Schema schema = factory.newSchema(schemaFile);
        // creazione di un validatore rispetto allo schema XSD
        Validator validator = schema.newValidator();
        // validazione del documento XML
        Source source = new StreamSource(XMLdocument);
        validator.validate(source);
    }

    public List parseDocument(String filename)
            throws ParserConfigurationException, SAXException, IOException, ParseException {
        List<comune> comuni = new ArrayList();
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document document;
        Element root, element;
        NodeList nodelist;
        comune comune;
        // creazione dell’albero DOM dal documento XML
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        document = builder.parse(filename);
        root = document.getDocumentElement();
        // generazione della lista degli elementi "comune"
        nodelist = root.getElementsByTagName("comune");
        if (nodelist != null && nodelist.getLength() > 0) {
            for (int i = 0; i < nodelist.getLength(); i++) {
                element = (Element) nodelist.item(i);
                comune = getcomune(element);
                comuni.add(comune);
            }
        }
        return comuni;
    }

    private comune getcomune(Element element) throws ParseException {
        comune comune;
        String nome = getTextValue(element, "campo", "nome");
        double latitudine = getFloatValue(element, "campo", "latitudine");
        double longitudine = getFloatValue(element, "campo", "longitudine");
        comune = new comune(nome, latitudine, longitudine);
        return comune;
    }

    // restituisce il valore testuale dell’elemento figlio specificato
    private String getTextValue(Element element, String tag, String attr) {
        Element value = null;
        NodeList nodelist;
        Node node;
        nodelist = element.getElementsByTagName(tag);
        String values = "";
        if (nodelist != null && nodelist.getLength() > 0) {
            for(int i = 0; i< nodelist.getLength(); i++){
                value = (Element) nodelist.item(i);
                if(value.getAttribute("nome").equals(attr))
                    values = value.getTextContent();
            }
        }
        return values;
    }

    

    // restituisce il valore numerico dell’elemento figlio specificato
    private double getFloatValue(Element element, String tag, String att) {
        String temp = getTextValue(element, tag, att);
        if(temp != "")
            return Double.parseDouble(temp);
        else
            return -1;
    }
}
