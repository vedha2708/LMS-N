package com.exam.portal.Model;




import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import jakarta.persistence.Transient;


@Entity
@Table(name="assignment")
public class Assignment {
	
	
	
//	@OneToMany(mappedBy="assignment",cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<UserModuleEnrollment> userModuleEnrollments;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="assignmentid")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="sequenceNo")
	private Topic topic;
	
	
	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="moduleId")
	private modules modules;
	
	private String name;
	
	private Integer marks;
	
	private String uploadassignment;  // The upload will be the admin side and the user side it will be the view assigment button
	
	private String viewassignment;   // The upload will be the user side and the admin side it will be the view assignment button
	
	private String assdes;
	
	private String duedate;
	
	private Boolean status;

	@ManyToMany
    @JoinTable(
    name = "topic_assignment", 
    joinColumns = @JoinColumn(name = "assignment_id"), 
    inverseJoinColumns = @JoinColumn(name = "topic_id")  
)
	private List<Topic> topics;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public modules getModules() {
		return modules;
	}

	public void setModules(modules modules) {
		this.modules = modules;
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMarks() {
		return marks;
	}

	public void setMarks(Integer marks) {
		this.marks = marks;
	}

	public String getUploadassignment() {
		return uploadassignment;
	}

	public void setUploadassignment(String uploadassignment) {
		this.uploadassignment = uploadassignment;
	}

	public String getViewassignment() {
		return viewassignment;
	}

	public void setViewassignment(String viewassignment) {
		this.viewassignment = viewassignment;
	}
	

	public String getAssdes() {
		return assdes;
	}

	public void setAssdes(String assdes) {
		this.assdes = assdes;
	}

	public String getDuedate() {
		return duedate;
	}

	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	
	
	public Assignment(Integer id, com.exam.portal.Model.modules modules, Integer marks, String uploadassignment,
			String viewassignment, String assdes, String duedate, Boolean status) {
		super();
		this.id = id;
		this.modules = modules;
		this.marks = marks;
		this.uploadassignment = uploadassignment;
		this.viewassignment = viewassignment;
		this.assdes = assdes;
		this.duedate = duedate;
		this.status = status;
		
	}

	public Assignment() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Transient
    public String getFile() {
        if (uploadassignment== null  ) return null;
         
        return "/user-files/" + uploadassignment;
    }

	@Transient
    public String getFiles() {
        if (viewassignment == null  ) return null;
         
        return "/user-files/" + viewassignment;
    }

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}
	
	
	
}