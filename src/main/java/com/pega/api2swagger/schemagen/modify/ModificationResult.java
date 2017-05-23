package com.pega.api2swagger.schemagen.modify;

import com.google.common.base.Optional;
import com.pega.api2swagger.builders.Consumer;

import io.swagger.models.Swagger;

public class ModificationResult {

    public static ModificationResult failed(Swagger swagger, Consumer<ModificationFailureCause> causeConsumer) {
        return new ModificationResult(swagger, Optional.of(causeConsumer));
    }

    public static ModificationResult success(Swagger swagger) {
        return new ModificationResult(swagger, Optional.<Consumer<ModificationFailureCause>>absent());
    }

    private final Optional<Consumer<ModificationFailureCause>> causeConsumer;
    private final Swagger swagger;

    public ModificationResult(Swagger swagger, Optional<Consumer<ModificationFailureCause>> causeConsumer) {
        this.swagger = swagger;
        this.causeConsumer = causeConsumer;
    }

    public Swagger swagger() {
        return swagger;
    }

    public boolean success() {
        return successOr(new ModificationFailureCause() {
            @Override
            public void pathNotDefined(OperationIdentifier identifier) {
                //do nothing
            }

            @Override
            public void operationNotDefined(OperationIdentifier identifier) {
                //do nothing
            }
        });
    }

    public boolean successOr(ModificationFailureCause cause) {
        if (causeConsumer.isPresent()) {
            causeConsumer.get().accept(cause);
            return false;
        }
        return true;
    }
}
