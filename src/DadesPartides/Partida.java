package DadesPartides;

import DadesUsuaris.Usuari;

public class Partida {
    
    private String nomUsuari;
    private boolean guanyada;
    private int intents;
    private int dificultat;
    private int puntuacio;
    private Integer posicio;
    private String numPartida;
    

    public Partida(String u, boolean bit, int intents, int dificultat, int puntuacio) {
        this.nomUsuari = u;
        this.guanyada = bit;
        this.intents = intents;
        this.dificultat = dificultat;
        this.puntuacio = puntuacio;
    }

    public Partida(String num, int dificultat, int puntuacio) {
        this.numPartida = num;
        this.dificultat = dificultat;
        this.puntuacio = puntuacio;
    }

    public Partida(Integer posicio, String nomUsuari, int puntuacio) {
        this.nomUsuari = nomUsuari;
        this.puntuacio = puntuacio;
        this.posicio = posicio;
    }


    public Partida(String u, boolean bit, int dificultat, int puntuacio) {
        this.nomUsuari = u;
        this.guanyada = bit;
        this.dificultat = dificultat;
        this.puntuacio = puntuacio;
    }

    public void setNomUsuari(String NomUsuari) {
        this.nomUsuari = NomUsuari;
    }

    public void setGuanyada(boolean guanyada) {
        this.guanyada = guanyada;
    }

    public void setIntents(int intents) {
        this.intents = intents;
    }

    public void setDificultat(int dificultat) {
        this.dificultat = dificultat;
    }

    public void setPuntuacio(int puntuacio) {
        this.puntuacio = puntuacio;
    }

    public String getNomUsuari() {
        return nomUsuari;
    }

    public boolean getGuanyada() {
        return guanyada;
    }

    public int getIntents() {
        return intents;
    }

    public int getDificultat() {
        return dificultat;
    }

    public int getPuntuacio() {
        return puntuacio;
    }

    public Integer getPosicio() {
        return posicio;
    }

    public void setPosicio(Integer posicio) {
        this.posicio = posicio;
    }

    public String getNum() {
        return numPartida;
    }

    public void setNum(String num) {
        this.numPartida = num;
    }
    
    
}
