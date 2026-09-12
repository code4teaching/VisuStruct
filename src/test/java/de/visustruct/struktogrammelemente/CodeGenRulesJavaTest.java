package de.visustruct.struktogrammelemente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.visustruct.other.JNumberField;
import de.visustruct.other.JTextAreaEasy;
import de.visustruct.view.CodeErzeuger;
import org.junit.jupiter.api.Test;

class CodeGenRulesJavaTest {

	@Test
	void inputLineBecomesJavaScanner() {
		String code = CodeGenRules.generateInstructionLine(
				"input: int zahl \"Zahl eingeben: \"",
				CodeErzeuger.typJava,
				0);
		assertTrue(code.contains("scanner.nextInt()"), code);
		assertTrue(code.contains("Zahl eingeben:"), code);
	}

	@Test
	void outputConcatenationBecomesPrintln() {
		String code = CodeGenRules.generateInstructionLine(
				"output: \"Ergebnis: \" + ergebnis",
				CodeErzeuger.typJava,
				0);
		assertEquals("System.out.println(\"Ergebnis: \" + ergebnis);", code);
	}

	@Test
	void anweisungWritesGeneratedCodeToTextArea() {
		JTextAreaEasy area = new JTextAreaEasy();
		area.beginQuellcodeBatch();
		Anweisung el = new Anweisung("output: \"Ergebnis: \" + ergebnis", null);
		el.quellcodeGenerieren(CodeErzeuger.typJava, 0, 3, false, area);
		area.endQuellcodeBatch();
		assertTrue(area.gibText().contains("System.out.println"), area.gibText());
	}

	@Test
	void indentFieldsAcceptPlainIntegers() {
		JNumberField field = new JNumberField();
		field.setText("3");
		assertTrue(field.isNumeric(), "Generate stays disabled when indent 3 is not numeric");
	}
}
