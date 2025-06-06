# Challenge - iFood

# Sumário

- [Motivação de Negócio (Hipótese, Descrição, Métricas e Solução)](#motivação-de-negócio)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Arquitetura](#arquitetura)
- [Mapeamento de Endpoints](#mapeamento-de-endpoints)
- [Exemplo de Cenário de Teste](#exemplo-de-cenário-de-teste)

# Motivação de Negócio

## Hipótese

O time de negócios do iFood notou que estabelecimentos menores não estavam cadastrando fotos dos seus produtos e existe uma suposição de que isso pode estar impactando negativamente suas vendas e indiretamente gerando menos receita para a empresa.  

Portanto, desejam validar a hipótese de que **itens exibidos com fotos são mais comprados do que itens sem foto (mesmo que essas fotos sejam geradas por IA)**.  

Para esse experimento, será utilizada a tab **Feira** da seção **Mercados**, onde será possível comparar o comportamento dos usuários diante das duas experiências de exibição de produtos.  

## Descrição do Experimento

O experimento consiste em exibir, para uma parcela dos usuários, a listagem de frutas com imagens geradas por IA (baseadas no nome de cada fruta), enquanto outro grupo de controle visualizará a listagem tradicional sem imagens.

A exibição das imagens será controlada por feature flag, permitindo comparações diretas entre os grupos.

Durante o experimento, serão rastreadas as interações dos usuários, especialmente os cliques no botão **"Add to Cart"**.

## Métricas de Teste Relevantes

Para avaliar o impacto do experimento, serão acompanhadas as seguintes métricas:

- **Taxa de Adição ao Carrinho**: Proporção de usuários que adicionam pelo menos um item ao carrinho.
- **Número Médio de Itens Adicionados por Usuário**: Quantidade média de itens adicionados ao carrinho por usuário em cada grupo.


## Solução
Para isso, foi criado um projeto que possui uma listagem de frutas com imagens geradas por IA e que podem ser adicionadas a um carrinho, conforme o seguinte esquema:
- A tab **Fruits** (Listagem de Frutas) recebe lista de frutas e imagens de endpoints externos.
- A tab **Cart** (Listagem do Carrinho) exibe os itens adicionados ao carrinho persistidos de forma local.

 <p align="center">
  <img alt="Listagem de Frutas" src="images/ListagemdeFrutas.png" width="45%">
&nbsp; &nbsp; &nbsp; &nbsp;
  <img alt="NoteAddEditScreen" src="images/DetalhesdeFruta.png" width="45%">
</p>

---

## Tecnologias Utilizadas

- **Kotlin** - Linguagem de programação
- **Jetpack Compose** - UI
- **Koin** - Injeção de dependência
- **Room** - Persistência local
- **Retrofit** - Consumo de APIs REST
- **JUnit5, Mockito** - Testes unitários (UseCase), testes  de integração (Repository) e mocks
- **Jetpack Compose Testing, JUnit4** - Testes instrumentados (Screen)

---

# Arquitetura

O projeto adota a arquitetura **Clean Architecture** com separação em camadas:

- **Presentation:** ViewModels e UI (Jetpack Compose/Activities/Fragments)
- **Domain:** Casos de uso e modelos de negócio
- **Data:** Repositórios, datasources locais (Room) e remotos (Retrofit)
- **DI:** Injeção de dependências com Koin


---

# Mapeamento de Endpoints


## Listagem de Frutas

Utilizado o [FruityVice](https://www.fruityvice.com/doc/index.html) que é um endpoint público gratuito que retorna uma lista com frutas e seus detalhes científicos/nutricionais.

| Método | Endpoint             | Descrição                                   | Parâmetros de Entrada                   | Resposta de Sucesso | Erros Comuns                      |
|--------|----------------------|---------------------------------------------|-----------------------------------------|---------------------|-----------------------------------|
| POST   | https://www.fruityvice.com/api/fruit/all     | Listagem de todas as frutas  | -         | Listagem de frutas (JSON)      | 500 Internal Server Error                  |

<details>
  <summary>Exemplo de Response - 200 Success</summary>

[
{
"name": "Apple",
"id": 6,
"family": "Rosaceae",
"order": "Rosales",
"genus": "Malus",
"nutritions": {
"carbohydrates": 11.4,
"protein": 0.3,
"fat": 0.4,
"calories": 52,
"sugar": 10.3
}
},
{
"name": "Banana",
"id": 1,
"family": "Musaceae",
"order": "Zingiberales",
"genus": "Musa",
"nutritions": {
"carbohydrates": 22,
"protein": 1,
"fat": 0.2,
"calories": 96,
"sugar": 17.2
}
}
]

</details>

## Geração de Imagem de IA - Cloudfare Workers AI

Utilizado o [Cloudfare Workers IA](https://developers.cloudflare.com/api/resources/ai/methods/run/) que fornece um endpoint que retorna uma imagem gerada por IA com base em texto.  
O prompt utilizado é: **"A {NOME_DA_FRUTA} in a basket, vibrant colors"**  
O modelo utilizado é: **@cf/bytedance/stable-diffusion-xl-lightning**

| Método | Endpoint            | Descrição                                   | Parâmetros de Entrada (Body)                           | Resposta de Sucesso | Erros Comuns                      |
|--------|---------------------|---------------------------------------------|-------------------------------------------------|---------------------|-----------------------------------|
| POST    | https://api.cloudflare.com/client/v4/accounts/{ACCOUNT_ID}/ai/run/{AI_MODEL_NAME}      | Gera uma imagem com base em texto         | prompt (texto base para geração da imagem), model (modelo de IA escolhido)                                               | Raw Base64 da imagem no body da resposta   | 500 Internal Server Error

<details>
  <summary>Exemplo de Response - 200 Success</summary>

BASE64 raw da imagem .PNG

</details>

---

## EXEMPLO DE CENÁRIO DE TESTE

## Tela 1: Listagem de Frutas

### Identificação

- **ID:** Tela-001
- **Módulo:** fruits
- **Responsáveis:** Time de desenvolvimento e equipe de QA

### Descrição

Validar se a listagem de frutas (textos e imagens) é exibida corretamente ao usuário.  
Validar se a listagem de frutas (textos) é exibida corretamente ao usuário quando as imagens não foram geradas.  
Validar que um placeholder é exibido no lugar da imagem quando ela não é carregada corretamente.  
Validar se a listagem de frutas exibe mensagem de erro quando textos dos itens estão indisponíveis.

### Pré-condições

- Aplicativo com acesso a internet.

### Passos do Teste

| Cenário | Ação                                 | Dados de Entrada     | Resultado Esperado                                                                                                                                          |
|-------|--------------------------------------|---------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1     | Acessar tela de listagem com carregamento de textos e imagens               |                    | Listagem com itens e imagens exibida conforme especificações do Figma                                                                                       | 
| 2     | Acessar tela de listagem com carregamento de textos e sem imagens geradas   |     | Listagem com itens e imagens com placeholder devem ser exibidos conforme especificações do Figma. Tracking de clique para teste A/B não deve ser disparado. |
| 3     | Informar usuário ou senha inválidos  |  | Mensagem de erro exibida corretamente conforme Figma                                                                                                        |

### Pós-condições

- Imagens carregadas devem ser mantidas ao navegar para tela de Detalhes/Aba de Carrinho e voltar.

### Critérios de Aceitação

- Experiência de sucesso e falha conforme o Figma
- Tracking do teste A/B validado com sucesso

### Outros Cenários de Teste

- **Tela de detalhes da fruta:**  
  Confirma se os detalhes de uma fruta são apresentados corretamente ao selecionar um item.

- **Adicionar fruta ao carrinho:**  
  Garante que a fruta selecionada é adicionada ao carrinho corretamente e persistida localmente.

- **Remover fruta do carrinho:**  
  Garante que a fruta selecionada é deletada do carrinho corretamente e atualização da UI.

---

