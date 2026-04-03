# VisuStruct

[![Build-Status](https://github.com/code4teaching/VisuStruct/actions/workflows/maven-build.yml/badge.svg?branch=master)](https://github.com/code4teaching/VisuStruct/actions)

Visueller Editor zur Erstellung von **Nassi-Shneiderman-Diagrammen** (Struktogrammen).  
Die **Benutzeroberfläche** und die **Bedienung** des Programms sind **deutsch**.

Dieses **Projektarchiv** ist eine **Abzweigung** (*Fork*) des Originals  
[kekru/struktogrammeditor](https://github.com/kekru/struktogrammeditor).  
Es enthält unter anderem Anpassungen für **aktuelle Java-Versionen**, ein **überarbeitetes Aussehen** sowie die Marke **VisuStruct** (Struktogramm-Editor).

**Website:** [visustruct.de](https://visustruct.de)  
**Repository:** [github.com/code4teaching/VisuStruct](https://github.com/code4teaching/VisuStruct)

```bash
git clone https://github.com/code4teaching/VisuStruct.git
```

Das Original stammt von Kevin Krummenauer (Schulprojekt 2010/2011). **Lizenz:** MIT (siehe [LICENSE](LICENSE)).

## Änderungen in Version 1.0.2 (Auszug)

- **Quellcode-Generator:** schnellere Ausgabe (gepuffertes Schreiben), effizientere String-Verarbeitung; bei Option „Struktogrammtext als Kommentare“ stehen Bedingungen lesbarer (Kommentarzeile, Klartext in den Klammern)
- **Textvorlagen für neue Elemente:** Einstellungsdialog mit Presets (u. a. englisch/Java-nah mit `condition`, `selector`, …)
- **Marke / Dateien:** u. a. Anpassungen für **VisuStruct** und **`.visustruct`** (Filter, Assoziationen)
- **Logo** und weitere kleinere UI-Anpassungen

## Technische Basis (1.0.1 und früher)

- **Java 17** als Zielversion (mit JDK 17 oder höher bauen und ausführen)
- **FlatLaf** mit Farben wie auf **[VisuStruct](https://visustruct.de)** (hell: `style.css`, dunkel: Layout-Farben aus `styles.css`), unter *Einstellungen → Look and Feel:* „Modern · hell“ / „Modern · dunkel“
- **JDOM2** statt JDOM 1, keine veraltete Bibliothek „AppleJavaExtensions“ mehr; Einbindung von macOS über die üblichen **Java-Schnittstellen**
- Der frühere **Motif**-Stil entfällt (in neueren JDKs nicht mehr enthalten); stattdessen **Metal** und FlatLaf
- Gebaute Datei: **`visustruct-1.0.2.jar`** (eine startfertige Datei **mit** allen Programmbibliotheken)

## Fertiges Programm herunterladen (ohne selbst zu bauen)

**Für den Unterricht:** Schülerinnen und Schüler brauchen **kein Maven** – nur **Java 17** (oder neuer) zum Starten.

**Direkter Download der neuesten veröffentlichten Version** (fester Dateiname, immer die aktuelle Release):

**[➜ visustruct.jar herunterladen](https://github.com/code4teaching/VisuStruct/releases/latest/download/visustruct.jar)**

Danach z. B. im Ordner, in dem die Datei liegt:

```bash
java -jar visustruct.jar
```

Unter **Windows** reicht oft ein Doppelklick, wenn `.jar` mit Java verknüpft ist.

Alle **veröffentlichten Versionen** und Hinweise zum Wechsel stehen unter:  
[github.com/code4teaching/VisuStruct/releases](https://github.com/code4teaching/VisuStruct/releases)

**Release auf GitHub aktualisieren:** Unter *Releases* eine **neue Version** anlegen, Tag z. B. **`v1.0.2`** auf den passenden Commit setzen und veröffentlichen. Die Workflow-Datei [`.github/workflows/release-assets.yml`](.github/workflows/release-assets.yml) baut dann die JARs und hängt u. a. **`visustruct.jar`** (fester Download-Link oben) an die Release an.

## Voraussetzungen

- [JDK 17](https://adoptium.net/) oder neuer (zum **Ausführen** der JAR; zum **Selbstbauen** siehe unten)
- **Nur beim Selbstbauen:** Internet beim ersten Mal, damit **Maven** die Bibliotheken laden kann

## Lauffähige Programmdatei (JAR) selbst erzeugen

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
target/visustruct-1.0.2.jar
```

## Programm starten

```bash
java -jar target/visustruct-1.0.2.jar
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

Verbesserungsvorschläge und **Fehlerbeschreibungen** sind willkommen – über [**Issues** und **Pull requests**](https://github.com/code4teaching/VisuStruct) dieses Repositories.

Für **Commits** und **Programm-Kommentare** kann **Englisch** verwendet werden.  
Alle **sichtbaren Texte im Programm** sollten **deutsch** bleiben – so wie beim Original vereinbart.

## Lizenz

**MIT** – siehe [LICENSE](LICENSE). Das **Urheberrecht** am ursprünglichen Programmcode liegt bei Kevin Krummenauer (steht auch in der LICENSE-Datei).

## Link zum Ursprungsprojekt (Web)

Website zum Original: [whiledo.de – Struktogrammeditor](https://whiledo.de/index.php?p=struktogrammeditor)
