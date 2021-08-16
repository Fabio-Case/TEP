/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientxml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerException;

/**
 *
 * @author Fabio
 */
public class ClientXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner t = new Scanner(System.in);
        try {
            comunicazioneSocket socket = new comunicazioneSocket(2222);
            
                  
            String scelta = "";
            do{
                System.out.println("V-visualizza con parola chiave");
                System.out.println("A-agggiungi domanda");
                System.out.println("F-fine");
                scelta = t.nextLine();

                switch(scelta){
                    case "V" -> {
                        System.out.println("Inserire parola chiave");
                        String pC = t.nextLine();

                        System.out.println(socket.invia("V;"+pC));

                    }
                    case "A" ->{
                        System.out.println("Inserire nome");
                        String nome = t.nextLine();
                        System.out.println("Inserire testo");
                        String text = t.nextLine();
                        String risp ="";
                        String risp1 = "";
                        do{
                            System.out.println("Inserire risposte - per terminare scrivere 0");
                            risp1 = t.nextLine();
                            if(!risp1.equals("0"))
                                risp += risp1 + "|";
                        }while(!risp1.equals("0"));
                        System.out.println(socket.invia("A;"+nome+";"+text+";"+risp));
                    }
                }
            }while(!"F".equals(scelta));
        } catch (IOException ex) {
            Logger.getLogger(ClientXML.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }
    
}
