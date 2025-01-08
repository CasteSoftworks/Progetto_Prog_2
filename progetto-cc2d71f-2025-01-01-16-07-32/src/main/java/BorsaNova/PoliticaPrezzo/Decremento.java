package BorsaNova.PoliticaPrezzo;

/**
 * Classe per rappresentare una politica di decremento
 * 
 * <p>
 * Una politica di decremento ha un valore da sottrarre al prezzo in caso di vendita.
 * Una politica di decremento può calcolare il prezzo di una quotazione in base se si tratta di una vendita, facendo attenzione a non fardiventare il prezzo <=0.
 * 
 * <p>
 * Fatto con l'aiuto di:
 * <ul>
 * <li>Github Copilot - GTP4.0</li>
 * <li>ChatGTP 4.o</li>
 * <li>StackOverflow</li>
 * <li>Gabriele Favazzi (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * <li>Simone Coccè (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * </ul>
 */
public class Decremento implements Politica {
    /**
     * AF
     * AF(decremento) = Una politica di decremento rappresentata da:
     * - decremento: è il decremento da applicare al prezzo
     * 
     * RI
     * RI(decremento) = L'oggetto politica di decremento rispetta la seguente condizione:
     * - decremento è maggiore o uguale a 0
     */
    /** Il decremento da applicare al prezzo */
    private final int decremento;

    /**
     * Costruttore della classe
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
     * Metodo per calcolare il prezzo di una quotazione
     * 
     * @param prezzo il prezzo corrente della quotazione
     * @param acquisto true se si tratta di un acquisto, false se si tratta di una vendita
     * 
     * @return il prezzo (se si tratta di una vendita, il prezzo calcolato sarà minore di quello corrente di un valore decremento)
     */
    @Override
    public int calcolaPrezzo(int prezzo, boolean acquisto) {
        if (!acquisto) {
            if(prezzo - decremento <= 0) {
                return 1;
            }
            return prezzo - decremento;
        }

        return prezzo;
    }
}
