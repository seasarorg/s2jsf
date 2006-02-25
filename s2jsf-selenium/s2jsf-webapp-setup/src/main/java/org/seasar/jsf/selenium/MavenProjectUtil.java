package org.seasar.jsf.selenium;

import java.io.File;

import org.seasar.framework.util.ResourceUtil;

public class MavenProjectUtil {

    public static File getProjectPomFile(final Class clazz) {
        File file = ResourceUtil.getBuildDir(clazz);
        for (File f = file; f != null; f = f.getParentFile()) {
            File pomFile = new File(f, "pom.xml");
            if (pomFile.exists()) {
                return pomFile;
            }
        }
        return null;
    }

}
