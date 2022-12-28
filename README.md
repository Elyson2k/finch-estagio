# Desafio Backend para vaga de estágio

### PROBLEMA

Somos uma empresa que fazemos cotação de preços e precisamos de uma automação que possa buscar os preços em sites de venda. Nosso foco é achar os três menores preços de um determinado produto em um site de vendas e trazer suas informações para o cliente.

### ESPECIFICAÇÕES:

- Site de vendas: MercadoLivre (www.americanas.com.br) ou similar
- Produto a ser pesquisado: Webcam
 
### CRITÉRIOS DE ENTREGA:

O projeto deve ser entregue atendendo os seguintes critérios:

- Java 8 ou 17
- Maven para gerenciar as dependências;
- Framework Spring (Spring Boot ou Spring MVC) para controle da aplicação;
- Utilizar framework Selenium com driver do Google Chrome;
- Utilização de LOGs em terminal, demonstrando a processo da aplicação;
- Disponibilizar o projeto, com instruções de execução, em um repositório git.
 
# Getting started

### Passo 1:

Fazer um clone do projeto:

```text
git clone https://github.com/Elyson2k/finch-estagio.git
```

### Passo 2:
Já na pasta do projeto sua IDE de preferencia [ IntelliJ :), Eclipse :| ], abra o terminal e execute o seguinte comando:
```text
docker-compose up -d
```
Isso fará com que o banco de dados da aplicação rode em um container Docker.

### Passo 3: 
Baixe/Atualize as bibliotecas do projeto com Maven:

```text
mvn clean install
```

### Passo 4: 
Rodar testes da aplicação:

```text
mvn test
```

### Passo 5:
Rode a aplicação pelo pelo IntelliJ, ou use o seguinte comando:

```text
mvn spring-boot:run
```

### Passo 6:
A documentação Swagger OpenApi da aplicação estará disponivel em:
```text
http://localhost:8080/swagger-ui/index.html
```

# Principais Ferramentas Utilizadas
- **springdoc-openapi-ui**: Para fazer documentação com Swagger.
- **mysql-connector-j**: Connector para o MySql, funciona como um driver.
- **com.h2database**: Banco de dados em memória, para testes iniciais e para testes da aplicação.
- **spring-boot-starter-web**: Biblioteca básica que faz com que minha aplicação tenha um servidor embutido do tomcat e também da várias classes uteis para desenvolvimentop da minha aplicação Web.
- **junit**: Ferramenta para testes unitários.
- **selenium-java**: Ferramenta comumente usada para testes, mas usada no projeto para realizar a busca de informações no site desejado.

# Padrão de Projeto Utilizado
## Strategy
O Strategy é um padrão de projeto comportamental que permite que você defina uma família de algoritmos, coloque-os em classes separadas, e faça os objetos deles intercambiáveis.

Basicamente, ao invés de ter if else if aninhados, ou termos switch case, podemos fazer com que o nosso código consiga crescer sem que tenhamos que fazer alterações no código que já funciona. No problema desse projeto, projetei o sistema para suportar mais de um tipo de sistema a ser pesquisado. A implementação principal, feita e já funcionando foi para o site Mercado Livre, porém foi feita de modo que suporte outros sites, sem mecher no que já funciona. :]

Basicamente no projeto, temos dentro da pasta service a seguinte estrutura: 

-- service

---- strategy

------ BrowseMercadoLivreService

------ BrowseService

------ BrowseServiceStrategy

Assim, com esse padrão de projeto, para cada vez que tiver uma nova implementação de algum site, crio um arquivo com o nome Browse<NomeDoSite>Service, e faço a implementação desse site. 

Referencia: https://refactoring.guru/pt-br/design-patterns/strategy
