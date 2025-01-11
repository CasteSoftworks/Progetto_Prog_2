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

import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import BorsaNova.Entita.Azienda;
import BorsaNova.Entita.Borsa;
import BorsaNova.Entita.Operatore;

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
   * prefissate da -), e per ognuna di esse i nomi degli operatori e delle
   * quantità che ne possiedono (in ordine alfabetico, prefissati da =). 
   */

  public static void main(String[] args){
    //inizializzo le strutture dati utili alla correttezza del test
    Set<Borsa> borse = new TreeSet<>();
    Map<String, Integer> mappaAziendaOperatoreAzioni = new TreeMap<>();
        
    try(Scanner scanner = new Scanner(System.in)){
      /*
      * Primo blocco:
      * - creo una borsa con nome
      * - creo un'azienda con nome
      * - quotazione dell'azienda nella borsa con prezzo unitario
      * - aggiunta delle azioni dell'azienda nella borsa con numero
      */
      while(scanner.hasNextLine()){
        String line = scanner.nextLine();

        if(line.equals("--")||line.isBlank()){
          break;
        }

        String[] tokens = line.split(" ");
        String nomeAzienda = tokens[0];
        String nomeBorsa = tokens[1];
        int numero = Integer.parseInt(tokens[2]);
        int prezzoUnitario = Integer.parseInt(tokens[3]);

        Borsa b=null;

        b=Borsa.factoryBorsa(nomeBorsa);
        
        Azienda a= Azienda.factoryAzienda(nomeAzienda);
        
        a.quotatiInBorsa(nomeBorsa, prezzoUnitario);
        a.erogaAzione(nomeBorsa, numero);
        borse.add(b);      
      }
      /*
      * Secondo blocco:
      * - creo un operatore con nome e budget
      */
      while(scanner.hasNextLine()){
        String line = scanner.nextLine();

        if(line.equals("--")||line.isBlank()){
          break;
        }

        String[] tokens = line.split(" ");
        String nomeOperatore = tokens[0];
        int budgetIniziale = Integer.parseInt(tokens[1]);

        Operatore o=Operatore.factoryOperatore(nomeOperatore);
        o.depositaInBudget(budgetIniziale);
        
      }

      
      /*
      * Terzo blocco:
      * - se l'operazione è di acquisto:
      *  - recupero un operatore per nome
      *  - acquisto azioni dell'azienda nella borsa con prezzo totale
      * - se l'operazione è di vendita:
      *  - recupero un operatore per nome
      *  - vendita azioni dell'azienda nella borsa con numero azioni
      */

      while(scanner.hasNextLine()){
        String line = scanner.nextLine();

        if(line.isBlank()){
          break;
        }

        String[] tokens = line.split(" ");
        String nomeOperatore = tokens[0];
        String tipoOperazione = tokens[1];
        String nomeBorsa = tokens[2];
        String nomeAzienda = tokens[3];

        Operatore o= Operatore.factoryOperatore(nomeOperatore);
        Azienda az = Azienda.factoryAzienda(nomeAzienda);
        
        String key=nomeBorsa+" "+az.getNome()+" "+o.getNome();

        if(tipoOperazione.equals("b")){
          int prezzoTotale = Integer.parseInt(tokens[4]);
          

          int numAzioni=o.acquistaAzione(az.getNome(), nomeBorsa, prezzoTotale);
          
          if(mappaAziendaOperatoreAzioni.containsKey(key)){
            numAzioni+=mappaAziendaOperatoreAzioni.get(key);
          }
          mappaAziendaOperatoreAzioni.put(key, numAzioni);

        }else if(tipoOperazione.equals("s")){
          int numeroAzioni = Integer.parseInt(tokens[4]);

          if(o.vendeAzione(az.getNome(), nomeBorsa, numeroAzioni)){
            mappaAziendaOperatoreAzioni.put(key, mappaAziendaOperatoreAzioni.get(key) - numeroAzioni);
          }        
        }
      }
    }

    //output
    //loopo su tutte le borse e ognuna la confronto con le borse del test, se il nome è uguale procedo
    for(Borsa b : Borsa.getBorse()){
      for(Borsa b2 : borse){
        if(b.getNome().equals(b2.getNome())){
          System.out.println(b.getNome());
          for(String a : b2.getAziendeQuotate()){
            System.out.println("- "+a+" "+b2.getNumeroAzioni(a));
            for(String key : mappaAziendaOperatoreAzioni.keySet()){
              if(key.contains(b2.getNome()+" "+a)&&mappaAziendaOperatoreAzioni.get(key)!=0){
                System.out.println("= "+key.split(" ")[2]+" "+mappaAziendaOperatoreAzioni.get(key));
              }
            }
          }
        }
      }
    }
    
  }
}
