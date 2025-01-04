package BorsaNova.Entita;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe per rappresentare un <p>operatore</p>
 */
public class Operatore {
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
    /** Mappa delle azioni possedute dall'operatore (key= nome azienda, value= quantità) */
    private final Map<String, Integer> portafoglioAzionario = new HashMap<>();
    
    /**
     * Metodo per costruire un operatore
     * 
     * @param nome il nome dell'operatore
     * 
     * @return l'operatore costruito
     * 
     * @throws IllegalArgumentException se il nome dell'operatore è nullo o vuoto o se è composto solo da spazi bianchi
     */
    public static Operatore factoryOperatore(String nome){
        if(nome==null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome dell'operatore deve essere non nullo o vuoto");
        }
        return operatori.computeIfAbsent(nome, op -> new Operatore(nome));
    }

    /**
     * Metodo per ottenere un operatore
     * 
     * @param nome il nome dell'operatore da ottenere
     */
    private Operatore(String nome) {
        this.nome = nome;
        this.budget = 0;
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
     * @throws IllegalArgumentException se l'importo del deposito è negativo
     */
    public void depositaInBudget(int deposito) throws IllegalArgumentException{
        if(deposito<=0){
            throw new IllegalArgumentException("Il deposito di denaro non può essere negativo");
        }
        budget += deposito;
    }

    /**
     * Metodo per prelevare denaro dal budget
     * 
     * @param prelievo l'importo da prelevare
     * 
     * @throws IllegalArgumentException se l'importo del prelievo è negativo, se l'importo del prelievo è maggiore del budget
     */
    public void prelievoDalBudget(int prelievo) throws IllegalArgumentException{
        if(prelievo<=0){
            throw new IllegalArgumentException("Il prelievo di denaro non può essere negativo");
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
     * @param quantita la quantità di azioni da acquistare
     * 
     * @throws NullPointerException se l'azienda è nulla
     * @throws IllegalArgumentException se la quantità di azioni da acquistare è negativa o se le azioni da acquistare sono maggiori di quelle disponibili nella borsa specificata
     */
    public void acquistaAzione(Azienda azienda, Borsa borsa, int quantita) throws NullPointerException, IllegalArgumentException{
        if(azienda==null){
            throw new NullPointerException("L'azienda non pèuò essere nulla");
        }

        if(quantita<=0){
            throw new IllegalArgumentException("La quantità di azioni da acquistare non può essere negativa");
        }

        if(!borsa.compraAzione(azienda, quantita)){
            throw new IllegalArgumentException("Le azioni da acquistare non devono essere maggiori di quelle disponibili");
        }

        int costo = azienda.getQuotazione(borsa).getPrezzoCorrente() * quantita;

        budget -= costo;
        portafoglioAzionario.put(azienda.getNome(), portafoglioAzionario.getOrDefault(azienda.getNome(), 0) + quantita);
    }

    /**
     * Metodo per vendere azioni di un'azienda (se l'operatore possiede abbastanza azioni)
     * L'entry sulla mappa rimane solo se dopo l'operazione rimangono delle azioni in possesso dell'operatore, altrimenti viene rimossa
     * 
     * @param azienda l'azienda a cui appartengono le azioni da vendere
     * @param borsa la borsa dove vendere le azioni
     * @param quantita la quantità di azioni da vendere
     * 
     * @throws NullPointerException se l'azienda è nulla
     * @throws IllegalArgumentException se la quantità di azioni da vendere è negativa o nulla, se l'operatore non possiede azioni di questa azienda, se il costo delle azioni da vendere è maggiore del budget
     */
    public void vendeAzione(Azienda azienda, Borsa borsa, int quantita) throws NullPointerException, IllegalArgumentException{
        if(azienda==null){
            throw new NullPointerException("L'azienda non pèuò essere nulla");
        }

        if(quantita<=0){
            throw new IllegalArgumentException("La quantità di azioni da vendere non può essere negativa o nulla");
        }

        if(!portafoglioAzionario.containsKey(azienda.getNome())){
            throw new IllegalArgumentException("L'operatore non possiede azioni di questa azienda");
        }

        if(portafoglioAzionario.get(azienda.getNome())<quantita){
            throw new IllegalArgumentException("L'operatore non possiede abbastanza azioni di questa azienda");
        }

        if(!borsa.reimmettiAzione(azienda, quantita)){
            throw new IllegalArgumentException("L'azienda non è quoatata nella borsa: " + borsa.getNome());
        }

        int guadagno = azienda.getQuotazione(borsa).getPrezzoCorrente() * quantita;
        budget += guadagno;
        
        portafoglioAzionario.put(azienda.getNome(), portafoglioAzionario.get(azienda.getNome()) - quantita);
        if(portafoglioAzionario.get(azienda.getNome())==0){
            portafoglioAzionario.remove(azienda.getNome());
        }
    }

    /**
     * Metodo per ottenere il valore totale del portafoglio dell'operatore (budget + valore delle azioni possedute)
     * 
     * @return il valore totale del portafoglio dell'operatore
     */
    public int getBudgetTotale(){
        int valorePortafoglio=0;
        for(Map.Entry<String, Integer> azione : portafoglioAzionario.entrySet()){ //rifalla con Iterator
            Azienda azienda = Azienda.getAzienda(azione.getKey());
            for(Borsa borsa : Borsa.getBorse()){
                valorePortafoglio += azienda.getQuotazione(borsa).getPrezzoCorrente() * azione.getValue();
            }
        }

        return budget + valorePortafoglio;
    }


}
