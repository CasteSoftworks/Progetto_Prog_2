package BorsaNova.Entita;

import java.util.Collections;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.SortedSet;
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
     * - politica: la politica di prezzo della Borsa
     * - azioni: un set di Azioni della Borsa
     *
     * RI:
     * L'oggetto Borsa deve rispettare la seguente condizione:
     * - nome non null, stringa vuota o composta solo da soli spazi bianchi
     * - politica può essere null o un oggetto di tipo Politica
     * - azioni non deve essere null e non deve contenere null
     */
    
    /** Il {@code nome} della Borsa */
    private final String nome;
    /** Set delle Aziende in Borsa */
    private SortedSet<Azienda> aziendeInBorsa = new TreeSet<>();

    /** La politica di prezzo della Borsa */
    private Politica politica;
    /** SortedSet di Azioni*/
    private SortedSet<Azione> azioni = new TreeSet<>();
    /** SortedSet di Allocazioni */
    private SortedSet<Allocazione> allocazioni = new TreeSet<>();

    /** SortedSet dei nomi */
    private static SortedSet<String> NOMI_USATI = new TreeSet<>();
    /** Set delle Borse */
    private static SortedSet<Borsa> borse = new TreeSet<>();
       
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
     * @throws NullPointerException se il nome della Borsa è nullo
     * @throws IllegalArgumentException se il nome della Borsa è vuoto o se il nome è già usato
     */
    public static Borsa of(String nome) throws NullPointerException, IllegalArgumentException{
        if (Objects.requireNonNull(nome, "Name must not be null.").isBlank()){
            throw new IllegalArgumentException("Name must not be empty.");
        }
        if (NOMI_USATI.contains(nome)) {
            return getBorsaDaNome(nome);
        }

        NOMI_USATI.add(nome);
        Borsa bo = new Borsa(nome);
        borse.add(bo);
        return bo;
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
    public static SortedSet<Borsa> getBorse(){
        return Collections.unmodifiableSortedSet(borse);
    }

    /**
     * Metodo per ottenere una Borsa a partire dal nome
     * 
     * @param nome il nome della Borsa da ottenere
     * 
     * @return la Borsa richiesta o null se non esiste
     */
    public static Borsa getBorsaDaNome(String nome){
        if(nome==null||nome.isBlank()){
            throw new IllegalArgumentException("Il nome della Borsa non può essere nullo, vuoto o composto solo da spazi bianchi");
        }
        if(!NOMI_USATI.contains(nome)){
            throw new IllegalArgumentException("La Borsa richiesta non esiste");
        }

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
     * @throws NullPointerException se l'Azienda richiesta non esiste
     */
    public Integer getQuotazioneAzienda(Azienda azienda){
        if(azienda==null){
            throw new NullPointerException("Azienda non può essere null");
        }

        for(Azione azione : azioni){
            if(azione.getAzienda().equals(azienda)){
                return azione.getPrezzo();
            }
        }

        return null;
    }

    /**
     * Metodo per quotare un'Azienda in questa Borsa
     * 
     * <p>
     * Fatto con l'aiuto di Copilot
     * Modifies la mappa {@code quotazioni} aggiungendo o modificando la quotazione dell'Azienda
     * 
     * @param az il nome della Azienda da quotare
     * @param prezzo il prezzo di quotazione della Azienda
     * 
     * @throws IllegalArgumentException se il prezzo è minore o uguale a 0 o se l'Azienda è già quotata in questa Borsa
     * @throws NullPointerException se l'Azienda richiesta non esiste
     */
    public final void quotaAzienda(Azienda az, int prezzo, int quantita) throws NullPointerException,IllegalArgumentException{
        if(prezzo<=0){
            throw new IllegalArgumentException("Il prezzo di quotazione deve essere maggiore di 0");
        }

        if(az==null){
            throw new NullPointerException("L'Azienda richiesta non esiste");
        }

        for(Azione azione : azioni){
            if(azione.getAzienda().equals(az)){
                throw new IllegalArgumentException("L'Azienda è già quotata in questa Borsa");
            }
        }

        azioni.add(new Azione(az, prezzo, quantita));
    }

    /**
     * Metodo per ottenere il numero di azioni totali di un'Azienda
     * 
     * @param azienda l'Azienda di cui si vuole ottenere il numero di azioni totali
     * 
     * @return il numero di azioni totali dell'Azienda richiesta o null se non ne ha
     * 
     * @throws NullPointerException se l'Azienda è null
     * @throws IllegalArgumentException se il nome dell'Azienda è nullo o vuoto
     */
    public final Integer getNumeroAzioniTotali(Azienda azienda) throws NullPointerException, IllegalArgumentException{
        if(azienda==null){
            throw new NullPointerException("L'Azienda non può essere nulla");
        }

        int tot=0;
        for(Allocazione allocazione : allocazioni){
            for(Entry<Azione, Integer> entry : allocazione.azioniPossedute.entrySet()){
                if(entry.getKey().getAzienda().equals(azienda)){
                    tot+=entry.getValue();
                }
            }
        }
        tot+=getNumeroAzioniDisponibili(azienda);

        return tot;
    }

    /**
     * Metodo per recuperare il numero di azioni disponibili di un'Azienda
     * 
     * @param azienda l'Azienda di cui si vuole ottenere il numero di azioni disponibili
     * 
     * @throws NullPointerException se l'Azienda è null
     */
    public final Integer getNumeroAzioniDisponibili(Azienda azienda) throws NullPointerException{

        if(azienda==null){
            throw new NullPointerException("L'Azienda non può essere nulla");
        }

        for(Azione azione : azioni){
            if(azione.getAzienda().equals(azienda)){
                return azione.getQuantita();
            }
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
     * @throws IllegalArgumentException se la quantità è minore o uguale a 0 o se il nome dell'Azienda è nullo o vuoto o se l'Azienda è già quotata in questa Borsa
     */
    public final void erogaAzione(String azienda, int prezzo, int quantita) throws NullPointerException, IllegalArgumentException{
        if(azienda==null||azienda.isBlank()){
            throw new IllegalArgumentException("Il nome della Azienda non può essere nullo, vuoto o composto solo da spazi bianchi");
        }

        Azienda a = Azienda.getAziendaDaNome(azienda);
        if(a==null){
            throw new NullPointerException("L'Azienda non può essere null");
        }

        if(quantita<=0){
            throw new IllegalArgumentException("La quantità di azioni da erogare deve essere maggiore di 0");
        }

        Azione azione=new Azione(a, prezzo, quantita);
        if(!azioni.contains(azione)){
            azioni.add(azione);
        }

        throw new IllegalArgumentException("L'Azienda è già quotata in questa Borsa");
    }
    
    /**
     * Metodo per ottenere le aziende quotate in questa borsa
     * 
     * @return set non modificabile delle aziende quotate in questa borsa
     */
    public SortedSet<Azienda> getAziendeQuotate(){
        return Collections.unmodifiableSortedSet(aziendeInBorsa);
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
     * Metodo per ottenere l'Azione di una Azienda in questa Borsa
     * 
     * @param azienda l'Azienda di cui ottenere l'Azione
     * @return l'Azione della Azienda richiesta o null se non esiste
     */
    public Azione getAzione(Azienda azienda){
        for(Azione azione : azioni){
            if(azione.getAzienda().equals(azienda)){
                return azione;
            }
        }

        return null;
    }
    
    /**
     * Metodo per far acquistare delle azioni ad un Operatore
     * 
     * @param operatore l'Operatore che compra le azioni
     * @param azienda l'Azienda di cui comprare le azioni
     * @param budgetAcquisto il budgetAcquisto dell'Operatore
     * 
     * @throws NullPointerException se l'Operatore o l'Azienda sono nulli
     * @throws IllegalArgumentException se il nome dell'Azienda è nullo o vuoto, se l'Azienda non è quotata nella Borsa, se il budgetAcquisto è minore o uguale al prezzo di una Azione o se l'Azienda non ha abbastanza Azioni disponibili
     */
    public final int compraAzione(Operatore operatore, Azienda azienda, int budgetAcquisto) throws NullPointerException, IllegalArgumentException{
        if(operatore==null){
            throw new NullPointerException("L'Operatore non può essere nullo");
        }

        System.err.println("operatore esiste");

        if(azienda==null){
            throw new NullPointerException("L'Azienda non può essere nulla");
        }

        System.err.println("azienda esiste");

        Integer prezzoAz=getQuotazioneAzienda(azienda);
        if(prezzoAz==null){
            throw new IllegalArgumentException("L'Azienda non è quotata in questa Borsa");
        }

        System.err.println("la quotazione esiste");

        if(prezzoAz>budgetAcquisto){
            throw new IllegalArgumentException("Il prezzo di acquisto deve essere maggiore o uguale al prezzo di quotazione");
        }

        System.err.println("il budget è sufficiente");

        int quantita=budgetAcquisto/prezzoAz;

        System.err.println("la quantità è "+quantita);

        if(getNumeroAzioniDisponibili(azienda)<quantita){
            throw new IllegalArgumentException("L'Azienda non ha abbastanza azioni disponibili");
        }

        System.err.println("le azioni sono disponibili");

        int resto = budgetAcquisto%prezzoAz;

        System.err.println("il resto è "+resto);
        System.err.println("la spesa totale è "+(quantita*prezzoAz));
        System.err.println("il budget rimanente è "+operatore.getBudget());

        operatore.prelievoDalBudget(quantita*prezzoAz);

        for(Allocazione allocazione : allocazioni){
            if(allocazione.getOperatore().equals(operatore)){

                System.err.println("l'operatore ha già azioni in borsa");

                for(Entry<Azione, Integer> entry : allocazione.azioniPossedute.entrySet()){
                    if(entry.getKey().getAzienda().equals(azienda)){

                        System.err.println("l'operatore ha già azioni di questa azienda");

                        int tot=(entry.getValue()+quantita);

                        Azione azione = getAzione(azienda);
                        azioni.remove(azione);
                        azione.modificaQuantita(-quantita);
                        azioni.add(azione);

                        System.err.println("le azioni sono state aggiornate");

                        allocazione.azioniPossedute.remove(azione);
                        allocazione.azioniPossedute.put(azione, tot);

                        System.err.println("le azioni dell'operatore sono state aggiornate");

                        allocazioni.remove(allocazione);
                        allocazioni.add(allocazione);
                        

                        System.err.println("le allocazioni sono state aggiornate");

                        return resto;
                    }
                }

                System.err.println("l'operatore non ha azioni di questa azienda");

                Azione azione = getAzione(azienda);
                azioni.remove(azione);
                azione.modificaQuantita(-quantita);
                azioni.add(azione);

                System.err.println("le azioni sono state aggiornate");

                allocazione.azioniPossedute.put(azione, quantita);

                System.err.println("le azioni dell'operatore sono state aggiornate");

                return resto;
            }
        }

        System.err.println("l'operatore non ha azioni in borsa");

        Azione azione = getAzione(azienda);
        azioni.remove(azione);
        azione.modificaQuantita(-quantita);
        azioni.add(azione);

        System.err.println("le azioni sono state aggiornate");

        Allocazione allocazione = new Allocazione(operatore);
        allocazione.azioniPossedute.put(azione, quantita);
        allocazioni.add(allocazione);

        System.err.println("le allocazioni sono state aggiornate");

        return resto;
    }
    /*public final int compraAzione(Operatore operatore, Azienda azienda, int budgetAcquisto) throws NullPointerException, IllegalArgumentException {
        if (operatore == null) {
            throw new NullPointerException("L'operatore non può essere nullo");
        }
    
        if (azienda == null) {
            throw new NullPointerException("L'azienda non può essere nulla");
        }
    
        Integer prezzoAz = getQuotazioneAzienda(azienda);
        if (prezzoAz == null) {
            throw new IllegalArgumentException("L'azienda non è quotata in questa borsa");
        }
    
        if (prezzoAz > budgetAcquisto) {
            throw new IllegalArgumentException("Il budget non è sufficiente per acquistare anche una sola azione");
        }
    
        int quantita = budgetAcquisto / prezzoAz;
        if (getNumeroAzioniDisponibili(azienda) < quantita) {
            throw new IllegalArgumentException("Non ci sono abbastanza azioni disponibili");
        }
    
        int resto = budgetAcquisto % prezzoAz;
    
        for (Allocazione allocazione : allocazioni) {
            if (allocazione.getOperatore().equals(operatore)) {
                Azione azione = getAzione(azienda);
                allocazione.azioniPossedute.merge(azione, quantita, Integer::sum);
                azione.modificaQuantita(-quantita);
                return resto;
            }
        }
    
        Azione azione = getAzione(azienda);
        azioni.remove(azione);
        azione.modificaQuantita(-quantita);
        azioni.add(azione);
    
        Allocazione allocazione = new Allocazione(operatore);
        allocazione.azioniPossedute.put(azione, quantita);
        allocazioni.add(allocazione);
    
        return resto;
    }*/
    /**
     * Metodo per far vendere delle azioni ad un Operatore
     * 
     * @param operatore l'Operatore che vende le azioni
     * @param azienda l'Azienda di cui vendere le azioni
     * @param quantita la quantità di azioni da vendere
     * 
     * @throws NullPointerException se l'Operatore o l'Azienda sono nulli
     * @throws IllegalArgumentException se il nome dell'Azienda è nullo o vuoto, se la quantità è minore o uguale a 0, se l'Operatore non ha abbastanza azioni da vendere o se l'Operatore non ha azioni di quell'Azienda
     */
    public final void vendiAzione(Operatore operatore, Azienda azienda, int quantita) throws NullPointerException, IllegalArgumentException{
        if(operatore==null){
            throw new NullPointerException("L'Operatore non può essere nullo");
        }

        if(azienda==null){
            throw new NullPointerException("L'Azienda non può essere nulla");
        }

        if(quantita<=0){
            throw new IllegalArgumentException("La quantità di azioni da vendere deve essere maggiore di 0");
        }

        for(Allocazione allocazione : allocazioni){
            if(allocazione.getOperatore().equals(operatore)){
                for(Entry<Azione, Integer> entry : allocazione.azioniPossedute.entrySet()){
                    if(entry.getKey().getAzienda().equals(azienda)){
                        if(entry.getValue()<quantita){
                            throw new IllegalArgumentException("L'Operatore non ha abbastanza azioni da vendere");
                        }

                        int tot=(entry.getValue()-quantita);

                        Azione azione = getAzione(azienda);

                        int prezzo=azione.getPrezzo();
                        operatore.depositaInBudget(quantita*prezzo);

                        azioni.remove(azione);
                        azione.modificaQuantita(quantita);
                        azioni.add(azione);

                        allocazioni.remove(allocazione);
                        if(tot!=0){
                            allocazioni.add(allocazione);
                        }

                        allocazione.azioniPossedute.remove(getAzione(azienda));
                        if(tot!=0){
                            allocazione.azioniPossedute.put(getAzione(azienda), tot);
                        }
                        return;
                    }
                }
            }
        }

        throw new IllegalArgumentException("L'Operatore non ha azioni da vendere");
    }

    public SortedSet<Azione> getAzioni(){
        return Collections.unmodifiableSortedSet(azioni);
    }

    public SortedSet<Allocazione> getAllocazioni(){
        return Collections.unmodifiableSortedSet(allocazioni);
    }

    @Override
    public int compareTo(Borsa other) {
        return nome.compareTo(other.nome);
    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Borsa other)){
            return false;
        }
        return nome.equals(other.nome);
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }

    /** Classe interna Azione */
    public class Azione implements Comparable<Azione>{
        /**
         * AF:
         * Una Azione è rappresentata da:
         * - azienda: la Azienda a cui è collegata la Azione
         * - quantita: la quantità di Azioni della Azienda ancora disponibili
         * - prezzo: il prezzo di quotazione dell'Azienda
         * 
         * RI:
         * L'oggetto Azione deve rispettare la seguente condizione:
         * - azienda non può essere null
         * - prezzo deve essere maggiore di 0
         * - quantita deve essere maggiore di 0
         */

        /** La Azienda a cui è collegata la Azione */
        private final Azienda azienda;
        /** La Borsa a cui è collegata la Azienda di cui si crea l'Azione */
        private final Borsa borsa;
        /** Il prezzo della Azione */
        private int prezzo;
        /** La quantità di Azioni non possedute da nessuno Operatore della Azienda */
        private int quantita;    

        /**
         * Costruttore privato per creare una Azione
         * 
         * @param azienda la Azienda a cui è collegata la Azione
         * @param quantita la quantità di Azioni della Azienda
         */
        public Azione(Azienda azienda, int prezzo, int quantita){
            this.azienda=azienda;
            this.borsa=Borsa.this;
            this.prezzo=prezzo;
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
         * Metodo per ottenere il prezzo della Azione
         * 
         * @return il prezzo della Azione
         */
        public int getPrezzo(){
            return prezzo;
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
         * Metodo per modificare il prezzo della Azione
         * 
         * @param modifica la modifica da applicare al prezzo
         */
        public void modificaPrezzo(int modifica){
            this.prezzo+=modifica;
        }

        /**
         * Metodo per recuperare la Borsa a cui è collegata l'Azione
         */
        public Borsa getBorsa(){
            return borsa;
        }

        /**
         * Metodo per modificare la quantità di Azioni della Azienda
         * 
         * @param modifica la modifica da applicare alla quantità di Azioni
         */
        public void modificaQuantita(int modifica){
            this.quantita+=modifica;
        }
        
        @Override
        public int compareTo(Azione other) {
            return azienda.compareTo(other.azienda);
        }
    }

    /** Classe interna Allocazione */
    public class Allocazione implements Comparable<Allocazione>{
        /*
         * AF:
         * Un'Allocazione è rappresentata da:
         * - operatore: l'Operatore a cui è collegata l'Allocazione
         * - azioniPossedute: le Azioni possedute dall'Operatore
         * RI:
         * L'oggetto Allocazione deve rispettare la seguente condizione:
         * - operatore non può essere null
         * - azioniPossedute non può essere null e non può contenere null
         */

        /** L'Operatore a cui è assegnata la Allocazione */
        private final Operatore operatore;
        /** Mappa delle Azioni possedute */
        private TreeMap<Azione, Integer> azioniPossedute = new TreeMap<>();
        

        /**
         * Costruttore privato per creare una Allocazione
         * 
         * @param operatore l'Operatore a cui è assegnata la Allocazione
         *
         * @throws NullPointerException se l'Operatore è nullo
         */
        private Allocazione(Operatore operatore){
            if(operatore==null){
                throw new NullPointerException("L'Operatore non può essere nullo");
            }

            this.operatore=operatore;
            this.azioniPossedute=new TreeMap<>();
            
        }

        /**
         * Metodo per ottenere l'Operatore a cui è assegnata la Allocazione
         * 
         * @return l'Operatore a cui è assegnata la Allocazione
         */
        public Operatore getOperatore(){
            return operatore;
        }

        public TreeMap<Azione, Integer> getAzioniPossedute(){
            return azioniPossedute;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Allocazione other)){
                return false;
            }
            return operatore.equals(other.operatore);
        }

        @Override
        public int hashCode() {
            return operatore.hashCode();
        }

        @Override
        public int compareTo(Allocazione other) {
            return this.operatore.getNome().compareTo(other.operatore.getNome());
        }

    }
}
