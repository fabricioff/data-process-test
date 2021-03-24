# Data processing API

API RESTful desenvolvida como exercício de leitura e armazenamento dos dados em banco de dados para posterior processamento e redistribuição desse dados.
Foram utilizados recursos de lambdas, streams, além dos recusos do Spring Framework.


## Tecnologias utilizadas

- Java 8
- Apache Maven
- Eclipse IDE
- Spring Boot DevTools
- Banco de dados relacional: PostgreSQL


## Usando banco de dados: PostgreSQL

Esta API foi desenvolvida e testada utilizando o banco de dados PostgreSQL.

É necessário realizar o [download](https://www.postgresql.org/download/), instalação e [configuração](https://medium.com/@thiago.reis/instalando-e-configurando-postgresql-no-ubuntu-86716cda5894) do PostgreSQL.

Caso ache interessante, mas não seria obrigatório, também poderia utilizar o [pgAdmin](https://www.pgadmin.org/download/), pois essa ferramenta poderia facilitar consultas e manipulação dos dados.


Após a instalação e configuração do PostgreSQL, é necessário criar o DATABASE:

```sql
CREATE DATABASE data_processing
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'pt_BR.UTF-8'
    LC_CTYPE = 'pt_BR.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
```

Devido a utilização do framework Spring Boot e suas configuações, não seria necessário a criação da tabela por script SQL.
 
Ao executar a aplicação pela primeira vez, a tabela 'inventory' será criada.

Caso a tabela não seja criada, segue abaixo o script DDL de criação da mesma:

```sql
CREATE TABLE public.inventory
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    industry character varying(255) COLLATE pg_catalog."default",
    origin character varying(255) COLLATE pg_catalog."default",
    price double precision,
    product character varying(255) COLLATE pg_catalog."default",
    quantity integer,
    type character varying(255) COLLATE pg_catalog."default",
    volume double precision,
    CONSTRAINT inventory_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE public.inventory
    OWNER to postgres;
```

> Observação:
>    Esses comandos SQL podem ser executados pelo terminal interativo [PSQL](http://postgresguide.com/utilities/psql.html) 


## Iniciando API

Após a instalação e configuração do PostgreSQL, agora podemos executar a aplicação.

Para iniciar a API, é necessário acessar a pasta "deploy/" e executar o .JAR:

```sh
cd deploy/

java -jar data-process-api-0.0.1.jar

```

## Endpoints

Após a execução da API, será possível fazer acesso ao endpoints acessando:

```url
localhost:8080/ENDPOINT
```

A API possui posui os sequintes endpoints:


- [/getInventory](localhost:8080/getInventory) -> Obtém uma lista de todos os produtos importados no banco 
- [/getProduct](localhost:8080/getInventory?product=EMMS) -> Obtém os detalhes de um produto especificado.
- [/createInventory](localhost:8080/createInventory) -> Cria e insere um produto no banco de dados. Necessário enviar um JSON pelo BODY com a estrutura especificada mais abaixo.
- [/importInventory](localhost:8080/importInventory?clientsAmount=2&product=EMMS) -> Realiza a importação dos produtos no banco de dados. Necessário enviar um JSON com a massa de dados pelo BODY.
- [/processInventory](localhost:8080/processInventory?clientsAmount=2&product=EMMS) -> Realiza o processamento dos dados, retornando a distribuição de produtos entre clientes. Necessário informar dois parametros 'clientsAmount'(quantidade de clientes) e 'product'(nome do produto).

> Para criar o produto, é necessário informar um JSON com a seguinte estrutura:

```json
{
  "product": "XYZ",
  "quantity": 12,
  "price": 3.45,
  "type": "T",
  "industry": "SpaceX",
  "origin": "n/a"
}
```
    
    
# Importando Produtos

Uma forma utilizada para fazer a importação dos dados, foi utilizar o comando [cURL](https://curl.se/docs/manpage.html) utilizando o paramentro '--data' para enviar o JSON com os dados.


```sh
curl -H "Content-Type: application/json" --data @data_1.json http://localhost:8080/importInventory
```