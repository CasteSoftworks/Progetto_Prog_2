package BorsaNova.PoliticaPrezzo;

/**
 * Classe per rappresentare una politica di incremento
 * <br>
 * Fatto con l'aiuto di:
 * <ul>
 * <li>Github Copilot - GTP4.0</li>
 * <li>ChatGTP 4.o</li>
 * <li>StackOverflow</li>
 * <li>Gabriele Favazzi (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * <li>Simone Coccè (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * </ul>
 */
public class Incremento implements Politica {
    /**
     * AF
     * AF(incremento) = Una politica di incremento rappresentata da:
     * - incremento: è l'incremento da applicare al prezzo
     * 
     * RI
     * RI(incremento) = L'oggetto politica di incremento rispetta la seguente condizione:
     * - incremento è maggiore o uguale a 0
     */
    /** L'incremento da applicare al prezzo */
    private final int incremento;

    /**
     * Costruttore della classe
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
     * Metodo per calcolare il prezzo di una quotazione
     * 
     * @param prezzo il prezzo corrente della quotazione
     * @param acquisto true se si tratta di un acquisto, false se si tratta di una vendita
     * 
     * @return il prezzo (se si tratta di un acquisto, il prezzo calcolato sarà maggiore di quello corrente di un valore incremento)
     */
    @Override
    public int calcolaPrezzo(int prezzo, boolean acquisto) {
        if (acquisto) {
            return prezzo + incremento;
        }

        return prezzo;
    }
    
}
