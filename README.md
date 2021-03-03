# Simian project
> Análise de sequencias de DNA para identificar Símios.


### Consumindo o serviço
A aplicação tem dois endpoints expostos, /simian e /stats.

### /simian
É necessário realizar um HTTP POST com o JSON que representa a sequencia de DNA a ser analisada pelo serviço. O endpoint disponibilizado é:

	http://simianproject.brazilsouth.azurecontainer.io/simian

O formato do JSON a ser enviado se encontra na descrição do projeto abaixo e o header Content-Type enviado na requisição deve ser setado para application/json.
O post pode ser realizado via script, postman ou alguma aplicação.


### /stats
É necessário realizar um HTTP GET para o endpoint:

	http://simianproject.brazilsouth.azurecontainer.io/stats


## Descrição da aplicação

O serviço busca prover uma maneira fácil e eficiente de definir se um animal pode ser considerado Símio ou Humano.

Através do envio da sequência de DNA em um HTTP POST para "/simian" com um JSON no formato a seguir, será possível afirmar a ordem do animal:

```sh
{
	"dna": [ "ATCCTA", "CTCCAC", "CTCCTC", "ACTTCA", "CATCTT", "TCTGCA"]
}
```

Essa array de string simula a representação de uma matriz NxN com a sequencia de DNA do animal. O serviço deverá retornar uma resposta HTTP 200-OK caso o DNA enviado seja de um Símio e HTTP 403-FORBBIDEN caso seja de um humano.(Hoje a response retorna em seu body a sequência enviada, a ordem do animal(HUMAN ou SIMIAN) e o id persistido na base, caso não seja necessário para o consumidor, o corpo pode ser removido só retornando o Http status para melhorar a performance).

Outro serviço disponibilizado é uma requisição HTTP GET para "/stats" que retornará um JSON contendo a quantidade de humanos e simios já processados e a proporção de símios do total. O formato do Json ficará como no exemplo a seguir:
```sh
{
	"count_mutant_dna": 40,
	"count_human_dna": 100,
	"ratio": 0.4
}
```
Uma sequência de DNA só pode ser considerada válida se os caracteres apresentados na matriz forem aqueles que representam a base nitrogenada do DNA no formato Uppercase.(Foi interessante manter somente uppercase para não onerar a performance do algoritmo na análise da entrada):
```sh
"A", "C", "G", "T"
```
Para um animal ser considerado Símio, ele deve ter pelo menos 1 sequência de 4 caracteres iguais, por exemplo:

```sh
        ["ATCCTA", "CTCCAC", "CCCCTC", "ACTTCA", "CATCTT", "TCTGCA"]
```
Na terceira linha da matriz existe uma sequencia CCCC, isso já é suficiente para dizer que um animal é Símio. Também é importante notar que esse padrão pode ser encontrado em sequências verticais e diagonais na matriz.


## Tecnologia

A aplicação foi desenvolvida em java utilizando o framework Spring no padrão MVC.
Os testes unitários foram realizados utilizando JUnit 5. A aplicação está hospedada em uma instância de containers na Azure cloud junto com também uma instância de SQL Server, onde os dados são persistidos. Para melhor desempenho da aplicação, a região escolhida fica na américa do sul.

A cobertura dos testes desenvolvidos chegou a 86%.

