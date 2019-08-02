package polyop;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Factor implements Derivation {
    private String coeffNumber;
    private String baseNumber;
    private String powerNumber;
    private String baseIn;

    public Factor(String base, String power) {
        this.coeffNumber = "1";
        this.baseNumber = base;
        this.powerNumber = power;
        if (!judgePower(power)) {
            System.out.println("WRONG FORMAT!");
            System.exit(0);
        }
        Pattern p = Pattern.compile("(sin|cos)(\\()(.+)(\\))");
        Matcher m = p.matcher(base);
        if (m.matches()) {
            this.baseIn = m.group(3);
        }
        Pattern p1 = Pattern.compile("(\\()(.+)(\\))");
        Matcher m1 = p1.matcher(base);
        if (m1.matches()) {
            this.baseIn = m1.group(2);
        }
    }

    protected boolean judgePower(String po) {
        try {
            int temp = Integer.parseInt(po);
            if (temp > 10000 || temp < -10000) {
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("WRONG FORMAT!");
            System.exit(0);
        }
        return true;
    }

    protected boolean judgeBrack() {
        if (!baseIn.matches("\\(.+\\)")) {
            return false;
        }
        return true;
    }

    public void error() {
        System.out.println("WRONG FORMAT!");
        System.exit(0);
    }

    @Override
    public String derivation() {
        String str = null;
        String coeff = coeffNumber;
        String power = powerNumber;
        coeff = power;
        power = addInt(power, "-1");
        if (coeff.equals("0")) {
            str = "0";
        } else {
            str = "" + coeff;
            if (!power.equals("0")) {
                if (!power.equals("1")) {
                    str = str + "*" + baseNumber + "^" + power;
                }
                else {
                    str = str + "*" + baseNumber;
                }
            }
        }
        return str;
    }

    protected String addInt(String adder1, String adder2) {
        BigInteger m1 = new BigInteger(adder1);
        BigInteger m2 = new BigInteger(adder2);
        m1 = m1.add(m2);
        return m1.toString();
    }

    protected String multiplyInt(String mul1, String mul2) {
        BigInteger m1 = new BigInteger(mul1);
        BigInteger m2 = new BigInteger(mul2);
        m1 = m1.multiply(m2);
        return m1.toString();
    }

    public String getCoefficient() {
        return this.coeffNumber;
    }

    public String getString() {
        if (powerNumber.equals("1") || powerNumber.equals("+1")) {
            return baseNumber;
        }
        else if (powerNumber.equals("0")) {
            return "1";
        }
        return baseNumber + "^" + powerNumber;
    }

    public String toString() {
        return baseNumber + " " + powerNumber;
    }

    public String getBase() {
        return this.baseNumber;
    }

    public String getPower() {
        return this.powerNumber;
    }

    public void setPower(String power) {
        this.powerNumber = power;
    }

    public String getBaseIn() { return this.baseIn; }
}
