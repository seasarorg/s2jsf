package examples.jsf.dto;

import java.io.Serializable;

public class SelectOneMenuDto implements Serializable {

	private String aaa = "1";
	
	private Integer bbb;
	
	private Integer ccc;
	
	public String getAaa() {
		return aaa;
	}
	
	public void setAaa(String aaa) {
		this.aaa = aaa;
	}
	
	public Integer getBbb() {
		return bbb;
	}
	
	public void setBbb(Integer bbb) {
		this.bbb = bbb;
	}
	
	public Integer getCcc() {
		return ccc;
	}
	
	public void setCcc(Integer ccc) {
		this.ccc = ccc;
	}
}