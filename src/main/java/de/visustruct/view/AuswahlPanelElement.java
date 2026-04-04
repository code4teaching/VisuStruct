package de.visustruct.view;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

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
		setText(StruktogrammPalette.getPaletteButtonKurzEnglish(typ));
		setToolTipText(StruktogrammPalette.getPaletteElementTooltipEnglish(typ));
		invalidate();
		revalidate();
		repaint();
	}

	public int gibTyp() {
		return typ;
	}
}
