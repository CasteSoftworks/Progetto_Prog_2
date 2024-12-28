package BorsaNova.Entita;

import java.util.HashMap;
import java.util.Map;

public class Azienda {
    public final String nome; //pensiamoci - il nome potrebbe variare
    private int numeroAzioni;
    private int prezzoUnitarioAzione;

    private static final Map<String, Azienda> aziende = new HashMap<>();

    /**
     * Metodo per costruire un'azienda
     * 
     * @param nome il nome dell'azienda
     * @param numeroAzioni il numero di azioni dell'azienda
     * @param prezzoUnitarioAzione il prezzo unitario di un'azione dell'azienda
     * 
     * @return l'azienda costruita
     * 
     * @throws IllegalArgumentException se il nome dell'azienda è nullo o vuoto, se il numero di azioni è minore o uguale a 0, se il prezzo unitario delle azioni è minore o uguale a 0
     */
    public static Azienda factoryAzienda(String nome, int numeroAzioni, int prezzoUnitarioAzione){
        if(nome==null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome dell'azienda deve essere non nullo o vuoto");
        }
        
        if(numeroAzioni<=0){
            throw new IllegalArgumentException("Il numero di azioni deve essere maggiore di 0");
        }

        if(prezzoUnitarioAzione<=0){
            throw new IllegalArgumentException("Il prezzo unitario delle azioni deve essere maggiore di 0");
        }

        if(!aziende.containsKey(nome)){
            Azienda az = new Azienda(nome, numeroAzioni, prezzoUnitarioAzione);
            aziende.put(nome, az);
        }
        return getAzienda(nome);
    }

    /**
     * Metodo per ottenere un'azienda
     * 
     * @param nome il nome dell'azienda da ottenere
     * 
     * @return l'azienda richiesta
     */
    public Azienda(String nome, int numeroAzioni, int prezzoUnitarioAzione){
        this.nome = nome;
        this.numeroAzioni = numeroAzioni;
        this.prezzoUnitarioAzione = prezzoUnitarioAzione; //deve scegleire l'azienda, correggi
    }

    /**
     * Metodo per ottenere il nome dell'azienda
     * 
     * @return il nome dell'azienda
     */
    public String getNome(){
        return nome;
    }

    /**
     * Metodo per ottenere il numero di azioni dell'azienda
     * 
     * @return il numero di azioni dell'azienda
     */
    public int getNumeroAzioni(){
        return numeroAzioni;
    }

    /**
     * Metodo per ottenere il prezzo unitario di un'azione dell'azienda
     * 
     * @return il prezzo unitario di un'azione dell'azienda
     */
    public int getPrezzoUnitarioAzione(){
        return prezzoUnitarioAzione;
    }

    /**
     * Metodo per ottenere3 l'azienda dal nome
     * 
     * @param nome il nome dell'azienda da ottenere
     * 
     * @return l'azienda richiesta
     */
    public static Azienda getAzienda(String nome){
        return aziende.get(nome);
    }

    /**
     * Metodo per ottenere la quotazione dell'azienda in una borsa
     * @param borsa la borsa dove cercare la quotazione
     * 
     * @return la quotazione dell'azienda
     */
    public Quotazione getQuotazione(Borsa borsa){
        return borsa.getQuotazioneAzienda(this);
    }
}
