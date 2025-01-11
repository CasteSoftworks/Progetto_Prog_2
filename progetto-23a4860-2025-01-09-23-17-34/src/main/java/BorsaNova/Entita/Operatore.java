package BorsaNova.Entita;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
 * </ul>
 */
public class Operatore implements Comparable<Operatore>{
    /**
     * AF:
     * Un Operatore è rappresentato da un nome e un budget 
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
    private static final Map<String, Operatore> operatori = new HashMap<>();
    /** Mappa delle azioni possedute dall'Operatore (key= "nome_azienda nome_borsa", value= quantità) */
    private final Map<String, Integer> portafoglioAzionario = new HashMap<>();
    
    /**
     * Metodo per costruire un operatore (aggiungendolo alla lista degli operatori) o ottenere un operatore già esistente a partire dal nome
     * 
     * @param nome il nome dell'Operatore da creare o recuperare
     * 
     * @return l'Operatore richiesto
     * 
     * @throws IllegalArgumentException se il nome dell'operatore è nullo o vuoto o se è composto solo da spazi bianchi, se il budget dell'operatore è negativo
     */
    public static Operatore factoryOperatore(String nome) throws IllegalArgumentException{
        if(nome==null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome dell'operatore deve essere non nullo o vuoto");
        }

        if(!operatori.containsKey(nome)){
            operatori.put(nome, new Operatore(nome, 0));
        }
        return operatori.get(nome);
    }

    /**
     * Metodo per ottenere un Operatore
     * 
     * @param nome il nome dell'Operatore da ottenere
     * @param budget il budget dell'Operatore da ottenere
     */
    private Operatore(String nome, int budget){
        this.nome = nome;
        this.budget = budget;
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
     * 
     * @param deposito l'importo da depositare
     * 
     * @throws IllegalArgumentException se l'importo del deposito è negativo o pari a 0
     */
    public void depositaInBudget(int deposito) throws IllegalArgumentException{
        if(deposito<=0){
            throw new IllegalArgumentException("Il deposito di denaro non può essere negativo o pari a 0");
        }
        this.budget += deposito;
    }

    /**
     * Metodo per prelevare denaro dal budget
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
     * @param azienda l'azienda a cui appartengono le azioni da acquistare
     * @param borsa la borsa dove acquistare le azioni
     * @param prezzoTot il prezzo totale delle azioni da acquistare
     * 
     * @return la quantità di azioni acquistate
     * 
     * @throws NullPointerException se l'azienda è nulla
     * @throws IllegalArgumentException se la quantità di azioni da acquistare è negativa o se le azioni da acquistare sono maggiori di quelle disponibili nella borsa specificata
     */
    public int acquistaAzione(String a, String b, int prezzoTot) throws NullPointerException, IllegalArgumentException{
        Azienda azienda = Azienda.factoryAzienda(a);
        if(azienda==null){
            throw new NullPointerException("L'azienda non pèuò essere nulla");
        }

        Borsa borsa = Borsa.factoryBorsa(b);
        if(borsa==null){
            throw new NullPointerException("La borsa non può essere nulla");
        }
        
        if(azienda.getQuotazione(borsa.getNome())==null){
            throw new IllegalArgumentException("L'azienda non è quotata nella borsa: " + borsa.getNome());
        }

        if(prezzoTot<=0){
            throw new IllegalArgumentException("Il denaro spendibile per le azioni non può essere negativo o pari a 0");
        }

        int costoPerAzione = azienda.getQuotazione(borsa.getNome()).getPrezzoCorrente();
        int quantita = prezzoTot / costoPerAzione;

        int costo = costoPerAzione * quantita;

        budget -= costo;
        borsa.modificaAzioni(azienda.getNome(), -quantita);
        borsa.allocaAzione(nome, azienda.getNome(), quantita);

        String key=azienda.getNome()+" "+borsa.getNome();
        portafoglioAzionario.put(key, portafoglioAzionario.getOrDefault(key, 0) + quantita);

        return quantita;
    }

    /**
     * Metodo per vendere azioni di un'azienda (se l'Operatore possiede abbastanza azioni)
     * 
     * <p>
     * L'entry sulla mappa portafoglioAzionario rimane solo se dopo l'operazione rimangono delle azioni in possesso dell'Operatore, altrimenti viene rimossa
     * 
     * @param azienda l'azienda a cui appartengono le azioni da vendere
     * @param borsa la borsa dove vendere le azioni
     * @param quantita la quantità di azioni da vendere
     * 
     * @return true se l'operazione è andata a buon fine, false altrimenti
     * 
     * @throws NullPointerException se l'azienda è nulla o l borsa è nulla
     * @throws IllegalArgumentException se la quantità di azioni da vendere è negativa o nulla, se l'operatore non possiede azioni di questa azienda, se il costo delle azioni da vendere è maggiore del budget
     */
    public boolean vendeAzione(String a, String b, int quantita) throws NullPointerException, IllegalArgumentException{
        Azienda azienda = Azienda.factoryAzienda(a);
        if(azienda==null){
            throw new NullPointerException("L'azienda non può essere nulla");
        }

        Borsa borsa = Borsa.factoryBorsa(b);
        if(borsa==null){
            throw new NullPointerException("La borsa non può essere nulla");
        }
        if(azienda.getQuotazione(b)==null){
            throw new IllegalArgumentException("L'azienda non è quotata nella borsa: " + b);
        }

        if(quantita<=0){
            throw new IllegalArgumentException("La quantità di azioni da vendere non può essere negativa o nulla");
        }

        String key=azienda.getNome()+" "+borsa.getNome();

        if(!portafoglioAzionario.containsKey(key)){
            throw new IllegalArgumentException("L'operatore non possiede azioni di questa azienda");
        }

        if(portafoglioAzionario.get(key)<quantita){
            throw new IllegalArgumentException("L'operatore non possiede abbastanza azioni di questa azienda");
        }


        int guadagno = azienda.getQuotazione(b).getPrezzoCorrente() * quantita;
        budget += guadagno;
        borsa.modificaAzioni(azienda.getNome(), +quantita);
        borsa.allocaAzione(nome, azienda.getNome(), -quantita);

        portafoglioAzionario.put(key, portafoglioAzionario.get(key) - quantita);
        if(portafoglioAzionario.get(key)==0){
            portafoglioAzionario.remove(key);
        }
        return true;
    }

    /**
     * Metodo per ottenere il valore totale del portafoglio dell'Operatore (valore delle azioni possedute)
     * 
     * @return il valore totale del portafoglio azionario dell'Operatore
     * 
     * @throws IllegalArgumentException se factoryAzienda o factoryBorsa incontrano problemi
     */
    public int getValorePortafoglio() throws IllegalArgumentException{
        int valorePortafoglio=0;
        Iterator<Map.Entry<String, Integer>> it = portafoglioAzionario.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Integer> entry = it.next();
            String[] tokens = entry.getKey().split(" ");
            Azienda a = Azienda.factoryAzienda(tokens[0]);
            valorePortafoglio += a.getQuotazione(tokens[1]).getPrezzoCorrente() * entry.getValue();
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
