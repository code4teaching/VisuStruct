package de.visustruct.struktogrammelemente;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import de.visustruct.control.GlobalSettings;
import de.visustruct.control.Struktogramm;
import de.visustruct.other.JTextAreaEasy;
import de.visustruct.view.CodeErzeuger;


public class Anweisung extends StruktogrammElement {

   public Anweisung(String text, Graphics2D g){
      super(g);
      obererRandZusatz = 10;
      setzeText(text);
   }

   public Anweisung(Graphics2D g){
      this(GlobalSettings.gibElementBeschriftung(Struktogramm.typAnweisung),g);
   }
   
   
   
   
   @Override
   public void quellcodeGenerieren(int typ, int anzahlEingerueckt, int anzahlEinzuruecken, boolean alsKommentar, JTextAreaEasy textarea){
      String zeile;
      if (alsKommentar) {
    	  zeile = wandleZuAusgabe(co("kommentar") + co("text") + co("kommentarzu") + "\n", typ, anzahlEingerueckt, true)
    			  + generiereAnweisungscode(typ, anzahlEingerueckt);
      } else {
    	  zeile = generiereAnweisungscode(typ, anzahlEingerueckt);
      }
      textarea.hinzufuegen(zeile + "\n");
   }

   private String generiereAnweisungscode(int typ, int anzahlEingerueckt){
      if (typ != CodeErzeuger.typJava) {
         return wandleZuAusgabe(co("text"), typ, anzahlEingerueckt, false);
      }

      StringBuilder code = new StringBuilder();
      for (int i = 0; i < text.length; i++) {
         if (i > 0) {
            code.append('\n');
         }
         code.append(einruecken(generiereJavaAnweisungszeile(text[i]), anzahlEingerueckt));
      }
      return code.toString();
   }

   private String generiereJavaAnweisungszeile(String eingabe){
      String zeile = eingabe != null ? eingabe.trim() : "";
      if (zeile.regionMatches(true, 0, "output:", 0, "output:".length())) {
         String ausdruck = zeile.substring("output:".length()).trim();
         if (ausdruck.endsWith(";")) {
            ausdruck = ausdruck.substring(0, ausdruck.length() - 1).trim();
         }
         return "System.out.println(" + ausdruck + ");";
      }

      return eingabe != null ? eingabe : "";
   }
   
   

   
   
   @Override
   protected int gibMindestbreite(){
      return gibBreiteDerBreitestenTextzeile() + getXVergroesserung() + 30;
   }
   
   @Override
   public Rectangle zeichenbereichAktualisieren(int x, int y){
      
      bereich.setBounds(x,y, gibMindestbreite() , getObererRand() + getYVergroesserung());

      return bereich;
   }
   
}