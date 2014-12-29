package com.malsr.dropwizarddemo.resources;

import com.malsr.dropwizarddemo.configuration.DropwizardDemoConfiguration;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;

public class ContentNegotiationResourceTest {

    private static final String INCORRECT_CONTENT_TYPE = "application/vnd.resource.v1+json";
    private static final String CONTENT_TYPE = "application/vnd.contentnegotiation.v1+json";

    public static final String WITH_PRODUCES_PATH = "/with-produces";
    public static final String WITHOUT_PRODUCES_PATH = "/without-produces";

    @ClassRule
    public static final DropwizardAppRule<DropwizardDemoConfiguration> RULE =
            new DropwizardAppRule<>(StubbedDropwizardDemoApplication.class, "dropwizard-demo.yml");

    // ********************************************* WITH_PRODUCES_PATH **********************************************

    @Test
    public void returnsNotAcceptableCallingResponseWithProducesAnnotationWithIncorrectAcceptHeader() {
        WebResource resource = Client.create().resource("http://localhost:9090/content-negotiation");

        ClientResponse response = resource.path(WITH_PRODUCES_PATH)
                                          .accept(INCORRECT_CONTENT_TYPE)
                                          .get(ClientResponse.class);

        assertEquals(HttpStatus.SC_NOT_ACCEPTABLE, response.getStatus());
        assertEquals("", response.getEntity(String.class));
    }

    @Test
    public void returnsOKCallingResponseWithProducesAnnotationWithEmptyAcceptHeader() {
        WebResource resource = Client.create().resource("http://localhost:9090/content-negotiation");

        ClientResponse response = resource.path(WITH_PRODUCES_PATH)
                                          .accept("")
                                          .get(ClientResponse.class);

        assertEquals(HttpStatus.SC_OK, response.getStatus());
        assertEquals("OK", response.getEntity(String.class));
    }

    @Test
    public void returnsOKCallingResponseWithProducesAnnotationWithNoAcceptHeader() {
        WebResource resource = Client.create().resource("http://localhost:9090/content-negotiation");

        ClientResponse response = resource.path(WITH_PRODUCES_PATH)
                                          .get(ClientResponse.class);

        assertEquals(HttpStatus.SC_OK, response.getStatus());
        assertEquals("OK", response.getEntity(String.class));
    }

    @Test
    public void returnsOKCallingResponseWithProducesAnnotationWithCorrectAcceptHeader() {
        WebResource resource = Client.create().resource("http://localhost:9090/content-negotiation");

        ClientResponse response = resource.path(WITH_PRODUCES_PATH)
                                          .accept(CONTENT_TYPE)
                                          .get(ClientResponse.class);

        assertEquals(HttpStatus.SC_OK, response.getStatus());
        assertEquals("OK", response.getEntity(String.class));
    }

    @Test
    public void returnsCorrectContentTypeOnResponseWithProducesAnnotation() {
        WebResource resource = Client.create().resource("http://localhost:9090/content-negotiation");

        ClientResponse response = resource.path(WITH_PRODUCES_PATH)
                .accept(CONTENT_TYPE)
                .get(ClientResponse.class);

        assertEquals(CONTENT_TYPE, response.getHeaders().getFirst("Content-Type"));
    }

    @Test
    public void returnsOKOnResponseWithProducesAnnotationWithMultipleAcceptHeaders() {
        /*
        Sending multiple accept headers but with the following order. This is telling the server that
        1 - When contacting the following end point I want the response to be in the "INCORRECT_CONTENT_TYPE" format, by
            default the acceptable quality level is 1.0 (no quality value specified e.g. q=x.x)
        2 - Failing which I then want the response to be in MediaType.APPLICATION_JSON_TYPE format
        3 - Else whatever format you have I want that in the response
         */
        WebResource resource = Client.create().resource("http://localhost:9090/content-negotiation");

        ClientResponse response = resource.path(WITH_PRODUCES_PATH)
                                          .accept(INCORRECT_CONTENT_TYPE)
                                          .accept(MediaType.APPLICATION_JSON_TYPE + ";q=0.9")
                                          .accept("*/*;q=0.8")
                                          .get(ClientResponse.class);

        assertEquals(HttpStatus.SC_OK, response.getStatus());
        assertEquals("OK", response.getEntity(String.class));
    }

    @Test
    public void returnsOKOnResponseWithProducesAnnotationWithContentTypeHeader() {
        WebResource resource = Client.create().resource("http://localhost:9090/content-negotiation");

        ClientResponse response = resource.path(WITH_PRODUCES_PATH)
                .accept(CONTENT_TYPE)
                .header("Content-Type", CONTENT_TYPE)
                .get(ClientResponse.class);

        assertEquals(HttpStatus.SC_OK, response.getStatus());
        assertEquals("OK", response.getEntity(String.class));
    }

    // ********************************************* WITHOUT_PRODUCES_PATH ********************************************

    @Test
    public void returnsExpectedContentTypeOnResponseWithoutProducesAnnotation() {
        String expectedAcceptHeader = "application/whatever.json";
        WebResource resource = Client.create().resource("http://localhost:9090/content-negotiation");

        ClientResponse response = resource.path(WITHOUT_PRODUCES_PATH)
                                          .accept(expectedAcceptHeader)
                                          .get(ClientResponse.class);

        assertEquals(expectedAcceptHeader, response.getHeaders().getFirst("Content-Type"));
        assertEquals(HttpStatus.SC_OK, response.getStatus());
    }

    @Test
    public void returnsDefaultContentTypeWhenNoAcceptHeaderSpecified() {
        WebResource resource = Client.create().resource("http://localhost:9090/content-negotiation");

        ClientResponse response = resource.path(WITHOUT_PRODUCES_PATH)
                                          .get(ClientResponse.class);

        assertEquals(MediaType.TEXT_HTML, response.getHeaders().getFirst("Content-Type"));
    }

    @Test
    public void returns200OnResponseWithoutProducesAnnotationWithNoAcceptHeader() {
        WebResource resource = Client.create().resource("http://localhost:9090/content-negotiation");

        ClientResponse response = resource.path(WITHOUT_PRODUCES_PATH)
                                          .get(ClientResponse.class);

        assertEquals(HttpStatus.SC_OK, response.getStatus());
    }

    @Test
    public void returns200OnResponseWithoutProducesAnnotationWithContentTypeHeader() {
        WebResource resource = Client.create().resource("http://localhost:9090/content-negotiation");

        ClientResponse response = resource.path(WITHOUT_PRODUCES_PATH)
                                          .header(HttpHeaders.CONTENT_TYPE, "application/somecontenttype.json")
                                          .get(ClientResponse.class);

        assertEquals(HttpStatus.SC_OK, response.getStatus());
        assertEquals(MediaType.TEXT_HTML, response.getHeaders().getFirst("Content-Type"));
    }
}