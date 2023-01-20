package ppp.simt.service.engines;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.list.TreeList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
//import org.codehaus.plexus.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfEncodings;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfWriter;

import ppp.simt.entity.applicant.Applicant;
import ppp.simt.entity.applicant.EntranceEligibleCenter;
import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.evaluation.Course;
import ppp.simt.entity.evaluation.Module;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.Certificate;
import ppp.simt.entity.production.OutSlip;
import ppp.simt.entity.pv.Student;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.user.User;
import ppp.simt.form.CategoryForm;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.form.SimtLicenseChecker;
import ppp.simt.service.applicant.ApplicantService;
import ppp.simt.service.applicant.EntranceEligibleCenterService;
import ppp.simt.service.core.CategoryRetrivalService;
import ppp.simt.service.core.EligibilityChecher;
import ppp.simt.service.core.SpecialityService;
import ppp.simt.service.evaluation.CourseService;
import ppp.simt.service.evaluation.ModuleService;
import ppp.simt.service.evaluation.TranscriptService;
import ppp.simt.service.production.ApplicationService;
import ppp.simt.service.production.CertificateService;
import ppp.simt.service.production.OutSlipService;
import ppp.simt.service.pv.StudentSessionService;
import ppp.simt.service.pv.StudentSessionStatusService;
import ppp.simt.service.pv.TrainingCenterService;
import ppp.simt.service.user.UserService;
import ppp.simt.service.pv.EligibleCenterService;
import ppp.simt.service.pv.StudentService;
import ppp.simt.util.Constants;
import ppp.simt.util.HeaderFooterPageEvent;
import ppp.simt.util.HeaderFooterPageEventAbsent;
import ppp.simt.util.HeaderFooterPageEventAdmitted;
import ppp.simt.util.HeaderFooterPageEventDemissionnaire;
import ppp.simt.util.HeaderFooterPageEventEligib;
import ppp.simt.util.HeaderFooterPageEventFailed;
import ppp.simt.util.HeaderFooterPageEventPresented;
import ppp.simt.util.HeaderFooterPageEventPv;
import ppp.simt.util.HeaderFooterPageEventRejected;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;

@Service
public class PrinterServiceImpl implements PrinterService {

	@Value("${codeArms.file.folder}")
	private String codeArms;

	@Autowired
	private EligibleCenterService eligibleCenterService;

	@Autowired
	private EntranceEligibleCenterService entranceEligibleCenterService;

	@Autowired
	private SpecialityService specialityService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private OutSlipService outSlipService;

	@Autowired
	private Logger_ logger_;

	@Autowired
	private StudentSessionService studentSessionService;

	@Autowired
	private ModuleService moduleService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private TranscriptService transcriptService;

	@Autowired
	private TrainingCenterService trainingCenterService;

	@Autowired
	private ApplicantService applicantService;

	@Autowired
	private QrcodeWriterService qrcodeWriterService;

	@Autowired
	protected StudentService studentService;

	@Autowired
	protected UserService userService;

	@Autowired
	protected Tracker tracker;

	@Autowired
	private CertificateService certificateService;

	@Autowired
	private PrinterService printerService;
	
	@Autowired
	private StudentSessionStatusService studentSessionStatusService;

	@Autowired
	private CategoryRetrivalService categoryRetrival;

	@Autowired
	protected EligibilityChecher eligibilityChecher;

	@Value("${capacity.background.folder}")
	private String capacityBackgroundFolder;

	@Value("${application.photo.folder}")
	private String capacityPhotoFolder;

	@Value("${application.signature.folder}")
	private String capacitySignatureFolder;

	@Value("${capacity.preview.folder}")
	private String capacityImageFolder;

	@Value("${folder.qrcode.images}")
	String rootQrcodeData;

	@Value("${studentSession.photo.folder}")
	private String studentSessionPhotoFolder;

	@Value("${studentSession.signature.folder}")
	private String studentSessionSignatureFolder;

	@Value("${professional.card.folder}")
	private String professionalCardFolder;

	@Value("${professional.card.preview.folder}")
	private String professionalCardPreviewFolder;

	@Value("${certificate.background.folder}")
	private String certificateBackGroundFolder;

	@Value("${certificate.preview.folder}")
	private String certificateFilesFolder;

	@Value("${transcript.preview.folder}")
	private String transcriptPreviewFolder;

	@Value("${transcript.background.folder}")
	private String transcriptBackgroundFolder;

	@Value("${folder.qrcode.images.for.card}")
	String rootQrcodeDataCard;

	@Value("${subscription.file.background.folder}")
	private String subscriptionBackgroundFolder;

	@Value("${application.photo.folder}")
	private String applicationPhotoFolder;

	@Value("${application.signature.folder}")
	private String applicationSignatureFolder;

	@Value("${authority.signature.folder}")
	private String authoritySignatureFolder;
	
	@Value("${admitted.icons.folder}")
	private String admittedListIconsFolder;

