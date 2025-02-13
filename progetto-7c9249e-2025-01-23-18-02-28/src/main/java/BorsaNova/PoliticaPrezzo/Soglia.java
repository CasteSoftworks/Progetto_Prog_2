package BorsaNova.PoliticaPrezzo;

import BorsaNova.Entita.Borsa.Azione;

//TODO descrittore
public class Soglia implements Politica{
    //TODO AF e RI
    
    /** La soglia minima per l'applicazione della Politica di Soglia */
    private final int soglia;

    /**
     * Costruttore di Soglia
     * 
     * @param soglia la soglia minima per l'applicazione della Politica di Soglia
     * 
     * @throws IllegalArgumentException se la soglia è minore o uguale a 0
     */
    public Soglia(int soglia) {
        if(soglia < 0) {
            throw new IllegalArgumentException("La soglia deve essere maggiore di 0");
        }
        this.soglia = soglia;
    }

    /**
     * Metodo per cambiare il prezzo di una Azione in caso di vendita
     * 
     * @param prezzo il prezzo corrente della quotazione
     * @param valore la quantità di azioni da vendere
     * 
     * @throws IllegalArgumentException se {@code modificaPrezzo} incorre in una IllegalArgumentException
     */
    @Override
    public void cambioPrezzoVendita(Azione az, int valore) {
        if(valore>soglia){
            int prezzo = az.getPrezzo()/2;
            if(prezzo>0){
                az.modificaPrezzo(prezzo);
            }
        } 
    }

    /**
     * Metodo per cambiare il prezzo di una Azione in caso di acquisto
     * 
     * @param prezzo il prezzo corrente della quotazione
     * @param valore la quantità di azioni da acquistare
     * 
     * @throws IllegalArgumentException se {@code modificaPrezzo} incorre in una IllegalArgumentException
     */
    @Override
    public void cambioPrezzoAcquisto(Azione az, int valore) {
        if(valore>soglia){
            az.modificaPrezzo(az.getPrezzo()*2);
        }
    }
    
}
