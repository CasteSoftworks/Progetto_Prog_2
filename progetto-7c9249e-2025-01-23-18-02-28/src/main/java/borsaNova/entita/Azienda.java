package borsaNova.entita;

import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Classe per rappresentare una Azienda
 * 
 * <p>
 * Una Azienda ha un nome, che la identifica univocamente.
 * Una Azienda può quotarsi in una Borsa facendosi erogare delle azioni e ottenere la sua quotazione in una Borsa.
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
 * <li>Matteo Mascherpa (compagno di corso, suggerimento di stile documentativo e reminder dei modifies)</li>
 * </ul>
 */

public class Azienda implements Comparable<Azienda>{
    /**
     * AF:
     * Un'Azienda è rappresentata da:
     * - nome: il nome dell'Azienda
     * - borse: elenco delle Borse in cui è quotata
     *
     * RI:
     * L'oggetto Azienda deve rispettare la seguente condizione: 
     * - nome non deve essere null, stringa vuota o composta solo da soli spazi bianchi
     * - borse non deve essere null e non deve contenere elementi null
     */

    /** Il {@code nome} dell'Azienda */
    private final String nome;
    /** Il SortedSet delle Borse in cui è quotata */
    private final SortedSet<Borsa> borse;

    /** SortedSet delle aziende (key= nome azienda, value=azienda stessa) */
    private static final SortedSet<Azienda> aziende = new TreeSet<>();
    /** Set dei nomi delle Aziende già usati */
    private static final SortedSet<String> NOMI_USATI = new TreeSet<>();

    /**
     * Metodo per o costruire un'Azienda (aggiungendola alla mappa delle Aziende) o ottenere un'Azienda già esistente
     * 
     * <p>
     * Modifies il set {@code aziende} e il set {@code NOMI_USATI} se l'Azienda non esiste già 
     * 
     * @param nome il nome dell'Azienda
     *  
     * @return l'Azienda costruita o, se già esistente, l'Azienda con quel nome
     * 
     * @throws NullPointerException se il nome dell'azienda è nullo
     * @throws IllegalArgumentException se il nome dell'azienda è nullo o vuoto
     */
    public static Azienda of(String nome) throws IllegalArgumentException{
        if (Objects.requireNonNull(nome, "Il nome non può essere null").isBlank()){
            throw new IllegalArgumentException("Il nome non può essere vuoto");
        }

        if (NOMI_USATI.contains(nome)) {
            return getAziendaDaNome(nome);
        }

        NOMI_USATI.add(nome);
        Azienda az = new Azienda(nome);
        aziende.add(az);
        return az;
    }

    /**
     * Costruttore privato per creare un'Azienda
     * 
     * @param nome il nome dell'Azienda da ottenere
     */
    private Azienda(String nome){
        this.nome = nome;
        this.borse = new TreeSet<>();
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
     * Metodo per recuperare una Azienda dal suo nome (se esiste)
     * 
     * @param az il nome dell'Azienda da cercare
     * 
     * @return l'Azienda se esiste, null altrimenti
     * 
     * @throws IllegalArgumentException se il nome dell'azienda è nullo o vuoto o se l'Azienda non esiste
     */
    private static Azienda getAziendaDaNome(String az) throws IllegalArgumentException{
        if(az==null || az.isBlank()){
            throw new IllegalArgumentException("Il nome dell'azienda non può essere nullo o vuoto");
        }
        
        if(!NOMI_USATI.contains(az)){
            throw new IllegalArgumentException("L'azienda non esiste");
        }

        Iterator<Azienda> it = getAziende();
        while(it.hasNext()){
            Azienda a = it.next();
            if(a.getNome().equals(az)){
                return a;
            }
        }
        return null;
    }

    /**
     * Metodo attraverso il quale una Azienda si quotata in una Borsa
     * 
     * @param borsa il nome la Borsa in cui quotarsi (attraverso il nome modifica la mappa {@code quotazioni} di Borsa)
     * @param prezzo il prezzo di quotazione
     * @param quantita il numero di azioni da quotare
     * 
     * @throws NullPointerException se la Borsa non esiste o se {@code quotaAzienda} lancia una NullPointerException
     * @throws IllegalArgumentException se il prezzo è minore o uguale a 0, se la quantità è minore o uguale a 0 o se {@code quotaAzienda} lancia una IllegalArgumentException
     */
    public final void quotatiInBorsa(Borsa borsa, int prezzo, int quantita) throws NullPointerException, IllegalArgumentException {
        if(borsa==null ){
            throw new NullPointerException("La Borsa non esiste");
        }

        if(prezzo <= 0){
            throw new IllegalArgumentException("Il prezzo di quotazione deve essere maggiore di 0");
        }

        if(quantita <= 0){
            throw new IllegalArgumentException("La quantità di Azioni da quotare deve essere maggiore di 0");
        }

        borsa.quotaAzienda(this, prezzo, quantita);

        borse.add(borsa);
    }

    /**
     * Metodo per ottenere la quotazione dell'Azienda in una Borsa
     * 
     * @param b il nome della Borsa dove cercare la quotazione
     * 
     * @return la quotazione dell'Azienda 
     * 
     * @throws NullPointerException se la Borsa non esiste o se {@code getQuotazioneAzienda} lancia una NullPointerException
     * @throws IllegalArgumentException se la Azienda non è quotata nella Borsa richiesta
     */
    public Integer getQuotazione(Borsa b) throws NullPointerException, IllegalArgumentException{
        if(b==null){
            throw new NullPointerException("La Borsa richiesta non esiste");
        }

        Integer quotazione = b.getQuotazioneAzienda(this);

        if(quotazione==null){
            throw new IllegalArgumentException("La Azienda non è quotata nella Borsa richiesta");
        }

        return quotazione;
    }

    /**
     * Metodo per ottenere un iterator non modificabile dei nomi delle Aziende
     * 
     * @return la mappa dei nomi delle Aziende
     */
    private static Iterator<Azienda> getAziende(){
        return Collections.unmodifiableCollection(aziende).iterator();
    }

    @Override
    public int compareTo(Azienda a){
        return this.getNome().compareTo(a.nome);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Azienda other)){
            return false;
        }
        return nome.equals(other.nome);
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }
    
}
