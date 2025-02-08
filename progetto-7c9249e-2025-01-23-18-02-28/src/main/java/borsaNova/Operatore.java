package borsaNova;

import java.util.Map.Entry;

import borsaNova.Borsa.Azione;

import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class Operatore implements Comparable<Operatore>{
    /*
     * AF:
     * Un Operatore è rappresentato da:
     * - nome: il nome dell'Operatore
     * - budget: il budget dell'Operatore
     * - azioniPossedute: le azioni possedute dall'Operatore
     * 
     * RI:
     * L'oggetto Operatore deve rispettare le seguenti condizioni:
     * - nome
     *  * non deve essere null o vuoto   
     *  * nome non deve essere già stato usato
     * - budget
     *  * non può essere mai negativo
     * - azioniPossedute
     *  * non può essere null
     *  * non può contenere valori null
     *  * non può contenere valori negativi
     *  * non può contenere chiavi null
     *  
     */

    private static final SortedSet<String> NOMI_USATI = new TreeSet<>();

    /** Stringa nome dell'Operatore */
    private final String nome;
    /** Intero budget dell'Operatore */
    private int budget;
    /** SortedMap per il numero di azioni possedute per una Azienda */
    private SortedMap<Azienda, Integer> azioniPossedute = new TreeMap<>();
    /** SortedSet per le Borsa in cui si possiedono azioni*/
    private SortedSet<Borsa> borseDoveAzioni = new TreeSet<>();

    /**
     * Factory method per creare un'Operatore con il nome specificato e, se non ci sono problemi, ritorna l'Operatore creato e aggiunge il nome alla lista dei nomi usati
     * 
     * @param nome il nome dell'Operatore da creare
     * 
     * @return l'Operatore creato con il nome specificato
     * 
     * @throws NullPointerException se il nome è nullo
     * @throws IllegalArgumentException se il nome è vuoto o se il nome è già stato usato
     */
    public static Operatore of(String nome) {
        if(Objects.requireNonNull(nome, "Nome non può essere nullo").isEmpty()) {
            throw new IllegalArgumentException("Nome non può essere vuoto");
        }
        if(NOMI_USATI.contains(nome)) {
            throw new IllegalArgumentException("Nome già usato: " + nome);
        }
        
        NOMI_USATI.add(nome);
        return new Operatore(nome);
    }

    /**
     * Costruttore privato per creare un'Operatore con il nome specificato e il budget iniziale 0
     * 
     * @param nome
     */
    private Operatore(final String nome) {
        this.nome = nome;
        this.budget = 0;
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
     * Metodo per depositare una somma di denaro nel budget dell'Operatore
     * 
     * @param deposito la somma da depositare nel budget dell'Operatore
     * 
     * @throws IllegalArgumentException se il deposito è negativo
     */
    public void depositaInBudget(int deposito) {
        if(deposito < 0) {
            throw new IllegalArgumentException("Il deposito non può essere negativo");
        }
        this.budget += deposito;

    }

    /**
     * Metodo per prelevare una somma di denaro dal budget dell'Operatore
     * 
     * @param prelievo la somma da prelevare dal budget dell'Operatore
     * 
     * @throws IllegalArgumentException se il prelievo è negativo o maggiore del budget
     */
    public void prelevaDaBudget(int prelievo) {
        if(prelievo < 0) {
            throw new IllegalArgumentException("Il prelievo non può essere negativo");
        }
        if(prelievo > budget) {
            throw new IllegalArgumentException("Il prelievo non può essere maggiore del budget");
        }
        this.budget -= prelievo;
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
     * Metodo per ottenere il numero di azioni possedute per una Azienda
     * 
     * @return il numero di azioni possedute per una Azienda
     */
    public int getCapitaleTotale() {
        int capitale = budget;

        for(Borsa b : borseDoveAzioni) {
            for(b.a)
        }

        return capitale;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Operatore other)) return false;
        return nome.equals(other.nome);
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }

    @Override
    public int compareTo(Operatore other) {
        return nome.compareTo(other.nome);
    }
    
}
