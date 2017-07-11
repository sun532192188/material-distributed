package com.material.website.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.material.website.entity.User;



public interface UserRepository  extends JpaRepository<User, Long>{

}
