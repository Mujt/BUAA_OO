package homework_1_guidebook;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.math.BigInteger;

public class Terms {
	private int coeff;
	private int power;
	private String co_str;
	private String po_str;
	private boolean big_sym1=false;
	private boolean big_sym2=false;
	public String get_power() {
		return po_str;
	}
	public String get_coeff() {
		return co_str;
	}
	public void set_coeff(String coeff_new) {
		co_str=coeff_new;
	}
	public Terms(String termstr) {
		String str1="\\*x\\^|\\*x|x\\^|x";
		Pattern r=Pattern.compile(str1);
		Matcher m=r.matcher(termstr);
		if(m.find()) {
			if(m.group(0).equals("*x^")) {
				String str2[]=termstr.split("\\*x\\^");
				if(str2[0].length() >10 && str2[1].length()>10) {
					big_sym1=true;
					big_sym2=true;
				}else if(str2[0].length() >10 && str2[1].length()<=10){
					big_sym1=true;
					big_sym2=false;
				}else if(str2[0].length() >10 && str2[1].length()<=10){
					big_sym1=false;
					big_sym2=true;
				}else {
				this.coeff=Integer.parseInt(str2[0]);
				this.power=Integer.parseInt(str2[1]);
				}
				co_str=str2[0];
				po_str=str2[1];
			}else if(m.group(0).equals("*x")) {
				String str2[]=termstr.split("\\*x");
				if(str2[0].length()>10) {
					big_sym1=true;
					big_sym2=false;
				}else
				this.coeff=Integer.parseInt(str2[0]);
				this.power=1;
				co_str=str2[0];
				po_str="1";
			}else if(m.group(0).equals("x^")) {
				String str2[]=termstr.split("x\\^");
				if(str2[0].equals("") || str2[0].equals("+")) {
					this.coeff=1;
					co_str="1";
				}
				else {
					this.coeff=-1;
					co_str="-1";
				}
				if(str2[1].length()>10) {
					big_sym1=false;
					big_sym2=true;
				}else
				this.power=Integer.parseInt(str2[1]);
				po_str=str2[1];
			}
			else if(m.group(0).equals("x")) {
				String str2[]=termstr.split("x");
				if(str2.length==0||str2[0].equals("") || str2[0].equals("+")) {
					this.coeff=1;
					co_str="1";
				}
				else {
					this.coeff=-1;
					co_str="-1";
				}
				this.power=1;
				po_str="1";
			}
		}else {
			this.coeff=1;
			this.power=0;
			co_str="1";
			po_str="0";
		}
	}
	public void setsub() {
		if(big_sym1 || big_sym2) {
		if(co_str.charAt(0)=='-')co_str=co_str.substring(1);
		else if(co_str.charAt(0)=='+')co_str="-"+co_str.substring(1);
		else co_str="-"+co_str;
		}else
			this.coeff=-this.coeff;
	}
	public void setsub1() {
		if(co_str.charAt(0)=='-')co_str=co_str.substring(1);
		else if(co_str.charAt(0)=='+')co_str="-"+co_str.substring(1);
		else co_str="-"+co_str;
	}
	public String derivateString() {
		int a=this.coeff;
		int b=this.power;
		String retstr=null;
		a=a*b;
		b=b-1;
		this.coeff=a;
		this.power=b;
		if(a==0) {
			retstr="";
		}else if(a==-1) {
			if(b==0)retstr="-1";
			else if(b==1)retstr="-x";
			else {
				retstr="-x^"+b;
			}
		}else if(a==1){
			if(b==0)retstr="+1";
			else if(b==1)retstr="+x";
			else {
				retstr="+x^"+b;
			}
		}else {
			String str=null;
			if(a>0) str="+"+a;
			else str=""+a;
			if(b==0)retstr=str;
			else if(b==1)retstr=str+"*x";
			else {
				retstr=str+"*x^"+b;
			}
		}
		return retstr;
	}
	public boolean jud_big() {
		if(big_sym1 || big_sym2) {
			return true;
		}return false;
	}
	public String derivateString1() {
		String retstr=null;
		BigInteger a=new BigInteger(co_str);
		BigInteger b=new BigInteger(po_str);
		BigInteger single=new BigInteger("1");
		BigInteger zero=new BigInteger("0");
		BigInteger subsingle=new BigInteger("-1");
			a=a.multiply(b);
			b=b.subtract(single);
		if(a.compareTo(zero)==0) {
			retstr="";
		}else if(a.compareTo(subsingle)==0) {
			if(b.compareTo(zero)==0)retstr="-1";
			else if(b.compareTo(single)==0)retstr="-x";
			else {
				retstr="-x^"+b;
			}
		}else if(a.compareTo(single)==0){
			if(b.compareTo(zero)==0)retstr="+1";
			else if(b.compareTo(single)==0)retstr="+x";
			else {
				retstr="+x^"+b;
			}
		}else {
			String str=null;
			if(a.compareTo(zero)==1) str="+"+a;
			else str=""+a;
			if(b.compareTo(zero)==0)retstr=str;
			else if(b.compareTo(single)==0)retstr=str+"*x";
			else {
				retstr=str+"*x^"+b;
			}
		}
		return retstr;
	}
}
