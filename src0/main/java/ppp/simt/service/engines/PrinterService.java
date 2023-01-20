package ppp.simt.service.engines;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import com.itextpdf.text.pdf.PdfWriter;

import ppp.simt.entity.production.Application;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.TrainingCenter;

import org.springframework.http.HttpHeaders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

public interface PrinterService {
    public void ouSlipPdf(ByteArrayOutputStream outStream, HttpHeaders headers, int id, int applicationStatus) throws DocumentException, IOException;
    public void printList(ByteArrayOutputStream outStream, HttpHeaders headers, int eligibleCenterId, String typeList) throws DocumentException, IOException;
    public void capacityPreview(ByteArrayOutputStream outStream, HttpHeaders headers, int id, int applicationStatus,HttpServletRequest httpServletRequest,String token) throws DocumentException, IOException;
	void generateImageFromPDF(String filename, String extension , int id )
			throws Exception, IOException;

    public void printListApplicant(ByteArrayOutputStream outStream, HttpHeaders headers, int eligibleCenterId, String typeList) throws DocumentException, IOException;
    //public void printTranscript(ByteArrayOutputStream outStream, HttpHeaders headers, int studentSessionId, int eligiblecenterId) throws DocumentException, IOException;
    public void printTranscript(ByteArrayOutputStream outStream, HttpHeaders headers, int studentSessionId, int eligiblecenterId, HttpServletRequest httpServletRequest) throws DocumentException, IOException;
    public void printEligibleList(ByteArrayOutputStream outStream, HttpHeaders headers, int eligibleCenterId, String typeList) throws DocumentException, IOException;
    public void printCertificate(ByteArrayOutputStream outStream, HttpHeaders headers, int studentSessionId, int eligibleCenterId, String token) throws DocumentException, IOException;
    public void printProfessionalCard(ByteArrayOutputStream outStream, HttpHeaders headers, Application application, String token) throws DocumentException, IOException, ParseException;
	public void printStudentListForTrainingCenter(ByteArrayOutputStream outStream, HttpHeaders headers,int trainingCenterId) throws DocumentException, IOException;
	public void printEligibleListByNote(ByteArrayOutputStream outStream, HttpHeaders headers, int eligibleCenterId, String typeList) throws DocumentException, IOException;
	public void printListByNote(ByteArrayOutputStream outStream, HttpHeaders headers, int eligibleCenterId, String typeList) throws DocumentException, IOException;
	public void printTrainscriptPreview(ByteArrayOutputStream outStream, HttpHeaders headers, int studentSessionId,int eligibleCenterId,HttpServletRequest httpServletRequest) throws DocumentException, IOException ;
	public void generateImageFromtranscriptPDF(String filename, String extension , int id) throws Exception, IOException ;
	public void generateImageFromProfessionalCardPDF(String filename, String extension, int id) throws Exception, IOException;
	public String generateCertificateNumber(String speciality, int certfificateNum);
	public String generateGrade(StudentSession studentSession);
	String generateTranscriptNumber(String speciality, int transcriptNum);
	public String generateProfessionalCardNumber(String speciality, int transcriptNum);
	public void printStudentReceipt(Document document , PdfWriter writer ,ByteArrayOutputStream outStream, HttpHeaders headers, int studentId)throws DocumentException, IOException ;
	public void printBlockRecuDroitsPdf(Document document, PdfWriter writer,ByteArrayOutputStream outStream, HttpHeaders headers, int studentSessionid) throws DocumentException, IOException ;
	public void ficheInscriptionPrintedPdf(ByteArrayOutputStream outStream, HttpHeaders headers, Document document, PdfWriter writer, int id) throws DocumentException, IOException;
	public void ficheInscriptionPrintedPdfEn(ByteArrayOutputStream outStream, HttpHeaders headers, Document document, PdfWriter writer, int id) throws DocumentException, IOException;
	public void printRegistrationForm(Document document , PdfWriter writer,ByteArrayOutputStream outStream, HttpHeaders headers, int eligibleCenterId) throws DocumentException, IOException;
	public void printBlockRecuDroitsPdfEnglish(Document document, PdfWriter writer,ByteArrayOutputStream outStream, HttpHeaders headers, int studentSessionid) throws DocumentException, IOException;
	public void printRegistrationFr(ByteArrayOutputStream outStream, HttpHeaders headers, int studentSessionid) throws DocumentException, IOException;
	public void printRegistrationEn(ByteArrayOutputStream outStream, HttpHeaders headers, int studentSessionid) throws DocumentException, IOException;
	public void printStudentReceiptEn(Document document , PdfWriter writer ,ByteArrayOutputStream outStream, HttpHeaders headers, int studentId) throws DocumentException, IOException;
	public void printCandidatsList(ByteArrayOutputStream outStream, HttpHeaders headers, int eligibleCenterId, String typeList, int trainingCenterId) throws DocumentException, IOException;
	public void printCandidatsList(ByteArrayOutputStream outStream, HttpHeaders headers, int eligibleCenterId, String typeList) throws DocumentException, IOException;
	public void printAdmittedPdf(ByteArrayOutputStream outStream, HttpHeaders headers, EligibleCenter eligibleCenter,TrainingCenter trainingCenter) throws DocumentException, IOException ;
}
