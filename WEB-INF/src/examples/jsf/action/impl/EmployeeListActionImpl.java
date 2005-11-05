package examples.jsf.action.impl;

import examples.jsf.action.EmployeeListAction;
import examples.jsf.common.Constants;
import examples.jsf.dto.EmployeeDto;
import examples.jsf.dto.ProcessModeDto;
import examples.jsf.logic.EmployeeLogic;

public class EmployeeListActionImpl implements EmployeeListAction {
	
	private int processMode;

	private ProcessModeDto processModeDto;
	
	private Integer empno;
	
	private EmployeeLogic employeeLogic;
	
	private EmployeeDto employeeDto;
	
	public void setProcessMode(int processMode) {
		this.processMode = processMode;
	}
	
	public void setProcessModeDto(ProcessModeDto processModeDto) {
		this.processModeDto = processModeDto;
	}
	
	public void setEmpno(Integer empno) {
		this.empno = empno;
	}

	public void setEmployeeLogic(EmployeeLogic employeeLogic) {
		this.employeeLogic = employeeLogic;
	}

	public EmployeeDto getEmployeeDto() {
		return employeeDto;
	}
	
	public String goNext() {
		processModeDto.setProcessMode(processMode);
		switch (processModeDto.getProcessMode()) {
		case Constants.UPDATE_MODE :
			return "employeeEdit";
		default :
			employeeDto = employeeLogic.getEmployeeDto(empno);
			return "employeeConfirm";
		}
	}
	
}