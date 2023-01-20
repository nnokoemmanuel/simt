package ppp.simt.service.evaluation;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.evaluation.Module;
import ppp.simt.entity.user.User;
import ppp.simt.form.evaluation.ModuleForm;
import ppp.simt.repository.evaluation.ModuleRepository;
import ppp.simt.service.core.SpecialityService;
import ppp.simt.service.user.UserService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


@Service
public class ModuleServiceImpl implements ModuleService {

	@Autowired
	private ModuleRepository moduleRepository;
	
	@Autowired
	private SpecialityService specialityService;
	

	
	@Autowired 
	private Logger_ logger_;
	
	@Autowired
	private Tracker tracker;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModuleService moduleService;
	
	
	@Override
	public List<Module> findAll() {
		return moduleRepository.findAll();
	}

	
	
	@Override
	public void createModule(Module module) {
		moduleRepository.save(module);
		
	}
	
	@Override
	public void updateModule(Module module) {
		moduleRepository.saveAndFlush(module);
		
	}

	
	@Override
	public Module findModuleById(int moduleId) {
		
		return moduleRepository.findById(moduleId);
	}
	
	/**
	 * Convertie un formulaire en bean
	 * @return
	 */
	@Override
	public Module persistModuleBean(ModuleForm moduleForm,HttpServletRequest httpServletRequest) throws ParseException{
		User user = userService.getLogedUser(httpServletRequest);
		
		Module module = null;
		Speciality speciality = null;
		if(moduleForm.getSpecialityAbbreviation() != null)
		   speciality = specialityService.findByAbbreviation(moduleForm.getSpecialityAbbreviation());	
		
		if(moduleForm.getId() ==0  ){
		    //case module does not exists yet
			//we create first instance
			if(speciality != null)
				module = new Module(moduleForm.getCompleteName(), speciality , moduleForm.getModuleClassification(), moduleForm.getModuleMinNote(),moduleForm.getModulePercentage());
			moduleService.createModule(module);
			tracker.track(module, "REGISTERED THE MODULE" , httpServletRequest);
			logger_.log(Constants.NORMAL_LOG_DIR, "Initiates the module Registration: "+user);	
			return module;
			
		}else{
			 //case module exists yet
			
			Module moduleToEdit = moduleService.findModuleById(moduleForm.getId());
			moduleToEdit.setCompleteName(moduleForm.getCompleteName());
			moduleToEdit.setSpeciality(speciality);
			moduleToEdit.setModuleMinNote(moduleForm.getModuleMinNote());
			moduleToEdit.setModuleClassification(moduleForm.getModuleClassification());
			moduleToEdit.setModulePercentage(moduleForm.getModulePercentage());
		    moduleService.updateModule(moduleToEdit);
			tracker.track(moduleToEdit, "EDITED THE MODULE" , httpServletRequest);
    		logger_.log(Constants.NORMAL_LOG_DIR, "Initiates the Module Edition: "+user);
			return moduleToEdit;
		}
	}



	@Override
	public List<Module> findBySpeciality(Speciality speciality) {
		// TODO Auto-generated method stub
		return moduleRepository.findBySpeciality(speciality);
	}
	
	@Override
	public void deleteModule(Module module) {
		moduleRepository.delete(module);
	}
	
	@Override
	public List<Module> findByModuleClassificationAndSpeciality(int classification,Speciality speciality){
		return moduleRepository.findByModuleClassificationAndSpeciality(classification, speciality);
	}
	
	@Override
	public List<Module> findBySpecialityOrderByModuleClassification(Speciality speciality){
		return moduleRepository.findBySpecialityOrderByModuleClassification(speciality);
	}
	
	@Override
	public List<Module> findByStatus(int status){
		return moduleRepository.findByStatus( status);
	}


}
