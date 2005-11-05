package examples.jsf.action.impl;

import java.util.List;

import examples.jsf.action.EmployeeSearchInitAction;
import examples.jsf.logic.EmployeeLogic;

public class EmployeeSearchInitActionImpl implements EmployeeSearchInitAction {

	private EmployeeLogic employeeLogic;

	private List departmentDtoList;

	public void setEmployeeLogic(EmployeeLogic employeeLogic) {
		this.employeeLogic = employeeLogic;
	}
	
	public List getDepartmentDtoList() {
		return departmentDtoList;
	}

	public String initialize() {
		departmentDtoList = employeeLogic.getAllDepartments();
		return null;
	}
	
}