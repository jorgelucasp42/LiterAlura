package com.alura.literalura;

import com.alura.literalura.controlador.LivroControlador;
import com.alura.literalura.modelo.Autor;
import com.alura.literalura.modelo.Livro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LivroControlador livroControlador;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("Escolha uma opção:");
			System.out.println("1. Buscar livro por título");
			System.out.println("2. Listar todos os livros");
			System.out.println("3. Listar todos os autores");
			System.out.println("4. Listar autores vivos em um determinado ano");
			System.out.println("5. Listar livros por idioma");
			System.out.println("0. Sair");
			int opcao = scanner.nextInt();
			scanner.nextLine();  // Consumir nova linha

			switch (opcao) {
				case 1:
					System.out.print("Digite o título do livro: ");
					String titulo = scanner.nextLine();
					try {
						Livro livro = livroControlador.buscarLivroPorTitulo(titulo);
						System.out.println("Livro encontrado: " + livro);
					} catch (Exception e) {
						System.out.println("Erro ao buscar livro: " + e.getMessage());
					}
					break;
				case 2:
					List<Livro> livros = livroControlador.listarTodosLivros();
					livros.forEach(System.out::println);
					break;
				case 3:
					List<Autor> autores = livroControlador.listarTodosAutores();
					autores.forEach(System.out::println);
					break;
				case 4:
					System.out.print("Digite o ano: ");
					int ano = scanner.nextInt();
					scanner.nextLine();  // Consumir nova linha
					List<Autor> autoresVivos = livroControlador.listarAutoresVivosEmAno(ano);
					autoresVivos.forEach(System.out::println);
					break;
				case 5:
					System.out.print("Digite o idioma (es, en, fr, pt): ");
					String idioma = scanner.nextLine();
					List<Livro> livrosPorIdioma = livroControlador.listarLivrosPorIdioma(idioma);
					livrosPorIdioma.forEach(System.out::println);
					break;
				case 0:
					System.exit(0);
					break;
				default:
					System.out.println("Opção inválida.");
					break;
			}
		}
	}
}
