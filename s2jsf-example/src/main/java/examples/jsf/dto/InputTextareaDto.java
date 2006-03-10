package examples.jsf.dto;

import java.io.Serializable;

public class InputTextareaDto implements Serializable {

	private String aaa;
    
    private int age = 1234;
	
	public String getAaa() {
		return aaa;
	}
	
	public void setAaa(String aaa) {
		this.aaa = aaa;
	}

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}