package ppp.simt.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.core.Speciality;

@Repository("specialityRepository")
public interface SpecialityRepository extends JpaRepository <Speciality, Integer>{

	Speciality findById(Long specialityId);
	Speciality findByName(String specialityName);
	Speciality findByAbbreviation(String abbreviation);
}
