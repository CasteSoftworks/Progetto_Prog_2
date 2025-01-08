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
 * <li>Github Copilot - GTP4.0 (correzione errori e problemi, generazione di parte del codice e autocompletamento javadoc)</li>
 * <li>ChatGTP 4.o (correzione errori e problemi)</li>
 * <li>StackOverflow (correzione errori e problemi)</li>
 * <li>Gabriele Favazzi (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * <li>Simone Coccè (compagno di corso, aiuto sulla formalità della documentazione e del codice)</li>
 * <li>Piero Chobanyan (compagno di corso, logica iniziale)</li>
 * </ul>
 */
public class Operatore implements Comparable<Operatore>{
    /**
     * AF:
     * AF(nome, budget) = Un operatore rappresentato da:
     * - nome: è il nome dell'operatore
     * - budget: è il budget dell'operatore
     *
     * RI:
     * RI(nome, budget) = L'oggetto operatore rispetta le seguenti condizioni:
     * - nome non è null, non è una stringa vuota o composta solo da spazi bianchi
     * - budget è maggiore o uguale a 0
     */
    
    /** Il nome dell'operatore */
    private final String nome;
    /** Mappa degli operatori (key= nome operatore, value= operatore stesso) */
    private static final Map<String, Operatore> operatori = new HashMap<>();
    /** Il budget dell'operatore */
    private int budget;
    /** Mappa delle azioni possedute dall'operatore (key= "nome_azienda nome_borsa", value= quantità) */
    private final Map<String, Integer> portafoglioAzionario = new HashMap<>();
    
    /**
     * Metodo per costruire un operatore
     * 
     * @param nome il nome dell'operatore
     * @param budget il budget dell'operatore
     * 
     * @return l'operatore costruito
     * 
     * @throws IllegalArgumentException se il nome dell'operatore è nullo o vuoto o se è composto solo da spazi bianchi, se il budget dell'operatore è negativo
     */
    public static Operatore factoryOperatore(String nome, int budget) throws IllegalArgumentException{
        if(nome==null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome dell'operatore deve essere non nullo o vuoto");
        }
        if(budget<0){
            throw new IllegalArgumentException("Il budget dell'operatore deve essere maggiore o uguale a 0");
        }
        return operatori.computeIfAbsent(nome, op -> new Operatore(nome, budget));
    }

    /**
     * Metodo per ottenere un operatore
     * 
     * @param nome il nome dell'operatore da ottenere
     * @param budget il budget dell'operatore da ottenere
     */
    private Operatore(String nome, int budget){
        this.nome = nome;
        this.budget = budget;
    }

    /**
     * Metodo per ottenere il nome dell'operatore
     * 
     * @return il nome dell'operatore
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo per ottenere il budget dell'operatore
     * 
     * @return il budget dell'operatore
     */
    public int getBudget() {
        return budget;
    }

    /**
     * Metodo per depositare denaro nel budget
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
    public int acquistaAzione(Azienda azienda, Borsa borsa, int prezzoTot) throws NullPointerException, IllegalArgumentException{
        if(azienda==null){
            throw new NullPointerException("L'azienda non pèuò essere nulla");
        }

        if(azienda.getQuotazione(borsa)==null){
            throw new IllegalArgumentException("L'azienda non è quotata nella borsa: " + borsa.getNome());
        }

        if(prezzoTot<=0){
            throw new IllegalArgumentException("Il denaro spendibile per le azioni non può essere negativo o pari a 0");
        }

        int costoPerAzione = azienda.getQuotazione(borsa).getPrezzoCorrente();
        int quantita = prezzoTot / costoPerAzione;

        int costo = costoPerAzione * quantita;

        budget -= costo;
        borsa.modificaAzioni(azienda, -quantita);
        borsa.allocaAzione(nome, azienda.getNome(), quantita);

        String key=azienda.getNome()+" "+borsa.getNome();
        portafoglioAzionario.put(key, portafoglioAzionario.getOrDefault(key, 0) + quantita);

        return quantita;
    }

    /**
     * Metodo per vendere azioni di un'azienda (se l'operatore possiede abbastanza azioni)
     * L'entry sulla mappa rimane solo se dopo l'operazione rimangono delle azioni in possesso dell'operatore, altrimenti viene rimossa
     * 
     * @param azienda l'azienda a cui appartengono le azioni da vendere
     * @param borsa la borsa dove vendere le azioni
     * @param quantita la quantità di azioni da vendere
     * 
     * @return true se l'operazione è andata a buon fine, false altrimenti
     * 
     * @throws NullPointerException se l'azienda è nulla
     * @throws IllegalArgumentException se la quantità di azioni da vendere è negativa o nulla, se l'operatore non possiede azioni di questa azienda, se il costo delle azioni da vendere è maggiore del budget
     */
    public boolean vendeAzione(Azienda azienda, Borsa borsa, int quantita) throws NullPointerException, IllegalArgumentException{
        if(azienda==null){
            throw new NullPointerException("L'azienda non può essere nulla");
        }

        if(borsa==null){
            throw new NullPointerException("La borsa non può essere nulla");
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


        int guadagno = azienda.getQuotazione(borsa).getPrezzoCorrente() * quantita;
        budget += guadagno;
        borsa.modificaAzioni(azienda, +quantita);
        borsa.allocaAzione(nome, azienda.getNome(), -quantita);

        portafoglioAzionario.put(key, portafoglioAzionario.get(key) - quantita);
        if(portafoglioAzionario.get(key)==0){
            portafoglioAzionario.remove(key);
        }
        return true;
    }

    /**
     * Metodo per ottenere il valore totale del portafoglio dell'operatore (valore delle azioni possedute)
     * 
     * @return il valore totale del portafoglio dell'operatore
     * 
     * @throws IllegalArgumentException se getAzienda o getBorsa incontrano problemi
     */
    public int getValorePortafoglio() throws IllegalArgumentException{
        int valorePortafoglio=0;
        Iterator<Map.Entry<String, Integer>> it = portafoglioAzionario.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Integer> entry = it.next();
            String[] tokens = entry.getKey().split(" ");
            Azienda a = Azienda.getAzienda(tokens[0]);
            Borsa b = Borsa.getBorsa(tokens[1]);
            valorePortafoglio += a.getQuotazione(b).getPrezzoCorrente() * entry.getValue();
        }

        return valorePortafoglio;
    }

    /**
     * Metodo per ottenere il patrimonio dell'operatore (budget + valore del portafoglio)
     * 
     * @return la somma del budget e del valore del portafoglio dell'operatore
     * 
     * @throws IllegalArgumentException se getValorePortafoglio incontra problemi
     */
    public int getCapitaleTotale() throws IllegalArgumentException{
        return budget + getValorePortafoglio();
    }

    /**
     * Metodo per ottenere un operatore
     * 
     * @param nome il nome dell'operatore da ottenere
     * 
     * @return l'operatore richiesto
     * 
     * @throws IllegalArgumentException se il nome dell'operatore è nullo o vuoto o se è composto solo da spazi bianchi, se l'operatore richiesto non esiste
     */
    public static Operatore getOperatore(String nome) throws IllegalArgumentException{
        if(nome==null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome dell'operatore deve essere non nullo o vuoto");
        }
        if(!operatori.containsKey(nome)){
            throw new IllegalArgumentException("L'operatore richiesto non esiste");
        }
        return operatori.get(nome);
    }

    /**
     * Metodo override per confrontare due operatori in base al nome
     * 
     * @param o l'operatore con cui confrontare
     * 
     * @return 0 se i due operatori sono uguali, un numero negativo se l'operatore è minore di o, un numero positivo altrimenti
     */
    @Override
    public int compareTo(Operatore o) {
        return this.getNome().compareTo(o.getNome());
    }


}
