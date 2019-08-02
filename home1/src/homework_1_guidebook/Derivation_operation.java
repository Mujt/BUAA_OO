package homework_1_guidebook;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Derivation_operation {
	private ArrayList<Terms> termque = new ArrayList<Terms>();
	protected String add_int(String adder1,String adder2) {
		BigInteger ad1=new BigInteger(adder1);
		BigInteger ad2=new BigInteger(adder2);
		return ad1.add(ad2).toString();
	}
	protected void merge_terms() {
		for(int i=0;i<termque.size();i++) {
			Terms t1=termque.get(i);
			String coeff_re=t1.get_coeff();
			for(int j=i+1;j<termque.size();) {
				Terms t2=termque.get(j);
				if(t1.get_power().equals(t2.get_power())) {
					coeff_re=add_int(coeff_re,t2.get_coeff());
					termque.remove(j);
					j=i+1;
				}
				else j++;
			}
			t1.set_coeff(coeff_re);
		}
	}
	protected void derivate() {
		String str1="";
		for(int i=0;i<termque.size();i++) {
			Terms t=termque.get(i);
			str1=str1+t.derivateString1();
		}
		if(!str1.equals("")) {
		char fir=str1.charAt(0);
		if(fir == '+') {
			str1=str1.substring(1);
		}
		System.out.print(str1);
		}else {
			System.out.print("0");
		}
	}
	public Derivation_operation(String instr) {
		String str1="((\\+|\\-)?([0-9]{1,}\\*)x(\\^(\\+|\\-)?[0-9]{1,}))"
				+ "|((\\+|\\-)?x(\\^(\\+|\\-)?[0-9]{1,}))"
				+ "|((\\+|\\-)?([0-9]{1,}\\*)x)"
				+ "|((\\+|\\-)?[0-9]{1,})|(\\+|\\-)?x";
		Pattern p=Pattern.compile(str1);
		Matcher m=p.matcher(instr);
		char firchar='\0';
		boolean ex=false;
		while(m.find()) {
			if((instr.charAt(0) == '+' || instr.charAt(0) == '-') 
					&& 
				(instr.charAt(1) == '+' || instr.charAt(1) == '-')) {
				firchar=instr.charAt(0);
				ex=true;
			}
			String str2=m.group(0);
			instr=instr.replaceFirst(str1, "");
			Terms tt=new Terms(str2);
			if(ex) {
				ex=false;
				instr=instr.substring(1);
				if(firchar == '-')
						tt.setsub1();
			}
			termque.add(tt);
			
		}
		if(!instr.equals("")) {
			System.out.print("WRONG FORMAT!");
			System.exit(0);
		}
		
		merge_terms();
		derivate();
	}
}
