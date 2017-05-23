package com.pega.api2swagger.builders;

import com.google.common.base.Optional;

import io.swagger.models.Info;

public class InfoBuilder {
    private Optional<String> maybeVersion = Optional.absent();
    private Optional<String> maybeTitle = Optional.absent();

    public InfoBuilder withVersion(String version) {
        maybeVersion = Optional.of(version);
        return this;
    }

    public InfoBuilder withTitle(String title){
        maybeTitle = Optional.of(title);
        return this;
    }

    public Info build(){
        Info info = new Info();
        info.setVersion(maybeVersion.get());
        info.setTitle(maybeTitle.get());
        return info;
    }
}
