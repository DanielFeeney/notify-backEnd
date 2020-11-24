package com.feeney.daniel.notify.services;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.unbescape.html.HtmlEscape;

import com.feeney.daniel.notify.services.teste.IcndbJoke;

@Service
public class PushService {

  private final RestTemplate restTemplate;

  private final FcmClient fcmClient;

  public PushService(FcmClient fcmClient) {
    this.restTemplate = new RestTemplate();
    this.fcmClient = fcmClient;
  }

	/* @Scheduled(fixedDelay = 30_000) */
  public void sendChuckQuotes() {
	  IcndbJoke joke = this.restTemplate.getForObject("http://api.icndb.com/jokes/random",
		        IcndbJoke.class);
	  sendPushMessage(HtmlEscape.unescapeHtml(joke.getValue().getJoke()));
  }

  void sendPushMessage(String joke) {
    Map<String, String> data = new HashMap<>();
    data.put("id", String.valueOf(1));
    data.put("titulo", "Titulo");
    data.put("descricao", "Descrição");

    // Send a message
    System.out.println("Mandando mensagem...");
    try {
      this.fcmClient.sendJoke(data);
    }
    catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

}
