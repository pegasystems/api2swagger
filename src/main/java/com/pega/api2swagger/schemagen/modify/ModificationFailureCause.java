package com.pega.api2swagger.schemagen.modify;

public interface ModificationFailureCause {
    void pathNotDefined(OperationIdentifier identifier);

    void operationNotDefined(OperationIdentifier identifier);
}
