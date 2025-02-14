package borsaNova.politicaPrezzo;

import borsaNova.entita.Borsa.Azione;

/**
 * Classe per rappresentare una Politica di Soglia
 * 
 * <p>
 * Una Politica di Soglia ha un valore minimo di azioni da acquistare o vendere per applicare una modifica al prezzo.
 * Una Politica di Soglia raddoppia il prezzo di un'azione in caso di acquisto di un numero di azioni maggiore della soglia e dimezza il prezzo in caso di vendita di un numero di azioni maggiore della soglia.
 * 
 * <p>
 * Fatto con l'aiuto di:
 * <ul>
 * <li>Github Copilot - GTP4.0 (correzione errori e problemi e autocompletamento javadoc [revisionato e corretto poi a mano])</li>
 * </ul>
 */
public class Soglia implements Politica{
    /**
     * AF
     * Una politica di Soglia rappresentata da una soglia minima per l'applicazione della Politica di Soglia
     * 
     * RI
     * L'oggetto Soglia rispetta la seguente condizione: soglia maggiore di 0
     */
    
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
     * @param az l'azione da modificare
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
     * @param az l'azione da modificare
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
