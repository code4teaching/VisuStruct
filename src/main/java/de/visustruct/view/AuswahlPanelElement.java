package de.visustruct.view;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import de.visustruct.i18n.StructureElementI18n;

/** Klickbare Kachel zum Ziehen eines neuen Struktogramm-Elements (FlatLaf-Button-Look). */
public class AuswahlPanelElement extends JButton {

	private static final long serialVersionUID = 5455270690460661892L;
	public static final String iconOrdner = "/icons/";

	private final int typ;

	public AuswahlPanelElement(int typ) {
		this.typ = typ;
		setFocusable(false);
		setRequestFocusEnabled(false);
		setRolloverEnabled(true);
		setDefaultCapable(false);
		setIcon(null);
		setVerticalTextPosition(SwingConstants.CENTER);
		setHorizontalTextPosition(SwingConstants.CENTER);
		setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		aktualisiereBeschriftung();
		PaletteButtonStyle.apply(this);
		// Ohne released-Event (typisch nach DnD) bleibt das Button-Model „pressed“ → grauer Kasten
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				SwingUtilities.invokeLater(() -> PaletteButtonStyle.clearPressedArmedState(AuswahlPanelElement.this));
			}
		});
	}

	public void aktualisiereBeschriftung() {
		setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		// Immer UI-Sprache (I18n); Standardtexte neuer Blöcke weiter über das Text-Preset (Java usw.).
		setText(StructureElementI18n.paletteShortLabel(typ));
		setToolTipText(StructureElementI18n.paletteTooltip(typ));
		invalidate();
		revalidate();
		repaint();
	}

	public int gibTyp() {
		return typ;
	}
}
