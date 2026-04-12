package de.visustruct.view;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;

import de.visustruct.control.GlobalSettings;
import de.visustruct.i18n.I18n;
import de.visustruct.control.Struktogramm;
import de.visustruct.other.JNumberField;
import de.visustruct.other.JTextAreaEasy;

/** Dialog: Quellcode-Export als Java, Python oder JavaScript. */
public class CodeErzeuger extends JDialog {

	private static final long serialVersionUID = 6073577055724789562L;

	private ButtonGroup buttongroup = new ButtonGroup();
	private JRadioButton javaButton = new JRadioButton();
	private JRadioButton pythonButton = new JRadioButton();
	private JRadioButton javaScriptButton = new JRadioButton();
	private JTextAreaEasy textarea;
	private JCheckBox checkboxKommentare = new JCheckBox();
	private JLabel jLabel1 = new JLabel();
	private JNumberField numberfieldEinrueckung = new JNumberField();
	private JLabel jLabel2 = new JLabel();
	private JNumberField numberfieldZeichenzahl = new JNumberField();
	private JButton buttonCodeErzeugen = new JButton();
	/** Je nach Zielsprache: Browser (JS) oder Zwischenablage (Java/Python). */
	private JButton buttonCodeSecondary = new JButton();
	private JButton buttonSchliessen = new JButton();
	private Struktogramm str;

	public static final int typJava = 0;
	/** {@code match}/{@code case} für Mehrfachauswahl; Einrückung wie bei Java. */
	public static final int typPython = 1;
	/** Klammer-Syntax wie Java; Ausgabe beginnt mit {@code "use strict";}. */
	public static final int typJavaScript = 2;

	public CodeErzeuger(JFrame owner, String title, boolean modal, Struktogramm str) {
		super(owner, title, modal);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		int frameWidth = 498;
		int frameHeight = 458;
		setSize(frameWidth, frameHeight);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - getSize().width) / 2;
		int y = (d.height - getSize().height) / 2;
		setLocation(x, y);
		Container cp = getContentPane();
		cp.setLayout(null);

		javaButton.setBounds(16, 220, 260, 17);
		javaButton.setText(I18n.tr("dialog.codeGen.targetJava"));
		cp.add(javaButton);
		pythonButton.setBounds(16, 242, 260, 17);
		pythonButton.setText(I18n.tr("dialog.codeGen.targetPython"));
		cp.add(pythonButton);
		javaScriptButton.setBounds(16, 264, 260, 17);
		javaScriptButton.setText(I18n.tr("dialog.codeGen.targetJavaScript"));
		cp.add(javaScriptButton);
		buttongroup.add(javaButton);
		buttongroup.add(pythonButton);
		buttongroup.add(javaScriptButton);
		int savedLang = GlobalSettings.getCodeErzeugerProgrammiersprache();
		if (savedLang == typPython) {
			pythonButton.setSelected(true);
		} else if (savedLang == typJavaScript) {
			javaScriptButton.setSelected(true);
		} else {
			javaButton.setSelected(true);
		}

