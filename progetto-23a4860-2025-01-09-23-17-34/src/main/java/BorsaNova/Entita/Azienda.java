package BorsaNova.Entita;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
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
 * <li>Matteo Mascherpa (compagno di corso, suggerimento di stile documentativo e reminder dei modifies)</li>
 * </ul>
 */

public class Azienda implements Comparable<Azienda>{
    /**
     * AF:
     * Un'Azienda è rappresentata da:
     * - nome: il nome dell'Azienda
     * - borse: un set di Borse dove l'Azienda è quotata
     *
     * RI:
     * L'oggetto Azienda deve rispettare la seguente condizione: 
     * - nome non deve essere null, stringa vuota o composta solo da soli spazi bianchi
     * - borse non deve essere null e non deve contenere null
     */

    /** Il {@code nome} dell'Azienda */
    public final String nome;
    /** La set dei nomi delle Borse dove l'Azienda è quotata */
    private final Set<Borsa> borse = new TreeSet<>();
    /** Mappa delle aziende (key= nome azienda, value=azienda stessa) */
    private static final Set<Azienda> aziende = new TreeSet<>();

    /**
     * Metodo per o costruire un'Azienda (aggiungendola alla mappa delle Aziende) o ottenere un'Azienda già esistente
     * 
     * <p>
     * Modifies la mappa {@code aziende} se l'Azienda non esiste già
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

        Azienda az = new Azienda(nome);

        if(!aziende.contains(az)){
            aziende.add(az);
        }
        return az;
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
     * Metodo per ottenere il Iterator delle Borse dove l'Azienda è quotata
     * 
     * @return set non modificabile dei nomi delle Borse
     */
    public Iterator<Borsa> getBorseDoveQuotata(){
        return Collections.unmodifiableCollection(borse).iterator();
    }

    /**
     * Metodo per recuperare una Borsa dove l'Azienda è quotata (se lo è)
     * 
     * @param nome il nome della Borsa da cercare
     * 
     * @return la Borsa se l'Azienda è quotata, null altrimenti
     */
    protected Borsa getBorsaQuotata(String nome){
        while(getBorseDoveQuotata().hasNext()){
            Borsa b = getBorseDoveQuotata().next();
            if(b.getNome().equals(nome)){
                return b;
            }
        }
        return null;
    }

    /**
     * Metodo per recuperare una Azienda dal suo nome (se esiste)
     * 
     * @param az il nome dell'Azienda da cercare
     * 
     * @return l'Azienda se esiste, null altrimenti
     */
    public static Azienda getAziendaDaNome(String az) {
        while(getAziende().hasNext()){
            Azienda a = getAziende().next();
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
     * 
     * @throws NullPointerException se la Borsa non esiste
     * @throws IllegalArgumentException se il prezzo è minore o uguale a 0
     */
    public final void quotatiInBorsa(String borsa, int prezzo) throws IllegalArgumentException {
        if(prezzo <= 0){
            throw new IllegalArgumentException("Il prezzo di quotazione deve essere maggiore di 0");
        }

        Borsa b = Borsa.getBorsaDaNome(borsa);
        if(b==null){
            throw new NullPointerException("La borsa non esiste");
        }
        b.quotaAzienda(this.getNome(), prezzo);
    }

    /**
     * Metodo per farsi erogare un determinato numero di azioni in una Borsa
     * 
     * @param borsa il nome della Borsa in cui erogare le azioni (attraverso il nome modifica la mappa {@code azioni} di Borsa)
     * @param numeroAzioni il numero di azioni da farsi erogare
     * 
     * @throws NullPointerException se la Borsa non esiste o se modificaAzioni incorre in una NullPointerException
     * @throws IllegalArgumentException se modificaAzioni o factoryBorsa incorrono in una IllegalArgumentException
     */
    public final void erogaAzione(String borsa, int numeroAzioni) throws NullPointerException, IllegalArgumentException{
        if(borsa==null || borsa.isBlank()){
            throw new IllegalArgumentException("Il nome della borsa non può essere nullo o vuoto");
        }

        Borsa b=getBorsaQuotata(borsa);
        if(b==null){
            throw new NullPointerException("La azienda non è quotata nella borsa richiesta");
        }
        //b.modificaAzioni(this.getNome(), numeroAzioni);
    }

    /**
     * Metodo per ottenere la quotazione dell'Azienda in una Borsa
     * 
     * @param b il nome della Borsa dove cercare la quotazione
     * 
     * @return la quotazione dell'Azienda
     * 
     * @throws NullPointerException se la Azienda non è quotata nella Borsa richiesta
     */
    public Quotazione getQuotazione(String b){
        Borsa borsa = getBorsaQuotata(b);
        if(borsa==null){
            throw new NullPointerException("La Azienda non è quotata nella Borsa richiesta");
        }

        return borsa.getQuotazioneAzienda(this.getNome());
    }

    /**
     * Metodo per ottenere un iterator non modificabile dei nomi delle Aziende
     * 
     * @return la mappa dei nomi delle Aziende
     */
    public static Iterator<Azienda> getAziende(){
        return Collections.unmodifiableCollection(aziende).iterator();
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
