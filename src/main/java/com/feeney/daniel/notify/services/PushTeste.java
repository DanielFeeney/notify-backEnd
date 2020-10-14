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
public class PushTeste {

  private final RestTemplate restTemplate;

  private final FcmClient fcmClient;

  private int id = 0;

  public PushTeste(FcmClient fcmClient) {
    this.restTemplate = new RestTemplate();
    this.fcmClient = fcmClient;
  }

  @Scheduled(fixedDelay = 30_000)
  public void sendChuckQuotes() {
		/*
		 * IcndbJoke joke =
		 * this.restTemplate.getForObject("http://api.icndb.com/jokes/random",
		 * IcndbJoke.class);
		 */
    sendPushMessage(HtmlEscape.unescapeHtml("Mandando uma mensagem"));
  }

  void sendPushMessage(String joke) {
    Map<String, String> data = new HashMap<>();
    data.put("id", String.valueOf(++this.id));
    data.put("text", joke);

    // Send a message
    System.out.println("Sending chuck joke...");
    try {
      this.fcmClient.sendJoke(data);
    }
    catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }

}
