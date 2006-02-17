package org.seasar.jsf.selenium;

import java.io.File;

import junit.framework.TestCase;

public class TestUtilTest extends TestCase {

    public void testGetProjectRootPath() throws Exception {
        String path = TestUtil.getProjectRootPath();
        System.out.println(path);
        assertEquals(true, path.endsWith(File.separatorChar + "s2jsf-selenium")
            || path.endsWith(File.separatorChar + "s2jsf-selenium-unit"));
    }

    public void testGetProjectPath() throws Exception {
        String path = TestUtil.getProjectPath("s2jsf-selenium-unit");
        System.out.println(path);
        assertEquals(true, path.endsWith(File.separatorChar
            + "s2jsf-selenium-unit"));
    }

    public void testGetCurrentProjectPath() throws Exception {
        String path = TestUtil.getCurrentProjectPath();
        System.out.println(path);
        assertEquals(true, path.endsWith(File.separatorChar
            + "s2jsf-selenium-unit"));
    }

    public void testGetProjectPathByClass() throws Exception {
        String path = TestUtil.getProjectPathByClass(TestUtilTest.class);
        System.out.println(path);
        assertEquals(true, path.endsWith(File.separatorChar
            + "s2jsf-selenium-unit"));
    }

}
