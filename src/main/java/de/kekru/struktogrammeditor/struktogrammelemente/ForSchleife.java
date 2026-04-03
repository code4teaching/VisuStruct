package de.kekru.struktogrammeditor.struktogrammelemente;

import java.awt.Graphics2D;

import de.kekru.struktogrammeditor.control.GlobalSettings;
import de.kekru.struktogrammeditor.control.Struktogramm;
import de.kekru.struktogrammeditor.other.JTextAreaEasy;
import de.kekru.struktogrammeditor.view.CodeErzeuger;

public class ForSchleife extends WhileSchleife { //erbt von WhileSchleife

   public ForSchleife(Graphics2D g){
      super(g);
      
      setzeText(GlobalSettings.gibElementBeschriftung(Struktogramm.typForSchleife));
   }
   
   
   @Override     //siehe DoUntilSchleife
   public void quellcodeGenerieren(int typ, int anzahlEingerueckt, int anzahlEinzuruecken, boolean alsKommentar, JTextAreaEasy textarea){
      String vorher = "";
      String nachher = "";


      switch(typ){
         case CodeErzeuger.typJava:
            vorher = quellcodeMitKommentarVorspann("for(", "){\n", typ, anzahlEingerueckt, alsKommentar);
            nachher = "}\n";
            break;

         case CodeErzeuger.typDelphi:
            vorher = (alsKommentar
            		? wandleZuAusgabe(co("kommentar") + co("text") + co("kommentarzu") + "\n", typ, anzahlEingerueckt, true)
            		: "")
            		+ wandleZuAusgabe("for " + co("text") + " do \n" + einruecken("begin\n", anzahlEingerueckt), typ,
            				anzahlEingerueckt, false);
            nachher = "end;\n";
            break;
      }

      textarea.hinzufuegen(wandleZuAusgabe(vorher,typ,anzahlEingerueckt,alsKommentar));
      liste.quellcodeAllerUnterelementeGenerieren(typ,anzahlEingerueckt+anzahlEinzuruecken,anzahlEinzuruecken,alsKommentar,textarea);
      textarea.hinzufuegen(wandleZuAusgabe(nachher,typ,anzahlEingerueckt,alsKommentar));

   }
   
}