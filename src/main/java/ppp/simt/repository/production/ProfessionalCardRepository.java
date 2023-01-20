package ppp.simt.repository.production;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.production.Certificate;
import ppp.simt.entity.production.ProfessionalCard;


@Repository("professionalCardRepository")
public interface ProfessionalCardRepository extends JpaRepository <ProfessionalCard, Integer> {
	
	
	public ProfessionalCard findByArchive(Archive archive);
	public ProfessionalCard findById(int id);
	public ProfessionalCard findByCertificate(Certificate certificate);
	public ProfessionalCard findByMatricule(String matricule) ;


}
