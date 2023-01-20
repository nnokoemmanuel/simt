package ppp.simt.service.archive;

import java.util.ArrayList;

import ppp.simt.entity.tracking.ArchiveFileTracking;

public interface ArchiveFileTrackingService {
	
	public void save(ArchiveFileTracking archiveFileTracking);
	public ArrayList<ArchiveFileTracking> findByArchiveFileId(int id);
}
