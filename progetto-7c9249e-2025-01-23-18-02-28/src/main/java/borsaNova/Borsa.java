package borsaNova;

import java.util.*;

/**
 * AF:
 * Una Borsa è rappresentata da:
 * - nome: il nome della Borsa
 * - aziende: un insieme di aziende quotate in questa Borsa
 * - azioni: un insieme di azioni emesse dalle aziende
 * - politicaPrezzo: la politica di prezzo corrente della Borsa
 *
 * RI:
 * - Il nome della Borsa non può essere nullo o vuoto
 * - La politica di prezzo non può essere nulla
 * - Un'azienda può essere quotata una sola volta
 */
public class Borsa implements Comparable<Borsa> {
    private final String nome;
    private final List<Azione> azioni = new ArrayList<>();
    //private PoliticaPrezzo politicaPrezzo;

    private static class Azione {
        private final Azienda azienda;
        private int quantitaDisponibile;
        private int prezzo;
        private final List<Allocazione> allocazioni = new ArrayList<>();

        public Azione(Azienda azienda, int quantita, int prezzo) {
            this.azienda = azienda;
            this.quantitaDisponibile = quantita;
            this.prezzo = prezzo;
        }

        public Azienda getAzienda() {
            return azienda;
        }

        public int getQuantitaDisponibile() {
            return quantitaDisponibile;
        }

        public void modificaQuantitaDisponibile(int delta) {
            this.quantitaDisponibile += delta;
        }

        public int getPrezzo() {
            return prezzo;
        }

        public void setPrezzo(int prezzo) {
            this.prezzo = prezzo;
        }

        public List<Allocazione> getAllocazioni() {
            return allocazioni;
        }
    }

    private static class Allocazione {
        private final String operatore;
        private int quantita;

        public Allocazione(String operatore, int quantita) {
            this.operatore = operatore;
            this.quantita = quantita;
        }

        public String getOperatore() {
            return operatore;
        }

        public int getQuantita() {
            return quantita;
        }

        public void modificaQuantita(int delta) {
            this.quantita += delta;
        }
    }

    public Borsa(String nome/*, PoliticaPrezzo politicaPrezzo*/) {
        if (Objects.requireNonNull(nome, "Il nome della Borsa non può essere nullo").isEmpty()) {
            throw new IllegalArgumentException("Il nome della Borsa non può essere vuoto");
        }
        this.nome = nome;
        //this.politicaPrezzo = Objects.requireNonNull(politicaPrezzo, "La politica di prezzo non può essere nulla");
    }

    public String getNome() {
        return nome;
    }

    public void quotaAzienda(Azienda azienda, int azioni, int prezzo) {
        if (azioni <= 0 || prezzo <= 0) {
            throw new IllegalArgumentException("Il numero di azioni e il prezzo devono essere positivi");
        }
        for (Azione a : this.azioni) {
            if (a.getAzienda().equals(azienda)) {
                throw new IllegalArgumentException("L'azienda è già quotata in questa Borsa");
            }
        }
        this.azioni.add(new Azione(azienda, azioni, prezzo));
    }

    public void compraAzioni(String operatore, Azienda azienda, int quantita) {
        Azione azione = trovaAzione(azienda);
        if (quantita > azione.getQuantitaDisponibile()) {
            throw new IllegalArgumentException("Non ci sono abbastanza azioni disponibili");
        }
        azione.modificaQuantitaDisponibile(-quantita);
        Allocazione allocazione = trovaAllocazione(azione, operatore);
        allocazione.modificaQuantita(quantita);
        //aggiornaPrezzo(azione, quantita, true);
    }

    public void vendiAzioni(String operatore, Azienda azienda, int quantita) {
        Azione azione = trovaAzione(azienda);
        Allocazione allocazione = trovaAllocazione(azione, operatore);
        if (quantita > allocazione.getQuantita()) {
            throw new IllegalArgumentException("L'operatore non possiede abbastanza azioni per venderle");
        }
        allocazione.modificaQuantita(-quantita);
        azione.modificaQuantitaDisponibile(quantita);
        //aggiornaPrezzo(azione, quantita, false);
    }

    private Azione trovaAzione(Azienda azienda) {
        for (Azione azione : azioni) {
            if (azione.getAzienda().equals(azienda)) {
                return azione;
            }
        }
        throw new IllegalArgumentException("L'azienda non è quotata in questa Borsa");
    }

    private Allocazione trovaAllocazione(Azione azione, String operatore) {
        for (Allocazione allocazione : azione.getAllocazioni()) {
            if (allocazione.getOperatore().equals(operatore)) {
                return allocazione;
            }
        }
        Allocazione nuovaAllocazione = new Allocazione(operatore, 0);
        azione.getAllocazioni().add(nuovaAllocazione);
        return nuovaAllocazione;
    }

    /*private void aggiornaPrezzo(Azione azione, int quantita, boolean acquisto) {
        int nuovoPrezzo = politicaPrezzo.aggiornaPrezzo(azione.getPrezzo(), quantita, acquisto);
        azione.setPrezzo(nuovoPrezzo);
    }

    public void cambiaPoliticaPrezzo(PoliticaPrezzo nuovaPolitica) {
        this.politicaPrezzo = Objects.requireNonNull(nuovaPolitica, "La politica di prezzo non può essere nulla");
    }*/

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Borsa other)) return false;
        return nome.equals(other.nome);
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }

    @Override
    public int compareTo(Borsa other) {
        return nome.compareTo(other.nome);
    }
}
