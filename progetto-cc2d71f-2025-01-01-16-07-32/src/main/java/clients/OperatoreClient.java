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

/** Client di test per alcune funzionalità relative agli <strong>operatori</strong>. */
public class OperatoreClient {

  /** . */
  private OperatoreClient() {}

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
   *     ... [oppure]
   *     nome_operatore d valore 
   *     ... [oppure] 
   *     nome_operatore w valore
   *
   * Assuma che i nomi non contengano spazi. In base al contenuto del primo
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
   * - d deposita denaro (secondo il valore specificato),
   * - w preleva denaro (secondo il valore specificato).
   *
   * Osservi che l'acquisto può determinare un resto, nel caso in cui il prezzo
   * totale non sia un multiplo esatto del prezzo dell'azione; tale resto rimane
   * a disposizione dell'operatore per eventuali operazioni successive.
   *
   * Al termine della lettura il programma emette nel flusso d'uscita l'elenco
   * degli operatori coinvolti (in ordine alfabetico) ciascuno dei quali seguito
   * (sulla stessa linea e separato da virgole) dal suo budget finale e dalla
   * somma del valore delle azioni che possiede, ogni operatore è poi seguito
   * dall'elenco delle azioni che possiede, ciascuna azione va descritta  
   * emettendo il nome della borsa (in ordine alfabetico, preceduto da -)
   * seguito da quello dell'azienda e dal numero di azioni possedute (separati
   * da virgole).
   */

   //b: buy, s: sell, d: deposit, w: withdraw
  public static void main(String[] args) {
    Set<Borsa> borse = new TreeSet<>();
    Set<Operatore> operatori = new TreeSet<>();

    Map<String, Integer> aziende = new TreeMap<>();
    Map<String, Integer> mappaAziendaOperatoreAzioni = new TreeMap<>();

    Scanner scanner = new Scanner(System.in);
    
    /*
     * Legge il primo blocco di input.
     */
    while (scanner.hasNext()) {
      String line = scanner.nextLine();
      if (line.equals("--")) {
        break;
      }
      String[] tokens = line.split(" ");
      String nomeAzienda = tokens[0];
      String nomeBorsa = tokens[1];
      int numero = Integer.parseInt(tokens[2]);
      int prezzoUnitario = Integer.parseInt(tokens[3]);

      Borsa b=null;

      if(Borsa.getBorsa(nomeBorsa)==null){
        b= new Borsa(nomeBorsa);
      }else{
        b=Borsa.getBorsa(nomeBorsa);
      }
      
      Azienda a= Azienda.factoryAzienda(nomeAzienda);
  
      b.quotaAzienda(a, prezzoUnitario);
      b.modificaAzioni(a, numero);
      b.aggiungiAllaLista();
      borse.add(b);
      
      aziende.put(b.getNome()+" "+a.getNome(), numero);
    }
    /*
     * Legge il secondo blocco di input.
     */
    while (scanner.hasNext()) {
      String line = scanner.nextLine();
      if (line.equals("--")) {
        break;
      }
      String[] tokens = line.split(" ");
      String nomeOperatore = tokens[0];
      int budgetIniziale = Integer.parseInt(tokens[1]);

      Operatore.factoryOperatore(nomeOperatore, budgetIniziale);
      operatori.add(Operatore.getOperatore(nomeOperatore));
    }
    /*
     * Legge il terzo blocco di input.
     */
    while (scanner.hasNext()) {
      String line = scanner.nextLine();
      if (line.equals("--")) {
        break;
      }
      String[] tokens = line.split(" ");
      String nomeOperatore = tokens[0];
      String operazione = tokens[1];

      Operatore o= Operatore.getOperatore(nomeOperatore);
      
      if(operazione.equals("b")){
        String nomeBorsa = tokens[2];
        String nomeAzienda = tokens[3];

        Azienda az = Azienda.getAzienda(nomeAzienda);
        Borsa b = Borsa.getBorsa(nomeBorsa);

        String key=b.getNome()+" "+az.getNome()+" "+o.getNome();
        String key2=b.getNome()+" "+az.getNome();

        int prezzoTotale = Integer.parseInt(tokens[4]);

        int numAzioni=o.acquistaAzione(az, b, prezzoTotale);
        aziende.put(key2, aziende.get(key2)-numAzioni);

        if(mappaAziendaOperatoreAzioni.containsKey(key)){
          numAzioni+=mappaAziendaOperatoreAzioni.get(key);
        }
        mappaAziendaOperatoreAzioni.put(key, numAzioni);

      }else if(operazione.equals("s")){
        String nomeBorsa = tokens[2];
        String nomeAzienda = tokens[3];

        int numeroAzioni = Integer.parseInt(tokens[4]);

        Azienda az = Azienda.getAzienda(nomeAzienda);
        Borsa b = Borsa.getBorsa(nomeBorsa);

        String key=b.getNome()+" "+az.getNome()+" "+o.getNome();
        String key2=b.getNome()+" "+az.getNome();

        if(o.vendeAzione(az, b, numeroAzioni)){
          aziende.put(key2, aziende.get(key2)+numeroAzioni);
          mappaAziendaOperatoreAzioni.put(key, mappaAziendaOperatoreAzioni.get(key) - numeroAzioni);
        }
      }else if(operazione.equals("d")){
        int valore = Integer.parseInt(tokens[2]);
        o.depositaInBudget(valore);
      }else if(operazione.equals("w")){
        int valore = Integer.parseInt(tokens[2]);
        o.prelievoDalBudget(valore);
      }
    }
    scanner.close();

    for (Operatore o : operatori) {
      System.out.println(o.getNome() + ", " + o.getBudget() + ", " + o.getValorePortafoglio());
      for (Map.Entry<String, Integer> entry : mappaAziendaOperatoreAzioni.entrySet()) {
        String[] tokens = entry.getKey().split(" ");
        if (tokens[2].equals(o.getNome())&&entry.getValue()>0) {
          System.out.println("- " + tokens[0] + ", " + tokens[1] + ", " + entry.getValue());
        }
      }
    }
  }

}
