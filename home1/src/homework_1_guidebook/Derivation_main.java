package homework_1_guidebook;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Derivation_main {
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		String str1=null;
		if(scan.hasNextLine()) {
			str1=scan.nextLine();
			scan.close();
		}
		if(str1==null || str1.length()>1000 || str1.length()==0) {
			System.out.print("WRONG FORMAT!");
			System.exit(0);
		}
		String str_space="[0-9]{1,}(\\s+|\t+)[0-9]{1,}"
				+ "|(\\+|\\-)(\\s*)(\\+|\\-)(\\s+|\\t+)[0-9]{1,}"
				+ "|\\^(\\s*)(\\+|\\-)(\\s+|\\t+)[0-9]{1,}"
				+ "|x(\\t+|\\s+)x|(\\+|\\-)\\*x"
				+ "|[0-9]{1,}(\\s+|\\t+)?x"
				+ "|x\\s*[0-9]{1,}"
				+ "|\\v";
		Pattern p = Pattern.compile(str_space);
		Matcher m = p.matcher(str1);
		if(m.find()) {
			System.out.print("WRONG FORMAT!");
			System.exit(0);
		}
		str1=str1.replaceAll("\\s|\t", "");
		Derivation_operation operate =new Derivation_operation(str1);
	}
}
