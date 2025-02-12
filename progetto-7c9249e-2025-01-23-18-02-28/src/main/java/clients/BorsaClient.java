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
import java.util.TreeMap;
import java.util.TreeSet;

import BorsaNova.Entita.*;
import BorsaNova.Entita.Borsa.Allocazione;
import BorsaNova.Entita.Borsa.Azione;

/** Client di test per alcune funzionalità relative alle <strong>borse</strong>. */
public class BorsaClient {

  /** . */
  private BorsaClient() {}

  /*-
   * Scriva un [@code main} che legge dal flusso in ingresso una sequenza di tre
   * gruppi di linee (separati tra loro dalla linea contenente solo --) ciascuno
   * della forma descritta di seguito:
   *
   *     nome_azienda nome_borsa numero prezzo_unitario
   *     ...
   *     --
   *     nome_operatore budget_iniziale
   *     ...
   *     --
   *     nome_operatore b nome_borsa nome_azienda prezzo_totale
   *     ... [oppure]
   *     nome_operatore s nome_borsa nome_azienda numero_azioni
   *
   * Assuma che i nomi non contengano spazi. Iqn base al contenuto del primo
   * blocco, quota le azioni delle aziende nelle borse secondo il numero e
   * prezzo unitario specificati, in base al secondo blocco crea gli operatori
   * specificati con il budget iniziale specificato e in base al terzo blocco
   * esegue le operazioni, a seconda che il carattere che segue il nome
   * dell'operatore sia:
   *
   * - b compra azioni (quotate nella borsa e dell'azienda specificata,
   *   impegnano il prezzo totale specificato),
   * - s vende azioni (quotate nella borsa e dell'azienda specificata, nel
   *   numero specificato).
   *
   * Osservi che l'acquisto può determinare un resto, nel caso in cui il prezzo
   * totale non sia un multiplo esatto del prezzo dell'azione; tale resto rimane
   * a disposizione dell'operatore per eventuali operazioni successive.
   *
   * Al termine della lettura il programma emette nel flusso d'uscita (una per
   * linea) l'elenco delle borse coinvolte (in ordine alfabetico), per ogni
   * borsa emette l'elenco delle azioni in essa quotate (in ordine alfabetico,
   * prefissate da - e seguite dal numero di azioni ancora disponibili), e per
   * ognuna di esse i nomi degli operatori e delle quantità che ne possiedono
   * (in ordine alfabetico, prefissati da =).
   */

  public static void main(String[] args) {
    SortedSet<Azienda> aziende = new TreeSet<>();
    SortedSet<Borsa> borse = new TreeSet<>();
    SortedSet<Operatore> operatori = new TreeSet<>();

    try(Scanner scanner = new Scanner(System.in)){
      while(scanner.hasNextLine()){
        String line = scanner.nextLine();
        
        if(line.equals("--")){
          break;
        }

        String[] tokens = line.split(" ");

        Azienda azienda = Azienda.of(tokens[0]);

        Borsa borsa = Borsa.of(tokens[1]);

        int numero = Integer.parseInt(tokens[2]);
        int prezzoUnitario = Integer.parseInt(tokens[3]);

        azienda.quotatiInBorsa(borsa, prezzoUnitario, numero);
        aziende.add(azienda);
        borse.add(borsa);
      }
      while(scanner.hasNextLine()){
        String line = scanner.nextLine();
        
        if(line.equals("--")){
          break;
        }

        String[] tokens = line.split(" ");

        Operatore operatore = Operatore.of(tokens[0]);
        int budgetIniziale = Integer.parseInt(tokens[1]);

        operatore.depositaInBudget(budgetIniziale);
        operatori.add(operatore);

      }


      while(scanner.hasNextLine()){
        String line = scanner.nextLine();

        if(line.isEmpty()){
          break;
        }
        
        String[] tokens = line.split(" ");

        Operatore operatore = Operatore.of(tokens[0]);
        for(Operatore op : operatori){
          if(op.getNome().equals(tokens[0])){
            operatore = op;
            break;
          }
        }


        char operazione = tokens[1].charAt(0);

        String nomeBorsa = tokens[2];
        String nomeAzienda = tokens[3];
        Borsa borsa = Borsa.of(nomeBorsa);
        Azienda azienda = Azienda.of(nomeAzienda);

        if(operazione == 'b'){
          
          int prezzoTotale = Integer.parseInt(tokens[4]);

          operatore.acquistaAzione(azienda, borsa, prezzoTotale);
        } else if(operazione == 's'){    
          int numeroAzioni = Integer.parseInt(tokens[4]);

          operatore.vendeAzione(azienda, borsa, numeroAzioni);
        }
      }
    }

    for(Borsa borsa : borse){
      System.out.println(borsa.getNome());
      for(Azione azione : borsa.getAzioni()){
        System.out.println("- " + azione.getAzienda().getNome() + " " + azione.getQuantita());
        for(Allocazione allocazione : borsa.getAllocazioni()){
          TreeMap<Azione, Integer> azioni = allocazione.getAzioniPossedute();
          if(azioni.containsKey(azione)){
            System.out.println("= " + allocazione.getOperatore().getNome() + " " + azioni.get(azione));
          }
        }
      }
    }

  }
}
