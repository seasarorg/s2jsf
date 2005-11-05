package examples.jsf.action.impl;

import examples.jsf.action.EmployeeConfirmAction;
import examples.jsf.common.Constants;
import examples.jsf.dto.EmployeeDto;
import examples.jsf.dto.ProcessModeDto;
import examples.jsf.logic.EmployeeLogic;

public class EmployeeConfirmActionImpl implements EmployeeConfirmAction {

	private EmployeeLogic employeeLogic;
	
	private ProcessModeDto processModeDto;
	
	private EmployeeDto employeeDto;

	public void setEmployeeLogic(EmployeeLogic employeeLogic) {
		this.employeeLogic = employeeLogic;
	}
	
	public void setProcessModeDto(ProcessModeDto processModeDto) {
		this.processModeDto = processModeDto;
	}
	
	public EmployeeDto getEmployeeDto() {
		return employeeDto;
	}
	
	public void setEmployeeDto(EmployeeDto employeeDto) {
		this.employeeDto = employeeDto;
	}

	public String store() {
		switch (processModeDto.getProcessMode()) {
		case Constants.CREATE_MODE :
			employeeLogic.insert(employeeDto);
			break;
		case Constants.UPDATE_MODE :
			employeeLogic.update(employeeDto);
			break;
		case Constants.DELETE_MODE :
			employeeLogic.delete(employeeDto);
			break;
		}
		return "employeeSearch";
	}
	
	public String goPrevious() {
		switch (processModeDto.getProcessMode()) {
		case Constants.CREATE_MODE :
		case Constants.UPDATE_MODE :
			return "employeeEdit";
		default:
			return "employeeList";
		}
	}
}