package com.exam.portal.Model;

import java.util.Date;



import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



@Entity
@Table(name = "register_organization")
public class Organization {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "organization_id")
	private int organizationId;
	
	
	@NotBlank(message = " Organization Name cannot be empty")
	@Size(min=10, message = " Organization-name is too short")
	public String organization_name;

	@Email
	@NotBlank(message="Enter the email id of the Organization")
	public String email_id;
	
	
	public Date registered_date;

	@NotBlank(message = " Discription cannot be empty")
	@Size(min=20, message = " Discription is too much short")
	private String description;

	
	private Boolean status;

	@NotBlank(message = "Address cannot be empty")
	@Size(min=20,message="Address must be at least 50 charcters long")
	private String Address;

	@NotBlank(message = "city cannot be empty")
	private String city;
	
	@NotBlank(message = "State cannot be empty")
	private String state;
	
	@NotBlank(message = "Country cannot be empty")
	private String country;

	@NotBlank(message="Enter the Contact no. of the Orgaization")
	@Size(min = 10, max = 10)
	public String contact_no;

	public int getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(int organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganization_name() {
		return organization_name;
	}

	public void setOrganization_name(String organization_name) {
		this.organization_name = organization_name;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public Date getRegistered_date() {
		return registered_date;
	}

	public void setRegistered_date(Date registered_date) {
		this.registered_date = registered_date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getContact_no() {
		return contact_no;
	}

	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}

	public Organization(int organizationId,
			String organization_name,
			String email_id, Date registered_date,
			 String description,
			Boolean status, String address,
			 String city,
			 String state,
			 String country,
			 String contact_no) {
		super();
		this.organizationId = organizationId;
		this.organization_name = organization_name;
		this.email_id = email_id;
		this.registered_date = registered_date;
		this.description = description;
		this.status = status;
		Address = address;
		this.city = city;
		this.state = state;
		this.country = country;
		this.contact_no = contact_no;
	}

	public Organization() {
		super();
		
	}

	@Override
	public String toString() {
		return "Organization [organizationId=" + organizationId + ", organization_name=" + organization_name
				+ ", email_id=" + email_id + ", registered_date=" + registered_date + ", description=" + description
				+ ", status=" + status + ", Address=" + Address + ", city=" + city + ", state=" + state + ", country="
				+ country + ", contact_no=" + contact_no + "]";
	}
	


}

