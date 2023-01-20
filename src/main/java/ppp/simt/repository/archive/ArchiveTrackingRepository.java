package ppp.simt.repository.archive;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.tracking.ArchiveTracking;

public interface ArchiveTrackingRepository  extends JpaRepository<ArchiveTracking,Long> {

	public ArrayList<ArchiveTracking> findByArchiveId(int id);
	
}
