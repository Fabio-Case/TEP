/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comuni;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 *
 * @author Fabio
 */
public class comune {

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    @Override
    public String toString() {
        return "comune{" + "nome=" + nome + ", latitudine=" + latitudine + ", longitudine=" + longitudine + '}';
    }
    
    private String nome;
    private Double latitudine, longitudine;
    
    public comune(){
        nome = "";
        latitudine = 0.0;
        longitudine = 0.0;
    }
    
    public comune(String nome, double latitudine, double longitudine){
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.nome = nome;
    }
    
    public comune(comune comuni){
        this.latitudine = comuni.latitudine;
        this.longitudine = comuni.longitudine;
        this.nome = comuni.nome;
    }

    public String visLatLog() {
        return "Latitudine: " + latitudine.toString() + "\tLongitudine: " + longitudine.toString();
    }
    
    
}
