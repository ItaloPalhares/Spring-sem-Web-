package services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class ConsumoApi {
	
	public String obterDados(String endereco) {
		
		HttpClient client = HttpClient.newHttpClient();

		HttpRequest request = HttpRequest.newBuilder()
		      .uri(URI.create(endereco))
		      .build();

		HttpResponse<String> response = null;
		try {
			response = client
					  .send(request, BodyHandlers.ofString());
			
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} String json = response.body();
		return json;
		
	}

}