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

/** Client di test per alcune funzionalità relative alle <strong>quotazioni</strong>. */
public class QuotazioneClient {

  /** . */
  private QuotazioneClient() {}

  /*-
   * Scriva un {@code main} che legge dal flusso di ingresso una sequenza di
   * linee della forma
   *
   *    nome_azienda nome_borsa quantità prezzo
   *
   * Assuma che i nomi non contengano spazi. Dopo aver quotato le aziende nelle
   * borse emette nel flusso d'uscita
   *
   * - per ciascuna azienda, l'elenco delle borse in cui è quotata, poi
   * - per ciascuna borsa, l'elenco di aziende in essa quotate;
   *
   * I nomi delle borse e delle aziende devono essere uno per linea, in ordine
   * alfabetico; i nomi di borsa nel primo elenco e i azienda nel secondo devono
   * essere prefissati da "- ".
   */

  public static void main(String[] args) {
    SortedSet<Azienda> aziende = new TreeSet<>();
    SortedSet<Borsa> borse = new TreeSet<>();

    try(Scanner scanner = new Scanner(System.in)) {
      while(scanner.hasNextLine()) {
        String line=scanner.nextLine();

        if(line.isEmpty()) {
          break;
        }

        String[] tokens = line.split(" ");

        Borsa b = Borsa.of(tokens[1]);
        borse.add(b);
        Azienda a = Azienda.of(tokens[0]);
        aziende.add(a);

        a.quotatiInBorsa(b, Integer.parseInt(tokens[3]), Integer.parseInt(tokens[2]));
      }
    }

    for(Azienda a : aziende) {
      System.out.println(a.getNome());
      for(Borsa b : borse) {
        if(a.getQuotazione(b) != null) {
          System.out.println("- " + b.getNome());
        }
      }
    }
    
    for(Borsa b : borse) {
      System.out.println(b.getNome());
      for(Azienda a : aziende) {
        if(a.getQuotazione(b) != null) {
          System.out.println("- " + a.getNome());
        }
      }
    }
  }
}
