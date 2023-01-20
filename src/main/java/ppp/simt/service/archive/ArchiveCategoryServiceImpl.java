package ppp.simt.service.archive;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.archive.ArchiveCategory;
import ppp.simt.repository.archive.ArchiveCategoryRepository;

@Service
public class ArchiveCategoryServiceImpl implements ArchiveCategoryService {

	@Autowired
	private ArchiveCategoryRepository archiveCategoryRepository;

	@Override
	public List<ArchiveCategory> findAll() {
		return archiveCategoryRepository.findAll();
	}

	/*@Override
	public List<ArchiveCategory> findByArchiveId(int archive_id) {
		// TODO Auto-generated method stub
		return archiveCategoryRepository.findByArchiveId(archive_id);
	}*/

	
}
