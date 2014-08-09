package com.malsr.dropwizarddemo.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

public class DemoRepresentation {

    private long id;

    @Length(max = 3)
    private String content;

    public DemoRepresentation() {
        // Jackson deserialization
    }

    public DemoRepresentation(long id, String content) {
        this.id = id;
        this.content = content;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }
}
