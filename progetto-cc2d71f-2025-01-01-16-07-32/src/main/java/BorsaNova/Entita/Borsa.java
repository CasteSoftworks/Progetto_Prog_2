package BorsaNova.Entita;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe per rappresentare una Borsa
 */

public class Borsa implements Comparable<Borsa>{
    /**
     * AF:
     * AF(nome) = Una borsa rappresentata da:
     * - nome: è il nome della borsa
     *
     * RI:
     * RI(nome) = L'oggetto borsa rispetta le seguenti condizioni:
     * - nome non è null, non è una stringa vuota o composta solo da spazi bianchi
     */
    
    /** Il nome della borsa */
    private final String nome;
    /** Mappa delle quotazioni (key= azienda, value= quotazione) */
    private final Map<Azienda, Quotazione> quotazioni = new HashMap<>();
    /** Lista delle borse */
    private static ArrayList<Borsa> borse = new ArrayList<>();
    /** Mappa delle azioni totali(key= azienda, value= quantità di azioni) */
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
     * Metodo per aggiungere la borsa alla lista delle borse se non è già presente
     */
    public void aggiungiAllaLista() {
        if(!borse.contains(this)){
            borse.add(this);
        }
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
     * @return le borse non modificabili
     */
    public static ArrayList<Borsa> getBorse(){
        return new ArrayList<>(Collections.unmodifiableList(borse));
    }

    /**
     * Metodo per ottenere una borsa specifica
     * 
     * @param nome il nome della borsa da ottenere
     * @return la borsa richiesta o null se non esiste
     * 
     * @throws IllegalArgumentException se il nome è nullo o composto da soli spazi bianchi
     */
    public static Borsa getBorsa(String nome){
        if(nome==null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome della borsa deve essere non nullo o vuoto");
        }

        for(Borsa b : borse){
            if(b.getNome().equals(nome)){
                return b;
            }
        }
        return null;
    }

    /**
     * Metodo per ottenere la quotazione di un'azienda
     * 
     * @param azienda l'azienda di cui si vuole ottenere la quotazione
     * 
     * @return la quotazione dell'azienda richiesta
     * 
     * @throws NullPointerException se l'azienda richiesta è nulla o ha un nome nullo o vuoto
     * @throws IllegalArgumentException se l'azienda richiesta non è quotata in questa borsa
     */
    public Quotazione getQuotazioneAzienda(Azienda azienda){

        if(azienda==null||azienda.getNome().isBlank()){
            throw new NullPointerException("L'azienda richiesta non può essere nulla o avere un nome nullo o vuoto");
        }

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
     * modifies la quotazione della azienda
     * 
     * @throws NullPointerException se l'azienda da quotare è nulla
     * @throws IllegalArgumentException se il prezzo di quotazione è minore o uguale a 0
     */
    public void quotaAzienda(Azienda azienda, int prezzo) throws NullPointerException, IllegalArgumentException{
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
     * @return true se l'operazione è andata a buon fine
     * 
     * @throws NullPointerException se l'azienda è nulla
     * @throws IllegalArgumentException se le azioni sono da rimuovere e ne vanno rimosse più di quante ne esistono in circolazione o se l'azienda non possiede azioni in questa borsa
     */
    public Boolean modificaAzioni(Azienda azienda, int quantita){
        if(azienda==null){
            throw new NullPointerException("L'azienda non può essere nulla");
        }     

        if(azioni.containsKey(azienda)){
            if(quantita<=0 && azioni.get(azienda)<Math.abs(quantita)){
                throw new IllegalArgumentException("La quantità di azioni da rimuovere non deve essere maggiore di quelle disponibili");
            }
            azioni.put(azienda, azioni.get(azienda) + quantita);
            return true;
        } else {
            if(quantita<=0){
                throw new IllegalArgumentException("Le azioni non possono essere rimosse se non esistono");
            }
            azioni.put(azienda, quantita);
            return true;
        }
    }

    /**
     * Metodo per ottenere le aziende quotate in questa borsa
     * 
     * @return lista non modificabile delle aziende quotate in questa borsa
     */
    public ArrayList<Azienda> getAziendeQuotate(){
        return new ArrayList<>(Collections.unmodifiableSet(quotazioni.keySet()));
    }

    @Override
    public int compareTo(Borsa borsa){
        return this.getNome().compareTo(borsa.getNome());
    }
}
