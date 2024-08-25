package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodios;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);

    private final String HOST = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=6585022c";

    private ConsumoApi consumoApi = new ConsumoApi();

    private  ConverteDados conversor = new ConverteDados();

    private List<DadosTemporada> temporadas = new ArrayList<>();

    public void exibeMenu(){

        System.out.println("Digite o nome da Série para busca");

        var nomeSerie = leitura.nextLine();

        var json =  consumoApi.obterDados(HOST + nomeSerie.replace(" ","+") + API_KEY);
        System.out.println(json);

        DadosSerie dadosSerie = conversor.obeterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

		for (int i = 1; i<= dadosSerie.totalTemporadas(); i++){
			json =  consumoApi.obterDados(HOST + nomeSerie.replace(" ", "+") + "&season=" + i + "&apikey=6585022c");
			DadosTemporada dadosTemporada = conversor.obeterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
//		temporadas.forEach(System.out::println);
        temporadas.forEach(temporadas -> temporadas.episodios().forEach(episodios -> System.out.println(episodios.titulo())));

    }
}
