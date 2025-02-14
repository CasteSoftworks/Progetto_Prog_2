package borsaNova.politicaPrezzo;

import borsaNova.entita.Borsa.Azione;

/**
 * Classe per rappresentare una Politica di Vocale
 * 
 * <p>
 * Una Politica di Vocale ha una lettera da controllare per applicare una modifica al prezzo.
 * Una Politica di Vocale raddoppia il prezzo di un'Azione in caso di acquisto di un'Azione di un'Azienda il cui nome (o quello della Borsa associata) inizia con la lettera specificata o una vocale e dimezza il prezzo in caso di vendita (seguiendo la medesima condizione dell'acquisto).
 * 
 * <p>
 * Fatto con l'aiuto di:
 * <ul>
 * <li>Github Copilot - GTP4.0 (correzione errori e problemi e autocompletamento javadoc [revisionato e corretto poi a mano])</li>
 * </ul>
 */
public class Vocale implements Politica{
    /**
     * AF
     * Una Politica di Vocale rappresentata da una lettera da controllare per l'applicazione della Politica di Vocale
     *  
     * RI
     * L'oggetto Vocale rispetta la seguente condizione: lettera non nulla
     */

    /** La lettera da controllare per la Politica di Vocale */
    private final char lettera;

    /**
     * Costruttore di Vocale
     * 
     * @param lettera la lettera da controllare
     * 
     * @throws IllegalArgumentException se la lettera è nulla
     */
    public Vocale(char lettera) throws IllegalArgumentException{
        if(lettera == ' '){
            throw new IllegalArgumentException("La lettera non può essere nulla");
        }

        this.lettera = lettera;
    }

    /**
     * Metodo per cambiare il prezzo di una Azione in caso di vendita
     * 
     * @param az l'Azione da modificare
     * @param valore non utile in questa implementazione di Politica
     * 
     * @throws IllegalArgumentException se {@code modificaPrezzo} incorre in una IllegalArgumentException
     */
    @Override
    public void cambioPrezzoVendita(Azione az, int valore) {
        if(az.getAzienda().getNome().toLowerCase().charAt(0)==Character.toLowerCase(lettera) || letteraVocale(az.getAzienda().getNome().toLowerCase().charAt(0))){
            int prezzo = az.getPrezzo()/2;
            if(prezzo>0){
                az.modificaPrezzo(prezzo);
            }else{
                az.modificaPrezzo(1);
            }
        }
        if(az.getBorsa().getNome().toLowerCase().charAt(0)==Character.toLowerCase(lettera) || letteraVocale(az.getBorsa().getNome().toLowerCase().charAt(0))){
            int prezzo = az.getPrezzo()/2;
            if(prezzo>0){
                az.modificaPrezzo(prezzo);
            }else{
                az.modificaPrezzo(1);
            }
        } 
    }

    /**
     * Metodo per cambiare il prezzo di una Azione in caso di acquisto
     * 
     * @param az l'Azione da modificare
     * @param valore non utile in questa implementazione di Politica
     * 
     * @throws IllegalArgumentException se {@code modificaPrezzo} incorre in una IllegalArgumentException
     */
    @Override
    public void cambioPrezzoAcquisto(Azione az, int valore) {
        if(az.getAzienda().getNome().toLowerCase().charAt(0)==Character.toLowerCase(lettera) || letteraVocale(az.getAzienda().getNome().toLowerCase().charAt(0))){
            az.modificaPrezzo(az.getPrezzo()*2);
            return;
        }
        if(az.getBorsa().getNome().toLowerCase().charAt(0)==Character.toLowerCase(lettera) || letteraVocale(az.getBorsa().getNome().toLowerCase().charAt(0))){
            az.modificaPrezzo(az.getPrezzo()*2);
            return;
        }
    }

    /**
     * Metodo per controllare se un carattere è una vocale
     * 
     * @param c il carattere da controllare
     * 
     * @return true se il carattere è una vocale, false altrimenti
     */
    private boolean letteraVocale(char c){
        return c=='a' || c=='e' || c=='i' || c=='o' || c=='u';
    }

}
