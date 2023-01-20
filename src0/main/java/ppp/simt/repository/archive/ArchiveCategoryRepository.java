package ppp.simt.repository.archive;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.archive.ArchiveCategory;

import java.util.List;

@Repository("archiveCategoryRepository")
public interface ArchiveCategoryRepository extends JpaRepository <ArchiveCategory, Integer>{
	
	ArchiveCategory findById(int archiveCategoryId);
	List<ArchiveCategory> findAll();
	//List<ArchiveCategory> findByArchiveId(int archive_id);

}