package BorsaNova.PoliticaPrezzo;

/**
 * Interfaccia per rappresentare una politica di prezzo che viene poi espansa dalle classi presenti nella stesso package
 * 
 * <p>
 * Una politica di prezzo ha un metodo per calcolare la variazione di prezzo di una quotazione in base alla politica della borsa e da un valore booleano che indica se si tratta di un acquisto o di una vendita.
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
     * Metodo per calcolare la variazione di prezzo di una quotazione in base alla politica della Borsa
     * 
     * @param prezzo il prezzo corrente della quotazione
     * @param acquisto {@code true} se si tratta di un acquisto, {@code false} se si tratta di una vendita
     * 
     * @return il prezzo calcolato
     * 
     * @throws IllegalArgumentException se il valore che deve modificare prezzo è minore di 0 nelle classi che implementano l'interfaccia
     */
    public abstract int calcolaPrezzo(int prezzo, boolean acquisto) throws IllegalArgumentException;
    
} 