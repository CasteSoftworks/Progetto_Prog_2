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
    String nomeBorsa = args[0];
    int valore = Integer.parseInt(args[1]);
    int budgetIniziale = Integer.parseInt(args[2]);

    Borsa borsa = Borsa.factoryBorsa(nomeBorsa);
    borsa.setPoliticaPrezzo(valore,valore,valore);


    Operatore operatore = Operatore.factoryOperatore("operatore");
    operatore.depositaInBudget(budgetIniziale);

    try(Scanner scanner = new Scanner(System.in)){
      /*
      * primo blocco
      */
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.equals("--")) {
          break;
        }
        String[] tokens = line.split(" ");
        String nomeAzienda = tokens[0];
        int numero = Integer.parseInt(tokens[1]);
        int prezzoUnitario = Integer.parseInt(tokens[2]);

        Azienda az = Azienda.factoryAzienda(nomeAzienda);
        az.quotatiInBorsa(nomeBorsa, prezzoUnitario);
        az.erogaAzione(nomeBorsa, numero);
        }

      /*
      * secondo blocco
      */
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.equals("--")) {
          break;
        }
        String[] tokens = line.split(" ");
        String nomeAzienda = tokens[1];
        int num = Integer.parseInt(tokens[2]);

        Azienda az = Azienda.factoryAzienda(nomeAzienda);

        if(tokens[0].equals("b")) {
          operatore.acquistaAzione(az.getNome(), nomeBorsa, num);
        } else {
          operatore.vendeAzione(az.getNome(), nomeBorsa, num);
        }
      }
    }

    //output
    for(String az : borsa.getAziendeQuotate()){
      System.out.println(az+", "+borsa.getQuotazioneAzienda(az).getPrezzoCorrente());
    }

  }
}
