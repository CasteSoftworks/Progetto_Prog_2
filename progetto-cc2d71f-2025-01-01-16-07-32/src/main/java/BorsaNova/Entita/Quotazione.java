package BorsaNova.Entita;

/**
 * Classe per rappresentare una Quotazione
 */
public class Quotazione {
    /**
     * AF:
     * AF(prezzoCorrente) = Una quotazione rappresentata da:
     * - prezzoCorrente: è il prezzo corrente della quotazione
     *
     * RI:
     * RI(prezzoCorrente) = L'oggetto quotazione rispetta la seguente condizione:
     * - prezzoCorrente è maggiore o uguale a 0
     */

    /** Il prezzo corrente della quotazione */
    private int prezzoCorrente;

    /**
     * Metodo per costruire una quotazione
     * 
     * @param prezzo il prezzo corrente della quotazione
     * 
     * @throws IllegalArgumentException se il prezzo corrente della quotazione è minore o uguale a 0 
     */
    public Quotazione(int prezzo) throws IllegalArgumentException{
        if(prezzo<=0){
            throw new IllegalArgumentException("Il prezzo corrente della quotazione deve essere maggiore di 0");
        }
        this.prezzoCorrente = prezzo;
    }

    /**
     * Metodo per ottenere il prezzo corrente della quotazione
     * 
     * @return il prezzo corrente della quotazione
     */
    public int getPrezzoCorrente(){
        return prezzoCorrente;
    }

    /**
     * Metodo per aggiornare il prezzo corrente della quotazione 
     * 
     * @param var la variazione del prezzo corrente
     * 
     * modifies this.prezzoCorrente
     */
    public void aggiornaPrezzo(int var){
        prezzoCorrente = var;
    }
}
