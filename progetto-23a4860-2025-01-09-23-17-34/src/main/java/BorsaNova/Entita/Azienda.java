package BorsaNova.Entita;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Classe per rappresentare una Azienda
 * 
 * <p>
 * Una Azienda ha un nome, che la identifica univocamente.
 * Una Azienda può quotarsi in una Borsa, farsi erogare delle azioni e ottenere la quotazione in una Borsa.
 * 
 * <p>
 * Fatto con l'aiuto di:
 * <ul>
 * <li>Github Copilot - GTP4.0 (correzione errori e problemi, generazione di parte del codice e autocompletamento javadoc)</li>
 * <li>ChatGTP 4.o (correzione errori e problemi)</li>
 * <li>StackOverflow (correzione errori e problemi)</li>
 * <li>Gabriele Favazzi (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
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
     * RI(nome) = L'oggetto Azienda rispetta le seguenti condizioni:
     * - nome non è null, non è una stringa vuota o composta solo da spazi bianchi
     */

    /** Il [@code nome} dell'azienda */
    public final String nome;
    /** La set dei nomi delle borse dove l'azienda è quotata */
    private final Set<String> borse = new TreeSet<>();
    /** Mappa delle aziende (key= nome azienda, value=azienda stessa) */
    private static final Map<String, Azienda> aziende = new TreeMap<>();

    /**
     * Metodo per costruire un'azienda (aggiungendola alla lista delle aziende) o ottenere un'azienda già esistente
     * 
     * @param nome il nome dell'azienda
     *  
     * @return l'azienda costruita o, se già esistente, l'azienda con quel nome
     * 
     * @throws IllegalArgumentException se il nome dell'azienda è nullo o vuoto
     */
    public static Azienda factoryAzienda(String nome) throws IllegalArgumentException{
        if(nome==null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome dell'azienda deve essere non nullo o vuoto");
        }

        if(!aziende.containsKey(nome)){
            aziende.put(nome, new Azienda(nome));
        }
        return getAzienda(nome);
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
     * Metodo per ottenere il set dei nomi delle borse dove l'azienda è quotata
     * 
     * @return set non modificabile dei nomi delle borse dove l'azienda è quotata
     */
    public Set<String> getBorseDoveQuotata(){
        return Collections.unmodifiableSet(borse);
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
        borse.add(borsa);
        b.quotaAzienda(this, prezzo);
    }

    /**
     * Metodo per farsi erogare un determinato numero di azioni in una borsa
     * 
     * @param borsa il nome della borsa in cui erogare le azioni
     * @param numeroAzioni il numero di azioni da farsi erogare
     * 
     * @throws NullPointerException se la borsa richiesta non esiste e se modificaAzioni incorre in una NullPointerException
     * @throws IllegalArgumentException se il nome della borsa è nullo o vuoto e se modificaAzioni incorre in una IllegalArgumentException
     */
    public final void erogaAzione(String borsa, int numeroAzioni) throws NullPointerException, IllegalArgumentException{
        if(borsa==null || borsa.isBlank()){
            throw new IllegalArgumentException("Il nome della borsa non può essere nullo o vuoto");
        }
        Borsa b = Borsa.getBorsa(borsa);
        if(b==null){
            throw new NullPointerException("La borsa richiesta non esiste");
        }
        b.modificaAzioni(this, numeroAzioni);
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
