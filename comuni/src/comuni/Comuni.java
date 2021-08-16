/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comuni;

import java.io.IOException;
import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Fabio
 */
public class Comuni {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner t = new Scanner(System.in);
        xml operazioniXML = new xml();
        try {
            String xml = "resource/comuni.xml";
            String xsd = "resource/comuni.xsd";
            operazioniXML.validate(xml, xsd);
            System.out.println("Documento XML valido.");
        } catch (SAXException exception) {
            System.err.println("Documento XML NON valido:");
            System.err.println(exception.getMessage());
            System.err.close();
        } catch (IOException ex) {
            Logger.getLogger(Comuni.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        List<comune> comuni = null;
        String xml = "resource/comuni.xml";
        try {
            comuni = operazioniXML.parseDocument(xml);
            String scelta = "";
            do{
                System.out.println("Inserire 'C' per vedere la latitudine e longitudine di un paese");
                System.out.println("Inserire 'D' per vedere la distanza a linea d'aria tra due citta");
                System.out.println("Inserire 'P' per visualizzare la città più vicina");
                System.out.println("Inserire 'F' per chiudere il software");
                
                System.out.print("Inserire una scelta tra quelle indicate sopra\t");
                scelta = t.nextLine();
                
                switch(scelta){
                    case "C" -> {
                        System.out.print("Inserire il nome di una citta/paese: ");
                        String città = t.nextLine();
                        int tempInd = comuni.
                        System.out.println(comuni.get(tempInd).visLatLog());
                    }
                    case "D" -> {
                        System.out.print("Inserire la prima città:\t");
                        int indC1 = comuni.indexOf(t.nextLine());
                        System.out.print("Inserire la seconda città:\t");
                        int indC2 = comuni.indexOf(t.nextLine());
                        Double distanza = distanza(comuni.get(indC1).getLatitudine(), comuni.get(indC1).getLongitudine(), comuni.get(indC2).getLatitudine(), comuni.get(indC2).getLongitudine());
                        System.out.println("La distanza delle città selezionate è: " + distanza.toString());
                    }
                    case "P" -> {
                        System.out.print("Inserire la latitudine della propria posizione\t");
                        double latP = t.nextDouble();
                        System.out.print("Inserire la longitudine della propria posizione\t");
                        double lonP = t.nextDouble();
                        String nome = "";
                        Double distMin = 1000.00;
                        for(comune comune : comuni){
                            double dist = distanza(comune.getLatitudine(), comune.getLongitudine(), latP, lonP);
                            if(dist < distMin){
                                distMin = dist;
                                nome = comune.getNome();
                            }
                        }
                        System.out.println("Il paese più vicino è: " + nome);
                    }
                }
                
                
            }while(!"F".equals(scelta));
        } catch (ParserConfigurationException | SAXException | IOException exception) {
            System.out.println("Errore!");
        } catch (ParseException ex) {
            Logger.getLogger(Comuni.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
        
    }
    
    private static double distanza(double latA, double lonA, double latB, double lonB){
        /* Definisce le costanti e le variabili */
        double R = 6371;
        double pigreco = 3.1415927;
        double lat_alfa, lat_beta;
        double lon_alfa, lon_beta;
        double fi;
        double p, d;
        /* Converte i gradi in radianti */
        lat_alfa = pigreco * latA / 180;
        lat_beta = pigreco * latB / 180;
        lon_alfa = pigreco * lonA / 180;
        lon_beta = pigreco * lonB / 180;
        /* Calcola l'angolo compreso fi */
        fi = Math.abs(lon_alfa - lon_beta);
        /* Calcola il terzo lato del triangolo sferico */
            p = acos(sin(lat_beta) * sin(lat_alfa) + 
          cos(lat_beta) * cos(lat_alfa) * cos(fi));
        /* Calcola la distanza sulla superficie 
        terrestre R = ~6371 km */
        d = p * R;
        return(d);
    }
    
}
