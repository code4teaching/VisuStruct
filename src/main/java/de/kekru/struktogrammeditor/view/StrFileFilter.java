package de.kekru.struktogrammeditor.view;
import java.io.File;

import javax.swing.filechooser.FileFilter;

//FileFilter für Dateitypen zum Abspeichern des Struktogramms und Dateitypen zum Abspeichern als Bilddatei
//http://www.java2s.com/Code/JavaAPI/javax.swing/JFileChoosersetFileFilterFileFilterfilter.htm
public class StrFileFilter extends FileFilter {
	private int filtertyp;
	public static final int filterAlleSpeicherdateien = 0;
	/** Standard-Erweiterung für Struktogramm Studio (Inhalt weiterhin XML). */
	public static final int filterStruktogrammStudio = 8;
	private static final int filterAlleBilddateien = 3;

	public StrFileFilter(int filtertyp){
		this.filtertyp = filtertyp;
	}

	//accept(...)-Methode überschreiben, damit der JFileChooser weis, ob er eine bestimmte Datei anzeigen soll
	public boolean accept(File f){
		return f.isDirectory() || dateiAkzeptiert(f.getAbsolutePath());//Ordner anzeigen und Dateien anzeigen die durch dateiAkzeptiert(...) akzeptiert werden
	}


	//Beschreibungen für die einzelnen Filtertypen
	public String getDescription(){
		switch(filtertyp){
		case filterStruktogrammStudio: return "Struktogramm Studio (*.strukstudio)";
		case filterAlleSpeicherdateien: return "All chart files (*.strukstudio, *.strk, *.xml)";
		case 1: return "Legacy .strk";
		case 2: return "XML (*.xml)";
		case filterAlleBilddateien: return "Image files";
		case 4: return "BMP images";
		case 5: return "GIF images";
		case 6: return "JPEG images";
		case 7: return "PNG images";
		default: return "";
		}
	}


	private String gibAktuelleErweiterung(){//Dateierweiterung bei diesem Filter
		switch(filtertyp){
		case filterStruktogrammStudio: return ".strukstudio";
		case filterAlleSpeicherdateien: return ".strukstudio";
		case 1: return ".strk";
		case 2: return ".xml";
		case filterAlleBilddateien: return ".png";
		case 4: return ".bmp";
		case 5: return ".gif";
		case 6: return ".jpg";
		case 7: return ".png";
		default: return "";
		}
	}


	public String erweiterungBeiBedarfAnhaengen(String pfad){
		/*if (!pfad.endsWith(gibAktuelleErweiterung())){//wenn der Pfad nicht mit der richtigen Dateierweiterung endet...
         return pfad + gibAktuelleErweiterung();//...wird diese angehangen
      }else{
         return pfad;
      }*/

		if(dateiAkzeptiert(pfad)){
			return pfad;
		}else{
			return pfad + gibAktuelleErweiterung();
		}
	}


	/**
	 * Wenn der Name noch keine der üblichen Struktogramm-Speicherendungen hat, wird {@code .strukstudio} angehängt
	 * (z. B. wenn im Speichern-Dialog „Alle Dateien“ gewählt ist).
	 */
	public static String haengeStrukstudioAnFallsKeineSpeicherendung(String pfad){
		String s = pfad.toLowerCase();
		if (s.endsWith(".strukstudio") || s.endsWith(".xml") || s.endsWith(".strk")){
			return pfad;
		}
		return pfad + ".strukstudio";
	}


	private boolean dateiAkzeptiert(String pfad){
		pfad = pfad.toLowerCase();
		switch(filtertyp){
		case filterAlleSpeicherdateien:
			return pfad.endsWith(".strukstudio") || pfad.endsWith(".xml") || pfad.endsWith(".strk");
		case filterAlleBilddateien: return pfad.endsWith(".bmp") || pfad.endsWith(".gif") || pfad.endsWith(".jpg") || pfad.endsWith(".jpeg") || pfad.endsWith(".png"); //wenn der Filter "Bilddateien" ist, werden alle Bilddateien akzeptiert
		default: return pfad.endsWith(gibAktuelleErweiterung()); //es werden nur die Dateien mit genau der ausgesuchten Endung akzeptiert
		}
	}

}