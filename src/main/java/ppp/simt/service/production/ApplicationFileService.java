package ppp.simt.service.production;


import java.util.List;

import ppp.simt.entity.production.ApplicationFile;



public interface ApplicationFileService {
	public List<ApplicationFile> findAll();
	public void createApplicationFile(ApplicationFile applicationFile);
	public void updateApplicationFile(ApplicationFile applicationFile);
	public ApplicationFile findApplicationFileById(int applicationFileId);
	public void copyFilesFromOneFolderToAnother(String originFolder, String destinationFolder , String originFileName , String destinationFileName);
}
