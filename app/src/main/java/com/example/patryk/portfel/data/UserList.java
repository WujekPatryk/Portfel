package com.example.patryk.portfel.data;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class UserList {

    @JsonProperty("record")
    public List<User> records = new ArrayList<User>();
}
