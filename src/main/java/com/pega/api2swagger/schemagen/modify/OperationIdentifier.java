package com.pega.api2swagger.schemagen.modify;

import io.swagger.models.HttpMethod;

public class OperationIdentifier {

    public static OperationIdentifier Get(String path) {
        return Create(HttpMethod.GET, path);
    }

    public static OperationIdentifier Post(String path) {
        return Create(HttpMethod.POST, path);
    }

    public static OperationIdentifier Put(String path) {
        return Create(HttpMethod.PUT, path);
    }

    public static OperationIdentifier Delete(String path) {
        return Create(HttpMethod.DELETE, path);
    }

    public static OperationIdentifier Options(String path) {
        return Create(HttpMethod.OPTIONS, path);
    }

    public static OperationIdentifier Patch(String path) {
        return Create(HttpMethod.PATCH, path);
    }

    public static OperationIdentifier Head(String path) {
        return Create(HttpMethod.HEAD, path);
    }

    public static OperationIdentifier Create(HttpMethod post, String path) {
        return new OperationIdentifier(post, path);
    }

    public final HttpMethod verb;
    public final String path;

    public OperationIdentifier(HttpMethod verb, String path) {
        this.verb = verb;
        this.path = path;
    }
}
