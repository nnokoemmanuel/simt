package ppp.simt.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;


public class HeaderFooterPageEventTranscript extends PdfPageEventHelper {
		
	public void setHeader(Phrase header) {
	    }

    public void onEndPage(PdfWriter writer, Document document) {
        Font headFont2 = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 11);
        String text7 ="POUR LE MINISTERE DES TRANSPORTS";
        Phrase phrase7 = new Phrase(text7, headFont2);
        Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 11);
        String text11 ="FOR THE MINISTRY OF TRANSPORT";
        Phrase phrase11 = new Phrase(text11, headFont2);
       //String text8 = "page " + document.getPageNumber();
        String text8 = "LE DIRECTEUR DES TRANSPORTS ROUTIERS ";
        Phrase phrase8 = new Phrase(text8, headFont2);
        String text18 = "THE DIRECTOR OF ROAD TRANSPORTATION";
        Phrase phrase18 = new Phrase(text18, headFont2);
        
        String text9 = "_____________________________________________";
        Phrase phrase9 = new Phrase(text9, headFont3);
        
       
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(phrase7), 300, 110, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(phrase11), 300, 100, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(phrase8), 297, 90, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(phrase18), 297, 80, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(phrase9), 297, 55, 0);
      
    }

}