package BorsaNova.Entita;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import BorsaNova.Entita.Borsa.Azione;

/**
 * Classe per rappresentare un Operatore
 * 
 * <p>
 * Un Operatore ha un nome, che lo identifica univocamente, e un budget.
 * Un Operatore può depositare denaro nel budget, prelevare denaro dal budget, acquistare azioni di un'azienda, vendere azioni di un'azienda, ottenere il valore del portafoglio azionario e il patrimonio totale.
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
public class Operatore implements Comparable<Operatore>{
    /**
     * AF:
     * Un Operatore è rappresentato da:
     * - nome: il nome dell'Operatore
     * - budget: il budget dell'Operatore
     * - portafoglioAzionario: una mappa delle azioni possedute dall'Operatore (key= "nome_azienda"+" "+"nome_borsa", value= quantità) 
     *
     * RI:
     * L'oggetto Operatore deve rispettare le seguenti condizioni:
     * - nome non null, stringa vuota o composta solo da soli spazi bianchi
     * - budget maggiore o uguale a 0 (inizialmente 0)
     */
    
    /** Il {@code nome} dell'operatore */
    private final String nome;
    /** Il budget dell'Operatore */
    private int budget;
    /** Mappa degli Operatori (key= nome_operatore, value= operatore stesso) */
    /** Mappa delle azioni possedute dall'Operatore (key= "nome_azienda nome_borsa", value= quantità) */
    //private final Map<String, Integer> portafoglioAzionario = new HashMap<>(); //  QUESTO È MERDA

    //SALVO UN TREEMAP DI AZIONE E NUMERO
    private final Map<Azione, Integer> portafoglioAzionario2 = new TreeMap<>();

    private static final SortedSet<String> NOMI_USATI = new TreeSet<>();
    private static final SortedSet<Operatore> operatori = new TreeSet<>();

    
    /**
     * Metodo per costruire un operatore 
     * 
     * <p>
     * Modified la mappa {@code operatori} se l'Operatore non esiste già
     * 
     * @param nome il nome dell'Operatore da creare o recuperare
     * 
     * @return l'Operatore richiesto
     * 
     * @throws IllegalArgumentException se il nome dell'operatore è nullo o vuoto o se è composto solo da spazi bianchi, se il budget dell'operatore è negativo
     */
    public static Operatore of(String nome) throws IllegalArgumentException{
        if(nome==null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome dell'operatore deve essere non nullo o vuoto");
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
     * @param budget il budget dell'Operatore da ottenere
     */
    private Operatore(String nome){
        this.nome = nome;
    }

    public static Operatore getOperatoreDaNome(String nome){
        if(nome==null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome dell'operatore deve essere non nullo o vuoto");
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
     * Metodo per acquistare azioni di un'azienda
     * 
     * <p>
     * Modifies {@code portafoglioAzionario} dell'Operatore
     * 
     * @param a il nome della Azienda a cui appartengono le azioni da acquistare
     * @param b il nome della Borsa dove acquistare le azioni (attraverso il nome modifica la mappa {@code azioni} di Borsa e la mappa {@code allocazione} della Borsa)
     * @param prezzoTot il prezzo totale delle azioni da acquistare
     * 
     * @return la quantità di azioni acquistate
     * 
     * @throws IllegalArgumentException se la quantità di azioni da acquistare è negativa, se le azioni da acquistare sono maggiori di quelle disponibili nella borsa specificata o se factoryAzienda o factoryBorsa incorrono in una IllegalArgumentException
     */
    public void acquistaAzione(Azienda a, Borsa b, int prezzoTot) throws IllegalArgumentException{        
        if(a==null){
            throw new NullPointerException("L'azienda non può essere nulla");
        }

        if(b==null){
            throw new NullPointerException("La borsa non può essere nulla");
        }

        if(prezzoTot<=0){
            throw new IllegalArgumentException("Il denaro spendibile per le azioni non può essere negativo o pari a 0");
        }

        System.err.println("\tBudget: "+this.budget);
        budget -= prezzoTot;
        System.err.println("\tBudget: "+this.budget);
        int resto=b.compraAzione(this, a, prezzoTot);
        depositaInBudget(resto);
        System.err.println("\tBudget: "+this.budget);
    }

    /**
     * Metodo per vendere azioni di un'azienda (se l'Operatore possiede abbastanza azioni)
     * 
     * <p>
     * Modifies {@code portafoglioAzionario} dell'Operatore aggiungendo o rimuovendo azioni o eliminando la chiave se non ci sono più azioni
     * 
     * @param a il nome della Azienda a cui appartengono le azioni da vendere
     * @param b il nome della Borsa dove vendere le azioni
     * @param quantita la quantità di azioni da vendere
     * 
     * @return true se l'operazione è andata a buon fine, false altrimenti
     * 
     * @throws NullPointerException se l'azienda è nulla o l borsa è nulla
     * @throws IllegalArgumentException se la quantità di azioni da vendere è negativa o nulla, se l'operatore non possiede azioni di questa azienda, se il costo delle azioni da vendere è maggiore del budget
     */
    /*public boolean vendeAzione(Azienda azienda, Borsa borsa, int quantita) throws NullPointerException, IllegalArgumentException{
        if(azienda==null){
            throw new NullPointerException("L'azienda non può essere nulla");
        }

        if(borsa==null){
            throw new NullPointerException("La borsa non può essere nulla");
        }

        if(quantita<=0){
            throw new IllegalArgumentException("La quantità di azioni da vendere non può essere negativa o nulla");
        }

        if(portafoglioAzionario2.get(borsa.new Azione(azienda, 1, 1))==null){
            throw new IllegalArgumentException("L'operatore non possiede azioni di questa azienda");
        }
        return true;
    }*/

    /**
     * Metodo per ottenere il valore totale del portafoglio dell'Operatore (valore delle azioni possedute)
     * 
     * @return il valore totale del portafoglio azionario dell'Operatore
     * 
     * @throws IllegalArgumentException se factoryAzienda o factoryBorsa incontrano problemi
     */
    public int getValorePortafoglio() throws IllegalArgumentException{
        int valorePortafoglio=0;
        Iterator<Map.Entry<Azione, Integer>> it = portafoglioAzionario2.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Azione, Integer> entry = it.next();
            Azienda a = entry.getKey().getAzienda();
            valorePortafoglio += a.getQuotazione(entry.getKey().getBorsa()) * entry.getValue();
        }

        return valorePortafoglio;
    }

    /**
     * Metodo per ottenere il patrimonio totale dell'Operatore (budget + valore del portafoglio)
     * 
     * @return la somma del budget e del valore del portafoglio dell'Operatore
     * 
     * @throws IllegalArgumentException se getValorePortafoglio incontra problemi
     */
    public int getCapitaleTotale() throws IllegalArgumentException{
        return budget + getValorePortafoglio();
    }

    /**
     * Metodo override per confrontare due Operatori in base al nome
     * 
     * <p>
     * Automaticamente generato da Github Copilot sulla base di compareTo scritto in Borsa
     * 
     * @param o l'Operatore con cui confrontare
     * 
     * @return 0 se i due Operatori sono uguali, un numero negativo se l'Operatore è minore di o, un numero positivo altrimenti
     */
    @Override
    public int compareTo(Operatore o) {
        return this.getNome().compareTo(o.getNome());
    }


}
