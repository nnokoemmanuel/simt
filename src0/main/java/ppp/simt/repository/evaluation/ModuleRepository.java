package ppp.simt.repository.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.evaluation.Module;

import java.util.List;

@Repository("moduleRepository")
public interface ModuleRepository extends JpaRepository <Module, Integer>{
	
	public Module findById(int moduleId);
	public List<Module> findBySpeciality(Speciality speciality);
	public Module save(Module module);
	public List<Module> findByModuleClassificationAndSpeciality(int classification  ,Speciality speciality);
	public List<Module> findBySpecialityOrderByModuleClassification(Speciality speciality);
	public List<Module> findByStatus(int status);

}