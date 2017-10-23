package com.pega.api2swagger.updater;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.pega.api2swagger.utils.LogHelper;

import io.swagger.models.HttpMethod;
import io.swagger.models.Model;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Response;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;

public class SwaggerUpdater {
	private final SwaggerParser mParser = new SwaggerParser();
	private final LogHelper logger = new LogHelper(SwaggerUpdater.class);
	
	public Swagger update(String fileLocation, Swagger schemaToBe) {
		return  update(mParser.read(fileLocation), schemaToBe);
	}
	
	public Swagger update(Swagger orig, Swagger schemaToBe) {
        if(orig == null ){
        	return schemaToBe;
        }
        	
		Map<String, Path> mergedPaths = mergePathDefinitions(orig, schemaToBe);
		Map<String, Model> mergedModels = mergedModelDefinitions(orig, schemaToBe);

		orig.setPaths(mergedPaths);
		orig.setDefinitions(mergedModels);

		logger.debug("Modified swagger is: "+ orig);
		return orig;
	}

	public Map<String, Path> mergePathDefinitions(Swagger target, Swagger source) {
		LinkedHashMap<String, Path> mergedDefinitions = new LinkedHashMap<>();
		mergedDefinitions.putAll(target.getPaths());

		Map<String, Path> pathsToBeMerged = source.getPaths();

		for (Entry<String, Path> entry : pathsToBeMerged.entrySet()) {
			if (!mergedDefinitions.containsKey(entry.getKey())) {
				logger.info("source path %s is not present in existing Swagger JSON file.", entry.getKey());
				mergedDefinitions.put(entry.getKey(), entry.getValue());
			} else {
				
				logger.info("source path %s is present in existing Swagger JSON file...merging operations", entry.getKey());
				
				Map<HttpMethod, Operation> modifedOperations = mergeOperationPaths(mergedDefinitions.get(entry.getKey()),
						entry.getValue());
				
				for(Entry<HttpMethod, Operation> operation : modifedOperations.entrySet()){
					mergedDefinitions.get(entry.getKey()).set(operation.getKey().name().toLowerCase(), operation.getValue());
				}				
			}
		}

		return mergedDefinitions;

	}

	private Map<HttpMethod, Operation> mergeOperationPaths(Path target, Path source) {
		Map<HttpMethod, Operation> mergedOperations = target.getOperationMap();

		for (Entry<HttpMethod, Operation> entry : source.getOperationMap().entrySet()) {
			if (!mergedOperations.containsKey(entry.getKey())) {
				logger.info("source operation %s is not present in existing Swagger JSON path", entry.getKey());
				mergedOperations.put(entry.getKey(), entry.getValue());
			} else {
				logger.info("source operation %s is present in existing Swagger JSON path, we shall merge operation responses", entry.getKey());
				Map<String, Response> modifiedResponses = mergeOperationResponse(mergedOperations.get(entry.getKey()),
						entry.getValue());
				mergedOperations.get(entry.getKey()).getResponses().putAll(modifiedResponses);
				mergedOperations.get(entry.getKey()).setParameters(entry.getValue().getParameters());
				logger.info("source operation %s is present in existing Swagger JSON path, merging of operation responses completed", entry.getKey());
			}
		}
		return mergedOperations;
	}

	private Map<String, Response> mergeOperationResponse(Operation target, Operation source) {
		Map<String, Response> mergedResponse = target.getResponses();
		
		for (Entry<String, Response> entry : source.getResponses().entrySet()) {
			mergedResponse.put(entry.getKey(), entry.getValue());
		}
		
		return mergedResponse;
	}

	public Map<String, Model> mergedModelDefinitions(Swagger target, Swagger source) {
		LinkedHashMap<String, Model> mergedDefinitions = new LinkedHashMap<>();
		
		if(target.getDefinitions() != null ){
			mergedDefinitions.putAll(target.getDefinitions());
		}
		
		if(source.getDefinitions() != null){
			mergedDefinitions.putAll(source.getDefinitions());	
		}
		return mergedDefinitions;
	}

}
