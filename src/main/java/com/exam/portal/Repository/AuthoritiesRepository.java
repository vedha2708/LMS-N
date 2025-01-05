package com.exam.portal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.portal.Model.Authorities;

public interface AuthoritiesRepository extends JpaRepository<Authorities,Long> {

}
