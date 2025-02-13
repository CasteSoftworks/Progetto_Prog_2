package BorsaNova.PoliticaPrezzo;

import BorsaNova.Entita.Borsa.Azione;

public class Vocale implements Politica{

    private final char lettera;

    public Vocale(char lettera) {
        this.lettera = lettera;
    }

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

    private boolean letteraVocale(char c){
        return c=='a' || c=='e' || c=='i' || c=='o' || c=='u';
    }
    
}
