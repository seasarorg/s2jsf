package examples.jsf.dao;

import java.util.List;

import examples.jsf.dto.DepartmentDto;

public interface DepartmentDtoDao {

	public Class BEAN = DepartmentDto.class;

	public List getAllDepartments();
	
	public String getDname_ARGS = "deptno";

	public String getDname(Integer deptno);
}
