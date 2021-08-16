/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esxml2localozzatore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Fabio
 */
public class EsXML2Localozzatore {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParserConfigurationException {
        // TODO code application logic here
        Scanner scan = new Scanner(System.in);
        OperazioniXML operazioniXML = new OperazioniXML();
        CalcoloDistanza calc = new CalcoloDistanza();
        boolean end = false;
        try {
            String xml = "resource/comuni.xml";
            String xsd = "resource/comuni.xsd";
            operazioniXML.validate(xml, xsd);
            System.out.println("Documento XML valido.");
        } catch (SAXException exception) {
            System.out.println("Documento XML NON valido:");
            System.out.println(exception.getMessage());
        }

        List<DatiGeo> dati = null;
        List<cache> listRicerche = null;
        List<comuniVicini> cv = null;
        String xml = "resource/comuni.xml";
        String cache1 = "resource/cache.xml";
        try {
            dati = operazioniXML.parseDocument(xml, 1);
        } catch (ParserConfigurationException | SAXException | IOException exception) {
            System.out.println(exception);
        }
        // iterazione della lista e visualizzazione degli oggetti

        while (end == false) {
            System.out.println("1-Dati citta");
            System.out.println("2-Distanza tra citta");
            System.out.println("3-Localizzazione citta");
            System.out.println("5-10 comuni vicini");
            System.out.println("4-Esci");

            String num = scan.nextLine();

            if ("1".equals(num)) {

                System.out.println("Inserisci Citta");
                String citta = scan.nextLine();

                for (DatiGeo dato : dati) {

                    if (citta.equals(dato.getNome())) {

                        System.out.println("Citta -> " + dato.getNome() + " Latitudine: " + dato.getLatitudine() + " Longitudine: " + dato.getLongitudine());
                        break;
                    }
                }
            } else if ("2".equals(num)) {
                try {
                    listRicerche = operazioniXML.parseDocument(cache1, 2);
                } catch (ParserConfigurationException | SAXException | IOException exception) {
                }
                
                System.out.println("Inserisci la prima citta");
                String cittaP = scan.nextLine();
                System.out.println("Inserisci la seconda citta");
                String cittaA = scan.nextLine();
                Double distanza = 0.0;
                boolean sentinella = false;
                if(listRicerche != null){
                    for(int i=0; i<listRicerche.size() && !sentinella; i++){
                        if(listRicerche.get(i).getComunePart().equals(cittaP) && listRicerche.get(i).getComuneArr().equals(cittaA)){
                            distanza = listRicerche.get(i).getDistanza();
                            sentinella = true;
                        }
                        else if(listRicerche.get(i).getComunePart().equals(cittaA) && listRicerche.get(i).getComuneArr().equals(cittaP)){
                            distanza = listRicerche.get(i).getDistanza();
                            sentinella = true;
                        }
                    }
                }
                if(!sentinella){
                    String cA = "", cP = "";
                    double latA = 0, longA = 0, latP = 0, longP = 0;
                    for(DatiGeo dato : dati){
                        if(dato.getNome().equals(cittaP)){
                            latP = dato.getLatitudine();
                            longP = dato.getLongitudine();
                            cP = dato.getNome();
                        }
                        if(dato.getNome().equals(cittaA)){
                            latA = dato.getLatitudine();
                            longA = dato.getLongitudine();
                            cA = dato.getNome();
                        }
                        if(!"".equals(cA) && !"".equals(cP))
                            break;
                    }
                    distanza = calc.disgeod(latA, longA, latP, longP);
                    cache temp = new cache(cA, cP, distanza);
                    if(listRicerche == null)
                        listRicerche = new ArrayList<>();
                    listRicerche.add(temp);
                    GenerateXML gen = new GenerateXML(operazioniXML.getDocumentC(),1);
                    Document doc = operazioniXML.getDocumentC();
                    gen.createDOMTreeC(listRicerche);
                    try {
                        gen.saveToFileXML(cache1,1);
                    } catch (TransformerException ex) {
                        Logger.getLogger(EsXML2Localozzatore.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                  

                System.out.println("La distanza tra le due citta è: " + distanza.toString() + " KM");
                
            } else if ("3".equals(num)) {

                double min = 99999999;
                String comune = "";
                System.out.println("Inserisci la Latitudine");
                String lat = scan.nextLine();
                System.out.println("Inserisci la Longitudine");
                String lon = scan.nextLine();

                double la = Double.parseDouble(lat);
                double lo = Double.parseDouble(lon);
                for (DatiGeo dato : dati) {

                    double dist = calc.disgeod(la, lo, dato.getLatitudine(), dato.getLongitudine());
                    if (dist < min) {

                        min = dist;
                        comune = dato.getNome();
                    }
                }

                System.out.println("Il comune più vicino è " + comune + " con distanza " + min);
            } else if ("4".equals(num)) {

                end = true;
            } else if ("5".equals(num)) {
                try {
                    cv = operazioniXML.parseDocument("resource/cache10Comuni.xml", 3);
                } catch (ParserConfigurationException | SAXException | IOException exception) {
                }
                boolean sentinella = false;
                double lat = 0, lon = 0;
                int ind = 0;
                System.out.println("Inserire un comune");
                String c = scan.nextLine();
                if(cv != null){
                    for(int i = 0; i < cv.size(); i++)
                        if(cv.get(i).getComune().equals(c)){
                            sentinella = true;
                            ind = i;
                            List<String> temp = cv.get(i).getComuni();
                            for(int j = 0; j < temp.size(); i++){
                                System.out.println(temp.get(j));
                            }
                            break;
                        }
                    
                }
                if(!sentinella){
                    for(int i = 0; i < dati.size(); i++){
                        if(dati.get(i).getNome().equals(c)){
                            lat = dati.get(i).getLatitudine();
                            lon = dati.get(i).getLongitudine();
                            ind = i;
                            break;
                        }
                    }
                    String[][] datiC = new String[7999][2];

                    for(int i = 0; i < dati.size(); i++){
                        Double d = calc.disgeod(lat, lon, (double)dati.get(i).getLatitudine(), (double)dati.get(i).getLongitudine());
                        if( i != ind){
                            datiC[i][0] = dati.get(i).getNome();
                            datiC[i][1] = d.toString();
                        }
                    }

                    for(int i = 0; i< datiC.length-1; i++)
                        for(int j = 1; j < datiC.length-1; j++){
                            String r1 = datiC[j][1], r2 = datiC[j+1][1];
                            if(r1 != null && r2 != null && Float.parseFloat(r1) > Float.parseFloat(r2)){
                                String temp10 = datiC[j][0];
                                String temp11 = datiC[j][1];
                                datiC[j][0] = datiC[j+1][0];
                                datiC[j][1] = datiC[j+1][1];
                                datiC[j+1][0] = temp10;
                                datiC[j+1][1] = temp11;
                            }
                        }
                    List<String> com = new ArrayList<>();
                    for(int i = 0; i < 10; i++){
                        com.add(datiC[i][0]);
                        System.out.println(datiC[i][0] + "\t" + datiC[i][1]);
                    }
                    
                    comuniVicini temp = new comuniVicini(c, com);
                    GenerateXML gen = new GenerateXML(operazioniXML.getDocumentCom(),2);
                    if(operazioniXML.getDocumentC() == null)
                        gen.createDOMTreeC10(temp);
                    else
                        gen.appendDOMTreeC10(temp);
                    try {
                        gen.saveToFileXML("resource/cache10Comuni.xml",2);
                    } catch (TransformerException ex) {
                        Logger.getLogger(EsXML2Localozzatore.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        }

    }

}
