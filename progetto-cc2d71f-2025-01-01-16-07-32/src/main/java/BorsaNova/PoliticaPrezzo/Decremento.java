package BorsaNova.PoliticaPrezzo;

/**
 * Classe per rappresentare una <p>politica di decremento</p>
 */
public class Decremento implements Politica {
    /** Il decremento da applicare al prezzo */
    private final int decremento;

    /**
     * Costruttore della classe
     * 
     * @param decremento il decremento da applicare al prezzo
     * 
     * @throws IllegalArgumentException se il decremento è minore di 0
     */
    public Decremento(int decremento) {
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
            return prezzo - decremento;
        }

        return prezzo;
    }
}
