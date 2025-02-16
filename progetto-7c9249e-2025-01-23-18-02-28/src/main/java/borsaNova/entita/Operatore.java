package borsaNova.entita;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import borsaNova.entita.Borsa.Azione;

/**
 * Classe per rappresentare un Operatore
 * 
 * <p>
 * Un Operatore ha un nome, che lo identifica univocamente, e un Budget inizialmente pari a 0.
 * Un Operatore può depositare denaro nel Budget, prelevare denaro dal Budget, acquistare/vendere Azioni di un'Azienda, ottenere il valore del Portafoglio Azionario e il proprio Capitale Totale.
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
 * <li>Alessandro Lamera (compagno di corso, suggerimento di semaforo booleano per la "sicurezza" di compravendita di Azioni e modifica del valore di Portafoglio Azionario)</li>
 * </ul>
 */
public class Operatore implements Comparable<Operatore>{
    /*
     * AF:
     * Un Operatore è rappresentato da:
     * - nome: il nome dell'Operatore
     * - budget: il budget dell'Operatore
     * - portafoglioAzionario: una mappa delle Azioni possedute dall'Operatore (chiave: Azione, Valore: quantità posseduta) 
     * - semaforo: valore booleano per evitare compravendite non eseguite dall'Operatore
     *
     * RI:
     * L'oggetto Operatore deve rispettare le seguenti condizioni:
     * - nome non null, stringa vuota o composta solo da soli spazi bianchi
     * - budget maggiore o uguale a 0 (inizialmente 0)
     * - portafoglioAzionario non deve essere null e non deve contenere chiavi o valori null
     * - semaforo deve essere true se e solo se si stanno effettuando operazioni di acquisto o vendita, false altrimenti
     */
    
    /** Il {@code nome} dell'Operatore */
    private final String nome;
    /** Il {@code budget} dell'Operatore */
    private int budget;
    /** Il {@code Portafoglio Azionario} dell'Operatore */
    private SortedMap<Azione, Integer> portafoglioAzionario = new TreeMap<>();
    /** Semaforo booleano per evitare compravendite non eseguite dall'Operatore e modifiche al contenuto del Portafoglio Azionario senza autorizzazione*/
    private boolean semaforo = false;

    /** Set dei nomi degli Operatori già usati */
    private static final SortedSet<String> NOMI_USATI = new TreeSet<>();
    /** Set degli Operatori */
    private static final SortedSet<Operatore> operatori = new TreeSet<>();

    
    /**
     * Metodo per costruire un Operatore 
     * 
     * <p>
     * Modified la mappa {@code operatori} se l'Operatore non esiste già
     * 
     * @param nome il nome dell'Operatore da creare o recuperare
     * 
     * @return l'Operatore richiesto
     * 
     * @throws NullPointerException se il nome dell'Operatore è nullo
     * @throws IllegalArgumentException se il nome dell'Operatore è nullo o vuoto o se {@code getOperatoreDaNome} incorre in una IllegalArgumentException
     */
    public static Operatore of(String nome) throws NullPointerException, IllegalArgumentException{
        if (Objects.requireNonNull(nome, "Il nome non può essere null").isBlank()){
            throw new IllegalArgumentException("Il nome non può essere vuoto");
        }

        if(NOMI_USATI.contains(nome)){
            return getOperatoreDaNome(nome);
        }

        return new Operatore(nome);
    }

    /**
     * Costruttore di Operatore
     * 
     * @param nome il nome dell'Operatore da ottenere
     */
    private Operatore(String nome){
        this.nome = nome;
        this.budget = 0;
        this.portafoglioAzionario=new TreeMap<>();
    }

    /**
     * Metodo per ottenere un Operatore dal nome
     * 
     * @param nome il nome dell'Operatore da ottenere
     * 
     * @return l'Operatore richiesto se esiste, null altrimenti
     * 
     * @throws IllegalArgumentException se il nome dell'Operatore è nullo o vuoto o se è composto solo da spazi bianchi o se l'Operatore non esiste
     */
    private static Operatore getOperatoreDaNome(String nome) throws IllegalArgumentException{
        if(nome==null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome dell'Operatore deve essere non nullo o vuoto");
        }

        if(!NOMI_USATI.contains(nome)){
            throw new IllegalArgumentException("L'Operatore non esiste");
        }

        for(Operatore o : operatori){
            if(o.getNome().equals(nome)){
                return o;
            }
        }
        return null;
    }

