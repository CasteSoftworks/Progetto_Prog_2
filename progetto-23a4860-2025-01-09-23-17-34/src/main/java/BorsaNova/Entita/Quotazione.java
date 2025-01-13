package BorsaNova.Entita;

/**
 * Classe per rappresentare una Quotazione
 * 
 * <p>
 * Una Quotazione ha un prezzo corrente.
 * Una Quotazione può essere aggiornata.
 * 
 * <p>
 * Fatto con l'aiuto di:
 * <ul>
 * <li>Gabriele Favizzi (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * <li>Simone Coccè (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * <li>Piero Chobanyan (compagno di corso, logica iniziale)</li>
 * <li>Matteo Mascherpa (compagno di corso, suggerimento di stile documentativo e reminder dei modifies)</li>
 * </ul>
 */
public class Quotazione {
    /**
     * AF:
     * Una Quotazione rappresentata da:
     * - prezzoCorrente: è il prezzo corrente della quotazione
     *
     * RI:
     * L'oggetto Quotazione rispetta la seguente condizione: prezzoCorrente è maggiore o uguale a 0
     */

    /** Il {@code prezzoCorrente} della Quotazione */
    private int prezzoCorrente;

    /**
     * Costruttore di Quotazione
     * 
     * @param prezzo il prezzo corrente della quotazione
     */
    public Quotazione(int prezzo){
        this.prezzoCorrente = prezzo;
    }

    /**
     * Metodo per ottenere il prezzo corrente della Quotazione
     * 
     * @return il prezzo corrente della Quotazione
     */
    public int getPrezzoCorrente(){
        return prezzoCorrente;
    }

    /**
     * Metodo per aggiornare il prezzo corrente della Quotazione 
     * 
     * <p>
     * Modifies {@code prezzoCorrente} della Quotazione
     * 
     * @param var la variazione del prezzo corrente
     */
    public void aggiornaPrezzo(int var){
        prezzoCorrente = var;
    }
}
