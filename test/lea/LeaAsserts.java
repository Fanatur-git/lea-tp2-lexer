package lea;

import static org.junit.jupiter.api.Assertions.*;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public final class LeaAsserts {

	private final Reporter reporter = new Reporter();
	private final List<Token> tokens = new ArrayList<Token>();
	
	public LeaAsserts(String source) {
		try (Reader reader = new StringReader(source)) {
			var lexer = new Lexer(reader, reporter);
			for(Token token = lexer.yylex(); token != null; token = lexer.yylex()) {
				tokens.add(token);
			}
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}
	
	public LeaAsserts assertHasErrorContaining(String fragment) {
		boolean matches = reporter.getErrors()
				.stream()
				.anyMatch(m -> m.contains(fragment));
		assertTrue(matches, () -> "Expected error containing: \"" + fragment + "\"");
		return this;
	}

	public LeaAsserts assertCuts(String... expected) {
		assertEquals(expected.length, tokens.size(), "Token count mismatch" + tokens);
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], tokens.get(i).text(), "Token mismatch at index " + i);
		}
		return this;
	}

	public LeaAsserts assertMatches(String... expected) {
		assertEquals(expected.length, tokens.size(), "Token count mismatch" + tokens);
		for (int i = 0; i < expected.length; i++) {
			assertEquals(expected[i], tokens.get(i).getClass().getSimpleName(), "Token mismatch at index " + i);
		}
		return this;
	}

}
