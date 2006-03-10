package examples.jsf.action.impl;

import java.util.List;

import examples.jsf.action.EmployeeListInitAction;
import examples.jsf.dto.EmployeeSearchDto;
import examples.jsf.logic.EmployeeLogic;

public class EmployeeListInitActionImpl implements EmployeeListInitAction {

	private EmployeeSearchDto employeeSearchDto;

	private EmployeeLogic employeeLogic;

	private List employeeDtoList;

	public void setEmployeeSearchDto(EmployeeSearchDto employeeSearchDto) {
		this.employeeSearchDto = employeeSearchDto;
	}

	public void setEmployeeLogic(EmployeeLogic employeeLogic) {
		this.employeeLogic = employeeLogic;
	} 

	public List getEmployeeDtoList() {
		return employeeDtoList;
	}

	public String initialize() {
		employeeDtoList = employeeLogic.searchEmployeeDtoList(employeeSearchDto);
		return null;
	}
}