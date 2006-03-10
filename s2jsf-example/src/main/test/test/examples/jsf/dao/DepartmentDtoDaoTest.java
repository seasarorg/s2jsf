package test.examples.jsf.dao;

import org.seasar.dao.unit.S2DaoTestCase;

import examples.jsf.dao.DepartmentDtoDao;

/**
 * @author higa
 *
 */
public class DepartmentDtoDaoTest extends S2DaoTestCase {

	private DepartmentDtoDao departmentDtoDao_;

	public DepartmentDtoDaoTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(DepartmentDtoDaoTest.class);
	}
	
	public void setUp() {
		include("examples/jsf/dicon/alldao.dicon");
	}
	
	public void testGetDname() throws Exception {
		assertNotNull("1", departmentDtoDao_.getDname(new Integer(10)));
	}
}