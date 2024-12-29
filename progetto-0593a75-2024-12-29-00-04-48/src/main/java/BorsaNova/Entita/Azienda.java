package BorsaNova.Entita;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe per rappresentare una <p>azienda</p>
 */
public class Azienda {
    /** Il [@code nome} dell'azienda */
    public final String nome;
    /** Il [@code numeroAzioni} dell'azienda */
    private int numeroAzioni;
    /** Mappa delle aziende (key= nome azienda, value=azienda stessa) */
    private static final Map<String, Azienda> aziende = new HashMap<>();

    /**
     * Metodo per costruire un'azienda
     * 
     * @param nome il nome dell'azienda
     * @param numeroAzioni il numero di azioni dell'azienda
     *  
     * @return l'azienda costruita
     * 
     * @throws IllegalArgumentException se il nome dell'azienda è nullo o vuoto, se il numero di azioni è minore o uguale a 0, se il prezzo unitario delle azioni è minore o uguale a 0
     */
    public static Azienda factoryAzienda(String nome, int numeroAzioni){
        if(nome==null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome dell'azienda deve essere non nullo o vuoto");
        }
        
        if(numeroAzioni<=0){
            throw new IllegalArgumentException("Il numero di azioni deve essere maggiore di 0");
        }

        if(!aziende.containsKey(nome)){
            Azienda az = new Azienda(nome, numeroAzioni);
            aziende.put(nome, az);
        }
        return getAzienda(nome);
    }

    /**
     * Metodo per creare un'azienda
     * 
     * @param nome il nome dell'azienda da ottenere
     * @param numeroAzioni il numero di azioni dell'azienda da creare
     */
    public Azienda(String nome, int numeroAzioni){
        this.nome = nome;
        this.numeroAzioni = numeroAzioni;
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
     * Metodo per ottenere l'azienda dal nome
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
