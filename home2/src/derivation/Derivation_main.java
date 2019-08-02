package derivation;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Derivation_main {
	public static void main(String args[]) {
		Scanner scan =new Scanner(System.in);
		String str1=null;
		if(scan.hasNextLine()) {
			str1=scan.nextLine();
			scan.close();
		}
		if(str1==null || str1.equals("")) {
			System.out.print("WRONG FORMAT!");
			System.exit(0);
		}
		String str_error="\\d+(\\s+|\t+)\\d+"
				+ "|(\\+|\\-)(\\s*)(\\+|\\-)\\s*(\\+|\\-)(\\s+)\\d+"
				+ "|(\\+|\\-)\\s*(\\+|\\-)\\s*(\\+|\\-)\\s*(x|s|c)"
				+ "|(\\^|\\*)(\\s*)(\\+|\\-)(\\s+)\\d+"
				+ "|x(\\s*)x|(\\+|\\-)\\*x"
				+ "|\\d+(\\s*)x"
				+ "|x(\\s*)\\d+"
				+ "|(x|\\d+)((sin)|(cos))|\\(x\\)(x|\\d+)"
				+ "|s(\\s+i|i\\s+|\\s+i\\s+)n"
				+ "|c(\\s+o|o\\s+|\\s+o\\s+)s"
				+ "|\\v";
		Pattern p = Pattern.compile(str_error);
		Matcher m = p.matcher(str1);
		if(m.find()) {
			System.out.print("WRONG FORMAT!");
			System.exit(0);
		}
		str1=str1.replaceAll("\\s+", "");
		if(str1.equals("")) {
			System.out.print("WRONG FORMAT!");
			System.exit(0);
		}
		Derivation_operation operation=new Derivation_operation(str1);
		operation.derivate();
		operation.print();
	}
}
