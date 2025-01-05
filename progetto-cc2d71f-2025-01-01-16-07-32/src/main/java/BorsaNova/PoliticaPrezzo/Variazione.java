package BorsaNova.PoliticaPrezzo;

/**
 * Classe per rappresentare una politica di variazione ad incremento-decremento
 */
public class Variazione implements Politica{
    /** La variazione da applicare al prezzo */
    private final int variazione;

    /**
     * Costruttore della classe
     * 
     * @param variazione la variazione da applicare al prezzo
     * 
     * @throws IllegalArgumentException se la variazione è minore di 0
     */
    public Variazione(int variazione) throws IllegalArgumentException {
        if(variazione < 0) {
            throw new IllegalArgumentException("La variazione deve essere maggiore di 0");
        }
        this.variazione = variazione;
    }

    /**
     * Metodo per calcolare il prezzo di una quotazione
     * 
     * @param prezzo il prezzo corrente della quotazione
     * @param acquisto true se si tratta di un acquisto, false se si tratta di una vendita
     * 
     * @return il prezzo (se si tratta di un acquisto, il prezzo calcolato sarà maggiore di quello corrente di un valore variazione altrimenti minore di un valore variazione o 1)
     */
    @Override
    public int calcolaPrezzo(int prezzo, boolean acquisto) {
        if (acquisto) {
            return prezzo + variazione;
        }
        if(prezzo - variazione <= 0) {
            return 1;
        }
        return prezzo - variazione;
    }
}
