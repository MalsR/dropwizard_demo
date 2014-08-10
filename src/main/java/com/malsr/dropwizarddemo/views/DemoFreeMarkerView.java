package com.malsr.dropwizarddemo.views;

import io.dropwizard.views.View;

public class DemoFreeMarkerView extends View {

    public DemoFreeMarkerView() {
        super("demofreemarker.ftl");
    }

    public String getDemoTemplateName() {
        return "demofreemarker.ftl";
    }
}
