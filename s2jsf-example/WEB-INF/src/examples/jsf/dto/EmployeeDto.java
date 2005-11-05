package examples.jsf.dto;

import examples.jsf.entity.Employee;

public class EmployeeDto extends Employee {

	private String dname;
	
	public EmployeeDto() {
	}
	
	public String getDname() {
		return dname;
	}
	
	public void setDname(String dname) {
		this.dname = dname;
	}
	
	protected void setupToString(StringBuffer buf) {
		super.setupToString(buf);
		buf.append(", ");
		buf.append(dname);
	}
}
