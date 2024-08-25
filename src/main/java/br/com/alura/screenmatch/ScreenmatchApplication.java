package br.com.alura.screenmatch;

import br.com.alura.screenmatch.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ScreenmatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {


        Principal principal = new Principal();
        principal.exibeMenu();
//		json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore-girls&season=2&episode=2&apikey=6585022c");
//		ConverteDados conversorEpisodios = new ConverteDados();
//		DadosEpisodios dadosEpisodios = conversorEpisodios.obeterDados(json, DadosEpisodios.class);
//		System.out.println(dadosEpisodios);

    }
}
