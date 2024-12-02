# ms-gestao-pedidos

# Gestão de Pedidos API

### Proposta de projeto Pós Tech FIAP - Tech Challenge - Fase 4

### Tópicos

- [Descrição do projeto](#descrição-do-projeto)
- [Funcionalidades](#funcionalidades)
- [Ferramentas utilizadas](#ferramentas-utilizadas)
- [Acesso ao projeto](#acesso-ao-projeto)
- [Sobre a aplicação](#sobre-a-aplicação)
- [Testes](#testes)
- [Guia de implantação](#guia-de-implantação)
- [Desenvolvedores](#desenvolvedores)

## Descrição do projeto

<p align="justify">
Este projeto mostra o desenvolvimento de um microsservice que faz toda a gestão de pedidos dentro de um sistema de e-commerce.
</p>

## Funcionalidades

`Funcionalidade 1:` Cadastrar, Consultar pedidos paginado, listar por status, listar por usuário, buscar por id, Alterar Status de pedido, alterar código de rastreio e Remover Produtos.

`Funcionalidade 2:` End-point para atualizar o código de rastreio de um pedido é utilizado pela api de Logistica.

`Funcionalidade 3:` End-point para cadastrar um pedido utiliza a api de Produtos para fazer a atualização do estoque.

## Ferramentas utilizadas
<div style="display: flex; gap: 15px">
<a href="https://www.java.com" target="_blank"> 
    <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="Java" width="40" height="40"/> 
</a>

<a href="https://spring.io/" target="_blank"> 
    <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/spring/spring-original.svg" alt="Spring" width="40" height="40"/> 
</a>

<a href="https://www.postman.com/" target="_blank"> 
    <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/postman/postman-original.svg" alt="Postman" width="40" /> 
</a>

<a href="https://junit.org/junit5/" target="_blank"> 
    <img src="https://camo.githubusercontent.com/47ab606787e47aee8033b92c8f1d05c0e74b9b81904550f35a8f54e39f6c993b/68747470733a2f2f6a756e69742e6f72672f6a756e6974352f6173736574732f696d672f6a756e6974352d6c6f676f2e706e67" alt="JUnit" width="40" height="40"/> 
</a>

<a href="https://www.postgresql.org/" target="_blank">
    <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/postgresql/postgresql-plain.svg" width="40"/>
</a>

<a href="https://www.docker.com/" target="_blank">
    <img src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/docker/docker-plain.svg" width="40"/>
</a>

</div>


## Acesso ao projeto

Você pode [acessar o código fonte do projeto](https://github.com/Grupo23TC/ms-gestao-pedidos).
Documentação swagger: [Swagger](http://localhost:8080/ms-gestao-pedidos.html)

## Sobre a aplicação

A aplicação foi arquitetada através dos princípios de Arquitetura MVC e foi projetada para ser consumida e consumir uma API Rest.

## Testes
Adotamos o TDD como metodologia para o desenvolvimento e utilizamos o JUnit como principal ferramenta para os testes
unitários e de integração.

## Guia de implantação
Para rodar o projeto localmente, precisamos que o microsservice de ms-produtos-catalog esteja rodando, pois o endpoint de cadastro utiliza esse microsservice para fazer a alteração no estoque. Além disso, precisamos do  [Docker Hub](https://www.docker.com/) instalado.
Rode o comando: <b><i>"docker-compose up"</i></b>
Confira em seu Docker Desktop a subida do container e suas respectivas aplicações e bancos de dados isolados.

## Desenvolvedores
<table align="center">
  <tr>
    <td align="center">
      <div>
        <img src="https://avatars.githubusercontent.com/LucasFrancoBN" width="120px;" alt="Foto no GitHub" class="profile"/><br>
          <b> Lucas Franco   </b><br>
            <a href="https://www.linkedin.com/in/lucas-franco-barbosa-navarro-a51937221/" alt="Linkedin"><img src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white" height="20"></a>
            <a href="https://github.com/LucasFrancoBN" alt="Github"><img src="https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white" height="20"></a>
      </div>
    </td>
  </tr>
</table>