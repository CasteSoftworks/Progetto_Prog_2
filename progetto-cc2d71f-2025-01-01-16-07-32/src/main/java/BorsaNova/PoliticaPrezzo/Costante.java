package BorsaNova.PoliticaPrezzo;

/**
 * Classe per rappresentare una politica di variazione nulla
 */
public class Costante implements Politica {
    /** La variazione costante da applicare al prezzo (questa classe è per la variazione nulla) */
    private final int valore;

    /**
     * Costruttore della classe
     * 
     * @param valore il valore costante da applicare al prezzo
     * 
     * @throws IllegalArgumentException se il valore è diverso da 0
     */
    public Costante(int valore) throws IllegalArgumentException {
        if(valore!=0){
            throw new IllegalArgumentException("Il valore deve essere 0");
        }
        this.valore = valore;
    }

    @Override
    public int calcolaPrezzo(int prezzoCorrente, boolean acquisto) {
        return prezzoCorrente + valore;
    }
    
}
