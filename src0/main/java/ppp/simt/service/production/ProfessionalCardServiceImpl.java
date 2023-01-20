package ppp.simt.service.production;



import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.production.Certificate;
import ppp.simt.entity.production.ProfessionalCard;

import ppp.simt.repository.production.ProfessionalCardRepository;

@Service
public class ProfessionalCardServiceImpl implements ProfessionalCardService {
	
	@Autowired
	private ProfessionalCardRepository professionalCardRepository;
	
	@Autowired
	EntityManager em;


	@Override
	public ProfessionalCard findByArchive(Archive archive) {
		return professionalCardRepository.findByArchive(archive);
				
	}
	

	@Override
	public ProfessionalCard findById(int id) {
		return professionalCardRepository.findById(id);
				
	}


	@Override
	public void save(ProfessionalCard professionalCard) {
		professionalCardRepository.save(professionalCard);
		
	}


	@Override
	public ProfessionalCard findByCertificate(Certificate certificate) {
		return professionalCardRepository.findByCertificate(certificate);
	}
	
	@Override
	public ProfessionalCard findByMatricule(String matricule) {
		return professionalCardRepository.findByMatricule(matricule);
	}

	@Override
	public void createProfessionalCard(ProfessionalCard professionalCard) {
		professionalCardRepository.save(professionalCard);
		
	}
	
	@Override
	public void updateProfessionalCard(ProfessionalCard professionalCard) {
		professionalCardRepository.saveAndFlush(professionalCard);
		
	}

}

