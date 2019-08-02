package derivation;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Derivation_operation {
	private String in=null;
	private ArrayList<Terms> Term_series=new ArrayList<Terms>();
	public Derivation_operation(String instr)
	{
		in=instr;
		String term_regx="(x(\\^(\\+|\\-)?\\d+)?|sin\\(x\\)(\\^(\\+|\\-)?\\d+)?"
				+ "|cos\\(x\\)(\\^(\\+|\\-)?\\d+)?|\\d+)"
				+ "(\\*x(\\^(\\+|\\-)?\\d+)?|\\*sin\\(x\\)(\\^(\\+|\\-)?\\d+)?"
				+ "|\\*cos\\(x\\)(\\^(\\+|\\-)?\\d+)?|\\*(\\+|\\-)?\\d+){0,}";
		Pattern p=Pattern.compile(term_regx);
		Matcher m=p.matcher(in);
		String str_temp=null;
		int k=0;
		//System.out.println("operation begin!");
		while(m.find()) {
			str_temp=m.group();
			Terms t=new Terms(str_temp,judge_symbol(k));
			
			in=in.replaceFirst(term_regx,"");
			
			Term_series.add(t);
			k++;
		}
		//System.out.print("operation end!" + " " +in);
		if(!in.equals("")) {
			System.out.print("WRONG FORMAT!");
			System.exit(0);
		}
		
	}
	public void derivate() {
		int size=Term_series.size();
		for(int i=0,k=0;k<size;k++)
		{
			Terms temp=Term_series.get(i);
			Term_series.remove(i);
			for(int j=1;j<=3;j++) {
				Terms temp1=temp.derivate_terms1(j);
				Term_series.add(temp1);
			}
		}
		for(int i=0;i<Term_series.size();i++)
		{
			Terms temp=Term_series.get(i);
			for(int j=i+1;j<Term_series.size();) {
				Terms temp1=Term_series.get(j);
				if(temp.getindx().equals(temp1.getindx()) 
						&& temp.getindsin().equals(temp1.getindsin()) 
						&& temp.getindcos().equals(temp1.getindcos()))
				{
					temp.setcoeff(add_int(temp.getcoeff(),temp1.getcoeff()));
					Term_series.remove(j);
				}else j++;
			}
		}
		for(int i=0;i<Term_series.size();) {
			Terms temp=Term_series.get(i);
			if(temp.getcoeff().equals("0")) {
				Term_series.remove(i);
			}else i++;
		}
	}
	public void print() {
		String str="";
		for(int i=0;i<Term_series.size();i++) {
			Terms temp=Term_series.get(i);
			str=str+temp.toString();
		}
		if(!str.isEmpty()) {
		if(str.charAt(0)=='+') {
			str=str.substring(1);
		}
		System.out.print(str);
		}else
			System.out.print("0");
	}
	public void print_result() {
		for(int i=0;i<Term_series.size();i++) {
			Terms temp=Term_series.get(i);
			System.out.println(temp.getcoeff()+" "+temp.getindx()+" "+temp.getindsin()+" "+temp.getindcos());
		}
	}
	protected String add_int(String adder1,String adder2) {
		BigInteger m1=new BigInteger(adder1);
		BigInteger m2=new BigInteger(adder2);
		m1=m1.add(m2);
		return m1.toString();
	}
	protected boolean judge_symbol(int time) {
		int sub_num=0;
		int sym_num=0;
		char first=in.charAt(0);
		while(first=='+' || first=='-') {
			if(first=='-') {
				sub_num++;
			}
			if(first=='-'||first=='+') {
				in=in.substring(1);
				sym_num++;
			}else break;
			first=in.charAt(0);
		}
		if(sym_num>=4 || (time>0 && sym_num==0)) {
			System.out.print("WRONG FORMAT!");
			System.exit(0);
		}
		if(sub_num%2==0) return true;
		return false;
	}
}
