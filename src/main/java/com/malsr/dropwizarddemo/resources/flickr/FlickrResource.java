package com.malsr.dropwizarddemo.resources.flickr;

import com.codahale.metrics.annotation.Timed;
import com.malsr.dropwizarddemo.service.FlickrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/flickr")
@Produces(MediaType.APPLICATION_JSON)
public class FlickrResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(FlickrResource.class);

    private FlickrService flickrService;

    public FlickrResource(FlickrService flickrService) {
        this.flickrService = flickrService;
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public FlickrUserResponse getInfoForUser(@HeaderParam("X-USER-NAME") String userName) {
        LOGGER.debug("Received header value: {}", userName);
        String url = "https://www.flickr.com/photos/mals_r/";
        FlickrUserResponse response = new FlickrUserResponse(userName, url);

        LOGGER.debug("Sending Response: {}", response);
        return response;
    }
}
