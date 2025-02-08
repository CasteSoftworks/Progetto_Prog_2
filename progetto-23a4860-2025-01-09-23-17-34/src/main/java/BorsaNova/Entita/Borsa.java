package BorsaNova.Entita;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
 * <li>Fernando Gavezzotti (compagno di corso, suggerimento di usare protected)</li>
 * <li>Matteo Mascherpa (compagno di corso, suggerimento di usare protected, di stile documentativo e reminder dei modifies)</li>
 * </ul>
 */

public class Borsa implements Comparable<Borsa>{
    /**
     * AF:
     * Una Borsa è rappresentata da:
     * - nome: il nome della Borsa
     * - quotazioni: le quotazioni delle Aziende in questa Borsa
     * - azioniTotali: le azioni totali delle Aziende in questa Borsa
     * - azioniDisponibili: le azioni disponibili delle Aziende in questa Borsa
     * - politica: la politica di prezzo della Borsa
     * - azioni: le azioni totali delle Aziende quotate in Borsa
     *
     * RI:
     * L'oggetto Borsa deve rispettare la seguente condizione:
     * - nome non null, stringa vuota o composta solo da soli spazi bianchi
     * - quotazioni non deve essere null e non deve contenere null
     * - azioniTotali non deve essere null e non deve contenere null
     * - azioniDisponibili non deve essere null e non deve contenere null
     * - politica può essere null o un oggetto di tipo Politica
     * - allocazioni non deve essere null e non deve contenere null
     */
    
    /** Il {@code nome} della Borsa */
    private final String nome;
    /** Mappa delle quotazioni (key= Azienda, value= quotazione) */
    private final Map<Azienda, Quotazione> quotazioni = new TreeMap<>();
    /** Lista delle Borse */
    private static Set<Borsa> borse = new TreeSet<>();
    /** Mappa delle azioni totali(key= azienda, value= quantità di azioni totali) */
    private Map<Azienda, Integer> azioniTotali = new TreeMap<>();
    /** Mappa delle azioni disponibili (key= azienda value=quntità di azioni disponibili) */
    private Map<Azienda, Integer> azioniDisponibili = new TreeMap<>();
    /** La politica di prezzo della Borsa */
    private Politica politica;
    /** Mappa delle allocazioni delle azioni agli operatori (key=nome_operatore+" "+nome_azienda value= quantità allocata)*/
    private ArrayList<Azione> azioni = new ArrayList<>();
       
    /**
     * Metodo per costruire una Borsa (aggiungendola alla lista delle borse) o ottenere una Borsa già esistente a partire dal nome
     * 
     * <p>
     * Modifies la mappa {@code borse} se la Borsa non esiste già
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
        
        Borsa b = new Borsa(nome);
        if(!borse.contains(b)){
            borse.add(b);
        }
        return b;
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
    public static Iterator<Borsa> getBorse(){
        return Collections.unmodifiableCollection(borse).iterator();
    }

    /**
     * Metodo per ottenere una Borsa a partire dal nome
     * 
     * @param nome il nome della Borsa da ottenere
     * 
     * @return la Borsa richiesta o null se non esiste
     */
    public static Borsa getBorsaDaNome(String nome){
        for (Borsa borsa : borse) {
            if(borsa.getNome().equals(nome)){
                return borsa;
            }
        }
        return null;
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

        Azienda a = Azienda.getAziendaDaNome(azienda);
        if(quotazioni.containsKey(a)){
            return quotazioni.get(a);
        }
        return null;
    }

    /**
     * Metodo per quotare un'Azienda in questa Borsa
     * 
     * <p>
     * Protected per evitare che venga chiamato da classi esterne a Entita
     * Fatto con l'aiuto di Copilot
     * Modifies la mappa {@code quotazioni} aggiungendo o modificando la quotazione dell'Azienda
     * 
     * @param az il nome della Azienda da quotare
     * @param prezzo il prezzo di quotazione della Azienda
     * 
     * @throws IllegalArgumentException se il prezzo è minore o uguale a 0 o se l'Azienda è già quotata in questa Borsa
     */
    protected final void quotaAzienda(String az, int prezzo, int quantita) throws IllegalArgumentException{
        if(prezzo<=0){
            throw new IllegalArgumentException("Il prezzo di quotazione deve essere maggiore di 0");
        }

        Azienda a = Azienda.getAziendaDaNome(az);
        if(quotazioni.containsKey(a)){ 
            throw new IllegalArgumentException("L'azienda è già quotata in questa borsa");
        } else {
            quotazioni.put(a, new Quotazione(prezzo));
            erogaAzione(a, quantita);
        }
    }

