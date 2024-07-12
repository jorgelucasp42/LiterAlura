package com.alura.literalura.controlador;

import com.alura.literalura.modelo.Livro;
import com.alura.literalura.modelo.Autor;
import com.alura.literalura.servico.LivroServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Scanner;

@RestController
@RequestMapping("/livros")
public class LivroControlador {
    @Autowired
    private LivroServico livroServico;

    private final Scanner scanner = new Scanner(System.in);

    @PostMapping("/buscar")
    public Livro buscarLivroPorTitulo() throws Exception {
        System.out.print("Digite o título do livro: ");
        String titulo = scanner.nextLine();
        return livroServico.buscarLivroPorTitulo(titulo);
    }

    @GetMapping("/listar")
    public List<Livro> listarTodosLivros() {
        return livroServico.listarTodosLivros();
    }

    @GetMapping("/autores")
    public List<Autor> listarTodosAutores() {
        return livroServico.listarTodosAutores();
    }

    @GetMapping("/autores/vivos/{ano}")
    public List<Autor> listarAutoresVivosEmAno(@PathVariable int ano) {
        return livroServico.listarAutoresVivosEmAno(ano);
    }

    @GetMapping("/idioma/{idioma}")
    public List<Livro> listarLivrosPorIdioma(@PathVariable String idioma) {
        return livroServico.listarLivrosPorIdioma(idioma);
    }
}
