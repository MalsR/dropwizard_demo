package service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;

public class FlickrService {

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

        return clientResponse.toString();
    }
}

