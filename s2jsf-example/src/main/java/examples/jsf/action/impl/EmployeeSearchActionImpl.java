package examples.jsf.action.impl;

import examples.jsf.action.EmployeeSearchAction;
import examples.jsf.common.Constants;
import examples.jsf.dto.EmployeeSearchDto;
import examples.jsf.dto.ProcessModeDto;
import examples.jsf.exception.BadCriteriaRuntimeException;
import examples.jsf.logic.EmployeeLogic;

public class EmployeeSearchActionImpl implements EmployeeSearchAction {

	private EmployeeLogic employeeLogic;
	
	private EmployeeSearchDto employeeSearchDto;
	
	private ProcessModeDto processModeDto;

	public void setEmployeeSearchDto(EmployeeSearchDto employeeSearchDto) {
		this.employeeSearchDto = employeeSearchDto;
	}
	
	public void setProcessModeDto(ProcessModeDto processModeDto) {
		this.processModeDto = processModeDto;
	}
	
	public void setEmployeeLogic(EmployeeLogic employeeLogic) {
		this.employeeLogic = employeeLogic;
	} 

	public String checkSearchCount() {
		if (employeeLogic.getSearchCount(employeeSearchDto) == 0) {
			throw new BadCriteriaRuntimeException();
		}
		return "employeeList";
	}
	
	public String goEditForCreate() {
		processModeDto.setProcessMode(Constants.CREATE_MODE);
		return "employeeEdit";
	}
}