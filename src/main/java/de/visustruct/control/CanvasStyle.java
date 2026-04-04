package de.visustruct.control;

import java.awt.Color;

/**
 * Farben und optische Tönung der Struktogramm-Zeichenfläche (nicht Swing-UI).
 * An die helle VisuStruct-Web-Palette angelehnt, weniger „reines Schwarz/Weiß/Gelb“.
 */
public final class CanvasStyle {

	private CanvasStyle() {
	}

	public static final Color BACKGROUND = new Color(0xFAFAFA);
	public static final Color DIAGRAM_FRAME = new Color(0xD1D5DB);
	public static final Color TITLE_TEXT = new Color(0x111827);

	public static final Color ELEMENT_BORDER = new Color(0x4B5563);
	public static final Color ELEMENT_TEXT = new Color(0x111827);

	/** Ersetzt klassisches {@code Color.yellow} bei Markierung. */
	public static final Color ELEMENT_SELECTED_FILL = new Color(0xBFDBFE);

	public static final Color DROP_PREVIEW = new Color(0xFCA5A5);
	public static final Color DRAG_FRAME = new Color(0x3B82F6);
}
