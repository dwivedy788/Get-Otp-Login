package com.govindtest.demo.user;

import java.util.Random;

import com.govindtest.demo.util.JWTUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private JWTUtil jwtUtil;


	
	public String getOtp() {
	    Random rnd = new Random();
	    int number = rnd.nextInt(999999);

	    // this will convert any number sequence into 6 character.
	    return String.format("%06d", number);
	}
	
	/* @RequestMapping(value = "/sendOtp", method = RequestMethod.POST)
	public ResponseEntity<String> sendOtp(@RequestBody String userName){
		log.info("userName is {}",userName);
		User user = userService.findUser(userName);
		if(user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}
		String otp = getOtp();
		user.setOtp(otp);
		userService.save(user);
		return ResponseEntity.ok("OTP sent successfully and the Otp is : " + otp);
	}*/


		@RequestMapping(value = "/sendOtp", method = RequestMethod.POST)
		public ResponseEntity<String> sendOtp(@RequestBody String userName) throws Exception{
			log.info("userName is {}",userName);
			User user = userService.findUser(userName);
			if(user == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
			}
			String otp = getOtp();
			user.setOtp(otp);
			userService.save(user);
			String stOtp= "Your Login OTP : "+otp;
			userService.sendOtpMessage(userName, "OTP -SpringBoot", stOtp);
			return ResponseEntity.ok("OTP sent successfully to Mail id "+userName);
		}


	@RequestMapping(value = "/validateOtp", method = RequestMethod.POST)
	public ResponseEntity<String> validateOtp(@RequestBody UserParam user){
		log.info(user.toString());
		User inUser = userService.findUser(user.getUserName());
		log.info(inUser.toString());
		log.info("Boolean : {}",!(user.getOtp().equals(inUser.getOtp())));
		if(inUser == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}else if(!(user.getOtp().equals(inUser.getOtp()))){
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Invalid Otp");
		}
		final String token = jwtUtil.generateToken(inUser);
		inUser.setOtp("");
		userService.save(inUser);
		return ResponseEntity.ok("OTP is Valid \n The token for user is : " + token);
	}
}

