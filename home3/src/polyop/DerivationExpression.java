package polyop;

import java.math.BigInteger;
import java.util.ArrayList;

public class DerivationExpression implements Derivation {
    private ArrayList<Term> termList = new ArrayList<Term>();
    private String in;
    private boolean expJudge = false;

    public DerivationExpression(String input) {
        in = input;
        char c = 0;
        int i = 0;
        boolean endsym = false;
        while (in != null && !in.equals("")) {
            boolean sym = judgeSymbol(i);
            Term temp = new Term(sym);
            String str = getString();
            temp.addFactor(str);
            if (lookChar() == '\0') {
                endsym = true;
            }
            else if (lookChar() == '*') {
                expJudge = true;
                while (lookChar() == '*') {
                    c = getChar();
                    str = getString();
                    temp.addFactor(str);
                }
            }
            i++;
            termList.add(temp);
            if (endsym) {
                break;
            }
        }
        if (i > 1) {
            expJudge = true;
        }
        if (!in.equals("")) {
            error();
        }
    }

    public boolean getJudgeExp() {
        return this.expJudge;
    }

    @Override
    public String derivation() {
        String str = "";
        for (int i = 0; i < termList.size(); i++) {
            str = str + termList.get(i).derivation();
        }
        return str;
    }

    public void error() {
        System.out.print("WRONG FORMAT!");
        System.exit(0);
    }

    protected char getChar() {
        if (in.equals("")) {
            return '\0';
        }
        char c = in.charAt(0);
        in = in.substring(1);
        return c;
    }

    protected char lookChar() {
        if (in.equals("")) {
            return '\0';
        }
        return in.charAt(0);
    }

    protected String getString() {
        int right = 0;
        int left = 0;
        char c = lookChar();
        ArrayList str = new ArrayList();
        if (c == 's') {
            str = sinString();
        } else if (c == 'c') {
            str = cosString();
        } else if (c == 'x') {
            str = xxString();
        } else if (c == '-' || c == '+') {
            str = symString();
        } else if (Character.isDigit(c)) {
            str = digitString();
        } else if (c == '(') {
            str = leftString();
        }
        char[] charTemp = new char[str.size()];
        for (int i = 0; i < str.size(); i++) {
            charTemp[i] = (char) str.get(i);
        }
        return new String(charTemp);
    }

    public ArrayList sinString() {
        ArrayList str = new ArrayList();
        char c = getChar();
        str.add(c);
        c = getChar();
        if (c != 'i') {
            error();
        }
        str.add(c);
        c = getChar();
        if (c != 'n') {
            error();
        }
        str.add(c);
        c = getChar();
        if (c != '(') {
            error();
        }
        int left = 0;
        int right = 0;
        left = left + 1;
        while (right != left) {
            str.add(c);
            c = getChar();
            if (c == '(') {
                left = left + 1;
            }
            else if (c == ')') {
                right = right + 1;
            }
            else if (c == '\0') {
                error();
            }
        }
        str.add(c);
        if (lookChar() == '^') {
            c = getChar();
            str.add(c);
            c = getChar();
            str.add(c);
            while (Character.isDigit(lookChar())) {
                c = getChar();
                str.add(c);
            }
        }
        return str;
    }

    public ArrayList cosString() {
        ArrayList str = new ArrayList();
        char c = getChar();
        str.add(c);
        c = getChar();
        if (c != 'o') {
            error();
        }
        str.add(c);
        c = getChar();
        if (c != 's') {
            error();
        }
        str.add(c);
        c = getChar();
        if (c != '(') {
            error();
        }
        int left = 0;
        int right = 0;
        left = left + 1;
        while (right != left) {
            str.add(c);
            c = getChar();
            if (c == '(') {
                left = left + 1;
            }
            else if (c == ')') {
                right = right + 1;
            }
            else if (c == '\0') {
                error();
            }
        }
        str.add(c);
        if (lookChar() == '^') {
            c = getChar();
            str.add(c);
            c = getChar();
            str.add(c);
            while (Character.isDigit(lookChar())) {
                c = getChar();
                str.add(c);
            }
        }
        return str;
    }

