package ppp.simt.form;

import org.springframework.web.multipart.MultipartFile;

import ppp.simt.entity.production.OutSlip;

public class OutSlipForm {

	private String filename;
	private MultipartFile file;
	//private int id=0;

    public OutSlipForm(String filename, MultipartFile file) {
		// TODO Auto-generated constructor stub
		super();
		//this.id=id;
		this.file=file;
		this.filename=filename;
		
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	/*public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	*/
	
	public OutSlip toSlip() {
		OutSlip slip=new OutSlip();
		
		return slip;
	}
	
	
}
