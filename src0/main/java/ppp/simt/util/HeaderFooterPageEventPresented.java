package ppp.simt.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooterPageEventPresented extends PdfPageEventHelper{
	
	public int numCandidates;
	public String speciality;
	public String startDateOfSession;
	public String sessionDateFormatted;
	
	public int getNumCandidates() {
		return numCandidates;
	}

	public void setNumCandidates(int numCandidates) {
		this.numCandidates = numCandidates;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getStartDateOfSession() {
		return startDateOfSession;
	}

	public void setStartDateOfSession(String startDateOfSession) {
		this.startDateOfSession = startDateOfSession;
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
        Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 8);
        Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);
        String text7 ="Liste des candidats declares presents a l'examen national du CAPEC-"+getSpeciality();
        Phrase phrase7 = new Phrase(text7, headFont2);
        String text71 ="List of candidates declared present for the competitive examination to train driving school instructors";
        Phrase phrase71 = new Phrase(text71, headFont2);
        String text8 = "page " + document.getPageNumber();
        Phrase phrase8 = new Phrase(text8, headFont2);
        String text10 = "SESSION DU (FROM) : " + getStartDateOfSession() + " - AU (TO) " + getSessionDateFormatted();
		Phrase phrase10 = new Phrase(text10, headFont2);
		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, phrase10, 700, 30, 0);
        
        
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, phrase7, 156, 30, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, phrase71, 206, 20, 0);
        //ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("examCenter_id"), 700, 30, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, phrase8, 400, 30, 0);
        
		String text888 = "TOTAL : " + getNumCandidates() + " Candidats / Candidates ";
		Phrase phrase888 = new Phrase(text888, headFont3);
		ColumnText.showTextAligned( writer.getDirectContent(), Element.ALIGN_LEFT, phrase888, 330, 40, 0);
    }



}
