package polyop;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FactorConstruction {
    private int num;
    private String in;
    private String base;
    private String power;
    private String baseIn;
    private boolean judge2;
    private boolean judge3;

    public FactorConstruction(String instr) {
        String[] str = new String[8];
        str[0] = "x";
        str[1] = "x(\\^(\\+|\\-)?\\d+)?";
        str[2] = "(\\+|\\-)?\\d+";
        str[3] = "sin\\(.+\\)";
        str[4] = "(sin\\(.+\\))(\\^)((\\+|\\-)?\\d+)";
        str[5] = "cos\\(.+\\)";
        str[6] = "(cos\\(.+\\))(\\^)((\\+|\\-)?\\d+)";
        str[7] = "(\\()(" + ".+" + ")(\\))";
        int i = 0;
        this.base = null;
        this.power = "1";
        while (i < 8) {
            Pattern p = Pattern.compile(str[i]);
            Matcher m = p.matcher(instr);
            if (m.matches()) {
                if (i == 4 || i == 6) {
                    this.power = m.group(3);
                    this.base = m.group(1);
                }
                if (i == 7) {
                    this.baseIn = m.group(2);
                    this.base = m.group();
                }
                if (i == 3 || i == 5) {
                    this.base = m.group();
                }
                break;
            }
            i = i + 1;
        }
        num = i;
        this.in = instr;
    }

    public void judgeBase() {
        boolean judge = false;
        boolean judge1 = false;
        if (base != null && !base.equals("")) {
            String strregx = "(cos|sin)(\\()(.+)(\\))";
            Pattern p = Pattern.compile(strregx);
            Matcher m = p.matcher(base);
            if (m.matches()) {
                if (!m.group(3).equals("x")) {
                    judge = true;
                }
                String strreg = "(\\+|\\-)?\\d+";
                Pattern p1 = Pattern.compile(strreg);
                Matcher m1 = p1.matcher(m.group(3));
                if (m1.matches()) {
                    judge1 = true;
                }
            }
        }
        judge2 = judge;
        judge3 = judge1;
    }

    public Factor getFactor() {
        Factor f = null;
        String[] str = new String[2];
        boolean judge = false;
        boolean judge1 = false;
        judgeBase();
        judge = judge2;
        judge1 = judge3;
        switch (num) {
            case 0: f = new Factor("x", "1");
                break;
            case 1: str = in.split("\\^");
                f = new Factor("x", str[1]);
                break;
            case 2: f = new Factor(in, "1");
                break;
            case 3: if (judge1) {
                    f = new ConstantFactor(in, "1");
                    break;
                } if (judge) {
                    f = new SinNesting(in, "1");
                } else {
                    f = new SinFactor(in, "1");
                }
                break;
            case 4: if (judge1) {
                    f = new ConstantFactor(this.base, this.power);
                    break;
                } if (judge) {
                    f = new SinNesting(this.base, this.power);
                } else {
                    f = new SinFactor(this.base, this.power);
                } break;
            case 5: if (judge1) {
                    f = new ConstantFactor(in, "1");
                    break;
                } if (judge) {
                    f = new CosNesting(in, "1");
                } else {
                    f = new CosFactor(in, "1");
                } break;
            case 6: if (judge1) {
                    f = new ConstantFactor(this.base, this.power);
                    break;
                } if (judge) {
                    f = new CosNesting(this.base, this.power);
                } else {
                    f = new CosFactor(this.base, this.power);
                } break;
            case 7: if (baseIn.matches("(\\+|\\-)?\\d+")) {
                    f = new ConstantFactor(this.base, "1");
                } else {
                    f = new ExpressionFactor(this.base, "1");
                } break;
            default: error();
                f = new Factor("1", "1");
                break;
        } return f;
    }

    public void error() {
        System.out.println("WRONG FORMAT!");
        System.exit(0);
    }

    public String multiplyInt(String mul1, String mul2) {
        BigInteger m1 = new BigInteger(mul1);
        BigInteger m2 = new BigInteger(mul2);
        m1 = m1.multiply(m2);
        return m1.toString();
    }

}
