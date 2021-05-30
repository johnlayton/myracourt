package com.bunnings.catalog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"ID", "Name"})
public class Supplier {
    @JsonProperty("ID")
    private String id;
    @JsonProperty("Name")
    private String name;

    public Supplier() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
