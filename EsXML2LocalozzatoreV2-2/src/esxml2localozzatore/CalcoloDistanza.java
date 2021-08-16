/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esxml2localozzatore;

import static java.lang.Math.abs;
import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 *
 * @author Fabio
 */
public class CalcoloDistanza {
    
    public CalcoloDistanza(){}
    
    public double disgeod(double latA, double lonA,double latB, double lonB) {
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
        fi = abs(lon_alfa - lon_beta);
        /* Calcola il terzo lato del triangolo sferico */
        p = acos(sin(lat_beta) * sin(lat_alfa)
                + cos(lat_beta) * cos(lat_alfa) * cos(fi));
        /* Calcola la distanza sulla superficie 
      terrestre R = ~6371 km */
        d = p * R;
        return (d);
    }
}
