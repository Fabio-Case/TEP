/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esxml2localozzatore;

/**
 *
 * @author Francesco
 */
public class DatiGeo {
    
    private String id;
    private String id_regione;
    private String id_provincia;
    private String nome;
    private String capoluogo_provincia;
    private String codice_catastale;
    private float latitudine;
    private float longitudine;
    
     public DatiGeo() {
    }

    public DatiGeo(String id, String id_regione, String id_provincia, String nome, String capoluogo_provincia, String codice_catastale, float latitudine, float longitudine) {
        this.id = id;
        this.id_regione = id_regione;
        this.id_provincia = id_provincia;
        this.nome = nome;
        this.capoluogo_provincia = capoluogo_provincia;
        this.codice_catastale = codice_catastale;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_regione() {
        return id_regione;
    }

    public void setId_regione(String id_regione) {
        this.id_regione = id_regione;
    }

    public String getId_provincia() {
        return id_provincia;
    }

    public void setId_provincia(String id_provincia) {
        this.id_provincia = id_provincia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCapoluogo_provincia() {
        return capoluogo_provincia;
    }

    public void setCapoluogo_provincia(String capoluogo_provincia) {
        this.capoluogo_provincia = capoluogo_provincia;
    }

    public String getCodice_catastale() {
        return codice_catastale;
    }

    public void setCodice_catastale(String codice_catastale) {
        this.codice_catastale = codice_catastale;
    }

    public float getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(float latitudine) {
        this.latitudine = latitudine;
    }

    public float getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(float longitudine) {
        this.longitudine = longitudine;
    }

    @Override
    public String toString() {
        return "DatiGeo{" + "id=" + id + ", id_regione=" + id_regione + ", id_provincia=" + id_provincia + ", nome=" + nome + ", capoluogo_provincia=" + capoluogo_provincia + ", codice_catastale=" + codice_catastale + ", latitudine=" + latitudine + ", longitudine=" + longitudine + '}';
    }

   
    
   

}
