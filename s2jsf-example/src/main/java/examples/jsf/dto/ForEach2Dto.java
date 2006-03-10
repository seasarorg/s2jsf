package examples.jsf.dto;

import java.io.Serializable;

public class ForEach2Dto implements Serializable {

	private boolean delete;
	
	private String input;
	
	public boolean isDelete() {
		return delete;
	}
	
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	
	public String getInput() {
		return input;
	}
	
	public void setInput(String input) {
		this.input = input;
	}
}
