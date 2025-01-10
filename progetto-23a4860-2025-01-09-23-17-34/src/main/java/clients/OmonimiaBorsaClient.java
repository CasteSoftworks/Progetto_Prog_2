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

import BorsaNova.Entita.Borsa;

/** Client di test per alcune funzionalità relative alle <strong>aziende</strong>. */
public class OmonimiaBorsaClient {

  /** . */
  private OmonimiaBorsaClient() {}

  /*-
   * Scriva un {@code main} che legge dal flusso di ingresso una sequenza di
   * linee, ciascuna delle quali corrispondente ad un nome di borsa ed emette
   * nel flusso d'uscita l'elenco di tali nomi di borsa in ordine alfabetico e
   * senza ripetizioni.
   */

  public static void main(String[] args) {
    
    Set<Borsa> borseTree = new TreeSet<>(); //FA SCHIFO ma non so come fare altrimenti
    
    try(Scanner scanner = new Scanner(System.in)){
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();

        Borsa b = Borsa.factoryBorsa(line);
        
        borseTree.add(b);
        
      }
    }

    //dimostro che le borse sono comunque presenti, nonostante il TreeSet
    for (Borsa b : Borsa.getBorse()) {
      for(Borsa b2 : borseTree) {
        if(b.getNome().equals(b2.getNome())) {
          System.out.println(b2.getNome());
        }
      }
    }
  }

}
