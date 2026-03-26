# RabbitMQ Email Sender

Este projeto demonstra uma arquitetura simples baseada em **mensageria utilizando RabbitMQ**, onde um serviço publica mensagens em uma fila e outro serviço consome essas mensagens para realizar o envio de e-mails.

A proposta do projeto é mostrar, de forma prática, como utilizar um **message broker** para desacoplar responsabilidades entre serviços. Nesse caso, um serviço é responsável por produzir eventos (como cadastro de usuário) e outro é responsável por processar esses eventos e enviar um e-mail correspondente.

O **RabbitMQ** é um broker de mensagens amplamente utilizado para comunicação assíncrona entre sistemas, utilizando o protocolo **AMQP** para transporte de mensagens entre produtores e consumidores.

---

# Arquitetura do Projeto

O projeto é dividido em dois serviços principais:

* **User Service (Producer)**
  Responsável por enviar mensagens para uma fila no RabbitMQ quando um evento ocorre.

* **Email Service (Consumer)**
  Responsável por consumir as mensagens da fila e realizar o envio do e-mail utilizando SMTP.

Fluxo simplificado:

```
User Service  →  RabbitMQ Queue  →  Email Service
   (Producer)        |             (Consumer)
                     |
              Message Broker
```

### Fluxo detalhado

1. O **User Service** publica uma mensagem na fila do RabbitMQ.
2. A mensagem contém as informações necessárias para envio de um e-mail.
3. O **Email Service** escuta essa fila continuamente.
4. Quando uma nova mensagem chega, o serviço processa os dados e envia o e-mail.

Essa abordagem traz vantagens como:

* Desacoplamento entre serviços
* Processamento assíncrono
* Maior escalabilidade
* Melhor tolerância a falhas

---

# Tecnologias Utilizadas

* Java
* Spring Boot
* RabbitMQ
* SMTP (envio de e-mails)
* Maven
* Variáveis de ambiente (.env)

---

# Estrutura do Projeto

```
RabbitMQ_Email_Sender
│
├── email-service
│   ├── src/main/java
│   │   ├── config
│   │   │   └── RabbitMQConfig
│   │   ├── consumer
│   │   │   └── EmailConsumer
│   │   ├── service
│   │   │   └── EmailService
│   │   └── EmailApplication
│   │
│   └── .env
│
├── user-service
│   ├── src/main/java
│   │   ├── config
│   │   │   └── RabbitMQConfig
│   │   ├── producer
│   │   │   └── UserProducer
│   │   └── UserApplication
│   │
│   └── .env
│
└── README.md
```

### email-service

Responsável por consumir mensagens da fila e enviar e-mails.

Principais responsabilidades:

* Conectar ao RabbitMQ
* Escutar a fila configurada
* Processar a mensagem recebida
* Enviar o e-mail via SMTP

### user-service

Responsável por produzir mensagens.

Principais responsabilidades:

* Conectar ao RabbitMQ
* Criar/publicar mensagens na fila
* Simular eventos que exigem envio de e-mail

---

# Variáveis de Ambiente

Cada serviço utiliza um arquivo `.env` para armazenar suas credenciais e configurações.

## email-service `.env`

```
HOST=jaragua.lmq.cloudamqp.com
USERNAME=rabbit_user
PASSWORD=strong_password
VIRTUALHOST=rabbit_vhost

EMAIL_USERNAME=example@gmail.com
EMAIL_PASSWORD=app_password_123
```

### Descrição

| Variável       | Descrição                       |
| -------------- | ------------------------------- |
| HOST           | Host do servidor RabbitMQ       |
| USERNAME       | Usuário do RabbitMQ             |
| PASSWORD       | Senha do RabbitMQ               |
| VIRTUALHOST    | Virtual host do RabbitMQ        |
| EMAIL_USERNAME | E-mail utilizado para envio     |
| EMAIL_PASSWORD | Senha ou app password do e-mail |

---

## user-service `.env`

```
HOST=jaragua.lmq.cloudamqp.com
USERNAME=rabbit_user
PASSWORD=strong_password
VIRTUALHOST=rabbit_vhost
```

### Descrição

| Variável    | Descrição                |
| ----------- | ------------------------ |
| HOST        | Host do RabbitMQ         |
| USERNAME    | Usuário do RabbitMQ      |
| PASSWORD    | Senha do RabbitMQ        |
| VIRTUALHOST | Virtual host configurado |

---

# Como Executar o Projeto

### 1. Subir o RabbitMQ

Você pode rodar utilizando Docker:

```bash
docker run -d \
--hostname rabbit \
--name rabbitmq \
-p 5672:5672 \
-p 15672:15672 \
rabbitmq:3-management
```

Interface web do RabbitMQ:

```
http://localhost:15672
```

Usuário padrão:

```
guest
guest
```

---

### 2. Configurar as variáveis de ambiente

Preencha os arquivos `.env` de cada serviço com as credenciais do RabbitMQ e do e-mail.

---

### 3. Executar os serviços

Primeiro execute o **email-service (consumer)**:

```bash
mvn spring-boot:run
```

Depois execute o **user-service (producer)**:

```bash
mvn spring-boot:run
```

---

# Conceitos Demonstrados

Este projeto demonstra alguns conceitos importantes de arquitetura de software:

* Comunicação assíncrona
* Message brokers
* Producer / Consumer pattern
* Event-driven architecture
* Integração entre microserviços

---

# Objetivo do Projeto

Este projeto foi desenvolvido com fins **educacionais**, com o objetivo de estudar:

* Integração entre serviços utilizando RabbitMQ
* Arquitetura baseada em eventos
* Processamento assíncrono
* Integração com envio de e-mails
