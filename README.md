# ELE-32 Lab 2

O programa foi desenvolvido em Java na IDE Eclipse. Procurou-se aplicar os conceito de Programação Orientada a Objeto e de Desenvolvimento Orientado a Teste. Dessa forma, o programa foi subdividido em quatro classes.

A primeira classe, denominada de Leitor, é responsável por ler o arquivo a ser compactado, armazenando-o na memória e gerando um dicionário com todos os símbolos iniciais (Strings cujo tamanho Unicode é unitário), e é responsável por aplicar a compactação de Lempel Ziv, gerando uma lista de valores booleanos (que representam os bits).

O algoritmo de compactação Lempel Ziv foi abstraído em um método estático com tipo genérico, podendo receber qualquer tipo de objeto Java comparável e capaz de gerar um valor Hash para sua utilização no dicionário (HashTable). Sua descrição segue: dado um dicionário inicial DIC que tem como chaves cadeias unitárias com todos os símbolos do alfabeto relacionadas com um índice crescente contado a partir de zero, e uma cadeia de símbolos LIST a ser compactada, cria-se uma nova lista (cadeia) temporária TEMP e transferem-se elementos de LIST para TEMP até que TEMP não pertença às chaves de DIC. Nesse caso, acrescenta-se TEMP como chave de DIC relacionada com um índice incrementado de uma unidade, e escreve-se na lista booleana de saída a representação em binário do índice de TEMP removido de seu último elemento. Repete-se esse processo até que não haja mais elementos em LIST. A representação binária utiliza a menor quantidade necessária de bits para se representar um inteiro que corresponde ao tamanho de DIC menos um.

É importante notar de todos os símbolos de LIST foram lidos e representados, inclusive o último. Para o último elemento, se adiciona TEMP a DIC, apenas se escreve TEMP na lista de booleanos.

A segunda classe, denominada Escritor, é responsável por escrever um arquivo em binário correspondente à lista booleana recebida e o alfabeto. Para tal, criou-se uma estrutura de arquivo com um cabeçalho fixo de cinco bytes, sendo que os quatro primeiros bytes do arquivo compactado, escrito em disco, representam a quantidade de bytes que o alfabeto ocupa em disco, e o quinto byte, representa a quantidade de bits que são lixo no final do arquivo. Após esse cabeçalho está disposto o alfabeto, cujo tamanho foi bem definido pelo cabeçalho. Terminada a representação do alfabeto, há a sequência de bits, cujo término se dá no final do arquivo, podendo ser os últimos bits do arquivo lixo, pois não se conseguiu completar um byte, e tal quantidade de lixo foi definida no cabeçalho do arquivo. Para se contabilizar todo o cabeçalho, o Escritor recebe do Leitor o dicionário inicial, que representa o alfabeto, e recebe a lista de booleanos a serem escritas. Dessa forma, ele calcula o tamanho em bytes do alfabeto e a quantidade de bits total e excedente do arquivo a ser escrito, sendo capaz de produzir o cabeçalho projetado.

As duas primeiras classes, relacionada à compactação são desacopladas das duas últimas classes, relacionadas à descompactação. O arquivo compactado tem todas as informações necessárias para a sua descompactação, isto é, apresenta seu alfabeto e sequência de bits bem definida.

A terceira classe, denominada LeitorCompactado, é responsável por ler o arquivo compactado, interpretando o seu cabeçalho, lendo o alfabeto, e gerando uma lista de booleanos correspondentes à sequência de bits do arquivo. Ela sabe exatamente o tamanho do alfabeto graças ao cabeçalho, com os quatro primeiros bytes indicando isso, e sabe exatamente onde parar de ler a sequência de bits graças ao quinto byte do cabeçalho, que indica a quantidade de bits lixo no final do arquivo.

A quarta classe, denominada EscritorDescompactado, é responsável por descompactar a lista de booleanos, gerando a cadeia de símbolos descompactado, e é responsável por escrevê-la em um arquivo descompactado. Para tal ele recebe o alfabeto e a sequência de bits lida pelo LeitorCompactado e executa a descompactação do Algoritmo de Lempel Ziv. O alfabeto é descrito como um dicionário cujas chaves são os índices e os valores associados são todas as cadeias de tamanho unitário, isto é, o alfabeto.

A descompactação de Lempel Ziv se dá com um dicionário inicial DIC, como descrito anteriormente, e uma sequência de booleanos BOOL. Devora-se uma quantia de booleanos de BOOL correspondentes ao tamanho de DIC, lendo-os como um número inteiro positivo, correspondentes ao índice de DIC. Assim, escreve-se o valor de DIC relacionado ao índice lido na saída e também adiciona-se a DIC a concatenação da penúltima cadeia escrita com o primeiro elemento da última cadeia escrita. Repete-se esse processo até que BOOL esteja vazia. É possível que o valor do índice lido não esteja em DIC, isso significa que na compactação a última cadeia lida era uma subcadeia da penúltima, dessa forma, o valor que não está em DIC é a concatenação da última cadeia lida com o seu primeiro elemento.

É importante notar, que assim como na compactação, a iteração de BOOL continua até o seu último elemento inclusive. Por isso é importante o cabeçalho que indica exatamente o último bit que importa do último byte do arquivo compactado.

O programa, por padrão, reinicia os dicionário quando estes atingem o tamanho de cerca de 2 bilhões, o limite superior da representação de um inteiro de 32 bits.  Esse número de reinicialização pode ser diminuído. A operação de reinicializar os dicionários não precisa ser sinalizada na sequência de bits uma vez que ela ocorre de forma determinística, durante a construção do dicionário, tanto na compactação quanto na descompactação, pois assim que eles chegam ao seu tamanho máximo, eles são imediatamente reiniciados. O processo de reinício é simples para a compactação, bastando reescrever o dicionário, porém para a descompactação é necessário tomar um cuidado extra, sendo necessária além da reescrita do dicionário, a leitura do seu primeiro símbolo, para que a leitura do próximo apresente um penúltimo.

Cada uma dessas classes foi testada independentemente, com testes de unidade, durante os seus desenvolvimentos. Depois, realizou-se o teste de integração das classes, com a compactação, descompactação e comparação do arquivo original com o arquivo descompactado, sendo estes rigorosamente iguais, comparados byte a byte.
