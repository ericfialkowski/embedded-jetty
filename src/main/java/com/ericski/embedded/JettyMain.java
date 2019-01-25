package com.ericski.embedded;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.ericski.embedded.servlets.EmbeddedResourceHandlerServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class JettyMain
{
    public static void main(String[] args) throws Exception
    {            
        System.getProperties().setProperty("org.slf4j.simpleLogger.defaultLogLevel","debug");
        
        MetricRegistry registry = new MetricRegistry();
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(9090);
        jettyServer.setHandler(context);

        context.addServlet(new ServletHolder(new EmbeddedResourceHandlerServlet(registry)), "/*");
//        context.addServlet(EmbeddedResourceHandlerServlet.class, "/*");

        ServletHolder jerseyServlet = new ServletHolder(ResourceLoader.resourceConfig(registry));
        jerseyServlet.setInitOrder(0);
        context.addServlet(jerseyServlet, "/rest/*");        


        try
        {
            final JmxReporter reporter = JmxReporter.forRegistry(registry).build();
            reporter.start();
            jettyServer.start();
            jettyServer.join();
        }
        finally
        {
            jettyServer.destroy();
        }
    }
}
