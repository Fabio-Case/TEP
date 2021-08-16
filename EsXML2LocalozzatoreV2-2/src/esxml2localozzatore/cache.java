/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esxml2localozzatore;

/**
 *
 * @author Fabio
 */
public class cache {

    public cache(String comunePart, String comuneArr, Double distanza) {
        this.comunePart = comunePart;
        this.comuneArr = comuneArr;
        this.distanza = distanza;
    }

    public cache() {
    }

    public String getComunePart() {
        return comunePart;
    }

    public void setComunePart(String comunePart) {
        this.comunePart = comunePart;
    }

    public String getComuneArr() {
        return comuneArr;
    }

    public void setComuneArr(String comuneArr) {
        this.comuneArr = comuneArr;
    }

    public Double getDistanza() {
        return distanza;
    }

    public void setDistanza(Double distanza) {
        this.distanza = distanza;
    }
    
    private String comunePart, comuneArr;
    private Double distanza;

    
    
}
