package ppp.simt.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;


public class HeaderFooterPageEventCapec extends PdfPageEventHelper {
	private int numCandidates;
		
	public void setHeader(Phrase header) {
	    }

    public void onEndPage(PdfWriter writer, Document document) {
        Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 8);
        Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);
        String text7 ="Securitised system for MINT";
        Phrase phrase7 = new Phrase(text7, headFont2);

        String text8 = "page " + document.getPageNumber();
        Phrase phrase8 = new Phrase(text8, headFont2);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(phrase7), 110, 30, 0);
        //ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("examCenter_id"), 700, 30, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(phrase8), 400, 30, 0);
        
		String text888 = "TOTAL : " + getNumCandidates() + " Candidats / Candidates ";
		Phrase phrase888 = new Phrase(text888, headFont3);
		ColumnText.showTextAligned( writer.getDirectContent(), Element.ALIGN_LEFT, phrase888, 200, 30, 0);
    }

	public int getNumCandidates() {
		return numCandidates;
	}

	public void setNumCandidates(int numCandidates) {
		this.numCandidates = numCandidates;
	}
    
    

}