package com.alura.literalura.controlador;

import com.alura.literalura.modelo.Livro;
import com.alura.literalura.modelo.Autor;
import com.alura.literalura.servico.LivroServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroControlador {
    @Autowired
    private LivroServico livroServico;

    @PostMapping("/buscar")
    public Livro buscarLivroPorTitulo(@RequestBody String titulo) {
        try {
            return livroServico.buscarLivroPorTitulo(titulo);
        } catch (Exception e) {
            System.err.println("Erro ao buscar livro: " + e.getMessage());
            return null;
        }
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
