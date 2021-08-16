package com.example;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;



public class Main 
{
    public static void main( String[] args ) throws Exception
    {
        Http http = new Http();
        Scanner t = new Scanner(System.in);
        Json json = new Json();
        Map<Object, Object> data = new HashMap<Object, Object>();
        
        int ind = 0;


        do{
            System.out.println("Inserire 1 per inserimento");
            System.out.println("Inserire 2 per la visualizzazione dei passa di una determinata targa");
            System.out.println("Inserire 0 per uscire");

            ind = Integer.parseInt(t.nextLine());
            switch (ind) {
                case 1:
                    List<String> dati = new ArrayList<String>();
                    List<String> key = new ArrayList<String>();
                    System.out.println("Inserire la targa");
                    key.add("targa");
                    String tempS = t.nextLine();
                    dati.add(tempS);
                    System.out.println("Inserire rilevatore");
                    key.add("rilevatore");
                    tempS = t.nextLine();
                    dati.add(tempS);
                    String ris = json.createJson(key, dati);
                    System.out.println(ris);
                    data.put("parametri", ris);
                    System.out.println(http.sendPost("http://localhost/TEP_API/es16/inserimento", "firefox", data));
                    break;
                case 2:
                    //GET
                    System.out.print("Inserire la targa \t");
                    String targa = t.nextLine();
                    String s = http.sendGet("http://localhost/TEP_API/es16/get/"+URLEncoder.encode(targa, "UTF-8"), "firefox");
                    String temp = s.substring(s.indexOf(" "));
                    
                    List<String> element = new ArrayList<>();
                    element.add("time");
                    element.add("pos");

                    json.parse(temp, "vis", element, null);
                    break;
                default:
                    break;
            }
        }while(ind!=0);

        

        
        //per togliere WARM
        t.close();
    }
}
