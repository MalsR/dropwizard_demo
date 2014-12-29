package com.malsr.dropwizarddemo.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Resource
 */
@Path("/content-negotiation")
public class ContentNegotiationResource {

    private static final String CONTENT_TYPE = "application/vnd.contentnegotiation.v1+json";

    /**
     * Endpoint with @Produces annotation.
     */
    @GET
    @Path("/with-produces")
    @Produces(CONTENT_TYPE) //The supported content type of the response body
    public Response getResponseWithProducesAnnotation() {
        return Response.ok("OK").build();
    }

    /**
     * End point without @Produces annotation. Purpose of not having the @Produces annotation is to see how the server
     * will cope with content negotiation on various scenarios.
     */
    @GET
    @Path("/without-produces")
    public Response getResponseWithoutProducesAnnotation() {
        return Response.ok("OK").build();
    }
}
