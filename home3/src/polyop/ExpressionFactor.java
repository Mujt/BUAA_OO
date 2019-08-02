package polyop;

public class ExpressionFactor extends Factor implements Derivation {

    public ExpressionFactor(String base, String power) {
        super(base, power);
    }

    @Override
    public String derivation() {
        DerivationExpression operaTemp
                = new DerivationExpression(this.getBaseIn());
        DerivationExpression operaTemp1
                = new DerivationExpression(operaTemp.derivation());
        operaTemp1.simplify();
        return operaTemp1.getStr();
    }

    public String getString() {
        DerivationExpression operaTemp1
                = new DerivationExpression(super.getBaseIn());
        operaTemp1.simplify();
        return "(" + operaTemp1.getStr() + ")";
    }
}
