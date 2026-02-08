package lea;

import org.junit.jupiter.api.Test;

/**
 * JUnit tests for the Lexer class of the starter code.
 */
public final class Exo1Test {

	/* =========================
	 * = KEYWORDS AND NUMBERS  =
	 * ========================= */

	@Test
	void keyword_si() {
		new LeaAsserts("si")
		.assertCuts("si")
		.assertMatches("KeyWord");
	}

	@Test
	void keyword_sinon() {
		new LeaAsserts("sinon")
		.assertCuts("sinon")
		.assertMatches("KeyWord");
	}

	@Test
	void number_single_and_multi_digits() {
		new LeaAsserts("358")
		.assertCuts("358")
		.assertMatches("Number");
	}

	/* =========================
	 * =      SEPARATORS       =
	 * ========================= */

	@Test
	void whitespace_is_ignored() {
		new LeaAsserts("  \n \t  \r  ")
		.assertCuts(/* rien */);
	}

	@Test
	void whitespace_separates_tokens() {
		new LeaAsserts("0 si 42")
		.assertCuts("0", "si", "42")
		.assertMatches("Number", "KeyWord", "Number");
	}

	/* =========================
	 * =       COMMENTS        =
	 * ========================= */

	@Test
	void line_comment_is_ignored() {
		new LeaAsserts("si // ceci est un commentaire \n sinon")
		.assertCuts("si", "sinon")
		.assertMatches("KeyWord", "KeyWord");
	}

	@Test
	void block_comment_is_ignored() {
		new LeaAsserts("0 /* commentaire \n multi \n ligne */ 1")
		.assertCuts("0", "1")
		.assertMatches("Number", "Number");
	}

	@Test
	void block_comment_is_not_greedy() {
		new LeaAsserts("/* c1 */ si /* c2 */")
		.assertCuts("si")
		.assertMatches("KeyWord");
	}
	
}
