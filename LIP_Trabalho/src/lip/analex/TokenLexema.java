package lip.analex;

public class TokenLexema {
	
	private Token token;
	private String lexema;
	public TokenLexema(Token token, String lexema) {
		this.token = token;
		this.lexema = lexema;
	}
	
	public Token getToken() {
		return this.token;
	}
	
	public String getLexema() {
		return this.lexema;
	}
	
	@Override
	public String toString() {
		String res = "";
		res += "Token: " + token + " Lexema: " + lexema;
		return res;
	}

}
