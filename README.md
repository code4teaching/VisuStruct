# Struktogramm Studio

[![Maven-Build](https://github.com/code4teaching/struktogrammeditor-studio/actions/workflows/maven-build.yml/badge.svg?branch=master)](https://github.com/code4teaching/struktogrammeditor-studio/actions)

Visueller Editor zur Erstellung von **Nassi-Shneiderman-Diagrammen** (Struktogrammen).  
Benutzeroberfläche und Bedienung sind **deutsch**.

Dieses Repository ist ein **Fork** von [kekru/struktogrammeditor](https://github.com/kekru/struktogrammeditor) und enthält u. a. Anpassungen für **moderne Java-Versionen**, ein **aktualisiertes Erscheinungsbild** sowie den Produktnamen **Struktogramm Studio**.  
Das Original stammt von Kevin Krummenauer (Schulprojekt 2010/2011); Lizenz: **MIT** (siehe [LICENSE](LICENSE)).

## Was in dieser Variante geändert wurde (Auszug)

- **Java 17** als Zielversion (mit JDK 17 oder neuer bauen und starten)
- **FlatLaf** – modernes Hell-/Dunkel-Design („Modern · hell / dunkel“ in den Einstellungen)
- **JDOM2** statt JDOM 1, **kein** veraltetes AppleJavaExtensions mehr; macOS-Integration über Standard-APIs
- **Motif-Look** entfällt (in aktuellen JDKs entfernt); Ersatz über **Metal** sowie FlatLaf
- Ausgabe-JAR: **`struktogrammeditor-studio-1.0.0.jar`** (eine ausführbare Datei inklusive Abhängigkeiten)

## Voraussetzungen

- [JDK 17](https://adoptium.net/) oder höher
- Internet nicht zwingend nötig, sobald Maven die Abhängigkeiten einmal geladen hat

## Laufähige JAR-Datei erzeugen

Repository klonen, im Projektordner ein Terminal öffnen.

**Windows (PowerShell):**

```powershell
.\mvnw.cmd clean package
```

**Linux oder macOS:**

```bash
chmod +x mvnw
./mvnw clean package
```

Die erzeugte Datei liegt unter:

```text
target/struktogrammeditor-studio-1.0.0.jar
```

## Programm starten

```bash
java -jar target/struktogrammeditor-studio-1.0.0.jar
```

Doppelklick auf die JAR-Datei funktioniert auf vielen Systemen ebenfalls, sofern `.jar` mit Java verknüpft ist.

## Originalprojekt und Upstream

- **Original:** [github.com/kekru/struktogrammeditor](https://github.com/kekru/struktogrammeditor)  
- Upstream in Git: `git remote add upstream https://github.com/kekru/struktogrammeditor.git` (falls noch nicht gesetzt), dann z. B.  
  `git fetch upstream` und `git merge upstream/master`

## Mitwirkung

Verbesserungen und Fehlerbeschreibungen sind willkommen (Issues oder Pull Requests in **diesem** Fork).  
Technische Commits und Code-Kommentare können Englisch sein; **sichtbare Texte in der Anwendung** sollten **deutsch** bleiben – analog zur Vorgabe im Original.

## Lizenz

**MIT** – siehe [LICENSE](LICENSE). Urheberrecht am ursprünglichen Code: Kevin Krummenauer (im LICENSE genannt).

## Weiterführender Hinweis (Original)

Website zum ursprünglichen Projekt: [whiledo.de – Struktogrammeditor](https://whiledo.de/index.php?p=struktogrammeditor)
