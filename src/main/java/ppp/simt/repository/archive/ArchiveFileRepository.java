package ppp.simt.repository.archive;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.applicant.EntranceExamSession;
import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.archive.ArchiveFile;

import java.util.List;
import java.util.Set;


@Repository("archiveFileRepository")
public interface ArchiveFileRepository extends JpaRepository <ArchiveFile, Integer>{
	
	ArchiveFile findById(int archiveFileId);
	public ArchiveFile save(ArchiveFile archiveFile);
	public List<ArchiveFile> findByArchiveOrderByIdDesc(Archive archive);
	public Set<ArchiveFile> findByArchiveAndDeleted(Archive archive, int deleted);

}