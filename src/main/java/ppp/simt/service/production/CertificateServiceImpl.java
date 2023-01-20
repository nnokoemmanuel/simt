package ppp.simt.service.production;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ppp.simt.entity.production.Certificate;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.repository.production.CertificateRepository;

@Service
public class CertificateServiceImpl implements CertificateService {
	
	@Autowired
	private CertificateRepository certificateRepository;
	
	@Autowired
	EntityManager em;

	@Override
	public List<Certificate> findCertificatesByType(String type) {
		return certificateRepository.findCertificatesByType(type,em) ;
	}
	
	@Override
	public Certificate save(Certificate certificate) {
		return certificateRepository.save(certificate);
		
	}
	
	@Override
	public void createCertificate(Certificate certificate) {
		certificateRepository.save(certificate);

	}

	@Override
	public void updateCertificate(Certificate certificate) {
		certificateRepository.saveAndFlush(certificate);

	}


	@Override
	public Certificate findCertificateByStudentSession(StudentSession studentSession) {
		return certificateRepository.findCertificateByStudentSession(studentSession);
				
	}
	
	@Override
	public Certificate findById(int certificateId){
		return certificateRepository.findById(certificateId);
	}
	
	@Override
	public Certificate findByNumber(String certificateNumber){
		return certificateRepository.findByNumber(certificateNumber);
	}
	
	}
