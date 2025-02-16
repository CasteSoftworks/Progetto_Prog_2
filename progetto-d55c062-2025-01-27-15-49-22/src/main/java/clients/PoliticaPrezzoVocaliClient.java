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
import java.util.SortedSet;
import java.util.TreeSet;

import borsaNova.Azienda;
import borsaNova.Borsa;
import borsaNova.Operatore;

/** Client di test per alcune funzionalità relative alle <strong>borse</strong>. */
public class PoliticaPrezzoVocaliClient {

  /** . */
  private PoliticaPrezzoVocaliClient() {}

  /*-
   * Scriva un [@code main} che riceve come parametri sulla linea di comando
   *
   *      nome_borsa lettera nome_operatore budget_iniziale
   *
   * il secondo parametro è una lettera che determina la politica di prezzo
   * della borsa come segue: le azioni "coinvolte" sono quelle la cui azienda o
   * borsa hanno un nome che inizia per vocale o coincide (a meno di maiuscole o
   * minuscole) con tale lettera; la politica è che il prezzo delle azioni
   * "coinvolte" raddoppia se acquistate e viene diviso per due (ma senza
   * scendere mai sotto 1) se vendute.
   *
   * Il programma quindi procede esattamente come nel caso della classe
   * PoliticaPrezzoClient, ossia: legge dal flusso in ingresso una sequenza di
   * due gruppi di linee (separati tra loro dalla linea contenente solo --)
   * ciascuno della forma descritta di seguito:
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
   * e budget iniziale specificati dal terzo e quarto parametro sulla
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
    Borsa b = Borsa.of(args[0]);
    char val = args[1].charAt(0);
    System.err.println("val: "+val);

    b.setPoliticaPrezzo((int)val, 0, 0);

    Operatore o = Operatore.of(args[2]);

    o.depositaInBudget(Integer.parseInt(args[3]));

    SortedSet<Azienda> aziende = new TreeSet<>();

    System.err.println("vocali inizio");
    
    try(Scanner scanner = new Scanner(System.in)) {
      while(scanner.hasNextLine()) {
        String line = scanner.nextLine();
        
        if(line.equals("--")) {
          break;
        }
        
        String[] tokens = line.split(" ");

        Azienda az = Azienda.of(tokens[0]);
        az.quotatiInBorsa(b, Integer.parseInt(tokens[2]), Integer.parseInt(tokens[1]));

        System.err.println(az.getNome() + ", " + b.getQuotazioneAzienda(az));
        aziende.add(az);
      }

      while(scanner.hasNextLine()) {
        String line = scanner.nextLine();

        if(line.isEmpty()) {
          break;
        }

        String[] tokens = line.split(" ");

        Character tipo = tokens[0].charAt(0);
        String nome = tokens[1];

        if(tipo=='b') {
          int prezzo = Integer.parseInt(tokens[2]);
          Azienda a=Azienda.of(nome);
           
          o.acquistaAzione(a, b, prezzo);
        } else if(tipo=='s') {
          int numero = Integer.parseInt(tokens[2]);
          Azienda a=Azienda.of(nome);

          o.vendeAzione(a, b, numero);
        }
      }
    }

    for(Azienda a : aziende) {
      System.out.println(a.getNome() + ", " + b.getQuotazioneAzienda(a));
      System.err.println(a.getNome() + ", " + b.getQuotazioneAzienda(a));
    }

    System.err.println("vocali fine");

  }

}
