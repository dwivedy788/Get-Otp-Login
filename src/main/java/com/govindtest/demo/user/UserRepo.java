package com.govindtest.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long>{

	User findByUserName(String userName);

}
