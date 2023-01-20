package ppp.simt.repository.evaluation;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.evaluation.Module;
import ppp.simt.entity.tracking.evaluation.ModuleTracking;
import ppp.simt.entity.user.User;

public interface ModuleTrackingRepository  extends JpaRepository<ModuleTracking,Integer> {
	public ModuleTracking findById(int id);
	public ArrayList<ModuleTracking> findByUser(User user);
	public ArrayList<ModuleTracking> findByModule(Module module);

}
