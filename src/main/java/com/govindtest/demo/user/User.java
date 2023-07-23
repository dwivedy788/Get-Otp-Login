package com.govindtest.demo.user;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "_user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	private String userName;
	
	private String otp;

	

}
