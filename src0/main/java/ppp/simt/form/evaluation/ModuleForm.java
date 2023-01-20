package ppp.simt.form.evaluation;

import javax.persistence.Column;

public class ModuleForm {


	private int id;
	private String completeName;
	private String specialityAbbreviation;
	private int moduleMinNote;
	private int moduleClassification;
	private int modulePercentage;
	
	public int getModuleMinNote() {
		return moduleMinNote;
	}


	public void setModuleMinNote(int moduleMinNote) {
		this.moduleMinNote = moduleMinNote;
	}

	
	

	
	public int getModuleClassification() {
		return moduleClassification;
	}


	public void setModuleClassification(int moduleClassification) {
		this.moduleClassification = moduleClassification;
	}
	
    
	public String getCompleteName() {
		return completeName;
	}

	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}

	

	public ModuleForm() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getSpecialityAbbreviation() {
		return specialityAbbreviation;
	}

	public void setSpecialityAbbreviation(String specialityAbbreviation) {
		this.specialityAbbreviation = specialityAbbreviation;
	}
	
    
	public int getModulePercentage() {
		return modulePercentage;
	}


	public void setModulePercentage(int modulePercentage) {
		this.modulePercentage = modulePercentage;
	}


	public ModuleForm(int id, String completeName, String specialityAbbreviation,int moduleMinNote,int modulePercentage) {
		super();
		this.id = id;
		this.completeName = completeName;
		this.specialityAbbreviation = specialityAbbreviation;
		this.moduleMinNote=moduleMinNote;
		this.modulePercentage=modulePercentage;
	}

	@Override
	public String toString() {
		return "ModuleForm [completeName=" + completeName + ", specialityAbbreviation=" + specialityAbbreviation  + "]";
	}
	
	
	
}
