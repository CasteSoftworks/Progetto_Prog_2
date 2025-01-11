package BorsaNova.Entita;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import BorsaNova.PoliticaPrezzo.*;

/**
 * Classe per rappresentare una Borsa
 * 
 * <p>
 * Una Borsa ha un nome (che la identifica univocamente) delle quotazioni, delle azioni, una politica di prezzo e delle allocazioni di azioni agli operatori
 * Una Borsa può quotare un'azienda (per procura di quest'ultima), aggiungere/rimuovere/creare azioni di un'azienda, allocare azioni ad un operatore e settare una politica di prezzo
 * <p> 
 * Fatto con l'aiuto di:
 * <ul>
 * <li>Github Copilot - GTP4.0 (correzione errori e problemi, generazione di parte del codice e autocompletamento javadoc [revisionato e corretto poi a mano])</li>
 * <li>ChatGTP 4.o (correzione errori e problemi)</li>
 * <li>StackOverflow (correzione errori e problemi)</li>
 * <li>Gabriele Favazzi (compagno di corso, aiuto sulla formalità della documentazione e del codice, suggerimento sul prestare attenzione ai metodi poco sicuri)</li>
 * <li>Simone Coccè (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * <li>Piero Chobanyan (compagno di corso, logica iniziale)</li>
 * <li>Fernando Gavezzotti (compagno di corso, suggerimento di usare protected)</li>
 * <li>Matteo Mascherpa (compagno di corso, suggerimento di usare protected e di stile documentativo)</li>
 * </ul>
 */

public class Borsa implements Comparable<Borsa>{
    /**
     * AF:
     * Una Borsa è rappresentata da un nome
     *
     * RI:
     * L'oggetto Borsa deve rispettare la seguente condizione: nome non null, stringa vuota o composta solo da soli spazi bianchi
     */
    
    /** Il {@code nome} della Borsa */
    private final String nome;
    /** Mappa delle quotazioni (key= nome_azienda, value= quotazione) */
    private final Map<String, Quotazione> quotazioni = new TreeMap<>();
    /** Lista delle Borse */
    private static Map<String, Borsa> borse = new TreeMap<>();
    /** Mappa delle azioni disponibili(key= azienda, value= quantità di azioni) */
    private Map<Azienda, Integer> azioni = new TreeMap<>();
    /** La politica di prezzo della Borsa */
    private Politica politica;
    /** Mappa delle allocazioni delle azioni agli operatori (key=nome_operatore+" "+nome_azienda value= quantità allocata)*/
    private Map<String, Integer> allocazioni = new TreeMap<>();
       
    /**
     * Metodo per costruire una Borsa (aggiungendola alla lista delle borse) o ottenere una Borsa già esistente a partire dal nome
     * 
     * @param nome il nome della Borsa
     * 
     * @return la Borsa richiesta
     * 
     * @throws IllegalArgumentException se il nome della Borsa è nullo o vuoto
     */
    public static Borsa factoryBorsa(String nome) throws IllegalArgumentException{
        if(nome==null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome della borsa deve essere non nullo o vuoto");
        }
        
        if(!borse.containsKey(nome)){
            borse.put(nome, new Borsa(nome));
        }
        return borse.get(nome);
    }

    /**
     * Costruttore privato per creare Borsa
     * 
     * @param nome il nome della Borsa
     */
    private Borsa(String nome){
        this.nome = nome;
    }

    /**
     * Metodo per ottenere il nome della Borsa
     * 
     * @return il nome della Borsa
     */
    public String getNome(){
        return nome;
    }

    /**
     * Metodo per ottenere tutte le Borse create
     * 
     * @return un set non modificabile di nomi di Borse 
     */
    public static Set<Borsa> getBorse(){
        return Collections.unmodifiableSet(new TreeSet<>(borse.values()));
    }

