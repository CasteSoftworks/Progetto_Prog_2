package BorsaNova.Entita;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Classe per rappresentare un Operatore
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
    /** Mappa delle azioni possedute dall'operatore (key= nome azienda, value= quantità) */
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
        borsa.modificaAzioni(azienda, quantita);
        portafoglioAzionario.put(azienda.getNome(), portafoglioAzionario.getOrDefault(azienda.getNome(), 0) + quantita);

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


        int guadagno = azienda.getQuotazione(borsa).getPrezzoCorrente() * quantita;
        budget += guadagno;
        borsa.modificaAzioni(azienda, -quantita);

        portafoglioAzionario.put(azienda.getNome(), portafoglioAzionario.get(azienda.getNome()) - quantita);
        if(portafoglioAzionario.get(azienda.getNome())==0){
            portafoglioAzionario.remove(azienda.getNome());
        }
        return true;
    }

    /**
     * Metodo per ottenere il valore totale del portafoglio dell'operatore (valore delle azioni possedute)
     * 
     * @return il valore totale del portafoglio dell'operatore
     */
    public int getValorePortafoglio(){
        int valorePortafoglio=0;
        
        Iterator<Map.Entry<String, Integer>> iterator = portafoglioAzionario.entrySet().iterator();
        
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> azione = iterator.next();
            Azienda azienda = Azienda.getAzienda(azione.getKey());
            for (Borsa borsa : Borsa.getBorse()) {
                valorePortafoglio += azienda.getQuotazione(borsa).getPrezzoCorrente() * azione.getValue();
            }
        }

        return valorePortafoglio;
    }

    /**
     * Metodo per ottenere un operatore
     * 
     * @param nome il nome dell'operatore da ottenere
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

    @Override
    public int compareTo(Operatore o) {
        return this.getNome().compareTo(o.getNome());
    }


}
