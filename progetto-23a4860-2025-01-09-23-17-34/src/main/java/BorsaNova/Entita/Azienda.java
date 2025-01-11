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
 * <li>Github Copilot - GTP4.0 (correzione errori e problemi, generazione di parte del codice e autocompletamento javadoc [revisionato e corretto poi a mano])</li>
 * <li>ChatGTP 4.o (correzione errori e problemi)</li>
 * <li>StackOverflow (correzione errori e problemi)</li>
 * <li>Gabriele Favazzi (compagno di corso, aiuto sulla formalità della documentazione e del codice, suggerimento sul prestare attenzione ai metodi poco sicuri)</li>
 * <li>Simone Coccè (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * <li>Piero Chobanyan (compagno di corso, logica iniziale)</li>
 * </ul>
 */

public class Azienda implements Comparable<Azienda>{
    /**
     * AF:
     * Un'Azienda è rappresentata da un nome
     *
     * RI:
     * L'oggetto Azienda deve rispettare la seguente condizione: nome non deve essere null, stringa vuota o composta solo da soli spazi bianchi
     */

    /** Il {@code nome} dell'Azienda */
    public final String nome;
    /** La set dei nomi delle Borse dove l'Azienda è quotata */
    private final Set<String> borse = new TreeSet<>();
    /** Mappa delle aziende (key= nome azienda, value=azienda stessa) */
    private static final Map<String, Azienda> aziende = new TreeMap<>();

    /**
     * Metodo per o costruire un'Azienda (aggiungendola alla mappa delle Aziende) o ottenere un'Azienda già esistente
     * 
     * @param nome il nome dell'Azienda
     *  
     * @return l'Azienda costruita o, se già esistente, l'Azienda con quel nome
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
        return aziende.get(nome);
    }

    /**
     * Costruttore privato per creare un'Azienda
     * 
     * @param nome il nome dell'Azienda da ottenere
     */
    private Azienda(String nome){
        this.nome = nome;
    }

    /**
     * Metodo per ottenere il nome dell'Azienda
     * 
     * @return il nome dell'Azienda
     */
    public String getNome(){
        return nome;
    }

    /**
     * Metodo per ottenere il set dei nomi delle Borse dove l'Azienda è quotata
     * 
     * @return set non modificabile dei nomi delle Borse
     */
    public Set<String> getBorseDoveQuotata(){
        return Collections.unmodifiableSet(borse);
    }

    /**
     * Metodo attraverso il quale una Azienda si quotata in una Borsa
     * 
     * @param borsa il nome la borsa in cui quotarsi
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

        Borsa b=Borsa.factoryBorsa(borsa);
        if(b==null){
            throw new NullPointerException("La borsa richiesta non esiste");
        }
        borse.add(borsa);
        b.quotaAzienda(this.getNome(), prezzo);
    }

    /**
     * Metodo per farsi erogare un determinato numero di azioni in una Borsa
     * 
     * @param borsa il nome della Borsa in cui erogare le azioni
     * @param numeroAzioni il numero di azioni da farsi erogare
     * 
     * @throws NullPointerException se la Borsa richiesta non esiste e se modificaAzioni incorre in una NullPointerException
     * @throws IllegalArgumentException se il nome della Borsa è nullo o vuoto e se modificaAzioni incorre in una IllegalArgumentException
     */
    public final void erogaAzione(String borsa, int numeroAzioni) throws NullPointerException, IllegalArgumentException{
        if(borsa==null || borsa.isBlank()){
            throw new IllegalArgumentException("Il nome della borsa non può essere nullo o vuoto");
        }
        Borsa b=Borsa.factoryBorsa(borsa);
        if(b==null){
            throw new NullPointerException("La borsa richiesta non esiste");
        }
        b.modificaAzioni(this.getNome(), numeroAzioni);
    }

    /**
     * Metodo per ottenere la quotazione dell'Azienda in una Borsa
     * @param borsa il nome della Borsa dove cercare la quotazione
     * 
     * @return la quotazione dell'Azienda
     */
    public Quotazione getQuotazione(String b){
        Borsa borsa = Borsa.factoryBorsa(b);
        return borsa.getQuotazioneAzienda(this.getNome());
    }

    /**
     * Metodo per ottenere un set non modificabile dei nomi delle Aziende
     * 
     * @return la mappa dei nomi delle Aziende
     */
    public static Set<String> getAziende(){
        return Collections.unmodifiableSet(aziende.keySet());
    }

    /**
     * Metodo override per confrontare due Aziende in base al loro nome
     * 
     * <p>
     * Automaticamente generato da Github Copilot sulla base di compareTo scritto in Borsa
     * 
     * @param a l'Azienda con cui confrontare
     * 
     * @return 0 se le Aziende sono uguali, un numero negativo se l'Azienda è minore di a, un numero positivo altrimenti
     */
    @Override
    public int compareTo(Azienda a){
        return this.getNome().compareTo(a.nome);
    }
}
