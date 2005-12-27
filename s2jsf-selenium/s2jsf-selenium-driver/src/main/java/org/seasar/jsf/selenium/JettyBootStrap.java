package org.seasar.jsf.selenium;

import java.io.File;
import java.io.IOException;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.WebApplicationContext;
import org.mortbay.util.InetAddrPort;
import org.mortbay.util.MultiException;

public class JettyBootStrap {

    public static void main(String[] args) throws IOException, MultiException {
        new JettyBootStrap().startJetty();
    }

    private void startJetty() throws IOException, MultiException {
        Server server = new Server();
        server.addListener(new InetAddrPort("localhost", 8080));
        {
            File seleniumWebApp = new File(TestUtil.getProjectRootPath(),
                "selenium-driver-0.6.war");
            WebApplicationContext context = new WebApplicationContext(
                seleniumWebApp.getCanonicalPath());
            context.setContextPath("/selenium-driver");
            server.addContext(context);
        }
        {
            File akabanaWebApp = new File(TestUtil.getProjectRootPath()
                + "/akabana-webapp/target/akabana/");
            WebApplicationContext context = new WebApplicationContext(
                akabanaWebApp.getCanonicalPath());
            context.setContextPath("/");
            server.addContext(context);
        }
        server.start();
    }

}
