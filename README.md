# Elotech Backend

Este projeto é um backend desenvolvido em **Java** utilizando **Spring Boot**. Ele faz parte do sistema da Elotech Library, uma biblioteca de gerenciamento de livros e usuários. A estrutura utiliza uma base de dados para armazenar informações sobre usuários, livros e empréstimos. Nesta documentação, iremos orientá-lo sobre como configurar e rodar o backend localmente.

## Estrutura do Projeto

A estrutura de pastas do projeto é organizada da seguinte forma:

```
elotech/
└── src/
    └── main/
        └── java/
            └── com.example.elotech/
                ├── controllers/         # Controladores REST da aplicação
                ├── domain/              # Entidades de domínio
                ├── enums/               # Enumerações para atributos
                ├── exceptions.handlers/ # Manipuladores de exceções
                ├── mappers/             # Utilização de MapStruct para mapear DTOs
                ├── repositories/        # Interfaces para acessar o banco de dados
                ├── services/            # Lógica de negócios
                ├── utils/               # Utilitários gerais
                └── ElotechApplication  # Classe principal da aplicação
        └── resources/
            ├── db.migration/         # Scripts de migração do banco de dados com Flyway
            ├── application.properties # Configurações da aplicação
```

## Tecnologias Utilizadas

- **Java 21**: Linguagem de programação usada para o backend.
- **Spring Boot**: Framework para facilitar o desenvolvimento de aplicações Java, com uma boa integração com o Spring Data JPA.
- **Flyway**: Ferramenta para migração do esquema do banco de dados, utilizando scripts SQL.
- **H2 Database**: Banco de dados em memória para desenvolvimento e testes.
- **Lombok**: Biblioteca Java que elimina códigos repetitivos, como getters e setters.
- **MapStruct**: Ferramenta para gerar implementações de mapeamento entre DTOs e entidades.
- **SpringDoc OpenAPI**: Integração para documentar e explorar as APIs REST do projeto.

## Requisitos Pré-Instalação

Certifique-se de ter instalados os seguintes softwares:

- **Java 21 ou superior**
- **Maven**: Para gerenciar dependências e compilar o projeto
- **Git**: Caso você deseje clonar o repositório

## Configuração e Instalação

Siga os passos abaixo para rodar o projeto localmente:

1. **Clone o repositório (opcional)**:
   ```bash
   git clone <URL-do-repositório>
   cd elotech
   ```

2. **Compilar e instalar dependências**:
   Navegue até a pasta raiz do projeto e execute o Maven para baixar as dependências e compilar o projeto.
   ```bash
   ./mvnw clean install
   ```
   Se preferir, você também pode usar o comando `mvn` diretamente, mas o wrapper (`mvnw`) garantirá a versão correta do Maven.

3. **Configuração do banco de dados**:
   A aplicação está configurada para usar um banco de dados **H2** em memória por padrão, o que é ideal para testes. Caso deseje alterar para um banco de dados persistente (MySQL, PostgreSQL, etc.), modifique o arquivo `src/main/resources/application.properties` com as credenciais adequadas.

4. **Rodar a aplicação**:
   Agora, execute a aplicação localmente utilizando:
   ```bash
   ./mvnw spring-boot:run
   ```

## Banco de Dados e Migração

Os scripts de criação de tabelas estão localizados em `src/main/resources/db/migration/` e são gerenciados pelo Flyway. Ao iniciar o aplicativo, o Flyway automaticamente aplica as migrações no banco de dados configurado.

Scripts incluídos:

- `V1_Create_Table_Users.sql`
- `V2_Create_Table_Books.sql`
- `V3_Create_Table_Loans.sql`

Esses scripts são aplicados para criar as tabelas **Usuários**, **Livros**, e **Empréstimos**.

## Testes

Você pode rodar os testes para garantir que a aplicação está funcionando como esperado:
```bash
./mvnw test
```

## Documentação da API

A documentação da API é gerada automaticamente com **SpringDoc OpenAPI**. Uma vez que a aplicação esteja rodando, você pode acessar a documentação interativa no navegador, utilizando o seguinte link:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Esta interface permite explorar os endpoints, enviar requisições e verificar respostas da API.

## Scripts Disponíveis

- **`mvnw clean install`**: Limpa, compila e instala as dependências do projeto.
- **`mvnw spring-boot:run`**: Inicia a aplicação em um servidor local.
- **`mvnw test`**: Executa os testes unitários do projeto.

## Ou para rodar mais fácil, use o Intellij.

- ** Abra o projeto no Intellij
- ** Sete o Java 21 no programa, para isso:

Configure o IntelliJ IDEA manualmente: No IntelliJ IDEA, vá até as configurações ( File > Project Structure > SDKs ) e adicione manualmente a JDK clicando no botão + e navegando até o diretório onde a JDK está instalada

- ** Clique no ícone de Play


