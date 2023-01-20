package ppp.simt.service.archive;

import java.util.List;

import ppp.simt.entity.archive.ArchiveCategory;


public interface ArchiveCategoryService {
	public List<ArchiveCategory> findAll();
	//public List<ArchiveCategory> findByArchiveId(int archive_id);
	
}
