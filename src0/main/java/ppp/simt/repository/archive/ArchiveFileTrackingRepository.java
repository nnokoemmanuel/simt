package ppp.simt.repository.archive;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.tracking.ArchiveFileTracking;

public interface ArchiveFileTrackingRepository  extends JpaRepository<ArchiveFileTracking,Long> {

	public ArrayList<ArchiveFileTracking> findByArchiveFileId(int id);
	
}
