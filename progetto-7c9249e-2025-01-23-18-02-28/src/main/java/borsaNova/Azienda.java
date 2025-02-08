package borsaNova;

import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

public class Azienda implements Comparable<Azienda>{
    /*
     * AF:
     * Una Azieda è rappresentata da:
     * - nome: il nome dell'Azienda
     * 
     * RI:
     * L'oggetto Azienda deve rispettare le seguenti condizioni:
     * - nome 
     *  * non deve essere null o vuoto
     *  * nome non deve essere già stato usato
     * 
     */

    /** SortedSet per i nomi già usati */
    private static final SortedSet<String> NOMI_USATI = new TreeSet<>();
    /** Stringa nome della azienda */
    private String nome;
    /** SortedSet per le borse dove l'Azienda è quotata*/
    //private SortedSet<Borsa> borse = new TreeSet<>();

    /**
     * Factory method per creare un'azienda con il nome specificato e, se non ci sono problemi, ritorna l'azienda creata e aggiunge il nome alla lista dei nomi usati
     * 
     * @param nome il nome dell'azienda da creare
     * 
     * @return l'azienda creata con il nome specificato 
     * 
     * @throws NullPointerException se il nome è nullo
     * @throws IllegalArgumentException se il nome è vuoto o se il nome è già stato usato
     */
    public static Azienda of(String nome) {
        if(Objects.requireNonNull(nome, "Nome non può essere nullo").isEmpty()) {
            throw new IllegalArgumentException("Nome non può essere vuoto");
        }
        if (NOMI_USATI.contains(nome)) {
            throw new IllegalArgumentException("Nome già usato: " + nome);
        }
        NOMI_USATI.add(nome);
        return new Azienda(nome);
    }

    /**
     * Costruttore privato per creare un'azienda con il nome specificato
     * 
     * @param nome il nome dell'azienda da creare
     */
    private Azienda(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo per ottenere il nome dell'azienda
     * 
     * @return il nome dell'azienda
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo per quotarsi in una borsa specificata, comunicando alla borsa quante azioni emettere e il prezzo di emissione
     * 
     * @param borsa la borsa dove quotarsi
     * @param azioni il numero di azioni da emettere
     * @param prezzo il prezzo di emissione delle azioni
     */
    public void quotatiInBorsa(/*Borsa borsa,*/ int azioni, int prezzo) {
        throw new UnsupportedOperationException("Not implemented yet");
        //borse.add(borsa);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Azienda other)) return false;
        return nome.equals(other.nome);
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }

    @Override
    public int compareTo(Azienda other) {
        return nome.compareTo(other.nome);
    }
    
}
