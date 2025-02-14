package borsaNova.entita;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map.Entry;

import borsaNova.politicaPrezzo.*;

import java.util.Objects;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Classe per rappresentare una Borsa
 * 
 * <p>
 * Una Borsa ha un nome che la identifica univocamente, delle Azioni delle Aziende quotate, una Politica di Prezzo e delle allocazioni di Azioni agli Operatori
 * Una Borsa può quotare una Azienda (per procura di quest'ultima) creandole delle Azioni, aggiungere/rimuovere il numero di Azioni di una Azienda, fornire ad un Operatore una Allocazione, che tiene traccia di tutte le Azioni ad esso assegnate e settare una Politica di Prezzo
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
     * - allocazioni: un set di Allocazioni della Borsa
     *
     * RI:
     * L'oggetto Borsa deve rispettare la seguente condizione:
     * - nome non null, stringa vuota o composta solo da soli spazi bianchi
     * - politica può essere null o un oggetto di tipo Politica
     * - azioni non deve essere null e non deve contenere null
     * - allocazioni non deve essere null e non deve contenere null
     */
    
    /** Il {@code nome} della Borsa */
    private final String nome;
    /** La politica di prezzo della Borsa */
    @SuppressWarnings("unused")
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
        if (Objects.requireNonNull(nome, "Il nome non può essere null").isBlank()){
            throw new IllegalArgumentException("Il nome non può essere vuoto");
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
     * Metodo per ottenere una Borsa a partire dal nome
     * 
     * @param nome il nome della Borsa da ottenere
     * 
     * @return la Borsa richiesta o null se non esiste
     * 
     * @throws IllegalArgumentException se il nome della Borsa è nullo, vuoto, composto solo da spazi bianchi o non esiste
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
     * Metodo per quotare un'Azienda in questa Borsa (creando delle Azioni)
     * 
     * <p>
     * Fatto con l'aiuto di Copilot
     * Modifies {@code azioni} aggiungendo una nuova Azione per la Azienda richiesta nella Borsa
     * 
     * @param az la Azienda da quotare
     * @param prezzo il prezzo di quotazione della Azienda
     * @param quantita la quantità di Azioni della Azienda
     * 
     * @throws NullPointerException se l'Azienda richiesta non esiste
     * @throws IllegalArgumentException se il prezzo è minore o uguale a 0 o se l'Azienda è già quotata in questa Borsa
     */
    public final void quotaAzienda(Azienda az, int prezzo, int quantita) throws NullPointerException,IllegalArgumentException{
        if(az==null){
            throw new NullPointerException("L'Azienda richiesta non esiste");
        }
        
        if(prezzo<=0){
            throw new IllegalArgumentException("Il prezzo di quotazione deve essere maggiore di 0");
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
     * @return il numero di azioni totali dell'Azienda richiesta o 0 se non esiste
     * 
     * @throws NullPointerException se l'Azienda è null
     */
    public final int getNumeroAzioniTotali(Azienda azienda) throws NullPointerException{
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
     * @return il numero di azioni disponibili dell'Azienda richiesta o 0 se non esiste
     * 
     * @throws NullPointerException se l'Azienda è null
     */
    public final int getNumeroAzioniDisponibili(Azienda azienda) throws NullPointerException{

        if(azienda==null){
            throw new NullPointerException("L'Azienda non può essere nulla");
        }

        for(Azione azione : azioni){
            if(azione.getAzienda().equals(azienda)){
                return azione.getQuantita();
            }
        }
        
        return 0;
    }

    /**
     * Metodo per settare una politica di prezzo per la borsa
     * 
     * <p>
     * Modifies {@code politica} settando la Politica di Prezzo della Borsa
     * 
     * <p>
     * Valore è usato come:
     * <ul>
     * <li> Selettore per il tipo di Politica di Prezzo in Incremento, Decremento, Vocale, Soglia o Variazione</li>
     * <li> Come lettera di riferimento (in ASCII) nella Politica di Vocale</li>
     * </ul>
     * 
     * <p>
     * vSu e vGiu sono usati come:
     * <ul>
     * <li> Valore di variazione in caso di acquisto per le Politica di Incremento, Decremento e Variazione</li>
     * <li> Selettori aggiuntivi per le Politiche di Vocale e Soglia</li>
     * </ul>
     * 
     * <p>
     * Come funziona la selezione della Politica di Prezzo:
     * <ul>
     * <li>Se valore è maggiore di 0:
     *   <ul>
     *      <li>Se vSu e vGiu sono entrambi 0, la Politica è di tipo Vocale</li>
     *      <li>Altrimenti la Politica è di tipo Incremento</li>
     *   </ul>
     * </li>
     * <li>Se valore è minore di 0, la politica è di tipo Decremento</li>
     * <li>Se valore è uguale a 0:
     *   <ul>
     *      <li>Se vSu e vGiu sono uguali, la Politica è di tipo Soglia</li>
     *      <li>Altrimenti la Politica è di tipo Variazione</li>
     *   </ul>
     * </li>
     * </ul>
     * 
     * @param valore il valore della Politica di Prezzo
     * @param vSu il valore di variazione in caso di acquisto
     * @param vGiu il valore di variazione in caso di vendita
     * 
     * @throws IllegalArgumentException se la creazione della Politica fallisce
     */
    public final void setPoliticaPrezzo(int valore, int vSu, int vGiu) throws IllegalArgumentException{
        if(valore>0){
            if(vGiu==0 && vSu==0){
                char lettera=(char)valore;
                politica= new Vocale(lettera);
            }else{
                politica= new Incremento(vSu);
            }
        }else if(valore<0){
            politica= new Decremento(Math.abs(vGiu));
        }else{
            if(vGiu==vSu){
                politica= new Soglia(vSu);
            }else{
                politica= new Variazione(vSu, vGiu);
            }
        }
    }

    /**
     * Metodo per applicare la variazione di prezzo ad una Azione in seguito ad un acquisto o una vendita
     * 
     * <p>
     * Modifies {@code azioni} cambiando il prezzo di quotazione di una Azione e {@code allocazioni} cambiando il prezzo di quotazione di tutte le Azioni possedute da un Operatore
     * 
     * @param az l'Azione a cui applicare la variazione
     * @param acquisto true se acquisto , false se vendita
     * @param quantita la quantità di Azioni acquistate o vendute (utile per Politica di Soglia)
     * 
     * @throws IllegalArgumentException se {@code cambioPrezzoAcquisto} o {@code cambioPrezzoVendita} o {@code modificaPrezzo} lanciano una IllegalArgumentException
     */
    private final void aggiornaPrezzoAzione(Azione az, boolean acquisto,int quantita){
        if(politica!=null){
            if(acquisto){
                politica.cambioPrezzoAcquisto(az,quantita);
            }else{
                politica.cambioPrezzoVendita(az,quantita);
            }
            for(Allocazione al : allocazioni){
                for(Entry<Azione, Integer> entry : al.azioniPossedute.entrySet()){
                    if(entry.getKey().equals(az)){
                        entry.getKey().modificaPrezzo(az.getPrezzo());
                    }
                }
            }
        }
    }

    /**
     * Metodo per ottenere l'Azione di una Azienda in questa Borsa
     * 
     * @param azienda l'Azienda di cui ottenere l'Azione
     * 
     * @return l'Azione della Azienda richiesta o null se non esiste
     * 
     * @throws NullPointerException se l'Azienda è null
     */
    public Azione getAzione(Azienda azienda) throws NullPointerException{
        if(azienda==null){
            throw new NullPointerException("L'Azienda non può essere nulla");
        }

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
     * <p>
     * Modifies {@code azioni} aggiungendo o modificando una Azione per l'Azienda richiesta, e aggiornandone il prezzo
     * <p>
     * Modifies {@code allocazioni} aggiungendo o modificando una Allocazione per l'Operatore richiesto
     * <p>
     * Modifies {@code operatore} aggiungendo o modificando le Azioni acquistate
     * <p>
     * Se l'Operatore ha già una Allocazione in Borsa, ma non ha Azioni di quell'Azienda, viene creata una nuova entry nella mappa {@code azioniPossedute} di Allocazione, altimenti se ne modifica il valore.
     * Se l'Operatore non ha una Allocazione in Borsa viene creata una nuova Allocazione con la nuova Azione in {@code azioniPossedute}
     * 
     * @param operatore l'Operatore che compra le azioni
     * @param azienda l'Azienda di cui comprare le azioni
     * @param budgetAcquisto il budgetAcquisto dell'Operatore
     * 
     * @throws NullPointerException se l'Operatore o l'Azienda sono nulli
     * @throws IllegalArgumentException se il nome dell'Azienda è nullo o vuoto, se l'Azienda non è quotata nella Borsa, se il budgetAcquisto è minore o uguale al prezzo di una Azione, se l'Azienda non ha abbastanza Azioni disponibili, se {@code prelievoDalBudget} incorre in una IllegalArgumentException, se {@code modificaQuantita} incorre in una IllegalArgumentException o se {@code aggiungiAzione} incorre in una IllegalArgumentException
     */
    public final void compraAzione(Operatore operatore, Azienda azienda, int budgetAcquisto) throws NullPointerException, IllegalArgumentException{
        if(operatore==null){
            throw new NullPointerException("L'Operatore non può essere nullo");
        }

        if(azienda==null){
            throw new NullPointerException("L'Azienda non può essere nulla");
        }

        Integer prezzoAz=getQuotazioneAzienda(azienda);
        if(prezzoAz==null){
            throw new IllegalArgumentException("L'Azienda non è quotata in questa Borsa");
        }

        if(prezzoAz>budgetAcquisto){
            throw new IllegalArgumentException("Il prezzo di acquisto deve essere maggiore o uguale al prezzo di quotazione");
        }

        int quantita=budgetAcquisto/prezzoAz;

        if(getNumeroAzioniDisponibili(azienda)<quantita){
            throw new IllegalArgumentException("L'Azienda non ha abbastanza azioni disponibili");
        }

        operatore.prelievoDalBudget(quantita*prezzoAz);

        for(Allocazione allocazione : allocazioni){
            if(allocazione.getOperatore().equals(operatore)){

                for(Entry<Azione, Integer> entry : allocazione.azioniPossedute.entrySet()){
                    if(entry.getKey().getAzienda().equals(azienda)){


                        int tot=(entry.getValue()+quantita);

                        Azione azione = getAzione(azienda);
                        azioni.remove(azione);
                        azione.modificaQuantita(-quantita);
                        aggiornaPrezzoAzione(azione, true, quantita);
                        azioni.add(azione);

                        allocazione.azioniPossedute.remove(azione);
                        allocazione.azioniPossedute.put(azione, tot);

                        allocazioni.remove(allocazione);
                        allocazioni.add(allocazione);

                        operatore.aggiungiAzione(azione, quantita);                        

                        return;
                    }
                }

                Azione azione = getAzione(azienda);
                azioni.remove(azione);
                azione.modificaQuantita(-quantita);
                aggiornaPrezzoAzione(azione, true, quantita);
                azioni.add(azione);

                allocazione.azioniPossedute.put(azione, quantita);

                operatore.aggiungiAzione(azione, quantita);

                return;
            }
        }

        Azione azione = getAzione(azienda);
        azioni.remove(azione);
        azione.modificaQuantita(-quantita);
        aggiornaPrezzoAzione(azione, true, quantita);
        azioni.add(azione);

        Allocazione allocazione = new Allocazione(operatore);
        allocazione.azioniPossedute.put(azione, quantita);
        allocazioni.add(allocazione);

        operatore.aggiungiAzione(azione, quantita);

        return;
    }
    
    /**
     * Metodo per far vendere delle azioni ad un Operatore
     * 
     * <p>
     * Modifies {@code azioni} aggiungendo o modificando una Azione per l'Azienda richiesta, e aggiornandone il prezzo
     * <p>
     * Modifies {@code allocazioni} rimuovendo o modificando una Allocazione per l'Operatore richiesto
     * <p>
     * Modifies {@code operatore} rimuovendo o modificando le Azioni vendute
     * <p>
     * Il metodo vende le Azioni se e solo se l'Operatore le possiede in Borsa
     * 
     * @param operatore l'Operatore che vende le azioni
     * @param azienda l'Azienda di cui vendere le azioni
     * @param quantita la quantità di azioni da vendere
     * 
     * @throws NullPointerException se l'Operatore o l'Azienda sono nulli
     * @throws IllegalArgumentException se la quantità è minore o uguale a 0, se l'Operatore non ha abbastanza azioni da vendere, se l'Operatore non ha azioni di quell'Azienda, se {@code depositaInBudget} incorre in una IllegalArgumentException, se {@code modificaQuantita} incorre in una IllegalArgumentException o se {@code rimuoviAzione} incorre in una IllegalArgumentException
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
                        aggiornaPrezzoAzione(azione, false, quantita);
                        azioni.add(azione);

                        allocazioni.remove(allocazione);
                        allocazione.azioniPossedute.remove(azione);

                        if(tot!=0){
                            allocazione.azioniPossedute.put(azione, tot);
                            allocazioni.add(allocazione);
                        }

                        operatore.rimuoviAzione(azione, quantita);
                        
                        return;
                    }
                }
            }
        }

        throw new IllegalArgumentException("L'Operatore non ha azioni da vendere");
    }

    /**
     * Metodo per ottenere un iteratore delle Azioni in questa Borsa
     * 
     * @return un iteratore di Azioni
     */
    public Iterator<Azione> getAzioni(){
        return Collections.unmodifiableSortedSet(azioni).iterator();
    }

    /**
     * Metodo per ottenere un iteratore di Allocazioni in questa Borsa
     * 
     * @return un iteratore di Allocazioni
     */
    public Iterator<Allocazione> getAllocazioni(){
        return Collections.unmodifiableSortedSet(allocazioni).iterator();
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
         * - borsa: la Borsa a cui è collegata la Azienda di cui si crea l'Azione
         * - quantita: la quantità di Azioni della Azienda ancora disponibili
         * - prezzo: il prezzo di quotazione dell'Azienda
         * 
         * RI:
         * L'oggetto Azione deve rispettare la seguente condizione:
         * - azienda non può essere null
         * - borsa non può essere null
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
         * @param prezzo il prezzo di quotazione della Azienda
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
         * Metodo per modificare il prezzo della Azione ad un nuovo valore
         * 
         * @param modifica il nuovo valore di prezzo
         * 
         * @throws IllegalArgumentException se il prezzo è minore o uguale a 0
         */
        public void modificaPrezzo(int modifica){
            if(modifica<=0){
                throw new IllegalArgumentException("Il prezzo di quotazione deve essere maggiore di 0");
            }

            this.prezzo=modifica;
        }

        /**
         * Metodo per recuperare la Borsa a cui è collegata l'Azione
         * 
         * @return la Borsa a cui è collegata l'Azione
         */
        public Borsa getBorsa(){
            return borsa;
        }

        /**
         * Metodo per modificare la quantità di Azioni della Azienda
         * 
         * @param modifica la modifica da applicare alla quantità di Azioni
         * 
         * @throws IllegalArgumentException se la quantità di Azioni diventa minore di 0
         */
        public void modificaQuantita(int modifica){
            if(this.quantita+modifica<0){
                throw new IllegalArgumentException("La quantità di Azioni non può essere minore di 0");
            }
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
         * - azioniPossedute: mappa delle Azioni possedute dall'Operatore (chiave: Azione, valore: quantità posseduta)
         * RI:
         * L'oggetto Allocazione deve rispettare la seguente condizione:
         * - operatore non può essere null
         * - azioniPossedute non può essere null e non può contenere chiavi o valori null 
         */

        /** L'Operatore a cui è assegnata la Allocazione */
        private final Operatore operatore;
        /** Mappa delle Azioni possedute */
        private SortedMap<Azione, Integer> azioniPossedute = new TreeMap<>();
        

        /**
         * Costruttore privato per creare una Allocazione
         * 
         * @param operatore l'Operatore a cui è assegnata la Allocazione
         *
         * @throws NullPointerException se l'Operatore è nullo
         */
        private Allocazione(Operatore operatore) throws NullPointerException{
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

        //TODO potrei fare Iterator<Entry<Azione, Integer>> anzichè SortedMap<Azione, Integer> ???
        /**
         * Metodo per ottenere le Azioni possedute dall'Operatore
         * 
         * @return una sorted map non modificabile delle Azioni possedute dall'Operatore
         */
        public SortedMap<Azione, Integer> getAzioniPossedute(){
            return Collections.unmodifiableSortedMap(azioniPossedute);
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
