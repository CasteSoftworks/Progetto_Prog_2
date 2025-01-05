/*

Copyright 2024 Massimo Santini

This file is part of "Programmazione 2 @ UniMI" teaching material.

This is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This material is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this file.  If not, see <https://www.gnu.org/licenses/>.

*/

package clients;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import BorsaNova.Entita.Azienda;
import BorsaNova.Entita.Borsa;
import BorsaNova.Entita.Operatore;

/** Client di test per alcune funzionalità relative alle <strong>borse</strong>. */
public class PoliticaPrezzoClient {

  /** . */
  private PoliticaPrezzoClient() {}

    /*-
   * Scriva un [@code main} che riceve come parametri sulla linea di comando
   *
   *      nome_borsa valore budget_iniziale
   *
   * il secondo parametro è un intero che determina la politica di prezzo della
   * borsa come segue: se è positivo, la politica è ad incremento costante pari
   * a tale valore, viceversa se è negativo, la politica è a decremento costante
   * pari al valore assoluto di tale valore.
   *
   * Il programma quindi legge dal flusso in ingresso una sequenza di due gruppi
   * di linee (separati tra loro dalla linea contenente solo --) ciascuno della
   * forma descritta di seguito:
   *
   *     nome_azienda numero prezzo_unitario
   *     ...
   *     --
   *     b nome_azienda prezzo_totale 
   *     ... [oppure] 
   *     s nome_azienda numero_azioni
   *
   * in base al contenuto del primo blocco, quota le azioni delle aziende
   * specificate nella borsa (definita dal primo parametro sulla linea di
   * comando) secondo il numero e prezzo unitario specificati, in base al
   * contenuto del secondo blocco — una volta creato un operatore (di nome
   * qualunque, con il budget iniziale specificato dal terzo parametro sulla
   * linea di comando) — esegue le operazioni a seconda che il carattere che
   * segue il nome dell'operatore sia: 
   *
   * - b compra azioni (dell'azienda specificata, impegnano il prezzo totale
   *   specificato),
   * - s vende azioni (dell'azienda specificata, nel numero specificato).
   *
   * Al termine della lettura il programma emette nel flusso d'uscita l'elenco
   * delle azioni (in ordine alfabetico) seguite dal prezzo (separato da una
   * virgola).
   */

  public static void main(String[] args) {
    Set<Azienda> azioni = new TreeSet<>();
    Scanner scanner = new Scanner(System.in);

    String nomeBorsa = args[0];
    int valore = Integer.parseInt(args[1]);
    int budgetIniziale = Integer.parseInt(args[2]);
      System.err.println("voglio creare una borsa di nome "+nomeBorsa+"\ncon politica di prezzo "+valore+"\ne un budget iniziale di "+budgetIniziale);


    Borsa borsa = new Borsa(nomeBorsa);
      System.err.println("\tcreata la borsa "+borsa.getNome());
    borsa.setPoliticaPrezzo(valore);
      System.err.println("\t\tsettata la politica di prezzo "+valore+" nella borsa "+borsa.getNome());

    borsa.aggiungiAllaLista();
      System.err.println("\t\taggiunta la borsa "+borsa.getNome()+" alla lista delle borse");

    Operatore operatore = Operatore.factoryOperatore("operatore", budgetIniziale);
      System.err.println("\tcreato l'operatore "+operatore.getNome()+" con budget iniziale "+operatore.getBudget());


    /*
     * primo blocco
     */
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line.equals("--")) {
          System.err.println("-----------------FINE PRIMO BLOCCO-----------------");
        break;
      }
      String[] tokens = line.split(" ");
      String nomeAzienda = tokens[0];
      int numero = Integer.parseInt(tokens[1]);
      int prezzoUnitario = Integer.parseInt(tokens[2]);
        System.err.println("voglio creare un'azienda di nome "+nomeAzienda+"\ncon "+numero+" azioni a "+prezzoUnitario+" nella borsa "+borsa.getNome());

      Azienda az = Azienda.factoryAzienda(nomeAzienda);
        System.err.println("\tcreata l'azienda "+az.getNome());
      azioni.add(az);
        System.err.println("\t\taggiunta l'azienda "+az.getNome()+" all'insieme delle azioni delle aziende");

      borsa.quotaAzienda(az, prezzoUnitario);
        System.err.println("\t\tquotata l'azienda "+az.getNome()+" a "+prezzoUnitario+" nella borsa "+borsa.getNome());
      borsa.modificaAzioni(az, numero);
        System.err.println("\t\tErogate le "+numero+" azioni di "+az.getNome()+" nella borsa "+borsa.getNome());
    }

    /*
     * secondo blocco
     */
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if (line.equals("--")) {
          System.err.println("-----------------FINE SECONDO BLOCCO-----------------");
        break;
      }
      String[] tokens = line.split(" ");
      String nomeAzienda = tokens[1];
      int prezzoTotale = Integer.parseInt(tokens[2]);

      Azienda az = Azienda.factoryAzienda(nomeAzienda);

      if(tokens[0].equals("b")) {
          System.err.println("compro azioni");
          System.err.println("\tcompro azioni di "+az.getNome()+" nella borsa "+borsa.getNome());
          int prezzoPre=az.getQuotazione(borsa).getPrezzoCorrente();
        int quanti=operatore.acquistaAzione(az, borsa, prezzoTotale);
          System.err.println("\t\tcomprate "+quanti+" azioni di "+az.getNome()+" a "+prezzoTotale+" nella borsa "+borsa.getNome());
          System.err.println("\t\tIl prezzo dovrebbe essere salito di "+valore+"\n\t\tprima era "+prezzoPre+" ora è "+az.getQuotazione(borsa).getPrezzoCorrente());
      } else {
          System.err.println("vendo azioni");
        int numeroAzioni = Integer.parseInt(tokens[3]);
          System.err.println("\tvendo "+numeroAzioni+" azioni di "+az.getNome()+" nella borsa "+borsa.getNome());
        boolean ok=operatore.vendeAzione(az, borsa, numeroAzioni);
        if(ok){
            System.err.println("\t\tvendute "+numeroAzioni+" azioni di "+az.getNome()+" nella borsa "+borsa.getNome());
        }
      }
    }

    scanner.close();
      System.err.println("fine lettura");

    //output
    for (Azienda az : azioni) {
      System.out.println(az.getNome() + ", " + borsa.getQuotazioneAzienda(az).getPrezzoCorrente());
    }
  }

}
