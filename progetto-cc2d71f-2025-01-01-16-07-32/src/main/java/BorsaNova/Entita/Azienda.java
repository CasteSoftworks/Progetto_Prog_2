package BorsaNova.Entita;

//import java.util.ArrayList;
import java.util.Collections;
//import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Classe per rappresentare una <p>azienda</p>
 */

public class Azienda implements Comparable<Azienda>{
    /**
     * AF:
     * AF(nome) = Un'azienda rappresentata da:
     * - nome: è il nome dell'azienda
     *
     * RI:
     * RI(nome,) = L'oggetto azienda rispetta le seguenti condizioni:
     * - nome non è null, non è una stringa vuota o composta solo da spazi bianchi
     */

    /** Il [@code nome} dell'azienda */
    public final String nome;
    /** Mappa delle aziende (key= nome azienda, value=azienda stessa) */
    private static final Map<String, Azienda> aziende = new TreeMap<>();

    /**
     * Metodo per costruire un'azienda
     * 
     * @param nome il nome dell'azienda
     *  
     * @return l'azienda costruita
     * 
     * @throws IllegalArgumentException se il nome dell'azienda è nullo o vuoto
     */
    public static Azienda factoryAzienda(String nome){
        if(nome==null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome dell'azienda deve essere non nullo o vuoto");
        }

        return aziende.computeIfAbsent(nome, Azienda::new);
    }

    /**
     * Metodo per creare un'azienda
     * 
     * @param nome il nome dell'azienda da ottenere
     */
    private Azienda(String nome){
        this.nome = nome;
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
     * Metodo per ottenere l'azienda dal nome
     * 
     * @param nome il nome dell'azienda da ottenere
     * 
     * @return l'azienda richiesta
     * 
     * @throws IllegalArgumentException se il nome dell'azienda è nullo o vuoto, se l'azienda richiesta non esiste
     */
    public static Azienda getAzienda(String nome){
        if(nome==null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome dell'azienda deve essere non nullo o vuoto");
        }

        if(!aziende.containsKey(nome)){
            throw new IllegalArgumentException("L'azienda richiesta non esiste");
        }

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

    /**
     * Metodo per ottenere la mappa non modificabile delle aziende
     * 
     * @return la mappa delle aziende
     */
    public static Map<String, Azienda> getAziende(){
        return Collections.unmodifiableMap(aziende);
    }

    @Override
    public int compareTo(Azienda a){
        return this.getNome().compareTo(a.nome);
    }

    @Override
    public int hashCode() {
        return this.getNome().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Azienda other)) return false;
        return this.getNome().equals(other.getNome());
    }
}
