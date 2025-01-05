package com.exam.portal.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="Stament must be filled")
	@Column(nullable=false,length=300)
	private String statement;

	

	@ManyToOne
	@JoinColumn(name= "exam_id", nullable=false)
	private Exam exams;


	@OneToMany(mappedBy="questions")
	private List<Option> options= new ArrayList<Option>();

	@OneToOne(mappedBy="questions")
	private Answer answer;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	
	public Exam getExams() {
		return exams;
	}

	public void setExams(Exam exams) {
		this.exams = exams;
	}

	public List<Option> getOptions() {
		return options;
	}


	public String getBaseEncodedId(){
		return Base64.getEncoder().encode(new byte[]{this.id.byteValue()}).toString();
	}
	public Answer getAnswer() {
		return answer;
	}
}