    /**
     * Metodo per ottenere il nome dell'Operatore
     * 
     * @return il nome dell'Operatore
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo per ottenere il valore del semaforo
     * 
     * @return il valore del semaforo (true o false)
     */
    public boolean getSemaforo(){
        return semaforo;
    }

    /**
     * Metodo per ottenere il budget dell'Operatore
     * 
     * @return il budget dell'Operatore
     */
    public int getBudget() {
        return budget;
    }

    /**
     * Metodo per depositare denaro nel budget
     * 
     * <p>
     * Utile sia per la creazione dell'Operatore  (che deve iniziare con budget 0) che per ricaricare il budget
     * Modifies {@code budget} dell'Operatore
     * 
     * @param deposito l'importo da depositare
     * 
     * @throws IllegalArgumentException se l'importo del deposito è negativo
     */
    public void depositaInBudget(int deposito) throws IllegalArgumentException{
        if(deposito<0){
            throw new IllegalArgumentException("Il deposito di denaro non può essere negativo");
        }
        this.budget += deposito;
    }

    /**
     * Metodo per prelevare denaro dal budget
     * 
     * <p>
     * Modifies {@code budget} dell'Operatore
     * 
     * @param prelievo l'importo da prelevare
     * 
     * @throws IllegalArgumentException se l'importo del prelievo è negativo o pari a 0, se l'importo del prelievo è maggiore del budget
     */
    public void prelievoDalBudget(int prelievo) throws IllegalArgumentException{
        if(prelievo<=0){
            throw new IllegalArgumentException("Il prelievo di denaro non può essere negativo o pari a 0");
        }

        if(prelievo>budget){
            throw new IllegalArgumentException("Il prelievo di denaro non può essere maggiore del budget");
        }

        this.budget -= prelievo;
    }
    
    /**
     * Metodo per acquistare Azioni di un'Azienda
     * 
     * <p>
     * Modifies {@code portafoglioAzionario} dell'Operatore
     * <p>
     * Chiama il metodo {@code compraAzione} di Borsa
     * 
     * @param a la Azienda a cui appartengono le Azioni da acquistare
     * @param b la Borsa dove acquistare le Azioni 
     * @param prezzoTot il prezzo totale delle Azioni da acquistare
     * 
     * @throws NullPointerException se l'Azienda è nulla o la Borsa è nulla o se {@code compraAzione} incorre in una NullPointerException
     * @throws IllegalArgumentException se il denaro spendibile per le Azioni è negativo o pari a 0 o se {@code compraAzione} incorre in una IllegalArgumentException
     */
    public void acquistaAzione(Azienda a, Borsa b, int prezzoTot) throws IllegalArgumentException{        
        if(a==null){
            throw new NullPointerException("L'Azienda non può essere nulla");
        }

        if(b==null){
            throw new NullPointerException("La Borsa non può essere nulla");
        }

        if(prezzoTot<=0){
            throw new IllegalArgumentException("Il denaro spendibile per le Azioni non può essere negativo o pari a 0");
        }
        
        semaforo = true;

        b.compraAzione(this, a, prezzoTot);

        semaforo = false;
    }

    /**
     * Metodo per vendere Azioni di un'Azienda (se l'Operatore possiede abbastanza Azioni)
     * 
     * <p>
     * Chiama il metodo {@code vendiAzione} di Borsa
     * 
     * @param azienda la Azienda a cui appartengono le Azioni da vendere
     * @param borsa la Borsa dove l'Azienda di cui vendere le Azioni è quotata
     * @param quantita la quantità di Azioni da vendere
     * 
     * @throws NullPointerException se l'Azienda è nulla o la Borsa è nulla o se {@code vendiAzione} incorre in una NullPointerException
     * @throws IllegalArgumentException se la quantità di Azioni da vendere è negativa o se {@code vendiAzione} incorre in una IllegalArgumentException
     */
    public void vendeAzione(Azienda azienda, Borsa borsa, int quantita) throws NullPointerException, IllegalArgumentException{
        if(azienda==null){
            throw new NullPointerException("L'Azienda non può essere nulla");
        }

        if(borsa==null){
            throw new NullPointerException("La Borsa non può essere nulla");
        }

        if(quantita<=0){
            throw new IllegalArgumentException("La quantità di Azioni da vendere non può essere negativa");
        }

        semaforo = true;

        borsa.vendiAzione(this, azienda, quantita);

        semaforo = false;
    }

