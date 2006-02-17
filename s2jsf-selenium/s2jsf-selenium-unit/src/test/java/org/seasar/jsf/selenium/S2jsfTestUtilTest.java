package org.seasar.jsf.selenium;

import java.io.File;

import junit.framework.TestCase;

public class S2jsfTestUtilTest extends TestCase {

    public void testGetProjectRootPath() throws Exception {
        String path = TestUtil.getProjectRootPath();
        System.out.println(path);
        assertEquals(true, path.endsWith(File.separatorChar + "s2jsf-selenium"));
    }

}
