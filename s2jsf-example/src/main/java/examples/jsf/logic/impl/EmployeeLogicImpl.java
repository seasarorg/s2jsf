package examples.jsf.logic.impl;

import java.util.List;

import examples.jsf.dao.DepartmentDtoDao;
import examples.jsf.dao.EmployeeDtoDao;
import examples.jsf.dto.EmployeeDto;
import examples.jsf.dto.EmployeeSearchDto;
import examples.jsf.logic.EmployeeLogic;

public class EmployeeLogicImpl implements EmployeeLogic {

	private EmployeeDtoDao employeeDtoDao;
	
	private DepartmentDtoDao departmentDtoDao;

	public void setEmployeeDtoDao(EmployeeDtoDao employeeDtoDao) {
		this.employeeDtoDao = employeeDtoDao;
	}
	
	public void setDepartmentDtoDao(DepartmentDtoDao departmentDtoDao) {
		this.departmentDtoDao = departmentDtoDao;
	}
	
	public int getSearchCount(EmployeeSearchDto dto) {
		return employeeDtoDao.getSearchCount(dto);
	}
	
	public List searchEmployeeDtoList(EmployeeSearchDto dto) {
		return employeeDtoDao.searchEmployeeDtoList(dto);
	}
	
	public EmployeeDto getEmployeeDto(Integer empno) {
		return employeeDtoDao.getEmployeeDto(empno);
	}
	
	public List getAllDepartments() {
		return departmentDtoDao.getAllDepartments();
	}
	
	public String getDname(Integer deptno) {
		return departmentDtoDao.getDname(deptno);
	}
	
	public void insert(EmployeeDto dto) {
		employeeDtoDao.insert(dto);
	}
	
	public void update(EmployeeDto dto) {
		employeeDtoDao.update(dto);
	}
	
	public void delete(EmployeeDto dto) {
		employeeDtoDao.delete(dto);
	}
	
	public boolean existEmployee(Integer empno) {
		return employeeDtoDao.getEmployeeDto(empno) != null;
	}
}
