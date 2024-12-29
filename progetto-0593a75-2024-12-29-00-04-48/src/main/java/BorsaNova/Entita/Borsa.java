package BorsaNova.Entita;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe per rappresentare una <p>borsa</p>
 */
public class Borsa {
    /** Il nome della borsa */
    private final String nome;
    /** Mappa delle quotazioni (key= azienda, value= quotazione) */
    private final Map<Azienda, Quotazione> quotazioni = new HashMap<>();
    /** Lista delle borse */
    private static ArrayList<Borsa> borse = new ArrayList<>();
    /** Mappa delle azioni (key= azienda, value= quantità) */
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
    }

    /**
     * Metodo per aggiungere la borsa alla lista delle borse
     */
    public void aggiungiAllaLista() {
        borse.add(this);
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
     * @throws NullPointerException se l'azienda da quotare è nulla
     * @throws IllegalArgumentException se il prezzo di quotazione è minore o uguale a 0
     */
    public void quotaAzienda(Azienda azienda, int prezzo){
        if(azienda==null){
            throw new NullPointerException("L'azienda da quotare non può essere nulla");
        }

        if(quotazioni.containsKey(azienda)){
            if(prezzo<=0){
                throw new IllegalArgumentException("Il prezzo di quotazione deve essere maggiore di 0");
            }
            quotazioni.get(azienda).aggiornaPrezzo(prezzo);
        } else {
            quotazioni.put(azienda, new Quotazione(azienda, this, prezzo));
        }
    }

    /**
     * Metodo per aggiungere/rimuovere o (se non presenti) emettere le azioni di un'azienda
     * 
     * @param azienda l'azienda di cui si vogliono modificare/aggiungere le azioni
     * @param quantita la quantità di azioni aggiunegre/rimuovere/creare
     * 
     * @throws NullPointerException se l'azienda è nulla
     * @throws IllegalArgumentException se le azioni sono da rimuovere e ne vanno rimosse più di quante ne esistono in circolazione o se l'azienda non possiede azioni in questa borsa
     */
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

    /**
     * Metodo per modificare la quotazione di un'azienda
     * 
     * @param azienda l'azienda di cui si vuole modificare la quotazione
     * @param var la variazione della quotazione
     * 
     * @throws NullPointerException se l'azienda è nulla
     * @throws IllegalArgumentException se l'azienda non è quotata in questa borsa
     */
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

    /**
     * Metodo per ottenere le aziende quotate in questa borsa
     * 
     * @return le aziende quotate in questa borsa
     */
    public ArrayList<Azienda> getAziendeQuotate(){
        return new ArrayList<>(quotazioni.keySet());
    }
}
