package com.exam.portal.Model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="trainee", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class trainee {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
//    @Column(name="trainee_id")
    private int id;
  
    @NotBlank(message = "Name cannot be empty")
    private String name;
    
    private String email;
  
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
  
    @NotBlank(message = "Confirm password will be required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String confirmpassword;
//  
//    @NotBlank(message = "phoneno cannot be empty")
//    @Size(min = 10, max = 10)
    private String phoneno;
  
    private String dob;
  
//    @NotBlank(message = "Photo is required")
    private String photo;
  
//    @NotBlank(message = "residentialaddress cannot be empty")
//    @Size(min=50, message = " Address has to be 50 characters long")
    private String residentialaddress;
//  
//    @NotBlank(message = "permenantaddress cannot be empty")
//    @Size(min=50, message = " Address has to be 50 charcters long")
    private String permenantaddress;
  
//    @NotBlank(message = "District cannot be empty")
    private String district;
  
//    @NotBlank(message = "Pincode cannot be empty")
//    @Size(min = 4, max = 12)
    private String pincode;
  
//    @NotBlank(message = "State cannot be empty")
    private String state;
    
//    @NotBlank(message="Profile summary should be filled")
    private String aboutme;
  
//    @NotBlank(message="Add you twitter profile link")
    private String twitter;
    
//    @NotBlank(message="Add your instargram profile link")
    private String instagram;
    
    
//     @NotBlank(message = "Aadhar card number is required")
    private String aadharcardno;
  
//     @NotBlank(message = "Aadhar card file is required")
    private String aadharcardfile;
  
//     @NotBlank(message = "Enrollment number is required")
    private String enrollmentno;
  
//     @NotBlank(message = "ID card photo is required")
    private String idcardphoto;
  
//     @NotBlank(message = "ABC ID is required")
    private String abcid;
    
    @ManyToOne
    @JoinColumn(name="program_id")
    private Program program;
  
//    @NotBlank(message = "Created date is required")
    private Date createddate;

    private Integer organizationId;

   

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

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getResidentialaddress() {
        return residentialaddress;
    }

    public void setResidentialaddress(String residentialaddress) {
        this.residentialaddress = residentialaddress;
    }

    public String getPermenantaddress() {
        return permenantaddress;
    }

    public void setPermenantaddress(String permenantaddress) {
        this.permenantaddress = permenantaddress;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAadharcardno() {
        return aadharcardno;
    }

    public void setAadharcardno(String aadharcardno) {
        this.aadharcardno = aadharcardno;
    }

    public String getAadharcardfile() {
        return aadharcardfile;
    }

    public void setAadharcardfile(String aadharcardfile) {
        this.aadharcardfile = aadharcardfile;
    }

    public String getEnrollmentno() {
        return enrollmentno;
    }

    public void setEnrollmentno(String enrollmentno) {
        this.enrollmentno = enrollmentno;
    }

    public String getIdcardphoto() {
        return idcardphoto;
    }

    public void setIdcardphoto(String idcardphoto) {
        this.idcardphoto = idcardphoto;
    }

    public String getAbcid() {
        return abcid;
    }

    public void setAbcid(String abcid) {
        this.abcid = abcid;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

	public String getAboutme() {
		return aboutme;
	}

	public void setAboutme(String aboutme) {
		this.aboutme = aboutme;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}
	
	
	
	public trainee(Integer id, String name, String email,
			 String password,
			 String confirmpassword,
			String phoneno, String dob, String photo, String residentialaddress, String permenantaddress,
			String district, String pincode, String state, String aboutme, String twitter, String instagram,
			String aadharcardno, String aadharcardfile, String enrollmentno, String idcardphoto, String abcid,
			Date createddate, Integer organizationId) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.confirmpassword = confirmpassword;
		this.phoneno = phoneno;
		this.dob = dob;
		this.photo = photo;
		this.residentialaddress = residentialaddress;
		this.permenantaddress = permenantaddress;
		this.district = district;
		this.pincode = pincode;
		this.state = state;
		this.aboutme = aboutme;
		this.twitter = twitter;
		this.instagram = instagram;
		this.aadharcardno = aadharcardno;
		this.aadharcardfile = aadharcardfile;
		this.enrollmentno = enrollmentno;
		this.idcardphoto = idcardphoto;
		this.abcid = abcid;
		this.createddate = createddate;
		this.organizationId = organizationId;
	}

	public trainee() {
		super();
		
	}
    

  
    

}
