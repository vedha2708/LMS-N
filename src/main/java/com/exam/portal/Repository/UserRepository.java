package com.exam.portal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.portal.Model.User;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value="SELECT * FROM users u WHERE u.email = ?1", nativeQuery = true)
    User findByEmail(String email);
}