# LiterAlura

## Descrição
O projeto LiterAlura é uma aplicação console em Java que permite aos usuários interagir com um catálogo de livros. A aplicação utiliza a API Gutendex para buscar informações sobre livros e armazena esses dados em um banco de dados PostgreSQL. 

## Funcionalidades
- Buscar livro pelo título e armazenar no banco de dados.
- Listar livros registrados no banco de dados.
- Listar autores registrados no banco de dados.
- Listar autores vivos em um determinado ano.
- Listar livros em um determinado idioma.

## Tecnologias Utilizadas
- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Gutendex API
- Maven
- Jackson
- Lombok

## Como Executar

### Pré-requisitos
- JDK 11 ou superior
- Maven
- PostgreSQL

### Passos para execução
1. Clone o repositório.
2. Configure as propriedades do banco de dados no arquivo `src/main/resources/application.properties`.
3. Execute o comando `mvn spring-boot:run` para iniciar a aplicação.
4. Interaja com a aplicação via console para utilizar as funcionalidades disponíveis.

## Estrutura do Projeto
LiterAlura
|-- src
| |-- main
| | |-- java
| | | |-- com.example.literalura
| | | | |-- LiterAluraApplication.java
| | | | |-- controlador
| | | | | |-- LivroControlador.java
| | | | |-- modelo
| | | | | |-- Autor.java
| | | | | |-- Livro.java
| | | | |-- repositorio
| | | | | |-- AutorRepositorio.java
| | | | | |-- LivroRepositorio.java
| | | | |-- servico
| | | | | |-- LivroServico.java
| | | |-- resources
| | | | |-- application.properties
| |-- test
|-- pom.xml


## Endpoints da API

### Buscar livro por título
- **Endpoint**: `/livros/buscar`
- **Método**: `POST`
- **Descrição**: Busca um livro pelo título utilizando a API Gutendex e armazena no banco de dados.

### Listar todos os livros
- **Endpoint**: `/livros/listar`
- **Método**: `GET`
- **Descrição**: Lista todos os livros registrados no banco de dados.

### Listar todos os autores
- **Endpoint**: `/livros/autores`
- **Método**: `GET`
- **Descrição**: Lista todos os autores registrados no banco de dados.

### Listar autores vivos em um determinado ano
- **Endpoint**: `/livros/autores/vivos/{ano}`
- **Método**: `GET`
- **Descrição**: Lista todos os autores que estavam vivos em um determinado ano.

### Listar livros por idioma
- **Endpoint**: `/livros/idioma/{idioma}`
- **Método**: `GET`
- **Descrição**: Lista todos os livros em um determinado idioma.

## Considerações Finais
Certifique-se de que o banco de dados PostgreSQL esteja configurado corretamente e em execução. A aplicação é inicializada com as dependências necessárias no `pom.xml`. Utilize os endpoints fornecidos para interagir com a aplicação e realizar as operações de busca e listagem de livros e autores.


 
