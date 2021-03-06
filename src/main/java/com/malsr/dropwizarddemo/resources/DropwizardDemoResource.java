package com.malsr.dropwizarddemo.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.malsr.dropwizarddemo.api.DemoRepresentation;
import com.malsr.dropwizarddemo.views.DemoFreeMarkerView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/dropwizard-demo")
@Produces(MediaType.APPLICATION_JSON)
public class DropwizardDemoResource {

    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public DropwizardDemoResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public DemoRepresentation pingDemo(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.or(defaultName));
        return new DemoRepresentation(counter.incrementAndGet(), value);
    }

    @GET
    @Timed
    @Path("/freemarker")
    @Produces(MediaType.TEXT_HTML)
    public DemoFreeMarkerView demoTest() {
        return new DemoFreeMarkerView();
    }
}
