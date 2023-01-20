package ppp.simt.form.evaluation;


import java.util.Date;

import ppp.simt.entity.evaluation.Course;



public class CourseForm {


	private int id;
	private int moduleId;
	private String completeName;
    private int courseCoeff;
    private int courseMaxNote;

	
	public CourseForm() {
		super();
	}

	public CourseForm(int id , int moduleId, String completeName, int courseCoeff, int courseMaxNote) {
		super();
		this.id = id;
		this.moduleId = moduleId;
		this.completeName = completeName;
		this.courseCoeff = courseCoeff;
		this.courseMaxNote = courseMaxNote;
	}

	public CourseForm(int moduleId, String completeName, int courseCoeff, int courseMaxNote) {
		super();
		this.moduleId = moduleId;
		this.completeName = completeName;
		this.courseCoeff = courseCoeff;
		this.courseMaxNote = courseMaxNote;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	
	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public String getCompleteName() {
		return completeName;
	}

	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}

	public int getCourseCoeff() {
		return courseCoeff;
	}

	public void setCourseCoeff(int courseCoeff) {
		this.courseCoeff = courseCoeff;
	}

	
	public int getCourseMaxNote() {
		return courseMaxNote;
	}

	public void setCourseMaxNote(int courseMaxNote) {
		this.courseMaxNote = courseMaxNote;
	}

	public Course convertToCourse(){
		Course course = new Course();
		
		course.setCompleteName(completeName);
		course.setCourseCoefficient(courseCoeff);
		course.setCourseMaxNote(courseMaxNote);
		return course;
		
	}
	
	public static CourseForm fromCourse(Course course){
		CourseForm courseForm = new CourseForm();	
		courseForm.setCompleteName(course.getCompleteName());
		courseForm.setCourseCoeff(course.getCourseCoefficient());
		courseForm.setCourseMaxNote(course.getCourseMaxNote());
		return courseForm;
		
	}
	
	
	
}
