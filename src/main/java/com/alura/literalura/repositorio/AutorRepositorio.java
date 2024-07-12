package com.alura.literalura.repositorio;

import com.alura.literalura.modelo.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepositorio extends JpaRepository<Autor, Long> {
}