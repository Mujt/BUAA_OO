package polyop;

public class CosFactor extends Factor implements Derivation {

    public CosFactor(String base, String power) {
        super(base, power);
    }

    @Override
    public String derivation() {
        String str = null;
        str = super.derivation() + "*" + "sin(" + this.getBaseIn() + ")*-1";
        return str;
    }

}
