package org.seasar.jsf.selenium;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.artifact.resolver.filter.ScopeArtifactFilter;
import org.apache.maven.cli.ConsoleDownloadMonitor;
import org.apache.maven.embedder.MavenEmbedder;
import org.apache.maven.embedder.MavenEmbedderConsoleLogger;
import org.apache.maven.embedder.PlexusLoggerAdapter;
import org.apache.maven.monitor.event.DefaultEventMonitor;
import org.apache.maven.monitor.event.EventMonitor;
import org.apache.maven.project.MavenProject;

public class MavenEmmedderSpikeTest extends TestCase {

    private static final String LF = System.getProperty("line.separator");

    public void testValidate() throws Exception {
        MavenEmbedder maven = new MavenEmbedder();
        ClassLoader classLoader = Thread.currentThread()
            .getContextClassLoader();
        maven.setClassLoader(classLoader);
        maven.setLogger(new MavenEmbedderConsoleLogger());
        maven.start();

        String userDir = System.getProperty("user.dir");
        System.out.println(userDir);
        File targetDirectory = new File(userDir, "");
        File pomFile = new File(targetDirectory, "pom.xml");

        MavenProject pom = maven.readProjectWithDependencies(pomFile);

        ArtifactFilter artifactFilter = new ScopeArtifactFilter(
            Artifact.SCOPE_RUNTIME);
        List libs = new ArrayList();
        for (Iterator it = pom.getArtifacts().iterator(); it.hasNext();) {
            Artifact artifact = (Artifact) it.next();
            if (!artifact.isOptional() && artifactFilter.include(artifact)
                && "jar".equals(artifact.getType())) {
                System.out.println("�� " + artifact);
                libs.add(artifact);
            } else {
                System.out.println("�~ " + artifact);
            }
            List dependencyTrail = artifact.getDependencyTrail();
            Collections.reverse(dependencyTrail);
            StringBuffer sb = new StringBuffer();
            int count = 0;
            for (Iterator itTrail = dependencyTrail.iterator(); itTrail
                .hasNext(); count++) {
                if (count != 0) {
                    for (int i = count; i > 1; i--) {
                        sb.append("  ");
                    }
                    sb.append(" + ");
                }
                String trail = (String) itTrail.next();
                sb.append(trail);
                sb.append(LF);
            }
            System.out.print(sb);
        }

        for (Iterator it = libs.iterator(); it.hasNext();) {
            Artifact artifact = (Artifact) it.next();
            System.out.println(artifact.getFile());
        }
        //
        //        EventMonitor eventMonitor = new DefaultEventMonitor(
        //            new PlexusLoggerAdapter(new MavenEmbedderConsoleLogger()));
        //
        //        Properties prop = new Properties();
        //        prop.put("maven.test.skip", "true");
        //        maven.execute(pom, Arrays.asList(new String[] { "validate",
        //            "war:exploded" }), eventMonitor, new ConsoleDownloadMonitor(),
        //            prop, targetDirectory);

    }

    public void testWhereIsClass() throws Exception {
        // ## Arrange ##
        System.out.println(getClass().getResource("").getFile());
        System.out.println(getClass().getClassLoader().getResource("")
            .getFile());
        final String file = Thread.currentThread().getContextClassLoader()
            .getResource("").getFile();
        System.out.println(file);
        final File project = new File(file).getParentFile().getParentFile();
        System.out.println(project.getCanonicalPath());
        File pom = new File(project, "pom.xml");
        System.out.println(pom);

        // ## Act ##

        // ## Assert ##

    }

}
