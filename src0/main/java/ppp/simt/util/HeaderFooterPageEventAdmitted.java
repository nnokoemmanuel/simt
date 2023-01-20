package ppp.simt.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooterPageEventAdmitted extends PdfPageEventHelper  {
	
	public int numCandidates;
	public String examCenter;
	public String division;
	public String region;
	public String sessionDateFormatted;
	
	public int getNumCandidates() {
		return numCandidates;
	}

	public void setNumCandidates(int numCandidates) {
		this.numCandidates = numCandidates;
	}

    

	public String getExamCenter() {
		return examCenter;
	}

	public void setExamCenter(String examCenter) {
		this.examCenter = examCenter;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSessionDateFormatted() {
		return sessionDateFormatted;
	}

	public void setSessionDateFormatted(String sessionDateFormatted) {
		this.sessionDateFormatted = sessionDateFormatted;
	}

    public void setHeader(Phrase header) {
	    }

    public void onEndPage(PdfWriter writer, Document document) {
    	
        Font headFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8);
        Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);
        String numPage ="page "+document.getPageNumber();
        Phrase phraseNumPage = new Phrase(numPage, headFont2);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, phraseNumPage, 815, 25, 0);
   
    }

	
	
	

}
