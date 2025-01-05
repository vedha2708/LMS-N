package com.exam.portal.Model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;




@Entity
@Table(name = "topic_registration")
public class Topic {
	
	@OneToMany(mappedBy="topic",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Assignment> assignments;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sequenceNo;
   

    // @ManyToOne
    private int topicCreatorId;

    @Column(name = "topic_id")
    private int topicId;
    

    @NotBlank(message = " Topic Name cannot be empty")
    private String topicName;

    @NotBlank(message = " Descrpition cannot be empty")
    @Size(min=15,max=150, message = " Discription have to at least 15 characters")
    private String topicDescrpition;
    
//    @NotEmpty(message="Videofile cannot be empty")
    private String videoFile;
    
//    @NotNull(message="Transcriptfile cannot be empty")
    private String transcriptFile;
    
//    @NotNull(message="Pdffile cannot be empty")
    private String pdfFile;
    
//    private List<modules> modules;
//
//
//	public List<modules> getModules() {
//		return modules;
//	}
//
//	public void setModules(List<modules> modules) {
//		this.modules = modules;
//	}

	public List<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public int getTopicCreatorId() {
		return topicCreatorId;
	}

	public void setTopicCreatorId(int topicCreatorId) {
		this.topicCreatorId = topicCreatorId;
	}

	public int getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(int sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getTopicDescrpition() {
		return topicDescrpition;
	}

	public void setTopicDescrpition(String topicDescrpition) {
		this.topicDescrpition = topicDescrpition;
	}

	public String getVideoFile() {
		return videoFile;
	}

	public void setVideoFile(String videoFile) {
		this.videoFile = videoFile;
	}

	public String getTranscriptFile() {
		return transcriptFile;
	}

	public void setTranscriptFile(String transcriptFile) {
		this.transcriptFile = transcriptFile;
	}

	public String getPdfFile() {
		return pdfFile;
	}

	public void setPdfFile(String pdfFile) {
		this.pdfFile = pdfFile;
	}
	
	

		public Topic(List<Assignment> assignments, int topicId, int topicCreatorId, int sequenceNo,
			@NotBlank(message = " Topic Name cannot be empty") String topicName,
			@NotBlank(message = " Descrpition cannot be empty") @Size(min = 15, max = 150, message = " Discription has invalid characters") String topicDescrpition,
			String videoFile, String transcriptFile, String pdfFile) {
		super();
		this.assignments = assignments;
		this.topicId = topicId;
		this.topicCreatorId = topicCreatorId;
		this.sequenceNo = sequenceNo;
		this.topicName = topicName;
		this.topicDescrpition = topicDescrpition;
		this.videoFile = videoFile;
		this.transcriptFile = transcriptFile;
		this.pdfFile = pdfFile;
	}
		
		
		public Topic() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Transient
	    public String getFile() {
	        if (pdfFile== null || sequenceNo == 0 ) return null;
	         
	        return "/user-files/" + pdfFile;
	    }

		@Transient
	    public String getFiles() {
	        if (transcriptFile == null || sequenceNo == 0 ) return null;
	         
	        return "/user-files/" + transcriptFile;
	    }

		@Transient
	    public String getVideo() {
	        if ( videoFile == null || sequenceNo == 0 ) return null;
	         
	        return "/user-files/" + videoFile;
	    }

    
    
}
