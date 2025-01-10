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
 * <li>Github Copilot - GTP4.0 (correzione errori e problemi, generazione di parte del codice e autocompletamento javadoc)</li>
 * <li>ChatGTP 4.o (correzione errori e problemi)</li>
 * <li>StackOverflow (correzione errori e problemi)</li>
 * <li>Gabriele Favizzi (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * <li>Simone Coccè (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * <li>Piero Chobanyan (compagno di corso, logica iniziale)</li>
 * </ul>
 */
public class Quotazione {
    /**
     * AF:
     * AF(prezzoCorrente) = Una quotazione rappresentata da:
     * - prezzoCorrente: è il prezzo corrente della quotazione
     *
     * RI:
     * RI(prezzoCorrente) = L'oggetto quotazione rispetta la seguente condizione:
     * - prezzoCorrente è maggiore o uguale a 0
     */

    /** Il prezzo corrente della quotazione */
    private int prezzoCorrente;

    /**
     * Metodo per costruire una quotazione
     * 
     * @param prezzo il prezzo corrente della quotazione
     */
    public Quotazione(int prezzo){
        this.prezzoCorrente = prezzo;
    }

    /**
     * Metodo per ottenere il prezzo corrente della quotazione
     * 
     * @return il prezzo corrente della quotazione
     */
    public int getPrezzoCorrente(){
        return prezzoCorrente;
    }

    /**
     * Metodo per aggiornare il prezzo corrente della quotazione 
     * 
     * @param var la variazione del prezzo corrente
     * 
     * modifies this.prezzoCorrente
     */
    public void aggiornaPrezzo(int var){
        prezzoCorrente = var;
    }
}