	public void ouSlipPdf(ByteArrayOutputStream outStream, HttpHeaders headers, int outSlipid, int applicationStatus)
			throws DocumentException, IOException {

		List<Application> applicationsDel = applicationService.findApplicationByOutSlipAndApplicationStatus(outSlipid,
				applicationStatus);
		List<Application> applicationsDels = applicationService.findApplicationByOutSlipAndApplicationStatus(outSlipid,
				Constants.applicationStatusDelivered);
		applicationsDels.addAll(applicationsDel);

		OutSlip outSlip = outSlipService.findOutSlipById(outSlipid);
		Document document = new Document(PageSize.A4);
		document.setMargins(35, 35, 30, 75);
		PdfWriter writer = PdfWriter.getInstance(document, outStream);
		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		writer.setPageEvent(event);
		document.open();
		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 14);
		Font headFont1 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 8);
		Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);
		Font headFont4 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 5);
		headersPdf(document, writer, false);

		if (applicationStatus == Constants.applicationStatusRejected) {

			String text7 = "Liste de(s) Dossier(s) à problemes: (" + applicationsDel.size() + "dossiers)";
			com.itextpdf.text.pdf.PdfContentByte canvas6 = writer.getDirectContent();
			Phrase phrase7 = new Phrase(text7, headFont1);
			ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase7, 200, 730, 0);
			String text8 = "List of file(s) with problems :(" + applicationsDel.size() + "files)";
			com.itextpdf.text.pdf.PdfContentByte canvas7 = writer.getDirectContent();
			Phrase phrase8 = new Phrase(text8, headFont3);
			ColumnText.showTextAligned(canvas7, Element.ALIGN_LEFT, phrase8, 205, 720, 0);
		} else {

			String text7 = "Bordereau de(s) Dossier(s) Imprimé(s): (" + applicationsDels.size() + " dossiers)";
			com.itextpdf.text.pdf.PdfContentByte canvas6 = writer.getDirectContent();
			Phrase phrase7 = new Phrase(text7, headFont1);
			ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase7, 200, 730, 0);
			String text8 = "List of Printed Documents:(" + applicationsDels.size() + " files)";
			com.itextpdf.text.pdf.PdfContentByte canvas7 = writer.getDirectContent();
			Phrase phrase8 = new Phrase(text8, headFont3);
			ColumnText.showTextAligned(canvas7, Element.ALIGN_LEFT, phrase8, 205, 720, 0);
		}

		String text30 = "";
		Phrase phrase30 = new Phrase(text30, headFont3);
		document.add(phrase30);

		document.add(Chunk.NEWLINE);

		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 3, 9, 10, 10, 8, 8, 7, 5 });
		table.setSpacingBefore(90);
		table.setSpacingAfter(18);

		PdfPCell c1 = new PdfPCell(new Phrase("S/N", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("NUMERO DE PERMIS", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("NOM", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("PRENOM", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("DATE DE NAISSANCE", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("LIEU DE NAISSANCE", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("REFERENCE", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("SPECIALITE", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		table.setHeaderRows(1);
		boolean flag = false;
		if (applicationStatus == Constants.applicationStatusRejected) {
			if (applicationsDel.size() >= 1) {
				int inSlipid = applicationsDel.get(0).getInSlip().getId();

				int i = 0;
				for (Application application : applicationsDel) {
					i += 1;
					PdfPCell cell;
					Person person = null;

					// application.getTranscript().getStudentSession().getStudent().getPerson();
					if (application.getTranscript() != null) {
						person = application.getTranscript().getStudentSession().getStudent().getPerson();
					} else if (application.getArchive() != null) {
						person = application.getArchive().getPerson();
					} else {
						person = application.getCertificate().getStudentSession().getStudent().getPerson();
					}
					if (inSlipid == application.getInSlip().getId()) {

						// PdfPCell cell;

						cell = new PdfPCell(new Phrase(String.valueOf(i), headFont2));
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setPaddingRight(5);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase(person.getLicenseNum(), headFont2));
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(5);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase(person.getSurName().toUpperCase(), headFont2));
						cell.setPaddingLeft(5);
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase(person.getGivenName().toUpperCase(), headFont2));
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(5);
						table.addCell(cell);
						SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
						String stringDate = DateFor.format(person.getDob());

						cell = new PdfPCell(new Phrase(stringDate, headFont2));
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(5);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase(person.getPob().toUpperCase(), headFont2));
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(5);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase(application.getSerialNumber(), headFont2));
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(5);
						table.addCell(cell);

						// Get Person Speciality monitor etc

						cell = new PdfPCell(new Phrase(application.getSpeciality().getAbbreviation(), headFont2));
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(5);
						table.addCell(cell);

					} else {
						flag = true;
						if (flag) {
							inSlipid = application.getInSlip().getId();
							cell = new PdfPCell(new Phrase("  "));
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setColspan(8);
							cell.setBackgroundColor(BaseColor.LIGHT_GRAY);

							cell.setPaddingRight(5);
							table.addCell(cell);
							flag = false;
						}
						if (!flag) {
							cell = new PdfPCell(new Phrase(String.valueOf(i), headFont2));
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setPaddingRight(5);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(person.getLicenseNum(), headFont2));
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(5);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(person.getSurName().toUpperCase(), headFont2));
							cell.setPaddingLeft(5);
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(person.getGivenName().toUpperCase(), headFont2));
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(5);
							table.addCell(cell);
							SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
							String stringDate = DateFor.format(person.getDob());

							cell = new PdfPCell(new Phrase(stringDate, headFont2));
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(5);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(person.getPob().toUpperCase(), headFont2));
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(5);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(application.getSerialNumber(), headFont2));
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(5);
							table.addCell(cell);

							// Get person speciality Monitor

							cell = new PdfPCell(new Phrase(application.getSpeciality().getAbbreviation(), headFont2));
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(5);
							table.addCell(cell);
						}

					}
				}
			}
		} else {
			if (applicationsDels.size() >= 1) {
				int inSlipid = applicationsDels.get(0).getInSlip().getId();

				int i = 0;
				for (Application application : applicationsDels) {
					i += 1;
					PdfPCell cell;
					Person person = null;

					if (application.getTranscript() != null) {
						person = application.getTranscript().getStudentSession().getStudent().getPerson();
					} else if (application.getArchive() != null) {
						person = application.getArchive().getPerson();
					} else {
						person = application.getCertificate().getStudentSession().getStudent().getPerson();
					}

					if (inSlipid == application.getInSlip().getId()) {

						// PdfPCell cell;

						cell = new PdfPCell(new Phrase(String.valueOf(i), headFont2));
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setPaddingRight(5);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase(person.getLicenseNum(), headFont2));
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(5);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase(person.getSurName().toUpperCase(), headFont2));
						cell.setPaddingLeft(5);
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase(person.getGivenName().toUpperCase(), headFont2));
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(5);
						table.addCell(cell);
						SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
						String stringDate = DateFor.format(person.getDob());

						cell = new PdfPCell(new Phrase(stringDate, headFont2));
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(5);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase(person.getPob().toUpperCase(), headFont2));
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(5);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase(application.getSerialNumber(), headFont2));
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(5);
						table.addCell(cell);

						// Get person speciality Monitor

						cell = new PdfPCell(new Phrase(application.getSpeciality().getAbbreviation(), headFont2));
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setPaddingRight(5);
						table.addCell(cell);

					} else {
						flag = true;
						if (flag) {
							inSlipid = application.getInSlip().getId();
							// PdfPHeaderCell headersCell = new
							// PdfPHeaderCell();
							cell = new PdfPCell(new Phrase("  "));
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							// cell.setBorderWidthLeft(0);
							// cell.setBorderWidthRight(0);
							cell.setColspan(8);
							cell.setBackgroundColor(BaseColor.LIGHT_GRAY);

							cell.setPaddingRight(5);
							table.addCell(cell);
							flag = false;
						}
						if (!flag) {
							cell = new PdfPCell(new Phrase(String.valueOf(i), headFont2));
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setPaddingRight(5);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(person.getLicenseNum(), headFont2));
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(5);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(person.getSurName().toUpperCase(), headFont2));
							cell.setPaddingLeft(5);
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(person.getGivenName().toUpperCase(), headFont2));
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(5);
							table.addCell(cell);
							SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
							String stringDate = DateFor.format(person.getDob());

							cell = new PdfPCell(new Phrase(stringDate, headFont2));
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(5);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(person.getPob().toUpperCase(), headFont2));
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(5);
							table.addCell(cell);

							cell = new PdfPCell(new Phrase(application.getSerialNumber(), headFont2));
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(5);
							table.addCell(cell);

							// Get person speciality Monitor

							cell = new PdfPCell(new Phrase(application.getSpeciality().getAbbreviation(), headFont2));
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
							cell.setPaddingRight(5);
							table.addCell(cell);
						}

					}
				}
			}
		}
		document.add(table);
		document.close();

		headers.add("Content-Disposition", "inline; filename=pdf-sample.pdf");
	}

	public void printList(ByteArrayOutputStream outStream, HttpHeaders headers, int eligibleCenterId, String typeList)
			throws DocumentException, IOException {
		EligibleCenter eligibleCenter = eligibleCenterService.findById(eligibleCenterId);
		List<StudentSession> candidateSessions = new ArrayList<StudentSession>();

		Document document = null;

		document = new Document(PageSize.A4.rotate());
		String speciality = null;
		document.setMargins(35, 35, 30, 75);
		PdfWriter writer = PdfWriter.getInstance(document, outStream);

		SimpleDateFormat sessionDate = new SimpleDateFormat("dd/MM/yyyy");
		String sessionDateFormatted = sessionDate.format(eligibleCenter.getExamSession().getSessionDate());

		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		String start = formatter.format(eligibleCenter.getExamSession().getSessionDate());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(start));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.add(Calendar.DATE, -2); // number of days to add
		String startDateOfSession = sdf.format(calendar.getTime());

		document.open();
		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 14);
		Font headFont1 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 8);
		Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);
		Font headFont4 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 5);
		// l'entete du pdf
		headersPdf(document, writer, true);

		if (typeList.contains("print_pv")) {
			speciality = typeList.split("@")[1];
			Speciality candidateSpeciality = specialityService.findByAbbreviation(speciality);

			candidateSessions = studentSessionService
					.findByEligibleCenterAndResultAndSpecialityOrderBySurname(eligibleCenter, 4, candidateSpeciality);

			HeaderFooterPageEventPv event = new HeaderFooterPageEventPv();
			event.setNumCandidates(candidateSessions.size());
			event.setSpeciality(speciality);
			event.setSessionDateFormatted(sessionDateFormatted);
			event.setStartDateOfSession(startDateOfSession);
			writer.setPageEvent(event);

			generatePvListsHeaders(candidateSessions, typeList.split("@")[0], speciality, eligibleCenter, headFont1,
					headFont3, writer);

			String text30 = "";
			Phrase phrase30 = new Phrase(text30, headFont3);
			document.add(phrase30);

			document.add(Chunk.NEWLINE);

			PdfPTable table = new PdfPTable(7);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 3, 8, 12, 11, 5, 7, 5 });
			table.setSpacingBefore(110);
			table.setSpacingAfter(18);

			PdfPCell c1 = new PdfPCell(new Phrase("No PV", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("ECOLE DE FORMATION", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("NOM", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("PRENOM", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("DATE DE NAISSANCE", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("LIEU DE NAISSANCE", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("GENRE", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);

			table.setHeaderRows(1);

			for (StudentSession candidateSession : candidateSessions) {
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(candidateSession.getPvNumber()), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(candidateSession.getStudent().getTrainingCenter().getName(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(candidateSession.getStudent().getPerson().getSurName(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				if (candidateSession.getStudent().getPerson().getGivenName() == null) {
					cell = new PdfPCell(new Phrase(" ", headFont2));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(
							new Phrase(candidateSession.getStudent().getPerson().getGivenName(), headFont2));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}

				SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
				String stringDate = DateFor.format(candidateSession.getStudent().getPerson().getDob());

				cell = new PdfPCell(new Phrase(stringDate, headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(
						new Phrase(candidateSession.getStudent().getPerson().getPob().toUpperCase(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(candidateSession.getStudent().getPerson().getGender(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

			}
			document.add(table);
		} else {
			speciality = typeList.split("@")[1];
			Speciality candidateSpeciality = specialityService.findByAbbreviation(speciality);
			if (typeList.contains("candidates_list_appouved")) {
				candidateSessions = studentSessionService.findByEligibleCenterAndResultAndSpeciality(eligibleCenter, 2,
						candidateSpeciality);
				HeaderFooterPageEventEligib event = new HeaderFooterPageEventEligib();
				event.setNumCandidates(candidateSessions.size());
				event.setSpeciality(speciality);
				event.setSessionDateFormatted(sessionDateFormatted);
				event.setStartDateOfSession(startDateOfSession);
				writer.setPageEvent(event);

			} else if (typeList.contains("candidates_list_presented")) {
				candidateSessions = studentSessionService.findByEligibleCenterAndResultAndSpeciality(eligibleCenter, 1,
						candidateSpeciality);
				HeaderFooterPageEventPresented event = new HeaderFooterPageEventPresented();
				event.setNumCandidates(candidateSessions.size());
				event.setSpeciality(speciality);
				event.setSessionDateFormatted(sessionDateFormatted);
				event.setStartDateOfSession(startDateOfSession);
				writer.setPageEvent(event);
			} else if (typeList.contains("candidates_list_rejected")) {
				candidateSessions = studentSessionService.findByEligibleCenterAndResultAndSpeciality(eligibleCenter, 3,
						candidateSpeciality);
				HeaderFooterPageEventRejected event = new HeaderFooterPageEventRejected();
				event.setNumCandidates(candidateSessions.size());
				event.setSpeciality(speciality);
				event.setSessionDateFormatted(sessionDateFormatted);
				event.setStartDateOfSession(startDateOfSession);
				writer.setPageEvent(event);

			} else if (typeList.contains("candidates_failed")) {
				candidateSessions = studentSessionService.findByEligibleCenterAndResultAndSpeciality(eligibleCenter, 5,
						candidateSpeciality);
				HeaderFooterPageEventFailed event = new HeaderFooterPageEventFailed();
				event.setNumCandidates(candidateSessions.size());
				event.setSpeciality(speciality);
				event.setSessionDateFormatted(sessionDateFormatted);
				event.setStartDateOfSession(startDateOfSession);
				writer.setPageEvent(event);

			} else if (typeList.contains("candidates_list_absent")) {
				candidateSessions = studentSessionService.findByEligibleCenterAndResultAndSpeciality(eligibleCenter, 6,
						candidateSpeciality);
				HeaderFooterPageEventAbsent event = new HeaderFooterPageEventAbsent();
				event.setNumCandidates(candidateSessions.size());
				event.setSpeciality(speciality);
				event.setSessionDateFormatted(sessionDateFormatted);
				event.setStartDateOfSession(startDateOfSession);
				writer.setPageEvent(event);

			} else if (typeList.contains("candidates_list_demissionnaire")) {
				candidateSessions = studentSessionService.findByEligibleCenterAndResultAndSpeciality(eligibleCenter, 7,
						candidateSpeciality);
				HeaderFooterPageEventDemissionnaire event = new HeaderFooterPageEventDemissionnaire();
				event.setNumCandidates(candidateSessions.size());
				event.setSpeciality(speciality);
				event.setSessionDateFormatted(sessionDateFormatted);
				event.setStartDateOfSession(startDateOfSession);
				writer.setPageEvent(event);

			}
			generatePvListsHeaders(candidateSessions, typeList.split("@")[0], speciality, eligibleCenter, headFont1,
					headFont3, writer);

			String text30 = "";
			Phrase phrase30 = new Phrase(text30, headFont3);
			document.add(phrase30);

			document.add(Chunk.NEWLINE);

			PdfPTable table = new PdfPTable(7);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 3, 8, 12, 11, 5, 7, 5 });
			table.setSpacingBefore(110);
			table.setSpacingAfter(18);

			PdfPCell c1 = new PdfPCell(new Phrase("No", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("ECOLE FORMATION", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("NOM", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("PRENOM", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("DATE DE NAISSANCE", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("LIEU DE NAISSANCE", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("GENDER", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);

			table.setHeaderRows(1);

			int i = 1;
			for (StudentSession candidateSession : candidateSessions) {
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(i), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(candidateSession.getStudent().getTrainingCenter().getName(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(
						new Phrase(candidateSession.getStudent().getPerson().getSurName().toUpperCase(), headFont2));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				if (candidateSession.getStudent().getPerson().getGivenName() == null) {
					cell = new PdfPCell(new Phrase(" ", headFont2));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(new Phrase(
							candidateSession.getStudent().getPerson().getGivenName().toUpperCase(), headFont2));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}

				SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
				String stringDate = DateFor.format(candidateSession.getStudent().getPerson().getDob());

				cell = new PdfPCell(new Phrase(stringDate, headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(
						new Phrase(candidateSession.getStudent().getPerson().getPob().toUpperCase(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				if (candidateSession.getStudent().getPerson().getGender() == null) {
					cell = new PdfPCell(new Phrase(" ", headFont2));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(
							new Phrase(candidateSession.getStudent().getPerson().getGender().toUpperCase(), headFont2));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}

				i++;
			}
			document.add(table);
		}

		document.close();
		headers.add("Content-Disposition", "inline; filename=pdf-sample.pdf");

	}

	public void headersPdf(Document document, PdfWriter writer, boolean isLandScape)
			throws IOException, DocumentException {
		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 14);
		Font headFont1 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 8);
		Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);
		Font headFont4 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 5);

		if (isLandScape) {
			Image image = Image.getInstance(codeArms + "cameroun.jpg");
			image.setAbsolutePosition(390f, 530f);
			image.scaleAbsolute(60f, 60f);
			document.add(image);
			String text1 = "REPUBLIQUE DU CAMEROUN";
			com.itextpdf.text.pdf.PdfContentByte canvas = writer.getDirectContent();
			Phrase phrase1 = new Phrase(text1, headFont2);
			ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase1, 27, 580, 0);
			String text2 = "PAIX-TRAVAIL-PATRIE";
			com.itextpdf.text.pdf.PdfContentByte canvas1 = writer.getDirectContent();
			Phrase phrase2 = new Phrase(text2, headFont4);
			ColumnText.showTextAligned(canvas1, Element.ALIGN_LEFT, phrase2, 60, 572, 0);
			String text9 = "------------------";
			com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
			Phrase phrase10 = new Phrase(text9, headFont4);
			ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase10, 70, 567, 0);
			String text3 = "MINISTERE DES TRANSPORTS";
			com.itextpdf.text.pdf.PdfContentByte canvas2 = writer.getDirectContent();
			Phrase phrase3 = new Phrase(text3, headFont2);
			ColumnText.showTextAligned(canvas2, Element.ALIGN_LEFT, phrase3, 27, 560, 0);

			com.itextpdf.text.pdf.PdfContentByte canvas11 = writer.getDirectContent();
			Phrase phrase12 = new Phrase(text9, headFont4);
			ColumnText.showTextAligned(canvas11, Element.ALIGN_LEFT, phrase12, 70, 555, 0);

			String text15 = "DIRECTION DES TRANSPORTS ROUTIERS";
			com.itextpdf.text.pdf.PdfContentByte canvas15 = writer.getDirectContent();
			Phrase phrase15 = new Phrase(text15, headFont2);
			ColumnText.showTextAligned(canvas15, Element.ALIGN_LEFT, phrase15, 8, 548, 0);

			String text4 = "REPUBLIC OF CAMEROON";
			com.itextpdf.text.pdf.PdfContentByte canvas3 = writer.getDirectContent();
			Phrase phrase4 = new Phrase(text4, headFont2);
			ColumnText.showTextAligned(canvas3, Element.ALIGN_RIGHT, phrase4, 805, 580, 0);
			String text5 = "PEACE-WORK-FATHERLAND";
			com.itextpdf.text.pdf.PdfContentByte canvas4 = writer.getDirectContent();
			Phrase phrase5 = new Phrase(text5, headFont4);
			ColumnText.showTextAligned(canvas4, Element.ALIGN_RIGHT, phrase5, 785, 572, 0);
			String text10 = "------------------";
			com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
			Phrase phrase11 = new Phrase(text10, headFont4);
			ColumnText.showTextAligned(canvas10, Element.ALIGN_RIGHT, phrase11, 765, 567, 0);
			String text6 = "MINISTRY OF TRANSPORT";
			com.itextpdf.text.pdf.PdfContentByte canvas5 = writer.getDirectContent();
			Phrase phrase6 = new Phrase(text6, headFont2);
			ColumnText.showTextAligned(canvas5, Element.ALIGN_RIGHT, phrase6, 805, 560, 0);

			com.itextpdf.text.pdf.PdfContentByte canvas13 = writer.getDirectContent();
			Phrase phrase13 = new Phrase(text10, headFont4);
			ColumnText.showTextAligned(canvas13, Element.ALIGN_LEFT, phrase13, 735, 555, 0);

			String text26 = "DEPARTMENT OF ROAD TRANSPORT";
			com.itextpdf.text.pdf.PdfContentByte canvas26 = writer.getDirectContent();
			Phrase phrase26 = new Phrase(text26, headFont2);
			ColumnText.showTextAligned(canvas26, Element.ALIGN_RIGHT, phrase26, 825, 548, 0);
		} else {

			Image image = Image.getInstance(codeArms + "cameroun.jpg");
			image.setAbsolutePosition(260f, 760f);
			image.scaleAbsolute(60f, 60f);
			document.add(image);

			String text1 = "REPUBLIQUE DU CAMEROUN";
			com.itextpdf.text.pdf.PdfContentByte canvas = writer.getDirectContent();
			Phrase phrase1 = new Phrase(text1, headFont2);
			ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase1, 35, 810, 0);
			String text2 = "PAIX-TRAVAIL-PATRIE";
			com.itextpdf.text.pdf.PdfContentByte canvas1 = writer.getDirectContent();
			Phrase phrase2 = new Phrase(text2, headFont4);
			ColumnText.showTextAligned(canvas1, Element.ALIGN_LEFT, phrase2, 60, 802, 0);
			String text9 = "------------------";
			com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
			Phrase phrase10 = new Phrase(text9, headFont4);
			ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase10, 70, 797, 0);
			String text3 = "MINISTERE DES TRANSPORTS";
			com.itextpdf.text.pdf.PdfContentByte canvas2 = writer.getDirectContent();
			Phrase phrase3 = new Phrase(text3, headFont2);
			ColumnText.showTextAligned(canvas2, Element.ALIGN_LEFT, phrase3, 35, 790, 0);

			com.itextpdf.text.pdf.PdfContentByte canvas11 = writer.getDirectContent();
			Phrase phrase12 = new Phrase(text9, headFont4);
			ColumnText.showTextAligned(canvas11, Element.ALIGN_LEFT, phrase12, 70, 785, 0);

			String text15 = "DIRECTION DES TRANSPORTS ROUTIERS";
			com.itextpdf.text.pdf.PdfContentByte canvas15 = writer.getDirectContent();
			Phrase phrase15 = new Phrase(text15, headFont2);
			ColumnText.showTextAligned(canvas15, Element.ALIGN_LEFT, phrase15, 30, 778, 0);

			String text4 = "REPUBLIC OF CAMEROON";
			com.itextpdf.text.pdf.PdfContentByte canvas3 = writer.getDirectContent();
			Phrase phrase4 = new Phrase(text4, headFont2);
			ColumnText.showTextAligned(canvas3, Element.ALIGN_RIGHT, phrase4, 555, 810, 0);
			String text5 = "PEACE-WORK-FATHERLAND";
			com.itextpdf.text.pdf.PdfContentByte canvas4 = writer.getDirectContent();
			Phrase phrase5 = new Phrase(text5, headFont4);
			ColumnText.showTextAligned(canvas4, Element.ALIGN_RIGHT, phrase5, 535, 802, 0);
			String text10 = "------------------";
			com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
			Phrase phrase11 = new Phrase(text10, headFont4);
			ColumnText.showTextAligned(canvas10, Element.ALIGN_RIGHT, phrase11, 515, 797, 0);
			String text6 = "MINISTRY OF TRANSPORT";
			com.itextpdf.text.pdf.PdfContentByte canvas5 = writer.getDirectContent();
			Phrase phrase6 = new Phrase(text6, headFont2);
			ColumnText.showTextAligned(canvas5, Element.ALIGN_RIGHT, phrase6, 555, 790, 0);

			com.itextpdf.text.pdf.PdfContentByte canvas13 = writer.getDirectContent();
			Phrase phrase13 = new Phrase(text10, headFont4);
			ColumnText.showTextAligned(canvas13, Element.ALIGN_LEFT, phrase13, 485, 785, 0);

			String text26 = "DEPARTMENT OF ROAD TRANSPORT";
			com.itextpdf.text.pdf.PdfContentByte canvas26 = writer.getDirectContent();
			Phrase phrase26 = new Phrase(text26, headFont2);
			ColumnText.showTextAligned(canvas26, Element.ALIGN_RIGHT, phrase26, 560, 778, 0);

		}

	}

	public void capacityPreview(ByteArrayOutputStream outStream, HttpHeaders headers, int id, int applicationStatus,
			HttpServletRequest httpServletRequest, String token) throws DocumentException, IOException {
		Application application = applicationService.findAppById(id);
		String signature = application.getSignature();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		// List<CandidateSession> candidateSessions =
		// application.getCapacity().getPerson().getCandidateSessions();
		List<Archive> archives = new ArrayList<Archive>();
		// archives.addAll(application.getCapacity().getPerson().getArchives());
		int processed = 1;

		String date = formatter.format(new Date());
		Document document = new Document(new Rectangle(243, 153));
		Document document1 = new Document(new Rectangle(243, 153));

		File file = new File(capacityImageFolder + "marine_capacity.pdf");
		FileOutputStream pdfFileout = new FileOutputStream(file);
		PdfWriter.getInstance(document, pdfFileout);
		PdfWriter.getInstance(document1, pdfFileout);

		PdfWriter writer = PdfWriter.getInstance(document, outStream);
		Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 5);
		document.open();
		if (!(applicationStatus == 4 || applicationStatus == 11)) {
			logger_.log(Constants.NORMAL_LOG_DIR,
					"Echec Impression de permis à un status non autorisé , application  ID=" + application.getId(),
					httpServletRequest);
			return;

		} else {

			Image background = Image.getInstance(capacityBackgroundFolder + "GD 3.jpg/");
			background.setAbsolutePosition(0, 0);
			background.scaleAbsolute(243, 153);
			if (token == "preview")
				document.add(background);

			Image pic = Image.getInstance(capacityPhotoFolder + application.getPhoto());
			pic.setAbsolutePosition(7f, 24f);
			pic.scaleAbsolute(70, 80);
			document.add(pic);

			Image sig = Image.getInstance(capacitySignatureFolder + signature);
			sig.setAbsolutePosition(90, 24);
			sig.scaleAbsolute(20, 15);
			document.add(sig);

			// Date dobDate = application.getCapacity().getPerson().getDob();
			// String dobDateFinal = formatter.format(dobDate);

			com.itextpdf.text.pdf.PdfContentByte canvas2 = writer.getDirectContent();
			// Phrase phrase2 = new
			// Phrase(application.getCapacity().getPerson().getSurName().toUpperCase(),
			// headFont2);
			// ColumnText.showTextAligned(canvas2, Element.ALIGN_LEFT, phrase2,
			// 90, 109, 0);

			com.itextpdf.text.pdf.PdfContentByte canvas3 = writer.getDirectContent();
			// Phrase phrase3 = new Phrase(
			// application.getCapacity().getPerson().getGivenName().toUpperCase()
			// ,
			// headFont2);
			// ColumnText.showTextAligned(canvas3, Element.ALIGN_LEFT, phrase3,
			// 90, 97, 0);

			com.itextpdf.text.pdf.PdfContentByte canvas4 = writer.getDirectContent();
			// Phrase phrase4 = new Phrase(dobDateFinal , headFont2);
			// ColumnText.showTextAligned(canvas4, Element.ALIGN_LEFT, phrase4,
			// 93, 84, 0);

			com.itextpdf.text.pdf.PdfContentByte canvas5 = writer.getDirectContent();
			Phrase phrase5 = new Phrase(application.getAuthority().getPosition().toUpperCase(), headFont2);
			ColumnText.showTextAligned(canvas5, Element.ALIGN_LEFT, phrase5, 157, 84, 0);

			com.itextpdf.text.pdf.PdfContentByte canvas8 = writer.getDirectContent();
			// Phrase phrase8 = new Phrase(
			// application.getCapacity().getPerson().getPob().toUpperCase(),
			// headFont2);
			// ColumnText.showTextAligned(canvas8, Element.ALIGN_LEFT, phrase8,
			// 93, 72, 0);
			// handling expiry date of archive
			if (!(archives == null)) {

			}
			/*
			 * else if (!( candidateSessions== null)) { //obtain list of all
			 * candidate sessions for a given person List<CandidateSession>
			 * cansessionsAll =
			 * studentSessionService.findCandidateSessionByPersonId(id); //case
			 * of extention
			 * if(!(application.getCapacity().getCapacityNumber()==null)) {
			 *
			 * List<CandidateSession> canSessions =
			 * studentSessionService.findByPersonIdAndProcessed(application.
			 * getCapacity(). getPerson(), processed);
			 * cansessionsAll.removeAll(canSessions); for (int i = 0; i <
			 * cansessionsAll.size(); i++) {
			 * if(cansessionsAll.get(i).getExamResult()==5) { Date expiryDate =
			 * cansessionsAll.get(i).getExpiryDate(); String expiryDateFinal =
			 * formatter.format(expiryDate);
			 *
			 * com.itextpdf.text.pdf.PdfContentByte canvas6 =
			 * writer.getDirectContent(); Phrase phrase6 = new
			 * Phrase(expiryDateFinal , headFont2);
			 * ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase6,
			 * 93, 59, 0);
			 *
			 * com.itextpdf.text.pdf.PdfContentByte canvas11 =
			 * writer.getDirectContent(); Phrase phrase11 = new
			 * Phrase(cansessionsAll.get(i).getCategory().getName(), headFont2);
			 * ColumnText.showTextAligned(canvas11, Element.ALIGN_LEFT,
			 * phrase11, 15, 15, 0); }
			 *
			 *
			 * }
			 *
			 *
			 * }else if (application.getCapacity().getCapacityNumber()==null) {
			 * for (int i = 0; i < cansessionsAll.size(); i++) {
			 * if(cansessionsAll.get(i).getExamResult()==5) { Date expiryDate =
			 * cansessionsAll.get(i).getExpiryDate(); String expiryDateFinal =
			 * formatter.format(expiryDate);
			 *
			 * com.itextpdf.text.pdf.PdfContentByte canvas6 =
			 * writer.getDirectContent(); Phrase phrase6 = new
			 * Phrase(expiryDateFinal , headFont2);
			 * ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase6,
			 * 93, 59, 0);
			 *
			 * com.itextpdf.text.pdf.PdfContentByte canvas11 =
			 * writer.getDirectContent(); Phrase phrase11 = new
			 * Phrase(cansessionsAll.get(i).getCategory().getName(), headFont2);
			 * ColumnText.showTextAligned(canvas11, Element.ALIGN_LEFT,
			 * phrase11, 15, 15, 0);
			 *
			 *
			 * }} }
			 *
			 * }
			 */
			com.itextpdf.text.pdf.PdfContentByte canvas7 = writer.getDirectContent();
			Phrase phrase7 = new Phrase("1", headFont2);
			ColumnText.showTextAligned(canvas7, Element.ALIGN_LEFT, phrase7, 93, 47, 0);

			com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
			Phrase phrase9 = new Phrase(application.getSerialNumber(), headFont2);
			ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase9, 157, 59, 0);

			com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
			// Phrase phrase10 = new
			// Phrase(application.getCapacity().getPerson().getNationality().getCountryName().toUpperCase()
			// , headFont2);
			// ColumnText.showTextAligned(canvas10, Element.ALIGN_LEFT,
			// phrase10, 157, 70, 0);

		}

		document.newPage();
		Image background1 = Image.getInstance(capacityBackgroundFolder + "GD.jpg");
		background1.setAbsolutePosition(0, 0);
		background1.scaleAbsolute(243, 153);
		if (token == "preview")
			document.add(background1);
		String finala = "";
		/*
		 * if(application.getCapacity().getPerson().getCandidateSessions()!=
		 * null ){ //obtain list of all candidate sessions for a given person
		 * List<CandidateSession> cansessionsAll =
		 * application.getCapacity().getPerson().getCandidateSessions(); for
		 * (int i = 0; i < cansessionsAll.size(); i++) {
		 * if(cansessionsAll.get(i).getExamResult()==5) { Date examSessionCand =
		 * cansessionsAll.get(i).getEligibleCenter().getExamSession().
		 * getSessionDate(); String examSessionCandFinal =
		 * formatter.format(examSessionCand);
		 * if(cansessionsAll.get(i).getCategory().getId()==1) {
		 * com.itextpdf.text.pdf.PdfContentByte canvas12 =
		 * writer.getDirectContent(); Phrase phrase12 = new
		 * Phrase(cansessionsAll.get(i).getPerson().getNationality().
		 * getCountryName(). toUpperCase() , headFont2);
		 * ColumnText.showTextAligned(canvas12, Element.ALIGN_LEFT, phrase12,
		 * 157, 73, 0);
		 *
		 * com.itextpdf.text.pdf.PdfContentByte canvas13 =
		 * writer.getDirectContent(); Phrase phrase13 = new
		 * Phrase(examSessionCandFinal, headFont2);
		 * ColumnText.showTextAligned(canvas13, Element.ALIGN_LEFT, phrase13,
		 * 70, 73, 0);
		 * if(!(application.getCapacity().getCapacityNumber()==null)) { Date
		 * expiryDate = cansessionsAll.get(i).getExpiryDate(); String
		 * expiryDateFinal = formatter.format(expiryDate);
		 *
		 * com.itextpdf.text.pdf.PdfContentByte canvas14 =
		 * writer.getDirectContent(); Phrase phrase14 = new
		 * Phrase(expiryDateFinal, headFont2);
		 * ColumnText.showTextAligned(canvas14, Element.ALIGN_LEFT, phrase14,
		 * 110, 73, 0 ); } } else if
		 * (cansessionsAll.get(i).getCategory().getId()==2) {
		 * com.itextpdf.text.pdf.PdfContentByte canvas12 =
		 * writer.getDirectContent(); Phrase phrase12 = new
		 * Phrase(application.getCapacity().getPerson().getNationality().
		 * getCountryName( ).toUpperCase() , headFont2);
		 * ColumnText.showTextAligned(canvas12, Element.ALIGN_LEFT, phrase12,
		 * 157, 63, 0);
		 *
		 * com.itextpdf.text.pdf.PdfContentByte canvas13 =
		 * writer.getDirectContent(); Phrase phrase13 = new
		 * Phrase(examSessionCandFinal, headFont2);
		 * ColumnText.showTextAligned(canvas13, Element.ALIGN_LEFT, phrase13,
		 * 70, 63, 0);
		 * capacityService.capacityExpiryDateCal(application.getId(),
		 * cansessionsAll.get(i).getPerson().getCapacity()); Date expiryDate =
		 * cansessionsAll.get(i).getExpiryDate(); String expiryDateFinal =
		 * formatter.format(expiryDate);
		 *
		 * com.itextpdf.text.pdf.PdfContentByte canvas14 =
		 * writer.getDirectContent(); Phrase phrase14 = new
		 * Phrase(expiryDateFinal, headFont2);
		 * ColumnText.showTextAligned(canvas14, Element.ALIGN_LEFT, phrase14,
		 * 110, 63, 0 );
		 *
		 * } } }
		 *
		 * } else {
		 * if(application.getCapacity().getPerson().getArchives()!=null) { for
		 * (int i = 0; i < archives.size(); i++) { Date examSessionArch =
		 * archives.get(i).getExamDate(); String examSessionArchFinal =
		 * formatter.format(examSessionArch); List<ArchiveCategory> archiveCats
		 * = (List<ArchiveCategory>) archives.get(i).getArchiveCategory();
		 * if(archiveCats!=null) { for (int n = 0; n < archiveCats.size(); n++)
		 * { if(archiveCats.get(n).getCategory().getId()==1) {
		 * com.itextpdf.text.pdf.PdfContentByte canvas12 =
		 * writer.getDirectContent(); Phrase phrase12 = new
		 * Phrase(application.getCapacity().getPerson().getNationality().
		 * getCountryName( ).toUpperCase() , headFont2);
		 * ColumnText.showTextAligned(canvas12, Element.ALIGN_LEFT, phrase12,
		 * 157, 63, 0);
		 *
		 * com.itextpdf.text.pdf.PdfContentByte canvas13 =
		 * writer.getDirectContent(); Phrase phrase13 = new
		 * Phrase(examSessionArchFinal, headFont2);
		 * ColumnText.showTextAligned(canvas13, Element.ALIGN_LEFT, phrase13,
		 * 70, 63, 0);
		 *
		 * if(!(application.getCapacity().getCapacityNumber()==null)) { Date
		 * expiryDate = archives.get(i).getExpiryDate(); String expiryDateFinal
		 * = formatter.format(expiryDate);
		 *
		 * com.itextpdf.text.pdf.PdfContentByte canvas14 =
		 * writer.getDirectContent(); Phrase phrase14 = new
		 * Phrase(expiryDateFinal, headFont2);
		 * ColumnText.showTextAligned(canvas14, Element.ALIGN_LEFT, phrase14,
		 * 110, 63, 0 ); }
		 *
		 * }else if (archiveCats.get(n).getCategory().getId()==2) {
		 * com.itextpdf.text.pdf.PdfContentByte canvas12 =
		 * writer.getDirectContent(); Phrase phrase12 = new
		 * Phrase(application.getCapacity().getPerson().getNationality().
		 * getCountryName( ).toUpperCase() , headFont2);
		 * ColumnText.showTextAligned(canvas12, Element.ALIGN_LEFT, phrase12,
		 * 157, 63, 0);
		 *
		 * com.itextpdf.text.pdf.PdfContentByte canvas13 =
		 * writer.getDirectContent(); Phrase phrase13 = new
		 * Phrase(examSessionArchFinal, headFont2);
		 * ColumnText.showTextAligned(canvas13, Element.ALIGN_LEFT, phrase13,
		 * 70, 63, 0);
		 *
		 * if(!(application.getCapacity().getCapacityNumber()==null)) { Date
		 * expiryDate = archives.get(i).getExpiryDate(); String expiryDateFinal
		 * = formatter.format(expiryDate);
		 *
		 * com.itextpdf.text.pdf.PdfContentByte canvas14 =
		 * writer.getDirectContent(); Phrase phrase14 = new
		 * Phrase(expiryDateFinal, headFont2);
		 * ColumnText.showTextAligned(canvas14, Element.ALIGN_LEFT, phrase14,
		 * 110, 63, 0 ); }
		 *
		 * } } }
		 *
		 * }
		 *
		 *
		 *
		 *
		 *
		 *
		 * } }
		 *
		 * qrcodeWriterService.generateEncryptedQrcode(id); Image codeQrImage =
		 * Image.getInstance(rootQrcodeData+id+".png"); //Image codeQrImage =
		 * barcodeQRCode.getImage(); codeQrImage.setAbsolutePosition(200, 105);
		 * codeQrImage.scaleAbsolute(40, 40); document.add(codeQrImage);
		 * document.close();
		 */

	}

	public void downloadPDF(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "attachment;filename=" + "testPDF.pdf");
		try {
			File f = new File(capacityImageFolder + "capacity_marine.pdf");
			FileInputStream fis = new FileInputStream(f);
			DataOutputStream os = new DataOutputStream(response.getOutputStream());
			response.setHeader("Content-Length", String.valueOf(f.length()));
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) >= 0) {
				os.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void generateImageFromPDF(String filename, String extension, int id) throws Exception, IOException {
		PDDocument document = PDDocument.load(new File(filename));
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		//FileUtils.cleanDirectory(certificateFilesFolder);
		for (int page = 0; page < document.getNumberOfPages(); ++page) {
			BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
			ImageIOUtil.writeImage(bim,
					String.format(certificateFilesFolder + "certificate_" + id + "-%d.%s", page + 1, extension), 300);
		}
		document.close();

	}

	@Override
	public void generateImageFromtranscriptPDF(String filename, String extension, int id)
			throws Exception, IOException {

		PDDocument document = PDDocument.load(new File(filename));
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		//FileUtils.cleanDirectory(transcriptPreviewFolder);
		for (int page = 0; page < document.getNumberOfPages(); ++page) {
			BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
			ImageIOUtil.writeImage(bim,
					String.format(transcriptPreviewFolder + "transcript_" + id + "-%d.%s", page + 1, extension), 300);
		}
		document.close();
	}

	@Override
	public void generateImageFromProfessionalCardPDF(String filename, String extension, int id)
			throws Exception, IOException {
		PDDocument document = PDDocument.load(new File(filename));
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		//FileUtils.cleanDirectory(professionalCardPreviewFolder);
		// System.out.println("document number of pages " +
		// document.getNumberOfPages());
		for (int page = 0; page < document.getNumberOfPages(); ++page) {
			BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
			ImageIOUtil.writeImage(bim, String.format(
					professionalCardPreviewFolder + "professional_card_" + id + "-%d.%s", page + 1, extension), 300);
		}
		document.close();

	}

	/**
	 * @MPA private function to adapt Header based on Speciality
	 */

	private void generatePvListsHeaders(List<StudentSession> candidateSessions, String listType, String speciality,
			EligibleCenter eligibleCenter, Font headFont1, Font headFont3, PdfWriter writer) {
		Speciality specialityObject = null;
		String specialityEnglish = null;
		String specialityFrench = null;
		if (speciality != null) {
			specialityObject = specialityService.findByAbbreviation(speciality);
			specialityFrench = "( CAPEC-" + speciality + " ) ";
			specialityEnglish = "( DSIPC-" + speciality + " ) ";
		}

		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 9);

		SimpleDateFormat sessionDate = new SimpleDateFormat("dd/MM/yyyy");
		String sessionDateFormatted = sessionDate.format(eligibleCenter.getExamSession().getSessionDate());

		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		String start = formatter.format(eligibleCenter.getExamSession().getSessionDate());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(start));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.add(Calendar.DATE, -2); // number of days to add
		String startDateOfSession = sdf.format(calendar.getTime());

		if (listType.equals("print_pv") || listType.equals("print_pv_note")) {
			String text7 = "RESULTAT DEFINITIF A L'EXAMEN NATIONAL DU CERTIFICAT D'APTITUDE PROFESSIONELLE A L'ENSEIGNEMENT DE LA CONDUITE DES VEHICULES A MOTEURS,";
			com.itextpdf.text.pdf.PdfContentByte canvas6 = writer.getDirectContent();
			Phrase phrase7 = new Phrase(text7, headFont);
			ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase7, 50, 500, 0);

			String text77 = "OPTION " + specialityObject.getName().toUpperCase() + " " + specialityFrench;
			com.itextpdf.text.pdf.PdfContentByte canvas66 = writer.getDirectContent();
			Phrase phrase77 = new Phrase(text77, headFont);

			// print align header w.r.t speciality
			String MAE = "Moniteur Auto Ecole";
			// System.out.println("MAE ="+MAE);
			if (specialityObject.getName().equalsIgnoreCase(MAE)) {
				ColumnText.showTextAligned(canvas66, Element.ALIGN_LEFT, phrase77, 310, 485, 0);
				System.out.println("MAE");
			} else {
				ColumnText.showTextAligned(canvas66, Element.ALIGN_LEFT, phrase77, 200, 485, 0);
				System.out.println("EXPERT");
			}

			String text8 = "FINAL RESULTS FOR THE NATIONAL EXAMINATION OF THE DRIVING SCHOOL INSTRUCTORS PROFESSIONAL CERTIFICATE,";
			com.itextpdf.text.pdf.PdfContentByte canvas7 = writer.getDirectContent();
			Phrase phrase8 = new Phrase(text8, headFont3);
			ColumnText.showTextAligned(canvas7, Element.ALIGN_LEFT, phrase8, 170, 470, 0);

			String text88 = "OPTION " + specialityObject.getNameEnglish().toUpperCase() + " " + specialityEnglish;
			System.out.println("text88" + text88);
			com.itextpdf.text.pdf.PdfContentByte canvas77 = writer.getDirectContent();
			Phrase phrase88 = new Phrase(text88, headFont3);

			// ColumnText.showTextAligned(canvas77, Element.ALIGN_LEFT,
			// phrase88, 310, 455,
			// 0);
			// ColumnText.showTextAligned(canvas66, Element.ALIGN_LEFT,
			// phrase77, 200, 455,
			// 0);

			// print OPTION to align with header w.r.t speciality
			String MAE1 = "Moniteur Auto Ecole";
			// System.out.println("MAE ="+MAE);
			if (specialityObject.getName().equalsIgnoreCase(MAE1)) {
				ColumnText.showTextAligned(canvas66, Element.ALIGN_LEFT, phrase77, 310, 455, 0);
				System.out.println("MAE");
			} else {
				ColumnText.showTextAligned(canvas66, Element.ALIGN_LEFT, phrase77, 200, 455, 0);
				System.out.println("EXPERT");
			}

			String text10 = "SESSION DU (FROM) : " + startDateOfSession + " - AU (TO) " + sessionDateFormatted;
			com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
			Phrase phrase10 = new Phrase(text10, headFont3);
			ColumnText.showTextAligned(canvas10, Element.ALIGN_LEFT, phrase10, 270, 440, 0);
			String text9 = "CENTRE : " + eligibleCenter.getExamCenter().getName();
			com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
			Phrase phrase9 = new Phrase(text9, headFont3);
			ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase9, 500, 440, 0);

		} else if (listType.equals("candidates_list_appouved")) {

			String text7 = "LISTE DES CANDIDATS DECLARES ELIGIBLES A L'EXAMEN NATIONAL DU CERTIFICAT D'APTITUDE ";
			com.itextpdf.text.pdf.PdfContentByte canvas6 = writer.getDirectContent();
			Phrase phrase7 = new Phrase(text7, headFont);
			ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase7, 210, 500, 0);

			String text7B = "PROFESSIONELLE A L'ENSEIGNEMENT DE LA CONDUITE DES VEHICULES A MOTEURS (CAPEC-"
					+ speciality + ")";
			com.itextpdf.text.pdf.PdfContentByte canvas6B = writer.getDirectContent();
			Phrase phrase7B = new Phrase(text7B, headFont);
			ColumnText.showTextAligned(canvas6B, Element.ALIGN_LEFT, phrase7B, 205, 485, 0);

			String text8 = "LIST OF CANDIDATES DECLARED ELIGIBLE FOR THE COMPETITIVE EXAMINATION ";
			com.itextpdf.text.pdf.PdfContentByte canvas7 = writer.getDirectContent();
			Phrase phrase8 = new Phrase(text8, headFont3);
			ColumnText.showTextAligned(canvas7, Element.ALIGN_LEFT, phrase8, 250, 470, 0);

			String text8B = "FOR DRIVING SCHOOL INSTRUCTOR PROFESSIONAL CERTIFICATE (DSIPC-" + speciality + ")";
			com.itextpdf.text.pdf.PdfContentByte canvas7B = writer.getDirectContent();
			Phrase phrase8B = new Phrase(text8B, headFont3);
			ColumnText.showTextAligned(canvas7B, Element.ALIGN_LEFT, phrase8B, 247, 455, 0);

			String text10 = "SESSION DE (FROM) : " + startDateOfSession + " - A (TO) " + sessionDateFormatted;
			com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
			Phrase phrase10 = new Phrase(text10, headFont3);
			ColumnText.showTextAligned(canvas10, Element.ALIGN_LEFT, phrase10, 270, 440, 0);
			String text9 = "CENTRE : " + eligibleCenter.getExamCenter().getName();
			com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
			Phrase phrase9 = new Phrase(text9, headFont3);
			ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase9, 500, 440, 0);

		} else if (listType.contains("candidates_list_presented")) {

			String text7 = "LISTE DES CANDIDATS DECLARES PRESENTS A L'EXAMEN NATIONAL DU CERTIFICAT D'APTITUDE ";
			com.itextpdf.text.pdf.PdfContentByte canvas6 = writer.getDirectContent();
			Phrase phrase7 = new Phrase(text7, headFont);
			ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase7, 210, 500, 0);

			String text7B = "PROFESSIONELLE A L'ENSEIGNEMENT DE LA CONDUITE DES VEHICULES A MOTEURS (CAPEC-"
					+ speciality + ")";
			com.itextpdf.text.pdf.PdfContentByte canvas6B = writer.getDirectContent();
			Phrase phrase7B = new Phrase(text7B, headFont);
			ColumnText.showTextAligned(canvas6B, Element.ALIGN_LEFT, phrase7B, 205, 485, 0);

			String text8 = "LIST OF CANDIDATES DECLARED PRESENT FOR THE COMPETITIVE EXAMINATION ";
			com.itextpdf.text.pdf.PdfContentByte canvas7 = writer.getDirectContent();
			Phrase phrase8 = new Phrase(text8, headFont3);
			ColumnText.showTextAligned(canvas7, Element.ALIGN_LEFT, phrase8, 250, 470, 0);

			String text8B = "FOR DRIVING SCHOOL INSTRUCTOR PROFESSIONAL CERTIFICATE (DSIPC-" + speciality + ")";
			com.itextpdf.text.pdf.PdfContentByte canvas7B = writer.getDirectContent();
			Phrase phrase8B = new Phrase(text8B, headFont3);
			ColumnText.showTextAligned(canvas7B, Element.ALIGN_LEFT, phrase8B, 247, 455, 0);

			String text10 = "SESSION DU (FROM) : " + startDateOfSession + " - AU (TO) " + sessionDateFormatted;
			com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
			Phrase phrase10 = new Phrase(text10, headFont3);
			ColumnText.showTextAligned(canvas10, Element.ALIGN_LEFT, phrase10, 270, 440, 0);
			String text9 = "CENTRE : " + eligibleCenter.getExamCenter().getName();
			com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
			Phrase phrase9 = new Phrase(text9, headFont3);
			ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase9, 500, 440, 0);

		} else if (listType.contains("candidates_list_rejected")) {

			String text7 = "LISTE DES CANDIDATS DECLARES REJETES A L'EXAMEN NATIONAL DU CERTIFICAT D'APTITUDE ";
			com.itextpdf.text.pdf.PdfContentByte canvas6 = writer.getDirectContent();
			Phrase phrase7 = new Phrase(text7, headFont);
			ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase7, 210, 500, 0);

			String text7B = "PROFESSIONELLE A L'ENSEIGNEMENT DE LA CONDUITE DES VEHICULES A MOTEURS (CAPEC-"
					+ speciality + ")";
			com.itextpdf.text.pdf.PdfContentByte canvas6B = writer.getDirectContent();
			Phrase phrase7B = new Phrase(text7B, headFont);
			ColumnText.showTextAligned(canvas6B, Element.ALIGN_LEFT, phrase7B, 205, 485, 0);

			String text8 = "LIST OF CANDIDATES DECLARED REJECTED IN THE COMPETITIVE EXAMINATION ";
			com.itextpdf.text.pdf.PdfContentByte canvas7 = writer.getDirectContent();
			Phrase phrase8 = new Phrase(text8, headFont3);
			ColumnText.showTextAligned(canvas7, Element.ALIGN_LEFT, phrase8, 250, 470, 0);

			String text8B = "FOR DRIVING SCHOOL INSTRUCTOR PROFESSIONAL CERTIFICATE (DSIPC-" + speciality + ")";
			com.itextpdf.text.pdf.PdfContentByte canvas7B = writer.getDirectContent();
			Phrase phrase8B = new Phrase(text8B, headFont3);
			ColumnText.showTextAligned(canvas7B, Element.ALIGN_LEFT, phrase8B, 247, 455, 0);

			String text10 = "SESSION DU (FROM) : " + startDateOfSession + " - AU (TO) " + sessionDateFormatted;
			com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
			Phrase phrase10 = new Phrase(text10, headFont3);
			ColumnText.showTextAligned(canvas10, Element.ALIGN_LEFT, phrase10, 270, 440, 0);
			String text9 = "CENTRE : " + eligibleCenter.getExamCenter().getName();
			com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
			Phrase phrase9 = new Phrase(text9, headFont3);
			ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase9, 500, 440, 0);

		} else if (listType.contains("candidates_failed")) {

			String text7 = "LISTE DES CANDIDATS DECLARES ECHOUES A L'EXAMEN NATIONAL DU CERTIFICAT D'APTITUDE ";
			com.itextpdf.text.pdf.PdfContentByte canvas6 = writer.getDirectContent();
			Phrase phrase7 = new Phrase(text7, headFont);
			ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase7, 210, 500, 0);

			String text7B = "PROFESSIONELLE A L'ENSEIGNEMENT DE LA CONDUITE DES VEHICULES A MOTEURS (CAPEC-"
					+ speciality + ")";
			com.itextpdf.text.pdf.PdfContentByte canvas6B = writer.getDirectContent();
			Phrase phrase7B = new Phrase(text7B, headFont);
			ColumnText.showTextAligned(canvas6B, Element.ALIGN_LEFT, phrase7B, 205, 485, 0);

			String text8 = "LIST OF CANDIDATES DECLARED UNSUCCESSFUL IN THE COMPETITIVE EXAMINATION ";
			com.itextpdf.text.pdf.PdfContentByte canvas7 = writer.getDirectContent();
			Phrase phrase8 = new Phrase(text8, headFont3);
			ColumnText.showTextAligned(canvas7, Element.ALIGN_LEFT, phrase8, 240, 470, 0);

			String text8B = "FOR DRIVING SCHOOL INSTRUCTOR PROFESSIONAL CERTIFICATE (DSIPC-" + speciality + ")";
			com.itextpdf.text.pdf.PdfContentByte canvas7B = writer.getDirectContent();
			Phrase phrase8B = new Phrase(text8B, headFont3);
			ColumnText.showTextAligned(canvas7B, Element.ALIGN_LEFT, phrase8B, 259, 455, 0);

			String text10 = "SESSION DU (FROM) : " + startDateOfSession + " - AU (TO) " + sessionDateFormatted;
			com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
			Phrase phrase10 = new Phrase(text10, headFont3);
			ColumnText.showTextAligned(canvas10, Element.ALIGN_LEFT, phrase10, 270, 440, 0);
			String text9 = "CENTRE : " + eligibleCenter.getExamCenter().getName();
			com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
			Phrase phrase9 = new Phrase(text9, headFont3);
			ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase9, 500, 440, 0);

			// String text11 = speciality;
			// com.itextpdf.text.pdf.PdfContentByte canvas11 =
			// writer.getDirectContent();
			// Phrase phrase11 = new Phrase(text11, headFont1);
			// ColumnText.showTextAligned(canvas11, Element.ALIGN_LEFT,
			// phrase11, 370, 435, 0);

		} else if (listType.contains("candidates_list_absent")) {

			String text7 = "LISTE DES CANDIDATS DECLARES ABSENTS  A L'EXAMEN NATIONAL DU CERTIFICAT D'APTITUDE ";
			com.itextpdf.text.pdf.PdfContentByte canvas6 = writer.getDirectContent();
			Phrase phrase7 = new Phrase(text7, headFont);
			ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase7, 210, 500, 0);

			String text7B = "PROFESSIONELLE A L'ENSEIGNEMENT DE LA CONDUITE DES VEHICULES A MOTEURS (CAPEC-"
					+ speciality + ")";
			com.itextpdf.text.pdf.PdfContentByte canvas6B = writer.getDirectContent();
			Phrase phrase7B = new Phrase(text7B, headFont);
			ColumnText.showTextAligned(canvas6B, Element.ALIGN_LEFT, phrase7B, 205, 485, 0);

			String text8 = "LIST OF CANDIDATES DECLARED ABSENT IN THE COMPETITIVE EXAMINATION ";
			com.itextpdf.text.pdf.PdfContentByte canvas7 = writer.getDirectContent();
			Phrase phrase8 = new Phrase(text8, headFont3);
			ColumnText.showTextAligned(canvas7, Element.ALIGN_LEFT, phrase8, 250, 470, 0);

			String text8B = "FOR DRIVING SCHOOL INSTRUCTOR PROFESSIONAL CERTIFICATE (DSIPC-" + speciality + ")";
			com.itextpdf.text.pdf.PdfContentByte canvas7B = writer.getDirectContent();
			Phrase phrase8B = new Phrase(text8B, headFont3);
			ColumnText.showTextAligned(canvas7B, Element.ALIGN_LEFT, phrase8B, 248, 455, 0);

			String text10 = "SESSION DU (FROM) : " + startDateOfSession + " - AU (TO) " + sessionDateFormatted;
			com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
			Phrase phrase10 = new Phrase(text10, headFont3);
			ColumnText.showTextAligned(canvas10, Element.ALIGN_LEFT, phrase10, 270, 440, 0);
			String text9 = "CENTRE : " + eligibleCenter.getExamCenter().getName();
			com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
			Phrase phrase9 = new Phrase(text9, headFont3);
			ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase9, 500, 440, 0);

		} else if (listType.contains("candidates_list_demissionnaire")) {

			String text7 = "LISTE DES CANDIDATS DECLARES DEMISSIONAIRES A L'EXAMEN NATIONAL DU CERTIFICAT D'APTITUDE ";
			com.itextpdf.text.pdf.PdfContentByte canvas6 = writer.getDirectContent();
			Phrase phrase7 = new Phrase(text7, headFont);
			ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase7, 210, 500, 0);

			String text7B = "PROFESSIONELLE A L'ENSEIGNEMENT DE LA CONDUITE DES VEHICULES A MOTEURS (CAPEC-"
					+ speciality + ")";
			com.itextpdf.text.pdf.PdfContentByte canvas6B = writer.getDirectContent();
			Phrase phrase7B = new Phrase(text7B, headFont);
			ColumnText.showTextAligned(canvas6B, Element.ALIGN_LEFT, phrase7B, 220, 485, 0);

			String text8 = "LIST OF CANDIDATES DECLARED RESIGNED IN THE COMPETITIVE EXAMINATION ";
			com.itextpdf.text.pdf.PdfContentByte canvas7 = writer.getDirectContent();
			Phrase phrase8 = new Phrase(text8, headFont3);
			ColumnText.showTextAligned(canvas7, Element.ALIGN_LEFT, phrase8, 250, 470, 0);

			String text8B = "FOR DRIVING SCHOOL INSTRUCTOR PROFESSIONAL CERTIFICATE (DSIPC-" + speciality + ")";
			com.itextpdf.text.pdf.PdfContentByte canvas7B = writer.getDirectContent();
			Phrase phrase8B = new Phrase(text8B, headFont3);
			ColumnText.showTextAligned(canvas7B, Element.ALIGN_LEFT, phrase8B, 247, 455, 0);

			String text10 = "SESSION DU (FROM) : " + startDateOfSession + " - AU (TO) " + sessionDateFormatted;
			com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
			Phrase phrase10 = new Phrase(text10, headFont3);
			ColumnText.showTextAligned(canvas10, Element.ALIGN_LEFT, phrase10, 270, 440, 0);
			String text9 = "CENTRE : " + eligibleCenter.getExamCenter().getName();
			com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
			Phrase phrase9 = new Phrase(text9, headFont3);
			ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase9, 500, 440, 0);

		} /*
			 * else if (listType.equals("print_pv_note")){ String text7 =
			 * "RESULTAT DEFINITIF A L'EXAMEN NATIONAL DU CAPEC A L ENSEIGNEMENT DE LA CONDUITE DES VEHICULES A MOTEURS PAR ORDRE DE MERITE: ( "
			 * + candidateSessions.size() + " Candidats)";
			 * com.itextpdf.text.pdf.PdfContentByte canvas6 =
			 * writer.getDirectContent(); Phrase phrase7 = new Phrase(text7,
			 * headFont); ColumnText.showTextAligned(canvas6,
			 * Element.ALIGN_LEFT, phrase7, 50, 500, 0);
			 *
			 * String text8 =
			 * "FINAL RESULTS - NATIONAL DSIPC EXAMINATION FOR DRIVING MOTOR VEHICLES BY ORDER OF MERIT: ( "
			 * + candidateSessions.size() + " Candidates)";
			 * com.itextpdf.text.pdf.PdfContentByte canvas7 =
			 * writer.getDirectContent(); Phrase phrase8 = new Phrase(text8,
			 * headFont3); ColumnText.showTextAligned(canvas7,
			 * Element.ALIGN_LEFT, phrase8, 200, 485, 0);
			 *
			 * String text9 = "CENTRE / CENTRE: " +
			 * eligibleCenter.getExamCenter().getName();
			 * com.itextpdf.text.pdf.PdfContentByte canvas9 =
			 * writer.getDirectContent(); Phrase phrase9 = new Phrase(text9,
			 * headFont1); ColumnText.showTextAligned(canvas9,
			 * Element.ALIGN_LEFT, phrase9, 340, 470, 0); String text10 =
			 * "SESSION DU/FROM: "+startDateOfSession+" A / TO "
			 * +sessionDateFormatted; com.itextpdf.text.pdf.PdfContentByte
			 * canvas10 = writer.getDirectContent(); Phrase phrase10 = new
			 * Phrase(text10, headFont3); ColumnText.showTextAligned(canvas10,
			 * Element.ALIGN_LEFT, phrase10, 370, 455, 0); String text11 =
			 * speciality; com.itextpdf.text.pdf.PdfContentByte canvas11 =
			 * writer.getDirectContent(); Phrase phrase11 = new Phrase(text11,
			 * headFont1); ColumnText.showTextAligned(canvas11,
			 * Element.ALIGN_LEFT, phrase11, 370, 440, 0);
			 *
			 *
			 *
			 * }
			 */

		// String text888 = "TOTAL : " + candidateSessions.size() + " Candidats
		// / Candidates ";
		// com.itextpdf.text.pdf.PdfContentByte canvas777 =
		// writer.getDirectContent();
		// Phrase phrase888 = new Phrase(text888, headFont3);
		// ColumnText.showTextAligned(canvas777, Element.ALIGN_LEFT, phrase888,
		// 200, 30, 0);

	}

	public void printListApplicant(ByteArrayOutputStream outStream, HttpHeaders headers, int eligibleCenterId,
			String typeList) throws DocumentException, IOException {
		EntranceEligibleCenter entranceEligibleCenter = entranceEligibleCenterService.findById(eligibleCenterId);
		List<Applicant> applicants = new ArrayList<Applicant>();
		Document document = null;

		document = new Document(PageSize.A4.rotate());

		String speciality = null;
		document.setMargins(35, 35, 30, 75);
		PdfWriter writer = PdfWriter.getInstance(document, outStream);
		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		writer.setPageEvent(event);
		document.open();
		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 14);
		Font headFont1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD.toUpperCase(), 9);
		Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD.toUpperCase(), 8);
		Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);
		Font headFont4 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 5);
		// l'entete du pdf
		headersPdf(document, writer, true);

		if (typeList.contains("print_pv")) {
			speciality = typeList.split("@")[1];
			Speciality applicantSpeciality = specialityService.findByAbbreviation(speciality);

			applicants = applicantService.findByEntranceEligibleCenterAndResultAndSpeciality(entranceEligibleCenter,
					"PASSED PRACTICALS", applicantSpeciality);
			generateApplicantPvListsHeaders(applicants, typeList.split("@")[0], speciality, entranceEligibleCenter,
					headFont1, headFont3, writer);

			String text30 = "";
			Phrase phrase30 = new Phrase(text30, headFont3);
			document.add(phrase30);

			document.add(Chunk.NEWLINE);

			PdfPTable table = new PdfPTable(7);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 3, 8, 12, 6, 8, 2, 5 });
			table.setSpacingBefore(110);
			table.setSpacingAfter(18);

			PdfPCell c1 = new PdfPCell(new Phrase("No PV", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("NOM/ SURNAME", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("PRENOM/ GIVENAME", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("DATE DE NAISSANCE/ DATE OF BIRTH", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("LIEU DE NAISSANCE/ PLACE OF BIRTH", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("GENRE/ GENDER", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);

			c1 = new PdfPCell(new Phrase("PERMIS/ LICENSE", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			table.setHeaderRows(1);

			for (Applicant applicant : applicants) {
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(applicant.getPvNumber()), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(applicant.getPerson().getSurName(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				if (applicant.getPerson().getGivenName() == null) {
					cell = new PdfPCell(new Phrase(" ", headFont2));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(new Phrase(applicant.getPerson().getGivenName(), headFont2));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}

				SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
				String stringDate = DateFor.format(applicant.getPerson().getDob());

				cell = new PdfPCell(new Phrase(stringDate, headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(applicant.getPerson().getPob().toUpperCase(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(applicant.getPerson().getGender(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(applicant.getPerson().getLicenseNum(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			}
			document.add(table);
		} else if (typeList.contains("applicant_list_training")) {
			speciality = typeList.split("@")[1];
			Speciality applicantSpeciality = specialityService.findByAbbreviation(speciality);

			applicants = applicantService.findByEntranceEligibleCenterAndResultAndSpecialityAndStudent(
					entranceEligibleCenter, applicantSpeciality);
			generateApplicantPvListsHeaders(applicants, typeList.split("@")[0], speciality, entranceEligibleCenter,
					headFont1, headFont3, writer);
			String text30 = "";
			Phrase phrase30 = new Phrase(text30, headFont3);
			document.add(phrase30);

			document.add(Chunk.NEWLINE);

			PdfPTable table = new PdfPTable(7);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 3, 8, 12, 11, 5, 9, 4 });
			table.setSpacingBefore(110);
			table.setSpacingAfter(18);

			PdfPCell c1 = new PdfPCell(new Phrase("No PV", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("CENTRE DE FORMATION/ TRAINING CENTER", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("NOM/ SURNAME", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("PRENOM/ GIVENAME", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("DATE DE NAISSANCE/ DATE OF BIRTH", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("LIEU DE NAISSANCE/ PLACE OF BIRTH", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("GENRE/ GENDER", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);

			table.setHeaderRows(1);

			for (Applicant applicant : applicants) {
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(applicant.getPvNumber()), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(5);
				table.addCell(cell);

				if (applicant.getStudent() != null) {
					cell = new PdfPCell(new Phrase(applicant.getStudent().getTrainingCenter().getName(), headFont2));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}
				cell = new PdfPCell(new Phrase(applicant.getPerson().getSurName(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				if (applicant.getPerson().getGivenName() == null) {
					cell = new PdfPCell(new Phrase(" ", headFont2));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(new Phrase(applicant.getPerson().getGivenName(), headFont2));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}

				SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
				String stringDate = DateFor.format(applicant.getPerson().getDob());

				cell = new PdfPCell(new Phrase(stringDate, headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(applicant.getPerson().getPob().toUpperCase(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(applicant.getPerson().getGender(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

			}
			document.add(table);
		} else {
			speciality = typeList.split("@")[1];
			Speciality applicantSpeciality = specialityService.findByAbbreviation(speciality);
			if (typeList.contains("applicant_list_presented")) {
				applicants = applicantService.findByEntranceEligibleCenterAndResultAndSpeciality(entranceEligibleCenter,
						"REGISTERED", applicantSpeciality);
			} else if (typeList.contains("applicant_pass_theorie")) {
				applicants = applicantService.findByEntranceEligibleCenterAndResultAndSpeciality(entranceEligibleCenter,
						"PASSED THEORY", applicantSpeciality);
			} else if (typeList.contains("applicant_close_theorie")) {
				applicants = applicantService.findByEntranceEligibleCenterAndResultAndSpeciality(entranceEligibleCenter,
						"FAILED THEORY", applicantSpeciality);
			} else if (typeList.contains("applicant_close_pratique")) {
				applicants = applicantService.findByEntranceEligibleCenterAndResultAndSpeciality(entranceEligibleCenter,
						"FAILED PRACTICALS", applicantSpeciality);
			}

			generateApplicantPvListsHeaders(applicants, typeList.split("@")[0], speciality, entranceEligibleCenter,
					headFont1, headFont3, writer);

			String text30 = "";
			Phrase phrase30 = new Phrase(text30, headFont3);
			document.add(phrase30);

			document.add(Chunk.NEWLINE);

			PdfPTable table = new PdfPTable(6);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 3, 10, 10, 10, 9, 4 });
			table.setSpacingBefore(110);
			table.setSpacingAfter(18);

			PdfPCell c1 = new PdfPCell(new Phrase("No", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("NOM/ SURNAME", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("PRENOM/ GIVENAME", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("DATE DE NAISSANCE/ DATE OF BIRTH", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("LIEU DE NAISSANCE/ PLACE OF BIRTH", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("GENRE/ GENDER", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			table.setHeaderRows(1);

			int i = 1;
			for (Applicant applicant : applicants) {
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(i), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(5);
				table.addCell(cell);

				/*
				 * if(applicant.getStudent() == null){ cell = new PdfPCell(new
				 * Phrase(" ", headFont2));
				 * cell.setVerticalAlignment(Element.ALIGN_CENTER);
				 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * cell.setPaddingRight(5); table.addCell(cell); }else { cell =
				 * new PdfPCell(new
				 * Phrase(applicant.getStudent().getTrainingCenter().getName(),
				 * headFont2)); cell.setVerticalAlignment(Element.ALIGN_CENTER);
				 * cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				 * cell.setPaddingRight(5); table.addCell(cell); }
				 */

				cell = new PdfPCell(new Phrase(applicant.getPerson().getSurName().toUpperCase(), headFont2));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				if (applicant.getPerson().getGivenName() == null) {
					cell = new PdfPCell(new Phrase(" ", headFont2));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(new Phrase(applicant.getPerson().getGivenName().toUpperCase(), headFont2));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}

				SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
				String stringDate = DateFor.format(applicant.getPerson().getDob());

				cell = new PdfPCell(new Phrase(stringDate, headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(applicant.getPerson().getPob().toUpperCase(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				if (applicant.getPerson().getGender() == null) {
					cell = new PdfPCell(new Phrase(" ", headFont2));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(new Phrase(applicant.getPerson().getGender().toUpperCase(), headFont2));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}

				i++;
			}
			document.add(table);
		}
		document.close();
		headers.add("Content-Disposition", "inline; filename=pdf-sample.pdf");

	}

	private void generateApplicantPvListsHeaders(List<Applicant> applicant, String listType, String speciality,
			EntranceEligibleCenter entranceEligibleCenter, Font headFont1, Font headFont3, PdfWriter writer) {

		if (speciality != null)
			speciality = "( Option: " + speciality + " ) ";
		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 9);

		SimpleDateFormat sessionDate = new SimpleDateFormat("dd/MM/yyyy");
		String sessionDateFormatted = sessionDate
				.format(entranceEligibleCenter.getEntranceExamSession().getSessionDate());

		if (listType.equals("print_pv")) {
			String text7 = "RESULTAT DEFINITIF AU CONCOURS DE FORMATION DES EXPERTS A LA SECURITE ROUTIERE: ( "
					+ applicant.size() + " Candidat(s))";
			com.itextpdf.text.pdf.PdfContentByte canvas6 = writer.getDirectContent();
			Phrase phrase7 = new Phrase(text7, headFont);
			ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase7, 150, 500, 0);

			String text8 = "FINAL RESULT OF THE ROAD SAFETY EXPERTS TRAINING EXAMINATION: ( " + applicant.size()
					+ " Candidate(s) )";
			com.itextpdf.text.pdf.PdfContentByte canvas7 = writer.getDirectContent();
			Phrase phrase8 = new Phrase(text8, headFont3);
			ColumnText.showTextAligned(canvas7, Element.ALIGN_LEFT, phrase8, 220, 485, 0);

			String text9 = "SOUS CENTRE / SUBCENTRE: " + entranceEligibleCenter.getEntranceExamCenter().getName();
			com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
			Phrase phrase9 = new Phrase(text9, headFont1);
			ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase9, 340, 470, 0);
			String text10 = "SESSION : " + sessionDateFormatted;
			com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
			Phrase phrase10 = new Phrase(text10, headFont3);
			ColumnText.showTextAligned(canvas10, Element.ALIGN_LEFT, phrase10, 370, 455, 0);
			String text11 = speciality;
			com.itextpdf.text.pdf.PdfContentByte canvas11 = writer.getDirectContent();
			Phrase phrase11 = new Phrase(text11, headFont1);
			ColumnText.showTextAligned(canvas11, Element.ALIGN_LEFT, phrase11, 370, 440, 0);

		} else if (listType.equals("applicant_list_training")) {

			String text7 = "LISTE DES CANDIDATS ENVOYES A UN CENTRE DE FORMATION DES EXPERTS A LA SECURITE ROUTIERE: ( "
					+ applicant.size() + " Candidat(s) )";
			com.itextpdf.text.pdf.PdfContentByte canvas6 = writer.getDirectContent();
			Phrase phrase7 = new Phrase(text7, headFont);
			ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase7, 130, 500, 0);

			String text8 = "LIST OF CANDIDATES SEND TO A TRAINING CENTRE FOR ROAD SAFETY EXPERTS : ( "
					+ applicant.size() + " Candidate(s) )";
			com.itextpdf.text.pdf.PdfContentByte canvas7 = writer.getDirectContent();
			Phrase phrase8 = new Phrase(text8, headFont3);
			ColumnText.showTextAligned(canvas7, Element.ALIGN_LEFT, phrase8, 190, 485, 0);

			String text9 = "SOUS CENTRE / SUBCENTRE: " + entranceEligibleCenter.getEntranceExamCenter().getName();
			com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
			Phrase phrase9 = new Phrase(text9, headFont1);
			ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase9, 340, 470, 0);
			String text10 = "SESSION : " + sessionDateFormatted;
			com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
			Phrase phrase10 = new Phrase(text10, headFont3);
			ColumnText.showTextAligned(canvas10, Element.ALIGN_LEFT, phrase10, 370, 455, 0);

			String text11 = speciality;
			com.itextpdf.text.pdf.PdfContentByte canvas11 = writer.getDirectContent();
			Phrase phrase11 = new Phrase(text11, headFont1);
			ColumnText.showTextAligned(canvas11, Element.ALIGN_LEFT, phrase11, 370, 440, 0);

		} else if (listType.contains("applicant_list_presented")) {

			String text7 = "LISTE DES CANDIDATS ELIGIBLES AU CONCOURS DE FORMATION DES EXPERTS A LA SECURITE ROUTIERE: ( "
					+ applicant.size() + " Candidat(s) )";
			com.itextpdf.text.pdf.PdfContentByte canvas6 = writer.getDirectContent();
			Phrase phrase7 = new Phrase(text7, headFont);
			ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase7, 130, 500, 0);

			String text8 = "LIST OF ELIGIBLE CANDIDATES FOR THE ROAD SAFETY EXPERTS TRAINING EXAMINATION: ("
					+ applicant.size() + " Candidate(s) )";
			com.itextpdf.text.pdf.PdfContentByte canvas7 = writer.getDirectContent();
			Phrase phrase8 = new Phrase(text8, headFont3);
			ColumnText.showTextAligned(canvas7, Element.ALIGN_LEFT, phrase8, 170, 485, 0);

			String text9 = "SOUS CENTRE / SUBCENTRE: " + entranceEligibleCenter.getEntranceExamCenter().getName();
			com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
			Phrase phrase9 = new Phrase(text9, headFont1);
			ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase9, 340, 470, 0);
			String text10 = "SESSION : " + sessionDateFormatted;
			com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
			Phrase phrase10 = new Phrase(text10, headFont3);
			ColumnText.showTextAligned(canvas10, Element.ALIGN_LEFT, phrase10, 370, 455, 0);

			String text11 = speciality;
			com.itextpdf.text.pdf.PdfContentByte canvas11 = writer.getDirectContent();
			Phrase phrase11 = new Phrase(text11, headFont1);
			ColumnText.showTextAligned(canvas11, Element.ALIGN_LEFT, phrase11, 370, 440, 0);

		} else if (listType.contains("applicant_pass_theorie")) {

			String text7 = "LISTE DES CANDIDATS AYANT REUSSI LA THEORIE AU CONCOURS DE FORMATION DES EXPERTS A LA SECURITE ROUTIERE: ( "
					+ applicant.size() + " Candidat(s) )";
			com.itextpdf.text.pdf.PdfContentByte canvas6 = writer.getDirectContent();
			Phrase phrase7 = new Phrase(text7, headFont);
			ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase7, 110, 500, 0);

			String text8 = "LIST OF SUCCESSFUL CANDIDATES IN THE ROAD SAFETY EXPERTS TRAINING EXAMINATION: ("
					+ applicant.size() + " Candidate(s) )";
			com.itextpdf.text.pdf.PdfContentByte canvas7 = writer.getDirectContent();
			Phrase phrase8 = new Phrase(text8, headFont3);
			ColumnText.showTextAligned(canvas7, Element.ALIGN_LEFT, phrase8, 170, 485, 0);

			String text9 = "SOUS CENTRE / SUBCENTRE: " + entranceEligibleCenter.getEntranceExamCenter().getName();
			com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
			Phrase phrase9 = new Phrase(text9, headFont1);
			ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase9, 340, 470, 0);
			String text10 = "SESSION : " + sessionDateFormatted;
			com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
			Phrase phrase10 = new Phrase(text10, headFont3);
			ColumnText.showTextAligned(canvas10, Element.ALIGN_LEFT, phrase10, 370, 455, 0);

			String text11 = speciality;
			com.itextpdf.text.pdf.PdfContentByte canvas11 = writer.getDirectContent();
			Phrase phrase11 = new Phrase(text11, headFont1);
			ColumnText.showTextAligned(canvas11, Element.ALIGN_LEFT, phrase11, 370, 440, 0);

		} else if (listType.contains("applicant_close_theorie")) {

			String text7 = "LISTE DES CANDIDATS AYANT ECHOUE LA THEORIE AU CONCOURS DE FORMATION DES EXPERTS A LA SECURITE ROUTIERE: ( "
					+ applicant.size() + " Candidat(s) )";
			com.itextpdf.text.pdf.PdfContentByte canvas6 = writer.getDirectContent();
			Phrase phrase7 = new Phrase(text7, headFont);
			ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase7, 110, 500, 0);

			String text8 = "LIST OF CANDIDATES WHO FAILED THEORY IN THE ROAD SAFETY EXPERTS TRAINING EXAMINATION: ("
					+ applicant.size() + " Candidate(s) )";
			com.itextpdf.text.pdf.PdfContentByte canvas7 = writer.getDirectContent();
			Phrase phrase8 = new Phrase(text8, headFont3);
			ColumnText.showTextAligned(canvas7, Element.ALIGN_LEFT, phrase8, 160, 485, 0);

			String text9 = "SOUS CENTRE / SUBCENTRE: " + entranceEligibleCenter.getEntranceExamCenter().getName();
			com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
			Phrase phrase9 = new Phrase(text9, headFont1);
			ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase9, 340, 470, 0);
			String text10 = "SESSION : " + sessionDateFormatted;
			com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
			Phrase phrase10 = new Phrase(text10, headFont3);
			ColumnText.showTextAligned(canvas10, Element.ALIGN_LEFT, phrase10, 370, 455, 0);

			String text11 = speciality;
			com.itextpdf.text.pdf.PdfContentByte canvas11 = writer.getDirectContent();
			Phrase phrase11 = new Phrase(text11, headFont1);
			ColumnText.showTextAligned(canvas11, Element.ALIGN_LEFT, phrase11, 370, 440, 0);

		} else if (listType.contains("applicant_close_pratique")) {

			String text7 = "LISTE DES CANDIDATS AYANT ECHOUE LA PRATIQUE AU CONCOURS DE FORMATION DES EXPERTS A LA SECURITE ROUTIERE: ( "
					+ applicant.size() + " Candidat(s) )";
			com.itextpdf.text.pdf.PdfContentByte canvas6 = writer.getDirectContent();
			Phrase phrase7 = new Phrase(text7, headFont);
			ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase7, 110, 500, 0);

			String text8 = "LIST OF CANDIDATES WHO FAILED PRATIQUE IN THE ROAD SAFETY EXPERTS TRAINING COMPETITION: ("
					+ applicant.size() + " Candidate(s) )";
			com.itextpdf.text.pdf.PdfContentByte canvas7 = writer.getDirectContent();
			Phrase phrase8 = new Phrase(text8, headFont3);
			ColumnText.showTextAligned(canvas7, Element.ALIGN_LEFT, phrase8, 160, 485, 0);

			String text9 = "SOUS CENTRE / SUBCENTRE: " + entranceEligibleCenter.getEntranceExamCenter().getName();
			com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
			Phrase phrase9 = new Phrase(text9, headFont1);
			ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase9, 340, 470, 0);
			String text10 = "SESSION : " + sessionDateFormatted;
			com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
			Phrase phrase10 = new Phrase(text10, headFont3);
			ColumnText.showTextAligned(canvas10, Element.ALIGN_LEFT, phrase10, 370, 455, 0);

			String text11 = speciality;
			com.itextpdf.text.pdf.PdfContentByte canvas11 = writer.getDirectContent();
			Phrase phrase11 = new Phrase(text11, headFont1);
			ColumnText.showTextAligned(canvas11, Element.ALIGN_LEFT, phrase11, 370, 440, 0);

		}

	}

	@Override
	public void printTranscript(ByteArrayOutputStream outStream, HttpHeaders headers, int studentSessionId,
			int eligibleCenterId, HttpServletRequest httpServletRequest) throws DocumentException, IOException {

		if (studentSessionId == 0) {
			// cas d impression de tout le PV
			if (eligibleCenterId != 0) {

				printTranscriptContent(outStream, headers, studentSessionId, eligibleCenterId, "NONE", false);
				// Tracking Transcript
				if (httpServletRequest != null) {
					User user = userService.getLogedUser(httpServletRequest);
					tracker.track(studentSessionId, "TRANSCRIPT WAS GENERATED AND PRINTED BY USER: "
							+ user.getFirstName() + " " + user.getLastName(), httpServletRequest);
					logger_.log(Constants.NORMAL_LOG_DIR, "Print Transcript: " + user);
				}

			}
		} else {
			// cas d impression du transcript d un seul candidate
			StudentSession studentSession = studentSessionService.findById(studentSessionId);
			Speciality studentSessionSpeciality = studentSession.getSpeciality();
			if (studentSessionSpeciality == null)
				studentSessionSpeciality = studentSession.getStudent().getSpeciality();

			if (studentSessionSpeciality.getAbbreviation().equals("MAE")) {

				printTranscriptContent(outStream, headers, studentSessionId, eligibleCenterId, "MAE", false);
				// Tracking Transcript
				if (httpServletRequest != null) {
					User user = userService.getLogedUser(httpServletRequest);
					tracker.track(studentSessionId, "TRANSCRIPT WAS GENERATED AND PRINTED BY USER: "
							+ user.getFirstName() + " " + user.getLastName(), httpServletRequest);
					logger_.log(Constants.NORMAL_LOG_DIR, "Print Transcript: " + user);
				}

			} else {
				if (studentSessionSpeciality.getAbbreviation().equals("IPCSR")) {
					printTranscriptContent(outStream, headers, studentSessionId, eligibleCenterId, "IPCSR", false);
				}
				if (studentSessionSpeciality.getAbbreviation().equals("DPCSR")) {
					printTranscriptContent(outStream, headers, studentSessionId, eligibleCenterId, "DPCSR", false);
				}
				// Tracking Transcript
				if (httpServletRequest != null) {
					User user = userService.getLogedUser(httpServletRequest);
					tracker.track(studentSessionId, "TRANSCRIPT WAS GENERATED AND PRINTED BY USER: "
							+ user.getFirstName() + " " + user.getLastName(), httpServletRequest);
					logger_.log(Constants.NORMAL_LOG_DIR, "Print Transcript: " + user);
				}

			}
		}

	}

	private void printTranscriptContent(ByteArrayOutputStream outStream, HttpHeaders headers, int studentSessionId,
			int eligiblecenterId, String specialityAbbreviation, boolean isPreview)
			throws DocumentException, IOException {
		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 14);
		Font headFont1 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		// old Font headFont2 =
		// FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 8);
		Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 7);
		Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 10);
		Font headFont4 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 5);
		Font headFont5 = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 10);
		// Font headFont6 =
		// FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 9);
		Font headFont6 = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 6);
		// isPreview=true;
		if (specialityAbbreviation.equals("NONE")) {
			// cas d impression de tous les transcripts d 'un PV
			EligibleCenter eligibleCenter = eligibleCenterService.findById(eligiblecenterId);
			List<StudentSession> allCenterStudents = studentSessionService.findByEligibleCenter(eligibleCenter);

			Document document = null;

			document = new Document(PageSize.A4);

			if (isPreview) {
				document.setMargins(44, 42, 25, 24);
			} else {
				// code to print on paper
				// old value document.setMargins(25, 25, 25, 24);
				document.setMargins(44, 42, 25, 24);
			}

			PdfWriter writer = PdfWriter.getInstance(document, outStream);
			// remove footer HeaderFooterPageEventTranscript event = new
			// HeaderFooterPageEventTranscript();
			// remove footer writer.setPageEvent(event);
			document.open();

			if (isPreview) {
				Image background = Image.getInstance(transcriptBackgroundFolder + "transcriptBackground.jpg");
				background.setAbsolutePosition(0, 0);
				background.scaleAbsolute(600, 850);
				document.add(background);
			}
			int countStudents = 0;
			for (StudentSession studentSession : allCenterStudents) {

				generateReleveTittles(studentSession, headFont1, headFont3, writer);

				printTranscriptTableOneStudent(studentSession, document, headFont5, headFont6, headFont3, headFont2);
				if (countStudents < allCenterStudents.size() - 1) {
					document.newPage();
					if (isPreview) {
						Image background = Image.getInstance(transcriptBackgroundFolder + "transcriptBackground.jpg");
						background.setAbsolutePosition(0, 0);
						background.scaleAbsolute(600, 850);
						document.add(background);
					}

				}
				countStudents++;
			}
			document.close();
			headers.add("Content-Disposition", "inline; filename=transcript.pdf");

		} else {

			if (specialityAbbreviation.equals("MAE")) {
				Document document = null;

				document = new Document(PageSize.A4);

				PdfWriter writer = PdfWriter.getInstance(document, outStream);
				// remove footer HeaderFooterPageEventTranscript event = new
				// HeaderFooterPageEventTranscript();
				// remove footer writer.setPageEvent(event);
				document.open();

				if (isPreview) {
					Image background = Image.getInstance(transcriptBackgroundFolder + "transcriptBackground_MAE.jpg");
					background.setAbsolutePosition(0, 0);
					background.scaleAbsolute(600, 850);
					document.add(background);
				}

				StudentSession studentSession = studentSessionService.findById(studentSessionId);
				generateReleveTittles(studentSession, headFont1, headFont3, writer);

				printTranscriptTableOneStudent(studentSession, document, headFont5, headFont6, headFont3, headFont2);

				document.close();
				headers.add("Content-Disposition", "inline; filename=transcript.pdf");

			} else if (specialityAbbreviation.equals("IPCSR")) {
				Document document = null;

				document = new Document(PageSize.A4);
				if (isPreview) {
					// document.setMargins(44, 42, 25, 24);
				} else {
					// code to print on paper
					// old value document.setMargins(25, 25, 25, 24);
					// document.setMargins(44, 42, 25, 24);
				}

				PdfWriter writer = PdfWriter.getInstance(document, outStream);
				// remove footer HeaderFooterPageEventTranscript event = new
				// HeaderFooterPageEventTranscript();
				// remove footer writer.setPageEvent(event);
				document.open();

				if (isPreview) {
					Image background = Image.getInstance(transcriptBackgroundFolder + "transcriptBackground_IPCSR.jpg");
					background.setAbsolutePosition(0, 0);
					background.scaleAbsolute(600, 850);
					document.add(background);
				}

				StudentSession studentSession = studentSessionService.findById(studentSessionId);
				generateReleveTittles(studentSession, headFont1, headFont3, writer);
				printTranscriptTableOneStudent(studentSession, document, headFont5, headFont6, headFont3, headFont2);
				document.close();
				headers.add("Content-Disposition", "inline; filename=transcript.pdf");

			} else if (specialityAbbreviation.equals("DPCSR")) {
				Document document = null;

				document = new Document(PageSize.A4);

				PdfWriter writer = PdfWriter.getInstance(document, outStream);
				// remove footer HeaderFooterPageEventTranscript event = new
				// HeaderFooterPageEventTranscript();
				// remove footer writer.setPageEvent(event);
				document.open();

				if (isPreview) {
					Image background = Image.getInstance(transcriptBackgroundFolder + "transcriptBackground_DPCSR.jpg");
					background.setAbsolutePosition(0, 0);
					background.scaleAbsolute(600, 850);
					document.add(background);
				}

				StudentSession studentSession = studentSessionService.findById(studentSessionId);
				generateReleveTittles(studentSession, headFont1, headFont3, writer);
				printTranscriptTableOneStudent(studentSession, document, headFont5, headFont6, headFont3, headFont2);
				document.close();
				headers.add("Content-Disposition", "inline; filename=transcript.pdf");

			}
		}

	}

	private void generateReleveTittles(StudentSession studentSession, Font headFont1, Font headFont3,
			PdfWriter writer) {
		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 10);
		Font headFontName = FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 10);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		String text48 = "Par :";
		com.itextpdf.text.pdf.PdfContentByte canvas47 = writer.getDirectContent();
		Phrase phrase48 = new Phrase(text48, headFont);
		ColumnText.showTextAligned(canvas47, Element.ALIGN_LEFT, phrase48, 36, 568, 0);

		com.itextpdf.text.pdf.PdfContentByte linePar = writer.getDirectContent();
		linePar.moveTo(59, 568);
		linePar.lineTo(550, 568);
		linePar.setLineWidth(0.5);
		linePar.closePathStroke();

		String text488 = studentSession.getStudent().getPerson().getSurName() + " "
				+ studentSession.getStudent().getPerson().getGivenName();
		com.itextpdf.text.pdf.PdfContentByte canvas477 = writer.getDirectContent();
		Phrase phrase488 = new Phrase(text488.toUpperCase(), headFontName);
		ColumnText.showTextAligned(canvas477, Element.ALIGN_LEFT, phrase488, 74, 570, 0);

		String text49 = "By                                            ";
		com.itextpdf.text.pdf.PdfContentByte canvas48 = writer.getDirectContent();
		Phrase phrase49 = new Phrase(text49, headFont);
		ColumnText.showTextAligned(canvas48, Element.ALIGN_LEFT, phrase49, 36, 558, 0);

		String text50 = "Né(e) le : ";
		com.itextpdf.text.pdf.PdfContentByte canvas49 = writer.getDirectContent();
		Phrase phrase50 = new Phrase(text50, headFont);
		ColumnText.showTextAligned(canvas49, Element.ALIGN_LEFT, phrase50, 36, 548, 0);

		com.itextpdf.text.pdf.PdfContentByte lineNeLe = writer.getDirectContent();
		lineNeLe.moveTo(75, 548);
		lineNeLe.lineTo(240, 548);
		lineNeLe.setLineWidth(0.5);
		lineNeLe.closePathStroke();

		String text500 = dateFormat.format(studentSession.getStudent().getPerson().getDob());
		com.itextpdf.text.pdf.PdfContentByte canvas499 = writer.getDirectContent();
		Phrase phrase500 = new Phrase(text500, headFont3);
		ColumnText.showTextAligned(canvas499, Element.ALIGN_LEFT, phrase500, 80, 550, 0);

		String text51 = "Born On                  ";
		com.itextpdf.text.pdf.PdfContentByte canvas50 = writer.getDirectContent();
		Phrase phrase51 = new Phrase(text51, headFont);
		ColumnText.showTextAligned(canvas50, Element.ALIGN_LEFT, phrase51, 36, 538, 0);

		com.itextpdf.text.pdf.PdfContentByte lineSession = writer.getDirectContent();
		lineSession.moveTo(75, 528);
		lineSession.lineTo(240, 528);
		lineSession.setLineWidth(0.5);
		lineSession.closePathStroke();

		String text54 = "Session : ";
		com.itextpdf.text.pdf.PdfContentByte canvas53 = writer.getDirectContent();
		Phrase phrase54 = new Phrase(text54, headFont);
		ColumnText.showTextAligned(canvas53, Element.ALIGN_LEFT, phrase54, 36, 528, 0);

		String text54Sessionvalue = dateFormat
				.format(studentSession.getEligibleCenter().getExamSession().getSessionDate());
		com.itextpdf.text.pdf.PdfContentByte canvas53SessionValue = writer.getDirectContent();
		Phrase phrase54SessionValue = new Phrase(text54Sessionvalue, headFont3);
		ColumnText.showTextAligned(canvas53SessionValue, Element.ALIGN_LEFT, phrase54SessionValue, 80, 530, 0);

		String text55 = "Session ";
		com.itextpdf.text.pdf.PdfContentByte canvas54 = writer.getDirectContent();
		Phrase phrase55 = new Phrase(text55, headFont);
		ColumnText.showTextAligned(canvas54, Element.ALIGN_LEFT, phrase55, 36, 518, 0);

		com.itextpdf.text.pdf.PdfContentByte lineMatricule = writer.getDirectContent();
		lineMatricule.moveTo(484, 528);
		lineMatricule.lineTo(550, 528);
		lineMatricule.setLineWidth(0.5);
		lineMatricule.closePathStroke();

		String text56 = "Matricule :";
		com.itextpdf.text.pdf.PdfContentByte canvas55 = writer.getDirectContent();
		Phrase phrase56 = new Phrase(text56, headFont);
		ColumnText.showTextAligned(canvas55, Element.ALIGN_LEFT, phrase56, 435, 528, 0);

		String text566 = studentSession.getStudent().getPerson().getMatricule();
		com.itextpdf.text.pdf.PdfContentByte canvas555 = writer.getDirectContent();
		Phrase phrase566 = new Phrase(text566, headFont3);
		ColumnText.showTextAligned(canvas555, Element.ALIGN_LEFT, phrase566, 490, 530, 0);

		String text57 = "Registration Number                  ";
		com.itextpdf.text.pdf.PdfContentByte canvas56 = writer.getDirectContent();
		Phrase phrase57 = new Phrase(text57, headFont);
		ColumnText.showTextAligned(canvas56, Element.ALIGN_LEFT, phrase57, 435, 518, 0);

		com.itextpdf.text.pdf.PdfContentByte lineCentre = writer.getDirectContent();
		lineCentre.moveTo(292, 528);
		lineCentre.lineTo(430, 528);
		lineCentre.setLineWidth(0.5);
		lineCentre.closePathStroke();

		String text58 = "Centre :";
		com.itextpdf.text.pdf.PdfContentByte canvas57 = writer.getDirectContent();
		Phrase phrase58 = new Phrase(text58, headFont);
		ColumnText.showTextAligned(canvas57, Element.ALIGN_LEFT, phrase58, 255, 528, 0);

		String text588 = studentSession.getEligibleCenter().getExamCenter().getName().toUpperCase();
		com.itextpdf.text.pdf.PdfContentByte canvas577 = writer.getDirectContent();
		Phrase phrase588 = new Phrase(text588, headFont3);
		ColumnText.showTextAligned(canvas577, Element.ALIGN_LEFT, phrase588, 296, 530, 0);

		String text59 = "Centre                  ";
		com.itextpdf.text.pdf.PdfContentByte canvas58 = writer.getDirectContent();
		Phrase phrase59 = new Phrase(text59, headFont);
		ColumnText.showTextAligned(canvas58, Element.ALIGN_LEFT, phrase59, 255, 518, 0);

		com.itextpdf.text.pdf.PdfContentByte lineA = writer.getDirectContent();
		lineA.moveTo(270, 548);
		lineA.lineTo(550, 548);
		lineA.setLineWidth(0.5);
		lineA.closePathStroke();

		String text60 = "A :";
		com.itextpdf.text.pdf.PdfContentByte canvas60 = writer.getDirectContent();
		Phrase phrase60 = new Phrase(text60, headFont);
		ColumnText.showTextAligned(canvas60, Element.ALIGN_LEFT, phrase60, 255, 548, 0);

		String text700 = studentSession.getStudent().getPerson().getPob().toUpperCase();
		com.itextpdf.text.pdf.PdfContentByte canvas700 = writer.getDirectContent();
		Phrase phrase700 = new Phrase(text700, headFont3);
		ColumnText.showTextAligned(canvas700, Element.ALIGN_LEFT, phrase700, 274, 550, 0);

		String text701 = "At                  ";
		com.itextpdf.text.pdf.PdfContentByte canvas701 = writer.getDirectContent();
		Phrase phrase701 = new Phrase(text701, headFont);
		ColumnText.showTextAligned(canvas58, Element.ALIGN_LEFT, phrase701, 255, 538, 0);

	}

	private void printTranscriptTableOneStudent(StudentSession studentSession, Document document, Font headFont5,
			Font headFont6, Font headFont3, Font headFont2) throws DocumentException, IOException {

		// start printing table
		String text30 = "";
		Phrase phrase30 = new Phrase(text30, headFont3);
		document.add(phrase30);

		document.add(Chunk.NEWLINE);

		/*
		 * Old version end
		 *
		 * PdfPTable table = new PdfPTable(6); table.setWidthPercentage(100);
		 * table.setWidths(new int[]{8, 24, 4, 4, 4, 5});
		 * //table.setSpacingBefore(340); table.setSpacingBefore(210);
		 * table.setSpacingAfter(17);
		 *
		 * Old version end
		 */

		Speciality studentSpecialite = studentSession.getSpeciality();
		if (studentSpecialite == null)
			studentSpecialite = studentSession.getStudent().getSpeciality();

		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100);
		// table.setWidths(new int[]{8, 26, 4, 4, 4, 5});
		// new -02-06-2021 table.setWidths(new int[] { 12, 34, 4, 4, 4, 5 });
		if (studentSpecialite.getAbbreviation().equals("MAE"))
			table.setWidths(new int[] { 12, 34, 8, 8, 4, 8 });
		else
			table.setWidths(new int[] { 12, 39, 8, 8, 4, 8 });
		// table.setSpacingBefore(280);
		// 28-05-2021 table.setSpacingBefore(230);
		if (studentSpecialite.getAbbreviation().equals("MAE"))
			table.setSpacingBefore(275);
		else
			table.setSpacingBefore(267);
		// 28-05-2021 table.setSpacingAfter(5);
		table.setSpacingAfter(2);

		Font headFont7 = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 7);

		PdfPCell c1 = new PdfPCell(new Phrase("", headFont5));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBorderWidthLeft(1);
		c1.setBorderWidthTop(1);
		c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
		c1.setPadding(3);
		table.addCell(c1);

		PdfPCell c2 = new PdfPCell(new Phrase("INTITULES\nCREDITS", headFont7));
		c2.setHorizontalAlignment(Element.ALIGN_CENTER);
		c2.setBorderWidthTop(1);
		c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
		c2.setPadding(3);
		table.addCell(c2);

		PdfPCell c3 = new PdfPCell(new Phrase("NOTE ELIM.\nELIM. MARKS", headFont7));
		c3.setHorizontalAlignment(Element.ALIGN_CENTER);
		c3.setBorderWidthTop(1);
		c3.setBackgroundColor(BaseColor.LIGHT_GRAY);
		c3.setPadding(3);
		table.addCell(c3);

		PdfPCell c4 = new PdfPCell(new Phrase("NOTES /20\nMARKS /20 ", headFont7));
		c4.setHorizontalAlignment(Element.ALIGN_CENTER);
		c4.setBorderWidthTop(1);
		c4.setBackgroundColor(BaseColor.LIGHT_GRAY);
		c4.setPadding(3);
		table.addCell(c4);

		PdfPCell c5 = new PdfPCell(new Phrase("COEF", headFont7));
		c5.setHorizontalAlignment(Element.ALIGN_CENTER);
		c5.setBorderWidthTop(1);
		c5.setBackgroundColor(BaseColor.LIGHT_GRAY);
		c5.setPadding(3);
		table.addCell(c5);

		PdfPCell c6 = new PdfPCell(new Phrase("NOTE * COEF\nMARK * COEF", headFont7));
		c6.setHorizontalAlignment(Element.ALIGN_CENTER);
		c6.setBorderWidthTop(1);
		c6.setBorderWidthRight(1);
		c6.setBackgroundColor(BaseColor.LIGHT_GRAY);
		c6.setPadding(3);
		table.addCell(c6);
		table.setHeaderRows(1);

		PdfPCell c7 = new PdfPCell(new Phrase("UNITES CERTIFICATIVES / SUBJECTS", headFont6));
		c7.setHorizontalAlignment(Element.ALIGN_CENTER);
		c7.setBorderWidthLeft(1);
		c7.setBorderWidthRight(1);
		c7.setBackgroundColor(BaseColor.LIGHT_GRAY);
		c7.setColspan(6);
		table.addCell(c7);
		List<Module> studentSessionSpecialityModules = new ArrayList<Module>();

		studentSessionSpecialityModules = moduleService.findBySpecialityOrderByModuleClassification(studentSpecialite);
		List<Module> modulesParcourus = new TreeList<Module>();
		Map<Integer, Float> modulesParcourusAndTheirTotal = new TreeMap<Integer, Float>();
		float totalModule1 = 0;
		float totalModule2 = 0;
		float totalOfAllModule = 0;
		float totalModule = 0;
		int numModules = studentSessionSpecialityModules.size();
		for (Module studentSessionSpecialityModule : studentSessionSpecialityModules) {
			// we add the module completeName
			modulesParcourus.add(studentSessionSpecialityModule);
			List<Course> listeOfCoursesOfModule = new ArrayList<Course>();
			listeOfCoursesOfModule = courseService.findByModuleOrderByCompleteName(studentSessionSpecialityModule);

			// gestion cours facultatif
			Course coursFacultaif = courseService.findByCompleteNameAndModule("Epreuve facultative / Optional paper",
					studentSessionSpecialityModule);
			if (listeOfCoursesOfModule.contains(coursFacultaif))
				listeOfCoursesOfModule.remove(coursFacultaif);

			PdfPCell cellModule = null;
			String classification;
			if (studentSessionSpecialityModule.getModuleClassification() == 1) {
				classification = "I";
			} else if (studentSessionSpecialityModule.getModuleClassification() == 2) {
				classification = "II";
			} else if (studentSessionSpecialityModule.getModuleClassification() == 3) {
				classification = "III";
			} else {
				classification = String.valueOf(studentSessionSpecialityModule.getModuleClassification());
			}
			if (studentSessionSpecialityModule.getStatus() == 1) {
				if (studentSessionSpecialityModule.getModuleClassification() == 2)
					cellModule = new PdfPCell(new Phrase(
							"MODULE " + classification + "\n\n\n\n\n\n\n\n\n\n"
									+ studentSessionSpecialityModule.getCompleteName().substring(0, 1).toUpperCase()
									+ studentSessionSpecialityModule.getCompleteName().substring(1).toLowerCase(),
							headFont6));
				else
					cellModule = new PdfPCell(new Phrase(
							"MODULE " + classification + "\n"
									+ studentSessionSpecialityModule.getCompleteName().substring(0, 1).toUpperCase()
									+ studentSessionSpecialityModule.getCompleteName().substring(1).toLowerCase(),
							headFont6));
			} else
				cellModule = new PdfPCell(new Phrase("MODULE " + classification, headFont6));

			cellModule.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellModule.setBorderWidthLeft(1);
			// c8.setBorderWidthRight(1);

			// ajout du cours epreuve facultative au module 3
			if (studentSessionSpecialityModule.getModuleClassification() == 3)
				listeOfCoursesOfModule.add(coursFacultaif);

			cellModule.setRowspan(listeOfCoursesOfModule.size());
			table.addCell(cellModule);
			int counterCourses = 0;
			float totalNoteOver20 = 0;
			int totalCoeff = 0;
			float totalNoteOver20MultiplyByCoeff = 0;

			for (Course course : listeOfCoursesOfModule) {

				float studentMark = 0;
				if (transcriptService.findByStudentSessionAndCourse(studentSession, course) != null)
					studentMark = transcriptService.findByStudentSessionAndCourse(studentSession, course)
							.getCourseNote();

				PdfPCell cellCourseName = new PdfPCell(new Phrase(course.getCompleteName().trim(), headFont2));
				cellCourseName.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cellCourseName);

				PdfPCell cellCourseNoteEliminatoire = new PdfPCell(new Phrase(String.valueOf(" "), headFont6));
				cellCourseNoteEliminatoire.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cellCourseNoteEliminatoire);

				PdfPCell cellStudentSessionMark = new PdfPCell(
						new Phrase(String.valueOf(Math.round(studentMark * 100.0) / 100.0), headFont2));
				cellStudentSessionMark.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cellStudentSessionMark);

				if (!course.getCompleteName().trim().equals("Epreuve facultative / Optional paper")) {
					PdfPCell cellCourseCoeff = new PdfPCell(
							new Phrase(String.valueOf(course.getCourseCoefficient()), headFont6));
					cellCourseCoeff.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellCourseCoeff.setBackgroundColor(BaseColor.LIGHT_GRAY);
					table.addCell(cellCourseCoeff);
				} else {
					PdfPCell cellCourseCoeff = new PdfPCell(new Phrase(" ", headFont6));
					cellCourseCoeff.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellCourseCoeff.setBackgroundColor(BaseColor.LIGHT_GRAY);
					table.addCell(cellCourseCoeff);

				}

				if (!course.getCompleteName().trim().equals("Epreuve facultative / Optional paper")) {
					PdfPCell cellCourseNoteMultiplyByCoeff = new PdfPCell(new Phrase(
							String.valueOf(Math.round((course.getCourseCoefficient() * studentMark) * 100.0) / 100.0),
							headFont6));
					cellCourseNoteMultiplyByCoeff.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellCourseNoteMultiplyByCoeff.setBorderWidthRight(1);
					table.addCell(cellCourseNoteMultiplyByCoeff);
				} else {
					PdfPCell cellCourseNoteMultiplyByCoeff;
					if (transcriptService.findByStudentSessionAndCourse(studentSession, coursFacultaif) != null) {
						cellCourseNoteMultiplyByCoeff = new PdfPCell(new Phrase(
								String.valueOf(transcriptService
										.findByStudentSessionAndCourse(studentSession, coursFacultaif).getCourseNote()),
								headFont6));
					} else {
						cellCourseNoteMultiplyByCoeff = new PdfPCell(new Phrase(String.valueOf(0f), headFont6));

					}
					cellCourseNoteMultiplyByCoeff.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellCourseNoteMultiplyByCoeff.setBorderWidthRight(1);
					table.addCell(cellCourseNoteMultiplyByCoeff);
				}

				if (counterCourses < listeOfCoursesOfModule.size()) {
					totalNoteOver20 += studentMark;
					if (!course.getCompleteName().trim().equals("Epreuve facultative / Optional paper")) // on
																											// exclut
																											// l
																											// epreuve
																											// facultative
																											// du
																											// calcul
																											// du
																											// total
						totalCoeff += course.getCourseCoefficient();

					totalNoteOver20MultiplyByCoeff += course.getCourseCoefficient() * studentMark;
					if (studentSessionSpecialityModule.getModuleClassification() == 1) {
						totalModule1 = totalNoteOver20MultiplyByCoeff / totalCoeff;
						modulesParcourusAndTheirTotal.put(studentSessionSpecialityModule.getId(), totalModule1);
					}
					if (studentSessionSpecialityModule.getModuleClassification() == 2) {
						totalModule2 = totalNoteOver20MultiplyByCoeff / totalCoeff;
						modulesParcourusAndTheirTotal.put(studentSessionSpecialityModule.getId(), totalModule2);
					}
					if (studentSessionSpecialityModule.getModuleClassification() != 0
							&& studentSessionSpecialityModule.getModuleClassification() != 1
							&& studentSessionSpecialityModule.getModuleClassification() != 2) {
						totalModule = totalNoteOver20MultiplyByCoeff / (totalCoeff); // we
																						// remove
																						// coefficient
																						// one
																						// of
																						// epreuve
																						// pratique
						modulesParcourusAndTheirTotal.put(studentSessionSpecialityModule.getId(), totalModule);

					}
				}
				if (studentSessionSpecialityModule.getModuleClassification() == 1
						&& course.getModule().getModuleClassification() == 1
						&& counterCourses == listeOfCoursesOfModule.size() - 1) { // verify
																					// if
																					// MODULE
																					// 1
					PdfPCell cellToTalModuleILabel = new PdfPCell(new Phrase("TOTAL MODULE I", headFont6));
					cellToTalModuleILabel.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellToTalModuleILabel.setBorderWidthLeft(1);
					cellToTalModuleILabel.setBackgroundColor(BaseColor.LIGHT_GRAY);
					cellToTalModuleILabel.setColspan(2);
					table.addCell(cellToTalModuleILabel);

					PdfPCell cellToTalModuleINoteElim = new PdfPCell(
							new Phrase("< " + studentSessionSpecialityModule.getModuleMinNote() + "/20", headFont6));
					cellToTalModuleINoteElim.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellToTalModuleINoteElim.setBackgroundColor(BaseColor.LIGHT_GRAY);
					table.addCell(cellToTalModuleINoteElim);

					PdfPCell cellToTalModuletotalNoteOver20 = new PdfPCell(
							new Phrase(String.valueOf(Math.round(totalNoteOver20 * 100.0) / 100.0), headFont6));
					cellToTalModuletotalNoteOver20.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellToTalModuletotalNoteOver20);

					PdfPCell cellToTalModuleITotalCoef = new PdfPCell(
							new Phrase(String.valueOf(totalCoeff), headFont6));
					cellToTalModuleITotalCoef.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellToTalModuleITotalCoef.setBackgroundColor(BaseColor.LIGHT_GRAY);
					table.addCell(cellToTalModuleITotalCoef);

					PdfPCell cellToTalModuleITotalNoteMultiplyByCoeff = new PdfPCell(new Phrase(
							String.valueOf(Math.round(totalNoteOver20MultiplyByCoeff * 100.0) / 100.0), headFont6));
					cellToTalModuleITotalNoteMultiplyByCoeff.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellToTalModuleITotalNoteMultiplyByCoeff.setBorderWidthRight(1);
					table.addCell(cellToTalModuleITotalNoteMultiplyByCoeff);

				}
				if (studentSessionSpecialityModule.getModuleClassification() == 2
						&& course.getModule().getModuleClassification() == 2
						&& counterCourses == listeOfCoursesOfModule.size() - 1) { // verify
																					// if
																					// MODULE
																					// 2
					PdfPCell cellToTalModuleILabel = new PdfPCell(new Phrase("TOTAL MODULE II", headFont6));
					cellToTalModuleILabel.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellToTalModuleILabel.setBorderWidthLeft(1);
					cellToTalModuleILabel.setBackgroundColor(BaseColor.LIGHT_GRAY);
					cellToTalModuleILabel.setColspan(2);
					table.addCell(cellToTalModuleILabel);

					PdfPCell cellToTalModuleINoteElim = new PdfPCell(
							new Phrase("< " + studentSessionSpecialityModule.getModuleMinNote() + "/20", headFont6));
					cellToTalModuleINoteElim.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellToTalModuleINoteElim.setBackgroundColor(BaseColor.LIGHT_GRAY);
					table.addCell(cellToTalModuleINoteElim);

					PdfPCell cellToTalModuletotalNoteOver20 = new PdfPCell(
							new Phrase(String.valueOf(Math.round(totalNoteOver20 * 100.0) / 100.0), headFont6));
					cellToTalModuletotalNoteOver20.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellToTalModuletotalNoteOver20);

					PdfPCell cellToTalModuleITotalCoef = new PdfPCell(
							new Phrase(String.valueOf(totalCoeff), headFont6));
					cellToTalModuleITotalCoef.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellToTalModuleITotalCoef.setBackgroundColor(BaseColor.LIGHT_GRAY);
					table.addCell(cellToTalModuleITotalCoef);

					PdfPCell cellToTalModuleITotalNoteMultiplyByCoeff = new PdfPCell(new Phrase(
							String.valueOf(Math.round(totalNoteOver20MultiplyByCoeff * 100.0) / 100.0), headFont6));
					cellToTalModuleITotalNoteMultiplyByCoeff.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellToTalModuleITotalNoteMultiplyByCoeff.setBorderWidthRight(1);
					table.addCell(cellToTalModuleITotalNoteMultiplyByCoeff);

				}

				if (studentSessionSpecialityModule.getModuleClassification() != 2
						&& studentSessionSpecialityModule.getModuleClassification() != 1
						&& studentSessionSpecialityModule.getModuleClassification() != 0 && course.getModule()
								.getModuleClassification() == studentSessionSpecialityModule.getModuleClassification()
						&& counterCourses == listeOfCoursesOfModule.size() - 1) {
					// verify if MODULE not 1 and 2
					PdfPCell cellToTalModuleILabel;
					if (studentSessionSpecialityModule.getModuleClassification() == 3) {
						cellToTalModuleILabel = new PdfPCell(new Phrase("TOTAL MODULE III", headFont6));
					} else {
						cellToTalModuleILabel = new PdfPCell(new Phrase(
								"TOTAL MODULE " + studentSessionSpecialityModule.getModuleClassification(), headFont6));
						cellToTalModuleILabel.setHorizontalAlignment(Element.ALIGN_LEFT);
					}
					cellToTalModuleILabel.setBorderWidthLeft(1);
					cellToTalModuleILabel.setBackgroundColor(BaseColor.LIGHT_GRAY);
					cellToTalModuleILabel.setColspan(2);
					table.addCell(cellToTalModuleILabel);

					PdfPCell cellToTalModuleINoteElim = new PdfPCell(
							new Phrase("< " + studentSessionSpecialityModule.getModuleMinNote() + "/20", headFont6));
					cellToTalModuleINoteElim.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellToTalModuleINoteElim.setBackgroundColor(BaseColor.LIGHT_GRAY);
					table.addCell(cellToTalModuleINoteElim);

					PdfPCell cellToTalModuletotalNoteOver20 = new PdfPCell(
							new Phrase(String.valueOf(Math.round(totalNoteOver20 * 100.0) / 100.0), headFont6));
					cellToTalModuletotalNoteOver20.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cellToTalModuletotalNoteOver20);

					PdfPCell cellToTalModuleITotalCoef = new PdfPCell(
							new Phrase(String.valueOf(totalCoeff), headFont6));
					cellToTalModuleITotalCoef.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellToTalModuleITotalCoef.setBackgroundColor(BaseColor.LIGHT_GRAY);
					table.addCell(cellToTalModuleITotalCoef);

					// ajout de la note pratique facultative au module 3
					// coursFacultaif
					/*
					 * float studentSessionPracticalNote = 0f;
					 * if(transcriptService.findByStudentSessionAndCourse(
					 * studentSession, coursFacultaif)!= null &&
					 * studentSessionSpecialityModule.getModuleClassification()
					 * == 3) studentSessionPracticalNote =
					 * transcriptService.findByStudentSessionAndCourse(
					 * studentSession, coursFacultaif).getCourseNote();
					 */
					PdfPCell cellToTalModuleITotalNoteMultiplyByCoeff = new PdfPCell(new Phrase(
							String.valueOf((Math.round(totalNoteOver20MultiplyByCoeff * 100.0) / 100.0)), headFont6));
					cellToTalModuleITotalNoteMultiplyByCoeff.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellToTalModuleITotalNoteMultiplyByCoeff.setBorderWidthRight(1);
					table.addCell(cellToTalModuleITotalNoteMultiplyByCoeff);

				}

				counterCourses++;
			}
		}

		// nous sommes au dernier module de la specialite du studentSession
		PdfPCell cellSynthese = new PdfPCell(new Phrase("SYNTHESE DES PERFORMANCES / PERFORMANCE SUMMARY", headFont6));
		cellSynthese.setHorizontalAlignment(Element.ALIGN_CENTER);
		cellSynthese.setBorderWidthLeft(1);
		cellSynthese.setBorderWidthRight(1);
		cellSynthese.setBackgroundColor(BaseColor.LIGHT_GRAY);
		cellSynthese.setColspan(6);
		table.addCell(cellSynthese);
		/*
		 * start comment totaux modules (moyenne per module)
		 *
		 * for(Module oneModule:modulesParcourus){ PdfPCell cellSyntheseModule =
		 * null; if(oneModule.getModuleClassification()== 1) cellSyntheseModule
		 * = new PdfPCell(new
		 * Phrase("MOD I - "+oneModule.getCompleteName().substring(0,1).
		 * toUpperCase()+
		 * oneModule.getCompleteName().substring(1).toLowerCase(), headFont6));
		 * if(oneModule.getModuleClassification()== 2) cellSyntheseModule = new
		 * PdfPCell(new
		 * Phrase("MOD II - "+oneModule.getCompleteName().substring(0,1).
		 * toUpperCase()+
		 * oneModule.getCompleteName().substring(1).toLowerCase(), headFont6));
		 * if(oneModule.getModuleClassification()!= 2 &&
		 * oneModule.getModuleClassification()!= 1 &&
		 * oneModule.getModuleClassification()!= -1 &&
		 * oneModule.getModuleClassification()!= 0) cellSyntheseModule = new
		 * PdfPCell(new
		 * Phrase("MOD "+oneModule.getModuleClassification()+" - "+oneModule.
		 * getCompleteName().substring(0,1).toUpperCase()+oneModule.
		 * getCompleteName(). substring(1).toLowerCase(), headFont6));
		 *
		 * cellSyntheseModule.setHorizontalAlignment(Element.ALIGN_LEFT);
		 * cellSyntheseModule.setBorderWidthLeft(1);
		 * //c8.setBorderWidthRight(1);
		 * //cellToTalModuleITotalNoteMultiplyByCoeff.setBackgroundColor(
		 * BaseColor. LIGHT_GRAY); cellSyntheseModule.setColspan(3);
		 * table.addCell(cellSyntheseModule);
		 *
		 * if( oneModule.getModuleClassification()== 1){
		 *
		 * PdfPCell cellSyntheseModule1 = new PdfPCell(new
		 * Phrase(String.valueOf(Math.round(totalModule1*100.0)/100.0)+" /20",
		 * headFont6));
		 * cellSyntheseModule1.setHorizontalAlignment(Element.ALIGN_LEFT);
		 * cellSyntheseModule1.setBorderWidthRight(1);
		 * //c8.setBorderWidthRight(1);
		 * //cellToTalModuleITotalNoteMultiplyByCoeff.setBackgroundColor(
		 * BaseColor. LIGHT_GRAY); cellSyntheseModule1.setColspan(3);
		 * table.addCell(cellSyntheseModule1);
		 *
		 * } if(oneModule.getModuleClassification()== 2){
		 *
		 * PdfPCell cellSyntheseModule2 = new PdfPCell(new
		 * Phrase(String.valueOf(Math.round(totalModule2*100.0)/100.0)+" /20",
		 * headFont6));
		 * cellSyntheseModule2.setHorizontalAlignment(Element.ALIGN_LEFT);
		 * cellSyntheseModule2.setBorderWidthRight(1);
		 * //c8.setBorderWidthRight(1);
		 * //cellToTalModuleITotalNoteMultiplyByCoeff.setBackgroundColor(
		 * BaseColor. LIGHT_GRAY); cellSyntheseModule2.setColspan(3);
		 * table.addCell(cellSyntheseModule2);
		 *
		 * }
		 *
		 * if(oneModule.getModuleClassification()!= 2 &&
		 * oneModule.getModuleClassification()!= 1 &&
		 * oneModule.getModuleClassification()!= 0 &&
		 * oneModule.getModuleClassification()!= -1){
		 * for(Map.Entry<Integer,Float> subModuleId :
		 * modulesParcourusAndTheirTotal.entrySet()){ Module subModule =
		 * moduleService.findModuleById(subModuleId.getKey());
		 * if(subModule.getModuleClassification() ==
		 * oneModule.getModuleClassification() ){ PdfPCell cellSyntheseModule2 =
		 * new PdfPCell(new
		 * Phrase(String.valueOf(Math.round(subModuleId.getValue()*100.0)/100.0)
		 * +" /20", headFont6));
		 * cellSyntheseModule2.setHorizontalAlignment(Element.ALIGN_LEFT);
		 * cellSyntheseModule2.setBorderWidthRight(1);
		 * //c8.setBorderWidthRight(1);
		 * //cellToTalModuleITotalNoteMultiplyByCoeff.setBackgroundColor(
		 * BaseColor. LIGHT_GRAY); cellSyntheseModule2.setColspan(3);
		 * table.addCell(cellSyntheseModule2); } } }
		 *
		 *
		 *
		 *
		 *
		 * }
		 *
		 * end comment totaux modules (moyenne per module)
		 */

		PdfPCell cellMoyenneFinale;
		cellMoyenneFinale = new PdfPCell(new Phrase("Moyenne Finale / Final Average", headFont6));
		cellMoyenneFinale.setVerticalAlignment(Element.ALIGN_LEFT);
		cellMoyenneFinale.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellMoyenneFinale.setColspan(3);
		cellMoyenneFinale.setBorderWidthLeft(1);
		cellMoyenneFinale.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(cellMoyenneFinale);

		for (Map.Entry<Integer, Float> subModule : modulesParcourusAndTheirTotal.entrySet()) {
			Module subModuleObject = moduleService.findModuleById(subModule.getKey());

			if (subModuleObject.getStatus() == 1)
				totalOfAllModule += subModule.getValue();
		}
		/*
		 * start System auto calculate final mark PdfPCell
		 * cellMoyenneFinaleValue; cellMoyenneFinaleValue = new PdfPCell(new
		 * Phrase(String.valueOf(Math.round(((totalOfAllModule)/(numModules
		 * -moduleService.findByStatus(0).size())) *100.0)/100.0)+" / 20 ",
		 * headFont6)); end System auto calculate final mark
		 */
		double note = Math.round(((totalOfAllModule) / (numModules - moduleService.findByStatus(0).size())) * 100.0)
				/ 100.0;

		if (studentSession.getNote() == 0) {
			studentSession.setNote(note);
			studentSessionService.updateStudentSession(studentSession);
			PdfPCell cellMoyenneFinaleValue;
			cellMoyenneFinaleValue = new PdfPCell(
					new Phrase(String.valueOf(studentSession.getNote()) + " / 20 ", headFont6));

			cellMoyenneFinaleValue.setVerticalAlignment(Element.ALIGN_LEFT);
			cellMoyenneFinaleValue.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellMoyenneFinaleValue.setColspan(3);
			cellMoyenneFinaleValue.setBorderWidthRight(1);
			cellMoyenneFinaleValue.setBackgroundColor(BaseColor.WHITE);
			table.addCell(cellMoyenneFinaleValue);
		} else {
			// System.out.println("note " + studentSession.getNote());
			PdfPCell cellMoyenneFinaleValueNew;
			double newNote = studentSession.getNote();
			cellMoyenneFinaleValueNew = new PdfPCell(new Phrase(String.valueOf(newNote) + " / 20 ", headFont6));

			cellMoyenneFinaleValueNew.setVerticalAlignment(Element.ALIGN_LEFT);
			cellMoyenneFinaleValueNew.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellMoyenneFinaleValueNew.setColspan(3);
			cellMoyenneFinaleValueNew.setBorderWidthRight(1);
			cellMoyenneFinaleValueNew.setBackgroundColor(BaseColor.WHITE);
			table.addCell(cellMoyenneFinaleValueNew);
		}
		PdfPCell cellSeuilReussiteLabel;
		Module moduleOne = null;
		Module moduleTwo = null;
		Module moduleThree = null;

		for (Module module : studentSessionSpecialityModules) {
			if (module.getModuleClassification() == 1)
				moduleOne = module;
			else if (module.getModuleClassification() == 2)
				moduleTwo = module;
			else {
				if (module.getModuleClassification() == 3)
					moduleThree = module;
			}

		}

		cellSeuilReussiteLabel = new PdfPCell(new Phrase("Seuil de Reussite / Minimum Pass Grade               MOD I ("
				+ moduleOne.getModulePercentage() + "%) + MOD II (" + moduleTwo.getModulePercentage() + "%) + MOD III ("
				+ moduleThree.getModulePercentage() + "%)", headFont6));
		cellSeuilReussiteLabel.setVerticalAlignment(Element.ALIGN_LEFT);
		cellSeuilReussiteLabel.setHorizontalAlignment(Element.ALIGN_LEFT);
		cellSeuilReussiteLabel.setColspan(6);
		cellSeuilReussiteLabel.setBorderWidthLeft(1);
		cellSeuilReussiteLabel.setBorderWidthRight(1);
		cellSeuilReussiteLabel.setBackgroundColor(BaseColor.WHITE);
		table.addCell(cellSeuilReussiteLabel);

		PdfPCell celldecisionJuryLabel;
		celldecisionJuryLabel = new PdfPCell(new Phrase("Décision Du Jury / Jury Decision", headFont6));
		celldecisionJuryLabel.setVerticalAlignment(Element.ALIGN_LEFT);
		celldecisionJuryLabel.setHorizontalAlignment(Element.ALIGN_LEFT);
		celldecisionJuryLabel.setColspan(3);
		celldecisionJuryLabel.setBorderWidthLeft(1);
		celldecisionJuryLabel.setBorderWidthBottom(1);
		celldecisionJuryLabel.setBackgroundColor(BaseColor.LIGHT_GRAY);
		table.addCell(celldecisionJuryLabel);

		PdfPCell celldecisionJuryValue;
		celldecisionJuryValue = new PdfPCell(new Phrase(" ", headFont6));
		celldecisionJuryValue.setVerticalAlignment(Element.ALIGN_LEFT);
		celldecisionJuryValue.setHorizontalAlignment(Element.ALIGN_LEFT);
		celldecisionJuryValue.setColspan(3);
		celldecisionJuryValue.setBorderWidthRight(1);
		celldecisionJuryValue.setBorderWidthBottom(1);
		celldecisionJuryValue.setBackgroundColor(BaseColor.WHITE);
		table.addCell(celldecisionJuryValue);

		document.add(table);
		// end printing table
	}

	public void printAdmittedPdf(ByteArrayOutputStream outStream, HttpHeaders headers, EligibleCenter eligibleCenter,TrainingCenter trainingCenter
			) throws DocumentException, IOException {
		List<StudentSession> listOfStudentSessions = new ArrayList<StudentSession>();
        List<StudentSession> listOfStudentSessionsOfTrainingCenter = new ArrayList<StudentSession>();
        if(eligibleCenter != null && eligibleCenter.getEligibleCenterStatus().getDescription().equals("VALIDATED")){
        	listOfStudentSessions = studentSessionService.findByStudentSessionStatusAndEligibleCenter(studentSessionStatusService.findById(4), eligibleCenter);
        	listOfStudentSessions.forEach((studentSession)->{
        		if(studentSession.getStudent().getTrainingCenter().getId() == trainingCenter.getId())
        			listOfStudentSessionsOfTrainingCenter.add(studentSession);
        		
        	});
        }
		
		Document document = null;
		document = new Document(PageSize.A4.rotate());
		document.setMargins(35, 35, 30, 150);
		PdfWriter writer = PdfWriter.getInstance(document, outStream);


		HeaderFooterPageEventAdmitted event = new HeaderFooterPageEventAdmitted();

		document.open();
		writer.setPageEvent(event);
		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 14);
		Font headFont1 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 8);
		Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);
		Font headFont4 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 5);
		// l'entete du pdf
		headersAdmittedPdf(document, writer, true);
		
		generateAdmittedListsHeadersForCandidatesList( eligibleCenter,  trainingCenter,
				 headFont3,  writer,  trainingCenter);


		String text30 = "";
		Phrase phrase30 = new Phrase(text30, headFont3);
		document.add(phrase30);

		document.add(Chunk.NEWLINE);

		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100);
		table.setWidths(new int[] {2,12,4,5,8,8,10});
		//table.setSpacingBefore(110);
		table.setSpacingBefore(140);
		table.setSpacingAfter(18);

		PdfPCell c1 = new PdfPCell(new Phrase("N°", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setPaddingTop(8);
		c1.setPaddingBottom(8);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("NOMS ET PRENOMS \n NAMES AND SURNAMES", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setPaddingTop(8);
		c1.setPaddingBottom(8);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("DIPLOME\n CERTIFICATE", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setPaddingTop(8);
		c1.setPaddingBottom(8);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("RELEVE DE NOTES\n TRANSCRIPT", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setPaddingTop(8);
		c1.setPaddingBottom(8);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("CARTE PROFESSIONNELLE\n PROFESSIONAL CARD", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setPaddingTop(8);
		c1.setPaddingBottom(8);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("CNI ET EMARGEMENT\n NID AND SIGNATURE", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setPaddingTop(8);
		c1.setPaddingBottom(8);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("OBSERVATIONS\n ", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setPaddingTop(8);
		c1.setPaddingBottom(8);
		table.addCell(c1);
		
		table.setHeaderRows(1);

		int i = 1;
		for (StudentSession candidateSession : listOfStudentSessionsOfTrainingCenter) {
			PdfPCell cell;
			cell = new PdfPCell(new Phrase(String.valueOf(i), headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPaddingRight(5);
			table.addCell(cell);
            
			String studentCompleteName = "";
			if(candidateSession.getStudent().getPerson().getSurName()!=null)
				 studentCompleteName+=candidateSession.getStudent().getPerson().getSurName().toUpperCase();
			if(candidateSession.getStudent().getPerson().getGivenName()!=null)
				 studentCompleteName+=" "+candidateSession.getStudent().getPerson().getGivenName().toUpperCase();
			
			cell = new PdfPCell(new Phrase(studentCompleteName, headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingLeft(6);
			cell.setPaddingTop(6);
			cell.setPaddingBottom(6);
			table.addCell(cell);
			
			
			
			String fontDejavu = admittedListIconsFolder+"/dejavu-sans/DejaVuSans.ttf";
	        FontSelector selector = new FontSelector();
	        BaseFont base = BaseFont.createFont(fontDejavu, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	        selector.addFont(FontFactory.getFont(FontFactory.HELVETICA, 12));
	        selector.addFont(new Font(base, 12));
	        char tickSymbol=10004;
	        char crossSymbol=10006;
	        String text = String.valueOf(tickSymbol);
	        Phrase phCheck = selector.process(text);
	        
	        String textCross = String.valueOf(crossSymbol);
	        Phrase phCross = selector.process(textCross);
			
			//gestion du certificat
			if(candidateSession.getCertificate()!= null ){
				List<Application> listOfApplicationsCertif = new ArrayList<Application>(candidateSession.getCertificate().getApplications());
				List<Application> listOfApplications = new ArrayList<Application>();
				listOfApplicationsCertif.forEach((app) -> {
					if(app.getProcessType().getDescription().contains("CERTIFICATE"))
							listOfApplications.add(app);
				});
				if(listOfApplications.size() > 0 && (listOfApplications.get(0).getApplicationStatus().getDescription().equals("SUCCESSFULLY PRINTED")|listOfApplications.get(0).getApplicationStatus().getDescription().equals("DELIVERED"))){
					cell = new PdfPCell(phCheck);
					cell.setPaddingLeft(5);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingTop(6);
					cell.setPaddingBottom(6);
					table.addCell(cell);
				}else if(listOfApplications.size() > 0 && ! listOfApplications.get(0).getApplicationStatus().getDescription().equals("UNSUCCESSFULLY PRINTED")){
					cell = new PdfPCell(phCross);
					cell.setPaddingLeft(5);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingTop(6);
					cell.setPaddingBottom(6);
					table.addCell(cell);
				}
			}else{
				cell = new PdfPCell(phCross);
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingTop(6);
				cell.setPaddingBottom(6);
				table.addCell(cell);
			}
			

			//gestion du relevé de notes
			if(candidateSession.getApplicationTranscript()!= null ){
				List<Application> listOfApplications = new ArrayList<Application>(candidateSession.getApplicationTranscript().getApplications());
			
				if(listOfApplications.size() > 0 && (listOfApplications.get(0).getApplicationStatus().getDescription().equals("SUCCESSFULLY PRINTED")|listOfApplications.get(0).getApplicationStatus().getDescription().equals("DELIVERED"))){
					cell = new PdfPCell(phCheck);
					cell.setPaddingLeft(5);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingTop(6);
					cell.setPaddingBottom(6);
					table.addCell(cell);
				}else if(listOfApplications.size() > 0 && ! listOfApplications.get(0).getApplicationStatus().getDescription().equals("UNSUCCESSFULLY PRINTED")){
					cell = new PdfPCell(phCross);
					cell.setPaddingLeft(5);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingTop(6);
					cell.setPaddingBottom(6);
					table.addCell(cell);
				}
			}else{
				cell = new PdfPCell(phCross);
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingTop(6);
				cell.setPaddingBottom(6);
				table.addCell(cell);
			}
			
			
			//gestion du Professional card
			if(candidateSession.getCertificate()!=null && candidateSession.getCertificate().getProfessionalCard()!= null ){
				List<Application> listOfApplicationsCertif = new ArrayList<Application>(candidateSession.getCertificate().getApplications());
				List<Application> listOfApplications = new ArrayList<Application>();
				listOfApplicationsCertif.forEach((app) -> {
					if(app.getProcessType().getDescription().contains("CARD"))
							listOfApplications.add(app);
				});
				if(listOfApplications.size() > 0 && (listOfApplications.get(0).getApplicationStatus().getDescription().equals("SUCCESSFULLY PRINTED")|listOfApplications.get(0).getApplicationStatus().getDescription().equals("DELIVERED"))){
					cell = new PdfPCell(phCheck);
					cell.setPaddingLeft(5);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingTop(6);
					cell.setPaddingBottom(6);
					table.addCell(cell);
				}else if(listOfApplications.size() > 0 && ! listOfApplications.get(0).getApplicationStatus().getDescription().equals("UNSUCCESSFULLY PRINTED")){
					cell = new PdfPCell(phCross);
					cell.setPaddingLeft(5);
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingTop(6);
					cell.setPaddingBottom(6);
					table.addCell(cell);
				}
			}else{
				cell = new PdfPCell(phCross);
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingTop(6);
				cell.setPaddingBottom(6);
				table.addCell(cell);
			}
			
		        
			cell = new PdfPCell(
					new Phrase(" ", headFont2) );
			cell.setPaddingLeft(5);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingTop(6);
			cell.setPaddingBottom(6);
			table.addCell(cell);
			
			
			cell = new PdfPCell(
					new Phrase(" ", headFont2));
			cell.setPaddingLeft(5);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingTop(6);
			cell.setPaddingBottom(6);
			table.addCell(cell);

	
			i++;
		}
		document.add(table);
		
		generateAdmittedListsFooter( writer,  document);
		
		document.close();
		headers.add("Content-Disposition", "inline; filename=delivery-slip.pdf");

	}

	@Override
	public void printCertificate(ByteArrayOutputStream outStream, HttpHeaders headers, int studentSessionId,
			int eligibleCenterId, String token) throws DocumentException, IOException {
		if (studentSessionId == 0) {
			// cas d impression de tout le PV
			if (eligibleCenterId != 0) {
				printCertificateContent(outStream, headers, studentSessionId, eligibleCenterId, null, token);
			}
		} else {
			// cas d impression du transcript d un seul candidate
			StudentSession studentSession = studentSessionService.findById(studentSessionId);
			Speciality studentSessionSpeciality = studentSession.getSpeciality();
			if (studentSessionSpeciality.getAbbreviation().equals("MAE")) {
				printCertificateContent(outStream, headers, studentSessionId, eligibleCenterId, "MAE", token);
			} else {
				if (studentSessionSpeciality.getAbbreviation().equals("IPCSR")
						|| studentSessionSpeciality.getAbbreviation().equals("DPCSR")) {
					printCertificateContent(outStream, headers, studentSessionId, eligibleCenterId, "EXPERTS", token);
				}
			}
		}

	}

	private void printCertificateContent(ByteArrayOutputStream outStream, HttpHeaders headers, int studentSessionId,
			int eligiblecenterId, String specialityAbbreviation, String token) throws DocumentException, IOException {
		Font headFont1 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);

		if (specialityAbbreviation.equals(null)) {

			// cas d impression de tous les transcripts d 'un PV
			EligibleCenter eligibleCenter = eligibleCenterService.findById(eligiblecenterId);
			List<StudentSession> allCenterStudents = studentSessionService.findByEligibleCenter(eligibleCenter);

			for (StudentSession studentSession : allCenterStudents) {
				// generateReleveTittles(studentSession, headFont1,headFont3);
				// printTranscriptTableOneStudent( studentSession, document,
				// headFont5,
				// headFont6, headFont3, headFont2);

			}
			headers.add("Content-Disposition", "inline; filename=transcript.pdf");
		} else {
			if (specialityAbbreviation.equals("MAE")) {

				StudentSession studentSession = studentSessionService.findById(studentSessionId);
				generateReleveTittles_for_Certificate(outStream, studentSession, headFont1, headFont3, token);

			} else {

				StudentSession studentSession = studentSessionService.findById(studentSessionId);
				generateReleveTittles_for_Certificate(outStream, studentSession, headFont1, headFont3, token);

		

			}
		}

	}

	private void generateReleveTittles_for_Certificate(ByteArrayOutputStream outStream, StudentSession studentSession,
			Font headFont1, Font headFont3, String token) throws IOException, DocumentException {

		Speciality studentSessionSpeciality = studentSession.getSpeciality();
		String speciality = studentSessionSpeciality.getAbbreviation();
		List<Certificate> certificatesPerType = certificateService.findCertificatesByType(speciality);
		List<Certificate> printedCertificate = new ArrayList<Certificate>();

		for (Certificate certificatesP : certificatesPerType) {
			if (certificatesP.getPrintedDate() != null) {
				printedCertificate.add(certificatesP);
			}
		}
		int certificateNum = printedCertificate.size() + 1;

		String certificateNumfinal = printerService.generateCertificateNumber(speciality, certificateNum);

		String studentGrade = printerService.generateGrade(studentSession);

		Rectangle pagesize = new Rectangle(575, 814);
		Document document = new Document(pagesize);

		File file = new File(certificateFilesFolder + "certificate.pdf");
		FileOutputStream pdfFileout = new FileOutputStream(file);
		PdfWriter.getInstance(document, pdfFileout);

		PdfWriter writer = PdfWriter.getInstance(document, outStream);
		// document.setPageSize(PageSize.A4);
		// document.setMargins(250, 25, 25, 24);
		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 13);

		document.open();

		String text79 = " ";
		com.itextpdf.text.pdf.PdfContentByte canvas79 = writer.getDirectContent();
		Phrase phrase79 = new Phrase(text79, headFont);
		ColumnText.showTextAligned(canvas79, Element.ALIGN_CENTER, phrase79, 400, 470, 0);

		document.add(phrase79);

		Image background = Image.getInstance(certificateBackGroundFolder + "certificate.jpg/");
		background.setAbsolutePosition(0, 0);
		background.scaleAbsolute(575, 814);
		if (token == "preview")
			document.add(background);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String sessionDate = dateFormat.format(studentSession.getEligibleCenter().getExamSession().getSessionDate());
		String dobPerson = dateFormat.format(studentSession.getStudent().getPerson().getDob());

		/** PARAGRAPH-SECTION BLOCK 2 */

		String text161 = " " + certificateNumfinal;
		com.itextpdf.text.pdf.PdfContentByte canvas161 = writer.getDirectContent();
		Phrase phrase161 = new Phrase(text161, headFont);
		ColumnText.showTextAligned(canvas161, Element.ALIGN_LEFT, phrase161, 203, 590, 0);

		String text261 = " " + studentSession.getEligibleCenter().getJuryNumber();
		com.itextpdf.text.pdf.PdfContentByte canvas261 = writer.getDirectContent();
		Phrase phrase261 = new Phrase(text261, headFont);
		ColumnText.showTextAligned(canvas261, Element.ALIGN_LEFT, phrase261, 191, 481, 0);

		String text61 = " " + sessionDate;
		com.itextpdf.text.pdf.PdfContentByte canvas61 = writer.getDirectContent();
		Phrase phrase61 = new Phrase(text61, headFont);
		ColumnText.showTextAligned(canvas61, Element.ALIGN_LEFT, phrase61, 298, 481, 0);

		com.itextpdf.text.pdf.PdfContentByte canvas62 = writer.getDirectContent();
		Phrase phrase62 = new Phrase(studentSession.getEligibleCenter().getExamCenter().getName(), headFont);
		ColumnText.showTextAligned(canvas62, Element.ALIGN_LEFT, phrase62, 426, 481, 0);

		/** PARAGRAPH-SECTION BLOCK 4 */

		com.itextpdf.text.pdf.PdfContentByte canvas64 = writer.getDirectContent();
		Phrase phrase64 = new Phrase(studentSession.getSpeciality().getName(), headFont);
		ColumnText.showTextAligned(canvas64, Element.ALIGN_LEFT, phrase64, 123, 379, 0);

		com.itextpdf.text.pdf.PdfContentByte canvas65 = writer.getDirectContent();
		Phrase phrase65 = new Phrase(studentSession.getStudent().getPerson().getMatricule(), headFont);
		ColumnText.showTextAligned(canvas65, Element.ALIGN_LEFT, phrase65, 210, 304, 0);

		/* end implementation on section line 4 */

		/** PARAGRAPH-SECTION BLOCK 5 */

		String text66 = studentSession.getStudent().getPerson().getSurName() + " "
				+ studentSession.getStudent().getPerson().getGivenName();
		com.itextpdf.text.pdf.PdfContentByte canvas66 = writer.getDirectContent();
		Phrase phrase66 = new Phrase(text66, headFont);
		ColumnText.showTextAligned(canvas66, Element.ALIGN_LEFT, phrase66, 158, 350, 0);

		/** PARAGRAPH-SECTION BLOCK 6 */

		/* start implementation on section line 6 */

		String text67 = dobPerson;
		com.itextpdf.text.pdf.PdfContentByte canvas67 = writer.getDirectContent();
		Phrase phrase67 = new Phrase(text67, headFont);
		ColumnText.showTextAligned(canvas67, Element.ALIGN_LEFT, phrase67, 472, 304, 0);

		com.itextpdf.text.pdf.PdfContentByte canvas68 = writer.getDirectContent();
		Phrase phrase68 = new Phrase(studentSession.getStudent().getPerson().getPob(), headFont);
		ColumnText.showTextAligned(canvas68, Element.ALIGN_LEFT, phrase68, 61, 275, 0);

		String text69 = "YAOUNDE";
		com.itextpdf.text.pdf.PdfContentByte canvas69 = writer.getDirectContent();
		Phrase phrase69 = new Phrase(text69, headFont);
		ColumnText.showTextAligned(canvas69, Element.ALIGN_LEFT, phrase69, 290, 196, 0);

		com.itextpdf.text.pdf.PdfContentByte canvas70 = writer.getDirectContent();
		Phrase phrase70 = new Phrase(studentGrade, headFont);
		ColumnText.showTextAligned(canvas70, Element.ALIGN_LEFT, phrase70, 452, 275, 0);

		document.close();
	}

	@Override
	public void printProfessionalCard(ByteArrayOutputStream outStream, HttpHeaders headers, Application application,
			String token) throws DocumentException, IOException, ParseException {

		StudentSession studentSession = application.getCertificate().getStudentSession();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String issueDate = formatter.format(new Date());

		Calendar calenderDate = Calendar.getInstance();
		calenderDate.setTime(new Date());
		calenderDate.add(Calendar.YEAR, 3);
		Date expiryDateCalculated = calenderDate.getTime();
		String expiryDate = formatter.format(expiryDateCalculated);

		Document document = new Document(new Rectangle(243, 153));
		Document document1 = new Document(new Rectangle(243, 153));

		File file = new File(professionalCardFolder + "professional_card_" + application.getId() + ".pdf");
		FileOutputStream pdfFileout = new FileOutputStream(file);
		PdfWriter.getInstance(document, pdfFileout);
		PdfWriter.getInstance(document1, pdfFileout);

		PdfWriter writer = PdfWriter.getInstance(document, outStream);
		Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD.toUpperCase(), 6);
		Font headFont3 = FontFactory.getFont(FontFactory.HELVETICA_BOLD.toUpperCase(), 8);
		// Font headFont3 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7);
		document.open();

		Image background = Image.getInstance(professionalCardFolder + "card-front.jpg");
		background.setAbsolutePosition(0, 0);
		background.scaleAbsolute(243, 153);
		System.out.println(token);
		if (token.equals("preview")) {
			document.add(background);
		}

		Image pic = Image.getInstance(applicationPhotoFolder + application.getPhoto());
		pic.setAbsolutePosition(10f, 30f);
		pic.scaleAbsolute(65, 80);
		document.add(pic);

		Image sig = Image.getInstance(applicationSignatureFolder + application.getSignature());
		sig.setAbsolutePosition(90, 24);
		sig.scaleAbsolute(20, 15);
		document.add(sig);

		Date dobDate = studentSession.getStudent().getPerson().getDob();
		String dobDateFinal = formatter.format(dobDate);

		com.itextpdf.text.pdf.PdfContentByte canvas2 = writer.getDirectContent();
		Phrase phrase2 = new Phrase(studentSession.getStudent().getPerson().getSurName().toUpperCase(), headFont2);
		ColumnText.showTextAligned(canvas2, Element.ALIGN_LEFT, phrase2, 90, 102, 0);

		com.itextpdf.text.pdf.PdfContentByte canvas3 = writer.getDirectContent();
		Phrase phrase3 = new Phrase(studentSession.getStudent().getPerson().getGivenName().toUpperCase(), headFont2);
		ColumnText.showTextAligned(canvas3, Element.ALIGN_LEFT, phrase3, 90, 91, 0);

		com.itextpdf.text.pdf.PdfContentByte canvas4 = writer.getDirectContent();
		Phrase phrase4 = new Phrase(
				dobDateFinal + " at " + studentSession.getStudent().getPerson().getPob().toUpperCase(), headFont2);
		ColumnText.showTextAligned(canvas4, Element.ALIGN_LEFT, phrase4, 90, 80, 0);

		com.itextpdf.text.pdf.PdfContentByte canvas5 = writer.getDirectContent();
		Phrase phrase5 = new Phrase(issueDate, headFont2);
		ColumnText.showTextAligned(canvas5, Element.ALIGN_LEFT, phrase5, 90, 70, 0);

		com.itextpdf.text.pdf.PdfContentByte canvas6 = writer.getDirectContent();
		Phrase phrase6 = new Phrase(expiryDate, headFont2);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase6, 90, 59, 0);

		com.itextpdf.text.pdf.PdfContentByte canvas7 = writer.getDirectContent();
		Phrase phrase7 = new Phrase(application.getAuthority().getCompleteName().toUpperCase(), headFont2);
		ColumnText.showTextAligned(canvas7, Element.ALIGN_LEFT, phrase7, 142, 70, 0);

		com.itextpdf.text.pdf.PdfContentByte canvas17 = writer.getDirectContent();
		Phrase phrase17 = new Phrase("DTR", headFont2);
		ColumnText.showTextAligned(canvas17, Element.ALIGN_LEFT, phrase17, 218, 86, 0);

		Image sigDtr = Image.getInstance(authoritySignatureFolder + "dtrSignature.jpg");
		sigDtr.setAbsolutePosition(209, 92);
		sigDtr.scaleAbsolute(23, 18);
		document.add(sigDtr);

		com.itextpdf.text.pdf.PdfContentByte canvas8 = writer.getDirectContent();
		Phrase phrase8 = new Phrase(studentSession.getStudent().getReferenceNum(), headFont2);
		ColumnText.showTextAligned(canvas8, Element.ALIGN_LEFT, phrase8, 142, 59, 0);

		com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
		Phrase phrase9 = new Phrase(studentSession.getStudent().getPerson().getMatricule().toUpperCase(), headFont2);
		ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase9, 90, 49, 0);

		com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
		Phrase phrase10 = new Phrase(studentSession.getStudent().getPerson().getLicenseNum().toUpperCase(), headFont2);
		ColumnText.showTextAligned(canvas10, Element.ALIGN_LEFT, phrase10, 25, 24, 0);

		if (studentSession.getSpeciality().getId() == 1) {
			com.itextpdf.text.pdf.PdfContentByte canvas11 = writer.getDirectContent();
			Phrase phrase11 = new Phrase(studentSession.getSpeciality().getAbbreviation() + "/DSI", headFont3);
			ColumnText.showTextAligned(canvas11, Element.ALIGN_LEFT, phrase11, 90, 15, 0);
		} else if (studentSession.getSpeciality().getId() == 2) {
			com.itextpdf.text.pdf.PdfContentByte canvas11 = writer.getDirectContent();
			Phrase phrase11 = new Phrase(studentSession.getSpeciality().getAbbreviation() + "/IDLRS", headFont3);
			ColumnText.showTextAligned(canvas11, Element.ALIGN_LEFT, phrase11, 90, 15, 0);
		} else {
			com.itextpdf.text.pdf.PdfContentByte canvas11 = writer.getDirectContent();
			Phrase phrase11 = new Phrase(studentSession.getSpeciality().getAbbreviation() + "/DDLRS", headFont3);
			ColumnText.showTextAligned(canvas11, Element.ALIGN_LEFT, phrase11, 90, 15, 0);
		}

		document.newPage();
		Image background1 = Image.getInstance(professionalCardFolder + "card-back.jpg");
		background1.setAbsolutePosition(0, 0);
		background1.scaleAbsolute(243, 153);
		if (token.equals("preview")) {
			document.add(background1);
		}

		com.itextpdf.text.pdf.PdfContentByte canvas14 = writer.getDirectContent();
		Phrase phrase14 = new Phrase("NEW", headFont2);
		ColumnText.showTextAligned(canvas14, Element.ALIGN_LEFT, phrase14, 20, 140, 0);

		if (studentSession.getSpeciality().getId() == 1) {
			com.itextpdf.text.pdf.PdfContentByte canvas18 = writer.getDirectContent();
			Phrase phrase18 = new Phrase(studentSession.getSpeciality().getName().toUpperCase(), headFont3);
			ColumnText.showTextAligned(canvas18, Element.ALIGN_LEFT, phrase18, 115, 115, 0);
			com.itextpdf.text.pdf.PdfContentByte canvas19 = writer.getDirectContent();
			Phrase phrase19 = new Phrase(studentSession.getSpeciality().getNameEnglish().toUpperCase(), headFont3);
			ColumnText.showTextAligned(canvas19, Element.ALIGN_LEFT, phrase19, 107, 105, 0);

		} else {
			String speciality = studentSession.getSpeciality().getName().toUpperCase();

			String[] tab = speciality.split("ET");

			String specialityPart1 = tab[0];

			String specialityPart2 = tab[1];

			com.itextpdf.text.pdf.PdfContentByte canvas18 = writer.getDirectContent();
			Phrase phrase18 = new Phrase(specialityPart1, headFont3);
			ColumnText.showTextAligned(canvas18, Element.ALIGN_LEFT, phrase18, 80, 115, 0);

			Phrase phrase20 = new Phrase("ET " + specialityPart2, headFont3);
			ColumnText.showTextAligned(canvas18, Element.ALIGN_LEFT, phrase20, 95, 106, 0);

			String specialityEnglish = studentSession.getSpeciality().getNameEnglish().toUpperCase();

			String[] specialityEnglishTab = specialityEnglish.split("AND");

			String specialityEnglish1 = specialityEnglishTab[0];

			String specialityEnglish2 = specialityEnglishTab[1];

			com.itextpdf.text.pdf.PdfContentByte canvas19 = writer.getDirectContent();
			Phrase phrase19 = new Phrase(specialityEnglish1, headFont3);
			ColumnText.showTextAligned(canvas19, Element.ALIGN_LEFT, phrase19, 85, 95, 0);

			com.itextpdf.text.pdf.PdfContentByte canvas20 = writer.getDirectContent();
			Phrase phrase21 = new Phrase("AND " + specialityEnglish2, headFont3);
			ColumnText.showTextAligned(canvas20, Element.ALIGN_LEFT, phrase21, 107, 86, 0);
		}
		Map<String, Object> resultsMap = new LinkedHashMap<String, Object>();
		List<CategoryForm> results = categoryRetrival.findAll(studentSession.getStudent().getPerson().getLicenseNum());

		for (int i = 0; i < results.size(); i++) {
			resultsMap = (Map<String, Object>) results.get(i);
			String categoryName = (String) resultsMap.get("name");
			String catExpiryDate = (String) resultsMap.get("expiryDate");

			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-DD");
			Date apiCatExpiryDate = (Date) formatter1.parse(catExpiryDate);

			SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
			String categoryExpiryDate = newFormat.format(apiCatExpiryDate);

			if (categoryName.equals("A")) {
				com.itextpdf.text.pdf.PdfContentByte canvas15 = writer.getDirectContent();
				Phrase phrase15 = new Phrase(categoryExpiryDate, headFont2);
				ColumnText.showTextAligned(canvas15, Element.ALIGN_LEFT, phrase15, 170, 55, 0);
			} else if (categoryName.equals("B")) {
				com.itextpdf.text.pdf.PdfContentByte canvas16 = writer.getDirectContent();
				Phrase phrase16 = new Phrase(categoryExpiryDate, headFont2);
				ColumnText.showTextAligned(canvas16, Element.ALIGN_LEFT, phrase16, 170, (float) 46.5, 0);
			} else if (categoryName.equals("C")) {
				com.itextpdf.text.pdf.PdfContentByte canvas15 = writer.getDirectContent();
				Phrase phrase15 = new Phrase(categoryExpiryDate, headFont2);
				ColumnText.showTextAligned(canvas15, Element.ALIGN_LEFT, phrase15, 170, 38, 0);
			} else if (categoryName.equals("D")) {
				com.itextpdf.text.pdf.PdfContentByte canvas15 = writer.getDirectContent();
				Phrase phrase15 = new Phrase(categoryExpiryDate, headFont2);
				ColumnText.showTextAligned(canvas15, Element.ALIGN_LEFT, phrase15, 170, (float) 29.5, 0);
			} else if (categoryName.equals("E")) {
				com.itextpdf.text.pdf.PdfContentByte canvas15 = writer.getDirectContent();
				Phrase phrase15 = new Phrase(categoryExpiryDate, headFont2);
				ColumnText.showTextAligned(canvas15, Element.ALIGN_LEFT, phrase15, 170, 21, 0);
			} else if (categoryName.equals("G")) {
				com.itextpdf.text.pdf.PdfContentByte canvas15 = writer.getDirectContent();
				Phrase phrase15 = new Phrase(categoryExpiryDate, headFont2);
				ColumnText.showTextAligned(canvas15, Element.ALIGN_LEFT, phrase15, 170, (float) 12.5, 0);
			} else {
				System.out.println("The Category " + categoryName + " is not considered!");
			}
		}
		
		qrcodeWriterService.generateEncryptedQrcode(application.getCertificate().getStudentSession().getId(), true);
		Image codeQrImage = Image
				.getInstance(rootQrcodeDataCard + application.getCertificate().getStudentSession().getId() + ".png");
		codeQrImage.setAbsolutePosition(15, 70);
		codeQrImage.scaleAbsolute(50, 50);
		document.add(codeQrImage);
		document.close();

	}

	@Override
	public void printStudentListForTrainingCenter(ByteArrayOutputStream outStream, HttpHeaders headers,
			int trainingCenterId) throws DocumentException, IOException {

		TrainingCenter trainingCenter = trainingCenterService.findById(trainingCenterId);
		List<Student> students = new ArrayList<Student>();
		Document document = null;

		document = new Document(PageSize.A4.rotate());

		String speciality = null;
		document.setMargins(35, 35, 30, 75);
		PdfWriter writer = PdfWriter.getInstance(document, outStream);
		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		writer.setPageEvent(event);
		document.open();
		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 14);
		Font headFont1 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 8);
		Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);
		Font headFont4 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 5);
		// l'entete du pdf
		headersPdf(document, writer, true);

		students = studentService.findByTrainingCenter(trainingCenter);
		generateStudentHeaders(students, trainingCenter, headFont1, headFont3, writer);

		String text30 = "";
		Phrase phrase30 = new Phrase(text30, headFont3);
		document.add(phrase30);

		document.add(Chunk.NEWLINE);

		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 3, 12, 11, 5, 7, 5, 6 });
		table.setSpacingBefore(110);
		table.setSpacingAfter(18);

		PdfPCell c1 = new PdfPCell(new Phrase("No.", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("NOM", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("PRENOM", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("DATE DE NAISSANCE", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("LIEU DE NAISSANCE", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("GENRE", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("SPECIALITY", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		table.setHeaderRows(1);

		int i = 1;
		for (Student student : students) {
			PdfPCell cell;

			cell = new PdfPCell(new Phrase(String.valueOf(i), headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPaddingRight(5);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(student.getPerson().getSurName().toUpperCase(), headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingRight(5);
			table.addCell(cell);

			if (student.getPerson().getGivenName() == null) {
				cell = new PdfPCell(new Phrase(" ", headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			} else {
				cell = new PdfPCell(new Phrase(student.getPerson().getGivenName().toUpperCase(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			}

			SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
			String stringDate = DateFor.format(student.getPerson().getDob());

			cell = new PdfPCell(new Phrase(stringDate, headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingRight(5);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(student.getPerson().getPob().toUpperCase(), headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingRight(5);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(student.getPerson().getGender(), headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingRight(5);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(student.getSpeciality().getAbbreviation(), headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingRight(5);
			table.addCell(cell);

			i++;

		}
		document.add(table);
		document.close();
		headers.add("Content-Disposition", "inline; filename=student-list.pdf");

	}

	private void generateStudentHeaders(List<Student> students, TrainingCenter trainingCenter, Font headFont1,
			Font headFont3, PdfWriter writer) {
		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 9);
		SimpleDateFormat sessionDate = new SimpleDateFormat("dd/MM/yyyy");
		String sessionDateFormatted = sessionDate.format(new Date());

		String text7 = "LISTE DES ETUDIANTS DANS LE CENTRE DE FORMATIONS: ( " + students.size() + " students)";
		com.itextpdf.text.pdf.PdfContentByte canvas6 = writer.getDirectContent();
		Phrase phrase7 = new Phrase(text7, headFont1);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase7, 250, 500, 0);

		String text8 = "STUDENT LIST AT TRAINING CENTER: ( " + students.size() + " students)";
		com.itextpdf.text.pdf.PdfContentByte canvas7 = writer.getDirectContent();
		Phrase phrase8 = new Phrase(text8, headFont3);
		ColumnText.showTextAligned(canvas7, Element.ALIGN_LEFT, phrase8, 300, 485, 0);

		String text9 = " CENTRE DE FORMATION / TRAINING CENTER: " + trainingCenter.getName();
		com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
		Phrase phrase9 = new Phrase(text9, headFont1);
		ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase9, 250, 470, 0);
		String text10 = "PRINTED ON : " + sessionDateFormatted;
		com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
		Phrase phrase10 = new Phrase(text10, headFont3);
		ColumnText.showTextAligned(canvas10, Element.ALIGN_LEFT, phrase10, 370, 455, 0);

	}

	public void printEligibleListByNote(ByteArrayOutputStream outStream, HttpHeaders headers, int eligibleCenterId,
			String typeList) throws DocumentException, IOException {
		EligibleCenter eligibleCenter = eligibleCenterService.findById(eligibleCenterId);
		List<StudentSession> candidateSessions = new ArrayList<StudentSession>();
		Document document = null;

		document = new Document(PageSize.A4.rotate());

		String speciality = null;
		document.setMargins(35, 35, 30, 75);
		PdfWriter writer = PdfWriter.getInstance(document, outStream);
		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		event.setNumCandidates(candidateSessions.size());
		writer.setPageEvent(event);
		document.open();
		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 14);
		Font headFont1 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 8);
		Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);
		Font headFont4 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 5);
		// l'entete du pdf
		headersPdf(document, writer, true);

		speciality = typeList.split("@")[1];
		Speciality candidateSpeciality = specialityService.findByAbbreviation(speciality);

		candidateSessions = studentSessionService
				.findByEligibleCenterAndResultAndSpecialityAndFinalResult(eligibleCenter, 2, candidateSpeciality);

		generatePvListsHeaders(candidateSessions, typeList.split("@")[0], speciality, eligibleCenter, headFont1,
				headFont3, writer);

		String text30 = "";
		Phrase phrase30 = new Phrase(text30, headFont3);
		document.add(phrase30);

		document.add(Chunk.NEWLINE);

		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 3, 8, 12, 11, 5, 7, 4, 5 });
		table.setSpacingBefore(110);
		table.setSpacingAfter(18);

		PdfPCell c1 = new PdfPCell(new Phrase("No", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("ECOLE FORMATION", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("NOM", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("PRENOM", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("DATE DE NAISSANCE", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("LIEU DE NAISSANCE", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("GENDER", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("PHOTO", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		table.setHeaderRows(1);

		int i = 1;
		for (StudentSession candidateSession : candidateSessions) {
			PdfPCell cell;
			cell = new PdfPCell(new Phrase(String.valueOf(i), headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPaddingRight(5);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(candidateSession.getStudent().getTrainingCenter().getName(), headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingRight(5);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(candidateSession.getStudent().getPerson().getSurName().toUpperCase(), headFont2));
			cell.setPaddingLeft(5);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);

			if (candidateSession.getStudent().getPerson().getGivenName() == null) {
				cell = new PdfPCell(new Phrase(" ", headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			} else {
				cell = new PdfPCell(
						new Phrase(candidateSession.getStudent().getPerson().getGivenName().toUpperCase(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			}

			SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
			String stringDate = DateFor.format(candidateSession.getStudent().getPerson().getDob());

			cell = new PdfPCell(new Phrase(stringDate, headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingRight(5);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(candidateSession.getStudent().getPerson().getPob().toUpperCase(), headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingRight(5);
			table.addCell(cell);

			if (candidateSession.getStudent().getPerson().getGender() == null) {
				cell = new PdfPCell(new Phrase(" ", headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			} else {
				cell = new PdfPCell(
						new Phrase(candidateSession.getStudent().getPerson().getGender().toUpperCase(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			}

			if (candidateSession.getPhotoAndSignature() == null) {
				Image studentPhoto = Image.getInstance(studentSessionPhotoFolder + "0.png");
				studentPhoto.scaleAbsolute(70, 70);
				cell = new PdfPCell(studentPhoto);
				table.addCell(cell);
			} else {
				Image studentPhoto = Image
						.getInstance(studentSessionPhotoFolder + candidateSession.getPhotoAndSignature());
				studentPhoto.scaleAbsolute(70, 70);
				cell = new PdfPCell(studentPhoto);
				table.addCell(cell);
			}

			i++;
		}
		document.add(table);
		document.close();
		headers.add("Content-Disposition", "inline; filename=eligible-candidates.pdf");

	}

	public void printListByNote(ByteArrayOutputStream outStream, HttpHeaders headers, int eligibleCenterId,
			String typeList) throws DocumentException, IOException {
		EligibleCenter eligibleCenter = eligibleCenterService.findById(eligibleCenterId);
		List<StudentSession> candidateSessions = new ArrayList<StudentSession>();
		Document document = null;

		document = new Document(PageSize.A4.rotate());

		String speciality = null;
		document.setMargins(35, 35, 30, 75);
		PdfWriter writer = PdfWriter.getInstance(document, outStream);
		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		event.setNumCandidates(candidateSessions.size());
		writer.setPageEvent(event);
		document.open();
		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 14);
		Font headFont1 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 8);
		Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);
		Font headFont4 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 5);
		// l'entete du pdf
		headersPdf(document, writer, true);

		if (typeList.contains("print_pv_note")) {
			speciality = typeList.split("@")[1];
			Speciality candidateSpeciality = specialityService.findByAbbreviation(speciality);

			candidateSessions = studentSessionService
					.findByEligibleCenterAndResultAndSpecialityAndFinalResult(eligibleCenter, 4, candidateSpeciality);
			generatePvListsHeaders(candidateSessions, typeList.split("@")[0], speciality, eligibleCenter, headFont1,
					headFont3, writer);

			String text30 = "";
			Phrase phrase30 = new Phrase(text30, headFont3);
			document.add(phrase30);

			document.add(Chunk.NEWLINE);

			PdfPTable table = new PdfPTable(8);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 3, 8, 12, 11, 5, 7, 3, 3 });
			table.setSpacingBefore(110);
			table.setSpacingAfter(18);

			PdfPCell c1 = new PdfPCell(new Phrase("Rang", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("ECOLE DE FORMATION", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("NOM", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("PRENOM", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("DATE DE NAISSANCE", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("LIEU DE NAISSANCE", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("GENRE", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("MOYENNE/20", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);

			table.setHeaderRows(1);
			int i = 1;
			for (StudentSession candidateSession : candidateSessions) {
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(i), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(candidateSession.getStudent().getTrainingCenter().getName(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(candidateSession.getStudent().getPerson().getSurName(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				if (candidateSession.getStudent().getPerson().getGivenName() == null) {
					cell = new PdfPCell(new Phrase(" ", headFont2));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(
							new Phrase(candidateSession.getStudent().getPerson().getGivenName(), headFont2));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}

				SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
				String stringDate = DateFor.format(candidateSession.getStudent().getPerson().getDob());

				cell = new PdfPCell(new Phrase(stringDate, headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(
						new Phrase(candidateSession.getStudent().getPerson().getPob().toUpperCase(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(candidateSession.getStudent().getPerson().getGender(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);
				cell = new PdfPCell(new Phrase(String.valueOf(candidateSession.getNote()), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				i++;
			}
			document.add(table);
		} else {
			speciality = typeList.split("@")[1];
			Speciality candidateSpeciality = specialityService.findByAbbreviation(speciality);
			if (typeList.contains("candidates_list_appouved")) {
				candidateSessions = studentSessionService.findByEligibleCenterAndResultAndSpeciality(eligibleCenter, 2,
						candidateSpeciality);
			} else if (typeList.contains("candidates_list_presented")) {
				candidateSessions = studentSessionService.findByEligibleCenterAndResultAndSpeciality(eligibleCenter, 1,
						candidateSpeciality);
			} else if (typeList.contains("candidates_list_rejected")) {
				candidateSessions = studentSessionService.findByEligibleCenterAndResultAndSpeciality(eligibleCenter, 3,
						candidateSpeciality);
			} else if (typeList.contains("candidates_failed")) {
				candidateSessions = studentSessionService.findByEligibleCenterAndResultAndSpeciality(eligibleCenter, 5,
						candidateSpeciality);
			} else if (typeList.contains("candidates_list_absent")) {
				candidateSessions = studentSessionService.findByEligibleCenterAndResultAndSpeciality(eligibleCenter, 6,
						candidateSpeciality);
			}
			generatePvListsHeaders(candidateSessions, typeList.split("@")[0], speciality, eligibleCenter, headFont1,
					headFont3, writer);

			String text30 = "";
			Phrase phrase30 = new Phrase(text30, headFont3);
			document.add(phrase30);

			document.add(Chunk.NEWLINE);

			PdfPTable table = new PdfPTable(8);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 3, 8, 12, 11, 5, 7, 3, 3 });
			table.setSpacingBefore(110);
			table.setSpacingAfter(18);

			PdfPCell c1 = new PdfPCell(new Phrase("No", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("ECOLE FORMATION", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("NOM", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("PRENOM", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("DATE DE NAISSANCE", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("LIEU DE NAISSANCE", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("GENDER", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);
			c1 = new PdfPCell(new Phrase("MOYENNE/20", headFont2));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(c1);

			table.setHeaderRows(1);

			int i = 1;
			for (StudentSession candidateSession : candidateSessions) {
				PdfPCell cell;
				cell = new PdfPCell(new Phrase(String.valueOf(i), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(candidateSession.getStudent().getTrainingCenter().getName(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(
						new Phrase(candidateSession.getStudent().getPerson().getSurName().toUpperCase(), headFont2));
				cell.setPaddingLeft(5);
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(cell);

				if (candidateSession.getStudent().getPerson().getGivenName() == null) {
					cell = new PdfPCell(new Phrase(" ", headFont2));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(new Phrase(
							candidateSession.getStudent().getPerson().getGivenName().toUpperCase(), headFont2));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}

				SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
				String stringDate = DateFor.format(candidateSession.getStudent().getPerson().getDob());

				cell = new PdfPCell(new Phrase(stringDate, headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(
						new Phrase(candidateSession.getStudent().getPerson().getPob().toUpperCase(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				if (candidateSession.getStudent().getPerson().getGender() == null) {
					cell = new PdfPCell(new Phrase(" ", headFont2));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				} else {
					cell = new PdfPCell(
							new Phrase(candidateSession.getStudent().getPerson().getGender().toUpperCase(), headFont2));
					cell.setVerticalAlignment(Element.ALIGN_CENTER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setPaddingRight(5);
					table.addCell(cell);
				}

				cell = new PdfPCell(new Phrase(Double.toString(candidateSession.getNote())));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				i++;
			}
			document.add(table);
		}
		document.close();
		headers.add("Content-Disposition", "inline; filename=pdf-sample.pdf");

	}

	@Override
	public void printTrainscriptPreview(ByteArrayOutputStream outStream, HttpHeaders headers, int studentSessionId,
			int eligibleCenterId, HttpServletRequest httpServletRequest) throws DocumentException, IOException {

		// cas d impression du transcript d un seul candidate
		StudentSession studentSession = studentSessionService.findById(studentSessionId);
		if (studentSession != null && studentSession instanceof StudentSession) {
			Speciality studentSessionSpeciality = studentSession.getSpeciality();
			if (studentSessionSpeciality.getAbbreviation().equals("MAE")) {
				printTranscriptContent(outStream, headers, studentSessionId, studentSession.getEligibleCenter().getId(),
						"MAE", true);
			} else {
				if (studentSessionSpeciality.getAbbreviation().equals("IPCSR")
						|| studentSessionSpeciality.getAbbreviation().equals("DPCSR")) {
					if (studentSessionSpeciality.getAbbreviation().equals("IPCSR"))
						printTranscriptContent(outStream, headers, studentSessionId,
								studentSession.getEligibleCenter().getId(), "IPCSR", true);
					if (studentSessionSpeciality.getAbbreviation().equals("DPCSR"))
						printTranscriptContent(outStream, headers, studentSessionId,
								studentSession.getEligibleCenter().getId(), "DPCSR", true);

				}
			}
		} else {
			EligibleCenter eligibleCenter = eligibleCenterService.findById(eligibleCenterId);
			if (eligibleCenter != null && eligibleCenter instanceof EligibleCenter) {
				// cas d'impression du preview pour tous les candidats
				printTranscriptContent(outStream, headers, 0, eligibleCenter.getId(), "NONE", true);
			}
		}
	}

	@Override
	public String generateCertificateNumber(String speciality, int certificateNum) {
		String str = String.format("%05d", certificateNum);
		String matricule = null;
		if (speciality != null && speciality.equals("MAE"))
			matricule = "M" + str;
		if (speciality != null && speciality.equals("IPCSR"))
			matricule = "I" + str;
		if (speciality != null && speciality.equals("DPCSR"))
			matricule = "D" + str;

		return matricule;
	}

	@Override
	public String generateGrade(StudentSession studentSession) {
		String grade = "";
		double gradeValue = studentSession.getNote();
		if (gradeValue < 3) {
			grade = "NULL";
		} else if (gradeValue < 6 && gradeValue >= 3) {
			grade = "TROP FAIBLE ";
		} else if (gradeValue < 7 && gradeValue >= 6) {
			grade = "TRES FAIBLE ";
		} else if (gradeValue < 8 && gradeValue >= 7) {
			grade = "FAIBLE / WEAK";
		} else if (gradeValue < 9 && gradeValue >= 8) {
			grade = "INSUFFISANT";
		} else if (gradeValue < 10 && gradeValue >= 9) {
			grade = "MEDIOCRE ";
		} else if (gradeValue < 12 && gradeValue >= 10) {
			grade = "PASSABLE / FAIR";
		} else if (gradeValue < 14 && gradeValue >= 12) {
			grade = "ASSEZ BIEN ";
		} else if (gradeValue < 16 && gradeValue >= 14) {
			grade = "BIEN ";
		} else if (gradeValue < 18 && gradeValue >= 16) {
			grade = "TRES BIEN ";
		} else if (gradeValue <= 20 && gradeValue >= 18) {
			grade = "EXCELLENT";
		}

		return grade;
	}

	@Override
	public String generateTranscriptNumber(String speciality, int transcriptNum) {
		String str = String.format("%05d", transcriptNum);
		String matricule = null;
		if (speciality != null && speciality.equals("MAE"))
			matricule = "TM" + str;
		if (speciality != null && speciality.equals("IPCSR"))
			matricule = "TI" + str;
		if (speciality != null && speciality.equals("DPCSR"))
			matricule = "TD" + str;

		return matricule;
	}

	@Override
	public String generateProfessionalCardNumber(String speciality, int certificateNum) {
		String str = String.format("%05d", certificateNum);
		String matricule = null;
		if (speciality != null && speciality.equals("MAE"))
			matricule = "PCM" + str;
		if (speciality != null && speciality.equals("IPCSR"))
			matricule = "PCI" + str;
		if (speciality != null && speciality.equals("DPCSR"))
			matricule = "PCD" + str;

		return matricule;
	}

	public void headersPresentPdf(Document document, PdfWriter writer, boolean isLandScape)
			throws IOException, DocumentException {
		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 14);
		Font headFont1 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 8);
		Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);
		Font headFont4 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 5);

		if (isLandScape) {
			Image image = Image.getInstance(codeArms + "cameroun.jpg");
			image.setAbsolutePosition(390f, 530f);
			image.scaleAbsolute(60f, 60f);
			document.add(image);
			String text1 = "REPUBLIQUE DU CAMEROUN";
			com.itextpdf.text.pdf.PdfContentByte canvas = writer.getDirectContent();
			Phrase phrase1 = new Phrase(text1, headFont2);
			ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase1, 27, 580, 0);
			String text2 = "PAIX-TRAVAIL-PATRIE";
			com.itextpdf.text.pdf.PdfContentByte canvas1 = writer.getDirectContent();
			Phrase phrase2 = new Phrase(text2, headFont4);
			ColumnText.showTextAligned(canvas1, Element.ALIGN_LEFT, phrase2, 60, 572, 0);
			String text9 = "------------------";
			com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
			Phrase phrase10 = new Phrase(text9, headFont4);
			ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase10, 70, 567, 0);
			String text3 = "MINISTERE DES TRANSPORTS";
			com.itextpdf.text.pdf.PdfContentByte canvas2 = writer.getDirectContent();
			Phrase phrase3 = new Phrase(text3, headFont2);
			ColumnText.showTextAligned(canvas2, Element.ALIGN_LEFT, phrase3, 27, 560, 0);

			com.itextpdf.text.pdf.PdfContentByte canvas11 = writer.getDirectContent();
			Phrase phrase12 = new Phrase(text9, headFont4);
			ColumnText.showTextAligned(canvas11, Element.ALIGN_LEFT, phrase12, 70, 555, 0);

			String text15 = "DIRECTION DES TRANSPORTS ROUTIERS";
			com.itextpdf.text.pdf.PdfContentByte canvas15 = writer.getDirectContent();
			Phrase phrase15 = new Phrase(text15, headFont2);
			ColumnText.showTextAligned(canvas15, Element.ALIGN_LEFT, phrase15, 8, 548, 0);

			String text4 = "REPUBLIC OF CAMEROON";
			com.itextpdf.text.pdf.PdfContentByte canvas3 = writer.getDirectContent();
			Phrase phrase4 = new Phrase(text4, headFont2);
			ColumnText.showTextAligned(canvas3, Element.ALIGN_RIGHT, phrase4, 805, 580, 0);
			String text5 = "PEACE-WORK-FATHERLAND";
			com.itextpdf.text.pdf.PdfContentByte canvas4 = writer.getDirectContent();
			Phrase phrase5 = new Phrase(text5, headFont4);
			ColumnText.showTextAligned(canvas4, Element.ALIGN_RIGHT, phrase5, 785, 572, 0);
			String text10 = "------------------";
			com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
			Phrase phrase11 = new Phrase(text10, headFont4);
			ColumnText.showTextAligned(canvas10, Element.ALIGN_RIGHT, phrase11, 765, 567, 0);
			String text6 = "MINISTRY OF TRANSPORT";
			com.itextpdf.text.pdf.PdfContentByte canvas5 = writer.getDirectContent();
			Phrase phrase6 = new Phrase(text6, headFont2);
			ColumnText.showTextAligned(canvas5, Element.ALIGN_RIGHT, phrase6, 805, 560, 0);

			com.itextpdf.text.pdf.PdfContentByte canvas13 = writer.getDirectContent();
			Phrase phrase13 = new Phrase(text10, headFont4);
			ColumnText.showTextAligned(canvas13, Element.ALIGN_LEFT, phrase13, 735, 555, 0);

			String text26 = "DEPARTMENT OF ROAD TRANSPORT";
			com.itextpdf.text.pdf.PdfContentByte canvas26 = writer.getDirectContent();
			Phrase phrase26 = new Phrase(text26, headFont2);
			ColumnText.showTextAligned(canvas26, Element.ALIGN_RIGHT, phrase26, 825, 548, 0);
		} else {

			Image image = Image.getInstance(codeArms + "cameroun.jpg");
			image.setAbsolutePosition(300f, 760f);
			image.scaleAbsolute(60f, 60f);
			document.add(image);

			PdfContentByte cb = writer.getDirectContent();
			cb.saveState();
			cb.setColorStroke(BaseColor.BLACK);
			cb.rectangle(20, 735, 35, 77);
			cb.setLineDash(3, 2, 0);
			cb.stroke();
			cb.restoreState();
			String textTimbre = "TIMBRE FISCAL ICI ";

			com.itextpdf.text.pdf.PdfContentByte canvasTimbre = writer.getDirectContent();
			Phrase phraseTimbre = new Phrase(textTimbre, headFont2);

			ColumnText.showTextAligned(canvasTimbre, Element.ALIGN_LEFT, phraseTimbre, 31, 810, -90);

			String text1 = "REPUBLIQUE DU CAMEROUN";
			com.itextpdf.text.pdf.PdfContentByte canvas = writer.getDirectContent();
			Phrase phrase1 = new Phrase(text1, headFont2);
			ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase1, 105, 810, 0);
			String text2 = "PAIX-TRAVAIL-PATRIE";
			com.itextpdf.text.pdf.PdfContentByte canvas1 = writer.getDirectContent();
			Phrase phrase2 = new Phrase(text2, headFont4);
			ColumnText.showTextAligned(canvas1, Element.ALIGN_LEFT, phrase2, 130, 802, 0);
			String text9 = "------------------";
			com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
			Phrase phrase10 = new Phrase(text9, headFont4);
			ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase10, 140, 797, 0);
			String text3 = "MINISTERE DES TRANSPORTS";
			com.itextpdf.text.pdf.PdfContentByte canvas2 = writer.getDirectContent();
			Phrase phrase3 = new Phrase(text3, headFont2);
			ColumnText.showTextAligned(canvas2, Element.ALIGN_LEFT, phrase3, 105, 790, 0);

			String text1000 = "------------------";
			com.itextpdf.text.pdf.PdfContentByte canvas1000 = writer.getDirectContent();
			Phrase phrase1100 = new Phrase(text1000, headFont4);
			ColumnText.showTextAligned(canvas1000, Element.ALIGN_RIGHT, phrase1100, 170, 785, 0);
			String text600 = "SECRETARAIT GENERAL";
			com.itextpdf.text.pdf.PdfContentByte canvas500 = writer.getDirectContent();
			Phrase phrase600 = new Phrase(text600, headFont2);
			ColumnText.showTextAligned(canvas500, Element.ALIGN_RIGHT, phrase600, 210, 775, 0);

			com.itextpdf.text.pdf.PdfContentByte canvas11 = writer.getDirectContent();
			Phrase phrase12 = new Phrase(text9, headFont4);
			ColumnText.showTextAligned(canvas11, Element.ALIGN_LEFT, phrase12, 140, 765, 0);

			String text15 = "DIRECTION DES TRANSPORTS ROUTIERS";
			com.itextpdf.text.pdf.PdfContentByte canvas15 = writer.getDirectContent();
			Phrase phrase15 = new Phrase(text15, headFont2);
			ColumnText.showTextAligned(canvas15, Element.ALIGN_LEFT, phrase15, 100, 758, 0);

			String text4 = "REPUBLIC OF CAMEROON";
			com.itextpdf.text.pdf.PdfContentByte canvas3 = writer.getDirectContent();
			Phrase phrase4 = new Phrase(text4, headFont2);
			ColumnText.showTextAligned(canvas3, Element.ALIGN_RIGHT, phrase4, 535, 810, 0);
			String text5 = "PEACE-WORK-FATHERLAND";
			com.itextpdf.text.pdf.PdfContentByte canvas4 = writer.getDirectContent();
			Phrase phrase5 = new Phrase(text5, headFont4);
			ColumnText.showTextAligned(canvas4, Element.ALIGN_RIGHT, phrase5, 515, 802, 0);
			String text10 = "------------------";
			com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
			Phrase phrase11 = new Phrase(text10, headFont4);
			ColumnText.showTextAligned(canvas10, Element.ALIGN_RIGHT, phrase11, 495, 797, 0);
			String text6 = "MINISTRY OF TRANSPORT";
			com.itextpdf.text.pdf.PdfContentByte canvas5 = writer.getDirectContent();
			Phrase phrase6 = new Phrase(text6, headFont2);
			ColumnText.showTextAligned(canvas5, Element.ALIGN_RIGHT, phrase6, 535, 790, 0);

			String text100 = "------------------";
			com.itextpdf.text.pdf.PdfContentByte canvas100 = writer.getDirectContent();
			Phrase phrase110 = new Phrase(text100, headFont4);
			ColumnText.showTextAligned(canvas100, Element.ALIGN_RIGHT, phrase110, 495, 785, 0);
			String text60 = "SECRETARAIT GENERAL";
			com.itextpdf.text.pdf.PdfContentByte canvas50 = writer.getDirectContent();
			Phrase phrase60 = new Phrase(text60, headFont2);
			ColumnText.showTextAligned(canvas50, Element.ALIGN_RIGHT, phrase60, 530, 775, 0);

			com.itextpdf.text.pdf.PdfContentByte canvas13 = writer.getDirectContent();
			Phrase phrase13 = new Phrase(text10, headFont4);
			ColumnText.showTextAligned(canvas13, Element.ALIGN_LEFT, phrase13, 465, 765, 0);

			String text26 = "DEPARTMENT OF ROAD TRANSPORT";
			com.itextpdf.text.pdf.PdfContentByte canvas26 = writer.getDirectContent();
			Phrase phrase26 = new Phrase(text26, headFont2);
			ColumnText.showTextAligned(canvas26, Element.ALIGN_RIGHT, phrase26, 560, 758, 0);

		}

	}

	public void ficheInscriptionPrintedPdf(ByteArrayOutputStream outStream, HttpHeaders headers, Document document,
			PdfWriter writer, int studentSessionId) throws DocumentException, IOException {
		Font headFont = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 8);
		Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 7);
		Font headFont4 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 5);
		Font headFont5 = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 10, new BaseColor(0, 105, 124));
		Font headFont6 = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 7);
		StudentSession studentSession = studentSessionService.findById(studentSessionId);


		String speciality="expert";
		if(studentSession.getSpeciality().getAbbreviation()=="MAE")
			speciality="moniteur";

		String diplome = studentSession.getStudent().getDiplome().trim();
		SimtLicenseChecker result = eligibilityChecher
				.check(studentSession.getStudent().getPerson().getLicenseNum().trim(), speciality, diplome);
		String all_categories = result.getCategories();

		// Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		// String catBDate = formatter.format(result.getCatB_Date());

		com.itextpdf.text.pdf.PdfContentByte timbre1 = writer.getDirectContent();
		timbre1.saveState();
		timbre1.setColorStroke(BaseColor.BLACK);
		timbre1.rectangle(8, 781, 75, 30);
		// rec1.roundRectangle(35, 450, 260,350,10);
		timbre1.setLineDash(3, 2, 0);
		timbre1.stroke();
		timbre1.restoreState();
		String timbreText1 = "TIMBRE FISCAL ICI ";
		com.itextpdf.text.pdf.PdfContentByte canvas = writer.getDirectContent();
		Phrase timbrePhrase1 = new Phrase(timbreText1, headFont6);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, timbrePhrase1, 12, 790, 0);

		com.itextpdf.text.pdf.PdfContentByte timbre2 = writer.getDirectContent();
		timbre2.saveState();
		timbre2.setColorStroke(BaseColor.BLACK);
		timbre2.rectangle(510, 781, 75, 30);
		// rec1.roundRectangle(35, 450, 260,350,10);
		timbre2.setLineDash(3, 2, 0);
		timbre2.stroke();
		timbre2.restoreState();
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, timbrePhrase1, 512, 790, 0);

		com.itextpdf.text.pdf.PdfContentByte rec1 = writer.getDirectContent();
		rec1.saveState();
		rec1.setColorStroke(BaseColor.BLACK);
		// recPaule.rectangle(35, 150, 520,250);
		rec1.roundRectangle(35, 450, 260, 350, 10);
		rec1.setLineDash(3, 2, 0);
		rec1.stroke();
		rec1.restoreState();
		SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");

		String text1 = "EXTRAIT DU PERMIS DE CONDUIRE ";
		Phrase phrase1 = new Phrase(text1, headFont5);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase1, 68, 780, 0);

		String text2 = "Je soussigné ....................................................................."
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase2 = new Phrase(text2, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase2, 45, 758, 0);

		String data2 = " ";
		canvas = writer.getDirectContent();
		Phrase dataphrase2 = new Phrase(data2, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase2, 105, 760, 0);

		String text3 = ".................................................................................................";
		canvas = writer.getDirectContent();
		Phrase phrase3 = new Phrase(text3, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase3, 45, 737, 0);

		String data3 = " ";
		canvas = writer.getDirectContent();
		Phrase dataphrase3 = new Phrase(data3, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase3, 70, 739, 0);

		String text4 = "Certifie que (Mlle, Mme, M.) ..........................................".toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase4 = new Phrase(text4, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase4, 45, 716, 0);

		String data4 = studentSession.getStudent().getPerson().getSurName().toUpperCase();
		canvas = writer.getDirectContent();
		Phrase dataphrase4 = new Phrase(data4, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase4, 190, 718, 0);

		String text5 = ".................................................................................................";
		canvas = writer.getDirectContent();
		Phrase phrase5 = new Phrase(text5, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase5, 45, 695, 0);

		String data5 = studentSession.getStudent().getPerson().getGivenName().toUpperCase();
		;
		canvas = writer.getDirectContent();
		Phrase dataphrase5 = new Phrase(data5, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase5, 45, 697, 0);

		String text6 = "Né(e) le :................................................................................"
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase6 = new Phrase(text6, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase6, 45, 674, 0);

		String data6 = DateFor.format(studentSession.getStudent().getPerson().getDob());
		canvas = writer.getDirectContent();
		Phrase dataphrase6 = new Phrase(data6, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase6, 90, 676, 0);

		String text7 = "à :............................................................................................"
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase7 = new Phrase(text7, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase7, 45, 653, 0);

		String data7 = studentSession.getStudent().getPerson().getPob().toUpperCase();
		if (data7 == null)
			data7 = "";
		canvas = writer.getDirectContent();
		Phrase dataphrase7 = new Phrase(data7, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase7, 90, 655, 0);

		String text8 = "Est titulaire du permis de conduire ".toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase8 = new Phrase(text8, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase8, 45, 632, 0);

		String text9 = "de la (des) catégorie(s)................................................".toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase9 = new Phrase(text9, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase9, 45, 611, 0);

		String data9 = all_categories;
		canvas = writer.getDirectContent();
		Phrase dataphrase9 = new Phrase(data9, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase9, 190, 613, 0);

		String text10 = "N° ...........................................................................................";
		canvas = writer.getDirectContent();
		Phrase phrase10 = new Phrase(text10, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase10, 45, 590, 0);

		String data10 = studentSession.getStudent().getPerson().getLicenseNum();
		if (data10 == null)
			data10 = "";
		canvas = writer.getDirectContent();
		Phrase dataphrase10 = new Phrase(data10, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase10, 100, 592, 0);

		String text11 = "Délivré le :.........................................................................."
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase11 = new Phrase(text11, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase11, 45, 569, 0);

		String data11 = " ";
		canvas = writer.getDirectContent();
		Phrase dataphrase11 = new Phrase(data11, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase11, 150, 571, 0);

		String text12 = "à :............................................................................................"
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase12 = new Phrase(text12, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase12, 45, 548, 0);

		String data12 = " ";
		canvas = writer.getDirectContent();
		Phrase dataphrase12 = new Phrase(data12, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase12, 100, 550, 0);

		String text13 = "Signature, noms et cachets de l'Autorité";
		canvas = writer.getDirectContent();
		Phrase phrase13 = new Phrase(text13, headFont3);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase13, 50, 507, 0);

		String text14 = "en charge des Transports";
		canvas = writer.getDirectContent();
		Phrase phrase14 = new Phrase(text14, headFont3);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase14, 65, 498, 0);

		com.itextpdf.text.pdf.PdfContentByte rec = writer.getDirectContent();
		rec.saveState();
		rec.setColorStroke(BaseColor.BLACK);
		// recPaule.rectangle(35, 150, 520,250);
		rec.roundRectangle(305, 450, 260, 350, 10);
		rec.setLineDash(3, 2, 0);
		rec.stroke();
		rec.restoreState();
		String text15 = "EXTRAIT DE LA CARTE NATIONALE D'IDENTITE ";
		Phrase phrase15 = new Phrase(text15, headFont5);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase15, 318, 780, 0);

		String text16 = "N° ...........................................................................................";
		canvas = writer.getDirectContent();
		Phrase phrase16 = new Phrase(text16, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase16, 316, 758, 0);

		String data16 = studentSession.getStudent().getPerson().getNic();
		if (data16 == null)
			data16 = "";
		canvas = writer.getDirectContent();
		Phrase dataphrase16 = new Phrase(data16, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase16, 356, 760, 0);

		String text17 = "Noms :..................................................................................."
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase17 = new Phrase(text17, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase17, 316, 737, 0);

		String data17 = studentSession.getStudent().getPerson().getSurnameCni().toUpperCase();
		if (data17 == null)
			data17 = "";
		canvas = writer.getDirectContent();
		Phrase dataphrase17 = new Phrase(data17, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase17, 366, 739, 0);

		String text18 = "Prénoms :............................................................................"
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase18 = new Phrase(text18, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase18, 316, 716, 0);

		String data18 = studentSession.getStudent().getPerson().getGivenNameCni().toUpperCase();
		if (data18 == null)
			data18 = "";
		canvas = writer.getDirectContent();
		Phrase dataphrase18 = new Phrase(data18, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase18, 370, 718, 0);

		String text19 = "Né(e) le :..............................à.............................................."
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase19 = new Phrase(text19, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase19, 316, 695, 0);

		String data019 = new String();
		if (studentSession.getStudent().getPerson().getDobCni() == null)
			data019 = "";
		else
			data019 = DateFor.format(studentSession.getStudent().getPerson().getDobCni());
		canvas = writer.getDirectContent();
		Phrase dataphrase019 = new Phrase(data019, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase019, 370, 697, 0);

		String data19 = studentSession.getStudent().getPerson().getPobCni();
		if (data19 == null)
			data19 = "";
		canvas = writer.getDirectContent();
		Phrase dataphrase19 = new Phrase(data19.toUpperCase(), headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase19, 450, 697, 0);

		String text20 = "Sexe :...................................................................................."
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase20 = new Phrase(text20, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase20, 316, 674, 0);

		String data20 = studentSession.getStudent().getPerson().getGenderCni();
		if (data20 == null)
			data20 = "";
		else {
			if (studentSession.getStudent().getPerson().getGenderCni().equals("M")) {
				data20 = "MASCULIN";
			} else {
				if (studentSession.getStudent().getPerson().getGenderCni().equals("F")) {
					data20 = "FEMININ";
				}
			}
		}
		canvas = writer.getDirectContent();
		Phrase dataphrase20 = new Phrase(data20, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase20, 370, 676, 0);

		String text21 = "Père :...................................................................................."
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase21 = new Phrase(text21, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase21, 316, 653, 0);

		String data21 = studentSession.getStudent().getPerson().getFatherCni();
		if (data21 == null)
			data21 = "";
		canvas = writer.getDirectContent();
		Phrase dataphrase21 = new Phrase(data21.toUpperCase(), headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase21, 370, 655, 0);

		String text22 = "Mère :...................................................................................."
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase22 = new Phrase(text22, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase22, 316, 632, 0);

		String data22 = studentSession.getStudent().getPerson().getMotherCni();
		if (data22 == null)
			data22 = "";
		canvas = writer.getDirectContent();
		Phrase dataphrase22 = new Phrase(data22.toUpperCase(), headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase22, 370, 634, 0);

		String text23 = "Profession :......................................................................"
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase23 = new Phrase(text23, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase23, 316, 611, 0);

		String data23 = studentSession.getStudent().getPerson().getProfessionCni();
		if (data23 == null)
			data23 = "";
		canvas = writer.getDirectContent();
		Phrase dataphrase23 = new Phrase(data23.toUpperCase(), headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase23, 380, 613, 0);

		String text24 = "Adresse :............................................................................"
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase24 = new Phrase(text24, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase24, 316, 590, 0);

		String data24 = studentSession.getStudent().getPerson().getAddressCni();
		if (data24 == null)
			data24 = "";
		canvas = writer.getDirectContent();
		Phrase dataphrase24 = new Phrase(data24.toUpperCase(), headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase24, 370, 592, 0);

		String text25 = "Taille :.................................................................................."
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase25 = new Phrase(text25, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase25, 316, 569, 0);

		String data25 = studentSession.getStudent().getPerson().getHeightCni();
		if (data25 == null)
			data25 = "";
		canvas = writer.getDirectContent();
		Phrase dataphrase25 = new Phrase(data25.toUpperCase(), headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase25, 370, 571, 0);

		String text26 = "Délivrée le ........................................................................"
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase26 = new Phrase(text26, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase26, 316, 548, 0);

		String data26 = DateFor.format(studentSession.getStudent().getPerson().getIssuedDateCni());
		if (data26 == null)
			data26 = "";
		canvas = writer.getDirectContent();
		Phrase dataphrase26 = new Phrase(data26, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase26, 380, 550, 0);

		String text27 = "Signature et empreinte du titulaire";
		canvas = writer.getDirectContent();
		Phrase phrase27 = new Phrase(text27, headFont3);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase27, 316, 527, 0);

		String text28 = "Signature, noms et cachets de l'Autorité";
		canvas = writer.getDirectContent();
		Phrase phrase28 = new Phrase(text28, headFont3);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase28, 425, 501, 0);

		// document.close();
		// headers.add("Content-Disposition", "inline;
		// filename=pdf-sample.pdf");
	}

	public void ficheInscriptionPrintedPdfEn(ByteArrayOutputStream outStream, HttpHeaders headers, Document document,
			PdfWriter writer, int studentSessionId) throws DocumentException, IOException {
		Font headFont = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 7);
		Font headFont5 = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 10, new BaseColor(0, 105, 124));
		Font headFont6 = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 7);
		StudentSession studentSession = studentSessionService.findById(studentSessionId);

		String speciality="expert";
		if(studentSession.getSpeciality().getAbbreviation()=="MAE")
			speciality="moniteur";

		String diplome = studentSession.getStudent().getDiplome().trim();
		SimtLicenseChecker result = eligibilityChecher
				.check(studentSession.getStudent().getPerson().getLicenseNum().trim(), speciality, diplome);
		String all_categories = result.getCategories();

		com.itextpdf.text.pdf.PdfContentByte timbre1 = writer.getDirectContent();
		timbre1.saveState();
		timbre1.setColorStroke(BaseColor.BLACK);
		timbre1.rectangle(8, 781, 75, 30);
		// rec1.roundRectangle(35, 450, 260,350,10);
		timbre1.setLineDash(3, 2, 0);
		timbre1.stroke();
		timbre1.restoreState();
		String timbreText1 = "TAX STAMP HERE ";
		com.itextpdf.text.pdf.PdfContentByte canvas = writer.getDirectContent();
		Phrase timbrePhrase1 = new Phrase(timbreText1, headFont6);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, timbrePhrase1, 12, 790, 0);

		com.itextpdf.text.pdf.PdfContentByte timbre2 = writer.getDirectContent();
		timbre2.saveState();
		timbre2.setColorStroke(BaseColor.BLACK);
		timbre2.rectangle(510, 781, 75, 30);
		// rec1.roundRectangle(35, 450, 260,350,10);
		timbre2.setLineDash(3, 2, 0);
		timbre2.stroke();
		timbre2.restoreState();
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, timbrePhrase1, 512, 790, 0);

		com.itextpdf.text.pdf.PdfContentByte rec1 = writer.getDirectContent();
		rec1.saveState();
		rec1.setColorStroke(BaseColor.BLACK);
		// recPaule.rectangle(35, 150, 520,250);
		rec1.roundRectangle(35, 450, 260, 350, 10);
		rec1.setLineDash(3, 2, 0);
		rec1.stroke();
		rec1.restoreState();
		SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");

		String text1 = "DRIVING LICENSE EXTRACT ";
		Phrase phrase1 = new Phrase(text1, headFont5);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase1, 78, 780, 0);

		String text2 = "I undersigned ...................................................................."
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase2 = new Phrase(text2, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase2, 45, 758, 0);

		String data2 = " ";
		canvas = writer.getDirectContent();
		Phrase dataphrase2 = new Phrase(data2, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase2, 105, 760, 0);

		String text3 = ".................................................................................................";
		canvas = writer.getDirectContent();
		Phrase phrase3 = new Phrase(text3, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase3, 45, 737, 0);

		String data3 = " ";
		canvas = writer.getDirectContent();
		Phrase dataphrase3 = new Phrase(data3, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase3, 70, 739, 0);

		String text4 = "Certifies that (Miss, Mrs., Mr.) ..................................".toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase4 = new Phrase(text4, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase4, 45, 716, 0);

		String data4 = studentSession.getStudent().getPerson().getSurName().toUpperCase();
		canvas = writer.getDirectContent();
		Phrase dataphrase4 = new Phrase(data4, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase4, 202, 718, 0);

		// data4="JEAN BERNARD";
		// String restSurname=null;
		// String initSurname=data4;
		// if(data4.length() <= 15 || (data4.length() > 15 && data4.contains("
		// ") && data4.split(" ")[0].length() <= 15)){
		// if(data4.length() > 15 && data4.contains(" ") && data4.split("
		// ")[0].length() <= 15){
		//
		// restSurname =initSurname.substring(initSurname.split("
		// ")[0].length()+1, initSurname.length()-1);
		// data4=data4.split(" ")[0];
		//
		// }
		// canvas = writer.getDirectContent();
		// Phrase dataphrase4 = new Phrase(data4, headFont);
		// ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
		// dataphrase4,202, 718, 0);
		// }

		String text5 = ".................................................................................................";
		canvas = writer.getDirectContent();
		Phrase phrase5 = new Phrase(text5, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase5, 45, 695, 0);

		String data5 = studentSession.getStudent().getPerson().getGivenName().toUpperCase();
		if (data5 == null)
			data5 = "";

		// if((initSurname.length() > 15 && data5 != null &&
		// !initSurname.contains(" ") ) ||(initSurname.length() > 15 &&
		// initSurname.contains(" ") && initSurname.split(" ")[0].length() <=
		// 15)){
		// if(initSurname.length() > 15 && data5 != null &&
		// !initSurname.contains(" ")){
		// data5=initSurname+" "+data5;
		//
		// }else if(initSurname.length() > 15 && initSurname.contains(" ") &&
		// initSurname.split(" ")[0].length() <= 15){
		// if(restSurname!=null)
		// data5=restSurname+" "+data5;
		// }
		// }else{
		// data5=initSurname+" "+data5;
		// }

		canvas = writer.getDirectContent();
		Phrase dataphrase5 = new Phrase(data5, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase5, 50, 697, 0);

		String text6 = "Born on :.............................................................................."
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase6 = new Phrase(text6, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase6, 45, 674, 0);

		String data6 = DateFor.format(studentSession.getStudent().getPerson().getDob());
		canvas = writer.getDirectContent();
		Phrase dataphrase6 = new Phrase(data6, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase6, 95, 676, 0);

		String text7 = "At :..........................................................................................."
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase7 = new Phrase(text7, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase7, 45, 653, 0);

		String data7 = studentSession.getStudent().getPerson().getPob().toUpperCase();
		canvas = writer.getDirectContent();
		Phrase dataphrase7 = new Phrase(data7, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase7, 90, 655, 0);

		String text8 = "Holds a driving license of the category (ies)".toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase8 = new Phrase(text8, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase8, 45, 632, 0);

		String text9 = ".................................................................................................";
		canvas = writer.getDirectContent();
		Phrase phrase9 = new Phrase(text9, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase9, 45, 611, 0);

		String data9 = all_categories;
		canvas = writer.getDirectContent();
		Phrase dataphrase9 = new Phrase(data9, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase9, 90, 613, 0);

		String text10 = "No :..........................................................................................";
		canvas = writer.getDirectContent();
		Phrase phrase10 = new Phrase(text10, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase10, 45, 590, 0);

		String data10 = studentSession.getStudent().getPerson().getLicenseNum().toUpperCase();
		canvas = writer.getDirectContent();
		Phrase dataphrase10 = new Phrase(data10, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase10, 100, 592, 0);

		String text11 = "Issued on :..........................................................................."
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase11 = new Phrase(text11, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase11, 45, 569, 0);

		String data11 = " ";
		canvas = writer.getDirectContent();
		Phrase dataphrase11 = new Phrase(data11, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase11, 150, 571, 0);

		String text12 = "At :.........................................................................................."
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase12 = new Phrase(text12, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase12, 45, 548, 0);

		String data12 = " ";
		canvas = writer.getDirectContent();
		Phrase dataphrase12 = new Phrase(data12, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase12, 100, 550, 0);

		String text13 = "Signature, names and stamps of the Authority";
		canvas = writer.getDirectContent();
		Phrase phrase13 = new Phrase(text13, headFont3);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase13, 50, 507, 0);

		String text14 = "in charge of transport";
		canvas = writer.getDirectContent();
		Phrase phrase14 = new Phrase(text14, headFont3);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase14, 65, 498, 0);

		com.itextpdf.text.pdf.PdfContentByte rec = writer.getDirectContent();
		rec.saveState();
		rec.setColorStroke(BaseColor.BLACK);
		// recPaule.rectangle(35, 150, 520,250);
		rec.roundRectangle(305, 450, 260, 350, 10);
		rec.setLineDash(3, 2, 0);
		rec.stroke();
		rec.restoreState();
		String text15 = "EXTRACT FROM THE NATIONAL IDENTITY CARD ";
		Phrase phrase15 = new Phrase(text15, headFont5);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase15, 318, 780, 0);

		String text16 = "No. :.........................................................................................";
		canvas = writer.getDirectContent();
		Phrase phrase16 = new Phrase(text16, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase16, 316, 758, 0);

		String data16 = studentSession.getStudent().getPerson().getNic();
		if (data16 == null)
			data16 = "";
		canvas = writer.getDirectContent();
		Phrase dataphrase16 = new Phrase(data16, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase16, 356, 760, 0);

		String text17 = "Surnames :.........................................................................."
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase17 = new Phrase(text17, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase17, 316, 737, 0);

		String data17 = studentSession.getStudent().getPerson().getSurnameCni().toUpperCase();
		if (data17 == null)
			data17 = "";
		canvas = writer.getDirectContent();
		Phrase dataphrase17 = new Phrase(data17, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase17, 375, 739, 0);

		String text18 = "Given names :......................................................................"
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase18 = new Phrase(text18, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase18, 316, 716, 0);

		String data18 = studentSession.getStudent().getPerson().getGivenNameCni().toUpperCase();
		if (data18 == null)
			data18 = "";
		canvas = writer.getDirectContent();
		Phrase dataphrase18 = new Phrase(data18, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase18, 385, 718, 0);

		String text19 = "Born on :.............................At............................................"
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase19 = new Phrase(text19, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase19, 316, 695, 0);

		String data019 = "";
		if (studentSession.getStudent().getPerson().getDobCni() == null)
			data019 = "";
		else
			data019 = DateFor.format(studentSession.getStudent().getPerson().getDobCni());
		canvas = writer.getDirectContent();
		Phrase dataphrase019 = new Phrase(data019, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase019, 370, 697, 0);

		String data19 = studentSession.getStudent().getPerson().getPobCni().toUpperCase();
		if (data19 == null)
			data19 = "";

		canvas = writer.getDirectContent();
		Phrase dataphrase19 = new Phrase(data19, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase19, 455, 697, 0);

		String text20 = "Sex :......................................................................................."
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase20 = new Phrase(text20, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase20, 316, 674, 0);

		String data20 = studentSession.getStudent().getPerson().getGenderCni();
		if (data20 == null)
			data20 = "";
		else {
			if (studentSession.getStudent().getPerson().getGenderCni().equals("M")) {
				data20 = "MALE";
			} else {
				if (studentSession.getStudent().getPerson().getGenderCni().equals("F")) {
					data20 = "FEMALE";
				}
			}
		}
		canvas = writer.getDirectContent();
		Phrase dataphrase20 = new Phrase(data20, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase20, 370, 676, 0);

		String text21 = "Father :................................................................................"
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase21 = new Phrase(text21, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase21, 316, 653, 0);

		String data21 = studentSession.getStudent().getPerson().getFatherCni().toUpperCase();
		if (data21 == null)
			data21 = "";
		canvas = writer.getDirectContent();
		Phrase dataphrase21 = new Phrase(data21, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase21, 370, 655, 0);

		String text22 = "Mother :.............................................................................."
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase22 = new Phrase(text22, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase22, 316, 632, 0);

		String data22 = studentSession.getStudent().getPerson().getMotherCni().toUpperCase();
		if (data22 == null)
			data22 = "";
		canvas = writer.getDirectContent();
		Phrase dataphrase22 = new Phrase(data22, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase22, 370, 634, 0);

		String text23 = "Occupation :......................................................................"
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase23 = new Phrase(text23, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase23, 316, 611, 0);

		String data23 = studentSession.getStudent().getPerson().getProfessionCni().toUpperCase();
		if (data23 == null)
			data23 = "";
		canvas = writer.getDirectContent();
		Phrase dataphrase23 = new Phrase(data23, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase23, 385, 613, 0);

		String text24 = "Address :............................................................................"
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase24 = new Phrase(text24, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase24, 316, 590, 0);

		String data24 = studentSession.getStudent().getPerson().getAddressCni().toUpperCase();
		if (data24 == null)
			data24 = "";
		canvas = writer.getDirectContent();
		Phrase dataphrase24 = new Phrase(data24, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase24, 370, 592, 0);

		String text25 = "Height :................................................................................"
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase25 = new Phrase(text25, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase25, 316, 569, 0);

		String data25 = studentSession.getStudent().getPerson().getHeightCni().toUpperCase();
		if (data25 == null)
			data25 = "";
		canvas = writer.getDirectContent();
		Phrase dataphrase25 = new Phrase(data25, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase25, 370, 571, 0);

		String text26 = "Issue on :............................................................................."
				.toUpperCase();
		canvas = writer.getDirectContent();
		Phrase phrase26 = new Phrase(text26, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase26, 316, 548, 0);

		String data26 = DateFor.format(studentSession.getStudent().getPerson().getIssuedDateCni());
		if (data26 == null)
			data26 = "";
		canvas = writer.getDirectContent();
		Phrase dataphrase26 = new Phrase(data26, headFont);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, dataphrase26, 370, 550, 0);

		String text27 = "Signature and imprint of the holder";
		canvas = writer.getDirectContent();
		Phrase phrase27 = new Phrase(text27, headFont3);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase27, 316, 527, 0);

		String text28 = "Signature, names and stamps of the Authority";
		canvas = writer.getDirectContent();
		Phrase phrase28 = new Phrase(text28, headFont3);
		ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase28, 425, 501, 0);

		// document.close();
		// headers.add("Content-Disposition", "inline;
		// filename=pdf-sample.pdf");
	}

	@Override
	public void printBlockRecuDroitsPdf(Document document, PdfWriter writer, ByteArrayOutputStream outStream,
			HttpHeaders headers, int studentSessionid) throws DocumentException, IOException {
		Font headFont = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		Font headFont1 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 7);
		Font headFontPaule = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 15, new BaseColor(0, 105, 124));
		Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);
		StudentSession studentSession = studentSessionService.findById(studentSessionid);

		ficheInscriptionPrintedPdf(outStream, headers, document, writer, studentSessionid);

		com.itextpdf.text.pdf.PdfContentByte recPaule = writer.getDirectContent();
		recPaule.saveState();
		recPaule.setColorStroke(BaseColor.BLACK);
		// recPaule.rectangle(35, 150, 520,250);
		recPaule.roundRectangle(35, 190, 530, 250, 10);
		recPaule.setLineDash(3, 2, 0);
		recPaule.stroke();
		recPaule.restoreState();

		String textPaule = "reçu des droits d'inscription ";
		com.itextpdf.text.pdf.PdfContentByte canvasPaule = writer.getDirectContent();
		Phrase phrasePaule = new Phrase(textPaule.toUpperCase(), headFontPaule);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 145, 420, 0);

		textPaule = "Je soussigné ................................................................................................................................................................................."
				.toUpperCase();
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule, headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 45, 390, 0);

		textPaule = studentSession.getStudent().getTrainingCenter().getOwner().toUpperCase();
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule.toUpperCase(), headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 135, 392, 0);

		textPaule = "Promoteur du centre de formation .................................................................................................................................."
				.toUpperCase();
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule, headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 45, 369, 0);

		textPaule = studentSession.getStudent().getTrainingCenter().getName().toUpperCase();
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule.toUpperCase(), headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 250, 371, 0);

		textPaule = "Atteste avoir perçu la somme de .............................................................Pénalité de..................................................."
				.toUpperCase();
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule, headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 45, 348, 0);

		textPaule = "40.000 FCFA";
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule.toUpperCase(), headFont);

	    ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 220, 350, 0);
		
		textPaule = "Représentant les droits d'inscription à l'examen du CAPEC - "+studentSession.getSpeciality().getAbbreviation()+", Session .....................................................".toUpperCase();

		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule, headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 45, 327, 0);

		SimpleDateFormat sessionDate = new SimpleDateFormat("MM/yyyy");
		String sessionDateFormatted = sessionDate
				.format(studentSession.getEligibleCenter().getExamSession().getSessionDate());
		textPaule = sessionDateFormatted;
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule.toUpperCase(), headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 430, 329, 0);

		textPaule = "Pour le compte de Mlle,Mme,M ............................................................................................................................................."
				.toUpperCase();

		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule, headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 45, 306, 0);

		if (studentSession.getStudent().getPerson().getSurnameCni() != null
				&& studentSession.getStudent().getPerson().getGivenNameCni() != null)
			textPaule = studentSession.getStudent().getPerson().getSurnameCni().toUpperCase() + " "
					+ studentSession.getStudent().getPerson().getGivenNameCni().toUpperCase();
		else if (studentSession.getStudent().getPerson().getSurnameCni() != null
				&& studentSession.getStudent().getPerson().getGivenNameCni() == null)
			textPaule = studentSession.getStudent().getPerson().getSurnameCni().toUpperCase();
		else
			textPaule = "EMPTY SURNAME AND EMPTY GIVEN  NAME";

		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule.toUpperCase(), headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 230, 308, 0);

		textPaule = "et lui ai délivré un reçu.".toUpperCase();
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule, headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 45, 285, 0);

		textPaule = "Fait à: ................................................ le ............................................................."
				.toUpperCase();
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule, headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 45, 264, 0);

		textPaule = "YAOUNDE";
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule, headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 79, 266, 0);

		SimpleDateFormat todayDate = new SimpleDateFormat("dd/MM/yyyy");
		String todayDateFormatted = todayDate.format(new java.util.Date());
		textPaule = todayDateFormatted;
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule.toUpperCase(), headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 219, 266, 0);

		textPaule = "Signature, noms et cachets du chef de Centre";
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule, headFont3);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 350, 243, 0);

	}

	@Override
	public void printStudentReceipt(Document document, PdfWriter writer, ByteArrayOutputStream outStream,
			HttpHeaders headers, int studentSessionId) throws DocumentException, IOException {
		StudentSession studentSession = studentSessionService.findById(studentSessionId);

		String speciality = null;

		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 14);
		Font headFont1 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 8);
		Font headFont5 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 8);
		Font headFont4 = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 8);

		// document.add(linebreak);
		String sectionBreak = "-----------------------------------------------------------";

		com.itextpdf.text.pdf.PdfContentByte canvas3 = writer.getDirectContent();
		Phrase phrase3 = new Phrase(sectionBreak + sectionBreak + sectionBreak, headFont1);
		ColumnText.showTextAligned(canvas3, Element.ALIGN_LEFT, phrase3, 20, 160, 0);

		// Print receipt header
		String receiptHeader = "PARTIE II: Récépissé ";
		String receiptHeader2 = "(A détacher et à remettre au candidat apres signature. Ce récépissé sera exigé a l'entrée de la salle de composition)";

		com.itextpdf.text.pdf.PdfContentByte canvas4 = writer.getDirectContent();
		Phrase phrase4 = new Phrase(receiptHeader, headFont1);
		ColumnText.showTextAligned(canvas4, Element.ALIGN_LEFT, phrase4, 20, 150, 0);

		com.itextpdf.text.pdf.PdfContentByte canvas5 = writer.getDirectContent();
		Phrase phrase5 = new Phrase(receiptHeader2, headFont2);
		ColumnText.showTextAligned(canvas5, Element.ALIGN_LEFT, phrase5, 110, 150, 0);
		com.itextpdf.text.pdf.PdfContentByte canvas6 = writer.getDirectContent();

		String text6 = "Noms et prénoms : .......................................................................................................................................";
		Phrase phrase6 = new Phrase(text6, headFont1);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase6, 20, 130, 0);

		String text7 = "Date et Lieu de Naissance : ...................................à.............................................. Session : ......................";
		Phrase phrase7 = new Phrase(text7, headFont1);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase7, 20, 110, 0);

		String text8 = "Etablissement de Formation : ................................................................... Région : .....................................";
		Phrase phrase8 = new Phrase(text8, headFont1);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase8, 20, 90, 0);

		String text9 = "Signature et cachet du Chef de Centre de Formation ";
		Phrase phrase9 = new Phrase(text9, headFont4);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase9, 25, 70, 0);

		String text10 = "Signature de l'autorité en charge des Transports ";
		Phrase phrase10 = new Phrase(text10, headFont4);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase10, 260, 70, 0);

		String text11 = "PHOTO 4*4 couleur, avec";
		Phrase phrase11 = new Phrase(text11, headFont5);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase11, 458, 115, 0);

		String text12 = "fond blanc, datant de moins";
		Phrase phrase12 = new Phrase(text12, headFont5);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase12, 457, 105, 0);

		String text13 = "de trois mois, meme photo";
		Phrase phrase13 = new Phrase(text13, headFont5);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase13, 458, 95, 0);

		String text14 = "que ci-dessus (coller)";
		Phrase phrase14 = new Phrase(text14, headFont5);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase14, 460, 85, 0);

		// print photo template
		com.itextpdf.text.pdf.PdfContentByte photoRectancle = writer.getDirectContent();
		photoRectancle.saveState();
		photoRectancle.setColorStroke(BaseColor.BLACK);
		photoRectancle.rectangle(450, 40, 100, 100);
		// recPaule.roundRectangle(35, 150, 520,250,10);
		photoRectancle.setLineDash(3, 2, 0);
		photoRectancle.stroke();
		photoRectancle.restoreState();

		// Now print person data to template
		com.itextpdf.text.pdf.PdfContentByte canvas12 = writer.getDirectContent();
		Phrase phrase15 = new Phrase(studentSession.getStudent().getPerson().getSurnameCni().toUpperCase() + " "
				+ studentSession.getStudent().getPerson().getGivenNameCni().toUpperCase(), headFont1);
		ColumnText.showTextAligned(canvas12, Element.ALIGN_LEFT, phrase15, 100, 132, 0);

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date dobDate = studentSession.getStudent().getPerson().getDobCni();
		String dobDateFinal = formatter.format(dobDate);
		Phrase phrase16 = new Phrase(dobDateFinal, headFont1);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase16, 140, 112, 0);
		Phrase phrase17 = new Phrase(studentSession.getStudent().getPerson().getPobCni().toUpperCase(), headFont1);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase17, 235, 112, 0);

		Date sessionDate = studentSession.getEligibleCenter().getExamSession().getSessionDate();
		String sessionDateFinal = formatter.format(sessionDate);
		Phrase phrase18 = new Phrase(sessionDateFinal, headFont1);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase18, 385, 112, 0);
		Phrase phrase19 = new Phrase(studentSession.getStudent().getTrainingCenter().getName().toUpperCase(),
				headFont1);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase19, 145, 92, 0);
		Phrase phrase20 = new Phrase(
				studentSession.getStudent().getTrainingCenter().getDivision().getRegion().getName(), headFont1);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase20, 360, 92, 0);

	}

	@Override
	public void printBlockRecuDroitsPdfEnglish(Document document, PdfWriter writer, ByteArrayOutputStream outStream,
			HttpHeaders headers, int studentSessionid) throws DocumentException, IOException {
		Font headFont = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		Font headFont1 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 7);
		Font headFontPaule = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 15, new BaseColor(0, 105, 124));
		Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);
		StudentSession studentSession = studentSessionService.findById(studentSessionid);

		ficheInscriptionPrintedPdfEn(outStream, headers, document, writer, studentSessionid);

		com.itextpdf.text.pdf.PdfContentByte recPaule = writer.getDirectContent();
		recPaule.saveState();
		recPaule.setColorStroke(BaseColor.BLACK);
		// recPaule.rectangle(35, 150, 520,250);
		recPaule.roundRectangle(35, 190, 530, 250, 10);
		recPaule.setLineDash(3, 2, 0);
		recPaule.stroke();
		recPaule.restoreState();

		String textPaule = "receipt for registration fees ";
		com.itextpdf.text.pdf.PdfContentByte canvasPaule = writer.getDirectContent();
		Phrase phrasePaule = new Phrase(textPaule.toUpperCase(), headFontPaule);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 145, 420, 0);

		textPaule = "I undersigned .............................................................................................................................................................................."
				.toUpperCase();
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule, headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 45, 390, 0);

		textPaule = studentSession.getStudent().getTrainingCenter().getOwner().toUpperCase();
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule.toUpperCase(), headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 145, 392, 0);

		textPaule = "Promotor of the training Center ......................................................................................................................................"
				.toUpperCase();
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule, headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 45, 369, 0);

		textPaule = studentSession.getStudent().getTrainingCenter().getName().toUpperCase();
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule.toUpperCase(), headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 225, 371, 0);

		textPaule = "Certifies having received the sum of .............................................................Penality of..........................................."
				.toUpperCase();
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule, headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 45, 348, 0);

		textPaule = "40.000 FCFA";
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule.toUpperCase(), headFont);

	    ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 245, 350, 0);
		
		textPaule = "Representing registration fees for the DSIPC - "+studentSession.getSpeciality().getAbbreviation()+" exam Session ....................................................................".toUpperCase();
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule, headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 45, 327, 0);

		SimpleDateFormat sessionDate = new SimpleDateFormat("MM/yyyy");
		String sessionDateFormatted = sessionDate
				.format(studentSession.getEligibleCenter().getExamSession().getSessionDate());
		textPaule = sessionDateFormatted;
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule.toUpperCase(), headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 445, 329, 0);

		textPaule = "On behalf of Miss, Mrs., M ......................................................................................................................................................."
				.toUpperCase();

		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule, headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 45, 306, 0);

		if (studentSession.getStudent().getPerson().getSurnameCni() != null
				&& studentSession.getStudent().getPerson().getGivenNameCni() != null)
			textPaule = studentSession.getStudent().getPerson().getSurnameCni().toUpperCase() + " "
					+ studentSession.getStudent().getPerson().getGivenNameCni().toUpperCase();
		else if (studentSession.getStudent().getPerson().getSurnameCni() != null
				&& studentSession.getStudent().getPerson().getGivenNameCni() == null)
			textPaule = studentSession.getStudent().getPerson().getSurnameCni().toUpperCase();
		else
			textPaule = "EMPTY SURNAME AND EMPTY GIVEN  NAME";

		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule.toUpperCase(), headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 190, 308, 0);

		textPaule = "and issued him a receipt.".toUpperCase();
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule, headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 45, 285, 0);

		textPaule = "Made in: ................................................ The ............................................................."
				.toUpperCase();
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule, headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 45, 264, 0);

		textPaule = "YAOUNDE";
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule, headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 90, 266, 0);

		SimpleDateFormat todayDate = new SimpleDateFormat("dd/MM/yyyy");
		String todayDateFormatted = todayDate.format(new java.util.Date());
		textPaule = todayDateFormatted;
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule.toUpperCase(), headFont);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 235, 266, 0);

		textPaule = "Signature, names and stamps of the head of the Center";
		canvasPaule = writer.getDirectContent();
		phrasePaule = new Phrase(textPaule, headFont3);
		ColumnText.showTextAligned(canvasPaule, Element.ALIGN_LEFT, phrasePaule, 350, 243, 0);

	}

	@Override
	public void printRegistrationFr(ByteArrayOutputStream outStream, HttpHeaders headers, int studentSessionid)
			throws DocumentException, IOException {
		// TODO Auto-generated method stub

		// Document opening
		Document document = null;
		document = new Document(PageSize.A4);
		document.setMargins(44, 42, 25, 24);
		PdfWriter writer = PdfWriter.getInstance(document, outStream);
		document.open();
		headersPresentPdf(document, writer, false);
		// code for background
		com.itextpdf.text.pdf.PdfContentByte canvas = writer.getDirectContentUnder();
		Image background = Image.getInstance(subscriptionBackgroundFolder + "ministere-transports.png");
		background.setAbsolutePosition(0, 0);
		background.scaleAbsolute(620, 690);
		canvas.saveState();
		com.itextpdf.text.pdf.PdfGState state = new com.itextpdf.text.pdf.PdfGState();
		state.setFillOpacity(0.3f);
		canvas.setGState(state);
		canvas.addImage(background);
		canvas.restoreState();

		// adding first page
		printRegistrationForm(document, writer, outStream, headers, studentSessionid);
		printStudentReceipt(document, writer, outStream, headers, studentSessionid);

		// adding second page
		document.newPage();
		com.itextpdf.text.pdf.PdfContentByte canvas2 = writer.getDirectContentUnder();
		Image backgroundPage2 = Image.getInstance(subscriptionBackgroundFolder + "ministere-transports.png");
		backgroundPage2.setAbsolutePosition(0, 0);
		backgroundPage2.scaleAbsolute(620, 690);
		canvas2.saveState();
		com.itextpdf.text.pdf.PdfGState state2 = new com.itextpdf.text.pdf.PdfGState();
		state2.setFillOpacity(0.3f);
		canvas2.setGState(state2);
		canvas2.addImage(backgroundPage2);
		canvas2.restoreState();
		printBlockRecuDroitsPdf(document, writer, outStream, headers, studentSessionid);
		// Document closing
		document.close();
		headers.add("Content-Disposition", "inline; filename=subscription.pdf");

	}

	@Override
	public void printRegistrationEn(ByteArrayOutputStream outStream, HttpHeaders headers, int studentSessionid)
			throws DocumentException, IOException {
		// TODO Auto-generated method stub

		// Document opening
		Document document = null;
		document = new Document(PageSize.A4);
		document.setMargins(44, 42, 25, 24);
		PdfWriter writer = PdfWriter.getInstance(document, outStream);
		document.open();
		headersPresentPdf(document, writer, false);

		// code for background
		com.itextpdf.text.pdf.PdfContentByte canvas = writer.getDirectContentUnder();
		Image background = Image.getInstance(subscriptionBackgroundFolder + "ministere-transports.png");
		background.setAbsolutePosition(0, 0);
		background.scaleAbsolute(620, 690);
		canvas.saveState();
		com.itextpdf.text.pdf.PdfGState state = new com.itextpdf.text.pdf.PdfGState();
		state.setFillOpacity(0.3f);
		canvas.setGState(state);
		canvas.addImage(background);
		canvas.restoreState();

		// adding first page
		enPrintRegistrationForm(document, writer, outStream, headers, studentSessionid);
		printStudentReceiptEn(document, writer, outStream, headers, studentSessionid);

		// adding second page
		document.newPage();
		com.itextpdf.text.pdf.PdfContentByte canvas2 = writer.getDirectContentUnder();
		Image backgroundPage2 = Image.getInstance(subscriptionBackgroundFolder + "ministere-transports.png");
		backgroundPage2.setAbsolutePosition(0, 0);
		backgroundPage2.scaleAbsolute(620, 690);
		canvas2.saveState();
		com.itextpdf.text.pdf.PdfGState state2 = new com.itextpdf.text.pdf.PdfGState();
		state2.setFillOpacity(0.3f);
		canvas2.setGState(state2);
		canvas2.addImage(backgroundPage2);
		canvas2.restoreState();
		printBlockRecuDroitsPdfEnglish(document, writer, outStream, headers, studentSessionid);
		// Document closing
		document.close();
		headers.add("Content-Disposition", "inline; filename=subscription.pdf");
	}

	public void enPrintRegistrationForm(Document document, PdfWriter writer, ByteArrayOutputStream outStream,
			HttpHeaders headers, int studentSessionId) throws DocumentException, IOException {
		StudentSession studentSession = studentSessionService.findById(studentSessionId);

		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 15);
		Font headFont1 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		Font headFont11 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		headFont11.setColor(0, 0, 80);
		;
		Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 8);
		Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);
		Font headFont4 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 5);
		Font headFont10 = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 9);
		Font headFontX = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 18);

		// int underline = headFont10.UNDERLINE;
		// l'entete du pdf

		PdfContentByte cb = writer.getDirectContent();
		cb.saveState();
		cb.setColorStroke(BaseColor.WHITE);
		cb.roundRectangle(40, 675, 525, 56, 10);
		cb.setColorFill(new BaseColor(220, 178, 83));
		cb.fillStroke();
		cb.restoreState();
		String textTimbre = "NATIONAL EXAMINATION OF THE PROFESSIONAL CERTIFICATE FOR  ";
		Color color = Color.GREEN;
		com.itextpdf.text.pdf.PdfContentByte canvasTimbre = writer.getDirectContent();
		Phrase phraseTimbre = new Phrase(textTimbre, headFont);
		ColumnText.showTextAligned(canvasTimbre, Element.ALIGN_LEFT, phraseTimbre, 43, 710, 0);
		String textHeader = "PROFESSIONAL APTITUDE IN THE TEACHING OF DRIVING MOTOR ";
		com.itextpdf.text.pdf.PdfContentByte canvasHeader = writer.getDirectContent();
		Phrase phraseHeader = new Phrase(textHeader, headFont);
		ColumnText.showTextAligned(canvasHeader, Element.ALIGN_LEFT, phraseHeader, 57, 695, 0);

		String textHeader1 = "VEHICLES (CAPEC-"+studentSession.getSpeciality().getAbbreviation()+")";

		com.itextpdf.text.pdf.PdfContentByte canvasHeader1 = writer.getDirectContent();
		Phrase phraseHeader1 = new Phrase(textHeader1, headFont);
		ColumnText.showTextAligned(canvasHeader1, Element.ALIGN_LEFT, phraseHeader1, 240, 680, 0);

		String textHeader2 = "SESSION REGISTRATION FORM : ";
		com.itextpdf.text.pdf.PdfContentByte canvasHeader2 = writer.getDirectContent();
		Phrase phraseHeader2 = new Phrase(textHeader2, headFont11);
		ColumnText.showTextAligned(canvasHeader2, Element.ALIGN_LEFT, phraseHeader2, 200, 660, 0);

		SimpleDateFormat sessionDate = new SimpleDateFormat("MM/yyyy");
		String sessionDateFormatted = sessionDate
				.format(studentSession.getEligibleCenter().getExamSession().getSessionDate());
		String text51 = " " + sessionDateFormatted;
		com.itextpdf.text.pdf.PdfContentByte canvasText51 = writer.getDirectContent();
		Phrase phraseText51 = new Phrase(text51, headFont1);
		ColumnText.showTextAligned(canvasText51, Element.ALIGN_LEFT, phraseText51, 345, 660, 0);

		String text1 = "TRAINING SCHOOL : " + studentSession.getStudent().getTrainingCenter().getName().toUpperCase();
		com.itextpdf.text.pdf.PdfContentByte canvasText1 = writer.getDirectContent();
		Phrase phraseText1 = new Phrase(text1, headFont1);
		ColumnText.showTextAligned(canvasText1, Element.ALIGN_LEFT, phraseText1, 30, 630, 0);

		String text3 = "REGION : "
				+ studentSession.getStudent().getTrainingCenter().getDivision().getRegion().getName().toUpperCase();
		com.itextpdf.text.pdf.PdfContentByte canvasText3 = writer.getDirectContent();
		Phrase phraseText3 = new Phrase(text3, headFont1);
		ColumnText.showTextAligned(canvasText3, Element.ALIGN_LEFT, phraseText3, 30, 615, 0);

		PdfContentByte pic = writer.getDirectContent();
		pic.saveState();
		pic.setColorStroke(BaseColor.BLACK);
		pic.rectangle(510, 612, 68, 60);
		pic.setLineDash(3, 2, 0);
		pic.stroke();
		pic.restoreState();

		String text4 = "color PHOTO 4X4, with white ";
		com.itextpdf.text.pdf.PdfContentByte canvasText4 = writer.getDirectContent();
		Phrase phraseText4 = new Phrase(text4, headFont4);
		ColumnText.showTextAligned(canvasText4, Element.ALIGN_LEFT, phraseText4, 512, 650, 0);
		String text5 = "background, dating under ";
		com.itextpdf.text.pdf.PdfContentByte canvasText5 = writer.getDirectContent();
		Phrase phraseText5 = new Phrase(text5, headFont4);
		ColumnText.showTextAligned(canvasText5, Element.ALIGN_LEFT, phraseText5, 515, 645, 0);
		String text6 = "three months old, same photo ";
		com.itextpdf.text.pdf.PdfContentByte canvasText6 = writer.getDirectContent();
		Phrase phraseText6 = new Phrase(text6, headFont4);
		ColumnText.showTextAligned(canvasText6, Element.ALIGN_LEFT, phraseText6, 512, 640, 0);
		String text7 = "than on the receipt ";
		com.itextpdf.text.pdf.PdfContentByte canvasText7 = writer.getDirectContent();
		Phrase phraseText7 = new Phrase(text7, headFont4);
		ColumnText.showTextAligned(canvasText7, Element.ALIGN_LEFT, phraseText7, 525, 635, 0);
		String text8 = "(paste)";
		com.itextpdf.text.pdf.PdfContentByte canvasText8 = writer.getDirectContent();
		Phrase phraseText8 = new Phrase(text8, headFont4);
		ColumnText.showTextAligned(canvasText8, Element.ALIGN_LEFT, phraseText8, 533, 630, 0);

		String text9 = "PART I : IDENTIFICATION OF THE CANDIDATE ";
		com.itextpdf.text.pdf.PdfContentByte canvasText9 = writer.getDirectContent();
		Phrase phraseText9 = new Phrase(text9, headFont10);
		ColumnText.showTextAligned(canvasText9, Element.ALIGN_LEFT, phraseText9, 30, 595, 0);

		String text10 = "1- LAST NAMES AND FIRST NAMES :";
		com.itextpdf.text.pdf.PdfContentByte canvasText10 = writer.getDirectContent();
		Phrase phraseText10 = new Phrase(text10, headFont1);
		ColumnText.showTextAligned(canvasText10, Element.ALIGN_LEFT, phraseText10, 30, 575, 0);

		String text13 = "....................................................................................................................................................";
		com.itextpdf.text.pdf.PdfContentByte canvasText13 = writer.getDirectContent();
		Phrase phraseText13 = new Phrase(text13, headFont1);
		ColumnText.showTextAligned(canvasText13, Element.ALIGN_LEFT, phraseText13, 183, 575, 0);

		String text14 = studentSession.getStudent().getPerson().getSurnameCni().toUpperCase() + " "
				+ studentSession.getStudent().getPerson().getGivenNameCni().toUpperCase();
		com.itextpdf.text.pdf.PdfContentByte canvasText14 = writer.getDirectContent();
		Phrase phraseText14 = new Phrase(text14, headFont1);
		ColumnText.showTextAligned(canvasText14, Element.ALIGN_LEFT, phraseText14, 190, 577, 0);

		String text11 = "2- DATE OF BIRTH : ";
		com.itextpdf.text.pdf.PdfContentByte canvasText11 = writer.getDirectContent();
		Phrase phraseText11 = new Phrase(text11, headFont1);
		ColumnText.showTextAligned(canvasText11, Element.ALIGN_LEFT, phraseText11, 30, 550, 0);
		String text15 = ".................................... ";
		com.itextpdf.text.pdf.PdfContentByte canvasText15 = writer.getDirectContent();
		Phrase phraseText15 = new Phrase(text15, headFont1);
		ColumnText.showTextAligned(canvasText15, Element.ALIGN_LEFT, phraseText15, 115, 550, 0);
		SimpleDateFormat dobDate = new SimpleDateFormat("dd/MM/yyyy");
		String dobDateFormatted = dobDate.format(studentSession.getStudent().getPerson().getDobCni());
		String text16 = "" + dobDateFormatted;
		com.itextpdf.text.pdf.PdfContentByte canvasText16 = writer.getDirectContent();
		Phrase phraseText16 = new Phrase(text16, headFont1);
		ColumnText.showTextAligned(canvasText16, Element.ALIGN_LEFT, phraseText16, 140, 552, 0);
		String text12 = "3- PLACE OF BIRTH :";
		com.itextpdf.text.pdf.PdfContentByte canvasText12 = writer.getDirectContent();
		Phrase phraseText12 = new Phrase(text12, headFont1);
		ColumnText.showTextAligned(canvasText12, Element.ALIGN_LEFT, phraseText12, 220, 550, 0);
		String text17 = "................................................................................................. ";
		com.itextpdf.text.pdf.PdfContentByte canvasText17 = writer.getDirectContent();
		Phrase phraseText17 = new Phrase(text17, headFont1);
		ColumnText.showTextAligned(canvasText17, Element.ALIGN_LEFT, phraseText17, 312, 550, 0);
		String text18 = studentSession.getStudent().getPerson().getPobCni().toUpperCase();
		com.itextpdf.text.pdf.PdfContentByte canvasText18 = writer.getDirectContent();
		Phrase phraseText18 = new Phrase(text18, headFont1);
		ColumnText.showTextAligned(canvasText18, Element.ALIGN_LEFT, phraseText18, 320, 552, 0);

		String text19 = "3- SEX :";
		com.itextpdf.text.pdf.PdfContentByte canvasText19 = writer.getDirectContent();
		Phrase phraseText19 = new Phrase(text19, headFont1);
		ColumnText.showTextAligned(canvasText19, Element.ALIGN_LEFT, phraseText19, 30, 530, 0);
		String text20 = "...............................";
		com.itextpdf.text.pdf.PdfContentByte canvasText20 = writer.getDirectContent();
		Phrase phraseText20 = new Phrase(text20, headFont1);
		ColumnText.showTextAligned(canvasText20, Element.ALIGN_LEFT, phraseText20, 65, 530, 0);
		if (studentSession.getStudent().getPerson().getGenderCni().equals("M")) {
			String text21 = "MALE";
			com.itextpdf.text.pdf.PdfContentByte canvasText21 = writer.getDirectContent();
			Phrase phraseText21 = new Phrase(text21, headFont1);
			ColumnText.showTextAligned(canvasText21, Element.ALIGN_LEFT, phraseText21, 77, 533, 0);
		} else {
			if (studentSession.getStudent().getPerson().getGenderCni().equals("F")) {
				String text21 = "FEMALE";
				com.itextpdf.text.pdf.PdfContentByte canvasText21 = writer.getDirectContent();
				Phrase phraseText21 = new Phrase(text21, headFont1);
				ColumnText.showTextAligned(canvasText21, Element.ALIGN_LEFT, phraseText21, 77, 533, 0);

			}
		}

		String text22 = "5- COMPOSITION LANGUAGE :";
		com.itextpdf.text.pdf.PdfContentByte canvasText22 = writer.getDirectContent();
		Phrase phraseText22 = new Phrase(text22, headFont1);
		ColumnText.showTextAligned(canvasText22, Element.ALIGN_LEFT, phraseText22, 150, 530, 0);
		String text23 = "FRENCH";
		com.itextpdf.text.pdf.PdfContentByte canvasText23 = writer.getDirectContent();
		Phrase phraseText23 = new Phrase(text23, headFont1);
		ColumnText.showTextAligned(canvasText23, Element.ALIGN_LEFT, phraseText23, 300, 530, 0);
		PdfContentByte frenchPic = writer.getDirectContent();
		frenchPic.saveState();
		frenchPic.setColorStroke(BaseColor.BLACK);
		frenchPic.rectangle(355, 525, 15, 15);
		frenchPic.stroke();
		frenchPic.restoreState();
		if (studentSession.getStudent().getPerson().getLanguage().equals("FR")) {
			PdfContentByte frenchPic1 = writer.getDirectContent();
			frenchPic1.saveState();
			frenchPic1.setColorStroke(BaseColor.BLACK);
			frenchPic1.rectangle(355, 525, 15, 15);
			frenchPic1.stroke();
			frenchPic1.restoreState();
			String text41 = "X";
			com.itextpdf.text.pdf.PdfContentByte canvasText41 = writer.getDirectContent();
			Phrase phraseText41 = new Phrase(text41, headFontX);
			ColumnText.showTextAligned(canvasText41, Element.ALIGN_LEFT, phraseText41, 357, 527, 0);
		}

		String text24 = "ENGLISH";
		com.itextpdf.text.pdf.PdfContentByte canvasText24 = writer.getDirectContent();
		Phrase phraseText24 = new Phrase(text24, headFont1);
		ColumnText.showTextAligned(canvasText24, Element.ALIGN_LEFT, phraseText24, 420, 530, 0);
		PdfContentByte englishPic = writer.getDirectContent();
		englishPic.saveState();
		englishPic.setColorStroke(BaseColor.BLACK);
		englishPic.rectangle(480, 525, 15, 15);
		englishPic.stroke();
		englishPic.restoreState();
		if (studentSession.getStudent().getPerson().getLanguage().equals("EN")) {
			PdfContentByte englishPic1 = writer.getDirectContent();
			englishPic1.saveState();
			englishPic1.setColorStroke(BaseColor.BLACK);
			englishPic1.rectangle(480, 525, 15, 15);
			englishPic1.stroke();
			englishPic1.restoreState();
			String text41 = "X";
			com.itextpdf.text.pdf.PdfContentByte canvasText41 = writer.getDirectContent();
			Phrase phraseText41 = new Phrase(text41, headFontX);
			ColumnText.showTextAligned(canvasText41, Element.ALIGN_LEFT, phraseText41, 481, 525, 0);
		}

		String text25 = "6- NATIONALITY :";
		com.itextpdf.text.pdf.PdfContentByte canvasText25 = writer.getDirectContent();
		Phrase phraseText25 = new Phrase(text25, headFont1);
		ColumnText.showTextAligned(canvasText25, Element.ALIGN_LEFT, phraseText25, 30, 500, 0);
		String text26 = "............................................";
		com.itextpdf.text.pdf.PdfContentByte canvasText26 = writer.getDirectContent();
		Phrase phraseText26 = new Phrase(text26, headFont1);
		ColumnText.showTextAligned(canvasText26, Element.ALIGN_LEFT, phraseText26, 106, 500, 0);
		String text27 = studentSession.getStudent().getPerson().getNationality().getCountryName().toUpperCase();
		com.itextpdf.text.pdf.PdfContentByte canvasText27 = writer.getDirectContent();
		Phrase phraseText27 = new Phrase(text27, headFont1);
		ColumnText.showTextAligned(canvasText27, Element.ALIGN_LEFT, phraseText27, 112, 502, 0);

		String text28 = "7- REGION OF ORIGIN :";
		com.itextpdf.text.pdf.PdfContentByte canvasText28 = writer.getDirectContent();
		Phrase phraseText28 = new Phrase(text28, headFont1);
		ColumnText.showTextAligned(canvasText28, Element.ALIGN_LEFT, phraseText28, 220, 500, 0);
		String text29 = "............................................................................................";
		com.itextpdf.text.pdf.PdfContentByte canvasText29 = writer.getDirectContent();
		Phrase phraseText29 = new Phrase(text29, headFont1);
		ColumnText.showTextAligned(canvasText29, Element.ALIGN_LEFT, phraseText29, 323, 500, 0);
		String text30 = "";
		com.itextpdf.text.pdf.PdfContentByte canvasText30 = writer.getDirectContent();
		Phrase phraseText30 = new Phrase(text30, headFont1);
		ColumnText.showTextAligned(canvasText30, Element.ALIGN_LEFT, phraseText30, 340, 502, 0);

		String text31 = "8- NATIONAL IDENTITY CARD No : ";
		com.itextpdf.text.pdf.PdfContentByte canvasText31 = writer.getDirectContent();
		Phrase phraseText31 = new Phrase(text31, headFont1);
		ColumnText.showTextAligned(canvasText31, Element.ALIGN_LEFT, phraseText31, 30, 470, 0);
		String text32 = ".......................................................";
		com.itextpdf.text.pdf.PdfContentByte canvasText32 = writer.getDirectContent();
		Phrase phraseText32 = new Phrase(text32, headFont1);
		ColumnText.showTextAligned(canvasText32, Element.ALIGN_LEFT, phraseText32, 178, 470, 0);
		String text33 = studentSession.getStudent().getPerson().getNic();
		com.itextpdf.text.pdf.PdfContentByte canvasText33 = writer.getDirectContent();
		Phrase phraseText33 = new Phrase(text33, headFont1);
		ColumnText.showTextAligned(canvasText33, Element.ALIGN_LEFT, phraseText33, 195, 472, 0);
		String text34 = "9- ISSUED ON : ";
		com.itextpdf.text.pdf.PdfContentByte canvasText34 = writer.getDirectContent();
		Phrase phraseText34 = new Phrase(text34, headFont1);
		ColumnText.showTextAligned(canvasText34, Element.ALIGN_LEFT, phraseText34, 320, 470, 0);
		String text35 = ".....................................................................";
		com.itextpdf.text.pdf.PdfContentByte canvasText35 = writer.getDirectContent();
		Phrase phraseText35 = new Phrase(text35, headFont1);
		ColumnText.showTextAligned(canvasText35, Element.ALIGN_LEFT, phraseText35, 386, 470, 0);
		SimpleDateFormat issuedDate = new SimpleDateFormat("dd/MM/yyyy");
		String issuedDateFormatted = issuedDate.format(studentSession.getStudent().getPerson().getIssuedDateCni());
		String text36 = " " + issuedDateFormatted;
		com.itextpdf.text.pdf.PdfContentByte canvasText36 = writer.getDirectContent();
		Phrase phraseText36 = new Phrase(text36, headFont1);
		ColumnText.showTextAligned(canvasText36, Element.ALIGN_LEFT, phraseText36, 400, 472, 0);

		document.add(Chunk.NEWLINE);
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 6, 6, 6, 6 });
		table.setSpacingBefore(350);
		table.setSpacingAfter(10);

		PdfPCell c1 = new PdfPCell(new Phrase(
				"HIGHEST ACADEMIC DIPLOMA" + "(Attach a certified copy of diploma to this form)", headFont11));
		c1.setColspan(4);
		c1.setFixedHeight(15f);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		String text50 = "";
		Phrase phrase30 = new Phrase(text30, headFont3);
		document.add(phrase30);

		c1 = new PdfPCell(new Phrase("TITLE OF DIPLOMA", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setFixedHeight(15f);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase(studentSession.getStudentQualification().getEntryCertificateName().toUpperCase(),
				headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("SERIES/OPTION/SPECIALITY", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(
				new Phrase(studentSession.getStudentQualification().getDiplomeOption().toUpperCase(), headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("EXAM SESSION", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setFixedHeight(15f);
		table.addCell(c1);
		SimpleDateFormat issuedExamDate = new SimpleDateFormat("dd/MM/yyyy");
		String issuedExamDateFormatted = issuedExamDate
				.format(studentSession.getStudentQualification().getIssuedDate());

		c1 = new PdfPCell(new Phrase(issuedExamDateFormatted, headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("EXAM PLACE", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(
				new Phrase(studentSession.getStudentQualification().getIssuedPlace().toUpperCase(), headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		table.setHeaderRows(1);

		document.add(table);

		String text37 = "Done at :................................. on :............................ ";
		com.itextpdf.text.pdf.PdfContentByte canvasText37 = writer.getDirectContent();
		Phrase phraseText37 = new Phrase(text37, headFont1);
		ColumnText.showTextAligned(canvasText37, Element.ALIGN_LEFT, phraseText37, 355, 340, 0);

		String text38 = "Signature of the authority in charge of transport";
		com.itextpdf.text.pdf.PdfContentByte canvasText38 = writer.getDirectContent();
		Phrase phraseText38 = new Phrase(text38, headFont1);
		ColumnText.showTextAligned(canvasText38, Element.ALIGN_LEFT, phraseText38, 220, 300, 0);

	}

	public void printRegistrationForm(Document document, PdfWriter writer, ByteArrayOutputStream outStream,
			HttpHeaders headers, int studentSessionId) throws DocumentException, IOException {
		StudentSession studentSession = studentSessionService.findById(studentSessionId);

		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 15);
		Font headFont1 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		Font headFont11 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		headFont11.setColor(0, 0, 80);
		;
		Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 8);
		Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);
		Font headFont4 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 5);
		Font headFont10 = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 9);
		Font headFontX = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 18);
		int underline = headFont10.UNDERLINE;

		PdfContentByte cb = writer.getDirectContent();
		cb.saveState();
		cb.setColorStroke(BaseColor.WHITE);
		cb.roundRectangle(40, 675, 525, 55, 10);
		cb.setColorFill(new BaseColor(220, 178, 83));
		cb.fillStroke();
		cb.restoreState();
		String textTimbre = "EXAMEN NATIONAL DU CERTIFICAT D'APTITUDE PROFESSIONELLE  ";
		Color color = Color.GREEN;
		com.itextpdf.text.pdf.PdfContentByte canvasTimbre = writer.getDirectContent();
		Phrase phraseTimbre = new Phrase(textTimbre, headFont);
		ColumnText.showTextAligned(canvasTimbre, Element.ALIGN_LEFT, phraseTimbre, 47, 710, 0);
		String textHeader = "A L'ENSEIGNEMENT DE LA CONDUITE DES VEHICULES A MOTEUR";
		com.itextpdf.text.pdf.PdfContentByte canvasHeader = writer.getDirectContent();
		Phrase phraseHeader = new Phrase(textHeader, headFont);
		ColumnText.showTextAligned(canvasHeader, Element.ALIGN_LEFT, phraseHeader, 57, 695, 0);

		String textHeader1 = "(CAPEC-"+studentSession.getSpeciality().getAbbreviation()+")";
		com.itextpdf.text.pdf.PdfContentByte canvasHeader1 = writer.getDirectContent();
		Phrase phraseHeader1 = new Phrase(textHeader1, headFont);
		ColumnText.showTextAligned(canvasHeader1, Element.ALIGN_LEFT, phraseHeader1, 240, 680, 0);

		String textHeader2 = "FICHE D'INSCRIPTION à LA SESSION : ".toUpperCase();
		com.itextpdf.text.pdf.PdfContentByte canvasHeader2 = writer.getDirectContent();
		Phrase phraseHeader2 = new Phrase(textHeader2, headFont11);
		ColumnText.showTextAligned(canvasHeader2, Element.ALIGN_LEFT, phraseHeader2, 200, 660, 0);
		SimpleDateFormat sessionDate = new SimpleDateFormat("MM/yyyy");
		String sessionDateFormatted = sessionDate
				.format(studentSession.getEligibleCenter().getExamSession().getSessionDate());
		String text51 = " " + sessionDateFormatted;
		com.itextpdf.text.pdf.PdfContentByte canvasText51 = writer.getDirectContent();
		Phrase phraseText51 = new Phrase(text51, headFont1);
		ColumnText.showTextAligned(canvasText51, Element.ALIGN_LEFT, phraseText51, 370, 660, 0);

		String text1 = "ÉTABLISSEMENT DE FORMATION : ".toUpperCase()
				+ studentSession.getStudent().getTrainingCenter().getName().toUpperCase();
		com.itextpdf.text.pdf.PdfContentByte canvasText1 = writer.getDirectContent();
		Phrase phraseText1 = new Phrase(text1, headFont1);
		ColumnText.showTextAligned(canvasText1, Element.ALIGN_LEFT, phraseText1, 30, 630, 0);

		String text3 = "RÉGION : ".toUpperCase()
				+ studentSession.getStudent().getTrainingCenter().getDivision().getRegion().getName().toUpperCase();
		com.itextpdf.text.pdf.PdfContentByte canvasText3 = writer.getDirectContent();
		Phrase phraseText3 = new Phrase(text3, headFont1);
		ColumnText.showTextAligned(canvasText3, Element.ALIGN_LEFT, phraseText3, 30, 615, 0);

		PdfContentByte pic = writer.getDirectContent();
		pic.saveState();
		pic.setColorStroke(BaseColor.BLACK);
		pic.rectangle(510, 612, 68, 60);
		pic.setLineDash(3, 2, 0);
		pic.stroke();
		pic.restoreState();

		String text4 = "PHOTO 4X4 couleure, avec ";
		com.itextpdf.text.pdf.PdfContentByte canvasText4 = writer.getDirectContent();
		Phrase phraseText4 = new Phrase(text4, headFont4);
		ColumnText.showTextAligned(canvasText4, Element.ALIGN_LEFT, phraseText4, 515, 650, 0);
		String text5 = "fond blanc, datant de moins ";
		com.itextpdf.text.pdf.PdfContentByte canvasText5 = writer.getDirectContent();
		Phrase phraseText5 = new Phrase(text5, headFont4);
		ColumnText.showTextAligned(canvasText5, Element.ALIGN_LEFT, phraseText5, 515, 645, 0);
		String text6 = "de trois mois, même photo ";
		com.itextpdf.text.pdf.PdfContentByte canvasText6 = writer.getDirectContent();
		Phrase phraseText6 = new Phrase(text6, headFont4);
		ColumnText.showTextAligned(canvasText6, Element.ALIGN_LEFT, phraseText6, 515, 640, 0);
		String text7 = "que sur le recepissé ";
		com.itextpdf.text.pdf.PdfContentByte canvasText7 = writer.getDirectContent();
		Phrase phraseText7 = new Phrase(text7, headFont4);
		ColumnText.showTextAligned(canvasText7, Element.ALIGN_LEFT, phraseText7, 525, 635, 0);
		String text8 = "(coller)";
		com.itextpdf.text.pdf.PdfContentByte canvasText8 = writer.getDirectContent();
		Phrase phraseText8 = new Phrase(text8, headFont4);
		ColumnText.showTextAligned(canvasText8, Element.ALIGN_LEFT, phraseText8, 533, 630, 0);

		String text9 = "PARTIE I : IDENTIFICATION DU CANDIDAT ";
		com.itextpdf.text.pdf.PdfContentByte canvasText9 = writer.getDirectContent();
		Phrase phraseText9 = new Phrase(text9, headFont10);
		ColumnText.showTextAligned(canvasText9, Element.ALIGN_LEFT, phraseText9, 30, 595, 0);

		String text10 = "1- NOMS ET PRÉNOMS : ".toUpperCase();
		com.itextpdf.text.pdf.PdfContentByte canvasText10 = writer.getDirectContent();
		Phrase phraseText10 = new Phrase(text10, headFont1);
		ColumnText.showTextAligned(canvasText10, Element.ALIGN_LEFT, phraseText10, 30, 575, 0);

		String text13 = ".............................................................................................................................................................................";
		com.itextpdf.text.pdf.PdfContentByte canvasText13 = writer.getDirectContent();
		Phrase phraseText13 = new Phrase(text13, headFont1);
		ColumnText.showTextAligned(canvasText13, Element.ALIGN_LEFT, phraseText13, 133, 575, 0);

		String text14 = studentSession.getStudent().getPerson().getSurnameCni().toUpperCase() + " "
				+ studentSession.getStudent().getPerson().getGivenNameCni().toUpperCase();
		com.itextpdf.text.pdf.PdfContentByte canvasText14 = writer.getDirectContent();
		Phrase phraseText14 = new Phrase(text14, headFont1);
		ColumnText.showTextAligned(canvasText14, Element.ALIGN_LEFT, phraseText14, 138, 577, 0);

		String text11 = "2- DATE DE NAISSANCE : ";
		com.itextpdf.text.pdf.PdfContentByte canvasText11 = writer.getDirectContent();
		Phrase phraseText11 = new Phrase(text11, headFont1);
		ColumnText.showTextAligned(canvasText11, Element.ALIGN_LEFT, phraseText11, 30, 550, 0);
		String text15 = "......................... ";
		com.itextpdf.text.pdf.PdfContentByte canvasText15 = writer.getDirectContent();
		Phrase phraseText15 = new Phrase(text15, headFont1);
		ColumnText.showTextAligned(canvasText15, Element.ALIGN_LEFT, phraseText15, 137, 550, 0);
		SimpleDateFormat dobDate = new SimpleDateFormat("dd/MM/yyyy");
		String dobDateFormatted = dobDate.format(studentSession.getStudent().getPerson().getDobCni());

		String text16 = "" + dobDateFormatted;
		com.itextpdf.text.pdf.PdfContentByte canvasText16 = writer.getDirectContent();
		Phrase phraseText16 = new Phrase(text16, headFont1);
		ColumnText.showTextAligned(canvasText16, Element.ALIGN_LEFT, phraseText16, 140, 552, 0);
		String text12 = "3- LIEU :";
		com.itextpdf.text.pdf.PdfContentByte canvasText12 = writer.getDirectContent();
		Phrase phraseText12 = new Phrase(text12, headFont1);
		ColumnText.showTextAligned(canvasText12, Element.ALIGN_LEFT, phraseText12, 220, 550, 0);
		String text17 = "............................................................................................................................ ";
		com.itextpdf.text.pdf.PdfContentByte canvasText17 = writer.getDirectContent();
		Phrase phraseText17 = new Phrase(text17, headFont1);
		ColumnText.showTextAligned(canvasText17, Element.ALIGN_LEFT, phraseText17, 255, 550, 0);
		String text18 = studentSession.getStudent().getPerson().getPobCni().toUpperCase();
		com.itextpdf.text.pdf.PdfContentByte canvasText18 = writer.getDirectContent();
		Phrase phraseText18 = new Phrase(text18, headFont1);
		ColumnText.showTextAligned(canvasText18, Element.ALIGN_LEFT, phraseText18, 257, 552, 0);

		String text19 = "3- SEXE :";
		com.itextpdf.text.pdf.PdfContentByte canvasText19 = writer.getDirectContent();
		Phrase phraseText19 = new Phrase(text19, headFont1);
		ColumnText.showTextAligned(canvasText19, Element.ALIGN_LEFT, phraseText19, 30, 530, 0);
		String text20 = "...........................";
		com.itextpdf.text.pdf.PdfContentByte canvasText20 = writer.getDirectContent();
		Phrase phraseText20 = new Phrase(text20, headFont1);
		ColumnText.showTextAligned(canvasText20, Element.ALIGN_LEFT, phraseText20, 70, 530, 0);
		if (studentSession.getStudent().getPerson().getGenderCni().equals("M")) {
			String text21 = "MASCULIN";
			com.itextpdf.text.pdf.PdfContentByte canvasText21 = writer.getDirectContent();
			Phrase phraseText21 = new Phrase(text21, headFont1);
			ColumnText.showTextAligned(canvasText21, Element.ALIGN_LEFT, phraseText21, 77, 533, 0);
		} else {
			if (studentSession.getStudent().getPerson().getGenderCni().equals("F")) {
				String text21 = "FÉMININ".toUpperCase();
				com.itextpdf.text.pdf.PdfContentByte canvasText21 = writer.getDirectContent();
				Phrase phraseText21 = new Phrase(text21, headFont1);
				ColumnText.showTextAligned(canvasText21, Element.ALIGN_LEFT, phraseText21, 77, 533, 0);
			}
		}

		String text22 = "5- LANGUE DE COMPOSITION :";
		com.itextpdf.text.pdf.PdfContentByte canvasText22 = writer.getDirectContent();
		Phrase phraseText22 = new Phrase(text22, headFont1);
		ColumnText.showTextAligned(canvasText22, Element.ALIGN_LEFT, phraseText22, 150, 530, 0);
		String text23 = "FRANCAIS";
		com.itextpdf.text.pdf.PdfContentByte canvasText23 = writer.getDirectContent();
		Phrase phraseText23 = new Phrase(text23, headFont1);
		ColumnText.showTextAligned(canvasText23, Element.ALIGN_LEFT, phraseText23, 300, 530, 0);
		PdfContentByte frenchPic = writer.getDirectContent();
		frenchPic.saveState();
		frenchPic.setColorStroke(BaseColor.BLACK);
		frenchPic.rectangle(355, 525, 15, 15);
		frenchPic.stroke();
		frenchPic.restoreState();
		if (studentSession.getStudent().getPerson().getLanguage().equals("FR")) {
			PdfContentByte frenchPic1 = writer.getDirectContent();
			frenchPic1.saveState();
			frenchPic1.setColorStroke(BaseColor.BLACK);
			frenchPic1.rectangle(355, 525, 15, 15);
			frenchPic1.stroke();
			frenchPic1.restoreState();
			String text41 = "X";
			com.itextpdf.text.pdf.PdfContentByte canvasText41 = writer.getDirectContent();
			Phrase phraseText41 = new Phrase(text41, headFontX);
			ColumnText.showTextAligned(canvasText41, Element.ALIGN_LEFT, phraseText41, 357, 527, 0);
		}

		String text24 = "ANGLAIS";
		com.itextpdf.text.pdf.PdfContentByte canvasText24 = writer.getDirectContent();
		Phrase phraseText24 = new Phrase(text24, headFont1);
		ColumnText.showTextAligned(canvasText24, Element.ALIGN_LEFT, phraseText24, 420, 530, 0);
		PdfContentByte englishPic = writer.getDirectContent();
		englishPic.saveState();
		englishPic.setColorStroke(BaseColor.BLACK);
		englishPic.rectangle(480, 525, 15, 15);
		englishPic.stroke();
		englishPic.restoreState();
		if (studentSession.getStudent().getPerson().getLanguage().equals("EN")) {
			PdfContentByte englishPic1 = writer.getDirectContent();
			englishPic1.saveState();
			englishPic1.setColorStroke(BaseColor.BLACK);
			englishPic1.rectangle(480, 525, 15, 15);
			englishPic1.stroke();
			englishPic1.restoreState();
			String text41 = "X";
			com.itextpdf.text.pdf.PdfContentByte canvasText41 = writer.getDirectContent();
			Phrase phraseText41 = new Phrase(text41, headFontX);
			ColumnText.showTextAligned(canvasText41, Element.ALIGN_LEFT, phraseText41, 481, 525, 0);
		}

		String text25 = "6- NATIONALITÉ :".toUpperCase();
		com.itextpdf.text.pdf.PdfContentByte canvasText25 = writer.getDirectContent();
		Phrase phraseText25 = new Phrase(text25, headFont1);
		ColumnText.showTextAligned(canvasText25, Element.ALIGN_LEFT, phraseText25, 30, 500, 0);
		String text26 = "....................................";
		com.itextpdf.text.pdf.PdfContentByte canvasText26 = writer.getDirectContent();
		Phrase phraseText26 = new Phrase(text26, headFont1);
		ColumnText.showTextAligned(canvasText26, Element.ALIGN_LEFT, phraseText26, 106, 500, 0);
		String text27 = studentSession.getStudent().getPerson().getNationality().getCountryNameFrench().toUpperCase();
		com.itextpdf.text.pdf.PdfContentByte canvasText27 = writer.getDirectContent();
		Phrase phraseText27 = new Phrase(text27, headFont1);
		ColumnText.showTextAligned(canvasText27, Element.ALIGN_LEFT, phraseText27, 112, 502, 0);

		String text28 = "7- RÉGION D'ORIGINE :".toUpperCase();
		com.itextpdf.text.pdf.PdfContentByte canvasText28 = writer.getDirectContent();
		Phrase phraseText28 = new Phrase(text28, headFont1);
		ColumnText.showTextAligned(canvasText28, Element.ALIGN_LEFT, phraseText28, 220, 500, 0);
		String text29 = ".................................................................................................";
		com.itextpdf.text.pdf.PdfContentByte canvasText29 = writer.getDirectContent();
		Phrase phraseText29 = new Phrase(text29, headFont1);
		ColumnText.showTextAligned(canvasText29, Element.ALIGN_LEFT, phraseText29, 323, 500, 0);
		String text30 = "";
		com.itextpdf.text.pdf.PdfContentByte canvasText30 = writer.getDirectContent();
		Phrase phraseText30 = new Phrase(text30, headFont1);
		ColumnText.showTextAligned(canvasText30, Element.ALIGN_LEFT, phraseText30, 340, 502, 0);

		String text31 = "8- CARTE NATIONALE D'IDENTITÉ N° : ".toUpperCase();
		com.itextpdf.text.pdf.PdfContentByte canvasText31 = writer.getDirectContent();
		Phrase phraseText31 = new Phrase(text31, headFont1);
		ColumnText.showTextAligned(canvasText31, Element.ALIGN_LEFT, phraseText31, 30, 470, 0);
		String text32 = "..............................................";
		com.itextpdf.text.pdf.PdfContentByte canvasText32 = writer.getDirectContent();
		Phrase phraseText32 = new Phrase(text32, headFont1);
		ColumnText.showTextAligned(canvasText32, Element.ALIGN_LEFT, phraseText32, 190, 470, 0);
		String text33 = studentSession.getStudent().getPerson().getNic();
		com.itextpdf.text.pdf.PdfContentByte canvasText33 = writer.getDirectContent();
		Phrase phraseText33 = new Phrase(text33, headFont1);
		ColumnText.showTextAligned(canvasText33, Element.ALIGN_LEFT, phraseText33, 195, 472, 0);
		String text34 = "9- DÉLIVRÉE LE : ".toUpperCase();
		com.itextpdf.text.pdf.PdfContentByte canvasText34 = writer.getDirectContent();
		Phrase phraseText34 = new Phrase(text34, headFont1);
		ColumnText.showTextAligned(canvasText34, Element.ALIGN_LEFT, phraseText34, 320, 470, 0);
		String text35 = ".....................................................................";
		com.itextpdf.text.pdf.PdfContentByte canvasText35 = writer.getDirectContent();
		Phrase phraseText35 = new Phrase(text35, headFont1);
		ColumnText.showTextAligned(canvasText35, Element.ALIGN_LEFT, phraseText35, 395, 470, 0);
		SimpleDateFormat issuedDate = new SimpleDateFormat("dd/MM/yyyy");
		String issuedDateFormatted = issuedDate.format(studentSession.getStudent().getPerson().getIssuedDateCni());

		String text36 = " " + issuedDateFormatted;
		com.itextpdf.text.pdf.PdfContentByte canvasText36 = writer.getDirectContent();
		Phrase phraseText36 = new Phrase(text36, headFont1);
		ColumnText.showTextAligned(canvasText36, Element.ALIGN_LEFT, phraseText36, 400, 472, 0);

		document.add(Chunk.NEWLINE);
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 6, 6, 6, 6 });
		table.setSpacingBefore(350);
		table.setSpacingAfter(10);

		PdfPCell c1 = new PdfPCell(new Phrase("DIPLOME ACADÉMIQUE LE PLUS ELEVÉ".toUpperCase()
				+ "(Joindre une photocopie certifiée conforme dudit diplôme à cette fiche)", headFont11));
		c1.setColspan(4);
		c1.setFixedHeight(15f);
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		String text50 = "";
		Phrase phrase30 = new Phrase(text30, headFont3);
		document.add(phrase30);

		c1 = new PdfPCell(new Phrase("INTITULÉ DU DIPLÔME", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setFixedHeight(15f);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase(studentSession.getStudentQualification().getEntryCertificateName().toUpperCase(),
				headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("SERIE/OPTION/SPECIALITÉ".toUpperCase(), headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(
				new Phrase(studentSession.getStudentQualification().getDiplomeOption().toUpperCase(), headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("SESSION D'OBTENTION", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setFixedHeight(15f);
		table.addCell(c1);
		SimpleDateFormat issuedExamDate = new SimpleDateFormat("dd/MM/yyyy");
		String issuedExamDateFormatted = issuedExamDate
				.format(studentSession.getStudentQualification().getIssuedDate());
		c1 = new PdfPCell(new Phrase(issuedExamDateFormatted, headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("LIEU D'OBTENTION", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(
				new Phrase(studentSession.getStudentQualification().getIssuedPlace().toUpperCase(), headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		table.setHeaderRows(1);

		document.add(table);

		String text37 = "Fait à :................................. le :............................ ";
		com.itextpdf.text.pdf.PdfContentByte canvasText37 = writer.getDirectContent();
		Phrase phraseText37 = new Phrase(text37, headFont1);
		ColumnText.showTextAligned(canvasText37, Element.ALIGN_LEFT, phraseText37, 355, 340, 0);

		String text38 = "Signature de l'autorité en charge des Transports";
		com.itextpdf.text.pdf.PdfContentByte canvasText38 = writer.getDirectContent();
		Phrase phraseText38 = new Phrase(text38, headFont1);
		ColumnText.showTextAligned(canvasText38, Element.ALIGN_LEFT, phraseText38, 220, 300, 0);

	}

	@Override
	public void printStudentReceiptEn(Document document, PdfWriter writer, ByteArrayOutputStream outStream,
			HttpHeaders headers, int studentSessionId) throws DocumentException, IOException {
		StudentSession studentSession = studentSessionService.findById(studentSessionId);

		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 14);
		Font headFont1 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 8);
		Font headFont5 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 8);
		Font headFont4 = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 8);

		// document.add(linebreak);
		String sectionBreak = "-----------------------------------------------------------";

		com.itextpdf.text.pdf.PdfContentByte canvas3 = writer.getDirectContent();
		Phrase phrase3 = new Phrase(sectionBreak + sectionBreak + sectionBreak, headFont1);
		ColumnText.showTextAligned(canvas3, Element.ALIGN_LEFT, phrase3, 20, 160, 0);

		// Print receipt header
		String receiptHeader = "PART II:";
		String receiptHeader2 = "Receipt (To be detached and given to the candidate after signature. This receipt will be required at the entrance to the examination room)";

		com.itextpdf.text.pdf.PdfContentByte canvas4 = writer.getDirectContent();
		Phrase phrase4 = new Phrase(receiptHeader, headFont1);
		ColumnText.showTextAligned(canvas4, Element.ALIGN_LEFT, phrase4, 20, 150, 0);

		com.itextpdf.text.pdf.PdfContentByte canvas5 = writer.getDirectContent();
		Phrase phrase5 = new Phrase(receiptHeader2, headFont2);
		ColumnText.showTextAligned(canvas5, Element.ALIGN_LEFT, phrase5, 65, 150, 0);
		com.itextpdf.text.pdf.PdfContentByte canvas6 = writer.getDirectContent();

		String text6 = "Surnames and Given names : .......................................................................................................................";
		Phrase phrase6 = new Phrase(text6, headFont1);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase6, 20, 130, 0);

		String text7 = "Date and place of birth : ...................................At............................................... Session : .........................";
		Phrase phrase7 = new Phrase(text7, headFont1);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase7, 20, 110, 0);

		String text8 = "Training Establishment : .......................................................................... Region : .....................................";
		Phrase phrase8 = new Phrase(text8, headFont1);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase8, 20, 90, 0);

		String text9 = "Signature and stamp of the Head of Training Center ";
		Phrase phrase9 = new Phrase(text9, headFont4);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase9, 25, 70, 0);

		String text10 = "Signature of the authority in charge of Transport ";
		Phrase phrase10 = new Phrase(text10, headFont4);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase10, 260, 70, 0);

		String text11 = "PHOTO 4 * 4 color, with";
		Phrase phrase11 = new Phrase(text11, headFont5);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase11, 458, 115, 0);

		String text12 = "white background, less than";
		Phrase phrase12 = new Phrase(text12, headFont5);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase12, 458, 105, 0);

		String text13 = "three months old, same photo";
		Phrase phrase13 = new Phrase(text13, headFont5);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase13, 454, 95, 0);

		String text14 = "as above (paste)";
		Phrase phrase14 = new Phrase(text14, headFont5);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase14, 468, 85, 0);

		// print photo template
		com.itextpdf.text.pdf.PdfContentByte photoRectancle = writer.getDirectContent();
		photoRectancle.saveState();
		photoRectancle.setColorStroke(BaseColor.BLACK);
		photoRectancle.rectangle(450, 40, 100, 100);
		// recPaule.roundRectangle(35, 150, 520,250,10);
		photoRectancle.setLineDash(3, 2, 0);
		photoRectancle.stroke();
		photoRectancle.restoreState();

		// Now print person data to template
		com.itextpdf.text.pdf.PdfContentByte canvas12 = writer.getDirectContent();
		Phrase phrase15 = new Phrase(studentSession.getStudent().getPerson().getSurnameCni().toUpperCase() + " "
				+ studentSession.getStudent().getPerson().getGivenNameCni().toUpperCase(), headFont1);
		ColumnText.showTextAligned(canvas12, Element.ALIGN_LEFT, phrase15, 140, 132, 0);

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date dobDate = studentSession.getStudent().getPerson().getDobCni();
		String dobDateFinal = formatter.format(dobDate);
		Phrase phrase16 = new Phrase(dobDateFinal, headFont1);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase16, 140, 112, 0);
		Phrase phrase17 = new Phrase(studentSession.getStudent().getPerson().getPobCni().toUpperCase(), headFont1);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase17, 235, 112, 0);

		Date sessionDate = studentSession.getEligibleCenter().getExamSession().getSessionDate();
		String sessionDateFinal = formatter.format(sessionDate);
		Phrase phrase18 = new Phrase(sessionDateFinal, headFont1);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase18, 385, 112, 0);
		Phrase phrase19 = new Phrase(studentSession.getStudent().getTrainingCenter().getName().toUpperCase(),
				headFont1);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase19, 145, 92, 0);
		Phrase phrase20 = new Phrase(
				studentSession.getStudent().getTrainingCenter().getDivision().getRegion().getName(), headFont1);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase20, 360, 92, 0);

	}

	public void printCandidatsList(ByteArrayOutputStream outStream, HttpHeaders headers, int eligibleCenterId,
			String typeList, int trainingCenterId) throws DocumentException, IOException {
		EligibleCenter eligibleCenter = eligibleCenterService.findById(eligibleCenterId);
		List<StudentSession> candidateSessions = new ArrayList<StudentSession>();

		Document document = null;

		document = new Document(PageSize.A4.rotate());

		String speciality = null;
		document.setMargins(35, 35, 30, 75);
		PdfWriter writer = PdfWriter.getInstance(document, outStream);

		speciality = typeList.split("@")[1];
		Speciality candidateSpeciality = specialityService.findByAbbreviation(speciality);
		TrainingCenter trainingCenter = trainingCenterService.findById(trainingCenterId);
		candidateSessions = studentSessionService
				.findByEligibleCenterAndResultAndSpecialityAndTrainingCenterOrderBySurname(eligibleCenter, 1,
						candidateSpeciality, trainingCenter);

		SimpleDateFormat sessionDate = new SimpleDateFormat("dd/MM/yyyy");
		String sessionDateFormatted = sessionDate.format(eligibleCenter.getExamSession().getSessionDate());

		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		String start = formatter.format(eligibleCenter.getExamSession().getSessionDate());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(start));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.add(Calendar.DATE, -2); // number of days to add
		String startDateOfSession = sdf.format(calendar.getTime());

		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		event.setNumCandidates(candidateSessions.size());
		event.setSpeciality(speciality);
		event.setSessionDateFormatted(sessionDateFormatted);
		event.setStartDateOfSession(startDateOfSession);
		writer.setPageEvent(event);

		document.open();
		Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 8);
		Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);
		// l'entete du pdf

		generatePvListsHeadersForCandidatesList(speciality, eligibleCenter, headFont3, writer, trainingCenter);

		String text30 = "";
		Phrase phrase30 = new Phrase(text30, headFont3);
		document.add(phrase30);

		document.add(Chunk.NEWLINE);

		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 3, 13, 13, 5, 9, 4 });
		table.setSpacingBefore(110);
		table.setSpacingAfter(18);

		PdfPCell c1 = new PdfPCell(new Phrase("No", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("NOM", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("PRENOM", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("DATE DE NAISSANCE", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("LIEU DE NAISSANCE", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("GENDER", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		table.setHeaderRows(1);

		int i = 1;
		for (StudentSession candidateSession : candidateSessions) {
			PdfPCell cell;
			cell = new PdfPCell(new Phrase(String.valueOf(i), headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPaddingRight(5);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(candidateSession.getStudent().getPerson().getSurName().toUpperCase(), headFont2));
			cell.setPaddingLeft(5);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);

			if (candidateSession.getStudent().getPerson().getGivenName() == null) {
				cell = new PdfPCell(new Phrase(" ", headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			} else {
				cell = new PdfPCell(
						new Phrase(candidateSession.getStudent().getPerson().getGivenName().toUpperCase(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			}

			SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
			String stringDate = DateFor.format(candidateSession.getStudent().getPerson().getDob());

			cell = new PdfPCell(new Phrase(stringDate, headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingRight(5);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(candidateSession.getStudent().getPerson().getPob().toUpperCase(), headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingRight(5);
			table.addCell(cell);

			if (candidateSession.getStudent().getPerson().getGender() == null) {
				cell = new PdfPCell(new Phrase(" ", headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			} else {
				cell = new PdfPCell(
						new Phrase(candidateSession.getStudent().getPerson().getGender().toUpperCase(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			}

			i++;
		}
		document.add(table);

		document.close();
		headers.add("Content-Disposition", "inline; filename=pdf-sample.pdf");

	}

	private void generatePvListsHeadersForCandidatesList(String speciality, EligibleCenter eligibleCenter,
			Font headFont3, PdfWriter writer, TrainingCenter trainingCenter) {

		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 9);

		SimpleDateFormat sessionDate = new SimpleDateFormat("dd/MM/yyyy");
		String sessionDateFormatted = sessionDate.format(eligibleCenter.getExamSession().getSessionDate());

		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		String start = formatter.format(eligibleCenter.getExamSession().getSessionDate());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(start));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.add(Calendar.DATE, -2); // number of days to add
		String startDateOfSession = sdf.format(calendar.getTime());

		String text7 = "LISTE DES CANDIDATS PRESENTES POUR L'EXAMEN NATIONAL DU CERTIFICAT D'APTITUDE PROFESSIONELLE";
		com.itextpdf.text.pdf.PdfContentByte canvas6 = writer.getDirectContent();
		Phrase phrase7 = new Phrase(text7, headFont);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase7, 185, 550, 0);

		String text7B = " A L'ENSEIGNEMENT DE LA CONDUITE DES VEHICULES A MOTEURS (CAPEC-" + speciality + ")";
		com.itextpdf.text.pdf.PdfContentByte canvas6B = writer.getDirectContent();
		Phrase phrase7B = new Phrase(text7B, headFont);
		ColumnText.showTextAligned(canvas6B, Element.ALIGN_LEFT, phrase7B, 230, 530, 0);

		String text8 = "LIST OF CANDIDATES PRESENTED FOR THE COMPETITIVE EXAMINATION ";
		com.itextpdf.text.pdf.PdfContentByte canvas7 = writer.getDirectContent();
		Phrase phrase8 = new Phrase(text8, headFont3);
		ColumnText.showTextAligned(canvas7, Element.ALIGN_LEFT, phrase8, 260, 510, 0);

		String text8B = "FOR DRIVING SCHOOL INSTRUCTOR PROFESSIONAL CERTIFICATE (DSIPC-" + speciality + ")";
		com.itextpdf.text.pdf.PdfContentByte canvas7B = writer.getDirectContent();
		Phrase phrase8B = new Phrase(text8B, headFont3);
		ColumnText.showTextAligned(canvas7B, Element.ALIGN_LEFT, phrase8B, 247, 490, 0);

		String text10 = "SESSION DU (FROM) : " + startDateOfSession + " - AU (TO) " + sessionDateFormatted;
		com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
		Phrase phrase10 = new Phrase(text10, headFont3);
		ColumnText.showTextAligned(canvas10, Element.ALIGN_LEFT, phrase10, 270, 470, 0);
		String text9 = "CENTRE : " + eligibleCenter.getExamCenter().getName();
		com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
		Phrase phrase9 = new Phrase(text9, headFont3);
		ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase9, 500, 470, 0);

		String text11 = "CENTRE DE FORMATION : " + trainingCenter.getName();
		com.itextpdf.text.pdf.PdfContentByte canvas11 = writer.getDirectContent();
		Phrase phrase11 = new Phrase(text11, headFont3);
		ColumnText.showTextAligned(canvas11, Element.ALIGN_LEFT, phrase11, 300, 450, 0);

	}

	public void printCandidatsList(ByteArrayOutputStream outStream, HttpHeaders headers, int eligibleCenterId,
			String typeList) throws DocumentException, IOException {
		EligibleCenter eligibleCenter = eligibleCenterService.findById(eligibleCenterId);
		List<StudentSession> candidateSessions = new ArrayList<StudentSession>();

		Document document = null;

		document = new Document(PageSize.A4.rotate());

		String speciality = null;
		document.setMargins(35, 35, 30, 75);
		PdfWriter writer = PdfWriter.getInstance(document, outStream);

		speciality = typeList.split("@")[1];
		Speciality candidateSpeciality = specialityService.findByAbbreviation(speciality);

		candidateSessions = studentSessionService.findByEligibleCenterAndResultAndSpeciality(eligibleCenter, 1,
				candidateSpeciality);

		SimpleDateFormat sessionDate = new SimpleDateFormat("dd/MM/yyyy");
		String sessionDateFormatted = sessionDate.format(eligibleCenter.getExamSession().getSessionDate());

		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		String start = formatter.format(eligibleCenter.getExamSession().getSessionDate());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(start));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.add(Calendar.DATE, -2); // number of days to add
		String startDateOfSession = sdf.format(calendar.getTime());

		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		event.setNumCandidates(candidateSessions.size());
		event.setSpeciality(speciality);
		event.setSessionDateFormatted(sessionDateFormatted);
		event.setStartDateOfSession(startDateOfSession);
		writer.setPageEvent(event);

		document.open();
		Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 8);
		Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);
		// l'entete du pdf

		generatePvListsHeadersForCandidatesList(speciality, eligibleCenter, headFont3, writer);

		String text30 = "";
		Phrase phrase30 = new Phrase(text30, headFont3);
		document.add(phrase30);

		document.add(Chunk.NEWLINE);

		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 3, 8, 12, 11, 5, 8, 4 });
		table.setSpacingBefore(110);
		table.setSpacingAfter(18);

		PdfPCell c1 = new PdfPCell(new Phrase("No", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("ECOLE FORMATION", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("NOM", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("PRENOM", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("DATE DE NAISSANCE", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("LIEU DE NAISSANCE", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("GENDER", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		table.setHeaderRows(1);

		int i = 1;
		for (StudentSession candidateSession : candidateSessions) {
			PdfPCell cell;
			cell = new PdfPCell(new Phrase(String.valueOf(i), headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPaddingRight(5);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(candidateSession.getStudent().getTrainingCenter().getName(), headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingRight(5);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(candidateSession.getStudent().getPerson().getSurName().toUpperCase(), headFont2));
			cell.setPaddingLeft(5);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);

			if (candidateSession.getStudent().getPerson().getGivenName() == null) {
				cell = new PdfPCell(new Phrase(" ", headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			} else {
				cell = new PdfPCell(
						new Phrase(candidateSession.getStudent().getPerson().getGivenName().toUpperCase(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			}

			SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
			String stringDate = DateFor.format(candidateSession.getStudent().getPerson().getDob());

			cell = new PdfPCell(new Phrase(stringDate, headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingRight(5);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(candidateSession.getStudent().getPerson().getPob().toUpperCase(), headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingRight(5);
			table.addCell(cell);

			if (candidateSession.getStudent().getPerson().getGender() == null) {
				cell = new PdfPCell(new Phrase(" ", headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			} else {
				cell = new PdfPCell(
						new Phrase(candidateSession.getStudent().getPerson().getGender().toUpperCase(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			}

			i++;
		}
		document.add(table);

		document.close();
		headers.add("Content-Disposition", "inline; filename=pdf-sample.pdf");

	}

	private void generatePvListsHeadersForCandidatesList(String speciality, EligibleCenter eligibleCenter,
			Font headFont3, PdfWriter writer) {

		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 9);

		SimpleDateFormat sessionDate = new SimpleDateFormat("dd/MM/yyyy");
		String sessionDateFormatted = sessionDate.format(eligibleCenter.getExamSession().getSessionDate());

		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		String start = formatter.format(eligibleCenter.getExamSession().getSessionDate());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(start));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.add(Calendar.DATE, -2); // number of days to add
		String startDateOfSession = sdf.format(calendar.getTime());

		String text7 = "LISTE DES CANDIDATS PRESENTES POUR L'EXAMEN NATIONAL DU CERTIFICAT D'APTITUDE PROFESSIONELLE";
		com.itextpdf.text.pdf.PdfContentByte canvas6 = writer.getDirectContent();
		Phrase phrase7 = new Phrase(text7, headFont);
		ColumnText.showTextAligned(canvas6, Element.ALIGN_LEFT, phrase7, 185, 550, 0);

		String text7B = " A L'ENSEIGNEMENT DE LA CONDUITE DES VEHICULES A MOTEURS (CAPEC-" + speciality + ")";
		com.itextpdf.text.pdf.PdfContentByte canvas6B = writer.getDirectContent();
		Phrase phrase7B = new Phrase(text7B, headFont);
		ColumnText.showTextAligned(canvas6B, Element.ALIGN_LEFT, phrase7B, 230, 530, 0);

		String text8 = "LIST OF CANDIDATES PRESENTED FOR THE COMPETITIVE EXAMINATION ";
		com.itextpdf.text.pdf.PdfContentByte canvas7 = writer.getDirectContent();
		Phrase phrase8 = new Phrase(text8, headFont3);
		ColumnText.showTextAligned(canvas7, Element.ALIGN_LEFT, phrase8, 260, 510, 0);

		String text8B = "FOR DRIVING SCHOOL INSTRUCTOR PROFESSIONAL CERTIFICATE (DSIPC-" + speciality + ")";
		com.itextpdf.text.pdf.PdfContentByte canvas7B = writer.getDirectContent();
		Phrase phrase8B = new Phrase(text8B, headFont3);
		ColumnText.showTextAligned(canvas7B, Element.ALIGN_LEFT, phrase8B, 247, 490, 0);

		String text10 = "SESSION DU (FROM) : " + startDateOfSession + " - AU (TO) " + sessionDateFormatted;
		com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
		Phrase phrase10 = new Phrase(text10, headFont3);
		ColumnText.showTextAligned(canvas10, Element.ALIGN_LEFT, phrase10, 270, 470, 0);
		String text9 = "CENTRE : " + eligibleCenter.getExamCenter().getName();
		com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
		Phrase phrase9 = new Phrase(text9, headFont3);
		ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase9, 500, 470, 0);

	}

	public void printEligibleList(ByteArrayOutputStream outStream, HttpHeaders headers, int eligibleCenterId,
			String typeList) throws DocumentException, IOException {
		EligibleCenter eligibleCenter = eligibleCenterService.findById(eligibleCenterId);
		List<StudentSession> candidateSessions = new ArrayList<StudentSession>();
		Document document = null;

		document = new Document(PageSize.A4.rotate());

		String speciality = null;
		document.setMargins(35, 35, 30, 75);
		PdfWriter writer = PdfWriter.getInstance(document, outStream);

		SimpleDateFormat sessionDate = new SimpleDateFormat("dd/MM/yyyy");
		String sessionDateFormatted = sessionDate.format(eligibleCenter.getExamSession().getSessionDate());

		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		String start = formatter.format(eligibleCenter.getExamSession().getSessionDate());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(start));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.add(Calendar.DATE, -2); // number of days to add
		String startDateOfSession = sdf.format(calendar.getTime());
		HeaderFooterPageEventEligib event = new HeaderFooterPageEventEligib();

		document.open();
		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 14);
		Font headFont1 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 8);
		Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);
		Font headFont4 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 5);
		// l'entete du pdf
		headersPdf(document, writer, true);

		speciality = typeList.split("@")[1];
		Speciality candidateSpeciality = specialityService.findByAbbreviation(speciality);

		candidateSessions = studentSessionService.findByEligibleCenterAndResultAndSpeciality(eligibleCenter, 2,
				candidateSpeciality);

		event.setNumCandidates(candidateSessions.size());
		event.setSpeciality(speciality);
		event.setSessionDateFormatted(sessionDateFormatted);
		event.setStartDateOfSession(startDateOfSession);
		writer.setPageEvent(event);

		generatePvListsHeaders(candidateSessions, typeList.split("@")[0], speciality, eligibleCenter, headFont1,
				headFont3, writer);

		String text30 = "";
		Phrase phrase30 = new Phrase(text30, headFont3);
		document.add(phrase30);

		document.add(Chunk.NEWLINE);

		PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 1, 8, 12, 11, 5, 7, 4, 5 });
		table.setSpacingBefore(110);
		table.setSpacingAfter(18);

		PdfPCell c1 = new PdfPCell(new Phrase("No", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("ECOLE FORMATION", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("NOM", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("PRENOM", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("DATE DE NAISSANCE", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("LIEU DE NAISSANCE", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("GENDER", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		c1 = new PdfPCell(new Phrase("PHOTO", headFont2));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		table.setHeaderRows(1);

		int i = 1;
		for (StudentSession candidateSession : candidateSessions) {
			PdfPCell cell;
			cell = new PdfPCell(new Phrase(String.valueOf(i), headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPaddingRight(5);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(candidateSession.getStudent().getTrainingCenter().getName(), headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingRight(5);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(candidateSession.getStudent().getPerson().getSurName().toUpperCase(), headFont2));
			cell.setPaddingLeft(5);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell);

			if (candidateSession.getStudent().getPerson().getGivenName() == null) {
				cell = new PdfPCell(new Phrase(" ", headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			} else {
				cell = new PdfPCell(
						new Phrase(candidateSession.getStudent().getPerson().getGivenName().toUpperCase(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			}

			SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
			String stringDate = DateFor.format(candidateSession.getStudent().getPerson().getDob());

			cell = new PdfPCell(new Phrase(stringDate, headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingRight(5);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(candidateSession.getStudent().getPerson().getPob().toUpperCase(), headFont2));
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setPaddingRight(5);
			table.addCell(cell);

			if (candidateSession.getStudent().getPerson().getGender() == null) {
				cell = new PdfPCell(new Phrase(" ", headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			} else {
				cell = new PdfPCell(
						new Phrase(candidateSession.getStudent().getPerson().getGender().toUpperCase(), headFont2));
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);
			}

			if (candidateSession.getPhotoAndSignature() == null) {
				Image studentPhoto = Image.getInstance(studentSessionPhotoFolder + "0.png");
				studentPhoto.scaleAbsolute(70, 70);
				cell = new PdfPCell(studentPhoto);
				table.addCell(cell);
			} else {
				Image studentPhoto = Image
						.getInstance(studentSessionPhotoFolder + candidateSession.getPhotoAndSignature());
				studentPhoto.scaleAbsolute(70, 70);
				cell = new PdfPCell(studentPhoto);
				table.addCell(cell);
			}

			i++;
		}
		document.add(table);
		document.close();
		headers.add("Content-Disposition", "inline; filename=eligible-candidates.pdf");

	}
	
	
	public void headersAdmittedPdf(Document document, PdfWriter writer, boolean isLandScape)
			throws IOException, DocumentException {
		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 14);
		Font headFont1 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 9);
		Font headFont2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);
		Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);
		Font headFont4 = FontFactory.getFont(FontFactory.HELVETICA.toUpperCase(), 6);

		if (isLandScape) {
			Image image = Image.getInstance(codeArms + "cameroun.jpg");
			image.setAbsolutePosition(390f, 510f);
			image.scaleAbsolute(60f, 60f);
			document.add(image);
			String text1 = "REPUBLIQUE DU CAMEROUN";
			com.itextpdf.text.pdf.PdfContentByte canvas = writer.getDirectContent();
			Phrase phrase1 = new Phrase(text1, headFont2);
			ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase1,67, 570, 0);
			String text2 = "PAIX-TRAVAIL-PATRIE";
			com.itextpdf.text.pdf.PdfContentByte canvas1 = writer.getDirectContent();
			Phrase phrase2 = new Phrase(text2, headFont4);
			ColumnText.showTextAligned(canvas1, Element.ALIGN_LEFT, phrase2,90, 562, 0);
			
			String text3 = "MINISTERE DES TRANSPORTS";
			com.itextpdf.text.pdf.PdfContentByte canvas2 = writer.getDirectContent();
			Phrase phrase3 = new Phrase(text3, headFont2);
			ColumnText.showTextAligned(canvas2, Element.ALIGN_LEFT, phrase3,67, 550, 0);

			String text15 = "SECRETARIAT GENERAL";
			com.itextpdf.text.pdf.PdfContentByte canvas15 = writer.getDirectContent();
			Phrase phrase15 = new Phrase(text15, headFont2);
			ColumnText.showTextAligned(canvas15, Element.ALIGN_LEFT, phrase15, 78, 538, 0);

			String text16 = "DIRECTION DES TRANSPORTS ROUTIERS";
			com.itextpdf.text.pdf.PdfContentByte canvas16 = writer.getDirectContent();
			Phrase phrase16 = new Phrase(text16, headFont2);
			ColumnText.showTextAligned(canvas16, Element.ALIGN_LEFT, phrase16, 48, 525, 0);
			
			String text17 = "Sous-direction de la Prévention et de Sécurité Routière";
			com.itextpdf.text.pdf.PdfContentByte canvas17 = writer.getDirectContent();
			Phrase phrase17 = new Phrase(text17, headFont2);
			ColumnText.showTextAligned(canvas17, Element.ALIGN_LEFT, phrase17, 35, 512, 0);
			
			String text18 = "N°_____________/BD/MINT/DTR/SDPSR/SFCAM";
			com.itextpdf.text.pdf.PdfContentByte canvas18 = writer.getDirectContent();
			Phrase phrase18 = new Phrase(text18, headFont2);
			ColumnText.showTextAligned(canvas18, Element.ALIGN_LEFT, phrase18, 35, 499, 0);

			String text4 = "REPUBLIC OF CAMEROON";
			com.itextpdf.text.pdf.PdfContentByte canvas3 = writer.getDirectContent();
			Phrase phrase4 = new Phrase(text4, headFont2);
			ColumnText.showTextAligned(canvas3, Element.ALIGN_RIGHT, phrase4, 740, 570, 0);
			String text5 = "PEACE-WORK-FATHERLAND";
			com.itextpdf.text.pdf.PdfContentByte canvas4 = writer.getDirectContent();
			Phrase phrase5 = new Phrase(text5, headFont4);
			ColumnText.showTextAligned(canvas4, Element.ALIGN_RIGHT, phrase5, 730, 562, 0);
		
			String text6 = "MINISTRY OF TRANSPORT";
			com.itextpdf.text.pdf.PdfContentByte canvas5 = writer.getDirectContent();
			Phrase phrase6 = new Phrase(text6, headFont2);
			ColumnText.showTextAligned(canvas5, Element.ALIGN_RIGHT, phrase6, 740, 550, 0);

			String text26 = "SECRETARIAT GENERAL";
			com.itextpdf.text.pdf.PdfContentByte canvas26 = writer.getDirectContent();
			Phrase phrase26 = new Phrase(text26, headFont2);
			ColumnText.showTextAligned(canvas26, Element.ALIGN_RIGHT, phrase26,738, 538, 0);
			
			String text27 = "DEPARTMENT OF ROAD TRANSPORT";
			com.itextpdf.text.pdf.PdfContentByte canvas27 = writer.getDirectContent();
			Phrase phrase27 = new Phrase(text27, headFont2);
			ColumnText.showTextAligned(canvas27, Element.ALIGN_LEFT, phrase27,624,525,0);
			
			String text19 = "Sub-department of Prevention and Road Safety";
			com.itextpdf.text.pdf.PdfContentByte canvas19 = writer.getDirectContent();
			Phrase phrase19 = new Phrase(text19, headFont2);
			ColumnText.showTextAligned(canvas19, Element.ALIGN_LEFT, phrase19, 590, 512, 0);
		} else {

			Image image = Image.getInstance(codeArms + "cameroun.jpg");
			image.setAbsolutePosition(260f, 760f);
			image.scaleAbsolute(60f, 60f);
			document.add(image);

			String text1 = "REPUBLIQUE DU CAMEROUN";
			com.itextpdf.text.pdf.PdfContentByte canvas = writer.getDirectContent();
			Phrase phrase1 = new Phrase(text1, headFont2);
			ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase1, 35, 810, 0);
			String text2 = "PAIX-TRAVAIL-PATRIE";
			com.itextpdf.text.pdf.PdfContentByte canvas1 = writer.getDirectContent();
			Phrase phrase2 = new Phrase(text2, headFont4);
			ColumnText.showTextAligned(canvas1, Element.ALIGN_LEFT, phrase2, 60, 802, 0);
			String text9 = "------------------";
			com.itextpdf.text.pdf.PdfContentByte canvas9 = writer.getDirectContent();
			Phrase phrase10 = new Phrase(text9, headFont4);
			ColumnText.showTextAligned(canvas9, Element.ALIGN_LEFT, phrase10, 70, 797, 0);
			String text3 = "MINISTERE DES TRANSPORTS";
			com.itextpdf.text.pdf.PdfContentByte canvas2 = writer.getDirectContent();
			Phrase phrase3 = new Phrase(text3, headFont2);
			ColumnText.showTextAligned(canvas2, Element.ALIGN_LEFT, phrase3, 35, 790, 0);

			com.itextpdf.text.pdf.PdfContentByte canvas11 = writer.getDirectContent();
			Phrase phrase12 = new Phrase(text9, headFont4);
			ColumnText.showTextAligned(canvas11, Element.ALIGN_LEFT, phrase12, 70, 785, 0);

			String text15 = "DIRECTION DES TRANSPORTS ROUTIERS";
			com.itextpdf.text.pdf.PdfContentByte canvas15 = writer.getDirectContent();
			Phrase phrase15 = new Phrase(text15, headFont2);
			ColumnText.showTextAligned(canvas15, Element.ALIGN_LEFT, phrase15, 30, 778, 0);

			String text4 = "REPUBLIC OF CAMEROON";
			com.itextpdf.text.pdf.PdfContentByte canvas3 = writer.getDirectContent();
			Phrase phrase4 = new Phrase(text4, headFont2);
			ColumnText.showTextAligned(canvas3, Element.ALIGN_RIGHT, phrase4, 555, 810, 0);
			String text5 = "PEACE-WORK-FATHERLAND";
			com.itextpdf.text.pdf.PdfContentByte canvas4 = writer.getDirectContent();
			Phrase phrase5 = new Phrase(text5, headFont4);
			ColumnText.showTextAligned(canvas4, Element.ALIGN_RIGHT, phrase5, 535, 802, 0);
			String text10 = "------------------";
			com.itextpdf.text.pdf.PdfContentByte canvas10 = writer.getDirectContent();
			Phrase phrase11 = new Phrase(text10, headFont4);
			ColumnText.showTextAligned(canvas10, Element.ALIGN_RIGHT, phrase11, 515, 797, 0);
			String text6 = "MINISTRY OF TRANSPORT";
			com.itextpdf.text.pdf.PdfContentByte canvas5 = writer.getDirectContent();
			Phrase phrase6 = new Phrase(text6, headFont2);
			ColumnText.showTextAligned(canvas5, Element.ALIGN_RIGHT, phrase6, 555, 790, 0);

			com.itextpdf.text.pdf.PdfContentByte canvas13 = writer.getDirectContent();
			Phrase phrase13 = new Phrase(text10, headFont4);
			ColumnText.showTextAligned(canvas13, Element.ALIGN_LEFT, phrase13, 485, 785, 0);

			String text26 = "DEPARTMENT OF ROAD TRANSPORT";
			com.itextpdf.text.pdf.PdfContentByte canvas26 = writer.getDirectContent();
			Phrase phrase26 = new Phrase(text26, headFont2);
			ColumnText.showTextAligned(canvas26, Element.ALIGN_RIGHT, phrase26, 560, 778, 0);

		}

	}
	
	private void generateAdmittedListsHeadersForCandidatesList(EligibleCenter eligibleCenter, TrainingCenter TrainingCenter,
	    Font headFont3, PdfWriter writer, TrainingCenter trainingCenter) {

		Font headFont = FontFactory.getFont(FontFactory.TIMES_BOLD.toUpperCase(), 9);
		Format formatter = new SimpleDateFormat("MMM yyyy");
		String session = "";
		if(eligibleCenter != null)
			session =formatter.format(eligibleCenter.getExamSession().getSessionDate()).toUpperCase();

		String textBordereau = "BORDEREAU DE TRANSMISSION DES DIPLOMES, RELEVES DE NOTES ET CARTES PROFESSIONNELLES";
		com.itextpdf.text.pdf.PdfContentByte canvastextBordereau = writer.getDirectContent();
		Phrase phrasetextBordereau = new Phrase(textBordereau, headFont);
		ColumnText.showTextAligned(canvastextBordereau, Element.ALIGN_LEFT, phrasetextBordereau, 185, 480, 0);

		String textDelivery = "DELIVERY SLIP FOR CERTIFICATES, TRANSCRIPTS AND PROFESSIONAL CARDS";
		com.itextpdf.text.pdf.PdfContentByte canvastextDelivery = writer.getDirectContent();
		Phrase phrasetextDelivery = new Phrase(textDelivery, headFont);
		ColumnText.showTextAligned(canvastextDelivery, Element.ALIGN_LEFT, phrasetextDelivery, 230, 468, 0);
        
		String region = "";
		if(eligibleCenter != null)
			region = eligibleCenter.getExamCenter().getDivision().getRegion().getName().toUpperCase();
		String textRegion = "REGION : "+region;
		com.itextpdf.text.pdf.PdfContentByte canvastextRegion = writer.getDirectContent();
		Phrase phrasetextRegion = new Phrase(textRegion, headFont);
		ColumnText.showTextAligned(canvastextRegion, Element.ALIGN_LEFT, phrasetextRegion, 35, 445, 0);
		
		String division = "";
		if(eligibleCenter != null)
			division = eligibleCenter.getExamCenter().getDivision().getName().toUpperCase();
		String textDepartment = "DEPARTEMENT / DIVISION : "+division;
		com.itextpdf.text.pdf.PdfContentByte canvastextDepartment = writer.getDirectContent();
		Phrase phrasetextDepartment = new Phrase(textDepartment, headFont);
		ColumnText.showTextAligned(canvastextRegion, Element.ALIGN_LEFT, phrasetextDepartment, 185, 445, 0);
		
		String examCenter="";
		if(eligibleCenter != null)
			examCenter = eligibleCenter.getExamCenter().getName().toUpperCase();
		String textTown = "VILLE / TOWN : "+ examCenter;
		com.itextpdf.text.pdf.PdfContentByte canvastextTown = writer.getDirectContent();
		Phrase phrasetextTown = new Phrase(textTown, headFont);
		ColumnText.showTextAligned(canvastextTown, Element.ALIGN_LEFT, phrasetextTown, 450, 445, 0);
		
		String textSession = "SESSION : "+session;
		com.itextpdf.text.pdf.PdfContentByte canvastextSession = writer.getDirectContent();
		Phrase phrasetextSession = new Phrase(textSession, headFont);
		ColumnText.showTextAligned(canvastextSession, Element.ALIGN_LEFT, phrasetextSession, 635, 445, 0);
		
		String trainingCenterLabel = "";
		if(trainingCenter!=null)
			trainingCenterLabel = trainingCenter.getName().toUpperCase();
		String textCentre = "CENTRE DE FORMATION / TRAINING CENTER : "+ trainingCenterLabel;
		com.itextpdf.text.pdf.PdfContentByte canvastextCentre = writer.getDirectContent();
		Phrase phrasetextCentre = new Phrase(textCentre, headFont);
		ColumnText.showTextAligned(canvastextCentre, Element.ALIGN_LEFT, phrasetextCentre, 35, 432, 0);

	}
	private void generateAdmittedListsFooter(PdfWriter writer, Document document) {
		Font headFont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8);
	    Font headFont3 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 9);
	    /*
    	 * pied de page gauche
    	 */
        String textRecuLe ="Recu le";
        Phrase phraseTextRecuLe = new Phrase(textRecuLe, headFont2);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, phraseTextRecuLe,40,60,0);
        String textLineRecuLe ="____________";
        Phrase phraseTextLineRecuLe = new Phrase(textLineRecuLe, headFont2);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, phraseTextLineRecuLe, 78, 61, 0);
        String textPar ="Par:";
        Phrase phrasetextPar = new Phrase(textPar, headFont2);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, phrasetextPar,35,47,0);
        
        /*
    	 * pied de page droit
    	 */
        String textFaitA ="Fait à Yaoundé, le";
        Phrase phraseTextFaitA = new Phrase(textFaitA, headFont2);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, phraseTextFaitA, 680, 60, 0);
        String textLineFaitA ="_____________________________";
        Phrase phrasetextLineFaitA = new Phrase(textLineFaitA, headFont2);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, phrasetextLineFaitA, 770, 61, 0);
        String textLeDirecteur ="Le Directeur Des Transports Routiers";
        Phrase phraseLeDirecteur = new Phrase(textLeDirecteur, headFont2);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, phraseLeDirecteur, 710, 47, 0);
        String textCordonnateur ="Cordonnateur National de l'examen du CAPEC";
        Phrase phraseCordonnateur = new Phrase(textCordonnateur, headFont2);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, phraseCordonnateur, 726, 37, 0);
       
       
	}

}
