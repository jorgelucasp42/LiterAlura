package com.alura.literalura;

import com.alura.literalura.controlador.LivroControlador;
import com.alura.literalura.modelo.Autor;
import com.alura.literalura.modelo.Livro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
			exibirMenu();
			int opcao = scanner.nextInt();
			scanner.nextLine();  // Consumir nova linha

			switch (opcao) {
				case 1:
					buscarLivro(scanner);
					break;
				case 2:
					listarTodosLivros();
					break;
				case 3:
					listarTodosAutores();
					break;
				case 4:
					listarAutoresVivos(scanner);
					break;
				case 5:
					listarLivrosPorIdioma(scanner);
					break;
				case 0:
					System.out.println("Saindo...");
					System.exit(0);
					break;
				default:
					System.out.println("Opção inválida.");
					break;
			}

			if (!desejaContinuar(scanner)) {
				System.out.println("Saindo...");
				break;
			}
		}
	}

	private void exibirMenu() {
		System.out.println("Escolha uma opção:");
		System.out.println("1. Buscar livro por título");
		System.out.println("2. Listar todos os livros");
		System.out.println("3. Listar todos os autores");
		System.out.println("4. Listar autores vivos em um determinado ano");
		System.out.println("5. Listar livros por idioma");
		System.out.println("0. Sair");
	}

	private void buscarLivro(Scanner scanner) {
		System.out.print("Digite o título do livro: ");
		String titulo = scanner.nextLine();
		try {
			Livro livro = livroControlador.buscarLivroPorTitulo(titulo);
			if (livro != null) {
				System.out.println("Livro encontrado:");
				System.out.println("Título: " + livro.getTitulo());
				System.out.println("Autor: " + livro.getAutor().getNome());
				System.out.println("Ano de Nascimento do Autor: " + livro.getAutor().getAnoNascimento());
				System.out.println("Ano de Falecimento do Autor: " + livro.getAutor().getAnoFalecimento());
				System.out.println("Idioma: " + livro.getIdiomas());
				System.out.println("Downloads: " + livro.getDownloads());
			} else {
				System.out.println("Livro não encontrado.");
			}
		} catch (Exception e) {
			System.out.println("Erro ao buscar livro: " + e.getMessage());
		}
	}

	private void listarTodosLivros() {
		List<Livro> livros = livroControlador.listarTodosLivros();
		if (livros.isEmpty()) {
			System.out.println("Nenhum livro encontrado.");
		} else {
			livros.forEach(l -> {
				System.out.println("Título: " + l.getTitulo());
				System.out.println("Autor: " + l.getAutor().getNome());
				System.out.println("Idioma: " + l.getIdiomas());
				System.out.println("Downloads: " + l.getDownloads());
				System.out.println("-----------");
			});
		}
	}

	private void listarTodosAutores() {
		List<Autor> autores = livroControlador.listarTodosAutores();
		if (autores.isEmpty()) {
			System.out.println("Nenhum autor encontrado.");
		} else {
			autores.forEach(a -> {
				System.out.println("Nome: " + a.getNome());
				System.out.println("Ano de Nascimento: " + a.getAnoNascimento());
				System.out.println("Ano de Falecimento: " + a.getAnoFalecimento());
				System.out.println("-----------");
			});
		}
	}

	private void listarAutoresVivos(Scanner scanner) {
		System.out.print("Digite o ano: ");
		int ano = scanner.nextInt();
		scanner.nextLine();  // Consumir nova linha
		List<Autor> autoresVivos = livroControlador.listarAutoresVivosEmAno(ano);
		if (autoresVivos.isEmpty()) {
			System.out.println("Nenhum autor vivo encontrado no ano " + ano + ".");
		} else {
			autoresVivos.forEach(a -> {
				System.out.println("Nome: " + a.getNome());
				System.out.println("Ano de Nascimento: " + a.getAnoNascimento());
				System.out.println("Ano de Falecimento: " + a.getAnoFalecimento());
				System.out.println("-----------");
			});
		}
	}

	private void listarLivrosPorIdioma(Scanner scanner) {
		System.out.print("Digite o idioma (es, en, fr, pt): ");
		String idioma = scanner.nextLine();
		List<Livro> livrosPorIdioma = livroControlador.listarLivrosPorIdioma(idioma);
		if (livrosPorIdioma.isEmpty()) {
			System.out.println("Nenhum livro encontrado no idioma " + idioma + ".");
		} else {
			livrosPorIdioma.forEach(l -> {
				System.out.println("Título: " + l.getTitulo());
				System.out.println("Autor: " + l.getAutor().getNome());
				System.out.println("Idioma: " + l.getIdiomas());
				System.out.println("Downloads: " + l.getDownloads());
				System.out.println("-----------");
			});
		}
	}

	private boolean desejaContinuar(Scanner scanner) {
		System.out.println("Deseja continuar? (s/n)");
		String continuar = scanner.nextLine();
		return continuar.equalsIgnoreCase("s");
	}
}
1