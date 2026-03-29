package de.kekru.struktogrammeditor.view;

import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

/**
 * Farben wie auf visustruct.de (CSS-Variablen aus {@code style.css} für Hell,
 * {@code styles.css} für Dunkel). Schlüssel {@code VisuStruct.paletteBackground}
 * für die linke Werkzeugleiste.
 */
public final class VisuStructTheme {

	/** UIDefaults-Schlüssel für die linke Palette (toolbar-bg wie auf der Webseite). */
	public static final String KEY_PALETTE_BACKGROUND = "VisuStruct.paletteBackground";

	private VisuStructTheme() {
	}

	private static ColorUIResource c(int rgb) {
		return new ColorUIResource(rgb);
	}

	/** Hell: :root aus visustruct.de {@code style.css}. */
	public static void applyLightPalette() {
		int bg = 0xFAFAFA;
		int surface = 0xFFFFFF;
		int toolbar = 0xF0F0F0;
		int border = 0xE5E7EB;
		int text = 0x111827;
		int muted = 0x6B7280;
		int accent = 0x3B82F6;
		int select = 0xE5E7EB;
		int tabHover = 0xF3F4F6;

		UIManager.put(KEY_PALETTE_BACKGROUND, c(toolbar));

		UIManager.put("Panel.background", c(bg));
		UIManager.put("Viewport.background", c(bg));
		UIManager.put("ScrollPane.background", c(bg));
		UIManager.put("SplitPane.background", c(bg));

		UIManager.put("MenuBar.background", c(surface));
		UIManager.put("MenuBar.foreground", c(text));
		UIManager.put("Menu.background", c(surface));
		UIManager.put("Menu.foreground", c(text));
		UIManager.put("PopupMenu.background", c(surface));
		UIManager.put("PopupMenu.foreground", c(text));
		UIManager.put("Menu.selectionBackground", c(select));
		UIManager.put("Menu.selectionForeground", c(text));
		UIManager.put("MenuItem.selectionBackground", c(select));
		UIManager.put("MenuItem.selectionForeground", c(text));

		UIManager.put("TabbedPane.background", c(bg));
		UIManager.put("TabbedPane.foreground", c(text));
		UIManager.put("TabbedPane.selectedBackground", c(surface));
		UIManager.put("TabbedPane.selectedForeground", c(text));
		UIManager.put("TabbedPane.underlineColor", c(accent));
		UIManager.put("TabbedPane.hoverColor", c(tabHover));
		UIManager.put("TabbedPane.focusColor", c(accent));

		UIManager.put("Separator.foreground", c(border));
		UIManager.put("Component.borderColor", c(border));

		UIManager.put("Label.foreground", c(text));
		UIManager.put("Button.foreground", c(text));
		UIManager.put("TextField.background", c(surface));
		UIManager.put("TextField.foreground", c(text));
		UIManager.put("TextArea.background", c(surface));
		UIManager.put("TextArea.foreground", c(text));
		UIManager.put("TitledBorder.titleColor", c(muted));

		UIManager.put("Button.default.background", c(accent));
		UIManager.put("Button.default.foreground", c(surface));
	}

	/** Dunkel: Farben aus dem Grid-Layout in {@code styles.css} (ältere Variante). */
	public static void applyDarkPalette() {
		int canvas = 0x0F0F1A;
		int surface = 0x16213E;
		int deep = 0x0F3460;
		int border = 0x2A2A4A;
		int text = 0xEAEAEA;
		int accent = 0xE94560;
		int muted = 0x8892B0;
		int select = 0x2A2A4A;

		UIManager.put(KEY_PALETTE_BACKGROUND, c(surface));

		UIManager.put("Panel.background", c(canvas));
		UIManager.put("Viewport.background", c(canvas));
		UIManager.put("ScrollPane.background", c(canvas));
		UIManager.put("SplitPane.background", c(canvas));

		UIManager.put("MenuBar.background", c(surface));
		UIManager.put("MenuBar.foreground", c(text));
		UIManager.put("Menu.background", c(surface));
		UIManager.put("Menu.foreground", c(text));
		UIManager.put("PopupMenu.background", c(surface));
		UIManager.put("PopupMenu.foreground", c(text));
		UIManager.put("Menu.selectionBackground", c(select));
		UIManager.put("Menu.selectionForeground", c(text));
		UIManager.put("MenuItem.selectionBackground", c(select));
		UIManager.put("MenuItem.selectionForeground", c(text));

		UIManager.put("TabbedPane.background", c(canvas));
		UIManager.put("TabbedPane.foreground", c(text));
		UIManager.put("TabbedPane.selectedBackground", c(deep));
		UIManager.put("TabbedPane.selectedForeground", c(text));
		UIManager.put("TabbedPane.underlineColor", c(accent));
		UIManager.put("TabbedPane.hoverColor", c(select));
		UIManager.put("TabbedPane.focusColor", c(accent));

		UIManager.put("Separator.foreground", c(border));
		UIManager.put("Component.borderColor", c(border));

		UIManager.put("Label.foreground", c(text));
		UIManager.put("Button.foreground", c(text));
		UIManager.put("TextField.background", c(deep));
		UIManager.put("TextField.foreground", c(text));
		UIManager.put("TextArea.background", c(deep));
		UIManager.put("TextArea.foreground", c(text));
		UIManager.put("TitledBorder.titleColor", c(muted));

		UIManager.put("Button.default.background", c(accent));
		UIManager.put("Button.default.foreground", c(0xFFFFFF));
	}
}
