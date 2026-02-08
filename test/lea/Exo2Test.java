package lea;

import org.junit.jupiter.api.Test;

/**
 * JUnit Tests for Exercise 2 : operators, identifiers, priorities and errors.
 */
public final class Exo2Test {

	/* ============================================================
     * === 2d : OPÉRATEURS (+ - *)
     * ============================================================ */

    @Test
    void test_operators() {
        new LeaAsserts("+ - *")
            .assertCuts("+", "-", "*")
            .assertMatches("Operator", "Operator", "Operator");
    }

    /* ============================================================
     * === 2e & 2f : IDENTIFIANTS ET PRIORITÉS
     * ============================================================ */

    @Test
    void test_identifiers_basic() {
        new LeaAsserts("x _var x42 a_b_c")
            .assertCuts("x", "_var", "x42", "a_b_c")
            .assertMatches("Identifier", "Identifier", "Identifier", "Identifier");
    }

    @Test
    void test_priority_keyword_vs_identifier() {
        new LeaAsserts("si sinon")
            .assertMatches("KeyWord", "KeyWord");
    }

    @Test
    void test_longest_match() {
        new LeaAsserts("si35")
            .assertCuts("si35")
            .assertMatches("Identifier");
    }

    /* ============================================================
     * === 2g : LITTÉRAUX DE CARACTÈRES
     * ============================================================ */

    @Test
    void test_char_literals() {
        new LeaAsserts("'a' '_' '\\n' '\\''")
            .assertCuts("'a'", "'_'", "'\\n'", "'\\''")
            .assertMatches("CharLiteral", "CharLiteral", "CharLiteral", "CharLiteral");
    }

    /* ============================================================
     * === 2h & 2i : ESPACES ET ERREURS LEXICALES
     * ============================================================ */

    @Test
    void test_whitespace_produces_no_tokens() {
        new LeaAsserts("   \t  \n  ")
            .assertCuts(/* Liste vide attendue */);
    }

    @Test
    void test_illegal_character_reports_error() {
        new LeaAsserts("42 @ 7")
            .assertHasErrorContaining("caractère inattendu")
            .assertCuts("42", "7")
            .assertMatches("Number", "Number");
    }
    
}