    /**
     * Metodo per ottenere la quotazione di un'Azienda
     * 
     * @param azienda il nome della Azienda di cui si vuole ottenere la quotazione
     * 
     * @return la quotazione dell'Azienda richiesta o null se non esiste
     * 
     * @throws IllegalArgumentException se l'Azienda richiesta è nulla o ha un nome nullo o vuoto
     */
    public Quotazione getQuotazioneAzienda(String azienda){

        if(azienda==null||azienda.isBlank()){
            throw new IllegalArgumentException("Il nome della Azienda non può essere nullo, vuoto o composto solo da spazi bianchi");
        }

        if(quotazioni.containsKey(azienda)){
            return quotazioni.get(azienda);
        }
        return null;
    }

    /**
     * Metodo per quotare un'Azienda in questa Borsa
     * 
     * <p>
     * Protected per evitare che venga chiamato da classi esterne a Entita
     * Fatto con l'aiuto di Copilot
     * 
     * @param azienda l'azienda da quotare
     * @param prezzo il prezzo di quotazione dell'azienda
     * 
     * modifies la quotazione della azienda
     * 
     * @throws IllegalArgumentException se il prezzo è minore o uguale a 0
     */
    protected final void quotaAzienda(String az, int prezzo) throws IllegalArgumentException{
        if(prezzo<=0){
            throw new IllegalArgumentException("Il prezzo di quotazione deve essere maggiore di 0");
        }

        if(quotazioni.containsKey(az)){ 
            quotazioni.get(az).aggiornaPrezzo(prezzo);
        } else {
            quotazioni.put(az, new Quotazione(prezzo));
        }
    }

    /**
     * Metodo per ottenere il numero di azioni di un'azienda
     * 
     * @param azienda l'azienda di cui si vuole ottenere il numero di azioni
     * 
     * @return il numero di azioni dell'azienda richiesta o null se non esiste
     */
    public final Integer getNumeroAzioni(String azienda){
        Azienda a = Azienda.factoryAzienda(azienda);
        if(azioni.containsKey(a)){
            return azioni.get(a);
        }
        return null;
    }

    /**
     * Metodo per aggiungere/rimuovere o (se non presenti) emettere le azioni di un'azienda (svolto con l'aiuto di Copilot)
     * 
     * <p>
     * Protected per evitare che venga chiamato da classi esterne a Entita
     * 
     * @param azienda l'Azienda di cui si vogliono modificare/aggiungere le azioni
     * @param quantita la quantità di azioni aggiunegre/rimuovere/creare
     *  
     * @throws NullPointerException se le azioni sono nulle o se le quotazioni sono nulle
     * @throws IllegalArgumentException se quantità è pari a 0, se le azioni sono da rimuovere e ne vanno rimosse più di quante ne esistono in circolazione, se l'Azienda non possiede azioni in questa borsa o se factoryAzienda incorre in una IllegalArgumentException
     */
    protected final void modificaAzioni(String a, int quantita) throws NullPointerException, IllegalArgumentException{
        Azienda azienda = Azienda.factoryAzienda(a);
        
        if(azioni==null){
            throw new NullPointerException("Le azioni non possono essere nulle");
        }

        if(quotazioni==null){
            throw new NullPointerException("Le quotazioni non possono essere nulle");
        }

        if(quantita==0){
            throw new IllegalArgumentException("La quantità di azioni da aggiungere/rimuovere non può essere 0");
        }

        if(azioni.containsKey(azienda)){
            if(quantita<0 && azioni.get(azienda)<Math.abs(quantita)){
                throw new IllegalArgumentException("La quantità di azioni da rimuovere non deve essere maggiore di quelle disponibili");
            }
            if(politica!=null){
                //se compro azioni sto sottraendo quantità, se vendo sto aggiungendo, quindi per fare in modo che quando compro arrivi <code>true</code> e quando vendo <code>false</code> inverto il check di quantità per tare true qwuando <0 e false altrimenti
                quotazioni.get(a).aggiornaPrezzo(politica.calcolaPrezzo(quotazioni.get(a).getPrezzoCorrente(), quantita<0));
            }
            azioni.put(azienda, azioni.get(azienda) + quantita);
        } else {
            if(quantita<0){
                throw new IllegalArgumentException("Le azioni non possono essere rimosse se non esistono");
            }
            azioni.put(azienda, quantita);
        }
    }

