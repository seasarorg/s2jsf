package org.seasar.jsf.selenium;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.ResourceUtil;

public class TestUtil {

    public static String getProjectRootPath() {
        try {
            File project = null;
            for (File current = getResourceAsFile("."); current != null; current = current
                    .getParentFile()) {
                if (pomExists(current)) {
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

    public static String getProjectPath(String projectPath) {
        try {
            File project = null;
            for (File current = getResourceAsFile("."); current != null; current = current
                    .getParentFile()) {
                if (projectPath.equals(current.getName())) {
                    if (pomExists(current)) {
                        project = current;
                        break;
                    }
                }
                File brother = new File(current, projectPath);
                if (pomExists(brother)) {
                    project = brother;
                    break;
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

    private static boolean pomExists(File f) {
        return new File(f, "pom.xml").exists();
    }

    private static File getResourceAsFile(String s) {
        Thread currentThread = Thread.currentThread();
        ClassLoader cl = currentThread.getContextClassLoader();
        try {
            currentThread.setContextClassLoader(getCallerClass()
                    .getClassLoader());
            return ResourceUtil.getResourceAsFile(s);
        } finally {
            currentThread.setContextClassLoader(cl);
        }
    }

    private static Class getCallerClass() {
        String className = null;
        StackTraceElement[] stackTrace = new Exception().getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            if (stackTrace[i].getClassName().equals(TestUtil.class.getName())) {
                continue;
            }
            className = stackTrace[i].getClassName();
            break;
        }
        Class clazz = ClassUtil.forName(className);
        return clazz;
    }

    public static String getCurrentProjectPath() {
        try {
            File project = null;
            for (File current = getResourceAsFile("."); current != null; current = current
                    .getParentFile()) {
                if (pomExists(current)) {
                    project = current;
                    break;
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

    public static String getProjectPathByClass(Class clazz) {
        URL resource = clazz.getClassLoader().getResource(".");
        File f = ResourceUtil.getFile(resource);
        for (File current = f; current != null; current = current
                .getParentFile()) {
            if (pomExists(current)) {
                try {
                    return current.getCanonicalPath();
                } catch (IOException e) {
                    throw new IORuntimeException(e);
                }
            }
        }
        return null;
    }

}
