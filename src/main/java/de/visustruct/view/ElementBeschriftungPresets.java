package de.visustruct.view;

import java.util.Arrays;

import de.visustruct.i18n.I18n;
import de.visustruct.i18n.StructureElementI18n;

/**
 * Fest vorgegebene Textvorlagen für neu eingefügte Elemente (keine freie Eingabe).
 * Muss dieselbe Länge haben wie {@link EinstellungsDialog#anzahlStruktogrammElemente}.
 */
public final class ElementBeschriftungPresets {

	public static final int PRESET_KLASSISCH = 0;
	public static final int PRESET_FORMAL = 1;
	public static final int PRESET_JAVA_NA = 2;
	/** Syntaxnahe Java-Vorgaben ({@code if}, {@code condition}, …) — Programm-Standard; Anzeigename z. B. „Java (Standard)“. */
	public static final int PRESET_ENGLISH_JAVA = 3;
	/** Didaktische Begriffe gemäß UI-Sprache ({@link StructureElementI18n}). */
	public static final int PRESET_DIDACTIC_I18N = 4;
	public static final int ANZAHL_PRESETS = 5;

	private static final int N = 10;

	private static final String[][] PRESETS = {
			{"Anweisung", "Verzweigung", "Fallauswahl", "0 < i < anzahl", "While Schleife", "Do-While Schleife",
					"Endlosschleife", "Aussprung", "Aufruf", "\u00f8"},
			{"Anweisung", "Verzweigung", "Fallauswahl", "Z\u00e4hlergesteuerte Schleife", "Kopfgesteuerte Schleife",
					"Fu\u00dfgesteuerte Schleife", "Endlosschleife", "Aussprung", "Aufruf", "Leeres Element"},
			{"Anweisung", "if (Verzweigung)", "switch (Auswahl)", "For Schleife", "While Schleife", "Do-While Schleife",
					"Endlosschleife", "Aussprung", "Aufruf", "Leeres Element"},
			{"Statement", "condition", "selector", "i = 0; i < n; i++",
					"condition", "condition", "\u221e", "break", "method()", "\u00f8"},
	};

	static {
		if (PRESETS.length != ANZAHL_PRESETS - 1) {
			throw new IllegalStateException("Preset-Anzahl");
		}
		for (String[] row : PRESETS) {
			if (row.length != N) {
				throw new IllegalStateException("Preset-L\u00e4nge");
			}
		}
	}

	private ElementBeschriftungPresets() {
	}

	public static String getPresetAnzeigename(int index) {
		switch (index) {
		case PRESET_KLASSISCH:
			return I18n.tr("elementPreset.classic");
		case PRESET_FORMAL:
			return I18n.tr("elementPreset.formal");
		case PRESET_JAVA_NA:
			return I18n.tr("elementPreset.javaLikeDe");
		case PRESET_ENGLISH_JAVA:
			return I18n.tr("elementPreset.englishJava");
		case PRESET_DIDACTIC_I18N:
			return I18n.tr("menu.settings.labelsStruktogramm");
		default:
			return I18n.tr("elementPreset.englishJava");
		}
	}

	public static String[] gibPresetZeile(int index) {
		if (index == PRESET_DIDACTIC_I18N) {
			return StructureElementI18n.didacticDefaultTexts();
		}
		if (index < 0 || index >= ANZAHL_PRESETS) {
			return PRESETS[PRESET_ENGLISH_JAVA];
		}
		return PRESETS[index];
	}

	public static String[] kopierePreset(int index) {
		return Arrays.copyOf(gibPresetZeile(index), N);
	}

	public static void kopierePresetIn(String[] ziel, int index) {
		if (ziel == null || ziel.length != N) {
			throw new IllegalArgumentException("ziel");
		}
		System.arraycopy(gibPresetZeile(index), 0, ziel, 0, N);
	}

	public static int findePresetIndex(String[] aktuell) {
		if (aktuell == null || aktuell.length != N) {
			return -1;
		}
		for (int p = 0; p < ANZAHL_PRESETS; p++) {
			String[] ref = gibPresetZeile(p);
			boolean match = true;
			for (int i = 0; i < N; i++) {
				String a = aktuell[i] != null ? aktuell[i].trim() : "";
				if (!ref[i].equals(a)) {
					match = false;
					break;
				}
			}
			if (match) {
				return p;
			}
		}
		return -1;
	}

	public static String alsVorschauText(int index) {
		String[] row = gibPresetZeile(index);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			if (i > 0) {
				sb.append('\n');
			}
			sb.append(StructureElementI18n.previewRowLabel(i)).append(": ").append(row[i]);
		}
		return sb.toString();
	}
}
