package BorsaNova.PoliticaPrezzo;

public interface Politica {
    /**
     * Metodo per calcolare il prezzo di una quotazione
     * 
     * @param prezzo il prezzo corrente della quotazione
     * @param acquisto true se si tratta di un acquisto, false se si tratta di una vendita
     * 
     * @return il prezzo calcolato
     * 
     * @throws IllegalArgumentException se il valore che deve modificare prezzo è minore di 0
     */
    public abstract int calcolaPrezzo(int prezzo, boolean acquisto) throws IllegalArgumentException;
    
} 