package BorsaNova.PoliticaPrezzo;

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
 * <li>Matteo Mascherpa (compagno di corso, aiuto sulla formalità della documentazione)</li>
 * </ul>
 */
public class Variazione implements Politica{
    /**
     * AF
     * AF(variazioneSu, variazioneGiu) = Una politica di variazione rappresentata da:
     * - variazioneSu: è la variazione da applicare al prezzo in caso di acquisto
     * - variazioneGiu: è la variazione da applicare al prezzo in caso di vendita
     * 
     * RI
     * RI(variazioneSu, variazioneGiu) = L'oggetto politica di variazione rispetta la seguente condizione:
     * - variazioneSu è maggiore o uguale a 0
     * - variazioneGiu è maggiore o uguale a 0
     */
    /** La variazione da applicare al prezzo dopo un acquisto */
    private final int variazioneSu;
    /** La variazione da applicare al prezzo dopo una vendita */
    private final int variazioneGiu;

    /**
     * Costruttore della classe
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
     * Metodo per calcolare il prezzo di una quotazione
     * 
     * @param prezzo il prezzo corrente della quotazione
     * @param acquisto {@code true} se si tratta di un acquisto, {@code false} se si tratta di una vendita
     * 
     * @return il prezzo (se si tratta di un acquisto, il prezzo calcolato sarà maggiore di quello corrente di un valore variazioneSu altrimenti minore di un valore variazioneGiu o 1)
     */
    @Override
    public int calcolaPrezzo(int prezzo, boolean acquisto) {
        if (acquisto) {
            return prezzo + variazioneSu;
        }
        if(prezzo - variazioneGiu <= 0) {
            return 1;
        }
        return prezzo - variazioneGiu;
    }
}