		checkboxKommentare.setBounds(16, 290, 400, 17);
		checkboxKommentare.setText(I18n.tr("dialog.codeGen.emitComments"));
		checkboxKommentare.setSelected(GlobalSettings.isCodeErzeugerAlsKommentar());
		cp.add(checkboxKommentare);
		jLabel1.setBounds(16, 318, 323, 16);
		jLabel1.setText(I18n.tr("dialog.codeGen.indentFirstLine"));
		jLabel1.setFont(new Font("MS Sans Serif", Font.PLAIN, 13));
		cp.add(jLabel1);
		numberfieldEinrueckung.setBounds(344, 318, 49, 24);
		numberfieldEinrueckung.setText(""+GlobalSettings.getCodeErzeugerEinrueckungGesamt());
		cp.add(numberfieldEinrueckung);
		jLabel2.setBounds(16, 350, 300, 16);
		jLabel2.setText(I18n.tr("dialog.codeGen.spacesPerLevel"));
		jLabel2.setFont(new Font("MS Sans Serif", Font.PLAIN, 13));
		cp.add(jLabel2);
		numberfieldZeichenzahl.setBounds(344, 350, 49, 24);
		numberfieldZeichenzahl.setText(""+GlobalSettings.getCodeErzeugerEinrueckungProStufe());
		cp.add(numberfieldZeichenzahl);
		buttonCodeErzeugen.setBounds(16, 386, 120, 25);
		buttonCodeErzeugen.setText(I18n.tr("dialog.codeGen.generate"));
		buttonCodeErzeugen.setMargin(new Insets(2, 2, 2, 2));
		buttonCodeErzeugen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				buttonCodeErzeugen_ActionPerformed(evt);
			}
		});
		cp.add(buttonCodeErzeugen);
		buttonCodeSecondary.setBounds(144, 386, 228, 25);
		buttonCodeSecondary.setMargin(new Insets(2, 2, 2, 2));
		buttonCodeSecondary.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonCodeSecondary_ActionPerformed();
			}
		});
		cp.add(buttonCodeSecondary);
		buttonSchliessen.setBounds(380, 386, 102, 25);
		buttonSchliessen.setText(I18n.tr("dialog.codeGen.close"));
		buttonSchliessen.setMargin(new Insets(2, 2, 2, 2));
		buttonSchliessen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				buttonSchliessen_ActionPerformed(evt);
			}
		});
		cp.add(buttonSchliessen);

		java.awt.event.ItemListener sprachWahlListener = e -> {
			if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
				aktualisiereCodeSecondaryButton();
			}
		};
		javaButton.addItemListener(sprachWahlListener);
		pythonButton.addItemListener(sprachWahlListener);
		javaScriptButton.addItemListener(sprachWahlListener);

		textarea = new JTextAreaEasy(8,10,480,200);
		textarea.setzeFont(new Font("Monospaced", Font.PLAIN, 15));
		textarea.setzeContainer(cp);

		this.str = str;

		aktualisiereCodeSecondaryButton();
		setResizable(false);
		setVisible(true);
	}

	private void aktualisiereCodeSecondaryButton() {
		boolean js = javaScriptButton.isSelected();
		buttonCodeSecondary.setEnabled(true);
		buttonCodeSecondary.setFocusable(true);
		if (js) {
			buttonCodeSecondary.setText(I18n.tr("dialog.codeGen.openInBrowser"));
			buttonCodeSecondary.setToolTipText(I18n.tr("dialog.codeGen.openInBrowser.tooltip"));
		} else {
			buttonCodeSecondary.setText(I18n.tr("dialog.codeGen.copyCode"));
			buttonCodeSecondary.setToolTipText(I18n.tr("dialog.codeGen.copyCode.tooltip"));
		}
	}

	private void buttonCodeSecondary_ActionPerformed() {
		if (javaScriptButton.isSelected()) {
			openJsPreviewInBrowser();
			return;
		}
		String code = textarea.gibText();
		if (code == null) {
			code = "";
		}
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(code), null);
		JOptionPane.showMessageDialog(this, I18n.tr("dialog.codeGen.copyDone.message"),
				I18n.tr("dialog.codeGen.copyDone.title"), JOptionPane.INFORMATION_MESSAGE);
	}

	private void openJsPreviewInBrowser() {
		String code = textarea.gibText();
		if (code == null || code.isBlank()) {
			JOptionPane.showMessageDialog(this, I18n.tr("dialog.codeGen.jsBrowserEmpty.message"),
					I18n.tr("dialog.codeGen.jsBrowserEmpty.title"), JOptionPane.WARNING_MESSAGE);
			return;
		}
		int choice = JOptionPane.showConfirmDialog(this, I18n.tr("dialog.codeGen.jsBrowserHint.message"),
				I18n.tr("dialog.codeGen.jsBrowserHint.title"), JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if (choice != JOptionPane.OK_OPTION) {
			return;
		}
		String escaped = code.replaceAll("(?i)</script>", "<\\/script>");
		String lang = I18n.currentLocale().toLanguageTag();
		String consoleHint = escapeForHtmlText(I18n.tr("dialog.codeGen.jsBrowserConsoleHint"));
		String hintBlock = "<p style=\"font-family:system-ui,Segoe UI,sans-serif;font-size:0.875rem;color:#1f2937;"
				+ "margin:0.75rem 1rem;max-width:42rem;line-height:1.45;white-space:pre-line;\">" + consoleHint + "</p>\n";
		String html = "<!DOCTYPE html>\n<html lang=\"" + lang + "\">\n<head>\n<meta charset=\"UTF-8\">\n<title>"
				+ escapeForHtmlText(I18n.tr("dialog.codeGen.jsBrowserPageTitle")) + "</title>\n</head>\n<body>\n"
				+ hintBlock + "<script>\n"
				+ escaped + "\n</script>\n</body>\n</html>\n";
		try {
			java.nio.file.Path temp = Files.createTempFile("visustruct-js-", ".html");
			Files.writeString(temp, html, StandardCharsets.UTF_8);
			java.io.File file = temp.toFile();
			file.deleteOnExit();
			if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				JOptionPane.showMessageDialog(this, I18n.tr("dialog.codeGen.jsBrowserNoDesktop.message"),
						I18n.tr("dialog.codeGen.jsBrowserNoDesktop.title"), JOptionPane.ERROR_MESSAGE);
				return;
			}
			Desktop.getDesktop().browse(file.toURI());
		} catch (Exception ex) {
			String detail = ex.getMessage();
			if (detail == null || detail.isBlank()) {
				detail = ex.toString();
			}
			JOptionPane.showMessageDialog(this, I18n.trf("dialog.codeGen.jsBrowserIoError.message", detail),
					I18n.tr("dialog.codeGen.jsBrowserIoError.title"), JOptionPane.ERROR_MESSAGE);
		}
	}

	private static String escapeForHtmlText(String raw) {
		if (raw == null) {
			return "";
		}
		return raw.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}

	private static JRadioButton getSelectedRadioButton(ButtonGroup bg) {
		for (java.util.Enumeration<AbstractButton> e = bg.getElements(); e.hasMoreElements();) {
			AbstractButton b = e.nextElement();
			if (b.isSelected()) {
				return (JRadioButton) b;
			}
		}
		return null;
	}

	public void buttonCodeErzeugen_ActionPerformed(ActionEvent evt) {
		if (numberfieldEinrueckung.isNumeric() && numberfieldZeichenzahl.isNumeric()){

			JRadioButton radioB = getSelectedRadioButton(buttongroup);
			int typ = typJava;
			if (radioB == pythonButton) {
				typ = typPython;
			} else if (radioB == javaScriptButton) {
				typ = typJavaScript;
			}

			textarea.leeren();
			textarea.beginQuellcodeBatch();
			final int einrueckung = numberfieldEinrueckung.getInt();
			final int einrueckungProStufe = numberfieldZeichenzahl.getInt();
			final boolean alsKommentar = checkboxKommentare.isSelected();
			try {
				if (typ == typJavaScript) {
					textarea.hinzufuegen("\"use strict\";\n\n");
				}
				str.gibListe().quellcodeAllerUnterelementeGenerieren(typ, einrueckung, einrueckungProStufe, alsKommentar, textarea);
			} finally {
				textarea.endQuellcodeBatch();
			}
			GlobalSettings.setCodeErzeugerEinrueckungGesamt(einrueckung);
			GlobalSettings.setCodeErzeugerEinrueckungProStufe(einrueckungProStufe);
			GlobalSettings.setCodeErzeugerProgrammiersprache(typ);
			GlobalSettings.setCodeErzeugerAlsKommentar(alsKommentar);
			GlobalSettings.saveSettings();
			aktualisiereCodeSecondaryButton();
		}else{
			JOptionPane.showMessageDialog(this, I18n.tr("dialog.codeInvalidInput.message"),
					I18n.tr("dialog.codeInvalidInput.title"), JOptionPane.ERROR_MESSAGE);
		}
	}

	public static String gibKommentarZeichen(boolean kommentarStart, int codeTyp) {
		if (codeTyp == typPython) {
			return "\"\"\"";
		}
		// Java und JavaScript: Blockkommentare wie in Java
		if (kommentarStart) {
			return "/*";
		}
		return "*/";
	}

	public void buttonSchliessen_ActionPerformed(ActionEvent evt) {
		setVisible(false);
	}

}
