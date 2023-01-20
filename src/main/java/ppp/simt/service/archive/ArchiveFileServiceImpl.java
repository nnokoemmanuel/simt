package ppp.simt.service.archive;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.archive.ArchiveFile;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.StudentSessionFile;
import ppp.simt.repository.archive.ArchiveFileRepository;


@Service
public class ArchiveFileServiceImpl implements ArchiveFileService {

	@Autowired
	private ArchiveFileRepository archiveFileRepository;
	
	
	@Override
	public List<ArchiveFile> findAll() {
		return archiveFileRepository.findAll();
	}

	
	
	@Override
	public void createArchiveFile(ArchiveFile archiveFile) {
		archiveFileRepository.save(archiveFile);
		
	}
	
	@Override
	public void updateArchiveFile(ArchiveFile archiveFile) {
		archiveFileRepository.saveAndFlush(archiveFile);
		
	}

	
	@Override
	public ArchiveFile findArchiveFileById(int archiveFileId) {
		
		return archiveFileRepository.findById(archiveFileId);
	}

	@Override
	public ArchiveFile save(ArchiveFile archiveFile) {
		// TODO Auto-generated method stub
		return archiveFileRepository.save(archiveFile);
	}


	@Override
	public List<ArchiveFile> findByArchiveOrderByIdDesc(Archive archive) {

		return archiveFileRepository.findByArchiveOrderByIdDesc(archive);
	}
	
	@Override
	public Set<ArchiveFile> findByArchiveAndDeleted(Archive archive, int deleted){
		return archiveFileRepository.findByArchiveAndDeleted(archive,  deleted);
	}

}
