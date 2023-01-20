package ppp.simt.form;

import org.springframework.web.multipart.MultipartFile;

import ppp.simt.entity.production.InSlip;

public class InSlipForm {
	
	private String filename;
	private MultipartFile file;
	private int id=0;
	private String slipNumber;
	private int total;
	
    public InSlipForm(String filename, MultipartFile file,int id, String slipNumber, int total) {
		// TODO Auto-generated constructor stub
		super();
		this.id=id;
		this.file=file;
		this.filename=filename;
		this.total=total;
		this.slipNumber=slipNumber;
		
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	public InSlip toSlip() {
		InSlip slip=new InSlip();
		
		return slip;
	}

	public String getSlipNumber() {
		return slipNumber;
	}

	public void setSlipNumber(String slipNumber) {
		this.slipNumber = slipNumber;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
