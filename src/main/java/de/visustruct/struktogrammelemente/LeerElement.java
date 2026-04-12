package de.visustruct.struktogrammelemente;


import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import de.visustruct.other.JTextAreaEasy;



public class LeerElement extends Anweisung {//erbt von Anweisung

   public LeerElement(Graphics2D g){
      super("ø", g);
   }
   
   @Override
   public void setzeText(String[] text){
      // LeerElement ist immer fest mit ø beschriftet (unabhängig von Preset/Sprache/XML).
      super.setzeText("ø");
   }

   @Override
   protected void setzeText(String textEineZeile){
      // LeerElement ist immer fest mit ø beschriftet (unabhängig von Preset/Sprache/XML).
      super.setzeText("ø");
   }

   
   @Override
   public Rectangle gibVorschauRect(Point vorschauPoint){
      return new Rectangle(gibX(),gibY(),gibBreite(),gibHoehe());//Voraschaurect geht über das ganze LeerElement, um zu zeigen, dass es beim Einfügen ersetzt wird
   }
   
   
   @Override
   public void quellcodeGenerieren(int typ, int anzahlEingerueckt, int anzahlEinzuruecken, boolean alsKommentar, JTextAreaEasy textarea){
      //LeerElement soll keinen QuellCode generieren
   }
   
}