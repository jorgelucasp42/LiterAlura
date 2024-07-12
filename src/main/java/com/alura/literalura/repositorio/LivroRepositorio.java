package com.alura.literalura.repositorio;

import com.alura.literalura.modelo.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepositorio extends JpaRepository<Livro, Long> {
}