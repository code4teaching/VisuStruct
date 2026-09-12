package de.visustruct.view

import de.visustruct.control.GlobalSettings
import de.visustruct.control.Struktogramm
import de.visustruct.i18n.I18n
import de.visustruct.other.JNumberField
import de.visustruct.other.JTextAreaEasy
import de.visustruct.struktogrammelemente.CodeGenRules
import java.awt.BorderLayout
import java.awt.Desktop
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.Font
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.ActionEvent
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import javax.swing.BorderFactory
import javax.swing.ButtonGroup
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JDialog
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JRadioButton
import javax.swing.JScrollPane
import javax.swing.WindowConstants

/** Dialog: Quellcode-Export als Java, Python oder JavaScript. */
class CodeErzeuger(owner: JFrame?, title: String?, modal: Boolean, private val str: Struktogramm) :
    JDialog(owner, title, modal) {

    private val buttongroup = ButtonGroup()
    private val javaButton = JRadioButton()
    private val pythonButton = JRadioButton()
    private val javaScriptButton = JRadioButton()
    private val javaOutputGroup = ButtonGroup()
    private val javaSnippetButton = JRadioButton()
    private val javaClassButton = JRadioButton()
    private lateinit var textarea: JTextAreaEasy
    private val checkboxKommentare = JCheckBox()
    private val jLabel1 = JLabel()
    private val numberfieldEinrueckung = JNumberField()
    private val jLabel2 = JLabel()
    private val numberfieldZeichenzahl = JNumberField()
    private val buttonCodeErzeugen = JButton()
    /** Je nach Zielsprache: Browser (JS) oder Zwischenablage (Java/Python). */
    private val buttonCodeSecondary = JButton()
    private val buttonCodeCopyJs = JButton()
    private val buttonSchliessen = JButton()

    companion object {
        private const val serialVersionUID = 6073577055724789562L

        @JvmField
        val typJava: Int = 0

        /** `match`/`case` für Mehrfachauswahl; Einrückung wie bei Java. */
        @JvmField
        val typPython: Int = 1

        /** Klammer-Syntax wie Java; Ausgabe beginnt mit `"use strict";`. */
        @JvmField
        val typJavaScript: Int = 2

        @JvmStatic
        fun gibKommentarZeichen(kommentarStart: Boolean, codeTyp: Int): String {
            if (codeTyp == typPython) {
                return "\"\"\""
            }
            return if (kommentarStart) "/*" else "*/"
        }

    }

    private fun getSelectedRadioButton(bg: ButtonGroup): JRadioButton? {
        val e = bg.elements
        while (e.hasMoreElements()) {
            val b = e.nextElement()
            if (b.isSelected) return b as JRadioButton
        }
        return null
    }

    init {
        defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
        isResizable = true
        layout = BorderLayout()

        javaButton.text = I18n.tr("dialog.codeGen.targetJava")
        pythonButton.text = I18n.tr("dialog.codeGen.targetPython")
        javaScriptButton.text = I18n.tr("dialog.codeGen.targetJavaScript")
        buttongroup.add(javaButton)
        buttongroup.add(pythonButton)
        buttongroup.add(javaScriptButton)
        when (GlobalSettings.getCodeErzeugerProgrammiersprache()) {
            typPython -> pythonButton.isSelected = true
            typJavaScript -> javaScriptButton.isSelected = true
            else -> javaButton.isSelected = true
        }

        javaSnippetButton.text = I18n.tr("dialog.codeGen.javaSnippet")
        javaClassButton.text = I18n.tr("dialog.codeGen.javaClass")
        javaOutputGroup.add(javaSnippetButton)
        javaOutputGroup.add(javaClassButton)
        javaSnippetButton.isSelected = true

        checkboxKommentare.text = I18n.tr("dialog.codeGen.emitComments")
        checkboxKommentare.isSelected = GlobalSettings.isCodeErzeugerAlsKommentar()
        jLabel1.text = I18n.tr("dialog.codeGen.indentFirstLine")
        numberfieldEinrueckung.columns = 4
        numberfieldEinrueckung.text = "${GlobalSettings.getCodeErzeugerEinrueckungGesamt()}"
        jLabel2.text = I18n.tr("dialog.codeGen.spacesPerLevel")
        numberfieldZeichenzahl.columns = 4
        numberfieldZeichenzahl.text = "${GlobalSettings.getCodeErzeugerEinrueckungProStufe()}"

        buttonCodeErzeugen.text = I18n.tr("dialog.codeGen.generate")
        buttonCodeErzeugen.margin = Insets(4, 10, 4, 10)
        buttonCodeErzeugen.addActionListener { evt -> buttonCodeErzeugen_ActionPerformed(evt) }
        buttonCodeSecondary.margin = Insets(4, 10, 4, 10)
        buttonCodeSecondary.addActionListener { buttonCodeSecondary_ActionPerformed() }
        buttonCodeCopyJs.text = I18n.tr("dialog.codeGen.copyCode")
        buttonCodeCopyJs.toolTipText = I18n.tr("dialog.codeGen.copyCode.tooltip")
        buttonCodeCopyJs.margin = Insets(4, 10, 4, 10)
        buttonCodeCopyJs.addActionListener { copyCodeToClipboard() }
        buttonSchliessen.text = I18n.tr("dialog.codeGen.close")
        buttonSchliessen.margin = Insets(4, 10, 4, 10)
        buttonSchliessen.addActionListener { evt -> buttonSchliessen_ActionPerformed(evt) }

        val sprachWahlListener = java.awt.event.ItemListener { e ->
            if (e.stateChange == java.awt.event.ItemEvent.SELECTED) {
                aktualisiereCodeSecondaryButton()
            }
        }
        javaButton.addItemListener(sprachWahlListener)
        pythonButton.addItemListener(sprachWahlListener)
        javaScriptButton.addItemListener(sprachWahlListener)

        textarea = JTextAreaEasy()
        textarea.rows = 12
        textarea.columns = 56
        textarea.setzeFont(Font(Font.MONOSPACED, Font.PLAIN, 15))
        val editor = JPanel(BorderLayout())
        editor.border = BorderFactory.createEmptyBorder(12, 12, 8, 12)
        editor.add(JScrollPane(textarea), BorderLayout.CENTER)
        add(editor, BorderLayout.CENTER)
        add(baueOptionsPanel(), BorderLayout.SOUTH)

        aktualisiereCodeSecondaryButton()
        pack()
        minimumSize = Dimension(maxOf(420, width / 2), maxOf(320, height / 2))
        setLocationRelativeTo(owner)
        isVisible = true
    }

    private fun baueOptionsPanel(): JPanel {
        val options = JPanel(GridBagLayout())
        options.border = BorderFactory.createEmptyBorder(0, 12, 12, 12)
        val c = GridBagConstraints()
        c.gridx = 0
        c.weightx = 1.0
        c.fill = GridBagConstraints.HORIZONTAL
        c.anchor = GridBagConstraints.WEST
        c.insets = Insets(0, 0, 6, 0)

        val languageRow = JPanel(FlowLayout(FlowLayout.LEADING, 12, 0))
        languageRow.add(javaButton)
        languageRow.add(pythonButton)
        languageRow.add(javaScriptButton)
        languageRow.add(javaSnippetButton)
        languageRow.add(javaClassButton)
        c.gridy = 0
        options.add(languageRow, c)

        c.gridy = 1
        options.add(checkboxKommentare, c)

        c.gridy = 2
        options.add(baueZahlenzeile(jLabel1, numberfieldEinrueckung), c)
        c.gridy = 3
        options.add(baueZahlenzeile(jLabel2, numberfieldZeichenzahl), c)

        val buttons = JPanel(FlowLayout(FlowLayout.LEADING, 8, 4))
        buttons.add(buttonCodeErzeugen)
        buttons.add(buttonCodeSecondary)
        buttons.add(buttonCodeCopyJs)
        buttons.add(buttonSchliessen)
        c.gridy = 4
        c.insets = Insets(4, 0, 0, 0)
        options.add(buttons, c)
        return options
    }

    private fun baueZahlenzeile(label: JLabel, field: JNumberField): JPanel {
        val row = JPanel(GridBagLayout())
        val lc = GridBagConstraints()
        lc.gridx = 0
        lc.gridy = 0
        lc.weightx = 1.0
        lc.fill = GridBagConstraints.HORIZONTAL
        lc.anchor = GridBagConstraints.WEST
        lc.insets = Insets(0, 0, 0, 8)
        row.add(label, lc)
        lc.gridx = 1
        lc.weightx = 0.0
        lc.fill = GridBagConstraints.NONE
        lc.insets = Insets(0, 0, 0, 0)
        row.add(field, lc)
        return row
    }

    private fun aktualisiereCodeSecondaryButton() {
        val js = javaScriptButton.isSelected
        val java = javaButton.isSelected
        javaSnippetButton.isVisible = java
        javaClassButton.isVisible = java
        javaSnippetButton.isEnabled = java
        javaClassButton.isEnabled = java
        buttonCodeSecondary.isEnabled = true
        buttonCodeSecondary.isFocusable = true
        if (js) {
            buttonCodeSecondary.text = I18n.tr("dialog.codeGen.openInBrowser")
            buttonCodeSecondary.toolTipText = I18n.tr("dialog.codeGen.openInBrowser.tooltip")
            buttonCodeCopyJs.isVisible = true
        } else {
            buttonCodeSecondary.text = I18n.tr("dialog.codeGen.copyCode")
            buttonCodeSecondary.toolTipText = I18n.tr("dialog.codeGen.copyCode.tooltip")
            buttonCodeCopyJs.isVisible = false
        }
        contentPane.revalidate()
        contentPane.repaint()
    }

    private fun buttonCodeSecondary_ActionPerformed() {
        if (javaScriptButton.isSelected) {
            openJsPreviewInBrowser()
            return
        }
        copyCodeToClipboard()
    }

    private fun copyCodeToClipboard() {
        var code = textarea.gibText() ?: ""
        Toolkit.getDefaultToolkit().systemClipboard.setContents(StringSelection(code), null)
        JOptionPane.showMessageDialog(
            this,
            I18n.tr("dialog.codeGen.copyDone.message"),
            I18n.tr("dialog.codeGen.copyDone.title"),
            JOptionPane.INFORMATION_MESSAGE,
        )
    }

    private fun openJsPreviewInBrowser() {
        val code = textarea.gibText()
        if (code.isNullOrBlank()) {
            JOptionPane.showMessageDialog(
                this,
                I18n.tr("dialog.codeGen.jsBrowserEmpty.message"),
                I18n.tr("dialog.codeGen.jsBrowserEmpty.title"),
                JOptionPane.WARNING_MESSAGE,
            )
            return
        }
        val escaped = code.replace("(?i)</script>".toRegex(), "<\\/script>")
        val lang = I18n.currentLocale().toLanguageTag()
        val german = lang.lowercase().startsWith("de")
        val title = if (german) "VisuStruct - JavaScript-Test" else "VisuStruct - JavaScript Test"
        val hint = if (german) {
            "Der Code laeuft im Browser. console.log-Ausgaben erscheinen unten."
        } else {
            "The code runs in the browser. console.log output appears below."
        }
        val html = "<!DOCTYPE html>\n" +
            "<html lang=\"$lang\">\n" +
            "<head>\n" +
            "<meta charset=\"UTF-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "<title>" + escapeForHtmlText(title) + "</title>\n" +
            "<style>\n" +
            "body{margin:0;font-family:-apple-system,BlinkMacSystemFont,'Segoe UI',sans-serif;background:#0b1220;color:#e5e7eb;}\n" +
            "header{padding:14px 16px;background:#111827;border-bottom:1px solid rgba(255,255,255,.08);}\n" +
            "h1{margin:0;font-size:14px;font-weight:700;letter-spacing:.01em;}\n" +
            "main{padding:14px 16px;display:grid;gap:12px;max-width:960px;}\n" +
            "pre{margin:0;padding:12px;background:rgba(255,255,255,.06);border:1px solid rgba(255,255,255,.1);border-radius:12px;overflow:auto;white-space:pre-wrap;}\n" +
            "button{padding:10px 12px;border-radius:12px;border:1px solid rgba(255,255,255,.12);background:rgba(59,130,246,.22);color:#fff;font-weight:700;cursor:pointer;}\n" +
            ".hint{font-size:12px;color:rgba(229,231,235,.75);line-height:1.45;}\n" +
            ".code-label{font-size:12px;color:rgba(229,231,235,.65);font-weight:700;text-transform:uppercase;letter-spacing:.06em;}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "<header><h1>" + escapeForHtmlText(title) + "</h1></header>\n" +
            "<main>\n" +
            "<div class=\"hint\">" + escapeForHtmlText(hint) + "</div>\n" +
            "<button id=\"run\">Run</button>\n" +
            "<div class=\"code-label\">Output</div>\n" +
            "<pre id=\"out\"></pre>\n" +
            "<div class=\"code-label\">Code</div>\n" +
            "<pre id=\"code\"></pre>\n" +
            "<script>\n" +
            "const out=document.getElementById('out');\n" +
            "const codeBlock=document.getElementById('code');\n" +
            "const userCode=String.raw`" + escapeForTemplateLiteral(escaped) + "`;\n" +
            "codeBlock.textContent=userCode;\n" +
            "const print=(s)=>{out.textContent+=s+'\\n';};\n" +
            "const oldLog=console.log;\n" +
            "console.log=(...args)=>{oldLog(...args);print(args.map(a=>typeof a==='string'?a:JSON.stringify(a)).join(' '));};\n" +
            "document.getElementById('run').addEventListener('click',()=>{out.textContent='';try{(0,eval)(userCode);}catch(e){print(String(e));}});\n" +
            "</script>\n" +
            "</main>\n" +
            "</body>\n" +
            "</html>\n"
        try {
            val temp = Files.createTempFile("visustruct-js-", ".html")
            Files.writeString(temp, html, StandardCharsets.UTF_8)
            val file = temp.toFile()
            file.deleteOnExit()
            openPreviewFile(file)
        } catch (ex: Exception) {
            var detail = ex.message
            if (detail.isNullOrBlank()) detail = ex.toString()
            JOptionPane.showMessageDialog(
                this,
                I18n.trf("dialog.codeGen.jsBrowserIoError.message", detail),
                I18n.tr("dialog.codeGen.jsBrowserIoError.title"),
                JOptionPane.ERROR_MESSAGE,
            )
        }
    }

    @Throws(Exception::class)
    private fun openPreviewFile(file: File) {
        var letzterFehler: Exception? = null
        if (Desktop.isDesktopSupported()) {
            val desktop = Desktop.getDesktop()
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                try {
                    desktop.open(file)
                    return
                } catch (ex: Exception) {
                    letzterFehler = ex
                }
            }
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(file.toURI())
                    return
                } catch (ex: Exception) {
                    letzterFehler = ex
                }
            }
        }
        if (System.getProperty("os.name", "").lowercase().contains("mac")) {
            try {
                ProcessBuilder("open", file.absolutePath).start()
                return
            } catch (ex: Exception) {
                letzterFehler = ex
            }
        }
        if (letzterFehler != null) throw letzterFehler
        JOptionPane.showMessageDialog(
            this,
            I18n.tr("dialog.codeGen.jsBrowserNoDesktop.message"),
            I18n.tr("dialog.codeGen.jsBrowserNoDesktop.title"),
            JOptionPane.ERROR_MESSAGE,
        )
    }

    private fun escapeForHtmlText(raw: String?): String {
        if (raw == null) return ""
        return raw.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
    }

    private fun escapeForTemplateLiteral(raw: String?): String {
        if (raw == null) return ""
        return raw.replace("\\", "\\\\").replace("`", "\\`").replace("\${'$'}{", "\\\${'$'}{")
    }

    fun buttonCodeErzeugen_ActionPerformed(evt: ActionEvent) {
        if (numberfieldEinrueckung.isNumeric() && numberfieldZeichenzahl.isNumeric()) {
            val radioB = getSelectedRadioButton(buttongroup)
            var typ = typJava
            if (radioB === pythonButton) {
                typ = typPython
            } else if (radioB === javaScriptButton) {
                typ = typJavaScript
            }

            textarea.leeren()
            textarea.beginQuellcodeBatch()
            val einrueckung = numberfieldEinrueckung.getInt()
            val einrueckungProStufe = numberfieldZeichenzahl.getInt()
            val alsKommentar = checkboxKommentare.isSelected
            var batchBeendet = false
            val javaScriptGen = typ == typJavaScript
            try {
                if (javaScriptGen) {
                    textarea.hinzufuegen("\"use strict\";\n\n")
                    CodeGenRules.beginJavaScriptCodeGeneration(str.gibListe())
                }
                try {
                    str.gibListe().quellcodeAllerUnterelementeGenerieren(
                        typ,
                        einrueckung,
                        einrueckungProStufe,
                        alsKommentar,
                        textarea,
                    )
                    textarea.endQuellcodeBatch()
                    batchBeendet = true
                    if (typ == typJava) {
                        var code = javaCodeNachbearbeiten(textarea.gibText(), einrueckungProStufe)
                        textarea.leeren()
                        textarea.hinzufuegen(code)
                    } else {
                        var code = CodeGenRules.postProcessGeneratedCode(textarea.gibText(), typ)
                        if (typ == typPython && CodeGenRules.pythonNeedsRandomImport(code)) {
                            code = "import random\n\n$code"
                        }
                        textarea.leeren()
                        textarea.hinzufuegen(code)
                    }
                } finally {
                    if (javaScriptGen) {
                        CodeGenRules.endJavaScriptCodeGeneration()
                    }
                }
            } finally {
                if (!batchBeendet) {
                    textarea.endQuellcodeBatch()
                }
            }
            GlobalSettings.setCodeErzeugerEinrueckungGesamt(einrueckung)
            GlobalSettings.setCodeErzeugerEinrueckungProStufe(einrueckungProStufe)
            GlobalSettings.setCodeErzeugerProgrammiersprache(typ)
            GlobalSettings.setCodeErzeugerAlsKommentar(alsKommentar)
            GlobalSettings.saveSettings()
            aktualisiereCodeSecondaryButton()
        } else {
            JOptionPane.showMessageDialog(
                this,
                I18n.tr("dialog.codeInvalidInput.message"),
                I18n.tr("dialog.codeInvalidInput.title"),
                JOptionPane.ERROR_MESSAGE,
            )
        }
    }

    private fun javaCodeNachbearbeiten(code: String, einrueckungProStufe: Int): String {
        var body = scannerDeklarationenBereinigen(CodeGenRules.postProcessGeneratedCode(code, typJava))
        val scannerNoetig = CodeGenRules.javaUsesScanner(body)
        if (javaClassButton.isSelected) {
            return javaClassCode(body, scannerNoetig, einrueckungProStufe)
        }
        return if (scannerNoetig) {
            "// import java.util.Scanner;\nScanner scanner = new Scanner(System.in);\n\n$body"
        } else {
            body
        }
    }

    private fun scannerDeklarationenBereinigen(code: String): String {
        val marker = "Scanner scanner = new Scanner(System.in);"
        val importKommentar = "// import java.util.Scanner;"
        val zeilen = code.split("\\R".toRegex())
        val b = StringBuilder()
        for (zeile in zeilen) {
            if (zeile.trim() == importKommentar) continue
            if (zeile.trim() == marker) continue
            if (b.isNotEmpty()) b.append('\n')
            b.append(zeile)
        }
        return b.toString()
    }

    private fun javaClassCode(body: String, scannerNoetig: Boolean, einrueckungProStufe: Int): String {
        val indent = " ".repeat(maxOf(0, einrueckungProStufe))
        val indent2 = indent + indent
        val b = StringBuilder()
        if (scannerNoetig) {
            b.append("import java.util.Scanner;\n\n")
        }
        b.append("public class ").append(javaKlassenName()).append(" {\n")
        b.append(indent).append("public static void main(String[] args) {\n")
        if (scannerNoetig) {
            b.append(indent2).append("Scanner scanner = new Scanner(System.in);\n")
            if (body.isNotBlank()) {
                b.append('\n')
            }
        }
        for (zeile in body.split("\\R".toRegex())) {
            if (zeile.isNotEmpty()) {
                b.append(indent2).append(zeile)
            }
            b.append('\n')
        }
        b.append(indent).append("}\n")
        b.append("}\n")
        return b.toString()
    }

    private fun javaKlassenName(): String {
        var basis: String? = str.getStruktogrammBeschreibung()
        if (basis.isNullOrBlank()) {
            val pfad = str.gibAktuellenSpeicherpfad()
            if (pfad != null && pfad.isNotBlank()) {
                basis = File(pfad).name
                val dot = basis.lastIndexOf('.')
                if (dot > 0) {
                    basis = basis.substring(0, dot)
                }
            }
        }
        return javaIdentifierAusName(basis)
    }

    private fun javaIdentifierAusName(raw: String?): String {
        if (raw.isNullOrBlank()) return "VisuStruct"
        val b = StringBuilder()
        for (part in raw.split("[^A-Za-z0-9_$]+".toRegex())) {
            if (part.isEmpty()) continue
            b.append(part[0].uppercaseChar())
            if (part.length > 1) {
                b.append(part.substring(1))
            }
        }
        if (b.isEmpty()) return "VisuStruct"
        if (!Character.isJavaIdentifierStart(b[0])) {
            b.insert(0, "VisuStruct")
        }
        for (i in 1 until b.length) {
            if (!Character.isJavaIdentifierPart(b[i])) {
                b.setCharAt(i, '_')
            }
        }
        return b.toString()
    }

    fun buttonSchliessen_ActionPerformed(evt: ActionEvent) {
        isVisible = false
    }
}
