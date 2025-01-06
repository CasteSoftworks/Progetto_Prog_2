package BorsaNova.PoliticaPrezzo;

/**
 * Interfaccia per rappresentare una politica di prezzo che viene poi espansa dalle classi presenti nella stesso package
 * <br>
 * Fatto con l'aiuto di:
 * <ul>
 * <li>Github Copilot -GTP4.0</li>
 * <li>Chat GTP4.o</li>
 * <li>StackOverflow</li>
 * <li>Gabriele Favizzi (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * <li>Simone Coccè (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * </ul>
 * 
 */
public interface Politica {
    /**
     * Metodo per calcolare la variazione di prezzo di una quotazione in base alla politica della borsa
     * 
     * @param prezzo il prezzo corrente della quotazione
     * @param acquisto true se si tratta di un acquisto, false se si tratta di una vendita
     * 
     * @return il prezzo calcolato
     * 
     * @throws IllegalArgumentException se il valore che deve modificare prezzo è minore di 0 nelle classi che implementano l'interfaccia
     */
    public abstract int calcolaPrezzo(int prezzo, boolean acquisto) throws IllegalArgumentException;
    
} 