/*
 * SPDX-License-Identifier: MIT
 * Author: Matthieu Perrin
 * Session: Introduction to JFlex
 */

package lea;

import java.util.*;

/**
 * Gestionnaire de diagnostics chargé de collecter les erreurs lors de l'analyse.
 * Permet de différer l'affichage des erreurs pour ne pas polluer la sortie standard.
 */
public final class Reporter {

	private final List<Diagnostics> diagnostics = new ArrayList<>();

	/**
	 * Enregistre une nouvelle erreur.
	 * @param position L'emplacement précis (ligne/colonne) de l'erreur.
	 * @param message  La description de l'erreur pour l'utilisateur.
	 */
	public void error(Span position, String message) {
		diagnostics.add(new Diagnostics(position, message));
	}

	/**
	 * Affiche un résumé des diagnostics sur la console.
	 * @return true si des erreurs ont été détectées, false sinon.
	 */
	public List<String> getErrors() {
		return diagnostics.stream().map(d->d.message).toList();
	}

	/**
	 * Représente une portion du fichier source par ses coordonnées.
	 */
	public boolean reportErrors() {
		if(diagnostics.isEmpty()) {
			System.out.println("La phase LEXER s'est terminée avec succès");
			return false;
		} else {
			System.out.println("La phase LEXER s'est terminée avec des erreurs");
			for (var d : diagnostics) {
				String pos = d.span().isEmpty() ? "" : d.span().toString();
				System.out.println("\t" + pos + d.message() + "\n");
			}
			return true;
		}
	}




	public record Diagnostics(Span span, String message) {}

	public static record Span(int startLine, int startColumn, int endLine, int endColumn) {
		public Span(int startLine, int startColumn, int length) { 
			this(startLine, startColumn, startLine, startColumn + length - 1);
		}
		private Span() { 
			this(-1,-1,-1,-1);
		}
		public Span union(Span other) {
			if (isEmpty()) return other;
			if (other.isEmpty()) return this;
			int sl = startLine, sc = startColumn, el=endLine, ec = endColumn;
			if(other.startLine < startLine || (other.startLine == startLine && other.startColumn < startColumn)) {
				sl = other.startLine; sc = other.startColumn;
			}
			if(endLine < other.endLine || (endLine == other.endLine && endColumn < other.endColumn)) {
				el = other.endLine; ec = other.endColumn;
			}
			return new Span (sl, sc, el, ec);
		}
		public String toString() {
			return "[" + startLine() + ":" + startColumn() + "->" + endLine() + ":" + endColumn() + "]\t";
		}
		public final static Span EMPTY = new Span();
		public boolean isEmpty() { return startLine < 0; }
	}

}
