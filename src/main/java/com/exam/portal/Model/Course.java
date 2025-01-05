package com.exam.portal.Model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Entity
@Table(name="course")
public class Course {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    private int courseId;
	private int creatortid;
	@NotBlank(message="Enter the course name")
	private String courseName;
	
	@NotBlank(message="Enter the course CourseAbbrevation")
	@Size(max = 15, message = "Course Abbrevation must be at least 15 characters long.")
	private String courseAbbrevation;
	
	@NotBlank(message="Enter the Course Course Description")
	@Size(min = 15, message = "Course description must be at least 15 characters long.")
	private String courseDescription;
	
//	@NotBlank(message="Please select the Course categary")
	private String courseCategory;
	private Date createdDate;
	
	private String image;
	 
	@ManyToMany
	@JoinTable(
			name="Enrolled_Modules",
			joinColumns=@JoinColumn(name="course_id"),
			inverseJoinColumns=@JoinColumn(name="module_id"))
	
	private List<modules> modules;
	
	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Course(int courseId, int creatortid, String courseName, String courseAbbrevation, String courseDescription,
			String courseCategory, Date createdDate) {
		super();
		this.courseId = courseId;
		this.creatortid = creatortid;
		this.courseName = courseName;
		this.courseAbbrevation = courseAbbrevation;
		this.courseDescription = courseDescription;
		this.courseCategory = courseCategory;
		this.createdDate = createdDate;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getCreatortid() {
		return creatortid;
	}

	public void setCreatortid(int creatortid) {
		this.creatortid = creatortid;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseAbbrevation() {
		return courseAbbrevation;
	}

	public void setCourseAbbrevation(String courseAbbrevation) {
		this.courseAbbrevation = courseAbbrevation;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public String getCourseCategory() {
		return courseCategory;
	}

	public void setCourseCategory(String courseCategory) {
		this.courseCategory = courseCategory;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public List<modules> getModules() {
		return modules;
	}

	public void setModules(List<modules> modules) {
		this.modules = modules;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
	

}
