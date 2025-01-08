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
 * <li>Github Copilot - GTP4.0 (correzione errori e problemi, generazione di parte del codice e autocompletamento javadoc)</li>
 * <li>ChatGTP 4.o (correzione errori e problemi)</li>
 * <li>StackOverflow (correzione errori e problemi)</li>
 * <li>Gabriele Favazzi (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * <li>Simone Coccè (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * <li>Piero Chobanyan (compagno di corso, logica iniziale)</li>
 * <li>Fernando Gavezzotti (compagno di corso, suggerimento di usare protected)</li>
 * <li>Matteo Mascherpa (compagno di corso, suggerimento di usare protected e di stile documentativo)</li>
 * </ul>
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
    private final Map<Azienda, Quotazione> quotazioni = new TreeMap<>();
    /** Lista delle borse */
    private static Map<String, Borsa> borse = new TreeMap<>();
    /** Mappa delle azioni disponibili(key= azienda, value= quantità di azioni) */
    private Map<Azienda, Integer> azioni = new TreeMap<>();
    /** La politica di prezzo della borsa */
    private Politica politica;
    /** Mappa delle allocazioni delle azioni agli operatori (key=nome_operatore+" "+nome_azienda value= quantità allocata)*/
    private Map<String, Integer> allocazioni = new TreeMap<>();
       
    /**
     * Metodo per costruire una borsa
     * 
     * @param nome il nome della borsa
     * 
     * @return la borsa costruita
     * 
     * @throws IllegalArgumentException se il nome della borsa è nullo o vuoto
     */
    public static Borsa factoryBorsa(String nome) throws IllegalArgumentException{
        if(nome==null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome della borsa deve essere non nullo o vuoto");
        }
        return borse.computeIfAbsent(nome, n -> new Borsa(nome));
    }

    /**
     * Metodo per creare una borsa
     * 
     * @param nome il nome della borsa
     */
    private Borsa(String nome){
        this.nome = nome;
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
    public static Set<Borsa> getBorse(){
        return Collections.unmodifiableSet(new TreeSet<>(borse.values()));
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

        return borse.get(nome);
    }

    /**
     * Metodo per ottenere la quotazione di un'azienda
     * 
     * @param azienda l'azienda di cui si vuole ottenere la quotazione
     * 
     * @return la quotazione dell'azienda richiesta o null se non esiste
     * 
     * @throws NullPointerException se l'azienda richiesta è nulla o ha un nome nullo o vuoto
     */
    public Quotazione getQuotazioneAzienda(Azienda azienda){

        if(azienda==null||azienda.getNome().isBlank()){
            throw new NullPointerException("L'azienda richiesta non può essere nulla o avere un nome nullo o vuoto");
        }

        if(quotazioni.containsKey(azienda)){
            return quotazioni.get(azienda);
        }
        return null;
    }

    /**
     * Metodo per quotare un'azienda (svolto con l'aiuto di Copilot)
     * 
     * <p>
     * Protected per evitare che venga chiamato da classi esterne a Entita
     * 
     * @param azienda l'azienda da quotare
     * @param prezzo il prezzo di quotazione dell'azienda
     * 
     * modifies la quotazione della azienda
     * 
     * @throws IllegalArgumentException se il prezzo passato a Quotazione è minore o uguale a 0
     */
    protected final void quotaAzienda(Azienda azienda, int prezzo) throws IllegalArgumentException{
        if(quotazioni.containsKey(azienda)){ 
            quotazioni.get(azienda).aggiornaPrezzo(prezzo);
        } else {
            quotazioni.put(azienda, new Quotazione(prezzo));
        }
    }

    /**
     * Metodo per ottenere il numero di azioni di un'azienda
     * 
     * @param azienda l'azienda di cui si vuole ottenere il numero di azioni
     * 
     * @return il numero di azioni dell'azienda richiesta o null se non esiste
     */
    public final Integer getNumeroAzioni(Azienda azienda){
        if(azioni.containsKey(azienda)){
            return azioni.get(azienda);
        }
        return null;
    }

    /**
     * Metodo per aggiungere/rimuovere o (se non presenti) emettere le azioni di un'azienda (svolto con l'aiuto di Copilot)
     * 
     * <p>
     * Protected per evitare che venga chiamato da classi esterne a Entita
     * 
     * @param azienda l'azienda di cui si vogliono modificare/aggiungere le azioni
     * @param quantita la quantità di azioni aggiunegre/rimuovere/creare
     *  
     * @throws NullPointerException se l'azienda è nulla, se le azioni sono nulle o se le quotazioni sono nulle
     * @throws IllegalArgumentException se quantità è pari a 0, se le azioni sono da rimuovere e ne vanno rimosse più di quante ne esistono in circolazione o se l'azienda non possiede azioni in questa borsa
     */
    protected final void modificaAzioni(Azienda azienda, int quantita) throws NullPointerException, IllegalArgumentException{
        if(azienda==null){
            throw new NullPointerException("L'azienda non può essere nulla");
        }
        
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
                quotazioni.get(azienda).aggiornaPrezzo(politica.calcolaPrezzo(quotazioni.get(azienda).getPrezzoCorrente(), quantita<0));
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
     * Metodo per ottenere le allocazioni delle azioni agli operatori
     * 
     * @return la mappa delle allocazioni delle azioni agli operatori
     */
    public final Map<String, Integer> getAllocazioni(){
        return Collections.unmodifiableMap(allocazioni);
    }

    /**
     * Metodo per allocare ad un operatore un certo numero di azioni di un'azienda
     * 
     * @param operatore il nome dell'operatore a cui allocare le azioni
     * @param azienda il nome dell'azienda di cui allocare le azioni
     * @param quantita la quantità di azioni da allocare (anche rimuovere se l'operatore sta vendendo)
     * 
     * @throws IllegalArgumentException se l'azienda non è quotata in questa borsa, se l'operatore è nullo o vuoto, se l'azienda è nulla o vuota, se la quantità è 0 o se la quantità da rimuovere è maggiore delle azioni possedute
     */
    public final void allocaAzione(String operatore, String azienda, int quantita) throws IllegalArgumentException{
        if(!quotazioni.containsKey(Azienda.factoryAzienda(azienda))){
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
    public Set<Azienda> getAziendeQuotate(){
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
