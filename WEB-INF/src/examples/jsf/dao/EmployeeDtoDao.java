package examples.jsf.dao;

import java.util.List;

import examples.jsf.dto.EmployeeDto;
import examples.jsf.dto.EmployeeSearchDto;

public interface EmployeeDtoDao {

	public Class BEAN = EmployeeDto.class;

	public String searchEmployeeDtoList_ARGS = "dto";

	public List searchEmployeeDtoList(EmployeeSearchDto dto);
	
	public String getSearchCount_ARGS = "dto";

	public int getSearchCount(EmployeeSearchDto dto);
	
	public String getEmployeeDto_ARGS = "empno";
	
	public EmployeeDto getEmployeeDto(Integer empno);
	
	public void insert(EmployeeDto dto);
	
	public void update(EmployeeDto dto);
	
	public void delete(EmployeeDto dto);
}
