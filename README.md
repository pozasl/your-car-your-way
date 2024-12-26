# your-car-your-way

This is a *Proof of Concept* to demonstrate Synchronous message exchange between the customers and the customer service staff for the application **Your Car Your Way** through a customer service chat box.

## Technologies

- Java 21
- Netflix DGS
- JWT
- Angular 19
- Apollo
- Material angular


## RSA keys:

JWT uses RSA key signing. Those keys need to be generated first:

``` bash
cd back/graphql/src/main/resources/certs
openssl genrsa -out keypair.pem 2048
openssl rsa -in keypair.pem -pubout -out public.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
```

## Setting up dev environment

### Requirements

if you're not using the conveniently provided dev container you'll need to install the following:

- Java JDK 21
- Maven 3
- NodeJS 22
- Angular 19
- PostgreSQL 17.2
- Openssl

### Environment variables

For local dev purpose only set the following environment variables:

``` bash
POSTGRES_PASSWORD: postgres
POSTGRES_USER: postgres
POSTGRES_DB: postgres
POSTGRES_HOSTNAME: postgresdb
```

### Setting up Database:

After installing postgres and creating a *postgres* user and database with all rights granted, initialize the tables as follow:

``` bash
psql -U $POSTGRES_USER -d $POSTGRES_DB -a -f .devcontainer/postgres-entrypoint-initdb.d/01-schema.sql
```

### Run the backend

To run the backend run:
``` bash
cd back/graphql
mvn spring-boot:run
```

The graphql API is available at : http://localhost:8080/graphql

GraphiQL UI is available at http://localhost:8080/graphiql


### Run the frontend
``` bash
cd front
npm install
ng serve
```

The application is available at : http://localhost:4200

## Generating GraphQL code

### Backend

The graphql server DSL is in *back/graphql/src/main/resources/schema/schema.graphqls*

``` bash
cd back/graphql
mvn genrate-sources
```

### Frontend

The graphql client DSL is in *front/src/resources/schema/ycyw.graphql*

**The backend's graphQL API must be running** while generating the frontend's client code

``` bash
cd front
npm run generate
```