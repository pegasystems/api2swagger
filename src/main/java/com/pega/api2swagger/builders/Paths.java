package com.pega.api2swagger.builders;

import com.google.common.base.Optional;

import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Path;

public class Paths {

    public Optional<Operation> getOperation(Path path, HttpMethod verb) {
        switch (verb) {
            case DELETE:
                return Optional.fromNullable(path.getDelete());
            case GET:
                return Optional.fromNullable(path.getGet());
            case HEAD:
                return Optional.fromNullable(path.getHead());
            case OPTIONS:
                return Optional.fromNullable(path.getOptions());
            case PATCH:
                return Optional.fromNullable(path.getPatch());
            case POST:
                return Optional.fromNullable(path.getPost());
            case PUT:
                return Optional.fromNullable(path.getPut());
            default:
                return Optional.absent();
        }
    }
}
