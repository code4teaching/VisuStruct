package de.kekru.struktogrammeditor.struktogrammelemente;

import java.awt.Graphics2D;

import de.kekru.struktogrammeditor.control.GlobalSettings;
import de.kekru.struktogrammeditor.control.Struktogramm;
import de.kekru.struktogrammeditor.other.JTextAreaEasy;
import de.kekru.struktogrammeditor.view.CodeErzeuger;


public class DoUntilSchleife extends Schleife {//erbt von Schleife

   public DoUntilSchleife(Graphics2D g){
      super(g);
      
      setzeText(GlobalSettings.gibElementBeschriftung(Struktogramm.typDoUntilSchleife));
   }
   
   
   
   @Override
   protected void textZeichnen(){
      int texthoehe = gibTexthoehe(text[0]);
      int yVerschiebungAktuell = gibHoehe() -15; //Position der untersten Zeile: 15 Pixel über dem unteren Rand

      g.setColor(getFarbeSchrift());
      
      //Textzeilen werden von unten nach oben gezeichnet (von der Letzten bis zur Ersten)
      for (int i=text.length -1; i >= 0; i--){
         g.drawString(text[i], gibX() + gibXVerschiebungFuerTextInMitte(text[i]), gibY() + yVerschiebungAktuell);
         yVerschiebungAktuell -= texthoehe;
      }
      
      zeichneBeideGroessenaenderungskaestchen(yVerschiebungAktuell);
   }
   
   
   @Override
   public boolean neuesElementMussOberhalbPlatziertWerden(int y){
      return y < gibY() + gibHoehe() - getUntererRand()/2;//hier wird anhand der Position der Maus im Kopfteil (der unten ist) entschieden, weil man beim ganzer Betrachtung nicht unterhalb einfügen kann
   }
   
   
   @Override
   protected void randGroesseSetzen(){
      setUntererRand(obererRandZusatz + text.length * gibTexthoehe(text[0]));//der untere Rand ist der Zusatzrand plus die Höhe aller Textzeilen (obererRandZusatz heißt nur oberer..., weil DoUntilSchleife von Schleife erbt und dort von einem oberen Rand ausgegangen wird)
   }
   
   
   
   

   @Override
   public void quellcodeGenerieren(int typ, int anzahlEingerueckt, int anzahlEinzuruecken, boolean alsKommentar, JTextAreaEasy textarea){
     String vorher = "";
      String nachher = "";


      switch(typ){
         case CodeErzeuger.typJava:
            vorher = "do{\n";
            nachher = quellcodeMitKommentarVorspann("}while(", ");\n", typ, anzahlEingerueckt, alsKommentar);
            break;

         case CodeErzeuger.typDelphi:
            vorher = "repeat\n";
            nachher = quellcodeMitKommentarVorspann("until ", ";\n", typ, anzahlEingerueckt, alsKommentar);
            break;
      }

      textarea.hinzufuegen(wandleZuAusgabe(vorher,typ,anzahlEingerueckt,alsKommentar));//"do{\n" bzw. "repeat\n" wird ausgegeben, richtig eingerückt
      liste.quellcodeAllerUnterelementeGenerieren(typ,anzahlEingerueckt+anzahlEinzuruecken,anzahlEinzuruecken,alsKommentar,textarea);//Quellcode für die Unterelemente wird ausgegeben
      textarea.hinzufuegen(wandleZuAusgabe(nachher,typ,anzahlEingerueckt,alsKommentar));//Schleifenende wird ausgegeben, Kommentare bei Bedarf hinzugefügt und alles richtig eingerückt
   }
   
   
   
   @Override
	public int getObererRand(){
		return 0;
	}
   
   @Override
	public int getUntererRand(){
		return super.getUntererRand() + getYVergroesserung();
	}
   
}