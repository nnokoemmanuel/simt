package ppp.simt.service.production;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.io.Files;

import ppp.simt.entity.production.ApplicationFile;
import ppp.simt.repository.production.ApplicationFileRepository;


@Service
public class ApplicationFileServiceImpl implements ApplicationFileService {

	@Autowired
	private ApplicationFileRepository applicationFileRepository;
	
	
	@Override
	public List<ApplicationFile> findAll() {
		return applicationFileRepository.findAll();
	}

	
	
	@Override
	public void createApplicationFile(ApplicationFile applicationFile) {
		applicationFileRepository.save(applicationFile);
		
	}
	
	@Override
	public void updateApplicationFile(ApplicationFile applicationFile) {
		applicationFileRepository.saveAndFlush(applicationFile);
		
	}

	
	@Override
	public ApplicationFile findApplicationFileById(int applicationFileId) {
		
		return applicationFileRepository.findById(applicationFileId);
	}
	
	@Override
	public void copyFilesFromOneFolderToAnother(String originFolder, String destinationFolder , String originFileName , String destinationFileName){
		 Path src = Paths.get(originFolder+originFileName);
		 Path dest =Paths.get(destinationFolder+destinationFileName) ;
		 try {
			Files.copy(src.toFile(), dest.toFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	
}
