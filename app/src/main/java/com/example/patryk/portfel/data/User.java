package com.example.patryk.portfel.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    public Integer id;

    @JsonProperty("session_id")
    public String sessionId;
    @JsonProperty("display_name")
    public String displayName;
}
