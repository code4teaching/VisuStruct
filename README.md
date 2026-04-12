<p align="center">
  <a href="https://visustruct.org/" title="VisuStruct">
    <img src="docs/VisuStruct-icon.png" alt="VisuStruct" width="128" height="128">
  </a>
</p>

# VisuStruct

[![Build](https://github.com/code4teaching/VisuStruct/actions/workflows/maven-build.yml/badge.svg?branch=master)](https://github.com/code4teaching/VisuStruct/actions)

**VisuStruct** is a **structure-chart editor** for **Nassi–Shneiderman** diagrams (desktop, Java / Swing).

Under **Settings → Languages** the **user interface** can be set to **English**, **German**, or **European Portuguese (Portugal)** (`uilanguage`: `en`, `de`, `pt_PT` in `visustruct.properties`). A few auxiliary dialogs may still show English-only labels.

*Lineage:* Based on the open-source project [kekru/struktogrammeditor](https://github.com/kekru/struktogrammeditor) (Kevin Krummenauer, MIT). Copyright and terms: [LICENSE](LICENSE).

**Website:** [visustruct.org](https://visustruct.org) · **Repository:** [github.com/code4teaching/VisuStruct](https://github.com/code4teaching/VisuStruct)

```bash
git clone https://github.com/code4teaching/VisuStruct.git
```

---

## Changes in 1.0.7 (summary)

- **Code generator:** **Delphi/Pascal** removed; **Python** added (**3.10+**, `match` / `case` for multi-way branches); dialog is **localized**; target language stored in **`visustruct.properties`** as **`celanguage`**: `0` = Java, `1` = Python.
- **Multi-way (Java):** default label for the last column is **`default`** (not “Else”); string from **`structure.multiway.defaultCaseLabel`** (i18n).
- **API:** `XMLLeser` load methods renamed **`ladeXML`** (fixes the old typo **`ladeXLM`**).
- **Code:** removed unused selection / size-box leftovers from upstream (`Struktogramm`, `StruktogrammElement`).
- **Build:** Maven **`de.visustruct:visustruct:1.0.7`** → artifact **`visustruct-1.0.7.jar`**; releases also ship **`visustruct.jar`**.

Full notes: [`release-notes/v1.0.7.md`](release-notes/v1.0.7.md).

## Changes in 1.0.6 (summary)

- **UI & theme:** cleaner menus; **light / dark** FlatLaf **without restart**; improved dark-mode readability.
- **Languages:** **Settings → Languages** lists **English**, **German**, **Portuguese**; stored in **`visustruct.properties`** (`uilanguage`). If unset, default follows **JVM locale**.
- **I18n:** menu, core confirmation dialogs, `JFileChooser` labels, context menu, palette (PNG, about, trash, etc.); **English** is no longer overridden by the OS default locale.
- **Structure-chart labels:** preset **“Java (default)”** (and localized equivalents); **preview** in the settings dialog; **palette** shows Java keywords on buttons when the Java preset is active.
- **Tabs:** new diagram tab uses a localized **“Untitled”**-style title instead of a hard-coded English string.
- **Build / `build.properties`:** empty `timestamp` / `revision` no longer break startup.
- **Build:** Maven **`de.visustruct:visustruct:1.0.6`**, **`visustruct-1.0.6.jar`**, plus **`visustruct.jar`**.

Full notes: [`release-notes/v1.0.6.md`](release-notes/v1.0.6.md).

## Changes in 1.0.5 (summary)

- **Canvas:** colors aligned with VisuStruct light style via **`CanvasStyle`** (background, borders, selection, drag preview).
- **Save / tabs:** tab title and dirty `*` refer to the **correct** diagram; saves use **UTF-8** with an **error dialog** on write failure.
- **macOS:** menu bar **inside the window** (same as Windows/Linux) so **Save** works reliably with FlatLaf; **FlatLaf 3.7.1**; save dialog after tab rename deferred one UI tick.
- **Light theme menus:** **`VisuStructTheme`** improves menu-item readability.

## Changes in 1.0.4 (summary)

This release aligns **branding and technical identity** with **VisuStruct**. **`.visustruct` / XML** project files stay compatible; element types remain numeric in XML.

- **Java packages:** **`de.visustruct.*`** (was `de.kekru.struktogrammeditor`).
- **Maven:** **`de.visustruct:visustruct:1.0.4`**. Dependents must update **groupId**, **artifactId**, and **version** if needed. Fat JAR: **`visustruct-1.0.4.jar`**; workflow also publishes **`visustruct.jar`**.
- **Settings:** default file **`visustruct.properties`**; migrates from **`struktogrammeditor.properties`** on the next settings save.
- **macOS `.app`:** bundle ID and document UTI → **`de.visustruct.*`**; you may need to set **Open With** for `.visustruct` again. **Main:** **`de.visustruct.control.Main`**.
- **Upstream:** [kekru/struktogrammeditor](https://github.com/kekru/struktogrammeditor) remains credited (MIT) in README and the about dialog.

## Changes in 1.0.3 (summary)

- **Build:** Maven Wrapper **3.3.4** (script-only), Maven **3.9.9**; `maven-compiler-plugin` **3.14.1**, `maven-assembly-plugin` **3.7.1**; compile with **`-Xlint:deprecation`**.
- **API / Swing:** modern modifiers (`getModifiersEx`, …), generics in `JListEasy` / `FontChooser`; `JNumberField` deprecation cleanups.

## Changes in 1.0.2 (summary)

- **Code generator:** faster buffered output; clearer comments when “diagram text as comments” is enabled.
- **New-element text presets** in the settings dialog (including English/Java-style placeholders).
- **Branding / files:** VisuStruct and **`.visustruct`** filters and associations.
- **Logo** and minor UI tweaks.

## Platform (1.0.1 and earlier)

- **Java 17** target (build and run with JDK 17+).
- **FlatLaf** light/dark themes; **JDOM2**; no legacy AppleJavaExtensions.
- **Motif** look-and-feel removed; **Metal** and FlatLaf available.
- Current fat JAR name follows **`pom.xml`** **`version`** (e.g. **`visustruct-1.0.7.jar`**).

---

## Download (no build required)

Students only need a **Java 17+** runtime — **not** Maven.

**Latest published build** (stable filename):

**[Download visustruct.jar](https://github.com/code4teaching/VisuStruct/releases/latest/download/visustruct.jar)**

Run from the folder that contains the JAR:

```bash
java -jar visustruct.jar
```

On **Windows**, double-click often works if `.jar` is associated with Java.

All releases: [github.com/code4teaching/VisuStruct/releases](https://github.com/code4teaching/VisuStruct/releases)

**Maintainers:** To ship a new version, create a GitHub **Release** with a tag such as **`v1.0.7`**. Workflow [`.github/workflows/release-assets.yml`](.github/workflows/release-assets.yml) builds the JARs and attaches **`visustruct.jar`** (stable download URL above).

---

## Prerequisites

- [JDK 17](https://adoptium.net/) or newer (to **run** the JAR; to **build**, see below)
- **Building only:** network on first run so Maven can download dependencies

## Build a runnable JAR

Clone or download the project and open a terminal in the project root.

**Windows (PowerShell):**

```powershell
.\mvnw.cmd clean package
```

**Linux or macOS:**

```bash
chmod +x mvnw
./mvnw clean package
```

Output (version from `pom.xml`):

```text
target/visustruct-1.0.7.jar
```

## Run

```bash
java -jar target/visustruct-1.0.7.jar
```

Double-click may work if `.jar` is associated with Java.

---

## Upstream repository and Git

- **Upstream:** [github.com/kekru/struktogrammeditor](https://github.com/kekru/struktogrammeditor)
- **Add `upstream` remote** (once):

  ```bash
  git remote add upstream https://github.com/kekru/struktogrammeditor.git
  ```

- **Merge upstream changes** (simplified):

  ```bash
  git fetch upstream
  git merge upstream/master
  ```

---

## Contributing

Suggestions and **bug reports** are welcome via [**Issues** and **Pull requests**](https://github.com/code4teaching/VisuStruct).

**Commits** and **code comments** may use **English**.  
New **visible UI strings** belong in **`Messages*.properties`** (and **`structure.*`** where applicable) for **en**, **de**, and **pt_PT**; open an issue first if keys or wording are unclear.

---

## License

**MIT** — see [LICENSE](LICENSE). Copyright to the original codebase remains with **Kevin Krummenauer** (also stated in LICENSE).

## Original upstream project page

[whiledo.de — Struktogrammeditor](https://whiledo.de/index.php?p=struktogrammeditor) (original editor by Kevin Krummenauer)
