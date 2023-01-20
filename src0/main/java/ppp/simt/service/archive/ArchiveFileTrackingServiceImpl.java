package ppp.simt.service.archive;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.tracking.ArchiveFileTracking;
import ppp.simt.repository.archive.ArchiveFileTrackingRepository;

@Service("ArchiveFileTrackingService")
public class ArchiveFileTrackingServiceImpl implements ArchiveFileTrackingService {
	
	 
		@Autowired
	    private ArchiveFileTrackingRepository archiveFileTrackingRepository;

		@Override
		public void save(ArchiveFileTracking archiveFile) {
			// TODO Auto-generated method stub
			archiveFileTrackingRepository.save(archiveFile);
			
		}

		@Override
		public ArrayList<ArchiveFileTracking> findByArchiveFileId(int id) {
			// TODO Auto-generated method stub
			return (ArrayList<ArchiveFileTracking>) archiveFileTrackingRepository.findByArchiveFileId(id);
		}

}
