# data-process-test

# Usando banco de dados: PostgreSQL
sudo -u postgres psql 

CREATE DATABASE data_processing
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'pt_BR.UTF-8'
    LC_CTYPE = 'pt_BR.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
    
CREATE TABLE public.product
(
    id bigint NOT NULL,
    industry character(80),
    price double precision,
    product character(50),
    quantity integer,
    type character(10),
    origin character(10),
    PRIMARY KEY (id)
);

ALTER TABLE public.product
    OWNER to postgres;
    
    
    
# Importando Produtos
curl -H "Content-Type: application/json" --data @data.json http://localhost:8080/importInventory
