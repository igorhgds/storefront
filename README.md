# Microsserviço: Storefront

Este microsserviço atua como uma fachada (façade) ou um BFF (Backend for Frontend) para o sistema de e-commerce. Sua principal função é compor dados de outros microsserviços e expô-los em uma API otimizada para o consumo por clientes, como um site ou um aplicativo mobile.

## Arquitetura
O `storefront` é um serviço que depende de outros para funcionar. Ele realiza chamadas **síncronas** (HTTP/REST) para serviços como o `warehouse` para obter dados em tempo real, e também se inscreve em eventos **assíncronos** via RabbitMQ para receber atualizações, garantindo que suas informações se mantenham consistentes.

## Principais Responsabilidades
* Expor uma API pública para consulta de produtos e sua disponibilidade.
* Consultar o serviço `warehouse` via REST para obter detalhes de produtos.
* Consumir eventos do RabbitMQ para manter o status de disponibilidade dos produtos atualizado em sua própria base de dados ou cache.

## Tecnologias Utilizadas
* **Java 21**
* **Spring Boot 3.x**
* **Spring Webflux / RestClient:** Para comunicação síncrona com outros serviços.
* **Spring AMQP:** Para integração com RabbitMQ.
* **Docker & Docker Compose:** Para containerização e orquestração do ambiente de desenvolvimento.
* **OpenAPI (Swagger):** Para documentação da API.

## Pré-requisitos
* JDK 21 ou superior.
* Docker e Docker Compose instalados.

## Como Executar

Existem duas formas principais de executar o projeto.

### 1. Ambiente Completo com Docker Compose
Esta abordagem sobe toda a infraestrutura e todos os microsserviços definidos no `docker-compose.yml`.

1.  Na raiz do projeto, execute o comando:
    ```bash
    docker-compose up --build -d
    ```
2.  Aguarde os containers subirem. O serviço `storefront` estará disponível em `http://localhost:8081/storefront`.

### 2. Ambiente Híbrido para Desenvolvimento Local
Ideal para quando você está desenvolvendo ativamente este microsserviço e quer usar o live-reload do Spring DevTools.

1.  **Suba as dependências** (`rabbitmq` e `warehouse`) com o Docker Compose:
    ```bash
    docker-compose up -d rabbitmq warehouse
    ```
2.  **Configure o `application-dev.yml`** para apontar para os serviços na sua máquina local:
    ```yaml
    spring:
      rabbitmq:
        host: localhost
    
    warehouse:
      base-path: http://localhost:8080/warehouse # URL do serviço warehouse
    ```
3.  **Execute a aplicação** diretamente pela sua IDE, rodando a classe principal `StorefrontApplication.java`.

## Endpoints da API
A documentação completa e interativa da API pode ser acessada via Swagger UI no seguinte endereço (após iniciar a aplicação):

* **[http://localhost:8081/storefront/swagger-ui.html](http://localhost:8081/storefront/swagger-ui.html)**

| Verbo | Endpoint      | Descrição                                 |
| :---- | :------------ | :---------------------------------------- |
| `GET` | `/products`     | Lista os produtos disponíveis para venda. |
| `GET` | `/products/{id}` | Busca os detalhes de um produto específico. |
| ...   | ...           | *(Adicionar outros endpoints)* |

## Comunicação Assíncrona (Eventos Consumidos)

Este serviço atua como **Consumidor** de eventos.

* **Evento:** Mudança no Status do Estoque.
* **Queue:** `product.change.availability.queue` (configurável no `application.yml`)
* **Ação:** Ao receber uma mensagem sobre a mudança de status de um produto, o serviço atualiza sua própria representação daquele produto para refletir a nova disponibilidade (ex: marcando como "Em estoque" ou "Esgotado").