    /**
     * Metodo per ottenere il numero di azioni totali di un'Azienda
     * 
     * @param azienda l'Azienda di cui si vuole ottenere il numero di azioni totali
     * 
     * @return il numero di azioni totali dell'Azienda richiesta o null se non ne ha
     * 
     * @throws NullPointerException se l'Azienda è null
     */
    public final Integer getNumeroAzioniTotali(String azienda){
        Azienda a = Azienda.getAziendaDaNome(azienda);
        if(a==null){
            throw new NullPointerException("L'Azienda non può essere nulla");
        }

        if(azioniTotali.containsKey(a)){
            return azioniTotali.get(a);
        }
        return null;
    }

    /**
     * Metodo per recuperare il numero di azioni disponibili di un'Azienda
     * 
     * @param azienda l'Azienda di cui si vuole ottenere il numero di azioni disponibili
     * 
     * @throws NullPointerException se l'Azienda è null
     */
    public final Integer getNumeroAzioniDisponibili(String azienda){
        Azienda a = Azienda.getAziendaDaNome(azienda);
        if(a==null){
            throw new NullPointerException("L'Azienda non può essere nulla");
        }

        if(azioniDisponibili.containsKey(a)){
            return azioniDisponibili.get(a);
        }
        return null;
    }

    /**
     * Metodo per erogare un certo numero di azioni ad un'azienda (e settare le azioni disponibili)
     * 
     * @param azienda il nome dell'Azienda a cui erogare le azioni
     * @param quantita la quantità di azioni da erogare
     * 
     * @throws NullPointerException se l'Azienda è nullo
     * @throws IllegalArgumentException se la quantità è minore o uguale a 0
     */
    private final void erogaAzione(Azienda azienda, int quantita) throws NullPointerException, IllegalArgumentException{
        if(azienda==null){
            throw new NullPointerException("L'Azienda non può essere null");
        }

        if(quantita<=0){
            throw new IllegalArgumentException("La quantità di azioni da erogare deve essere maggiore di 0");
        }

        azioniTotali.put(azienda, quantita);
        azioniDisponibili.put(azienda, quantita);
    }
    
    /**
     * Metodo per far comprare delle azioni ad un Operatore
     * 
     * @param operatore l'Operatore che compra le azioni
     * @param azienda l'Azienda di cui comprare le azioni
     * @param quantita la quantità di azioni da comprare
     * 
     * @throws NullPointerException se l'Operatore o l'Azienda sono nulli
     * @throws IllegalArgumentException se l'Azienda non è quotata o non ha azioni disponibili
     */
    protected void compraAzione(Operatore operatore, Azienda azienda, int quantita) throws NullPointerException, IllegalArgumentException{
        if(operatore==null){
            throw new NullPointerException("L'Operatore non può essere nullo");
        }
        
        if(azienda==null){
            throw new NullPointerException("L'Azienda non può essere nulla");
        }

        if(!quotazioni.containsKey(azienda)){
            throw new IllegalArgumentException("L'azienda non è quotata");
        }
        
        if(azioniDisponibili.get(azienda)==0){
            throw new IllegalArgumentException("L'azienda non ha azioni disponibili");
        }

        if(azioniDisponibili.containsKey(azienda)){
            azioniDisponibili.put(azienda, azioniDisponibili.get(azienda) - quantita);
        }
        for (Azione azione : azioni) {
            if(azione.getAzienda().equals(azienda)){
                azione.aggiungiAllocazione(operatore, quantita);
                break;
            }
        }
    }

    /**
     * Metodo per ottenere le aziende quotate in questa borsa
     * 
     * @return set non modificabile delle aziende quotate in questa borsa
     */
    public Iterator<String> getAziendeQuotate(){
        Set<String> aziendeNomi = new TreeSet<>();
        for (Azienda azienda : quotazioni.keySet()) {
            aziendeNomi.add(azienda.getNome());
        }
        return Collections.unmodifiableCollection(aziendeNomi).iterator();
    }

