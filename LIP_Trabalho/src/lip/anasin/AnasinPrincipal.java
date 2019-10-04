package lip.anasin;

public class AnasinPrincipal {
	public static void main(String[] args) {
		String exp = "begin a = (2 + 3) end";
		AnasinSimples anasin = new AnasinSimples(exp);
		anasin.anasint();
	}
	
}
