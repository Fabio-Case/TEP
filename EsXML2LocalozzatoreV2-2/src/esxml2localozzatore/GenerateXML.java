/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esxml2localozzatore;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 *
 * @author Fabio
 */
public class GenerateXML {

    Document documentC, documentC10;

    /*public GenerateXML() throws ParserConfigurationException {
        
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        document = builder.newDocument();
    }*/
    
    public GenerateXML(Document document, int tipo) throws ParserConfigurationException{
        if(tipo == 1){
            if(documentC == null){
                DocumentBuilderFactory factory;
                DocumentBuilder builder;
                factory = DocumentBuilderFactory.newInstance();
                builder = factory.newDocumentBuilder();
                this.documentC = builder.newDocument();
            }
            else 
                this.documentC = document;
        } else {
            if(documentC10 == null){
                DocumentBuilderFactory factory;
                DocumentBuilder builder;
                factory = DocumentBuilderFactory.newInstance();
                builder = factory.newDocumentBuilder();
                this.documentC10 = builder.newDocument();
            }
            else 
                this.documentC10 = document;
        }
    }

    public void createDOMTreeC(List<cache> cache1) throws ParserConfigurationException {

        Element root;
        DatiGeo comune;
        Element element;

        root = documentC.createElement("ricerche");
        
        documentC.appendChild(root);
        for(int i= 0;i < cache1.size(); i++){
            element = createComuni2Element(cache1.get(i));
            root.appendChild(element);
        }
    }

    public void appendDOMTreeC(cache cache) {
        Element root, element;

        // estrazione del nodo sotto cui appendere l'elemento <libro>
        // nel caso corrente è la root
        root = documentC.getDocumentElement();

        // creazione dell'elementocomplesso <libro> e inserimento come ultimo figlio
        element = createComuni2Element(cache);
        root.appendChild(element);

    }
    
    private Element createComuni2Element(cache cache) {

        
        Element vect = documentC.createElement("ricerca");
        
        Element partenza = createSimpleElement("partenza", cache.getComunePart(), 1);
        vect.appendChild(partenza);

        
        Element arr = createSimpleElement("arrivo", cache.getComuneArr(), 1);
        vect.appendChild(arr);

        
        Element d = createSimpleElement("distanza", cache.getDistanza().toString(), 1);
        vect.appendChild(d);
        
        return vect;
    }
    
    public void createDOMTreeC10(comuniVicini cache) throws ParserConfigurationException {

        Element root;
        DatiGeo comune;
        Element element;

        root = documentC10.createElement("richieste");
        
        documentC10.appendChild(root);
        element = createComuni10Element(cache);
        root.appendChild(element);
    }

    public void appendDOMTreeC10(comuniVicini cache) {
        Element root, element;

        // estrazione del nodo sotto cui appendere l'elemento <libro>
        // nel caso corrente è la root
        root = documentC10.getDocumentElement();

        // creazione dell'elementocomplesso <libro> e inserimento come ultimo figlio
        element = createComuni10Element(cache);
        root.appendChild(element);

    }
    
    private Element createComuni10Element(comuniVicini cache) {

        
        Element vect = documentC10.createElement("richiesta");
        
        Element partenza = createSimpleElement("comuneRichiedente", cache.getComune(), 2);
        vect.appendChild(partenza);

        
        List<String> t = cache.getComuni();
        for(int i = 0; i < t.size(); i++){
            Element d = createSimpleElement("comune", t.get(i), 2);
            vect.appendChild(d);
        }
        return vect;
    }
    
    private Element createSimpleElement(String tag, String content, int tipo) {
        // creo sotto-elemento semplice <tag>
        if(tipo == 1){
            Element simpleElement = documentC.createElement(tag);
            // creo nodo e lo aggiungo come figlio al sotto-elemento semplice <tag>
            Text textNode = documentC.createTextNode(content);
            simpleElement.appendChild(textNode);
            return simpleElement;
        } else {
            Element simpleElement = documentC10.createElement(tag);
            // creo nodo e lo aggiungo come figlio al sotto-elemento semplice <tag>
            Text textNode = documentC10.createTextNode(content);
            simpleElement.appendChild(textNode);
            return simpleElement;
        }
        
    }

    public void saveToFileXML(String filename, int tipo) throws TransformerException {
        TransformerFactory factory;
        Transformer transformer;
        DOMSource source;
        StreamResult stream;

        factory = TransformerFactory.newInstance();
        transformer = factory.newTransformer();
        if(tipo == 1)
            source = new DOMSource(documentC);
        else 
            source = new DOMSource(documentC10); 
        stream = new StreamResult(new File(filename));
        transformer.transform(source, stream);
    }

}
