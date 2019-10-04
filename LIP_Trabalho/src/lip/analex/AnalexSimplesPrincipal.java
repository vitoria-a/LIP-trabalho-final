package lip.analex;

public class AnalexSimplesPrincipal {
	
	public static void main(String[] args) {
		String exp = "n = ((a - b) + c ** d /(2.6 - 2) * 1 - e) / (f + g)";
		AnalexSimples analexSimples = new AnalexSimples(exp);
		analexSimples.analex();
		System.out.println(analexSimples);
	}
	
	 

}
