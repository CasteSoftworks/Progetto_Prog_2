package BorsaNova.PoliticaPrezzo;

import BorsaNova.Entita.Borsa.Azione;

/**
 * Classe per rappresentare una Politica di Incremento
 * 
 * <p>
 * Una Politica di Incremento ha un valore da aggiungere al prezzo in caso di acquisto.
 * Una Politica di Incremento può aggiornare il prezzo di una Azione in caso di un acquisto o non fare nulla in caso di vendita.
 * 
 * <p>
 * Fatto con l'aiuto di:
 * <ul>
 * <li>Github Copilot - GTP4.0 (correzione errori e problemi e autocompletamento javadoc [revisionato e corretto poi a mano])</li>
 * <li>Gabriele Favazzi (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * <li>Simone Coccè (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * <li>Matteo Mascherpa (compagno di corso, suggerimento di stile documentativo)</li>
 * </ul>
 */
public class Incremento implements Politica {
    /**
     * AF
     * Una politica di Incremento rappresentata da un incremento da applicare al prezzo
     * 
     * RI
     * L'oggetto Incremento rispetta la seguente condizione: incremento maggiore o uguale a 0
     */

    /** L'incremento da applicare al prezzo */
    private final int incremento;

    /**
     * Costruttore di Incremento
     * 
     * @param incremento l'incremento da applicare al prezzo
     * 
     * @throws IllegalArgumentException se l'incremento è minore di 0
     */
    public Incremento(int incremento) throws IllegalArgumentException {
        if(incremento < 0) {
            throw new IllegalArgumentException("L'incremento deve essere maggiore di 0");
        }
        this.incremento = incremento;
    }

    /**
     * Metodo per cambiare il prezzo di una Azione in caso di vendita (non utile in questa implementazione di Politica)
     * 
     * @param az l'Azione da modificare
     * @param valore non utile in questa implementazione di Politica
     */
    @Override
    public void cambioPrezzoVendita(Azione az, int valore) {
        return;
    }

    /**
     * Metodo per cambiare il prezzo di una Azione in caso di acquisto
     * 
     * @param az l'Azione da modificare
     * @param valore non utile in questa implementazione di Politica
     * 
     * @throws IllegalArgumentException se {@code modificaPrezzo} incorre in una IllegalArgumentException
     */
    @Override
    public void cambioPrezzoAcquisto(Azione az, int valore) {
        az.modificaPrezzo(az.getPrezzo() + this.incremento);
    }
    
}
