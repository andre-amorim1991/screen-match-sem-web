package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodios;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

//        System.out.println("\n Top 10 episódios");
//        List<DadosEpisodios> dadosEpisodios = temporadas.stream()
//                .flatMap(temporada -> temporada.episodios().stream()).collect(Collectors.toList());
//
//        dadosEpisodios.stream()
//                .filter(episodio-> !episodio.avaliacao().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(DadosEpisodios::avaliacao).reversed())
//                .limit(10)
//                .map(episodios -> episodios.titulo().toUpperCase())
//                .forEach(System.out::println);


        List<Episodio> episodios = temporadas.stream()
                .flatMap(temporada -> temporada.episodios().stream()
                        .map(dadoEpisodio -> new Episodio(temporada.numero(), dadoEpisodio)))
                .collect(Collectors.toList());
        episodios.forEach(System.out::println);

        System.out.println("Digite o trecho do titulo para encontrar: ");
        var trechoTitulo = leitura.nextLine();
        Optional<Episodio> tituloBuscado = episodios.stream()
                .filter(episodio -> episodio.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();

        if(tituloBuscado.isPresent()){
            System.out.println("Episodio encontrado! ");
            System.out.println("Temporada; " + tituloBuscado.get().getTemporada());
        }else {
            System.out.println("Episodio não encontrado! =(");
        }

//        System.out.println("A partir de que ano vc quer ver os eposodios");
//        var ano =  leitura.nextInt();
//        leitura.nextLine();
//
//        LocalDate dataBsuca = LocalDate.of(ano, 1, 1);
//        DateTimeFormatter formataData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream().filter(episodio ->
//                episodio.getDataLancamento() != null
//                && episodio.getDataLancamento().isAfter(dataBsuca))
//                .forEach(episodio -> System.out.println(
//                        "Temporada: " + episodio.getTemporada() +
//                        " Episódio: " + episodio.getTemporada() +
//                        " Data Lançamento: " + episodio.getDataLancamento().format(formataData)));
    }
}
