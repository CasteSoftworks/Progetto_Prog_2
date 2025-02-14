package borsaNova.politicaPrezzo;

import borsaNova.entita.Borsa.Azione;

/**
 * Classe per rappresentare una politica di decremento
 * 
 * <p>
 * Una politica di decremento ha un valore da sottrarre al prezzo in caso di vendita.
 * Una politica di decremento può calcolare il prezzo di una quotazione in base se si tratta di una vendita, facendo attenzione a non fardiventare il prezzo minore o uguale a 0.
 * 
 * <p>
 * Fatto con l'aiuto di:
 * <ul>
 * <li>Github Copilot - GTP4.0 (correzione errori e problemi e autocompletamento javadoc [revisionato e corretto poi a mano])</li>
 * <li>ChatGTP 4.o (correzione errori e problemi)</li>
 * <li>StackOverflow (correzione errori e problemi)</li>
 * <li>Gabriele Favazzi (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * <li>Simone Coccè (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * <li>Matteo Mascherpa (compagno di corso, suggerimento di stile documentativo)</li>
 * </ul>
 */
public class Decremento implements Politica {
    /**
     * AF
     * Una politica di decremento rappresentata da un decremento da applicare al prezzo
     * 
     * RI
     * L'oggetto politica di decremento rispetta la seguente condizione: decremento maggiore o uguale a 0
     */

    /** Il decremento da applicare al prezzo */
    private final int decremento;

    /**
     * Costruttore di Decremento
     * 
     * @param decremento il decremento da applicare al prezzo
     * 
     * @throws IllegalArgumentException se il decremento è minore di 0
     */
    public Decremento(int decremento) throws IllegalArgumentException {
        if(decremento < 0) {
            throw new IllegalArgumentException("Il decremento deve essere maggiore di 0");
        }
        this.decremento = decremento;
    }

    /**
     * Metodo per cambiare il prezzo di una Azione in caso di vendita
     * 
     * @param az l'Azione da modificare
     * @param valore non utile in questa implementazione di Politica
     * 
     * @throws IllegalArgumentException se {@code modificaPrezzo} è minore di 0
     */
    @Override
    public void cambioPrezzoVendita(Azione az, int valore) {
        int prz = az.getPrezzo() - this.decremento;
        if(prz <= 0) {
            az.modificaPrezzo(1);
        } else {
            az.modificaPrezzo(prz);
        }   
    }

    /**
     * Metodo per cambiare il prezzo di una Azione in caso di vendita (non utile in questa implementazione di Politica)
     * 
     * @param az l'Azione da modificare
     * @param valore non utile in questa implementazione di Politica
     */
    @Override
    public void cambioPrezzoAcquisto(Azione az, int valore) {
        return;
    }
}
