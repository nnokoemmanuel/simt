package ppp.simt.service.archive;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.tracking.ArchiveTracking;
import ppp.simt.entity.tracking.evaluation.CourseTracking;
import ppp.simt.repository.archive.ArchiveTrackingRepository;

@Service("ArchiveTrackingService")
public class ArchiveTrackingServiceImpl implements ArchiveTrackingService {
	
	 
		@Autowired
	    private ArchiveTrackingRepository archiveTrackingRepository;

		@Override
		public void save(ArchiveTracking archive) {
			// TODO Auto-generated method stub
			archiveTrackingRepository.save(archive);
			
		}

		@Override
		public ArrayList<ArchiveTracking> findByArchiveId(int id) {
			// TODO Auto-generated method stub
			return (ArrayList<ArchiveTracking>) archiveTrackingRepository.findByArchiveId(id);
		}
		
		@Override
		public void deleteArchiveTracking(ArchiveTracking archiveTracking) {
			archiveTrackingRepository.delete(archiveTracking);
		}

}
