package com.malsr.dropwizarddemo.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malsr.dropwizarddemo.configuration.DropwizardDemoConfiguration;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FlickrResourceIntegrationTest {

    @ClassRule
    public static final DropwizardAppRule<DropwizardDemoConfiguration> RULE =
            new DropwizardAppRule<>(StubbedDropwizardDemoApplication.class, "dropwizard-demo.yml");

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void returnsOKForUserInfo() {
        WebResource resource = Client.create().resource("http://localhost:9090/flickr");

        ClientResponse response = resource.get(ClientResponse.class);

        assertEquals(HttpStatus.SC_OK, response.getStatus());
    }

    @Test
    public void returnsCorrectResponseBodyForUserInfo() throws JsonProcessingException {
        ExpectedResponseBody expectedResponseBody = new ExpectedResponseBody("mals_r", "https://www.flickr.com/photos/mals_r/");
        String expectedResponse = objectMapper.writeValueAsString(expectedResponseBody);
        WebResource resource = Client.create().resource("http://localhost:9090/flickr");

        ClientResponse response = resource.get(ClientResponse.class);

        assertEquals(expectedResponse, response.getEntity(String.class));
    }

    private class ExpectedResponseBody {
        @JsonProperty
        private Flickr _flickr;

        private ExpectedResponseBody(final String userName, final String url) {
            this._flickr = new Flickr(userName, url);
        }

        private class Flickr {
            @JsonProperty
            private User _user;

            private Flickr(final String userName, final String url) {
                this._user = new User(userName, url);
            }

            public User get_user() {
                return _user;
            }

            private class User {
                @JsonProperty
                private String userName;
                @JsonProperty
                private String _url;

                private User(String userName, String _url) {
                    this.userName = userName;
                    this._url = _url;
                }

                public String getUserName() {
                    return userName;
                }

                public String get_url() {
                    return _url;
                }
            }
        }
    }

}