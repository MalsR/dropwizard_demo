package com.malsr.dropwizarddemo.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Resource
 */
@Path("/content-negotiation")
public class ContentNegotiationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentNegotiationResource.class);

    private static final String CONTENT_TYPE = "application/vnd.contentnegotiation.v1+json";

    /**
     * Endpoint with @Produces annotation.
     */
    @GET
    @Path("/opened-version")
    @Produces(CONTENT_TYPE) //The supported content type of the response body
    public Response getResponseWithSpecificContentType() {
        LOGGER.info("called getResponseWithSpecificContentType");
        return Response.ok("OK").build();
    }

    /**
     * End point without @Produces annotation. Purpose of not having the @Produces annotation is to see how the server
     * will cope with content negotiation on various scenarios.
     */
    @GET
    @Path("/opened-version")
    public Response getResponseWithDefaultContentType() {
        LOGGER.info("called getResponseWithDefaultContentType");
        return Response.ok("DEFAULT").build();
    }

    @GET
    @Path("/example-get-v1")
    @Produces("application/vnd.version1.v1+json")
    public Response getVersion1() {
        LOGGER.info("called getVersion1");

        return Response.ok("Version 1").build();
    }
}
