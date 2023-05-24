package com.alexpournaras.springmicroservices.web;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TextInput {
    
    @JsonProperty("text")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
