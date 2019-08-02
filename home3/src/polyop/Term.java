package polyop;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Term implements Derivation {
    private String coefficient;
    private ArrayList<Factor> factors = new ArrayList<Factor>();

    public Term(boolean judge) {
        if (judge) {
            coefficient = "1";
        }
        else {
            coefficient = "-1";
        }
    }

    public void addFactor(String instr) {
        Pattern p = Pattern.compile("(\\+|\\-)?\\d+");
        Matcher m = p.matcher(instr);
        Factor factor = null;
        if (m.matches()) {
            coefficient = multiplyInt(coefficient, instr);
        } else {
            FactorConstruction construction = new FactorConstruction(instr);
            factor = construction.getFactor();
            factors.add(factor);
        }
    }

    @Override
    public String derivation() {
        String str = "";
        for (int i = 0; i < factors.size(); i++) {
            str = str + "+" + coefficient + "*";
            if (factors.get(i) instanceof ExpressionFactor) {
                str = str + "(" + factors.get(i).derivation() + ")";
            } else {
                str = str + factors.get(i).derivation();
            }
            str = str + restString(i);
        }
        if (str.equals("")) {
            str = "+0";
        }
        return str;
    }

    public String restString(int order) {
        String str = "";
        for (int i = 0; i < factors.size(); i++) {
            if (i != order) {
                str = str + "*" + factors.get(i).getString();
            }
        }
        return str;
    }

    public String getStr() {
        String str = "";
        if (coefficient.charAt(0) != '-' && coefficient.charAt(0) != '+') {
            str = str + "+";
        }
        String str1 = "";
        for (int i = 0; i < factors.size(); i++) {
            Factor temp = factors.get(i);
            str1 = str1 + temp.getString() + "*";
        }
        if (!coefficient.equals("1") && !coefficient.equals("+1")
                && !coefficient.equals("-1")) {
            str = str + coefficient + "*";
            str = str + str1;
        } else {
            if (str1.equals("")) {
                str = str + coefficient + "*";
            }
            else {
                if (coefficient.equals("-1")) {
                    str = str + "-";
                }
                str = str + str1;
            }
        }
        str = str.substring(0, str.length() - 1);

        return str;
    }

    protected String addInt(String adder1, String adder2) {
        BigInteger m1 = new BigInteger(adder1);
        BigInteger m2 = new BigInteger(adder2);
        m1 = m1.add(m2);
        return m1.toString();
    }

    public String multiplyInt(String mul1, String mul2) {
        BigInteger m1 = new BigInteger(mul1);
        BigInteger m2 = new BigInteger(mul2);
        m1 = m1.multiply(m2);
        return m1.toString();
    }

    public void lookUp() {
        System.out.println(coefficient + ": ");
        for (int i = 0; i < factors.size(); i++) {
            System.out.println(factors.get(i).toString());
        }
    }

    public ArrayList<Factor> getArray() {
        return factors;
    }

    public void setCoeff(String coeff) {
        this.coefficient = coeff;
    }

    public String getCoeff() {
        return this.coefficient;
    }

    public void error() {
        System.out.println("WRONG FORMAT!");
        System.exit(0);
    }

    public void simplify() {
        for (int i = 0; i < factors.size(); i++) {
            Factor temp1 = factors.get(i);
            if (temp1 instanceof ExpressionFactor) {
                continue;
            }
            for (int j = i + 1; j < factors.size(); ) {
                Factor temp2 = factors.get(j);
                if (temp1.getBase().equals(temp2.getBase())) {
                    temp1.setPower(addInt(temp1.getPower(), temp2.getPower()));
                    factors.remove(j);
                } else {
                    j++;
                }
            }
        }
    }
}