![alt text](https://i.ibb.co/Ps2GXcQ/coverage-Total.png)


A entidade persistida tem o seguinte formato:
```sh
Animal{
			Long                 id,
			String[]             DNA,
			AnimalOrder    animalType
}
```
Onde AnimalOrder é um Enum(HUMAN, SIMIAN) que representa a ordem genética do animal.

A tabela representada no banco de dados possui os campos
```sh
ID BIGINT,
DNA VARBINARY,(8000) - (poderia ser maior.)
ANIMALTYPE VARCHAR,
DNAHASH VARBINARY(1000)
```
   Como o hibernate persiste a String[] de forma binária, foi interessante utilizar o tipo VARBINARY para armazenar esse dado, visto que sequências de DNA podem ser bem grandes e é necessário espaço para armazenar. Além disso, outras formas de armazenar os dados não seriam eficientes(como criar uma tabela relacional com cada string da array de char usando o id do DNA como FK dessas arrays e talvez uma coluna dizendo qual a linha da matriz que ela representa por exemplo).
   
   Como não é possível indexar colunas do tipo VARBINARY(8000), para garantir a unicidade dos DNAs persistidos na base, a coluna DNAHASH(1000) foi utilizada como marcador, onde ela recebe o valor processado do DNA pelo algoritmo de hash SHA-2 com uma função nativa do SQL Server, hashbytes(Essa solução talvez não seria a melhor pois amarramos a solução ao tipo de base de dados, talvez se precisassemos migrar para outro, seria necessário pensar em contornos ou métodos similares.. mas para esse cenário é funcional e efetiva), essa sendo possível de ser indexada. Então para todo dna incluído existe um hash 'único' que o representa, assim a coluna marcada como UNIQUE garante a unicidade dos DNAs.
   


### Busca dos padrões símios
O algoritmo de busca de padrões foi desenvolvido em duas etapas, uma versão foi feita utilizando o algoritmo KPM para a busca na horizontal e vertical, que tem complexidade de tempo O(n) que é um ótimo valor. A busca na diagonal foi feita de uma forma bem inefetiva onde uma matriz representando as diagonais da original era criada e o padrão era novamente buscado pela KPM. Apesar do KPM ser eficiente, o processo de criar uma nova matriz com a diagonal era muito custoso e onerava a performance do serviço.
Uma nova versão da busca foi customizada para o processo de busca na diagonal onde para cada posição [i][j], ele verificava tanto as 3 próximas posições [i+1],[j+1] quanto as posições [i+1][j-1] em busca de uma sequência do padrão buscado. Com isso o processo ficou muito mais eficiente.

O primeiro passo realizado pelo processo de busca é validar se a matriz enviada é N x N e se os caracteres presentes são os representantes da base nitrogenada. É importante dizer que se essas premissas puderem ser garantidas pelo consumidor do serviço, esse passo poderia ser removido, otimizando a performance do processo.
Após a validação, é realizado o processo de busca dos padrões que podem representar um símio, ou seja:
```sh
"AAAA"ou  "CCCC" ou "GGGG" ou "TTTT"
```
Garantindo que pelo menos um desses padrões aparecem, já podemos afirmar que o DNA recebido é de um símio.
Essa sequencia de padrões está fixada no AnimalService, onde o processo é realizado. Porém, os padrões buscados poderiam ser consumidos de uma base fazendo com que o processo não ficasse estático no código e novos padrões poderiam ser adicionados ou removidos "on demand" sem que a aplicação precisasse ser alterada (apesar disso gerar mais uma consulta em banco, que dependendo da finalidade da aplicação, pode ser custoso).

Outro ponto importante é que toda a lógica de busca de padrão foi desenvolvida em uma classe utilitária chamada StringPatternFinder de uma forma genérica. Com isso nós a deixamos desacoplada e podendo ser utilizada para outras finalidades, como a busca de outros padrões para outros fins. Ex:

- Converte o padrão de matriz recebido da requisição em uma matriz de caracteres(Não necessariamente seguindo a limitação dos padrões de caracter esperados do DNA)
```sh
char[][] getCharMatrix(String[] stringArray) ;
```
- Enviando a matriz e o array com o padrão desejado, é possível buscar esse padrão na horizontal e vertical da matriz
```sh
boolean isPatternPresentInRowColumn(char[][] charArrayMatrix, char[] pattern)
```
- Enviando a matriz e o array com o padrão desejado, é possível buscar esse padrão na diagonal da matriz
```sh
boolean isPatternPresentInDiagonal(char[][] matrix, char[] pattern)
```
- Passando a matriz e o array com um padrão desejado, é possível buscar esse padrão tanto na horizontal, vertical e diagonal.
```sh
boolean isPatternPresentInStringArray(char[][] charMatrix, char[] pattern)
```
- Aqui é possível procurar por uma lista toda de padrões pela matriz
```sh
boolean isAnyPatternPresentInStringArray(String[] stringArray, char[][] patterns)
```
Dessa forma, tendo a interface StringPatternFinder, e essa implementação realizada, é possível utilizar os métodos para diversos fins, além de deixar o código desacoplado usando a interface e futuramente podendo utilizar algoritmos mais eficientes que implementem a mesma.(Uma outra modificação que poderia ser feita é de mudar as entradas de caracteres e deixar uma coisa mais genérica como objetos e sendo possível realizar buscas de padrões em arrays de diferentes tipos desde que seja possível garantir a comparabilidade desses objetos para que o algoritmo realize as buscas).

### Estatísticas dos dados já processados
Para o outro serviço disponibilizado que retorna as estatísticas de quantidade de cada animal e a proporção de símios, foi criada uma query personalizada para buscar esses valores e um objeto representacional desses dados. O processo foi realizado assim para evitar a necessidade de realizar duas consultas no banco, o que poderia onerar a performance. Então a query retorna os dados, eles são processados e enviados pro consumidor por um DTO que é serializado no body da resposta.

