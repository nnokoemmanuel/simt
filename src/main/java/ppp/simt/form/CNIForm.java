package ppp.simt.form;

import java.util.Date;


public class CNIForm  {

	private int id;
	private String nic;	
	private String surnameCni;
	private String givenNameCni;
	private String pobCni;
	private String  dobCni;
	private String genderCni;
	private String fatherCni;
	private String motherCni;
	private String professionCni;
	private String addressCni;
	private String heightCni;
	private String  issuedDateCni;
	private String language;
	private String diplome;
	private String diplomeIssuedDate;
	private String  diplomeIssuedPlace;
	private String diplomeOption;
	
	
	
	public CNIForm() {
		super();
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNic() {
		return nic;
	}


	public void setNic(String nic) {
		this.nic = nic;
	}


	public String getSurnameCni() {
		return surnameCni;
	}


	public void setSurnameCni(String surnameCni) {
		this.surnameCni = surnameCni;
	}


	public String getGivenNameCni() {
		return givenNameCni;
	}


	public void setGivenNameCni(String givenNameCni) {
		this.givenNameCni = givenNameCni;
	}


	public String getPobCni() {
		return pobCni;
	}


	public void setPobCni(String pobCni) {
		this.pobCni = pobCni;
	}


	public String getDobCni() {
		return dobCni;
	}


	public void setDobCni(String dobCni) {
		this.dobCni = dobCni;
	}


	public String getGenderCni() {
		return genderCni;
	}


	public void setGenderCni(String genderCni) {
		this.genderCni = genderCni;
	}


	public String getFatherCni() {
		return fatherCni;
	}


	public void setFatherCni(String fatherCni) {
		this.fatherCni = fatherCni;
	}


	public String getMotherCni() {
		return motherCni;
	}


	public void setMotherCni(String motherCni) {
		this.motherCni = motherCni;
	}


	public String getProfessionCni() {
		return professionCni;
	}


	public void setProfessionCni(String professionCni) {
		this.professionCni = professionCni;
	}


	public String getAddressCni() {
		return addressCni;
	}


	public void setAddressCni(String addressCni) {
		this.addressCni = addressCni;
	}


	public String getHeightCni() {
		return heightCni;
	}


	public void setHeightCni(String heightCni) {
		this.heightCni = heightCni;
	}


	public String getIssuedDateCni() {
		return issuedDateCni;
	}


	public void setIssuedDateCni(String issuedDateCni) {
		this.issuedDateCni = issuedDateCni;
	}

	

	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getDiplome() {
		return diplome;
	}


	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}


	public String getDiplomeIssuedDate() {
		return diplomeIssuedDate;
	}


	public void setDiplomeIssuedDate(String diplomeIssuedDate) {
		this.diplomeIssuedDate = diplomeIssuedDate;
	}


	public String getDiplomeIssuedPlace() {
		return diplomeIssuedPlace;
	}


	public void setDiplomeIssuedPlace(String diplomeIssuedPlace) {
		this.diplomeIssuedPlace = diplomeIssuedPlace;
	}


	public String getDiplomeOption() {
		return diplomeOption;
	}


	public void setDiplomeOption(String diplomeOption) {
		this.diplomeOption = diplomeOption;
	}


	@Override
	public String toString() {
		return "CNIForm [id=" + id + ", nic=" + nic + ", surnameCni=" + surnameCni + ", givenNameCni=" + givenNameCni
				+ ", pobCni=" + pobCni + ", dobCni=" + dobCni + ", genderCni=" + genderCni + ", fatherCni=" + fatherCni
				+ ", motherCni=" + motherCni + ", professionCni=" + professionCni + ", addressCni=" + addressCni
				+ ", heightCni=" + heightCni + ", issuedDateCni=" + issuedDateCni + ", diplomeIssueDate=" + diplomeIssuedDate + "]";
	}
	
	
}
