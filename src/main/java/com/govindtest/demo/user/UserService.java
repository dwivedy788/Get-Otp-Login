package com.govindtest.demo.user;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private JavaMailSender javaMailSender;
	
	public User findUser(String userName) {
		return userRepo.findByUserName(userName);
	}
	
	public void save(User user) {
		userRepo.save(user);
	}

	public void sendOtpMessage(String to, String subject, String message) throws MessagingException {

		MimeMessage msg = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(msg, true);

		helper.setTo(to);

		helper.setSubject(subject);

		helper.setText(message, true);

		javaMailSender.send(msg);
	}

}
