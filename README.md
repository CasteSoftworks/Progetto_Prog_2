# Progetto Programmazione 2 - Inverno 2025

Il progetto prevede una specie di simulzaione di borsa con aziende che si quotano in diverse borse a diversi prezzi e broker che comprano e vendono azioni e depositano o prelevano denaro da un _misterioso_ fondo
Le azioni seguono una politica di prezzo (abbastanza astrusa e a costante) che fa:
* dimuinure se vengono vendute
* alzare se vengono comprate
* entrambe le precedenti (forse la cosa che si avvicina si più alla realtà)

## Tree del progetto
* **Entita**
  * Azienda
  * Borsa
  * Operatore
  * Quotazione
* **PoliticaPrezzo**
  * Politica (interfaccia)
  * Decremento
  * Incremento
  * Variazione
* **clients**
  * roba del santini

## Percentaule di completamento
* 3/01 - **50%** ma probabilmente molta roba è da buttare e rifare a causa di non uso di ISTANCES