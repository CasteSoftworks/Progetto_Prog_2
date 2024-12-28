package BorsaNova.Entita;

public class Quotazione {
    private final Azienda azienda;
    private final Borsa borsa;
    private int prezzoCorrente;

    

    /**
     * Metodo per costruire una quotazione
     * 
     * @param azienda l'azienda della quotazione
     * @param borsa la borsa della quotazione
     * 
     * @throws NullPointerException se l'azienda o la borsa sono nulle
     */
    public Quotazione(Azienda azienda, Borsa borsa){
        if(azienda==null || borsa==null){
            throw new NullPointerException("L'azienda e/o la borsa della quotazione non possono essere nulli");
        }
        this.azienda = azienda;
        this.borsa = borsa;
        this.prezzoCorrente = azienda.getPrezzoUnitarioAzione();
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
