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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import BorsaNova.Entita.Azienda;
import BorsaNova.Entita.Borsa;

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
   * borse emette nel flusso d'uscita, per ciascuna borsa, l'elenco di aziende
   * in essa quotate; i nomi delle borse e delle aziende devono essere uno per
   * linea, in ordine alfabetico, e i nomi di azienda devono essere prefissati
   * da "- ". 
   */
  public static void main(String[] args) {
    Map<String, Borsa> borseMap = new HashMap<>();
    Scanner scanner = new Scanner(System.in);

    while (scanner.hasNextLine()) {
      String[] input = scanner.nextLine().split(" ");

      Borsa borsa = borseMap.computeIfAbsent(input[1], Borsa::new);
      borsa.aggiungiAllaLista();

      Azienda az = Azienda.factoryAzienda(input[0], Integer.parseInt(input[2]), Integer.parseInt(input[3]));

      borsa.quotaAzienda(az, Integer.parseInt(input[3]));
    }

    stampaBorseQuotate(borseMap);

  }

  private static void stampaBorseQuotate(Map<String, Borsa> borseMap){
    ArrayList<Borsa> borse = new ArrayList<>(borseMap.values());
    Collections.sort(borse, (b1, b2) -> b1.getNome().compareTo(b2.getNome()));
    for(Borsa b : borse){
      System.out.println(b.getNome());
      stampaAziendeQuotate(b);
    }
  }

  private static void stampaAziendeQuotate(Borsa b){
    ArrayList<Azienda> aziende = new ArrayList<>(b.getAziendeQuotate());
    Collections.sort(aziende, (a1, a2) -> a1.nome.compareTo(a2.nome));
    for(Azienda a : aziende){
      System.out.println("- "+a.nome);
    }
  }
}
