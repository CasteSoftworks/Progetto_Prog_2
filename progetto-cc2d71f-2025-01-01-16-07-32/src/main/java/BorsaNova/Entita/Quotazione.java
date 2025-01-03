package BorsaNova.Entita;

/**
 * Classe per rappresentare una <p>quotazione</p>
 */
public class Quotazione {
    /**
     * AF:
     * AF(azienda, borsa, prezzoCorrente) = Una quotazione rappresentata da:
     * - azienda: è l'azienda della quotazione
     * - borsa: è la borsa della quotazione
     * - prezzoCorrente: è il prezzo corrente della quotazione
     *
     * RI:
     * RI(azienda, borsa, prezzoCorrente) = L'oggetto quotazione rispetta le seguenti condizioni:
     * - azienda non è null e non è una stringa vuota o composta solo da spazi bianchi
     * - borsa non è null e non è una stringa vuota o composta solo da spazi bianchi
     * - prezzoCorrente è maggiore o uguale a 0
     */

    /** L'azienda della quotazione */
    private final Azienda azienda;
    /** La borsa della quotazione */
    private final Borsa borsa;
    /** Il prezzo corrente della quotazione */
    private int prezzoCorrente;

    /**
     * Metodo per costruire una quotazione
     * 
     * @param azienda l'azienda della quotazione
     * @param borsa la borsa della quotazione
     * @param prezzo il prezzo corrente della quotazione
     * 
     * @throws NullPointerException se l'azienda o la borsa sono nulle
     * @throws IllegalArgumentException se l'azienda o la borsa sono stringhe vuote o composte solo da spazi bianchi e se il prezzo è minore di 0 o nullo
     */
    public Quotazione(Azienda azienda, Borsa borsa, Integer prezzo){
        if(azienda==null || borsa==null){
            throw new NullPointerException("L'azienda e/o la borsa della quotazione non possono essere nulli");
        }
        if(azienda.getNome().isBlank() || borsa.getNome().isBlank()){
            throw new IllegalArgumentException("L'azienda e/o la borsa della quotazione non possono essere stringhe vuote o composte solo da spazi bianchi");
        }
        if(prezzo<0||prezzo==null){
            throw new IllegalArgumentException("Il prezzo della quotazione non può essere negativo o nullo");
        }
        
        this.azienda = azienda;
        this.borsa = borsa;
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
     */
    public void aggiornaPrezzo(int var){ //rifalla che non è chiara
        if(var<0 && prezzoCorrente<Math.abs(var)){
            prezzoCorrente=0;
        }
        prezzoCorrente+=var;
    }

    /**
     * Metodo per ottenere l'azienda della quotazione
     * 
     * @return l'azienda della quotazione
     */
    public Azienda getAzienda(){
        return azienda;
    }

    /**
     * Metodo per ottenere la borsa della quotazione
     * 
     * @return la borsa della quotazione
     */
    public Borsa getBorsa(){
        return borsa;
    }


}
