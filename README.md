
# Atividade Mensageria

## Introdução
Este projeto implementa uma arquitetura de microserviços com Kafka, utilizando Spring Boot para o gerenciamento de APIs. 
O microserviço Produtor envia mensagens para um tópico Kafka, e o microserviço Consumidor consome essas mensagens.

**Tecnologias utilizadas**:
- Kafka
- Spring Boot
- Docker
- Maven

## Requisitos do Sistema

### Requisitos de Software
- Java 21 ou superior
- Kafka 2.8 ou superior
- Docker
- Maven 3.9 ou superior


## Como Executar o Projeto

### Iniciar o Ambiente
Execute o seguinte comando para iniciar todos os serviços via Docker:


Execute o zookeper
```bash
zookeeper-server-start.bat "(Seu Diretorio)\kafka\config\zookeeper.properties"
```

Execute o kafka
```bash
kafka-server-start.bat "(Seu Diretorio)\kafka\config\server.properties"
```

Execute o docker
```bash
docker-compose up -d
```

E por ultimo execute as duas classes do projeto

#### Docker Compose
Certifique-se que o `docker-compose.yml` esteja de acordo com as configurações da sua maquina:

```yaml
version: '3.8'
services:
  zookeeper:
    image: confluentinc/cp-zookeeper:latest
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181

  kafka:
    image: confluentinc/cp-kafka:latest
    environment:
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092
    depends_on:
      - zookeeper

  produtor-service:
    build: ./produtor-service
    ports:
      - "8081:8081"
    depends_on:
      - kafka

  consumidor-service:
    build: ./consumidor-service
    ports:
      - "8082:8082"
    depends_on:
      - kafka
```

### Configuração do `application.yml` para Kafka
Certifique-se as configurações e a porta local da sua maquina

**Produtor**:
```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
server:
  port: 8081
```

**Consumidor**:
```yaml
spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: my-group
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
server:
  port: 8082
```

## Como Testar

1. Envie uma mensagem para o **Produtor** usando `POST` no endpoint `/api/producer/send`.
2. Verifique se o **Consumidor** processa a mensagem corretamente no console.

## Logs e Monitoramento
- **Logs do Kafka**: Verifique os logs do Kafka para depuração.
- **Logs do Produtor/Consumidor**: Verifique os logs para ver as mensagens enviadas e consumidas.
