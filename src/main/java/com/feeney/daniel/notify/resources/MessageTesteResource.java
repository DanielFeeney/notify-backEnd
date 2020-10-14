package com.feeney.daniel.notify.resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.feeney.daniel.notify.services.MessageTesteService;

@RestController
@CrossOrigin
@RequestMapping("/message")
public class MessageTesteResource {
	
	@Autowired
	private MessageTesteService pushSender;
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void register(@RequestParam("token") String token) {
	System.out.println("register: " + token);
	    this.pushSender.addToken(token);
	}
	
	@PostMapping("/unregister")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void unregister(@RequestParam("token") String token) {
	System.out.println("unregister: " + token);
	    this.pushSender.removeToken(token);
	}

}
