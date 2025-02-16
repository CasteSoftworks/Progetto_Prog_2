package borsaNova;

import borsaNova.Borsa.Azione;

/**
 * Interfaccia per rappresentare una Politica di Prezzo che viene poi espansa dalle classi presenti nella stesso package
 * 
 * <p>
 * Una politica di prezzo ha un metodo per calcolare la variazione di prezzo di una Azione in base alla Politica della Borsa in caso di vendita e un metodo per calcolare la variazione di prezzo di una Azione in base alla Politica della Borsa in caso di acquisto.
 * 
 * <p>
 * Fatto con l'aiuto di:
 * <ul>
 * <li>Github Copilot - GTP4.0 (correzione errori e problemi e autocompletamento javadoc [revisionato e corretto poi a mano])</li>
 * <li>Gabriele Favazzi (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * <li>Simone Coccè (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * <li>Piero Chobanyan (compagno di corso, discussione sull'uso della interfaccia)</li>
 * <li>Matteo Mascherpa (compagno di corso, suggerimento di stile documentativo)</li>
 * </ul>
 */
public interface Politica {
    /**
     * Metodo per cambiare il prezzo di una Azione in caso di vendita
     * 
     * @param az l'Azione da modificare
     * @param valore utile per la Politica di Soglia
     *  
     * @throws IllegalArgumentException se le classi che implementano l'interfaccia incorrono in IllegalArgumentException
     */
    public abstract void cambioPrezzoVendita(Azione az, int valore) throws IllegalArgumentException;
    
    /**
     * Metodo per cambiare il prezzo di una Azione in caso di acquisto
     * 
     * @param az l'Azione da modificare
     * @param valore utile per la Politica di Soglia
     * 
     * @throws IllegalArgumentException se le classi che implementano l'interfaccia incorrono in IllegalArgumentException
     */
    public abstract void cambioPrezzoAcquisto(Azione az, int valore) throws IllegalArgumentException;
} 