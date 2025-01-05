package com.exam.portal.Model;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name="adminuser")
public class AdminUser {
  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
//	@NotBlank(message = "Admin-name cannot be empty")
	private String name;
	
//	@NaturalId(mutable=true)
	@NotBlank(message="email cannot be blank")
	private String email;
//	@NotBlank(message = "Password cannot be empty")
//  @Size(min = 8, message = "Password must be at least 8 characters long")
	private String password;
    private String role;
    private String rights;
    private Date createdate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_authorities",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authorities> authorities = new HashSet<>();

    @OneToMany(mappedBy = "adminuser")
	private List<Exam> exams =new ArrayList<Exam>();


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getRights() {
		return rights;
	}
	public Set<Authorities> getAuthorities(){
		return authorities;
	}
	public void setAuthorities(Set<Authorities> authorities){
		this.authorities=authorities;
	}
	public void setRights(String rights) {
		this.rights = rights;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
    
}
