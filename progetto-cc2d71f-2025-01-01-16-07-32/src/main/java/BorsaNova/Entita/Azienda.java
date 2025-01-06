package BorsaNova.Entita;

//import java.util.ArrayList;
import java.util.Collections;
//import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Classe per rappresentare una Azienda
 * <br>
 * Fatto con l'aiuto di:
 * <ul>
 * <li>Github Copilot -GTP4.0</li>
 * <li>Chat GTP4.o</li>
 * <li>StackOverflow</li>
 * <li>Gabriele Favizzi (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * <li>Simone Coccè (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * <li>Piero Chobanyan (compagno di corso, logica iniziale)</li>
 * </ul>
 */

public class Azienda implements Comparable<Azienda>{
    /**
     * AF:
     * AF(nome) = Un'azienda rappresentata da:
     * - nome: è il nome dell'azienda
     *
     * RI:
     * RI(nome) = L'oggetto azienda rispetta le seguenti condizioni:
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
    public static Azienda factoryAzienda(String nome) throws IllegalArgumentException{
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
     * Metodo per ottenere una azienda dal nome
     * 
     * @param nome il nome dell'azienda da ottenere
     * 
     * @return l'azienda richiesta
     * 
     * @throws IllegalArgumentException se il nome dell'azienda è nullo o vuoto, se l'azienda richiesta non esiste
     */
    public static Azienda getAzienda(String nome) throws IllegalArgumentException{
        if(nome==null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome dell'azienda deve essere non nullo o vuoto");
        }

        if(!aziende.containsKey(nome)){
            throw new IllegalArgumentException("L'azienda richiesta non esiste");
        }

        return aziende.get(nome);
    }

    /**
     * Metodo attraverso il quale una azieda si quotata in una borsa
     * 
     * @param borsa la borsa in cui quotarsi
     * @param prezzo il prezzo di quotazione
     * 
     * @throws IllegalArgumentException se il nome della borsa è nullo o vuoto, se il prezzo è minore o uguale a 0
     * @throws NullPointerException se la borsa richiesta non esiste
     */
    public final void quotatiInBorsa(String borsa, int prezzo) throws IllegalArgumentException, NullPointerException{
        if(borsa==null || borsa.isBlank()){
            throw new IllegalArgumentException("Il nome della borsa non può essere nullo o vuoto");
        }

        if(prezzo<=0){
            throw new IllegalArgumentException("Il prezzo di quotazione deve essere maggiore di 0");
        }

        Borsa b = Borsa.getBorsa(borsa);
        if(b==null){
            throw new NullPointerException("La borsa richiesta non esiste");
        }
        b.quotaAzienda(this, prezzo);
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

    /**
     * Metodo override per confrontare due aziende in base al loro nome
     * 
     * @param a l'azienda con cui confrontare
     * 
     * @return 0 se le aziende sono uguali, un numero negativo se l'azienda è minore di a, un numero positivo altrimenti
     */
    @Override
    public int compareTo(Azienda a){
        return this.getNome().compareTo(a.nome);
    }
}
