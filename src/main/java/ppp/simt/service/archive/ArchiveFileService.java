package ppp.simt.service.archive;

import java.util.Date;
import java.util.List;
import java.util.Set;

import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.archive.ArchiveFile;
import ppp.simt.entity.core.Country;
import ppp.simt.entity.core.Person;


public interface ArchiveFileService {
	public List<ArchiveFile> findAll();
	public void createArchiveFile(ArchiveFile archiveFile);
	public void updateArchiveFile(ArchiveFile archiveFile);
	public ArchiveFile findArchiveFileById(int archiveFileId);
	public ArchiveFile save(ArchiveFile archiveFile);
	public List<ArchiveFile> findByArchiveOrderByIdDesc(Archive archive);
	public Set<ArchiveFile> findByArchiveAndDeleted(Archive archive, int deleted);

}