    /**
     * Metodo per ottenere le allocazioni delle azioni agli Operatori
     * 
     * @return la mappa delle allocazioni delle azioni agli Operatori
     */
    public final Map<String, Integer> getAllocazioni(){
        return Collections.unmodifiableMap(allocazioni);
    }

    /**
     * Metodo per allocare ad un Operatore un certo numero di azioni di un'Azienda
     * 
     * <p>
     * Protected per evitare che venga chiamato da classi esterne a Entita
     * 
     * @param operatore il nome dell'Operatore a cui allocare le azioni
     * @param azienda il nome dell'Azienda di cui allocare le azioni
     * @param quantita la quantità di azioni da allocare (anche rimuovere se l'Operatore sta vendendo)
     * 
     * @throws IllegalArgumentException se l'Azienda non è quotata in questa borsa, se l'Operatore è nullo o vuoto, se l'Azienda è nulla o vuota, se la quantità è 0 o se la quantità da rimuovere è maggiore delle azioni possedute
     */
    protected final void allocaAzione(String operatore, String azienda, int quantita) throws IllegalArgumentException{
        if(!quotazioni.containsKey(azienda)){
            throw new IllegalArgumentException("L'azienda non è quotata in questa borsa");
        }

        if(operatore==null||operatore.isBlank()){
            throw new IllegalArgumentException("L'operatore non può essere nullo o vuoto");
        }

        if(azienda==null||azienda.isBlank()){
            throw new IllegalArgumentException("L'azienda non può essere nulla o vuota");
        }

        if(quantita==0){
            throw new IllegalArgumentException("La quantità di azioni da allocare non può essere 0");
        }

        String key=operatore+" "+azienda;

        if(quantita<0 && allocazioni.get(key)<Math.abs(quantita)){
            throw new IllegalArgumentException("La quantità di azioni da rimuovere non deve essere maggiore di quelle possedute");
        }

        allocazioni.put(key, allocazioni.getOrDefault(key, 0) + quantita);
        if(allocazioni.get(key)==0){
            allocazioni.remove(key);
        }
    }

    /**
     * Metodo per ottenere le aziende quotate in questa borsa
     * 
     * @return set non modificabile delle aziende quotate in questa borsa
     */
    public Set<String> getAziendeQuotate(){
        return Collections.unmodifiableSet(quotazioni.keySet());
    }

    /**
     * Metodo per settare una politica di prezzo per la borsa
     * 
     * <p>
     * Valore serve solo a decidere se la politica è di incremento, decremento o variazione.
     * Per decidere il valore di incremento e decremento si usano vSu e vGiu
     * 
     * <ul>
     * <li> se il valore è positivo, la politica è ad incremento costante pari a vSu</li>
     * <li> se il valore è negativo, la politica è a decremento costante pari al valore assoluto di vGiu</li>
     * <li> se il valore è 0, la politica è di variazione (incremento e decremento a seconda) di vSu e vGiu</li>
     * </ul>
     * 
     * @param valore il valore della politica di prezzo
     * @param vSu il valore di variazione in caso di acquisto
     * @param vGiu il valore di variazione in caso di vendita
     * 
     * @throws IllegalArgumentException se la creazione della politica fallisce
     */
    public final void setPoliticaPrezzo(int valore, int vSu, int vGiu) throws IllegalArgumentException{
        if(valore>0){
            politica= new Incremento(vSu);
        }else if(valore<0){
            politica= new Decremento(Math.abs(vGiu));
        }else{
            politica= new Variazione(vSu,vGiu);
        }
    }

    /**
     * Metodo per confrontare due borse in base al loro nome
     * 
     * @param borsa la borsa con cui confrontare
     * 
     * @return 0 se le borse sono uguali, un numero negativo se la borsa è minore di borsa, un numero positivo altrimenti
     */
    @Override
    public int compareTo(Borsa borsa){
        return this.getNome().compareTo(borsa.getNome());
    }
}
