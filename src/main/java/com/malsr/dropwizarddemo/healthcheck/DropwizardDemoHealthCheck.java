package com.malsr.dropwizarddemo.healthcheck;

import com.codahale.metrics.health.HealthCheck;

public class DropwizardDemoHealthCheck extends HealthCheck {

    private final String template;

    public DropwizardDemoHealthCheck(String template) {
        this.template = template;
    }

    @Override
    protected Result check() throws Exception {
        final String message = String.format(template, "GREEN LIGHT");

        if (!message.contains("GREEN LIGHT")) {
            return Result.unhealthy("Somethings not right...");
        }
        return Result.healthy();
    }
}
