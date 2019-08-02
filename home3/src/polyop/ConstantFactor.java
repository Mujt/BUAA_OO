package polyop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConstantFactor extends Factor implements Derivation {

    public ConstantFactor(String base, String power) {
        super(base, power);
    }

    public String derivation() {
        return "0";
    }

    public String getString() {
        String str = "(\\()(\\d+)(\\))";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(getBase());
        if (m.matches()) {
            return m.group(2);
        }
        return super.getString();
    }
}
