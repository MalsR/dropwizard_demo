package com.malsr.dropwizarddemo.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.malsr.dropwizarddemo.service.HystrixDemoExecutor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;

@Path("/hystrix")
public class HystrixDemoResource {

    private static Logger LOG = LoggerFactory.getLogger(HystrixDemoResource.class);

    private final ObjectMapper objectMapper;

    public HystrixDemoResource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @GET
    @Timed
    public String testSuccess() throws JsonProcessingException {
        String demoResponse = objectMapper.writeValueAsString(Response.ok().build());

        HystrixDemoExecutor hystrixDemoExecutor = new HystrixDemoExecutor("Demo Hystrix");
        try {
            LOG.info("--Executing hystrix demo executor---");
            hystrixDemoExecutor.queue().get();
        } catch (InterruptedException e) {
            LOG.error("Got InterruptedException");
            e.printStackTrace();
        } catch (ExecutionException e) {
            LOG.error("Got ExecutionException");
            e.printStackTrace();
        }

        return demoResponse;
    }
}
