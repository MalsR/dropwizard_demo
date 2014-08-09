package com.malsr.dropwizarddemo;

import com.malsr.dropwizarddemo.configuration.DropwizardDemoConfiguration;
import com.malsr.dropwizarddemo.healthcheck.DropwizardDemoHealthCheck;
import com.malsr.dropwizarddemo.resources.DropwizardDemoResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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

    }

    @Override
    public void run(DropwizardDemoConfiguration dropwizardDemoConfiguration, Environment environment) throws Exception {
        DropwizardDemoResource dropwizardDemoResource = new DropwizardDemoResource(
                dropwizardDemoConfiguration.getTemplate(), dropwizardDemoConfiguration.getDefaultName());

        DropwizardDemoHealthCheck dropwizardDemoHealthCheck = new DropwizardDemoHealthCheck(
                dropwizardDemoConfiguration.getTemplate());

        environment.jersey().register(dropwizardDemoResource);
        environment.healthChecks().register("template", dropwizardDemoHealthCheck);
    }
}
