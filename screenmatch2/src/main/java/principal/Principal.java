package principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import models.DadosEpisodios;
import models.DadosSeries;
import models.DadosTemporadas;
import models.Episodio;
import services.ConsumoApi;
import services.ConverteDados;

public class Principal {

	ConsumoApi consumir = new ConsumoApi();
	Scanner read = new Scanner(System.in);
	ConverteDados conversor = new ConverteDados();
	private final String ENDERECO = "https://www.omdbapi.com/?t=";
	private final String API_KEY = "&apikey=242deea2";

	public void exibeMenu() {
		System.out.println("Insira o nome da série desejada:");
		var titulo = read.nextLine();
		var json = consumir.obterDados(ENDERECO + titulo.replace(" ", "+") + API_KEY);
		var infoSerie = conversor.obterDados(json, DadosSeries.class);
		System.out.println(infoSerie);

		List<DadosTemporadas> temporadas = new ArrayList<>();

		for (int i = 1; i <= infoSerie.totalTemporadas(); i++) {
			json = consumir.obterDados(ENDERECO + titulo.replace(" ", "+") + "&season=" + i + API_KEY);
			var infoTemporadas = conversor.obterDados(json, DadosTemporadas.class);
			temporadas.add(infoTemporadas);

		}

		temporadas.forEach(System.out::println);

//		temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
		
		List<DadosEpisodios> listaEpisodios = temporadas.stream()
				.flatMap(t -> t.episodios().stream())
				.collect(Collectors.toList());
		
		listaEpisodios.stream()
			.filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
			.sorted(Comparator.comparing(DadosEpisodios::avaliacao).reversed())
			.limit(5)
			.forEach(System.out::println);
		
		List<Episodio> episodios = temporadas.stream()
				.flatMap(t -> t.episodios().stream()
						.map(d -> new Episodio(t.temporada(),d))
				).collect(Collectors.toList());
		
		episodios.forEach(System.out::println);
		
		System.out.println("A partir de que ano você deseja verificar os episódios? ");
		System.out.print("Inserir ano: ");
		var ano = read.nextInt();
		read.nextLine();
		
		LocalDate dataBusca = LocalDate.of(ano, 1, 1);
		
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		episodios.stream()
			.filter(e -> e.getDataLancamento()!= null && e.getDataLancamento().isAfter(dataBusca))
			.forEach(e-> System.out.println(
					"Temporada: " + e.getTemporada()+"\r\n"+
						"Episódio: " + e.getTitulo()+ "\r\n"+
						"Data de Lançamento: " + e.getDataLancamento().format(formatador)
					));
		
			
		

	}
}
