package de.kekru.struktogrammeditor.view;

import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.InsetsUIResource;

import com.formdev.flatlaf.FlatLaf;

/** Zusätzliche UI-Defaults; wirkt vor allem mit FlatLaf. */
public final class UiTheme {

	private UiTheme() {
	}

	public static void applyAfterLookAndFeel() {
		UIManager.put("ScrollPane.smoothScrolling", Boolean.TRUE);

		if (UIManager.getLookAndFeel() instanceof FlatLaf) {
			FontUIResource uiFont = new FontUIResource(Font.SANS_SERIF, Font.PLAIN, 13);
			UIManager.put("defaultFont", uiFont);
			// Leicht größere, klarere Komponenten
			UIManager.put("Component.arc", Integer.valueOf(8));
			UIManager.put("Button.arc", Integer.valueOf(10));
			UIManager.put("TextComponent.arc", Integer.valueOf(8));
			UIManager.put("ScrollBar.width", Integer.valueOf(12));
			UIManager.put("ScrollBar.thumbArc", Integer.valueOf(999));
			UIManager.put("TabbedPane.tabHeight", Integer.valueOf(34));
			UIManager.put("TabbedPane.tabsOverlap", Integer.valueOf(0));
			UIManager.put("TabbedPane.tabInsets", new InsetsUIResource(6, 14, 6, 14));
			UIManager.put("TabbedPane.showTabSeparators", Boolean.TRUE);
			UIManager.put("MenuItem.margin", new InsetsUIResource(4, 10, 4, 10));
			UIManager.put("MenuBar.horizontalGripEnabled", Boolean.FALSE);
		} else {
			UIManager.put("Component.arc", Integer.valueOf(6));
			UIManager.put("Button.arc", Integer.valueOf(8));
			UIManager.put("TextComponent.arc", Integer.valueOf(8));
			UIManager.put("ScrollBar.width", Integer.valueOf(10));
			UIManager.put("TabbedPane.tabHeight", Integer.valueOf(28));
			UIManager.put("TabbedPane.tabsOverlap", Integer.valueOf(0));
		}
	}
}
