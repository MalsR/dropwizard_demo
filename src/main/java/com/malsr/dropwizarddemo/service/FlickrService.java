package com.malsr.dropwizarddemo.service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;

public class FlickrService {

    private static final Logger LOG = LoggerFactory.getLogger(FlickrService.class);

    private Client jerseyClient;

    public FlickrService(Client jerseyClient) {
        this.jerseyClient = jerseyClient;
    }

    public String getUserInfo() {
        WebResource webResource = jerseyClient
                .resource("https://www.flickr.com/photos/mals_r/");

        ClientResponse clientResponse = webResource
                .accept(MediaType.TEXT_HTML)
                .get(ClientResponse.class);

        LOG.debug("Calling flickr endpoint and received response {}", clientResponse.getHeaders());

        return clientResponse.toString();
    }
}

