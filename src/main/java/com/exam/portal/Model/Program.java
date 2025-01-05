package com.exam.portal.Model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Program {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

	//	@ManyToOne
	private Integer nodalofficerid;
	//	@ManyToOne
	private Integer organizationid;
	
	 private Integer creatorid;


	@NotBlank(message="Prgramme name is required")
	private String name;

	@NotBlank(message="Description is required")
	@Size(min=15,max=30, message = " Discription has to be at least 15 chracters")
	private String description;

	@NotNull(message = "duration is required")
    @DecimalMin(value = "0.0", message = "Duration must be greater than or equal to 0")
	private Float duration;

	
	@ManyToMany(cascade = CascadeType.REMOVE)
	@JoinTable(
			name="Enrolled_Courses",
			joinColumns=@JoinColumn(name="program_id"),
			inverseJoinColumns=@JoinColumn(name="course_id"))
	 private List<Course> courses;

	 private Date createddate;
	 
	 private String status;
	 
	 private Date verifieddate;

	 private String image;
     
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNodalofficerid() {
        return nodalofficerid;
    }

    public void setNodalofficerid(Integer nodalofficerid) {
        this.nodalofficerid = nodalofficerid;
    }

    public Integer getOrganizationid() {
        return organizationid;
    }

    public void setOrganizationid(Integer organizationid) {
        this.organizationid = organizationid;
    }

    public Integer getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(Integer creatorid) {
        this.creatorid = creatorid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getVerifieddate() {
        return verifieddate;
    }

    public void setVerifieddate(Date verifieddate) {
        this.verifieddate = verifieddate;
    }

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	
     

}
