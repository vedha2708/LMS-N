package com.exam.portal.Model;

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "organisers", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Organiser implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(nullable=false,unique=true,length=50)
	private String email;
	
	@NotBlank(message="Name can't be blank")
	@Column(nullable=false,length=50)
	private String name;
	
	@NotBlank(message="Password can't be blank")
	@Size(min = 8, message = "Password must be at least 8 characters long")
	@Column(nullable=false,length=250)
	private String password;
	
	

}