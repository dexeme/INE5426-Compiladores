# INE5426-Compiladores

Este projeto é um compilador desenvolvido para a disciplina INE5426 - Compiladores. Ele realiza análise léxica, sintática, semântica e gera código intermediário para uma linguagem de programação didática.

## Como Executar

1. **Compilar o projeto:**
   ```sh
   make
   ```
2. **Executar o compilador com um arquivo de entrada:**
   ```sh
   make run FILE=resources/instances/dimensions.txt
   ```
   Substitua o caminho após `FILE=` pelo arquivo de entrada desejado.

   **Exemplo:**
   ```sh
   make run FILE=resources/instances/simpleCode1.txt
   ```


## Funcionalidades
- **Análise Léxica:** Identifica tokens e erros léxicos.
- **Análise Sintática:** Constrói a AST e detecta erros de sintaxe.
- **Análise Semântica:** Verifica declarações, tipos, escopos e gera uma árvore de escopos com tabelas de símbolos.
- **Geração de Código Intermediário:** Produz uma representação intermediária do código.

## Exemplos de entrada
Veja exemplos em `resources/instances/` para testar diferentes aspectos do compilador.

## Autores  
- Gabriela Regina Lother
- Ismael Coral Hoepers Heinzelmann
- Lucas Castro Truppel Machado
- Lucas Coelho Pini de Sousa
- Tiago Faustino de Siqueira

---

