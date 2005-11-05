package examples.jsf.action.impl;

import examples.jsf.action.EmployeeConfirmInitAction;
import examples.jsf.dto.EmployeeDto;
import examples.jsf.logic.EmployeeLogic;

public class EmployeeConfirmInitActionImpl implements EmployeeConfirmInitAction {

	private EmployeeLogic employeeLogic;
	
	private EmployeeDto employeeDto;

	public void setEmployeeLogic(EmployeeLogic employeeLogic) {
		this.employeeLogic = employeeLogic;
	}
	
	public void setEmployeeDto(EmployeeDto employeeDto) {
		this.employeeDto = employeeDto;
	}

	public String initialize() {
		if (employeeDto.getDeptno() != null) {
			String dname = employeeLogic.getDname(employeeDto.getDeptno());
			employeeDto.setDname(dname);
		}
		return null;
	}
	
}