package com.malsr.dropwizarddemo.resources;

import com.codahale.metrics.annotation.Timed;
import com.malsr.dropwizarddemo.service.FlickrService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/flickr")
@Produces(MediaType.APPLICATION_JSON)
public class FlickrResource {

    private FlickrService flickrService;

    public FlickrResource(FlickrService flickrService) {
        this.flickrService = flickrService;
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForUser() {
        return flickrService.getUserInfo();
    }
}
