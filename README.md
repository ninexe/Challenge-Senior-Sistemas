# Desafio Java Back-end - Senior Sistemas

O desafio está detalhado no arquivo Desafio Hotel - Full Doc

### Requisitos usados para o desenvolvimento:
+ Java 11
+ Spring boot
+ Documentação Swagger
+ Banco de dados PostgreSQL
+ Testes unitários JUnit5

## Tutorial
### Configurar banco de dados:
+ É necessario configurar seu banco, ajustando sua senha e usuario na src\main\resources\application.properties 
+ Lá também tem um CREATE DATABASE para você executar no seu banco.
+ Não é necessario criar colunas ou tabelas, o codigo faz isso automaticamente após ser executado.
+ Segue a imagem do modelo conceitual do banco de dados:
### ![image](https://github.com/ninexe/Challenge-Senior-Sistemas/assets/61746866/0b7d56df-b102-4c74-8670-50475a8197e1)

### Inicializar o projeto:
+ Para inicializar o projeto, faça o clone com o comando no seu git: "git clone https://github.com/ninexe/Challenge-Senior-Sistemas.git"
+ Abra o repositório na sua IDE de preferência, e dê um build
+ Assim que a IDE baixar todas as dependências do projeto, clique em Run Application:
### ![image](https://github.com/ninexe/Challenge-Senior-Sistemas/assets/61746866/e867cecd-5644-413d-835c-ee35072aa55d)

### Acessar api
+ Para acessar a api pela documentação do Swagger acesse o caminho: http://localhost:8080/swagger-ui/index.html
+ Clique em Show/Hide
+ Acesse a Api, e siga o exemplo de consulta Ex:
### ![image](https://github.com/ninexe/Challenge-Senior-Sistemas/assets/61746866/88d80457-15c4-453e-aa5e-92d2df6474d6)
+ Aqui você pode adicionar as Reservas/Check-ins e Adicionar Guests/Hospedes.
+ Aqui você pode consultar como é feito a consulta e ao mesmo tempo executa-la, não precisa ter postman baixado.

### Utilizar Testes Unitários com jUnit
+ Para utilizar o teste Unitário, configure igual à imagem para rodar os testes do diretório completo.
### ![image](https://github.com/ninexe/Challenge-Senior-Sistemas/assets/61746866/3762b1d0-75dc-4819-87c6-8538329d24c3)
+ Para executar o teste, selecione "Qual classe deseja testar" e dê Run.
