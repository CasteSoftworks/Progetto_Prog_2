package BorsaNova.PoliticaPrezzo;

import BorsaNova.Entita.Borsa.Azione;

public class Soglia implements Politica{

    private final int soglia;

    public Soglia(int soglia) {
        if(soglia < 0) {
            throw new IllegalArgumentException("La soglia deve essere maggiore di 0");
        }
        this.soglia = soglia;
    }

    @Override
    public void cambioPrezzoVendita(Azione az, int valore) {
        if(valore>soglia){
            int prezzo = az.getPrezzo()/2;
            if(prezzo>0){
                az.modificaPrezzo(prezzo);
            }
        } 
    }

    @Override
    public void cambioPrezzoAcquisto(Azione az, int valore) {
        if(valore>soglia){
            az.modificaPrezzo(az.getPrezzo()*2);
        }
    }
    
}
