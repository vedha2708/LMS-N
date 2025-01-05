package com.exam.portal.Model;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.Size;

import java.util.*;



@Entity
@Table(name = "exams")
public class Exam {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false,name = "title")
	private String title;
	
	@Size(min = 30, message = "Description must be at least 50 characters long.")
	@Column(name = "description")
	private String description;
	
	@Size(min = 30, message = "Instructions must be at least 50 characters long.")
	@Column(name = "instructions")
	private String	instructions;

	@DateTimeFormat(pattern = "dd/MM/yyyy h:mm a")
	@Column(nullable = false,name = "start_date")
	private Date startDate;
	
	@Column(nullable=false,length=5,name = "marks")
	private int marksOfEachQuestion ;
	
	@Column(nullable=false,length=5,name = "time")
	private int examTime;
	
	@Column(length=5,name = "negative_marks",nullable = true)
	@NegativeOrZero(message = "NegativeMarks cannot be positive")
	private int negativeMarkOfEachQuestion;
	
	
	
	
	
	@ManyToOne
	@JoinColumn(name= "creator_id", nullable= false)
	private AdminUser adminuser;
	public AdminUser getAdminuser() {
		return adminuser;
	}

	public void setAdminuser(AdminUser adminuser) {
		this.adminuser = adminuser;
	}


	@OneToMany(mappedBy="exams")
	private List<Question> questions= new ArrayList<Question>();

	public List<UserExam> getUserExam() {
		return userExam;
	}

	public void setUserExam(List<UserExam> userExam) {
		this.userExam = userExam;
	}

	@OneToMany(mappedBy="exams")
	private List<UserExam> userExam;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public int getMarksOfEachQuestion() {
		return marksOfEachQuestion;
	}

	public void setMarksOfEachQuestion(int marksOfEachQuestion) {
		this.marksOfEachQuestion = marksOfEachQuestion;
	}

	public int getExamTime() {
		return examTime;
	}

	public void setExamTime(int timeOfEachQuestion) {
		this.examTime = timeOfEachQuestion;
	}

	public int getNegativeMarkOfEachQuestion() {
		return negativeMarkOfEachQuestion;
	}

	public void setNegativeMarkOfEachQuestion(int negativeMarkOfEachQuestion) {
		this.negativeMarkOfEachQuestion = negativeMarkOfEachQuestion;
	}

	

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public List<Question> getQuestions() {
		return questions;
	}
	public String getExamCode(){
		String[] titleList=this.title.split(" ");
		StringBuilder Code=new StringBuilder();
		for (String t:titleList) {
			char c=t.charAt(0);
			if(Character.isLetter(c) || Character.isDigit(c)){
				Code.append(c);
			}
		}
		String[] letters={"EXAM","XAM","MOK","LT","MDS","FPA","KU","POU","KIH","RTU"};
		if(Code.toString().equals("")){
			int index= (int) (this.id % 10);
			Code.append(letters[index]);
		}
		return Code.toString().toUpperCase()+"-"+this.id.toString();
	}

	public int getNextQuestionNo(Long question_id) {
		List<Question> questions = this.getQuestions();
		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i).getId().equals(question_id)) {
				if (i == questions.size() - 1) {
					// Last Question, return the same question number
					return i + 1;
				} else {
					// Return the next question number
					return i + 2;
				}
			}
		}
		// Question not found, return 1 as default
		return 1;
	}
	
	public boolean isLastQuestion(Long question_id){
		List<Question> questions_list = this.getQuestions();
		return questions_list.get(questions_list.size() - 1).getId().equals(question_id);
	}

	public int calculateScore(int correctAnswers,int incorrectAnswer){

		int totalMarks=this.getMarksOfEachQuestion()*this.getQuestions().size();
		int markScored=correctAnswers*this.getMarksOfEachQuestion();
		int negativeMark=incorrectAnswer*this.getNegativeMarkOfEachQuestion();
		int actualScore=markScored-negativeMark;

		return actualScore;
	}

	public boolean isOver(){
		long time=this.getExamTime()*60*1000;
		long currentTime=new Date().getTime();
		long examTime =this.getStartDate().getTime();
		return examTime+time <= currentTime;
	}
	public boolean isStarted(){
		long time=this.getExamTime()*60*1000;
		long currentTime=new Date().getTime();
		long examTime =this.getStartDate().getTime();
		if(examTime < currentTime && currentTime < examTime + time)
			return true;
		else
			return false;
	}

	public String getExamStatus(){
		long time=this.getExamTime()*60*1000;
		long currentTime=new Date().getTime();
		long examTime =this.getStartDate().getTime();
		if(isOver()){
			return "Exam Over";
		} else if(examTime < currentTime && currentTime < examTime + time){
			return "Started";
		}else{
			return "Not Started";
		}
	}
	public  long getRemainingTime(){
		long currentTime=new Date().getTime();
		long examTime =this.getStartDate().getTime();
		long time=this.getExamTime()*60*1000;
		return examTime+time-currentTime;
	}

	public long getStartTimeInMilliseconds(){
		return this.getStartDate().getTime()-new Date().getTime();
	}
}