package examples.jsf.exception;

public class EmployeeAlreadyExistRuntimeException extends AppRuntimeException {

	private int empno;

	public EmployeeAlreadyExistRuntimeException(int empno) {
		super("examples.jsf.EmployeeAlreadyExist", new Object[]{String.valueOf(empno)});
		this.empno = empno;
	}
	
	public int getEmpno() {
		return empno;
	}
}