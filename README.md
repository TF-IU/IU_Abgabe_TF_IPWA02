# Generell

Quellcode zur Fallstudie im Modul IPWA02-01 der IU. Dieses Projekt dient der studienbezogenen Abgabe im Rahmen der Prüfungsleistung.

# Maven-Projekt in Eclipse importieren

Um das Projekt als Maven-Projekt in Eclipse zu verwenden, gehe wie folgt vor:

1. Starte Eclipse und öffne dein Workspace.
2. Navigiere zu **Datei → Importieren**.
3. Wähle **Maven → Existing Maven Projects** aus und klicke auf **Weiter**.
4. Wähle den Projektordner aus, der die Datei `pom.xml` enthält.
5. Klicke auf **Finish**, um das Projekt zu importieren.

Nach dem Import wird das Projekt automatisch von Eclipse als Maven-Projekt erkannt. Abhängigkeiten werden über die `pom.xml` verwaltet und bei Bedarf automatisch heruntergeladen.

Sollte das Projekt nicht korrekt erkannt werden, kann über **Rechtsklick → Configure → Convert to Maven Project** nachgeholfen werden.


# Datenbank Export und Import mit phpMyAdmin (XAMPP)

Diese Anleitung beschreibt die Schritte zum Exportieren und Importieren einer MySQL-Datenbank mit phpMyAdmin in einer lokalen XAMPP-Umgebung.

## Voraussetzungen

- Installation von XAMPP
- MySQL-Server ist gestartet (über das XAMPP-Control-Panel)
- Zugriff auf phpMyAdmin: http://localhost/phpmyadmin

## Datenbank exportieren

1. Starte XAMPP und öffne phpMyAdmin.
2. Wähle in der linken Spalte die gewünschte Datenbank aus.
3. Klicke oben auf den Reiter „Exportieren“.
4. Wähle eine Export-Methode:
   - Schnell: Exportiert alle Tabellen ohne weitere Optionen.
   - Angepasst: Ermöglicht die Auswahl einzelner Tabellen und Optionen.
5. Wähle das Format `SQL`.
6. Klicke auf „Los“, um den Export zu starten. Eine `.sql`-Datei wird heruntergeladen.

## Datenbank importieren

1. Starte XAMPP und öffne phpMyAdmin.
2. Erstelle eine neue Datenbank:
   - Klicke in der linken Spalte auf „Neu“.
   - Vergib einen Namen und klicke auf „Erstellen“.
3. Wähle die neu erstellte Datenbank aus.
4. Klicke oben auf den Reiter „Importieren“.
5. Wähle die zuvor exportierte `.sql`-Datei aus.
6. Klicke auf „Los“, um den Import auszuführen.
