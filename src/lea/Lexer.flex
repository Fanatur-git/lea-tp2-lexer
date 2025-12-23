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

  public Lexer(java.io.Reader reader, Reporter reporter) {
    this(reader);
    this.reporter = reporter;
  }

  private void error(String message) {
    reporter.error(Reporter.Phase.LEXER, new Reporter.Span(yyline+1, yycolumn+1, yylength()), message);
  }
  
%}

DIGIT = [0-9]

%% // Règles lexicales

"si"						{ return new Token.KeyWord(yytext()); }
"sinon"						{ return new Token.KeyWord(yytext()); }
{DIGIT}+					{ return new Token.Number(yytext()); }

 /* Removal of characters that should not be parsed */
\/\/.*						{ /* One-line comments */ }
\/\*([^\*]|\*[^\/])*\*\/	{ /* Multi-line comments */ }

[^]							{ System.out.print(Main.FG_GRAY + yytext() + Main.RESET); }
