### Hintergrund/Zielsetzung:
 Es soll ein Web-Server überwacht werden.

### Funktionale Anforderung:
 Die Anwendung soll in der Lage sein, die Verfügbarkeit einer URL zu testen. 
Dafür soll ein Benutzer das Programm starten und eine URL zu einer HTML-Seite gefolgt von Enter angeben.
Das Programm prüft nun alle 30 Sekunden ob die URL noch verfügbar ist und schreibt das Ergebnis laufend in ein lokales Log-File.

### Beispiel Output (Datumsformat beachten):
15-SEP-2011 14:00:30 Uhr : http://mynode/login.html -> erreichbar!
15-SEP-2011 14:01:00 Uhr : http://mynode/login.html -> nicht erreichbar!
15-SEP-2011 14:01:30 Uhr : http://mynode/login.html -> erreichbar!

 Das Programm zeigt folgende Meldung:
http://mynode/login.html wird überwacht, drücken Sie eine beliebige Taste um das Programm zu beenden...

Beim nächsten Tastendruck des Nutzers wird das Programm beendet.

### Technische Hinweise:
- Eine Konsolen-Applikation ist ausreichend, eine GUI nicht erforderlich
- Das Programm sollte als Applikation über die Main-Methode zu starten sein (kein Applet oder Webapp)
- Der Code ist, wo sinnvoll, mit Kommentaren zu dokumentieren
- Bei der Wahl zu benutzender Klassen und Bibliotheken: feel free.
- Bedenke: Weniger ist manchmal mehr, halte es so einfach wie möglich