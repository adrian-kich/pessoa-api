
# API de Gerenciamento Pessoas e Endereços

Esta API foi desenvolvida para o gerenciamento Pessoas e Endereços e tem como principal objetivo o exercício de boas práticas utilizando padrões de projeto, princípios SOLID e conceitos de Clean Code, além do desenvolvimento de testes unitários e automatizados utilizando JUnit e Mockito.

## Inicialização do Projeto

- **Java:** 21 instalado.
- **Banco de Dados:** Altere as informações de conexão no arquivo `application.properties`.

## Sobre o Projeto

Este sistema é um gerenciamento de Pessoas e seus Endereços com as seguintes funcionalidade:
- Listar todas as pessoas e seus respectivos endereços.
- Criar uma nova pessoa com um ou mais endereços.
- Atualizar os dados de uma pessoa e/ou seu(s) endereço(s).
- Excluir uma pessoa e todos os seus endereços.
- Mostrar a Idade da Pessoa.

## Documentação
A documentação da API pode ser econtrada conforme abaixo:

**Acessar a Documentação Swagger**  
Após iniciar a aplicação, a documentação estará disponível em:
   ```
   http://localhost:8080/swagger-ui/index.html
   ```
Na raíz do projeto há uma collection do postman com as requisições configuradas.
   ```
   Pessoa api.postman_collection.json
   ```