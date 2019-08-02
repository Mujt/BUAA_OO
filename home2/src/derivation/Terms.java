package derivation;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Terms {
	private String coefficient;
	private ArrayList<Factors> Factorseries =new ArrayList<Factors>();
	private String index_x;
	private String index_sin;
	private String index_cos;
	public String getcoeff() {
		return coefficient;
	}
	public void setcoeff(String coeff) {
		coefficient=coeff;
	}
	public String getindx() {
		return index_x;
	}
	public String getindsin() {
		return index_sin;
	}
	public String getindcos() {
		return index_cos;
	}
	protected String multiply_int(String mul1,String mul2) {
		BigInteger m1=new BigInteger(mul1);
		BigInteger m2=new BigInteger(mul2);
		m1=m1.multiply(m2);
		return m1.toString();
	}
	protected String add_int(String adder1,String adder2) {
		BigInteger m1=new BigInteger(adder1);
		BigInteger m2=new BigInteger(adder2);
		m1=m1.add(m2);
		return m1.toString();
	}
	public Terms(String coeff,String indx,String indsin,String indcos) {
		coefficient=coeff;
		index_x=indx;
		index_sin=indsin;
		index_cos=indcos;
	}
	public Terms(String term_str,boolean judge) {
		if(judge)coefficient="1";
		else coefficient="-1";
		String factors[]=term_str.split("\\*");
		for(int i=0;i<factors.length;i++) {
			Factors factor=new Factors(factors[i]);
			Factorseries.add(factor);
		}
		index_x="0";
		index_sin="0";
		index_cos="0";
		merge_factor();
	}
	protected void merge_factor() {
		for(int i=0;i<Factorseries.size();) {
			Factors f_temp=Factorseries.get(i);
			if(f_temp.isDigit()) {
				coefficient=multiply_int(coefficient,f_temp.getBase());
				Factorseries.remove(i);
				continue;
			}
			if(f_temp.isOne()) {
				Factorseries.remove(i);
				continue;
			}
			i++;
		}
		for(int i=0;i<Factorseries.size();) {
			Factors f_temp=Factorseries.get(i);
			if(f_temp.ret_base()==1) {
				index_x=add_int(index_x,f_temp.getIndex());
				Factorseries.remove(i);
			}else if(f_temp.ret_base()==2) {
				index_sin=add_int(index_sin,f_temp.getIndex());
				Factorseries.remove(i);
			}else if(f_temp.ret_base()==3) {
				index_cos=add_int(index_cos,f_temp.getIndex());
				Factorseries.remove(i);
			}
		}
	}
	protected String oper_one(String op,int i) {
		BigInteger p = new BigInteger(op);
		BigInteger p1 = new BigInteger("1");
		if(i==1)
			p=p.subtract(p1);
		else
			p=p.add(p1);
		return p.toString();
	}
	public String toString() {
		String str1="",str2="",str3="";
		if(index_x.equals("0")) {
			
		}else if(index_x.equals("1")){
			str1=str1+"x";
		}else {
			str1=str1+"x^"+index_x;
		}
		if(index_sin.equals("0")) {
		}else if(index_sin.equals("1")){
			str2=str2+"sin(x)";
		}else {
			str2=str2+"sin(x)^"+index_sin;
		}
		if(index_cos.equals("0")) {
		}else if(index_cos.equals("1")){
			str3=str3+"cos(x)";
		}else {
			str3=str3+"cos(x)^"+index_cos;
		}
		/*debug*/
		String str="";
		String op=null;
		boolean judge=false;
		if(coefficient.equals("1")) {
			op="+";
		}else if(coefficient.equals("-1")) {
			op="-";
		}else {
			if(coefficient.charAt(0)=='-') {
				op="";
				str=str+coefficient+"*";
			}else {
				op="+";
				str=str+coefficient+"*";
			}
			judge=true;
		}
		if(!str1.equals("")) {
			str=str+str1+"*";
			judge=true;
		}
		if(!str2.equals("")) {
			str=str+str2+"*";
			judge=true;
		}
		if(!str3.equals("")) {
			str=str+str3+"*";
			judge=true;
		}
		str=op+str;
		if(!judge)str=str+"1*";
		str=str.substring(0, str.length()-1);
		return str;
	}
	public Terms derivate_terms1(int time) {
		String coeff=coefficient;
		String indx=index_x;
		String indsin=index_sin;
		String indcos=index_cos;
		if(time==1) {
			coeff=multiply_int(coeff,indx);
			indx=oper_one(indx,1);
		}else if(time==2) {
			coeff=multiply_int(coeff,indsin);
			indsin=oper_one(indsin,1);
			indcos=oper_one(indcos,2);
		}else if(time==3) {
			coeff=multiply_int(coeff,indcos);
			coeff=multiply_int(coeff,"-1");
			indsin=oper_one(indsin,2);
			indcos=oper_one(indcos,1);
		}
		Terms temp=new Terms(coeff,indx,indsin,indcos);
		return temp;
	}
}
