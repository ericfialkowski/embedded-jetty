package com.ericski.embedded;

import com.ericski.embedded.servlets.EmbeddedResourceHandlerServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class JettyMain
{
    public static void main(String[] args) throws Exception
    {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(9090);
        jettyServer.setHandler(context);

        context.addServlet(EmbeddedResourceHandlerServlet.class, "/*");
        
        ServletHolder jerseyServlet = new ServletHolder(ResourceLoader.resourceConfig());
        jerseyServlet.setInitOrder(0);
        context.addServlet(jerseyServlet, "/rest/*");        

        try
        {
            jettyServer.start();
            jettyServer.join();
        }
        finally
        {
            jettyServer.destroy();
        }
    }
}
