package lea;

import org.junit.jupiter.api.Test;

/**
 * JUnit Tests for Exercise 3 : strings
 */
public final class Exo3Test {
	
	/* ============================================================
     * === CHAÎNES SIMPLES
     * ============================================================ */

    @Test
    void test_string_simple() {
        new LeaAsserts("\"bonjour\" \"123\"")
            .assertCuts("\"bonjour\"", "\"123\"")
            .assertMatches("StringLiteral", "StringLiteral");
    }

    @Test
    void test_string_empty() {
        new LeaAsserts("\"\"")
            .assertCuts("\"\"")
            .assertMatches("StringLiteral");
    }

    /* ============================================================
     * === GOURMANDISE (Plusieurs chaînes sur une ligne)
     * ============================================================ */

    @Test
    void test_string_multiple_on_same_line() {
        new LeaAsserts("\"chaîne 1\" si \"chaîne 2\"")
            .assertCuts("\"chaîne 1\"", "si", "\"chaîne 2\"")
            .assertMatches("StringLiteral", "KeyWord", "StringLiteral");
    }

    /* ============================================================
     * === ÉCHAPPEMENT (Le guillemet protégé)
     * ============================================================ */

    @Test
    void test_string_with_escaped_quote() {
        String input = "\"Il a dit \\\"Bonjour\\\"\"";
        new LeaAsserts(input)
            .assertCuts(input)
            .assertMatches("StringLiteral");
    }

    @Test
    void test_string_with_escaped_backslash() {
        String input = "\"C:\\\\Program Files\"";
        new LeaAsserts(input)
            .assertCuts(input)
            .assertMatches("StringLiteral");
    }

    /* ============================================================
     * === ROBUSTESSE : ERREURS ET LIMITES
     * ============================================================ */

    @Test
    void test_string_unterminated_fails() {
        new LeaAsserts("\"chaîne non fermée \n si")
            .assertHasErrorContaining("caractère inattendu")
            .assertMatches("KeyWord"); // "si" doit quand même être reconnu après
    }
    
}
