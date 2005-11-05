package examples.jsf.dto;

import java.io.Serializable;

public class CheckboxDto implements Serializable {

	private boolean aaa;
	
	public boolean isAaa() {
		return aaa;
	}
	
	public void setAaa(boolean aaa) {
		this.aaa = aaa;
	}
	
	public CheckboxDto() {
	}
}