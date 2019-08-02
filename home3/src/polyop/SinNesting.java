package polyop;

public class SinNesting extends Factor implements Derivation {

    public SinNesting(String base, String power) {
        super(base, power);
    }

    @Override
    public String derivation() {
        DerivationExpression operaTemp
                = new DerivationExpression(super.getBaseIn());
        if (operaTemp.getJudgeExp() && !judgeBrack()) {
            error();
        }
        DerivationExpression operaTemp1
                = new DerivationExpression(operaTemp.derivation());
        operaTemp1.simplify();
        return super.derivation() + "*" + "cos(" + super.getBaseIn() + ")"
                + "*(" + operaTemp1.getStr() + ")";
    }

    public String getString() {
        DerivationExpression operaTemp1
                = new DerivationExpression(super.getBaseIn());
        operaTemp1.simplify();
        String str = null;
        if (getPower().equals("0")) {
            str = "1";
        }
        else if (getPower().equals("1") || getPower().equals("+1")) {
            str = "sin(" + operaTemp1.getStr() + ")";
        }
        else {
            str = "sin(" + operaTemp1.getStr() + ")^" + getPower();
        }
        return str;
    }
}
