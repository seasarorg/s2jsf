package examples.jsf.entity;

import java.io.Serializable;

public class Department implements Serializable {
	
	public static final String TABLE = "DEPT";

    private int deptno;

    private String dname;

    private String loc;
    
    private int versionNo;

    public Department() {
    }

    public int getDeptno() {
        return this.deptno;
    }

    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }

    public java.lang.String getDname() {
        return this.dname;
    }

    public void setDname(java.lang.String dname) {
        this.dname = dname;
    }

    public java.lang.String getLoc() {
        return this.loc;
    }

    public void setLoc(java.lang.String loc) {
        this.loc = loc;
    }
    
    public int getVersionNo() {
        return this.versionNo;
    }

    public void setVersionNo(int versionNo) {
        this.versionNo = versionNo;
    }

    public boolean equals(Object other) {
        if ( !(other instanceof Department) ) return false;
        Department castOther = (Department) other;
        return this.getDeptno() == castOther.getDeptno();
    }
    
    public String toString() {
    	StringBuffer buf = new StringBuffer("[");
    	buf.append(deptno).append(", ");
		buf.append(dname).append(", ");
		buf.append(loc).append(", ");
		buf.append(versionNo).append("]");
    	return buf.toString();
    }

    public int hashCode() {
        return this.getDeptno();
    }
}
