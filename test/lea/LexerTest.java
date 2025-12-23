package lea;

import static org.junit.jupiter.api.Assertions.*;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import lea.Reporter.Phase;

/**
 * JUnit tests for the Lexer class.
 */
public final class LexerTest {

	private static List<Token> analyse(String source, Reporter reporter) {
		var tokens = new ArrayList<Token>();
		try (Reader reader = new StringReader(source)) {
			var lexer = new Lexer(reader, reporter);
			for(Token token = lexer.yylex(); token != null; token = lexer.yylex()) {
				tokens.add(token);
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
		return tokens;
	}

	private static void assertHasErrorContaining(String source, String fragment) {
		var reporter = new Reporter();
		analyse(source, reporter);
		boolean matches = reporter.getErrors(Phase.LEXER)
				.stream()
				.anyMatch(m -> m.contains(fragment));
		assertTrue(matches, () -> "Expected error containing: \"" + fragment + "\"");
	}

	private static void assertCut(String source, String... expected) {
		var reporter = new Reporter();
		var tokens = analyse(source, reporter);
		assertEquals(expected.length, tokens.size(), "Token count mismatch" + tokens);
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], tokens.get(i).text(), "Token mismatch at index " + i);
		}
	}

	private static void assertMatches(String source, String... expected) {
		var reporter = new Reporter();
		var tokens = analyse(source, reporter);
		assertEquals(expected.length, tokens.size(), "Token count mismatch" + tokens);
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], tokens.get(i).getClass().getSimpleName(), "Token mismatch at index " + i);
		}
	}

	/* =========================
	 * === EX 2: TOKENS DE BASE
	 * ========================= */

	@Test
	void keyword_si() {
		String input = "si";
		assertCut(input, "si");
		assertMatches(input, "KeyWord");
	}

	@Test
	void keyword_sinon() {
		String input = "sinon";
		assertCut(input, "sinon");
		assertMatches(input, "KeyWord");
	}

	@Test
	void number_single_and_multi_digits() {
		String input = "0 7 42";
		assertCut(input, "0", "7", "42");
		assertMatches(input, "Number", "Number", "Number");
	}

	/* =========================
	 * === EX 2d: OPERATEURS + - *
	 * ========================= */

	@Test
	void operators_plus_minus_star() {
		String input = "+ - *";
		assertCut(input, "+", "-", "*");
		assertMatches(input, "Operator", "Operator", "Operator");
	}

	/* =========================
	 * === EX 2e: PRIORITES / LONGEST MATCH
	 * ========================= */

	@Test
	void si_followed_by_number_is_split() {
		String input = "si35";
		assertCut(input, "si", "35");
		assertMatches(input, "KeyWord", "Number");
	}

	/* =========================
	 * === EX 2e: IDENTIFIANTS (final behavior)
	 * ========================= */

	@Test
	void identifiers_basic_forms() {
		String input = "x _x x42 a_b_c";
		assertCut(input, "x", "_x", "x42", "a_b_c");
		assertMatches(input, "Identifier", "Identifier", "Identifier", "Identifier");
	}

	@Test
	void identifiers_can_contain_digits_after_first_char() {
		String input = "x0 a9 _7";
		assertCut(input, "x0", "a9", "_7");
		assertMatches(input, "Identifier", "Identifier", "Identifier");
	}

	/* =========================
	 * === EX 2: ESPACES / COMMENTAIRES
	 * ========================= */

	@Test
	void whitespace_is_ignored_as_tokens() {
		assertCut(" \n\t  \r" /* no tokens */);
	}

	@Test
	void line_comment_is_ignored() {
		String input = """
				si // comment
				42
				""";
		assertCut(input, "si", "42");
		assertMatches(input, "KeyWord", "Number");
	}

	@Test
	void block_comment_is_ignored() {
		String input = "si /* comment */ 42";
		assertCut(input, "si", "42");
		assertMatches(input, "KeyWord", "Number");
	}

	/* =========================
	 * === EX 2f: CHAR LITERALS
	 * ========================= */

	@Test
	void char_literals_basic_and_escape() {
		String input = "'a' '_' '\\n'";
		assertCut(input, "'a'", "'_'", "'\\n'");
		assertMatches(input, "CharLiteral", "CharLiteral", "CharLiteral");
	}

	@Test
	void char_literal_backslash_any_char_is_allowed() {
		String input = "'\\'' '\\\\'";
		assertCut(input, "'\\''", "'\\\\'");
		assertMatches(input, "CharLiteral", "CharLiteral");
	}

	/* =========================
	 * === EX 3: STRING LITERALS
	 * ========================= */

	@Test
	void string_literal_simple_no_escapes() {
		String input = "\"abc\" \"a b\" \"\"";
		assertCut(input, "\"abc\"", "\"a b\"", "\"\"");
		assertMatches(input, "StringLiteral", "StringLiteral", "StringLiteral");
	}

	@Test
	void string_literal_allows_escaped_quote() {
		String input = "\"a\\\"b\"";
		assertCut(input, "\"a\\\"b\"");
		assertMatches(input, "StringLiteral");
	}

	@Test
	void string_literal_allows_other_escapes_too() {
		String input = "\"\\\\n\" \"\\\\t\"";
		assertCut(input, "\"\\\\n\"", "\"\\\\t\"");
		assertMatches(input, "StringLiteral", "StringLiteral");
	}

	/* =========================
	 * === EX 2h: ERREURS LEXICALES
	 * ========================= */

	@Test
	void illegal_character_is_reported() {
		assertHasErrorContaining("2 @ 8", "caractère inattendu");
	}

	@Test
	void illegal_character_does_not_prevent_other_tokens() {
		String input = "2 @ 8";
		assertCut(input, "2", "8");
		assertMatches(input, "NUMBER", "NUMBER");
	}

	/* =========================
	 * === ROBUSTNESS: unfinished literals
	 * ========================= */

	@Test
	void unterminated_char_literal_is_reported() {
		assertHasErrorContaining("'a", "caractère inattendu");
	}

	@Test
	void unterminated_string_literal_is_reported() {
		assertHasErrorContaining("\"abc\n", "caractère inattendu");
	}

}
