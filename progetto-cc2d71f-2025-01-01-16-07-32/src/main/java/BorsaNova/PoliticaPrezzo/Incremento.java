package BorsaNova.PoliticaPrezzo;

/**
 * Classe per rappresentare una politica di incremento
 */
public class Incremento implements Politica {
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
