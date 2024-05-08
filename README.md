# ms-pessoa

`ms-pessoa` é um microserviço responsável por gerenciar usuários (pessoas) e fornecer operações CRUD (Create, Read, Update, Delete) relacionadas a usuários. Também tem um producer em uma queue RabbitMQ que produz mensagems de envio de email; Sendo consumido pelo microserviço ms-email

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Data JPA
- Spring Web
- Spring AMQP (RabbitMQ)
- Spring Cloud OpenFeign
- Springdoc OpenAPI
- PostgreSQL
- Maven
- API ViaCEP

## Pré-requisitos para rodar localmente

- Java 17
- PostgreSQL instalado e configurado
- Maven
- Conexão Gmail APP Passwords para execução de envio de email.

## Configuração

1. Clone este repositório:
   https://github.com/luanreis164/ms-user
2. Configure as propriedades do banco de dados no arquivo `application.properties`:

   - `spring.datasource.url=jdbc:postgresql://localhost:5432/ms-pessoa(crie uma base com essa nomenclatura)`
   - `spring.datasource.username=seu_usuario`
   - `spring.datasource.password=sua_senha`
   
3. Configure uma conexão com o RabbitMQ
    - Utilizado CloudAMQP para configuração de uma instancia de RabbitMQ e gerenciamento de filas e exchanges
    - `spring.rabbitmq.addresses =endereco_gerado`
    - `broker.queue.email.name=default.email`
4. Execute a aplicação -  Acesse a documentação da API Swagger:
   Após iniciar a aplicação, você pode acessar a documentação da API Swagger em:
   http://localhost:8081/swagger-ui/index.html

## Contribuindo
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues para reportar bugs, sugerir novas funcionalidades ou enviar pull requests.

## Licença
Este projeto está licenciado sob a MIT License.
