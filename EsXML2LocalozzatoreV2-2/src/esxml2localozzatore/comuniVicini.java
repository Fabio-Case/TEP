package esxml2localozzatore;


import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fabio
 */
public class comuniVicini {

    public comuniVicini(String comune) {
        this.comune = comune;
    }

    public comuniVicini(String comune, List<String> comuni) {
        this.comune = comune;
        this.comuni = comuni;
    }

    public comuniVicini() {
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public List<String> getComuni() {
        return comuni;
    }

    public void setComuni(List<String> comuni) {
        this.comuni = comuni;
    }
    String comune;
    List<String> comuni;
}
