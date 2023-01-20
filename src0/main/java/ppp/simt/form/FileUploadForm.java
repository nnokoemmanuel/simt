package ppp.simt.form;

import org.springframework.web.multipart.MultipartFile;
public class FileUploadForm  {

	private int id;
	private MultipartFile file;
	private String fileName;
	private String entity;
	private int number;
	private String entityID;
	
	
	public FileUploadForm() {
	}
	
	public FileUploadForm(int id,  MultipartFile file, String fileName,int number,String entityID, String entity) {
		super();
		this.id = id;
		this.file = file;
		this.fileName = fileName;
		this.number = number;
		this.entityID = entityID;
		this.entity = entity;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getEntityID() {
		return entityID;
	}
	public void setEntityID(String entityID) {
		this.entityID = entityID;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}

	@Override
	public String toString() {
		return "FileUploadForm [id=" + id + ", fileName=" + fileName + ", entity=" + entity+ ", number=" + number + ", entityID=" + entityID + "]";
	}


	
}
