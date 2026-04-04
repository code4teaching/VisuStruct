package de.visustruct.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import de.visustruct.control.GlobalSettings;

public class EinstellungsDialog extends JDialog {

	private static final long serialVersionUID = -6402017961524470279L;

	public static final int anzahlStruktogrammElemente = 10;

	private final GUI hostGui;

	public EinstellungsDialog(GUI gui, boolean modal) {
		super(gui, "Einstellungen — Textvorlagen", modal);
		hostGui = gui;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 480);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((d.width - getSize().width) / 2, (d.height - getSize().height) / 2);
		setLayout(new BorderLayout(10, 10));

		JLabel kopf = new JLabel("<html><div style='width:420px'>"
				+ "Standardtexte für <b>neu eingefügte</b> Struktogramm-Elemente. "
				+ "Voreinstellung: englisch / Java-ähnlich (z. B. <code>condition</code> in der Verzweigung). "
				+ "Optional deutsch — Vorschau unten.</div></html>");
		kopf.setBorder(BorderFactory.createEmptyBorder(4, 8, 0, 8));
		add(kopf, BorderLayout.NORTH);

		int startIdx = GlobalSettings.getElementBeschriftungPresetIndex();
		if (startIdx < 0 || startIdx >= ElementBeschriftungPresets.ANZAHL_PRESETS) {
			startIdx = ElementBeschriftungPresets.PRESET_ENGLISH_JAVA;
		}

		JPanel radioPanel = new JPanel();
		radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
		radioPanel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
		ButtonGroup gruppe = new ButtonGroup();
		JRadioButton[] radios = new JRadioButton[ElementBeschriftungPresets.ANZAHL_PRESETS];
		for (int p = 0; p < ElementBeschriftungPresets.ANZAHL_PRESETS; p++) {
			JRadioButton rb = new JRadioButton(ElementBeschriftungPresets.PRESET_NAMEN[p]);
			rb.setActionCommand(Integer.toString(p));
			gruppe.add(rb);
			radioPanel.add(rb);
			radioPanel.add(Box.createVerticalStrut(2));
			radios[p] = rb;
			if (p == startIdx) {
				rb.setSelected(true);
			}
		}

		JTextArea vorschau = new JTextArea();
		vorschau.setEditable(false);
		vorschau.setLineWrap(true);
		vorschau.setWrapStyleWord(true);
		vorschau.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		vorschau.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
		vorschau.setText(ElementBeschriftungPresets.alsVorschauText(startIdx));

		Runnable vorschauAktualisieren = () -> {
			int sel = ElementBeschriftungPresets.PRESET_ENGLISH_JAVA;
			for (int p = 0; p < radios.length; p++) {
				if (radios[p].isSelected()) {
					sel = p;
					break;
				}
			}
			vorschau.setText(ElementBeschriftungPresets.alsVorschauText(sel));
		};
		for (JRadioButton rb : radios) {
			rb.addActionListener(e -> vorschauAktualisieren.run());
		}

		JScrollPane scroll = new JScrollPane(vorschau);
		scroll.setBorder(BorderFactory.createTitledBorder("Vorschau der Standardtexte"));

		JPanel mitte = new JPanel(new BorderLayout(0, 8));
		mitte.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 8));
		mitte.add(radioPanel, BorderLayout.NORTH);
		mitte.add(scroll, BorderLayout.CENTER);
		add(mitte, BorderLayout.CENTER);

		JPanel unten = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
		JButton abbrechen = new JButton("Abbrechen");
		abbrechen.addActionListener(e -> setVisible(false));
		JButton ok = new JButton("OK");
		ok.addActionListener(e -> {
			int sel = ElementBeschriftungPresets.PRESET_ENGLISH_JAVA;
			for (int p = 0; p < radios.length; p++) {
				if (radios[p].isSelected()) {
					sel = p;
					break;
				}
			}
			GlobalSettings.wendeElementBeschriftungsPresetAn(sel);
			GlobalSettings.saveSettings();
			SwingUtilities.invokeLater(() -> {
				hostGui.gibAuswahlPanel().aktualisiereBeschriftungen();
				hostGui.gibAuswahlPanel().revalidate();
				hostGui.gibAuswahlPanel().repaint();
			});
			setVisible(false);
		});
		unten.add(abbrechen);
		unten.add(ok);
		add(unten, BorderLayout.SOUTH);

		setVisible(true);
	}
}
