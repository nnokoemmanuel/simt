package ppp.simt.service.archive;

import java.util.ArrayList;

import ppp.simt.entity.tracking.ArchiveTracking;

public interface ArchiveTrackingService {
	
	public void save(ArchiveTracking archiveTracking);
	public ArrayList<ArchiveTracking> findByArchiveId(int id);
	public void deleteArchiveTracking(ArchiveTracking archiveTracking) ;
	
}
