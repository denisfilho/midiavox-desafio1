# Desafio - MidiaVox

## Projeto
Gerenciamento de login em ramais

## ⚙️ Tecnologias

- Backend: Java 17, Spring Boot 3.4.4
- Banco de Dados: MySQL
- Testes: JUnit 5, Mockito

## 🏁 Instalação e Execução

### 1. Clone o repositório

```bash
git clone https://github.com/denisfilho/midiavox-desafio1.git
```

### 2. Clone o repositório
```bash
CREATE TABLE extensions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    extension_number VARCHAR(10) NOT NULL UNIQUE,
    logged_user VARCHAR(100)
);
```

### 3. Backend
```bash
./mvnw spring-boot:run
```

### 4. Tests

#### 4.1 Terminal
```bash
./mvnw test
```

#### 4.2 Postman
src/test/resources/postman/Desafio.postman_collection.json
