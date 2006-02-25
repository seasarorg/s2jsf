/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.jsf.selenium;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.ResourceUtil;

/**
 * @author manhole
 */
public class MavenUtilTest extends TestCase {

    public void testGetSeleniumDriverWar() throws Exception {
        File project = getProjectFile("s2jsf-selenium-unit");
        File pom = new File(project, "pom.xml");
        File seleniumDriverWar = MavenUtil.getArtifactFromPom(pom,
                "s2-jsf-selenium-driver");
        System.out.println(seleniumDriverWar);
        assertNotNull(seleniumDriverWar);
        assertEquals(true, seleniumDriverWar.exists());
        String name = seleniumDriverWar.getName();
        assertEquals(name, true, name.startsWith("s2-jsf-selenium-driver"));
        assertEquals(name, true, name.endsWith(".war"));
    }

    public void no_testResourceUtilSpike() throws Exception {
        File buildDir = ResourceUtil.getBuildDir(getClass());
        File resourceDir = ResourceUtil.getResourceAsFile(".");
        // with Maven2, each path end with "target/test-classes"
        // but with Eclipse, former ends with "target/test-classes"
        // and latter latter ends with "target/classes"
        System.out.println(buildDir);
        System.out.println(resourceDir);
        assertEquals(buildDir, resourceDir);
    }

    private File getProjectFile(String projectName) {
        try {
            File project = null;
            for (File current = ResourceUtil.getResourceAsFile("."); current != null; current = current
                    .getParentFile()) {
                if (projectName.equals(current.getName())) {
                    if (pomExists(current)) {
                        project = current;
                        break;
                    }
                }
                File brother = new File(current, projectName);
                if (pomExists(brother)) {
                    project = brother;
                    break;
                }
            }
            if (project == null) {
                return null;
            }
            return project.getCanonicalFile();
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    private boolean pomExists(File f) {
        return new File(f, "pom.xml").exists();
    }

}
