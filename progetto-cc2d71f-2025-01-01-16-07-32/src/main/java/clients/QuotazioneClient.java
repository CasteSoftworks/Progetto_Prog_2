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
    //peccato per ste due strutture dati che sono contretto ad usare
    Set<Azienda> aziende = new TreeSet<>();
    Set<Borsa> borse = new TreeSet<>();

    try(Scanner scanner = new Scanner(System.in)){
      while (scanner.hasNextLine()) {
        String[] input = scanner.nextLine().split(" ");

        Borsa borsa = Borsa.factoryBorsa(input[1]);
        borse.add(borsa);

        Azienda az = Azienda.factoryAzienda(input[0]);
        az.quotatiInBorsa(input[1], Integer.parseInt(input[3]));
        az.erogaAzione(input[1], Integer.parseInt(input[2]));
        aziende.add(az);


        
      }
    }

    //per ogni azienda elenca le borse in cui è quotata
    for(Azienda a : Azienda.getAziende().values()){
      for(Azienda a2 : aziende){
        if(a.getNome().equals(a2.getNome())){
          System.out.println(a.getNome());
          for(Borsa b : Borsa.getBorse()){
            if(b.getAziendeQuotate().contains(a)){
              System.out.println("- "+b.getNome());
            }
          }
        }
      }
    }

    //per ogni borsa elenca le aziende quotate
    for(Borsa b : Borsa.getBorse()){
      for(Borsa b2 : borse){
        if(b2.getNome().equals(b.getNome())){
          System.out.println(b.getNome());
          for(Azienda a : b2.getAziendeQuotate()){
            System.out.println("- "+a.getNome());
          }
        }
      }
    }
  }

  
}
