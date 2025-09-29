API de Notificações Magalu
Esta é uma API RESTful desenvolvida com Spring Boot para gerenciamento de notificações. Permite agendar, consultar e cancelar notificações, com envio automático via Spring Scheduler. A API inclui validações, tratamento de erros e documentação com Swagger (OpenAPI).
Funcionalidades

Agendamento de notificações: Crie notificações com destinatário (e-mail), mensagem e data de envio.
Consulta de notificações: Consulte por ID ou liste todas com paginação e filtro por status.
Cancelamento de notificações: Cancele notificações pendentes.
Envio automático: Um scheduler processa notificações pendentes a cada 10 segundos, simulando envio via logs e atualizando o status para ENVIADA ou FALHA.
Documentação: Interface Swagger UI para explorar e testar endpoints.

Tecnologias Utilizadas

Java 21 com Spring Boot 3.5.6
Spring Data JPA para persistência com MySQL
Spring Scheduler para envio automático
Springdoc OpenAPI (Swagger) para documentação
Lombok para redução de boilerplate
Docker para banco de dados MySQL
Maven para gerenciamento de dependências

Pré-requisitos

Java 21 (OpenJDK)
Maven 3.8+
Docker e Docker Compose (para o MySQL)
Um editor como IntelliJ IDEA ou VS Code (opcional)

Configuração e Execução

Clone o repositório:
git clone https://github.com/SEU_USUARIO/magalu-notificacoes.git
cd magalu-notificacoes


Inicie o MySQL com Docker:

Certifique-se de que o Docker está rodando.
Execute:docker-compose up -d


Isso inicia um container MySQL com o banco notificacoes_db.


Configure o projeto:

Verifique o arquivo application.properties:spring.datasource.url=jdbc:mysql://localhost:3306/notificacoes_db
spring.datasource.username=user
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html


As configurações padrão devem funcionar com o docker-compose.yml.


Compile e execute o projeto:
mvn clean install
mvn spring-boot:run


Acesse a API:

Swagger UI: http://localhost:8080/swagger-ui.html
API Docs (JSON): http://localhost:8080/v3/api-docs



Endpoints da API



Método
Endpoint
Descrição
Exemplo de Requisição (JSON)



POST
/api/notificacoes
Agenda uma notificação
{"destinatario": "email@example.com", "mensagem": "Teste", "dataAgendamento": "2025-09-30T14:00:00"}


GET
/api/notificacoes/{id}
Consulta uma notificação por ID
-


GET
/api/notificacoes?page=0&size=5
Lista notificações (paginado, filtro opcional por status)
-


DELETE
/api/notificacoes/{id}
Cancela uma notificação pendente
-


Exemplos com curl

Agendar notificação:curl -X POST http://localhost:8080/api/notificacoes \
-H "Content-Type: application/json" \
-d '{"destinatario": "email@example.com", "mensagem": "Teste", "dataAgendamento": "2025-09-30T14:00:00"}'


Consultar por ID:curl -X GET http://localhost:8080/api/notificacoes/1


Listar com filtro:curl -X GET "http://localhost:8080/api/notificacoes?page=0&size=5&status=PENDENTE"


Cancelar:curl -X DELETE http://localhost:8080/api/notificacoes/1



Estrutura do Projeto
magalu-notificacoes/
├── src/
│   ├── main/
│   │   ├── java/com/tech/notificacoes/
│   │   │   ├── controller/       # Endpoints REST
│   │   │   ├── dto/              # DTOs para validação
│   │   │   ├── entity/           # Entidades JPA
│   │   │   ├── exception/        # Tratamento de erros
│   │   │   ├── repository/       # Repositórios JPA
│   │   │   ├── scheduler/        # Scheduler para envio
│   │   │   ├── service/          # Lógica de negócios
│   │   ├── resources/
│   │       ├── application.properties  # Configurações
│   ├── docker-compose.yml        # Configuração do MySQL
│   ├── pom.xml                   # Dependências Maven
│   ├── README.md                 # Documentação

Notas

O Scheduler verifica notificações pendentes a cada 10 segundos (ajustável).
O envio é simulado via logs; para envio real, integre com um serviço como Spring Mail.
Para testes, use o Swagger UI ou ferramentas como Postman.

Problemas Comuns

Erro "Data truncated for column 'status'": Verifique se a coluna status na tabela notificacoes é VARCHAR(20). Execute:ALTER TABLE notificacoes MODIFY COLUMN status VARCHAR(20) NOT NULL;


Erro no Swagger UI: Certifique-se de usar Springdoc 2.8.5 e acesse /swagger-ui.html.

Contribuições
Sinta-se à vontade para abrir issues ou pull requests no GitHub!
Licença
MIT License