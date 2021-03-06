package com.malsr.dropwizarddemo;

import com.malsr.dropwizarddemo.configuration.DropwizardDemoConfiguration;
import com.malsr.dropwizarddemo.healthcheck.DropwizardDemoHealthCheck;
import com.malsr.dropwizarddemo.resources.ContentNegotiationResource;
import com.malsr.dropwizarddemo.resources.DropwizardDemoResource;
import com.malsr.dropwizarddemo.resources.flickr.FlickrResource;
import com.malsr.dropwizarddemo.resources.HystrixDemoResource;
import com.sun.jersey.api.client.Client;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import com.malsr.dropwizarddemo.service.FlickrService;

public class DropwizardDemoApplication extends Application<DropwizardDemoConfiguration> {

    public static void main(String[] args) throws Exception {
        new DropwizardDemoApplication().run(args);
    }

    @Override
    public String getName() {
        return "dropwizard-demo";
    }

    @Override
    public void initialize(Bootstrap<DropwizardDemoConfiguration> helloWorldConfigurationBootstrap) {
        //enables the rendering of FreeMarker & Mustache views
        helloWorldConfigurationBootstrap.addBundle(new ViewBundle());
    }

    @Override
    public void run(DropwizardDemoConfiguration dropwizardDemoConfiguration, Environment environment) throws Exception {
        DropwizardDemoResource dropwizardDemoResource = new DropwizardDemoResource(
                dropwizardDemoConfiguration.getTemplate(), dropwizardDemoConfiguration.getDefaultName());

        DropwizardDemoHealthCheck dropwizardDemoHealthCheck = new DropwizardDemoHealthCheck(
                dropwizardDemoConfiguration.getTemplate());

        Client jerseyClient = new JerseyClientBuilder(environment).using(dropwizardDemoConfiguration.getJerseyClientConfiguration())
                .build(getName());

        FlickrService flickrService = new FlickrService(jerseyClient);
        FlickrResource flickrResource = new FlickrResource(flickrService);

        HystrixDemoResource hystrixDemoResource = new HystrixDemoResource(environment.getObjectMapper());

        ContentNegotiationResource contentNegotiationResource = new ContentNegotiationResource();

        environment.jersey().register(dropwizardDemoResource);
        environment.jersey().register(flickrResource);
        environment.jersey().register(hystrixDemoResource);
        environment.jersey().register(contentNegotiationResource);
        environment.healthChecks().register("template", dropwizardDemoHealthCheck);
    }
}
