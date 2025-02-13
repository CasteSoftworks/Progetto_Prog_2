package BorsaNova.PoliticaPrezzo;

import BorsaNova.Entita.Borsa.Azione;

//TODO descrittore
public class Vocale implements Politica{

    /** La lettera da controllare per la Politica di Vocale */
    private final char lettera;

    /**
     * Costruttore di Vocale
     * 
     * @param lettera la lettera da controllare
     */
    public Vocale(char lettera) {
        this.lettera = lettera;
    }

    /**
     * Metodo per cambiare il prezzo di una Azione in caso di vendita
     * 
     * @param prezzo il prezzo corrente della quotazione
     * @param valore non utile in questa implementazione di Politica
     * 
     * @throws IllegalArgumentException se {@code modificaPrezzo} incorre in una IllegalArgumentException
     */
    @Override
    public void cambioPrezzoVendita(Azione az, int valore) {
        if(az.getAzienda().getNome().toLowerCase().charAt(0)==Character.toLowerCase(lettera) || letteraVocale(az.getAzienda().getNome().toLowerCase().charAt(0))){
            System.err.println("\t\tLettera vocale o iniziale uguale - azienda");
            int prezzo = az.getPrezzo()/2;
            if(prezzo>0){
                System.err.println("\t\tPrezzo dimezzato");
                az.modificaPrezzo(prezzo);
            }else{
                System.err.println("\t\tPrezzo a 1");
                az.modificaPrezzo(1);
            }
        }
        if(az.getBorsa().getNome().toLowerCase().charAt(0)==Character.toLowerCase(lettera) || letteraVocale(az.getBorsa().getNome().toLowerCase().charAt(0))){
            System.err.println("\t\tLettera vocale o iniziale uguale - borsa");
            int prezzo = az.getPrezzo()/2;
            if(prezzo>0){
                System.err.println("\t\tPrezzo dimezzato");
                az.modificaPrezzo(prezzo);
            }else{
                System.err.println("\t\tPrezzo a 1");
                az.modificaPrezzo(1);
            }
        } 
    }

    /**
     * Metodo per cambiare il prezzo di una Azione in caso di acquisto
     * 
     * @param prezzo il prezzo corrente della quotazione
     * @param valore non utile in questa implementazione di Politica
     * 
     * @throws IllegalArgumentException se {@code modificaPrezzo} incorre in una IllegalArgumentException
     */
    @Override
    public void cambioPrezzoAcquisto(Azione az, int valore) {
        if(az.getAzienda().getNome().toLowerCase().charAt(0)==Character.toLowerCase(lettera) || letteraVocale(az.getAzienda().getNome().toLowerCase().charAt(0))){
            System.err.println("\t\tLettera vocale o iniziale uguale");
            az.modificaPrezzo(az.getPrezzo()*2);
            return;
        }
        if(az.getBorsa().getNome().toLowerCase().charAt(0)==Character.toLowerCase(lettera) || letteraVocale(az.getBorsa().getNome().toLowerCase().charAt(0))){
            System.err.println("\t\tLettera vocale o iniziale uguale");
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
