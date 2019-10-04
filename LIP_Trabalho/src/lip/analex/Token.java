package lip.analex;

public enum Token {
	
	PARENTESES_ESQ('('),
	PARENTESES_DIR(')'),
	OPERADOR_SOMA('+'),
	OPERADOR_MULT('*'),
	OPERADOR_DIVI('/'),
	OPERADOR_SUBT('-'),
	OPERADOR_ATRIB('='),
	OPERADOR_POTEN('*'),
	PONTO_VIRGULA(';'),
	END("end"),
	PONTO_FLUTUANTE('.'),
	BEGIN("begin"),
	IDENTIFICADOR('I'),
	LITERAL_INTEIRO('L');
	
	private char valor;
	private String valor2;
	
	Token(char valor) {
		this.valor = valor;
	}
	
	public char getValor() {
		return this.valor;
	}
		
	Token(String valor2) {
		this.valor2 = valor2;
	}

	public String getValor2() {
		return this.valor2;
	}

}
