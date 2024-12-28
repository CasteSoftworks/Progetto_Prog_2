package BorsaNova.Entita;

import java.util.HashMap;
import java.util.Map;

public class Operatore {

    private static final Map<String, Operatore> operatori = new HashMap<>();
    private final String nome;
    private int budget;
    private final Map<String, Integer> portafoglioAzionario = new HashMap<>();
    
    /**
     * Metodo per costruire un operatore
     * 
     * @param nome il nome dell'operatore
     * 
     * @return l'operatore costruito
     * 
     * @throws IllegalArgumentException se il nome dell'operatore è nullo o vuoto
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
     * 
     * @return l'operatore richiesto
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
     * @throws IllegalArgumentException se l'importo del deposito è negativo o nullo
     */
    public void depositaInBudget(int deposito){
        if(deposito<=0){
            throw new IllegalArgumentException("Il deposito di denaro non può essere negativo o nullo");
        }
        budget += deposito;
    }

    /**
     * Metodo per prelevare denaro dal budget
     * 
     * @param prelievo l'importo da prelevare
     * 
     * @throws IllegalArgumentException se l'importo del prelievo è negativo o nullo, se l'importo del prelievo è maggiore del budget
     */
    public void prelievoDalBudget(int prelievo){
        if(prelievo<=0){
            throw new IllegalArgumentException("Il prelievo di denaro non può essere negativo o nullo");
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
     * @throws IllegalArgumentException se la quantità di azioni da acquistare è negativa o nulla, se il costo delle azioni da acquistare è maggiore del budget
     */
    public void acquistaAzione(Azienda azienda, Borsa borsa, int quantita){
        if(azienda==null){
            throw new NullPointerException("L'azienda non pèuò essere nulla");
        }

        if(quantita<=0){
            throw new IllegalArgumentException("La quantità di azioni da acquistare non può essere negativa o nulla");
        }

        int costo = azienda.getQuotazione(borsa).getPrezzoCorrente() * quantita;

        /*if(costo>budget){ //l'operatore non fa sta roba
            throw new IllegalArgumentException("Il costo delle azioni da acquistare non può essere maggiore del budget");
        }*/

        budget -= costo;
        portafoglioAzionario.put(azienda.getNome(), portafoglioAzionario.getOrDefault(azienda.getNome(), 0) + quantita);
    }

    /**
     * Metodo per vendere azioni di un'azienda
     * 
     * @param azienda l'azienda a cui appartengono le azioni da vendere
     * @param borsa la borsa dove vendere le azioni
     * @param quantita la quantità di azioni da vendere
     * 
     * @throws NullPointerException se l'azienda è nulla
     * @throws IllegalArgumentException se la quantità di azioni da vendere è negativa o nulla, se l'operatore non possiede azioni di questa azienda, se il costo delle azioni da vendere è maggiore del budget
     */
    public void vendeAzione(Azienda azienda, Borsa borsa, int quantita){
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
        portafoglioAzionario.put(azienda.getNome(), portafoglioAzionario.get(azienda.getNome()) - quantita);
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
