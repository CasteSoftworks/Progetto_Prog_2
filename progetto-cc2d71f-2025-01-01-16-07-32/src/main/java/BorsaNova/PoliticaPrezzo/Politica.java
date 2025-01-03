package BorsaNova.PoliticaPrezzo;

public interface Politica {
    /**
     * Metodo per calcolare il prezzo di una quotazione
     * 
     * @param prezzo il prezzo corrente della quotazione
     * @param acquisto true se si tratta di un acquisto, false se si tratta di una vendita
     * 
     * @return il prezzo calcolato
     */
    public abstract int calcolaPrezzo(int prezzo, boolean acquisto);
    
} 