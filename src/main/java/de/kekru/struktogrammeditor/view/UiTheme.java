package de.kekru.struktogrammeditor.view;

import javax.swing.UIManager;

/** Zusätzliche UI-Defaults; wirkt vor allem mit FlatLaf, andere LAFs ignorieren meist unbekannte Keys. */
public final class UiTheme {

	private UiTheme() {
	}

	public static void applyAfterLookAndFeel() {
		UIManager.put("ScrollPane.smoothScrolling", Boolean.TRUE);
		UIManager.put("Component.arc", 6);
		UIManager.put("Button.arc", 8);
		UIManager.put("TextComponent.arc", 8);
		UIManager.put("ScrollBar.width", 10);
		UIManager.put("TabbedPane.tabHeight", 28);
		UIManager.put("TabbedPane.tabsOverlap", 0);
	}
}
