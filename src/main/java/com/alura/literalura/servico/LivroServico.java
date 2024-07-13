package com.alura.literalura.servico;

import com.alura.literalura.modelo.Autor;
import com.alura.literalura.modelo.Livro;
import com.alura.literalura.repositorio.AutorRepositorio;
import com.alura.literalura.repositorio.LivroRepositorio;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.nio.charset.StandardCharsets;
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
        String encodedTitulo = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
        String url = "https://gutendex.com/books?search=" + encodedTitulo;
        System.out.println("URL: " + url);  // Log da URL

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (Exception e) {
            System.err.println("Erro ao enviar a requisição: " + e.getMessage());
            throw new Exception("Erro ao enviar a requisição para a API.");
        }

        if (response.body().isEmpty()) {
            System.err.println("Response body is empty");
            throw new Exception("Resposta da API está vazia.");
        }

        JsonNode root = objectMapper.readTree(response.body());
        System.out.println("Parsed JSON: " + root.toPrettyString());

        if (!root.has("results")) {
            System.err.println("JSON does not contain 'results'");
            throw new Exception("Resposta da API não contém 'results'.");
        }

        JsonNode resultsNode = root.get("results");
        if (!resultsNode.isArray() || resultsNode.size() == 0) {
            System.err.println("Results node is not an array or is empty");
            throw new Exception("Resultados não encontrados ou resposta inesperada da API.");
        }

        JsonNode livroNode = resultsNode.get(0);
        System.out.println("First book in results: " + livroNode.toPrettyString());

        Livro livro = new Livro();
        livro.setTitulo(livroNode.get("title").asText());
        livro.setIdiomas(livroNode.get("languages").get(0).asText());
        livro.setDownloads(livroNode.get("download_count").asInt());

        if (livroNode.has("authors") && livroNode.get("authors").isArray() && livroNode.get("authors").size() > 0) {
            JsonNode autorNode = livroNode.get("authors").get(0);
            Autor autor = new Autor();
            autor.setNome(autorNode.get("name").asText());
            autor.setAnoNascimento(autorNode.get("birth_year").asInt());
            if (autorNode.has("death_year")) {
                autor.setAnoFalecimento(autorNode.get("death_year").asInt());
            }

            autor = autorRepositorio.save(autor);
            livro.setAutor(autor);
        } else {
            System.err.println("No authors found or unexpected API response");
            throw new Exception("Autores não encontrados ou resposta inesperada da API.");
        }

        return livroRepositorio.save(livro);
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
