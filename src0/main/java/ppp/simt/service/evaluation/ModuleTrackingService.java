package ppp.simt.service.evaluation;

import java.util.ArrayList;

import ppp.simt.entity.evaluation.Module;
import ppp.simt.entity.tracking.evaluation.ModuleTracking;

public interface ModuleTrackingService {
	
	public void save(ModuleTracking courseTracking);
	public ArrayList<ModuleTracking> findByModule(Module module);
	public void deleteModuleTracking(ModuleTracking moduleTracking);
	
}
