package ppp.simt.service.core;

import java.util.List;

import ppp.simt.entity.configuration.SpecialityPrerequisite;
import ppp.simt.entity.core.Speciality;

public interface SpecialityPrerequisiteService {
	
	public SpecialityPrerequisite findById(int id);
	public List<SpecialityPrerequisite> findBySpecialityAndDIplomaStatus(Speciality speciality, String diplomaStatus);
	public List<SpecialityPrerequisite> findAll();

}
