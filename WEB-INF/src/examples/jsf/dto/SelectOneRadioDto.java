package examples.jsf.dto;

import java.io.Serializable;

public class SelectOneRadioDto implements Serializable {

	private String aaa = "1";
	
	private String bbb;
	
	private Integer ccc;
	
	public String getAaa() {
		return aaa;
	}
	
	public void setAaa(String aaa) {
		this.aaa = aaa;
	}
	
	public String getBbb() {
		return bbb;
	}
	
	public void setBbb(String bbb) {
		this.bbb = bbb;
	}
	
	public Integer getCcc() {
		return ccc;
	}
	
	public void setCcc(Integer ccc) {
		this.ccc = ccc;
	}
}