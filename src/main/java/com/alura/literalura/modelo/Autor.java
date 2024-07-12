package com.alura.literalura.modelo;

import javax.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
@Data
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private int anoNascimento;
    private Integer anoFalecimento;

    @OneToMany(mappedBy = "autor")
    private Set<Livro> livros;
}
