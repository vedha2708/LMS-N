package com.exam.portal.Model;

import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;



@Entity
@Table(name = "nodalOfficer")
public class NodalOfficer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer organizationid;
    
    @NotBlank(message="Enter the nodal officer's name")
    private String name;
    
    @NotBlank(message="Enter the email of the nodal officer")
    private String email;
    
//    @NotBlank(message="Password cannot be blank")
    private String password;
    
  
    
    @NotBlank
    @Size(min = 10, max = 10)
    private String contactno;
    
    
    private String designation;
    
//    @NotBlank(message = "Address cannot be empty")
//    @Size(min =30,message = "Address must be at least 30 charcters long")
    private String address;
    
//    @NotBlank(message="Enter the ward of the Nodal officer")
    private String ward;
    
//    @Past(message="Registered date should be in the past")
    private Date registereddate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrganizationid() {
        return organizationid;
    }

    public void setOrganizationid(Integer organizationid) {
        this.organizationid = organizationid;
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

  

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public Date getRegistereddate() {
        return registereddate;
    }

    public void setRegistereddate(Date registereddate) {
        this.registereddate = registereddate;
    }
   
}
