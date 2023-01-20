package ppp.simt.entity.production;


import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.core.Division;
import ppp.simt.entity.pv.ExamCenter;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.SubDistrict;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/*
 *Circonscription maritime (en francais) 
 */

@Entity
@Table(name = "professional_card")
public class ProfessionalCard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	@Column(name="issue_date")
	private Date issueDate;

	@Column(name="matricule", columnDefinition="varchar(255) not null COMMENT 'matricule'")
	private String matricule;

	@Column(name="on_process", columnDefinition="integer COMMENT 'à valeur > 0 signifie la presence de un processus sur la carte professionnel et à 0 si non '")
	private int onProcess;

	@OneToOne
	@JoinColumn(name = "certificate_id", referencedColumnName = "id")
	private Certificate certificate;

	@OneToOne
	@JoinColumn(name = "archive_id", referencedColumnName = "id")
	private Archive archive;
	
	@Column(name="is_printed", columnDefinition="integer COMMENT '1 si imprimer et 0 si non'")
	private int isPrinted;

    
	public ProfessionalCard() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public ProfessionalCard(Date issueDate, String matricule, int onProcess, Certificate certificate, Archive archive,
			int isPrinted) {
		super();
		this.issueDate = issueDate;
		this.matricule = matricule;
		this.onProcess = onProcess;
		this.certificate = certificate;
		this.archive = archive;
		this.isPrinted = isPrinted;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
	
	public int getIsPrinted() {
		return isPrinted;
	}

	public void setIsPrinted(int isPrinted) {
		this.isPrinted = isPrinted;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public int getOnProcess() {
		return onProcess;
	}

	public void setOnProcess(int onProcess) {
		this.onProcess = onProcess;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	public Archive getArchive() {
		return archive;
	}

	public void setArchive(Archive archive) {
		this.archive = archive;
	}

}
