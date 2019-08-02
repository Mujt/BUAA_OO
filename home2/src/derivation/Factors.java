package derivation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Factors {
	private String coefficient;
	private String base;
	private String index;
	public boolean isDigit() {
		String num_str="(\\+|\\-)?\\d+";
		Pattern p=Pattern.compile(num_str);
		Matcher m=p.matcher(base);
		if(m.matches()) {
			return true;
		}return false;
	}
	public boolean isOne() {
		if(index.equals("0"))return true;
		return false;
	}
	public String getBase() {
		return base;
	}
	public String getIndex() {
		return index;
	}
	public Factors(int k,String factor_str) {
		if(factor_str.equals("cos(x)")) {
			base="cos(x)";
			index="1";
			coefficient="1";
		}else if(factor_str.equals("-sin(x)")) {
			base="sin(x)";
			index="1";
			coefficient="-1";
		}
	}
	public Factors(String factor_str) {
		String factor_regx="x|sin\\(x\\)|cos\\(x\\)|(\\+|\\-)?\\d+";
		Pattern p=Pattern.compile(factor_regx);
		Matcher m=p.matcher(factor_str);
		if(m.find()) {
			base=m.group();
			factor_str=factor_str.replaceFirst(factor_regx,"");
		}
		if(!factor_str.equals("")) {
			index=factor_str.substring(1);
		}else index="1";
		coefficient="1";
	}
	public int ret_base() {
		if(base.equals("x")) {
			return 1;
		}else if(base.equals("sin(x)")) {
			return 2;
		}else {
			return 3;
		}
	}
	public String toString() {
		return coefficient+" "+base+" "+index;
	}
}
