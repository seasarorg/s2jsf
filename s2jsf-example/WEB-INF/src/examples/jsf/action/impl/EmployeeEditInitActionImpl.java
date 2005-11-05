package examples.jsf.action.impl;

import java.util.List;

import examples.jsf.action.EmployeeEditInitAction;
import examples.jsf.common.Constants;
import examples.jsf.dto.EmployeeDto;
import examples.jsf.dto.ProcessModeDto;
import examples.jsf.logic.EmployeeLogic;

public class EmployeeEditInitActionImpl implements EmployeeEditInitAction {

	private EmployeeLogic employeeLogic;

	private Integer empno;
	
	private ProcessModeDto processModeDto;
	
	private EmployeeDto employeeDto;
	
	private List departmentDtoList;

	public void setEmployeeLogic(EmployeeLogic employeeLogic) {
		this.employeeLogic = employeeLogic;
	}
	
	public void setEmpno(Integer empno) {
		this.empno = empno;
	}
	
	public void setProcessModeDto(ProcessModeDto processModeDto) {
		this.processModeDto = processModeDto;
	}
	
	public EmployeeDto getEmployeeDto() {
		return employeeDto;
	}
	
	public List getDepartmentDtoList() {
		return departmentDtoList;
	}

	public String initialize() {
		departmentDtoList = employeeLogic.getAllDepartments();
		if (processModeDto.getProcessMode() == Constants.UPDATE_MODE) {
			employeeDto = employeeLogic.getEmployeeDto(empno);
		}
		return null;
	}
	
}