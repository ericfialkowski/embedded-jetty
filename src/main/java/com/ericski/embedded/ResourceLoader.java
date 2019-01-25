package com.ericski.embedded;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jersey2.InstrumentedResourceMethodApplicationListener;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class ResourceLoader extends ResourceConfig
{
    public ResourceLoader(MetricRegistry registry)
    {
        register(new InstrumentedResourceMethodApplicationListener(registry));
        packages("com.ericski.embedded.resources");
    }

    public static ServletContainer resourceConfig(MetricRegistry registry)
    {
        return new ServletContainer(new ResourceConfig(new ResourceLoader(registry).getClasses()));
    }
}
