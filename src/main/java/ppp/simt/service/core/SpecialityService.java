package ppp.simt.service.core;

import java.util.List;
import ppp.simt.entity.core.Speciality;

public interface SpecialityService {
	
	public List<Speciality> findAll();
	public Speciality findById (Long specialityId);
	public Speciality findByAbbreviation(String abbreviation);
	public Speciality findByName(String specialityName);

}
