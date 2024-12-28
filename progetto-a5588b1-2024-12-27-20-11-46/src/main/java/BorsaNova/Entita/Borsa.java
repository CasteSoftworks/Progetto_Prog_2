package BorsaNova.Entita;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Borsa {
    private final String nome;
    private final Map<Azienda, Quotazione> quotazioni = new HashMap<>();
    private static ArrayList<Borsa> borse = new ArrayList<>();

    private Map<Azienda, Integer> azioni = new HashMap<>();

    /**
     * Metodo per costruire una borsa
     * 
     * @param nome il nome della borsa
     *  
     * @throws IllegalArgumentException se il nome della borsa è nullo o vuoto
     */
    public Borsa(String nome){
        if(nome==null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome della borsa deve essere non nullo o vuoto");
        }
        this.nome = nome;
        //borse.add(this);
    }

    /**
     * Metodo per ottenere il nome della borsa
     * 
     * @return il nome della borsa
     */
    public String getNome(){
        return nome;
    }

    /**
     * Metodo per ottenere le borse
     * 
     * @return le borse
     */
    public static ArrayList<Borsa> getBorse(){
        return borse;
    }

    /**
     * Metodo per ottenere la quotazione di un'azienda
     * 
     * @param azienda l'azienda di cui si vuole ottenere la quotazione
     * 
     * @return la quotazione dell'azienda richiesta
     * 
     * @throws IllegalArgumentException se l'azienda richiesta non è quotata in questa borsa
     */
    public Quotazione getQuotazioneAzienda(Azienda azienda){
        if(quotazioni.containsKey(azienda)){
            return quotazioni.get(azienda);
        }
        throw new IllegalArgumentException("L'azienda richiesta non è quotata in questa borsa");
    }

    /**
     * Metodo per quotare un'azienda
     * 
     * @param azienda l'azienda da quotare
     * @param prezzo il prezzo di quotazione dell'azienda
     * 
     * @throws IllegalArgumentException se l'azienda da quotare è nulla, se il prezzo di quotazione è minore o uguale a 0
     */
    public void quotaAzienda(Azienda azienda, int prezzo){
        if(azienda==null){
            throw new IllegalArgumentException("L'azienda da quotare non può essere nulla");
        }

        if(quotazioni.containsKey(azienda)){
            if(prezzo<=0){
                throw new IllegalArgumentException("Il prezzo di quotazione deve essere maggiore di 0");
            }
            quotazioni.get(azienda).aggiornaPrezzo(prezzo);
        } else {
            quotazioni.put(azienda, new Quotazione(azienda, this));
        }
    }

    public void modificaAzioni(Azienda azienda, int quantita){
        if(azienda==null){
            throw new NullPointerException("L'azienda non può essere nulla");
        }     

        if(azioni.containsKey(azienda)){
            if(quantita<=0 && azioni.get(azienda)<Math.abs(quantita)){
                throw new IllegalArgumentException("La quantità di azioni da rimuovere non deve essere maggiore di quelle disponibili");
            }
            azioni.put(azienda, azioni.get(azienda) + quantita);
        } else {
            if(quantita<=0){
                throw new IllegalArgumentException("Le azioni non possono essere rimosse se non esistono");
            }
            azioni.put(azienda, quantita);
        }
    }

    public void modificaQuotazione(Azienda azienda, int var){
        if(azienda==null){
            throw new NullPointerException("L'azienda non può essere nulla");
        }

        if(quotazioni.containsKey(azienda)){
            quotazioni.get(azienda).aggiornaPrezzo(var);
        } else {
            throw new IllegalArgumentException("L'azienda non è quotata in questa borsa");
        }
    }
}
