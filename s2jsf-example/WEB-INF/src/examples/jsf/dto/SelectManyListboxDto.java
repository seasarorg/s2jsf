package examples.jsf.dto;

import java.io.Serializable;

public class SelectManyListboxDto implements Serializable {

	private String[] aaa = {"2"};
	
	private int[] bbb = {3};
	
	private int[] ccc;
	
	public String[] getAaa() {
		return aaa;
	}
	
	public void setAaa(String[] aaa) {
		this.aaa = aaa;
	}
	
	public int[] getBbb() {
		return bbb;
	}
	
	public void setBbb(int[] bbb) {
		this.bbb = bbb;
	}
	
	public int[] getCcc() {
		return ccc;
	}
	
	public void setCcc(int[] ccc) {
		this.ccc = ccc;
	}
}