package polyop;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DerivationMain {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String stringIn = null;
        if (scan.hasNextLine()) {
            stringIn = scan.nextLine();
            scan.close();
        }

        if (stringIn == null || stringIn.equals("")) {
            System.out.print("WRONG FORMAT!");
            System.exit(0);
        }
        String strError = "\\d+(\\s+|\t+)\\d+"
                + "|(\\+|\\-)(\\s*)(\\+|\\-)\\s*(\\+|\\-)(\\s+)\\d+"
                + "|(\\+|\\-)\\s*(\\+|\\-)\\s*(\\+|\\-)\\s*(x|s|c)"
                + "|(\\^|\\*)(\\s*)(\\+|\\-)(\\s+)\\d+"
                + "|x(\\s*)x|(\\+|\\-)\\*x"
                + "|\\d+(\\s*)x"
                + "|x(\\s*)\\d+"
                + "|(x|\\d+)((sin)|(cos))|\\(x\\)(x|\\d+)"
                + "|s(\\s+i|i\\s+|\\s+i\\s+)n"
                + "|c(\\s+o|o\\s+|\\s+o\\s+)s"
                + "|\\v|\\f";
        Pattern p = Pattern.compile(strError);
        Matcher m = p.matcher(stringIn);
        if (m.find()) {
            System.out.print("WRONG FORMAT!");
            System.exit(0);
        }
        stringIn = stringIn.replaceAll("\\s+", "");
        if (stringIn.equals("")) {
            System.out.print("WRONG FORMAT!");
            System.exit(0);
        }

        DerivationExpression operation
                = new DerivationExpression(stringIn);
        String str = operation.derivation();
        String simplyPoint = "0(\\+|\\-|\\*)|(\\+|\\-|\\*)0" +
                "|\\(+\\d+\\)+|\\*1|1\\*";
        Pattern p1 = Pattern.compile(simplyPoint);
        Matcher m1 = null;
        String strpre = null;
        while (true) {
            DerivationExpression operation1
                    = new DerivationExpression(str);
            strpre = str;
            operation1.simplify();
            str = operation1.getStr();
            m1 = p1.matcher(str);
            if (strpre.equals(str)) {
                break;
            }
            if (m1.find()) {
                continue;
            }
            else {
                break;
            }
        }
        System.out.println(str);

    }

}
