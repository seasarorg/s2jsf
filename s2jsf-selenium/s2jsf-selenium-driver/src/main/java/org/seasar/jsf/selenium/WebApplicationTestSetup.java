package org.seasar.jsf.selenium;

import java.io.File;
import java.util.Arrays;
import java.util.Properties;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.maven.BuildFailureException;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.cli.ConsoleDownloadMonitor;
import org.apache.maven.embedder.MavenEmbedder;
import org.apache.maven.embedder.MavenEmbedderConsoleLogger;
import org.apache.maven.embedder.MavenEmbedderException;
import org.apache.maven.embedder.PlexusLoggerAdapter;
import org.apache.maven.lifecycle.LifecycleExecutionException;
import org.apache.maven.monitor.event.DefaultEventMonitor;
import org.apache.maven.monitor.event.EventMonitor;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuildingException;
import org.codehaus.plexus.util.dag.CycleDetectedException;
import org.seasar.framework.util.ResourceUtil;

public class WebApplicationTestSetup extends TestSetup {

    private Class _testClass;

    public WebApplicationTestSetup(Test test) {
        super(test);
    }

    public WebApplicationTestSetup(Class testClass) {
        this(new TestSuite(testClass));
        _testClass = testClass;
    }

    protected void setUp() throws Exception {
        super.setUp();
        buildWebapp();
    }

    protected void buildWebapp() throws MavenEmbedderException,
        ProjectBuildingException, ArtifactResolutionException,
        ArtifactNotFoundException, CycleDetectedException,
        LifecycleExecutionException, BuildFailureException {

        if (_testClass == null) {
            NullPointerException npe = new NullPointerException(
                "_testClass is null");
            npe.printStackTrace();
            throw npe;
        }
        MavenEmbedder maven = new MavenEmbedder();
        ClassLoader classLoader = Thread.currentThread()
            .getContextClassLoader();
        maven.setClassLoader(classLoader);
        maven.setLogger(new MavenEmbedderConsoleLogger());
        maven.start();

        final File pomFile = getProjectPomFile();
        System.out.println("pomFile:" + pomFile);
        final File projectDirectory = pomFile.getParentFile();
        System.out.println("projectDirectory:" + projectDirectory);

        MavenProject mavenProject = maven.readProjectWithDependencies(pomFile);
        EventMonitor eventMonitor = new DefaultEventMonitor(
            new PlexusLoggerAdapter(new MavenEmbedderConsoleLogger()));

        Properties prop = new Properties();
        prop.put("maven.test.skip", "true");

        maven.execute(mavenProject, Arrays
            .asList(new String[] { "resources:resources", "compiler:compile",
                "resources:testResources", "compiler:testCompile",
                "war:exploded" }), eventMonitor, new ConsoleDownloadMonitor(),
            prop, projectDirectory);
    }

    protected File getProjectPomFile() {
        File file = ResourceUtil.getFile(_testClass.getResource("."));
        for (File f = file; f != null; f = f.getParentFile()) {
            File pomFile = new File(f, "pom.xml");
            if (pomFile.exists()) {
                return pomFile;
            }
        }
        return null;
    }

    public void setTestClass(Class testClass) {
        _testClass = testClass;
    }

}
