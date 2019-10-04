package lip.anasin;

import java.util.List;

import javax.swing.JOptionPane;

import lip.analex.AnalexSimples;
import lip.analex.Token;
import lip.analex.TokenLexema;

public class AnasinSimples {

	private List<TokenLexema> tokenLexemaList;
	private int contador = 0;
	private int numParen1 = 0;
	private int numParen2 = 0;

	public AnasinSimples(String cadeia) {
		AnalexSimples analex = new AnalexSimples(cadeia);
		this.tokenLexemaList = analex.analex();
	}
	public void anasint() {
		if(this.tokenLexemaList!=null) {
			this.program();
			if(this.contador!=tokenLexemaList.size()-1) {
				//ERRO NO ANALISADOR SINTÃ�TICO
				this.msgJ("ERRO NO ANALISADOR SINTATICO");
			}
		}
		else {
			//ERRO NO ANALISADOR LÃ‰XICO
			this.msgJ("ERRO NO ANALISADOR LEXICO");
		}

	}

	private void program() {
		this.msg("Entrou no program()");
		if(this.nextToken().getToken() == Token.BEGIN) {
		this.msg("teste , token: " + this.nextToken());
		this.lex();
		this.stmt_list();
			if(this.nextToken().getToken() != Token.END){
				this.msgJ("ERRO expressão faltando");
				//this.msg("teste end, token: " + this.nextToken());
			}else if(this.nextToken().getToken() == Token.END && tokenLexemaList.get(contador-1).getToken() == Token.OPERADOR_ATRIB) {
				this.msgJ("ERRO expressão faltando");
			}
		}
		this.msg("Saiu do program()");
	}

	private void stmt_list() {
		this.msg("Entrou no stmt_list()");
		this.stmt();
		while(this.nextToken().getToken() == Token.PONTO_VIRGULA) {
			this.lex();
			if(this.nextToken().getToken() == Token.IDENTIFICADOR) {
					this.stmt_list();
			}else {
				this.msgJ("ERRO Gramaticalll ");
				return;
			}
		}

		this.msg("Saiu do stmt_list()");
	}

	private void stmt() {
		this.msg("Entrou stmt()");
		if((this.nextToken().getToken() == Token.PARENTESES_ESQ ||
		this.nextToken().getToken() == Token.PARENTESES_DIR)  ||
		(tokenLexemaList.get(contador+1).getToken() == Token.PARENTESES_DIR || 
		tokenLexemaList.get(contador+1).getToken() == Token.IDENTIFICADOR)){
			this.msgJ("ERRO Gramaticalll ");
			return;
		}else if(this.nextToken().getToken() == Token.PARENTESES_ESQ && 
				(tokenLexemaList.get(contador+1).getToken() == Token.PARENTESES_DIR ||
				tokenLexemaList.get(contador-1).getToken() == Token.OPERADOR_ATRIB)||
				(this.nextToken().getToken() == Token.IDENTIFICADOR && (this.nextToken().getLexema().equals("begin")) || this.nextToken().getLexema().equals("end"))){
			this.msgJ("ERRO Gramaticalll ");
			return;
		//}else {
			
		}
		if(this.nextToken().getToken() == Token.IDENTIFICADOR && tokenLexemaList.get(contador+1).getToken() == Token.OPERADOR_ATRIB) {
			this.msg("teste id, token: " + this.nextToken());
			this.lex();
			if(this.nextToken().getToken() == Token.OPERADOR_ATRIB && tokenLexemaList.get(contador+1).getToken() != Token.END) {
				this.msg("teste =, token: " + this.nextToken());
				this.lex();
				if(this.nextToken().getToken() == Token.IDENTIFICADOR || 
						this.nextToken().getToken() == Token.LITERAL_INTEIRO ||
						this.nextToken().getToken() == Token.PONTO_FLUTUANTE || 
						this.nextToken().getToken() == Token.PARENTESES_DIR ||
						this.nextToken().getToken() == Token.PARENTESES_ESQ) {
						this.expr();
					}else if(this.nextToken().getToken() != Token.END){
						this.msgJ("ERRO expressão invalida ");
					}
			}
		}
		
		this.msg("Saiu stmt() teste id, token: " + this.nextToken());
	}

	private void expr() {
		this.msg("Entrou expr(), token: " + this.nextToken());
		//this.lex()	
		this.term();
		
		while(this.nextToken().getToken() == Token.OPERADOR_SOMA ||
				this.nextToken().getToken() == Token.OPERADOR_SUBT) {
			this.lex();
			if(this.nextToken().getToken() == Token.OPERADOR_SOMA ||
					this.nextToken().getToken() == Token.OPERADOR_SUBT) {
				this.msgJ("ERRO operador Duplo"+ this.nextToken()+"  -");
				break;
			}
			if(this.nextToken().getToken() == Token.OPERADOR_MULT ||
					this.nextToken().getToken() == Token.OPERADOR_DIVI||
					this.nextToken().getToken() == Token.OPERADOR_POTEN||
					this.nextToken().getToken() == Token.PARENTESES_DIR||
					this.nextToken().getToken() == Token.END) {
				this.msgJ("ERRO de Gramaticall com o operador "+ tokenLexemaList.get(contador-1).getLexema()+ " e "  + this.nextToken().getLexema());
				break;
			}
			this.term();
		}
		this.msg("Saiu expr(), token: " + this.nextToken());
	}

