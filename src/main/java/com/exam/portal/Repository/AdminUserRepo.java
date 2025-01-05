package com.exam.portal.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exam.portal.Model.AdminUser;

import jakarta.transaction.Transactional;

public interface AdminUserRepo extends JpaRepository<AdminUser, Integer> {

	@Query(value="SELECT * FROM adminuser WHERE email=:username", nativeQuery=true)
	AdminUser findByEmail(@Param("username")String username);
	
	@Modifying
    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM adminuser WHERE email IN(?1)")
	void deleteOrg(List<String> organization);

}
