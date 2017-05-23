package com.pega.api2swagger.builders;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import io.swagger.models.Path;

public class PathBuilder {

	private final List<Consumer<Path>> operations = newArrayList();
	private final List<ParameterBuilder> parameters = newArrayList();

	public OperationBuilder withOption() {
		final OperationBuilder builder = new OperationBuilder();
		operations.add(new Consumer<Path>() {
			@Override
			public void accept(Path p) {
				p.set("options", builder.build());
			}
		});
		// operations.add(path -> path.set("options", builder.build()));
		return builder;
	}

	public OperationBuilder withPost() {
		final OperationBuilder builder = new OperationBuilder();

		operations.add(new Consumer<Path>() {
			@Override
			public void accept(Path p) {
				p.set("post", builder.build());
			}
		});

		// operations.add(path -> path.set("post", builder.build()));
		return builder;
	}

	public OperationBuilder withGet() {
		final OperationBuilder builder = new OperationBuilder();

		operations.add(new Consumer<Path>() {
			@Override
			public void accept(Path p) {
				p.set("get", builder.build());
			}
		});

		return builder;
	}

	public OperationBuilder withDelete() {
		final OperationBuilder builder = new OperationBuilder();

		operations.add(new Consumer<Path>() {
			@Override
			public void accept(Path p) {
				p.set("delete", builder.build());
			}
		});

		return builder;
	}

	public OperationBuilder withPut() {
		final OperationBuilder builder = new OperationBuilder();

		operations.add(new Consumer<Path>() {
			@Override
			public void accept(Path p) {
				p.set("put", builder.build());
			}
		});

		return builder;

	}

	public ParameterBuilder withParameterForAllOperations() {
		ParameterBuilder parameterBuilder = new ParameterBuilder();
		withParameterForAllOperations(parameterBuilder);
		return parameterBuilder;
	}

	public PathBuilder withParameterForAllOperations(String parameterName, ParameterBuilder parameterBuilder) {
		parameterBuilder.withName(parameterName);
		return withParameterForAllOperations(parameterBuilder);
	}

	public PathBuilder withParameterForAllOperations(ParameterBuilder parameterBuilder) {
		parameters.add(parameterBuilder);
		return this;
	}

	public Path build() {
		Path path = new Path();

		for (Consumer<Path> operation : operations) {
			operation.accept(path);
		}

		// operations.stream().forEach(operation -> operation.accept(path));

		for (ParameterBuilder parameterBuilder : parameters) {
			path.addParameter(parameterBuilder.build());
		}
		// parameters.stream().forEach(parameterBuilder ->
		// path.addParameter(parameterBuilder.build()));
		return path;
	}
}
