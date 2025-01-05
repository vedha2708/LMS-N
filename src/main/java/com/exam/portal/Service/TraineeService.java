package com.exam.portal.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.exam.portal.Controller.Encrypt;
import com.exam.portal.Model.trainee;
import com.exam.portal.Repository.traineeRepository;


@Service
public class TraineeService {

 @Autowired
 traineeRepository traineeRepository;

 public void setUser(trainee trainee1, trainee user){
    trainee1.setAadharcardno(user.getAadharcardno());
    trainee1.setAbcid(user.getAbcid());
    trainee1.setCreateddate(user.getCreateddate());
    trainee1.setDistrict(user.getDistrict());
    trainee1.setDob(user.getDob());
    trainee1.setEmail(user.getEmail());
    trainee1.setEnrollmentno(user.getEnrollmentno());
    trainee1.setId(user.getId());
    trainee1.setName(user.getName());
    trainee1.setOrganizationId(user.getOrganizationId());
  //  trainee1.setPassword(new Encrypt().sha256(user.getPassword()));
    trainee1.setPermenantaddress(user.getPermenantaddress());
    trainee1.setPhoneno(user.getPhoneno());
    trainee1.setPincode(user.getPincode());
    trainee1.setResidentialaddress(user.getResidentialaddress());
    trainee1.setState(user.getState());
    traineeRepository.save(trainee1);
 }

    @Value("${lms.filepath}")
	String folderpath;
	public String uploadfile(MultipartFile file) throws Exception, IllegalStateException, IOException {
		try {
			File myDir = new File(folderpath);
			if (!myDir.exists())
				myDir.mkdirs();
			long timeadd = System.currentTimeMillis();
			
			if (file != null) {
				if (!file.isEmpty()) {
					file.transferTo(Paths.get(myDir.getAbsolutePath(), timeadd + "_" + file.getOriginalFilename()));

					return timeadd + "_" + file.getOriginalFilename();
				} else {
					return null;
				}
			} else
				return null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
    


}
