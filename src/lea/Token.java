package lea;

public sealed interface Token {

	String text();
    
	public record KeyWord	(String text)	implements Token {}
	public record Operator	(String text)	implements Token {}
	public record Number	(String text)	implements Token {}
	
}