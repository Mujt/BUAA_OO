package polyop;

public class SinFactor extends Factor implements Derivation {

    public SinFactor(String base, String power) {
        super(base, power);
    }

    @Override
    public String derivation() {
        String str = null;
        str = super.derivation() + "*" + "cos(" + super.getBaseIn() + ")";
        return str;
    }

}
