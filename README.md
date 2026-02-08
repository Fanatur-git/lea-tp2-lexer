# Languages and Automata – TP 2: Introduction to JFlex

This repository contains the **starter code** for **TP 2** of the *Languages and Automata* course at Nantes University.

This practical session introduces **lexical analysis** using **JFlex**.  
Students build a lexer for a small algorithmic language (*Algo*), and learn how
regular expressions are integrated into a real lexer generator.

This TP is the **first step of the compiler/interpreter pipeline** that will be extended
through the following sessions (parser, static analysis, typing, interpretation).

See the [main organization](https://github.com/LangagesEtAutomates/) for more information on the course
and related teaching materials.

---

## Structure

```
├── LICENSE.txt           # MIT license (see organization-wide license file)
├── README.md             # This file
├── build.xml             # Ant build file
├── lib/                  # External libraries (JFlex, JUnit, etc.)
├── src/
│   └── lea/
│       ├── Lexer.flex    # JFlex lexer specification (to be completed)
│       ├── Main.java     # Entry point displaying the token stream
│       └── Token.java    # Token interface and variants
├── gen/                  # Generated sources (lexer)
├── build/                # Compiled main classes
└── test/                 # Test files or example inputs
```

Generated directories (`gen/`, `build/`) are produced automatically and should not be edited manually.

---

## Goals of the TP

This TP focuses on:
- understanding the structure of a **JFlex specification**,
- writing lexical rules using **regular expressions**,
- recognizing keywords, identifiers, numbers, operators and literals,
- understanding **rule priority** and longest-match behavior,
- reporting **lexical errors** without stopping the analysis,
- observing the produced **token stream**.

Special attention is paid to the distinction between:
- ignored characters (whitespace, comments),
- valid tokens,
- illegal characters that must be reported as errors.

---

## Build and Execution

The project uses **Apache Ant**.

- Generate the lexer and compile the project:

```bash
ant compile
```

This command:
- runs **JFlex** on `Lexer.flex`,
- compiles the generated lexer and supporting classes,
- produces compiled classes in `build/`.

⚠️ After **each modification** of `Lexer.flex`, the project must be regenerated and recompiled.

- Run the test suite

```bash
ant test
```

- Run the lexer on the default input:

```bash
ant run
```

or equivalently:

```bash
java -cp "build:lib/*" lea.Main
```

The program reads a sample input string, runs the lexer, and prints the recognized tokens
(using colors to distinguish token categories).

---

## Dependencies

All dependencies are provided in the `lib/` directory:

- **JFlex** — lexer generation  
- **JUnit** — testing support

No external installation is required beyond a JDK and Ant.

---

## License

All **source code** in this repository is distributed under the **MIT License**.

- The full legal text is available in [LICENSE.txt](LICENSE.txt).
- Organization-wide licensing details and attributions are documented in  
  [https://github.com/LangagesEtAutomates/.github/blob/main/LICENSE.md](https://github.com/LangagesEtAutomates/.github/blob/main/LICENSE.md)

This license applies to all Java sources, grammar files (`.flex`),
and supporting code in this repository.

---

## Contributing

Contributions are welcome, in particular:
- clarifications or improvements to diagnostics,
- fixes to the starter code,
- improvements to example inputs.

Please use pull requests to propose changes.
For significant modifications, consider opening an issue first to discuss the design.
