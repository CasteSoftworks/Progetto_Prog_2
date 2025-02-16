package borsaNova;

import borsaNova.Borsa.Azione;

/**
 * Classe per rappresentare una politica di variazione ad incremento-decremento
 * 
 * <p>
 * Una politica di variazione ha un valore da aggiungere al prezzo in caso di acquisto e uno da sottrarre in caso di vendita.
 * Una politica di variazione può calcolare il prezzo di una quotazione in base a se si tratta di un acquisto o di una vendita (facendo attenzione a non rendere il prezzo minore o uguale a 0).
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
public class Variazione implements Politica{
    /*
     * AF
     * Una politica di Variazione è rappresentata da:
     * - variazioneSu: è la variazione da applicare al prezzo in caso di acquisto
     * - variazioneGiu: è la variazione da applicare al prezzo in caso di vendita
     * 
     * RI
     * L'oggetto Variazione rispetta le seguenti condizioni:
     * - variazioneSu è maggiore o uguale a 0
     * - variazioneGiu è maggiore o uguale a 0
     */
    
    /** La Variazione da applicare al prezzo dopo un acquisto */
    private final int variazioneSu;
    /** La Variazione da applicare al prezzo dopo una vendita */
    private final int variazioneGiu;

    /**
     * Costruttore di Variazione
     * 
     * @param variazioneA la variazione da applicare al prezzo in caso di acquisto
     * @param variazioneB la variazione da applicare al prezzo in caso di vendita
     * 
     * @throws IllegalArgumentException se la variazioneA o la variazioneB è minore di 0
     */
    public Variazione(int variazioneA, int variazioneB) throws IllegalArgumentException {
        if(variazioneA < 0 || variazioneB < 0) {
            throw new IllegalArgumentException("La variazione deve essere maggiore di 0");
        }
        this.variazioneSu = variazioneA;
        this.variazioneGiu = variazioneB;
    }

    /**
     * Metodo per cambiare il prezzo di una Azione in caso di vendita
     * 
     * @param az l'Azione da modificare
     * @param valore non utile in questa implementazione di Politica
     * 
     * @throws IllegalArgumentException se {@code modificaPrezzo} incorre in una IllegalArgumentException
     */
    @Override
    public void cambioPrezzoVendita(Azione az, int valore) {
        int prz = az.getPrezzo() - variazioneGiu;
        if(prz <= 0) {
            az.modificaPrezzo(1);
        } else {
            az.modificaPrezzo(prz);
        }   
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
        az.modificaPrezzo(az.getPrezzo() + variazioneSu);
    }
}