	private void term() {
		this.msg("Entrou term(), token: " + this.nextToken());
		//this.lex();
		this.factor();
		while((this.nextToken().getToken() == Token.OPERADOR_MULT && this.nextToken().getToken() != Token.OPERADOR_POTEN) ||
				this.nextToken().getToken() == Token.OPERADOR_DIVI) {
			this.lex();
			if(this.nextToken().getToken() == Token.OPERADOR_DIVI) {
				this.msgJ("ERRO operador Duplo"+ this.nextToken());
				break;
			}
			if(this.nextToken().getToken() == Token.OPERADOR_SOMA ||
					this.nextToken().getToken() == Token.OPERADOR_SUBT||
					this.nextToken().getToken() == Token.OPERADOR_MULT ||
					this.nextToken().getToken() == Token.OPERADOR_DIVI||
					this.nextToken().getToken() == Token.OPERADOR_POTEN||
					this.nextToken().getToken() == Token.PARENTESES_DIR||
					this.nextToken().getToken() == Token.END) {
				this.msgJ("ERRO de Gramatical com o operador "+ tokenLexemaList.get(contador-1).getLexema()+ " e "  + this.nextToken().getLexema());
				break;
			}
			this.factor();
		}
		this.msg("Saiu term(), token: " + this.nextToken());
	}

	private void factor() {
		this.msg("Entrou factor(), token: " + this.nextToken());
		//this.lex();
		this.exp();
		while(this.nextToken().getToken() == Token.OPERADOR_POTEN) {
			this.lex();
			if(this.nextToken().getToken() == Token.OPERADOR_POTEN) {
				this.msgJ("ERRO operador Duplo"+ this.nextToken());
				break;
			}
			if(this.nextToken().getToken() == Token.OPERADOR_SOMA ||
					this.nextToken().getToken() == Token.OPERADOR_SUBT||
					this.nextToken().getToken() == Token.OPERADOR_MULT ||
					this.nextToken().getToken() == Token.OPERADOR_DIVI||
					this.nextToken().getToken() == Token.OPERADOR_POTEN||
					this.nextToken().getToken() == Token.PARENTESES_DIR||
					this.nextToken().getToken() == Token.END) {
				this.msgJ("ERRO de Gramaticall com o operador "+ tokenLexemaList.get(contador-1).getLexema()+ " e "  + this.nextToken().getLexema());
				break;
			}
			this.exp();
		}
		this.msg("Saiu factor(), token: " + this.nextToken());
	}

	private void exp() {
		this.msg("Entrou exp(), token: " + this.nextToken());
		if(this.nextToken().getToken() == Token.IDENTIFICADOR && 
				!((this.nextToken().getLexema().equals("begin")) || 
						(this.nextToken().getLexema().equals("end")))) {
			this.lex();
		}else if(this.nextToken().getToken() == Token.PONTO_FLUTUANTE) {
			this.lex();
		}else if(this.nextToken().getToken() == Token.LITERAL_INTEIRO) {
			this.lex();
		}else if(this.nextToken().getToken() == Token.PARENTESES_ESQ &&
				tokenLexemaList.get(contador+1).getToken() != Token.OPERADOR_SOMA &&
				tokenLexemaList.get(contador+1).getToken() != Token.OPERADOR_SUBT &&
				tokenLexemaList.get(contador+1).getToken() != Token.OPERADOR_MULT &&
				tokenLexemaList.get(contador+1).getToken() != Token.OPERADOR_POTEN &&
				tokenLexemaList.get(contador+1).getToken() != Token.OPERADOR_MULT &&
				tokenLexemaList.get(contador+1).getToken() != Token.OPERADOR_DIVI) {
			this.lex();
			this.expr();
		if(this.nextToken().getToken() == Token.PARENTESES_DIR) {
				this.lex();
			}
		}else if(this.nextToken().getToken() == Token.PARENTESES_DIR) {
			this.lex();
		} 
		if(this.nextToken().getToken() == Token.PARENTESES_DIR || this.nextToken().getToken() == Token.PARENTESES_ESQ ) {
			for(int i = 0; i < tokenLexemaList.size()-1; i++) {
				if(tokenLexemaList.get(i).getToken() == Token.PARENTESES_ESQ){
					numParen1++;
				}
				if(tokenLexemaList.get(i).getToken() == Token.PARENTESES_DIR){
					numParen2++;
				}
			}
		}
		
		if(numParen1 > numParen2) {
			this.msgJ("ERRO Parenteses Direito esperado ");
		}
		if(numParen1 < numParen2) {
			this.msgJ("ERRO Parenteses Esquerdo esperado ");
		}
		this.msg("Saiu do exp(), token: " + this.nextToken());
	}


	private void msg(String cadeia) {
		System.out.println(cadeia);
	}

	private void msgJ(String cadeia) {
		JOptionPane.showMessageDialog(null, cadeia);
		System.exit(0);
	}
	
	private void lex() {
		if(this.contador==this.tokenLexemaList.size()-1) return;
		this.contador++;
	}

	private TokenLexema nextToken() {
		if(this.contador==0 && this.tokenLexemaList.get(contador).getLexema().equals("begin")) {
			return new TokenLexema(Token.BEGIN, Token.BEGIN.getValor2()+"");
		}
		if(this.contador==this.tokenLexemaList.size()-1 && this.tokenLexemaList.get(contador).getLexema().equals("end")) {
			return new TokenLexema(Token.END, Token.END.getValor2()+"");
		}
		return this.tokenLexemaList.get(this.contador);
	}



}
