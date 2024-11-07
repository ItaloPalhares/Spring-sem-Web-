package models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSeries(@JsonAlias("Title") String titulo,
		                  @JsonAlias("TotalSeasons")Integer totalTemporadas,
		                  @JsonAlias("imdbRating")String avaliacao) {

}