    /**
     * Metodo per settare una politica di prezzo per la borsa
     * 
     * <p>
     * Valore serve solo a decidere se la politica è di incremento, decremento o variazione.
     * Per decidere il valore di incremento e decremento si usano vSu e vGiu
     * Modifies {@code politica} settando la politica di prezzo
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

    public class Azione{
        /**
         * AF:
         * Una Azione è rappresentata da:
         * - azienda: la Azienda a cui è collegata la Azione
         * - quantita: la quantità di Azioni della Azienda
         * - allocazioni: le allocazioni degli Operatori
         * 
         * RI:
         * L'oggetto Azione deve rispettare la seguente condizione:
         * - azienda non può essere null
         * - quantita deve essere maggiore di 0
         * - allocazioni non deve essere null e non deve contenere null
         */

        /** La Azienda a cui è collegata la Azione */
        private final Azienda azienda;
        /** La quantità di Azioni della Azienda */
        private final int quantita;
        /** Mappa delle allocazioni degli operatori (key= Operatore, value= quantità allocata) */
        private Map<Operatore, Integer> allocazioni = new TreeMap<>();

        /**
         * Costruttore privato per creare una Azione
         * 
         * @param azienda la Azienda a cui è collegata la Azione
         * @param quantita la quantità di Azioni della Azienda
         */
        private Azione(Azienda azienda, int quantita){
            this.azienda=azienda;
            this.quantita=quantita;
        }

        /**
         * Metodo per ottenere la Azienda a cui è collegata la Azione
         * 
         * @return la Azienda a cui è collegata la Azione
         */
        public Azienda getAzienda(){
            return azienda;
        }

        /**
         * Metodo per ottenere la quantità di Azioni della Azienda
         * 
         * @return la quantità di Azioni della Azienda
         */
        public int getQuantita(){
            return quantita;
        }

        /**
         * Metodo per ottenere le allocazioni degli Operatori
         * 
         * @return la mappa delle allocazioni degli Operatori
         */
        public Map<Operatore, Integer> getAllocazioni(){
            return Collections.unmodifiableMap(allocazioni);
        }

        /**
         * Metodo aggiungere un'allocazione ad un Operatore di un certo numero di azioni di un'Azienda
         * 
         * @param operatore l'Operatore a cui allocare le azioni
         * @param quantita la quantità di azioni da allocare
         * 
         * @throws NullPointerException se l'Operatore è nullo
         * @throws IllegalArgumentException se la quantità è minore o uguale a 0
         */
        public void aggiungiAllocazione(Operatore operatore, int quantita) throws NullPointerException, IllegalArgumentException{
            if(operatore==null){
                throw new NullPointerException("L'Operatore non può essere nullo");
            }

            if(quantita<=0){
                throw new IllegalArgumentException("La quantità di azioni da allocare deve essere maggiore di 0");
            }

            if(allocazioni.containsKey(operatore)){
                allocazioni.put(operatore, allocazioni.get(operatore) + quantita);
            }else{
                allocazioni.put(operatore, quantita);
            }
        }

        /**
         * Metodo per rimuovere dalle allocazioni ad un Operatore di un certo numero di azioni di un'Azienda (se la quantità post rimozione è 0, rimuove l'allocazione)
         * 
         * @param operatore l'Operatore da cui rimuovere le azioni
         * @param quantita la quantità di azioni da rimuovere
         * 
         * @throws NullPointerException se l'Operatore è nullo
         * @throws IllegalArgumentException se la quantità è minore o uguale a 0, se la quantità da rimuovere è maggiore delle azioni allocate o se l'Operatore non ha azioni allocate
         */
        public void rimuoviDaAllocazione(Operatore operatore, int quantita) throws NullPointerException, IllegalArgumentException{
            if(operatore==null){
                throw new NullPointerException("L'Operatore non può essere nullo");
            }

            if(quantita<=0){
                throw new IllegalArgumentException("La quantità di azioni da rimuovere deve essere maggiore di 0");
            }

            if(allocazioni.containsKey(operatore)){
                if(quantita<0 && allocazioni.get(operatore)<Math.abs(quantita)){
                    throw new IllegalArgumentException("La quantità di azioni da rimuovere non deve essere maggiore di quelle allocate");
                }

                allocazioni.put(operatore, allocazioni.get(operatore) - quantita);
                if(allocazioni.get(operatore)==0){
                    allocazioni.remove(operatore);
                }
            }else{
                throw new IllegalArgumentException("L'Operatore non ha azioni allocate");
            }
        }
    }
}
