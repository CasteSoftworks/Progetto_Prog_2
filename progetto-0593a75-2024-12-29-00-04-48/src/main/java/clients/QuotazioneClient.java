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
import java.util.stream.Collectors;

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
    Map<String, Azienda> aziendeMap = new HashMap<>();
    Map<String, Borsa> borseMap = new HashMap<>();
    Scanner scanner = new Scanner(System.in);

    while (scanner.hasNextLine()) {
      String[] input = scanner.nextLine().split(" ");

      Borsa borsa = borseMap.computeIfAbsent(input[1], Borsa::new);
      borsa.aggiungiAllaLista();

      Azienda az = Azienda.factoryAzienda(input[0], Integer.parseInt(input[2]));
      aziendeMap.putIfAbsent(input[0], az);

      borsa.quotaAzienda(az, Integer.parseInt(input[3]));
    }

    scanner.close();

    //per ogni azienda elenca le borse in cui è quotata
    stampaAziende(aziendeMap, borseMap);
    //per ogni borsa elenca le aziende quotate
    stampaBorseQuotate(borseMap);

  }

  /**
   * Stampa le aziende e per ciascuna azienda le borse in cui è quotata.
   * 
   * @param aziendeMap mappa delle aziende
   * @param borseMap mappa delle borse
   */
  private static void stampaAziende(Map<String, Azienda> aziendeMap, Map<String, Borsa> borseMap){
    ArrayList<Azienda> aziende = new ArrayList<>(aziendeMap.values());
    Collections.sort(aziende, (a1, a2) -> a1.getNome().compareTo(a2.getNome()));
    for(Azienda a : aziende){
      System.out.println(a.getNome());
      stampaBorseDoveQuotata(a,borseMap);
    }
  }

  /**
   * Stampa le borse in cui è quotata una determinata azienda.
   * 
   * @param a azienda
   * @param borseMap mappa delle borse
   */
  private static void stampaBorseDoveQuotata(Azienda a, Map<String, Borsa> borseMap){
    //riempio un con tutte le borse dove per ogni borsa, controllo che tra le aziende quotate in essa, esista a
    ArrayList<Borsa> borse = new ArrayList<>(borseMap.values().stream().filter(b -> b.getAziendeQuotate().contains(a)).collect(Collectors.toList()));
    Collections.sort(borse, (b1, b2) -> b1.getNome().compareTo(b2.getNome()));
    for(Borsa b : borse){
      System.out.println("- "+b.getNome());
    }
  }

  /**
   * Stampa le borse quotate e per ciascuna borsa le aziende quotate in essa.
   * 
   * @param borseMap mappa delle borse
   */
  private static void stampaBorseQuotate(Map<String, Borsa> borseMap){
    ArrayList<Borsa> borse = new ArrayList<>(borseMap.values());
    Collections.sort(borse, (b1, b2) -> b1.getNome().compareTo(b2.getNome()));
    for(Borsa b : borse){
      System.out.println(b.getNome());
      stampaAziendeQuotate(b);
    }
  }

  /**
   * Stampa le aziende quotate in una determinata borsa.
   * 
   * @param b borsa
   */
  private static void stampaAziendeQuotate(Borsa b){
    ArrayList<Azienda> aziende = new ArrayList<>(b.getAziendeQuotate());
    Collections.sort(aziende, (a1, a2) -> a1.nome.compareTo(a2.nome));
    for(Azienda a : aziende){
      System.out.println("- "+a.nome);
    }
  }
}
