package lip.analex;

import java.util.ArrayList;
import java.util.List;

public class AnalexSimples {
	
	private List<TokenLexema> tokenLexemaList;
	private String cadeia;
	private int i;
	private char cadeiaArray[];

	/**
	 * Recebe uma cadeia a ser analisada
	 */
	public AnalexSimples(String cadeia) {
		this.cadeia = cadeia;
		this.tokenLexemaList = new ArrayList<TokenLexema>();
	}

	/**
	 * Analisa a cadeia.
	 * @return uma lista de {@link TokenLexema} ou <code>null</code>, caso a cadeia tenha um lexema inv√°lido.
	 */
	public List<TokenLexema> analex() {
		if (this.cadeia == null)
			return null;
		 
		cadeiaArray = this.cadeia.toCharArray();
		for (i = 0; i < cadeiaArray.length; i++) {
			if (!this.ehParentesis(cadeiaArray[i]))
			if (!this.ehOperador(cadeiaArray[i])) 
			if (!this.ehPontoVirgula(cadeiaArray[i]))
			if (!this.ehFloatOrLiteral(cadeiaArray[i]))	
			if (!this.ehIdentificador(cadeiaArray[i]))
			if (!Character.isWhitespace(cadeiaArray[i]))
			{
				//ERRO pois n√£o √© nada reconhecido...
				//retorne null
				return null;
			}
		}

		return this.tokenLexemaList;
	}

	private boolean ehParentesis(char c) {
		if (c == Token.PARENTESES_ESQ.getValor()) {
			this.tokenLexemaList.add(new TokenLexema(Token.PARENTESES_ESQ, "" + Token.PARENTESES_ESQ.getValor()));
			return true;
		} else if (c == Token.PARENTESES_DIR.getValor()) {
			this.tokenLexemaList.add(new TokenLexema(Token.PARENTESES_DIR, "" + Token.PARENTESES_DIR.getValor()));
			return true;
		}
		return false;
	}

	private boolean ehOperador(char c) {
		if (c == Token.OPERADOR_ATRIB.getValor()) {
			this.tokenLexemaList.add(new TokenLexema(Token.OPERADOR_ATRIB, "" + Token.OPERADOR_ATRIB.getValor()));
			return true;
		}  else if (c == Token.OPERADOR_MULT.getValor() && cadeiaArray[i+1] != Token.OPERADOR_POTEN.getValor()) {
			this.tokenLexemaList.add(new TokenLexema(Token.OPERADOR_MULT, "" + Token.OPERADOR_MULT.getValor()));
			return true;
		} else if (c == Token.OPERADOR_SOMA.getValor()) {
			this.tokenLexemaList.add(new TokenLexema(Token.OPERADOR_SOMA, "" + Token.OPERADOR_SOMA.getValor()));
			return true;
		} else if (c == Token.OPERADOR_SUBT.getValor()) {
			this.tokenLexemaList.add(new TokenLexema(Token.OPERADOR_SUBT, "" + Token.OPERADOR_SUBT.getValor()));
			return true;
		} else if (c == Token.OPERADOR_DIVI.getValor()) {
			this.tokenLexemaList.add(new TokenLexema(Token.OPERADOR_DIVI, "" + Token.OPERADOR_DIVI.getValor()));
			return true;
		} else if (c == Token.OPERADOR_POTEN.getValor() && cadeiaArray[i+1] == Token.OPERADOR_POTEN.getValor()) {
			i++;
			this.tokenLexemaList.add(new TokenLexema(Token.OPERADOR_POTEN, "" + Token.OPERADOR_POTEN.getValor() + Token.OPERADOR_POTEN.getValor()));
			return true;
		}
		return false;
	}

	public boolean ehPontoVirgula(char c) {
		if (c == Token.PONTO_VIRGULA.getValor()) {
			this.tokenLexemaList.add(new TokenLexema(Token.PONTO_VIRGULA, "" + Token.PONTO_VIRGULA.getValor()));
			return true;
		}
		return false;
	}
	
	public boolean ehFloatOrLiteral(char c) {
		String digito = "";
		boolean ehhliteral = true;
		if(Character.isDigit(c)) {
			do {
				if(Character.isDigit(cadeiaArray[i])) { 
					digito += cadeiaArray[i] + "";
					i = i + 1;
				}else {
					if(cadeiaArray[i] == Token.PONTO_FLUTUANTE.getValor() && 
							Character.isDigit(cadeiaArray[i-1]) && Character.isDigit(cadeiaArray[i+1])) {
						if(digito.contains(".")) {
							System.out.println("Erro de An·lise ponto flutuante duplo");
							return false;
						}
						ehhliteral = false;
						digito += cadeiaArray[i] + "";
						i = i + 1;
					}else 
						if(cadeiaArray[i] == Token.PONTO_FLUTUANTE.getValor() && 
							(!Character.isDigit(cadeiaArray[i-1]) || !Character.isDigit(cadeiaArray[i+1]))) {
								System.out.println("Erro de An·lise ponto flutuante incompleto");
								return false;
							}else {
								break;	
							}
					}
			} while(i != cadeiaArray.length && (Character.isDigit(cadeiaArray[i]) || cadeiaArray[i] == Token.PONTO_FLUTUANTE.getValor()));
			
			if (i != cadeiaArray.length) {
				i = i - 1;
			}
			if(ehhliteral == true) {
				this.tokenLexemaList.add(new TokenLexema(Token.LITERAL_INTEIRO, digito));
			}else {
				this.tokenLexemaList.add(new TokenLexema(Token.PONTO_FLUTUANTE, digito));
			}
			return true;
		}
		return false;
	}

	public boolean ehIdentificador(char c) {
		if (Character.isLetter(c)) {
			String identificador = "";
			do {
				identificador += cadeiaArray[i];
				i = i + 1;
			} while (i != cadeiaArray.length && (Character.isLetter(cadeiaArray[i]) || Character.isDigit(cadeiaArray[i])));
				this.tokenLexemaList.add(new TokenLexema(Token.IDENTIFICADOR, identificador));
			if (i != cadeiaArray.length) {
				i = i - 1;
			}
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		String res = "";
		for (TokenLexema tl : this.tokenLexemaList) {
			res += tl;
			res += "\n";
		}
		return res;
	}

}
