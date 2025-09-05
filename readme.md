# DR1 - TP3

## Inicialização

- Criar container docker antes;
       
      docker run -it --rm --name dr1_crm -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=123456 -e POSTGRES_DB=crm -p 5432:5432 postgres:16.10-alpine3.22
