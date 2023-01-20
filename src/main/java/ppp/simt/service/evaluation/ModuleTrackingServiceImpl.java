package ppp.simt.service.evaluation;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppp.simt.entity.evaluation.Module;
import ppp.simt.entity.tracking.evaluation.CourseTracking;
import ppp.simt.entity.tracking.evaluation.ModuleTracking;
import ppp.simt.repository.evaluation.ModuleTrackingRepository;



@Service("moduleTrackingService")
public class ModuleTrackingServiceImpl implements ModuleTrackingService {
	
	 
		@Autowired
	    private ModuleTrackingRepository moduleTrackingRepository;

		@Override
		public void save(ModuleTracking moduleTracking) {
			// TODO Auto-generated method stub
			moduleTrackingRepository.save(moduleTracking);
			
		}

		@Override
		public ArrayList<ModuleTracking> findByModule(Module module) {
			// TODO Auto-generated method stub
			return (ArrayList<ModuleTracking>) moduleTrackingRepository.findByModule(module);
		}
		
		@Override
		public void deleteModuleTracking(ModuleTracking moduleTracking) {
			moduleTrackingRepository.delete(moduleTracking);
		}


}
