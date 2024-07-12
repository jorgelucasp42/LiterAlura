package com.alura.literalura.servico;

import com.alura.literalura.modelo.Autor;
import com.alura.literalura.modelo.Livro;
import com.alura.literalura.repositorio.AutorRepositorio;
import com.alura.literalura.repositorio.LivroRepositorio;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroServico {
    @Autowired
    private LivroRepositorio livroRepositorio;

    @Autowired
    private AutorRepositorio autorRepositorio;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Livro buscarLivroPorTitulo(String titulo) throws Exception {
        String url = "https://gutendex.com/books?title=" + titulo;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode root = objectMapper.readTree(response.body());

        if (root.get("results").isArray() && root.get("results").size() > 0) {
            JsonNode livroNode = root.get("results").get(0);

            Livro livro = new Livro();
            livro.setTitulo(livroNode.get("title").asText());
            livro.setIdiomas(livroNode.get("languages").asText());
            livro.setDownloads(livroNode.get("download_count").asInt());

            JsonNode autorNode = livroNode.get("authors").get(0);
            Autor autor = new Autor();
            autor.setNome(autorNode.get("name").asText());
            autor.setAnoNascimento(autorNode.get("birth_year").asInt());
            if (autorNode.has("death_year")) {
                autor.setAnoFalecimento(autorNode.get("death_year").asInt());
            }

            autor = autorRepositorio.save(autor);
            livro.setAutor(autor);

            return livroRepositorio.save(livro);
        }

        return null;
    }

    public List<Livro> listarTodosLivros() {
        return livroRepositorio.findAll();
    }

    public List<Autor> listarTodosAutores() {
        return autorRepositorio.findAll();
    }

    public List<Autor> listarAutoresVivosEmAno(int ano) {
        return autorRepositorio.findAll().stream()
                .filter(autor -> autor.getAnoNascimento() <= ano && (autor.getAnoFalecimento() == null || autor.getAnoFalecimento() > ano))
                .collect(Collectors.toList());
    }

    public List<Livro> listarLivrosPorIdioma(String idioma) {
        return livroRepositorio.findAll().stream()
                .filter(livro -> livro.getIdiomas().equalsIgnoreCase(idioma))
                .collect(Collectors.toList());
    }
}
