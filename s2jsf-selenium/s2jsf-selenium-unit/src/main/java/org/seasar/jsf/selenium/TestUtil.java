package org.seasar.jsf.selenium;

import java.io.File;
import java.io.IOException;

import org.seasar.framework.exception.IORuntimeException;

public class TestUtil {

    public static String getProjectRootPath() {
        try {
            File project = null;
            for (File current = new File(".").getCanonicalFile(); current != null; current = current
                .getParentFile()) {
                File pom = new File(current, "pom.xml");
                if (pom.exists()) {
                    project = current;
                }
            }
            if (project == null) {
                return null;
            }
            return project.getCanonicalPath();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

}
