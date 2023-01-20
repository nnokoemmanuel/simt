package ppp.simt.service.production;


import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.production.Certificate;
import ppp.simt.entity.production.ProfessionalCard;

public interface ProfessionalCardService {
	
	public ProfessionalCard findByArchive(Archive archive);
	public ProfessionalCard findById(int id);
	public ProfessionalCard findByCertificate(Certificate certificate);
	public void save(ProfessionalCard professionalCard);
	public ProfessionalCard findByMatricule(String matricule) ;
	public void createProfessionalCard(ProfessionalCard professionalCard);
	public void updateProfessionalCard(ProfessionalCard professionalCard);

	
}