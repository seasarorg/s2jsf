package examples.jsf.dto;

import java.io.Serializable;

public class ForEach3Dto implements Serializable {
	
	private String value;
	
	public ForEach3Dto(String value) {
		setValue(value);
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
