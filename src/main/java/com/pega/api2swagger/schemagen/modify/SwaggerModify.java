package com.pega.api2swagger.schemagen.modify;

import com.google.common.base.Optional;
import com.pega.api2swagger.builders.Consumer;
import com.pega.api2swagger.builders.Paths;

import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;

public class SwaggerModify {

    private final Paths paths = new Paths();

    public ModificationResult modify(Swagger swagger, final OperationIdentifier identifier, Consumer<Operation> modification) {
        Path path = swagger.getPath(identifier.path);
        if (null == path) {
        	return ModificationResult.failed(swagger, new Consumer<ModificationFailureCause>() {

				@Override
				public void accept(ModificationFailureCause t) {
					t.pathNotDefined(identifier);
				}
        		
			});
        }
        Optional<Operation> maybeOperation = paths.getOperation(path, identifier.verb);
        if (!maybeOperation.isPresent()) {
            return ModificationResult.failed(swagger, new Consumer<ModificationFailureCause>() {

				@Override
				public void accept(ModificationFailureCause t) {
					t.operationNotDefined(identifier);
				}
            	
			});
        }
        modification.accept(maybeOperation.get());
        return ModificationResult.success(swagger);
    }
}
