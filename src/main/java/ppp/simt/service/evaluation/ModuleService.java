package ppp.simt.service.evaluation;

import java.text.ParseException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.evaluation.Course;
import ppp.simt.entity.evaluation.Module;
import ppp.simt.form.evaluation.ModuleForm;


public interface ModuleService {
	public List<Module> findAll();
	public void createModule(Module module);
	public void updateModule(Module module);
	public Module findModuleById(int moduleId);
	public List<Module> findBySpeciality(Speciality speciality);
	Module persistModuleBean(ModuleForm moduleForm, HttpServletRequest httpServletRequest) throws ParseException;
	public void deleteModule(Module module);
	public List<Module> findByModuleClassificationAndSpeciality(int classification,Speciality speciality);
	public List<Module> findBySpecialityOrderByModuleClassification(Speciality speciality);
	public List<Module> findByStatus(int status);


}
