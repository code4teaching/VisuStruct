package de.visustruct.struktogrammelemente;

import java.awt.Graphics2D;

import de.visustruct.control.GlobalSettings;
import de.visustruct.control.Struktogramm;
import de.visustruct.other.JTextAreaEasy;
import de.visustruct.view.CodeErzeuger;

public class ForSchleife extends WhileSchleife { //erbt von WhileSchleife
   private static final String KOPF_TRENNER = "; ";

   public ForSchleife(Graphics2D g){
      super(g);
      
      setzeText(GlobalSettings.gibElementBeschriftung(Struktogramm.typForSchleife));
   }

   @Override
   protected void randGroesseSetzen(){
      if (hatMehrteiligenKopf()){
         setObererRand(obererRandZusatz + gibTexthoehe(text[0]));
      }else{
         super.randGroesseSetzen();
      }
   }

   @Override
   protected int gibBreiteDerBreitestenTextzeile(){
      if (hatMehrteiligenKopf()){
         return gibTextbreite(gibKopfText());
      }

      return super.gibBreiteDerBreitestenTextzeile();
   }

   @Override
   protected void textZeichnen(){
      if (!hatMehrteiligenKopf()){
         super.textZeichnen();
         return;
      }

      String kopfText = gibKopfText();
      int texthoehe = gibTexthoehe(text[0]);

      g.setColor(getFarbeSchrift());
      g.drawString(kopfText, gibX() + gibXVerschiebungFuerTextInMitte(kopfText), gibY() + texthoehe - 5);
   }

   private boolean hatMehrteiligenKopf(){
      return text != null && text.length > 1;
   }

   private String gibKopfText(){
      return String.join(KOPF_TRENNER, text);
   }
   
   
   @Override     //siehe DoUntilSchleife
   public void quellcodeGenerieren(int typ, int anzahlEingerueckt, int anzahlEinzuruecken, boolean alsKommentar, JTextAreaEasy textarea){
      String vorher = "";
      String nachher = "";


      if (typ == CodeErzeuger.typPython) {
         vorher = quellcodeMitKommentarVorspann("for ", ":\n", typ, anzahlEingerueckt, alsKommentar);
         nachher = "";
      } else if (hatMehrteiligenKopf()) {
         vorher = quellcodeMitKommentarVorspann("for(", "){\n", gibKopfText(), typ, anzahlEingerueckt, alsKommentar);
         nachher = "}\n";
      } else {
         vorher = quellcodeMitKommentarVorspann("for(", "){\n", typ, anzahlEingerueckt, alsKommentar);
         nachher = "}\n";
      }

      textarea.hinzufuegen(wandleZuAusgabe(vorher,typ,anzahlEingerueckt,alsKommentar));
      liste.quellcodeAllerUnterelementeGenerieren(typ,anzahlEingerueckt+anzahlEinzuruecken,anzahlEinzuruecken,alsKommentar,textarea);
      if (!nachher.isEmpty()) {
         textarea.hinzufuegen(wandleZuAusgabe(nachher,typ,anzahlEingerueckt,alsKommentar));
      }

   }

   private String quellcodeMitKommentarVorspann(String linkerTeil, String rechterTeil, String codeText, int typ,
         int anzahlEingerueckt, boolean alsKommentar) {
      StringBuilder sb = new StringBuilder();
      if (alsKommentar) {
         sb.append(wandleZuAusgabe(co("kommentar") + co("text") + co("kommentarzu") + "\n", typ, anzahlEingerueckt, true));
      }
      sb.append(einruecken(linkerTeil + codeText + rechterTeil, anzahlEingerueckt));
      return sb.toString();
   }
   
}