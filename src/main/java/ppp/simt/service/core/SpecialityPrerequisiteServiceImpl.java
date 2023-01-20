package ppp.simt.service.core;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.configuration.SpecialityPrerequisite;
import ppp.simt.entity.core.Speciality;
import ppp.simt.repository.core.SpecialityPrerequisiteRepository;

@Service
public class SpecialityPrerequisiteServiceImpl implements SpecialityPrerequisiteService{
	
	@Autowired
	private SpecialityPrerequisiteRepository specialityPrequisiteRepository;
	
	@Autowired
	EntityManager em;

	@Override
	public SpecialityPrerequisite findById(int id) {
		// TODO Auto-generated method stub
		return specialityPrequisiteRepository.findById(id);
	}

	@Override
	public List<SpecialityPrerequisite> findBySpecialityAndDIplomaStatus(Speciality speciality, String diplomaStatus) {
		// TODO Auto-generated method stub
		return specialityPrequisiteRepository.findBySpecialityAndDIplomaStatus(speciality, diplomaStatus, em);
	}

	@Override
	public List<SpecialityPrerequisite> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
