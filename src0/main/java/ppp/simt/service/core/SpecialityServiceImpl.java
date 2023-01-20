package ppp.simt.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import ppp.simt.entity.core.Speciality;
import ppp.simt.repository.core.SpecialityRepository;

@Service("specialityService")
public class SpecialityServiceImpl implements SpecialityService {

	
	@Autowired
	private SpecialityRepository specialityRepository;
	
	public Speciality findByName(String specialityName) {
		
		return specialityRepository.findByName(specialityName);
	}
	
	public List<Speciality> findAll() {
		return specialityRepository.findAll();
	}
	
	public Speciality findByAbbreviation(String abbreviation){
		return specialityRepository.findByAbbreviation(abbreviation);
	}

	@Override
	public Speciality findById(Long specialityId) {
		return specialityRepository.findById(specialityId);
	}

	

}