    public ArrayList xxString() {
        ArrayList str = new ArrayList();
        char c = getChar();
        str.add(c);
        if (lookChar() == '^') {
            c = getChar();
            str.add(c);
            c = getChar();
            str.add(c);
            while (Character.isDigit(lookChar())) {
                c = getChar();
                str.add(c);
            }
        }
        return str;
    }

    public ArrayList symString() {
        ArrayList str = new ArrayList();
        char c = getChar();
        str.add(c);
        while (Character.isDigit(lookChar())) {
            c = getChar();
            str.add(c);
        }
        return str;
    }

    public ArrayList digitString() {
        ArrayList str = new ArrayList();
        char c = getChar();
        str.add(c);
        while (Character.isDigit(lookChar())) {
            c = getChar();
            str.add(c);
        }
        return str;
    }

    public ArrayList leftString() {
        ArrayList str = new ArrayList();
        int left = 0;
        int right = 0;
        left = left + 1;
        char c = getChar();
        while (right != left) {
            str.add(c);
            c = getChar();
            if (c == '(') {
                left = left + 1;
            }
            else if (c == ')') {
                right = right + 1;
            }
            else if (c == '\0') {
                error();
            }
        }
        str.add(c);
        if (lookChar() == '^') {
            error();
        }
        return str;
    }

    protected boolean judgeSymbol(int time) {
        int subNum = 0;
        int symNum = 0;
        char first = in.charAt(0);
        while (first == '+' || first == '-') {
            if (first == '-') {
                subNum++;
            }
            if (first == '-' || first == '+') {
                in = in.substring(1);
                symNum++;
            } else {
                break;
            }
            first = in.charAt(0);
        }
        if (symNum >= 4 || (time > 0 && symNum == 0)) {
            System.out.print("WRONG FORMAT!");
            System.exit(0);
        }
        if (subNum % 2 == 0) {
            return true;
        }
        return false;
    }

    public void simplify() {
        for (int i = 0; i < termList.size(); i++) {
            Term temp = termList.get(i);
            temp.simplify();
        }
        for (int i = 0; i < termList.size(); ) {
            Term temp = termList.get(i);
            if (temp.getCoeff().equals("0")) {
                termList.remove(i);
            } else {
                i++;
            }
        }
        for (int i = 0; i < termList.size(); i++) {
            Term temp1 = termList.get(i);
            for (int j = i + 1; j < termList.size(); ) {
                Term temp2 = termList.get(j);
                if (equalTerm(temp1, temp2)) {
                    temp1.setCoeff(addInt(temp1.getCoeff(), temp2.getCoeff()));
                    termList.remove(j);
                } else {
                    j++;
                }
            }
        }
    }

    public String getStr() {
        String str = "";
        for (int i = 0; i < termList.size(); i++) {
            str = str + termList.get(i).getStr();
        }
        if (str.equals("")) {
            str = "0";
        }
        if (str.charAt(0) == '+') {
            str = str.substring(1);
        }
        return str;
    }

    protected String addInt(String adder1, String adder2) {
        BigInteger m1 = new BigInteger(adder1);
        BigInteger m2 = new BigInteger(adder2);
        m1 = m1.add(m2);
        return m1.toString();
    }

    public boolean equalTerm(Term t1, Term t2) {
        ArrayList<Factor> a1 = t1.getArray();
        ArrayList<Factor> b1 = t2.getArray();
        boolean judge = true;
        int i;
        int j;
        if (a1.size() != b1.size()) {
            judge = false;
        }
        else {
            for (i = 0; i < a1.size(); i++) {
                Factor f1 = (Factor) a1.get(i);
                for (j = 0; j < b1.size(); j++) {
                    Factor f2 = (Factor) b1.get(j);
                    if (equalFactor(f1, f2)) {
                        break;
                    }
                }
                if (j == b1.size()) {
                    break;
                }
            }
            if (i != a1.size()) {
                judge = false;
            }
        }
        return judge;
    }

    public boolean equalFactor(Factor factor1, Factor factor2) {
        return factor1.getBase().equals(factor2.getBase())
                && factor1.getPower().equals(factor2.getPower());
    }
}
