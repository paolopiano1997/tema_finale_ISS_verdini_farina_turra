# Come replicare il funzionamento del sistema "tearoom" sulla propria macchina locale.

## Premessa: La cartella Componenti_Per_Replicare_Funzionamento nella root della directory contiene tutte le componenti necessarie alla simulazione.
  
> 1. Assicurarsi di avere virtualRobot attivo
> 2. Assicurarsi anche di mettere in esecuzione il basicrobot sulla porta 8020 in localhost e con broker localhost:1883 (es. mosquitto)
> 3. Una volta messi in esecuzione i componenti dei punti 1 e 2, far partire lo script "Tearoom.bat" nella cartella Tearoom
> 4. Importare su eclipse le due GUI, managerGui e clientSimulationGui.
> 5. Controllare le dipendenze delle librerie con unibolibs (mettere nella directory superiore la cartella "unibolibs" aggiornata)
> 6. Lanciare gradlew build di entrambi i progetti.
> 7. Se esce un errore su connQak, provare a buildare/refreshare/fare clean finchè non scompare
> 8. Lanciare entrambe le GUI da ManagerGui.java e ClientSimulationGui.java
> 9. managerGui risiede in "localhost:7001".
> 10. clientSimulationGui risiede in "localhost:7002".

## Organizzazione vari Sprint e Progetti.

> Nella directory "Sprint" sono disponibili i vari Sprint (1, 2 e 3) con i relativi modelli dell'analisi dei requisiti e dell'analisi del problema  
> Nella directory "VideoProgetto" sono presenti i video con cui è stato presentato il tema finale.
> 
> 
> 
> 
> 
> 