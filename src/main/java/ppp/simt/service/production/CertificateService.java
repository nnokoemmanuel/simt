package ppp.simt.service.production;

import java.util.List;

import ppp.simt.entity.production.Certificate;
import ppp.simt.entity.pv.StudentSession;

public interface CertificateService {
	
	public List<Certificate> findCertificatesByType(String type);
	public Certificate save(Certificate certificate);
	public void updateCertificate(Certificate certificate);
	public Certificate findCertificateByStudentSession(StudentSession studentSession);
	public Certificate findById(int certificateId);
	public Certificate findByNumber(String certificateNumber);
	public void createCertificate(Certificate certificate);
	
}