/*package BorsaNova.Entita;

public class Azione {
    private final String azienda; 
    private final String borsa;
    private int prezzoUnitarioAzione;

    public Azione(String azienda, String borsa, int prezzo){
        if(azienda==null || azienda.isBlank()){
            throw new IllegalArgumentException("Il nome dell'azienda deve essere non nullo o vuoto");
        }
        if(borsa==null || borsa.isBlank()){
            throw new IllegalArgumentException("Il nome della borsa deve essere non nullo o vuoto");
        }
        if(prezzo<=0){
            throw new IllegalArgumentException("Il prezzo unitario delle azioni deve essere maggiore di 0");
        }

        this.azienda = azienda;
        this.borsa = borsa;
        this.prezzoUnitarioAzione = prezzo;
    }

    //getvarie


}*/
