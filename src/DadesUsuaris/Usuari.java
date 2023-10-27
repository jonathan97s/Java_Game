/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DadesUsuaris;

/**
 *
 * @author xiaot
 */
public class Usuari {
    
    private String nom;
    private String contrasenya;

    public Usuari(String nom, String contrasenya) {
        this.nom = nom;
        this.contrasenya = contrasenya;
    }

    public Usuari() {
        
    }
    

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }
    
    
    
}
