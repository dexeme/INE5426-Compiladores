# INE5426-Compiladores

Este projeto é um compilador desenvolvido para a disciplina INE5426 - Compiladores. Ele realiza análise léxica, sintática, semântica e gera código intermediário para uma linguagem de programação didática.

## Estrutura do Projeto

- `src/` — Código-fonte Java do compilador
  - `AL/` — Analisador Léxico
  - `AS/` — Analisador Sintático
  - `AST/` — Estruturas da Árvore Sintática Abstrata
  - `Semantics/` — Análise Semântica
  - `Symbols/` — Tabela de Símbolos e Árvore de Escopos
  - `CodeGeneration/` — Geração de Código Intermediário
  - `Util/` — Utilitários
  - `Main.java` — Ponto de entrada do compilador
- `resources/` — Recursos auxiliares
  - `automaton.json` — Autômato para o analisador léxico
  - `instances/` — Exemplos de código-fonte para teste
- `test/` — Testes automatizados
- `docs/` — Documentação e gramática

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

5**Mensagem de uso:**
   Se nenhum argumento for passado, ou se o arquivo não existir, o programa exibirá:
   ```
   Usage: java Main <input_file>
   ```
   ou
   ```
   Erro ao ler arquivo: Arquivo de entrada não encontrado: <caminho/do/arquivo>
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
- Tiago de Pacheco Moraes

---

