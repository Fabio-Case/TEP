/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esxml2localozzatore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Fabio
 */
public class OperazioniXML {
    
    private Document documentT, documentC, documentCom;

    public Document getDocumentC() {
        return documentC;
    }
    
    public Document getDocumentCom() {
        return documentCom;
    }
    
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

    public List parseDocument(String filename, int tipo)
            throws ParserConfigurationException, SAXException, IOException {
        
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Element root, element;
        NodeList nodelist;
        // creazione dell’albero DOM dal documento XML
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        
        // generazione della lista degli elementi "Veicolo"
        if(tipo == 1){
            documentT = builder.parse(filename);
            root = documentT.getDocumentElement();
            List<DatiGeo> dati = new ArrayList();
            DatiGeo dato;
            nodelist = root.getElementsByTagName("comune"); //non la root baka
            if (nodelist != null && nodelist.getLength() > 0) {
                for (int i = 0; i < nodelist.getLength(); i++) {
                    element = (Element) nodelist.item(i);
                    dato = getDatoTot(element);
                    dati.add(dato);
                }
            }
            return dati;
        }
        else if(tipo == 2){
            List<cache> dati = new ArrayList();
            cache dato;
            documentC = builder.parse(filename);
            root = documentC.getDocumentElement();
            nodelist = root.getElementsByTagName("ricerca"); //non la root baka
            if (nodelist != null && nodelist.getLength() > 0) {
                for (int i = 0; i < nodelist.getLength(); i++) {
                    element = (Element) nodelist.item(i);
                    dato = getDatoCache(element);
                    dati.add(dato);
                }
            }
            return dati;
        } else {
            List<comuniVicini> dati = new ArrayList();
            comuniVicini dato;
            documentCom = builder.parse(filename);
            root = documentCom.getDocumentElement();
            nodelist = root.getElementsByTagName("richiesta"); //non la root baka
            if (nodelist != null && nodelist.getLength() > 0) {
                for (int i = 0; i < nodelist.getLength(); i++) {
                    element = (Element) nodelist.item(i);
                    dato = getDatoComVic(element);
                    dati.add(dato);
                }
            }
            return dati;
        }
    }
    
    
    private DatiGeo getDatoTot(Element element) {
        
        DatiGeo comune = new DatiGeo();
        
        NodeList nodelist = element.getElementsByTagName("campo");
        for (int i = 0; i < nodelist.getLength(); i++) {
            
            Element current = (Element)nodelist.item(i);
            
            if (current.getAttribute("nome").equals("id"))
                comune.setId(current.getTextContent());
            if (current.getAttribute("nome").equals("id_regione"))
                comune.setId_regione(current.getTextContent());
            if (current.getAttribute("nome").equals("id_provincia"))
                comune.setId_provincia(current.getTextContent());
            if (current.getAttribute("nome").equals("nome"))
                comune.setNome(current.getTextContent());
            if (current.getAttribute("nome").equals("capoluogo_provincia"))
                comune.setCapoluogo_provincia(current.getTextContent());
            if (current.getAttribute("nome").equals("codice_catastale"))
                comune.setCodice_catastale(current.getTextContent());
            if (current.getAttribute("nome").equals("latitudine"))
                comune.setLatitudine(Float.parseFloat(current.getTextContent()));
            if (current.getAttribute("nome").equals("longitudine"))
                comune.setLongitudine(Float.parseFloat(current.getTextContent()));
        }
        
        return comune;
    }
    
    private cache getDatoCache(Element element) {
        
        cache cache = new cache();
        
        NodeList nodelist = element.getElementsByTagName("partenza");
        Element current = (Element)nodelist.item(0);
        cache.setComuneArr(current.getTextContent());
        nodelist = element.getElementsByTagName("arrivo");
        current = (Element)nodelist.item(0);
        cache.setComunePart(current.getTextContent());
        nodelist = element.getElementsByTagName("distanza");
        current = (Element)nodelist.item(0);
        cache.setDistanza(Double.parseDouble(current.getTextContent()));
        
        
        return cache;
    }
    
    private comuniVicini getDatoComVic(Element element){
        comuniVicini com = new comuniVicini();
        List<String> comuni = new ArrayList<>();
        NodeList nodelist = element.getElementsByTagName("comuneRichiedente");
        Element current = (Element)nodelist.item(0);
        com.setComune(current.getTextContent());
        nodelist = element.getElementsByTagName("comune");
        for(int i = 0; i < nodelist.getLength(); i++){
            current = (Element)nodelist.item(i);
            comuni.add(current.getTextContent());
        }
        com.setComuni(comuni);
        return com;
    }

    // restituisce il valore testuale dell’elemento figlio specificato
    private String getTextValue(Element element, String tag) {
        String value = null;
        NodeList nodelist;
        nodelist = element.getElementsByTagName(tag);
        if (nodelist != null && nodelist.getLength() > 0) {
            value = nodelist.item(0).getFirstChild().getNodeValue();
        }
        return value;
    }

    // restituisce il valore intero dell’elemento figlio specificato
    private int getIntValue(Element element, String tag) {
        return Integer.parseInt(getTextValue(element, tag));
    }

    // restituisce il valore numerico dell’elemento figlio specificato
    private float getFloatValue(Element element, String tag) {
        return Float.parseFloat(getTextValue(element, tag));
    }

     // restituisce il valore numerico dell’elemento figlio specificato
    private double getDoubleValue(Element element, String tag) {
        return Double.parseDouble(getTextValue(element, tag));
    }
}
