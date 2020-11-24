package com.feeney.daniel.notify.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class MessageService {

	/* private final Set<String> tokenRegistry = new CopyOnWriteArraySet<>(); */

	private final FcmClient fcmClient;

	public MessageService(FcmClient fcmClient) {
		this.fcmClient = fcmClient;
	}

	/*
	 * public void addToken(String token) { this.tokenRegistry.add(token); }
	 * 
	 * public void removeToken(String token) { this.tokenRegistry.remove(token); }
	 */

	/* @Scheduled(fixedDelay = 30000) */
	public void sendPushMessages(String token, String id, String titulo, String descricao) {
		System.out.println("Sending personal message to: " + token);
		Map<String, String> data = new HashMap<>();
		data.put("id", id);
		data.put("titulo", titulo);
		data.put("descricao", descricao);

		try {
			this.fcmClient.sendPersonalMessage(token, data);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

	}

}
