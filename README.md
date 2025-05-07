# 📝 API Blog Pessoal

Esta é uma API RESTful desenvolvida com **Spring Boot** e **MySQL**, parte do projeto de aprendizado e prática de desenvolvimento de aplicações Java com persistência de dados, segurança e testes.

---

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- Spring Security (com JWT)
- MySQL 8
- Docker & Docker Compose
- JUnit & Mockito
- Maven

---

## 📁 Estrutura do Projeto

```
📦api-blog-pessoal
 ┣ 📂src
 ┃ ┣ 📂main
 ┃ ┃ ┣ 📂java
 ┃ ┃ ┃ ┗ 📂com.ro77en.blog_pessoal
 ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┣ 📂model
 ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┣ 📂security
 ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┗ 📂resources
 ┣ 📂test
 ┃ ┗ 📂com.ro77en.blog_pessoal
 ┣ Dockerfile
 ┣ docker-compose.yml
 ┗ README.md
```

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos

- Docker e Docker Compose instalados
- Git instalado

### Passos

1. Clone o repositório:
   ```bash
   git clone https://github.com/ro77en/api-blog-pessoal.git
   cd api-blog-pessoal
   ```

2. Execute o projeto com Docker Compose:
   ```bash
   docker compose up --build
   ```

3. A API estará disponível em: [http://localhost:8080](http://localhost:8080)

---

## 🔐 Segurança

A autenticação é feita via **JWT (JSON Web Token)**. Algumas rotas são públicas e outras protegidas, exigindo um token válido.

---

## 🧪 Testes

Os testes unitários e de integração são implementados com:

- **JUnit 5**
- **Mockito**
  
Para executá-los:
```bash
./mvnw test
```

---

## 🛠️ Variáveis de Ambiente

As configurações do banco são feitas no `application.properties`, mas você pode configurar usando variáveis de ambiente para maior segurança e flexibilidade. Exemplo no `docker-compose.yml`.

---

## ✨ Funcionalidades

- Cadastro e autenticação de usuários
- CRUD de postagens
- Relacionamento entre postagens e temas
- Validações com Bean Validation
- Segurança com Spring Security e JWT

---

## 📄 Licença

Projeto com fins educacionais — sinta-se livre para usar e adaptar.

---

## 👨‍💻 Autor

Desenvolvido por **[@ro77en](https://github.com/ro77en)** 🚀