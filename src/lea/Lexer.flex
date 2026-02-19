 /*
 * SPDX-License-Identifier: MIT
 * Author: Matthieu Perrin
 * Session: Introduction to JFlex
 */
 
 // Code Java et définitions (copié tel quel)

package lea;

%% // Options et déclarations JFlex

%public
%class Lexer
%type Token
%unicode
%line
%column

%{
  private Reporter reporter;

  /**
   * Construit un lexer avec un gestionnaire d'erreurs personnalisé.
   * @param reader   Le flux de caractères à analyser.
   * @param reporter Le collecteur de diagnostics pour les erreurs lexicales.
   */
  public Lexer(java.io.Reader reader, Reporter reporter) {
    this(reader);
    this.reporter = reporter;
  }

  /**
   * Crée un signalement d'erreur au point actuel de l'analyse.
   */
  private void error(String message) {
    reporter.error(new Reporter.Span(yyline+1, yycolumn+1, yylength()), message);
    
  }
  
%}

DIGIT = [0-9]

%% // Règles lexicales

"si"						{ return new Token.KeyWord(yytext()); }
"sinon"						{ return new Token.KeyWord(yytext()); }
\"([^\"\\\n]|\\.)*\" { return new Token.StringLiteral(yytext()); }
\"([^\"\\\n]|\\.)*  { error("caractère inattendu " + yytext()); }

{DIGIT}+					{ return new Token.Number(yytext()); }
[A-Za-z_][A-Za-z0-9_]* { return new Token.Identifier(yytext()); }
"+"					{ return new Token.Operator(yytext()); }
"-"					{ return new Token.Operator(yytext()); }
"*"					{ return new Token.Operator(yytext()); }
'(\\.|[^’\\\n])'					{ return new Token.CharLiteral(yytext()); }

[ \t\n]+                {System.out.print(Main.FG_GRAY + "·" + Main.RESET);}





 /* Suppression de caractères qui n'ont pas de rôle sémantique */
\/\/.*						{ /* One-line comments */ }
\/\*([^\*]|\*[^\/])*\*\/	{ /* Multi-line comments */ }

[^]							{ error("caractère inattendu "+ yytext());}
