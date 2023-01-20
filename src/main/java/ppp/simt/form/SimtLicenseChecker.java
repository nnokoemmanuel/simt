package ppp.simt.form;

import java.util.Date;

public class SimtLicenseChecker {
private String can_be_register;
private String can_be_presented;
private Date CatB_Date;
private SimtPerson person;
private String categories;
public String getCan_be_register() {
	return can_be_register;
}
public void setCan_be_register(String can_be_register) {
	this.can_be_register = can_be_register;
}
public String getCan_be_presented() {
	return can_be_presented;
}
public void setCan_be_presented(String can_be_presented) {
	this.can_be_presented = can_be_presented;
}
public Date getCatB_Date() {
	return CatB_Date;
}
public void setCatB_Date(Date catB_Date) {
	CatB_Date = catB_Date;
}
public SimtPerson getPerson() {
	return person;
}
public void setPerson(SimtPerson person) {
	this.person = person;
}
public String getCategories() {
	return categories;
}
public void setCategories(String categories) {
	this.categories = categories;
}


}
