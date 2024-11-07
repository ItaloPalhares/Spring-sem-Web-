package br.com.alura.screenmatch2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import models.DadosSeries;
import services.ConsumoApi;
import services.ConverteDados;

@SpringBootApplication
public class Screenmatch2Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Screenmatch2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("primeiro Projeto Spring Sem Web");
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=242deea2");
		System.out.println(json);
		
		var conversor = new ConverteDados();
		var infoSerie = conversor.obterDados(json, DadosSeries.class);
	    System.out.println(infoSerie);
		
		
	}

}
