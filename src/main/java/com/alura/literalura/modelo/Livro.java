package com.alura.literalura.modelo;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String idiomas;
    private int downloads;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
}
