# Projeto de Demonstração para a API do Google Calendar Usando Spring Boot Rest API com OAuth2

Este guia mostra como construir um aplicativo de exemplo que interage com as APIs do Google, especificamente a API do Google Calendar, usando OAuth2 e Spring Boot.

O projeto começa com um login único (Single Sign-On) utilizando OAuth2 com o Google e inclui funcionalidades adicionais, como criação de eventos no Google Calendar e listagem de eventos após o login bem-sucedido.

## Google Calendar API  - Spring Boot and OAuth2

1. **Spring Boot**
2. **Rest API**
3. **Google Calendar API**
4. **OAuth2**

## O que este projeto faz?

Este é um aplicativo Spring Boot protegido pelo Spring Security OAuth2, utilizando o Google como provedor de autenticação. Além do login seguro, ele também oferece os seguintes recursos:

- **Autenticação OAuth2 com Google**
- **Listar todos os eventos do Google Calendar após o login**
- **Criar um novo evento no Google Calendar**

## Como executar o projeto?

O projeto utiliza Maven, então basta executar o seguinte comando para compilá-lo:
```bash
    mvn clean build
```

O arquivo application.properties (em src/main/resources) contém as credenciais da aplicação Google. Certifique-se de modificar os seguintes atributos para os valores do seu aplicativo:
```bash
    google.client.id=
    google.client.secret=
    google.redirect.uri=http://localhost:9000/login/google
    google.client.access-token-uri=
    google.client.user-authorization-uri=
```

### Como registrar um aplicativo no Google?

1. **Acesse Google Cloud Console e faça login com sua conta Google.**
2. **Se ainda não tiver um projeto, crie um novo e selecione-o.**
3. **No menu lateral, vá para "APIs e serviços" → "Credenciais" → "Criar credencial" → "ID do Cliente OAuth".**
4. **Selecione as seguintes opções:**
    - Tipo de Aplicação = Aplicação Web (Spring Boot)
    - Origens JavaScript Autorizadas = (deixe em branco ou preencha conforme necessário)
    - URI de Redirecionamento Autorizado = http://localhost:9000/login/google
        - **Copie o Client ID e o Client Secret e atualize o arquivo application.properties.**
        - **Vá até "APIs e serviços" → "Biblioteca" e ative a Google Calendar API.**
        - **Preencha as informações obrigatórias na aba "Tela de Consentimento OAuth".**

### Testando a autenticação e integração com o Google Calendar

1. **Autenticar com o Google**

    - Após iniciar a aplicação Spring Boot, acesse:
      ```bash
       http://localhost:9000/login/google
      ```
    - Isso redirecionará para a página de login do Google.
    - Ao fazer login, o Google pedirá autorização para acessar os dados da sua conta e do Google Calendar.

2. **Listar eventos do Google Calendar**

    - Após autenticar, faça uma requisição GET para:
      ```bash
       GET http://localhost:9000/calendar/list-events
      ```
    - A resposta conterá todos os eventos do seu Google Calendar.

3. **Criar um novo evento no Google Calendar**

    - Para criar um evento, envie uma requisição POST para:
      ```bash
        POST http://localhost:9000/api/calendar/add-event
       ```
    - Corpo da requisição de exemplo (JSON):
      ```bash
        {
            "summary": "Reunião de Time",
            "location": "Google Meet",
            "description": "Discussão sobre o projeto X",
            "startTime": "2025-03-13T14:00:00-03:00",
            "endTime": "2025-03-13T15:00:00-03:00"
        }
      ```
    - A resposta confirmará a criação do evento no Google Calendar.
