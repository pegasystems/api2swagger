package com.pega.api2swagger.builders;

import io.swagger.models.Tag;

public class TagDefinitionBuilder {

    public static TagDefinitionBuilder tagDefinitionFor(String tagName) {
        return new TagDefinitionBuilder().withName(tagName);
    }

    private String name;
    private String description;

    public TagDefinitionBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TagDefinitionBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public Tag build(){
        Tag tag = new Tag();
        tag.setName(name);
        tag.setDescription(description);
        return tag;
    }
}