    /**
     * Metodo per ottenere il valore totale del portafoglio dell'Operatore (valore delle Azioni possedute)
     * 
     * @return il valore totale del portafoglio azionario dell'Operatore o 0 se non possiede Azioni
     */
    public int getValorePortafoglio(){
        int valorePortafoglio=0;
        Iterator<Map.Entry<Azione, Integer>> it = portafoglioAzionario.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Azione, Integer> entry = it.next();
            
            valorePortafoglio += entry.getKey().getPrezzo()*entry.getValue();
        }

        return valorePortafoglio;
    }

    /**
     * Metodo per ottenere il Capitale Totale dell'Operatore (budget + valore del portafoglio)
     * 
     * @return la somma del budget e del valore del portafoglio dell'Operatore
     * 
     */
    public int getCapitaleTotale(){
        return budget + getValorePortafoglio();
    }

    /**
     * Metodo per ottenere il Portafoglio Azionario dell'Operatore
     * 
     * @return una sorted map non modificabile del Portafoglio Azionario dell'Operatore
     */
    public SortedMap<Azione, Integer> getPortafoglioAzionario(){
        return Collections.unmodifiableSortedMap(portafoglioAzionario);
    }

    /**
     * Metodo per aggiungere Azioni al Portafoglio Azionario dell'Operatore
     * 
     * <p>
     * Modifies {@code portafoglioAzionario} dell'Operatore
     * 
     * @param azione l'Azione da aggiungere
     * @param quantita la quantità di Azioni da aggiungere
     * 
     * @throws IllegalArgumentException se la quantità di Azioni da aggiungere è negativa o nulla
     * @throws IllegalStateException se non si stanno effettuando operazioni di acquisto ma si sta cercando di aggiungere Azioni al Portafoglio Azionario
     */
    public void aggiungiAzione(Azione azione, int quantita) throws IllegalArgumentException{
        if(quantita<=0){
            throw new IllegalArgumentException("La quantità di Azioni da aggiungere non può essere negativa o nulla");
        }

        if(!getSemaforo()){
            throw new IllegalStateException("Non è possibile aggiungere Azioni al Portafoglio Azionario se non si stanno effettuando operazioni di acquisto ");
        }

        if(portafoglioAzionario.containsKey(azione)){
            portafoglioAzionario.put(azione, portafoglioAzionario.get(azione)+quantita);
        }else{
            portafoglioAzionario.put(azione, quantita);
        }
    }

    /**
     * Metodo per rimuovere Azioni dal Portafoglio Azionario dell'Operatore
     * 
     * <p>
     * Modifies {@code portafoglioAzionario} dell'Operatore
     * 
     * @param azione l'Azione da rimuovere
     * @param quantita la quantità di Azioni da rimuovere
     * 
     * @throws IllegalArgumentException se la quantità di Azioni da rimuovere è negativa o nulla, se l'Operatore non possiede Azioni di questa Azienda
     * @throws IllegalStateException se non si stanno effettuando operazioni di vendita ma si sta cercando di rimuovere Azioni dal Portafoglio Azionario
     */
    public void rimuoviAzione(Azione azione, int quantita) throws IllegalArgumentException{
        if(quantita<=0){
            throw new IllegalArgumentException("La quantità di Azioni da rimuovere non può essere negativa o nulla");
        }

        if(!getSemaforo()){
            throw new IllegalStateException("Non è possibile rimuovere Azioni dal Portafoglio Azionario se non si stanno effettuando operazioni di vendita");
        }

        if(portafoglioAzionario.containsKey(azione)){
            if(portafoglioAzionario.get(azione)-quantita<=0){
                portafoglioAzionario.remove(azione);
            }else{
                portafoglioAzionario.put(azione, portafoglioAzionario.get(azione)-quantita);
            }
        }else{
            throw new IllegalArgumentException("L'Operatore non possiede Azioni di questa Azienda");
        }
    }

    @Override
    public int compareTo(Operatore o) {
        return this.getNome().compareTo(o.getNome());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Operatore other)){
            return false;
        }
        return nome.equals(other.nome);
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }


}
