package com.exam.portal.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	public User(String email, String name) {
		this.email=email;
		this.name=name;
	}

	public User() {

	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	@Column(nullable=false,unique=true,length=50)
	private String email;
	
	@NotBlank(message="Name can't be empty")
	@Column(nullable=false,length=50)
	private String name;

}
