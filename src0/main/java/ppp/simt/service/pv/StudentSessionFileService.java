package ppp.simt.service.pv;

import java.util.List;
import java.util.Set;

import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.StudentSessionFile;


public interface StudentSessionFileService {
	
	public StudentSessionFile findById(int studentSessionFileId);
	public Set<StudentSessionFile> findByStudentSessionAndDeleted(StudentSession studentSession, int deleted);
	public List<StudentSessionFile> findAll();
	public void save(StudentSessionFile candidateFile);


	

}
