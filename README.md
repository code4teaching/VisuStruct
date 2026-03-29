# Struktogramm Studio

[![Build-Status](https://github.com/code4teaching/struktogrammeditor-studio/actions/workflows/maven-build.yml/badge.svg?branch=master)](https://github.com/code4teaching/struktogrammeditor-studio/actions)

Visueller Editor zur Erstellung von **Nassi-Shneiderman-Diagrammen** (Struktogrammen).  
Die **Benutzeroberfläche** und die **Bedienung** des Programms sind **deutsch**.

Dieses **Projektarchiv** ist eine **Abzweigung** (*Fork*) des Originals  
[kekru/struktogrammeditor](https://github.com/kekru/struktogrammeditor).  
Es enthält unter anderem Anpassungen für **aktuelle Java-Versionen**, ein **überarbeitetes Aussehen** sowie den Namen **Struktogramm Studio**.

Das Original stammt von Kevin Krummenauer (Schulprojekt 2010/2011). **Lizenz:** MIT (siehe [LICENSE](LICENSE)).

## Änderungen in dieser Version (Auszug)

- **Java 17** als Zielversion (mit JDK 17 oder höher bauen und ausführen)
- **FlatLaf** – modernes Erscheinungsbild, Hell- und Dunkeldesign (unter *Einstellungen → Look and Feel:* „Modern · hell“ / „Modern · dunkel“)
- **JDOM2** statt JDOM 1, keine veraltete Bibliothek „AppleJavaExtensions“ mehr; Einbindung von macOS über die üblichen **Java-Schnittstellen**
- Der frühere **Motif**-Stil entfällt (in neueren JDKs nicht mehr enthalten); stattdessen **Metal** und FlatLaf
- Gebaute Datei: **`struktogrammeditor-studio-1.0.0.jar`** (eine startfertige Datei **mit** allen Programmbibliotheken)

## Voraussetzungen

- [JDK 17](https://adoptium.net/) oder neuer
- **Internet:** nur beim ersten Bauen nötig, damit das Werkzeug **Maven** die Bibliotheken herunterladen kann (danach kann es offline weitergehen, wenn der Rechner die Daten schon gespeichert hat)

## Lauffähige Programmdatei (JAR) erzeugen

Das Projekt herunterladen oder klonen und im Projektordner ein Terminal öffnen.

**Windows (PowerShell):**

```powershell
.\mvnw.cmd clean package
```

**Linux oder macOS:**

```bash
chmod +x mvnw
./mvnw clean package
```

Die fertige Datei befindet sich hier:

```text
target/struktogrammeditor-studio-1.0.0.jar
```

## Programm starten

```bash
java -jar target/struktogrammeditor-studio-1.0.0.jar
```

Auf vielen Rechnern genügt auch ein **Doppelklick** auf die JAR-Datei, wenn der Dateityp `.jar` mit Java verknüpft ist.

## Original und Synchronisation mit dem Ursprungsprojekt

- **Original auf GitHub:** [github.com/kekru/struktogrammeditor](https://github.com/kekru/struktogrammeditor)  
- **Git – Bezug zum Original einrichten** (einmalig, falls noch nicht geschehen):

  ```bash
  git remote add upstream https://github.com/kekru/struktogrammeditor.git
  ```

  **Änderungen vom Original übernehmen** (vereinfacht):

  ```bash
  git fetch upstream
  git merge upstream/master
  ```

## Mitmachen

Verbesserungsvorschläge und **Fehlerbeschreibungen** sind willkommen – über die **Themenverwaltung** und **Zusammenführungsanträge** dieses Projekts auf GitHub (auf der Oberfläche oft „Issues“ und „Pull requests“ genannt).

Für **Commits** und **Programm-Kommentare** kann **Englisch** verwendet werden.  
Alle **sichtbaren Texte im Programm** sollten **deutsch** bleiben – so wie beim Original vereinbart.

## Lizenz

**MIT** – siehe [LICENSE](LICENSE). Das **Urheberrecht** am ursprünglichen Programmcode liegt bei Kevin Krummenauer (steht auch in der LICENSE-Datei).

## Link zum Ursprungsprojekt (Web)

Website zum Original: [whiledo.de – Struktogrammeditor](https://whiledo.de/index.php?p=struktogrammeditor)
