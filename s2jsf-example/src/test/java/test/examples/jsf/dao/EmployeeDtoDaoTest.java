package test.examples.jsf.dao;

import org.seasar.dao.unit.S2DaoTestCase;

import examples.jsf.dao.EmployeeDtoDao;
import examples.jsf.dto.EmployeeSearchDto;

/**
 * @author higa
 *
 */
public class EmployeeDtoDaoTest extends S2DaoTestCase {

    private EmployeeDtoDao employeeDtoDao_;

    public void setUp() {
        include("examples/jsf/dicon/alldao.dicon");
    }

    public void testGetSearchCount() throws Exception {
        EmployeeSearchDto searchDto = new EmployeeSearchDto();
        searchDto.setEmpno(new Integer(7788));
        assertEquals("1", 1, employeeDtoDao_.getSearchCount(searchDto));
    }

    public void testSearchEmployeeDtoList() throws Exception {
        EmployeeSearchDto searchDto = new EmployeeSearchDto();
        searchDto.setEmpno(new Integer(7788));
        assertNotNull("1", employeeDtoDao_.searchEmployeeDtoList(searchDto));
    }

    public void testGetEmployeeDto() throws Exception {
        assertNotNull("1", employeeDtoDao_.getEmployeeDto(new Integer(7788)));
    }

}
