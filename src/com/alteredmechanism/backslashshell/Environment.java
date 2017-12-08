package com.alteredmechanism.backslashshell;

import java.util.HashMap;
import java.util.Map;

public class Environment {

    private Map<String,EnvironmentVariable> map = new HashMap<String,EnvironmentVariable>();

    public void set(String name, String value) {
        map.put(name.toLowerCase(), new EnvironmentVariable(name,value));
    }

    public String getValue(String name) {
        EnvironmentVariable var = map.get(name.toLowerCase());
        String value = null;
        if (var != null) {
            value = var.getValue();
        }
        return value;
    }

    public String getNameInOriginalCase(String name) {
        EnvironmentVariable var = map.get(name.toLowerCase());
        String value = null;
        if (var != null) {
            value = var.getName();
        }
        return value;
    }
}
